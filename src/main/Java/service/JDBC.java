package main.Java.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    public static Connection conectar() throws SQLException {
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do PostgreSQL não encontrado!", e);
        }
        
        String host = "localhost";
        String port = "5432";
        String db = "projeto_sensores";
        String user = "usuario";
        String pass = "1234";

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

        return DriverManager.getConnection(url, user, pass);
    }

}
