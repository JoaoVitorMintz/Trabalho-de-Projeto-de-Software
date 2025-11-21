package main.Java.model.subClass;

import main.Java.model.Usuario;

public class Administrador extends Usuario {

    public Administrador(String nome, int userID, String email) {
        super(nome, userID, email);
    }

    @Override
    public boolean fazerLogin(String usuario, String senha) {
        // Pode ser ignorado pois o login agora é pelo BD
        return true;
    }

    public void gerenciarSistema() {
        System.out.print("""
                Gerenciando sistema:
                [1] - Cadastrar área agrícola
                [2] - Cadastrar drone
                [3] - Agendar missão de voo
                """);
    }
}