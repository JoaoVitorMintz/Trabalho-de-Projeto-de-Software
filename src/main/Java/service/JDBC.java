package main.Java.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    public static Connection conectar() {
        String url = "jdbc:postgresql://localhost:5432/projeto_sensores";
        String user = "usuario";
        String pass = "1234";

        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco!", e);
        }

    }

}
