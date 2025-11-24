package main.Java.service.impl;

import java.sql.*;
import java.util.List;
import java.util.UUID;
import main.Java.model.Sensor;
import main.Java.service.JDBC;
import main.Java.service.MissoesVoo;

public class MissoesVooImpl implements MissoesVoo {

    


    // VERIFICAR SOBREPOSIÇÃO DE MISSÃO
    @Override
    public boolean verificarSobreposicao(String dataStr, String nomeArea, List<Sensor> sensores) {

        String sqlArea = "SELECT id FROM CadastroAreas WHERE localizacao = ?";
        String sqlCheck = "SELECT COUNT(*) FROM MissoesVoo WHERE data = ? AND area_id = ?";

        try (Connection conn = JDBC.conectar()) {

            // 1 — pegar ID da área
            int areaId;
            try (PreparedStatement stmt = conn.prepareStatement(sqlArea)) {
                stmt.setString(1, nomeArea);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    throw new RuntimeException("Área não encontrada: " + nomeArea);
                }
                areaId = rs.getInt("id");
            }

            // 2 — converter data
            java.sql.Date data = java.sql.Date.valueOf(formatarData(dataStr));

            // 3 — verificar sobreposição
            try (PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
                stmt.setDate(1, data);
                stmt.setInt(2, areaId);

                ResultSet rs = stmt.executeQuery();
                rs.next();
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar missão", e);
        }
    }

    // AGENDAR MISSÃO
    @Override
    public void agendarMissao(String dataStr, String nomeArea, List<Sensor> sensores) {

        String sqlArea = "SELECT id FROM CadastroAreas WHERE localizacao = ?";
        String sqlInsert = """
            INSERT INTO MissoesVoo (idMissao, data, drone_id, area_id)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = JDBC.conectar()) {

            // 1 — pegar ID da área
            int areaId;
            try (PreparedStatement stmt = conn.prepareStatement(sqlArea)) {
                stmt.setString(1, nomeArea);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    throw new RuntimeException("Área não encontrada: " + nomeArea);
                }
                areaId = rs.getInt("id");
            }

            // 2 — escolher drone (vamos pôr 1 fixo até você enviar sua lógica)
            int droneId = 1;

            // 3 — converter data
            java.sql.Date data = java.sql.Date.valueOf(formatarData(dataStr));

            // 4 — gerar idMissao único
            String idMissao = UUID.randomUUID().toString();

            // 5 — inserir missão
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {

                stmt.setString(1, idMissao);
                stmt.setDate(2, data);
                stmt.setInt(3, droneId);
                stmt.setInt(4, areaId);

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao agendar missão", e);
        }
    }

    // Converte "DD/MM/YYYY" → "YYYY-MM-DD"
    private String formatarData(String dataStr) {
        if (dataStr.contains("/")) {
            String[] p = dataStr.split("/");
            return p[2] + "-" + p[1] + "-" + p[0];
        }
        if (dataStr.contains(".")) {
            String[] p = dataStr.split("\\.");
            return p[2] + "-" + p[1] + "-" + p[0];
        }
        return dataStr;
    }
}
