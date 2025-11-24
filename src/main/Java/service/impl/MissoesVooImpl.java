package main.Java.service.impl;

import java.sql.*;
import java.util.List;
import main.Java.model.Sensor;
import main.Java.service.MissoesVoo;

public class MissoesVooImpl implements MissoesVoo {

    private Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/seuschema";
        String user = "root";
        String pass = "1234";
        return DriverManager.getConnection(url, user, pass);
    }

    @Override
    public boolean verificarSobreposicao(String data, String nomeArea, List<Sensor> sensores) {

        String sql = "SELECT COUNT(*) FROM missoes WHERE data = ? AND area = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data);
            stmt.setString(2, nomeArea);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Já existe missão marcada
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar missão", e);
        }

        return false;
    }

    @Override
    public void agendarMissao(String data, String nomeArea, List<Sensor> sensores) {

        String sql = "INSERT INTO missoes (data, area, qtdSensores) VALUES (?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data);
            stmt.setString(2, nomeArea);
            stmt.setInt(3, sensores.size());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao agendar missão", e);
        }
    }
}
