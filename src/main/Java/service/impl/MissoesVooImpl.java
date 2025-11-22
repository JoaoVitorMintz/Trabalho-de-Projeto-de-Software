package main.Java.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import main.Java.service.MissoesVoo;

public class MissoesVooImpl implements MissoesVoo {

    private Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/seuschema";
        String user = "root";
        String pass = "1234";
        return DriverManager.getConnection(url, user, pass);
    }

    public void agendarMissao(String data, String nomeArea, List<Sensor> sensores) {
        
    }
    
}
