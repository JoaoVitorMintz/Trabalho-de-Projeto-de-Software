package main.Java.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    public static Connection conectar() throws SQLException {

        String host = System.getenv("POSTGRES_HOST");
        String port = System.getenv("POSTGRES_PORT");
        String db   = System.getenv("POSTGRES_DB");
        String user = System.getenv("POSTGRES_USER");
        String pass = System.getenv("POSTGRES_PASSWORD");

        if (host == null || host.isEmpty()) host = "localhost";
        if (port == null || port.isEmpty()) port = "5432";
        if (db == null || db.isEmpty()) db = "projeto_sensores";
        if (user == null || user.isEmpty()) user = "usuario";
        if (pass == null || pass.isEmpty()) pass = "1234";

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

        return DriverManager.getConnection(url, user, pass);
    }

}
