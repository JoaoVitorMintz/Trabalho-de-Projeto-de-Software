package main.Java.model.subClass;

import main.Java.model.Usuario;

public class Operador extends Usuario {

    public Operador(String nome, int userID) {
        super(nome, userID);
    }

    @Override
    public boolean fazerLogin(String usuario, String senha) {
        return usuario.equals("admin") && senha.equals("1234");
    }
}
