package main.Java.model;

public abstract class Usuario {
    private String nome;
    private int userID;

    protected Usuario(String nome, int userID) {
        this.nome = nome;
        this.userID = userID;
    }

    public String getNome() {
        return nome;
    }

    public int getUserID() {
        return userID;
    }

    public abstract boolean fazerLogin(String usuario, String senha);

}
