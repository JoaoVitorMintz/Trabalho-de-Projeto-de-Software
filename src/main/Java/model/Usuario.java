package main.Java.model;

public abstract class Usuario {
    private String nome;
    private int userID;
    private String email;

    protected Usuario(String nome, int userID, String email) {
        this.nome = nome;
        this.userID = userID;
        this.email = email;
    }

    public String getNome() { return nome; }
    public int getUserID() { return userID; }
    public String getEmail() { return email; }

    public abstract boolean fazerLogin(String usuario, String senha);
}
 