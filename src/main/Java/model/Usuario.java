package main.Java.model;

public abstract class Usuario {
    private String nome;
    private int userID;
    private String email;
    private String setor;

    protected Usuario(String nome, int userID, String email, String setor) {
        this.nome = nome;
        this.userID = userID;
        this.email = email;
        this.setor = setor;
    }

    public String getNome() { return nome; }
    public int getUserID() { return userID; }
    public String getEmail() { return email; }
    public String getSetor() { return setor; }

}
 