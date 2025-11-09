package main.Java.service;

public class Autorizacao {
    private String senhaHash;
    private String role;

    public String verificarRole(String role) {
        return role;
    }

    public boolean verificarSenha(String senha) {
        return true; // Apenas para não aparecer mensagem de erro
    }

    public String getSenhaHash() {
        return senhaHash;
    }
}
