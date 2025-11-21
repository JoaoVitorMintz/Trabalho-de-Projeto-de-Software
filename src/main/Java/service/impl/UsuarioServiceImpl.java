package main.Java.service.impl;

import java.sql.*;
import main.Java.model.Usuario;
import main.Java.model.subClass.Administrador;
import main.Java.model.subClass.Operador;
import main.Java.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

    private Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/seuschema";
        String user = "root";
        String pass = "1234";
        return DriverManager.getConnection(url, user, pass);
    }

    @Override
    public boolean autenticar(String nome, String senha) {
        String sql = "SELECT senha FROM usuario WHERE nome = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaBanco = rs.getString("senha");
                return senhaBanco.equals(senha);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }

        return false;
    }

    @Override
    public void cadastrar(Usuario usuario, String senha) {
        String sql = "INSERT INTO usuario (nome, email, userID, tipo, senha) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getUserID());
            stmt.setString(4, usuario instanceof Administrador ? "ADMIN" : "OPERADOR");
            stmt.setString(5, senha);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário", e);
        }
    }

    @Override
    public void remover(int userID) {
        String sql = "DELETE FROM usuario WHERE userID = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover usuário", e);
        }
    }

    @Override
    public Usuario buscar(String nome) {
        String sql = "SELECT * FROM usuario WHERE nome = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");
                int id = rs.getInt("userID");
                String email = rs.getString("email");

                if (tipo.equals("ADMIN")) {
                    return new Administrador(nome, id, email);
                } else {
                    return new Operador(nome, id, email);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }

        return null;
    }
}