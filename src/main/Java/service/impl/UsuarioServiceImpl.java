package main.Java.service.impl;

import java.sql.*;
import main.Java.model.Usuario;
import main.Java.model.subClass.Administrador;
import main.Java.model.subClass.Operador;
import main.Java.service.JDBC;
import main.Java.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

    // AUTENTICAÇÃO
    @Override
    public boolean autenticar(String nome, String senha) {
        String sql = """
            SELECT a.senha_hash
            FROM Usuario u
            JOIN Autorizacao a ON a.usuario_id = u.id
            WHERE u.nome = ?
        """;

        try (Connection conn = JDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return senha.equals(rs.getString("senha_hash"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }

        return false;
    }

    // CADASTRAR USUÁRIO
    @Override
    public void cadastrar(Usuario usuario, String senha) {

        String sqlUsuario = "INSERT INTO Usuario (nome, setor) VALUES (?, ?) RETURNING id";

        String sqlAuth = """
            INSERT INTO Autorizacao (usuario_id, senha_hash, role)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = JDBC.conectar()) {

            // 1 — inserir na tabela Usuario
            int novoId;
            try (PreparedStatement stmt = conn.prepareStatement(sqlUsuario)) {
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getSetor());
                ResultSet rs = stmt.executeQuery();
                rs.next();
                novoId = rs.getInt("id");
            }

            // 2 — inserir na tabela Autorizacao
            try (PreparedStatement stmt = conn.prepareStatement(sqlAuth)) {
                stmt.setInt(1, novoId);
                stmt.setString(2, senha);
                stmt.setString(3, usuario instanceof Administrador ? "ADMIN" : "OPERADOR");
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário", e);
        }
    }

    // REMOVER USUÁRIO
    @Override
    public void remover(int userID) {
        String deleteAuth = "DELETE FROM Autorizacao WHERE usuario_id = ?";
        String deleteUser = "DELETE FROM Usuario WHERE id = ?";

        try (Connection conn = JDBC.conectar()) {

            try (PreparedStatement stmt = conn.prepareStatement(deleteAuth)) {
                stmt.setInt(1, userID);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(deleteUser)) {
                stmt.setInt(1, userID);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover usuário", e);
        }
    }

    // BUSCAR USUÁRIO
    @Override
    public Usuario buscar(String nome) {
        String sql = """
            SELECT u.id, u.setor, a.role
            FROM Usuario u
            JOIN Autorizacao a ON a.usuario_id = u.id
            WHERE u.nome = ?
        """;

        try (Connection conn = JDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String setor = rs.getString("setor");
                String role = rs.getString("role");

                if (role.equals("ADMIN")) {
                    return new Administrador(nome, id, setor);
                } else {
                    return new Operador(nome, id, setor);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }

        return null;
    }
}
