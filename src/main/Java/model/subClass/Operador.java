package main.Java.model.subClass;

import main.Java.model.Usuario;

public class Operador extends Usuario {

    public Operador(String nome, int userID, String email) {
        super(nome, userID, email);
    }

    @Override
    public boolean fazerLogin(String usuario, String senha) {
        return true;
    }
}