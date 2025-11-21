package main.Java.service;

public class Autorizacao {

    private String senhaHash;
    private String token;
    private String role;

    public boolean validarSenha(String senhaDigitada) {
        return senhaHash.equals(Integer.toString(senhaDigitada.hashCode()));
    }

    public boolean validarToken(String tk) {
        return tk != null && tk.equals(token);
    }

    public String verificarRole() {
        return role;
    }
}
