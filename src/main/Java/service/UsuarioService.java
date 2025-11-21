package main.Java.service;

import main.Java.model.Usuario;

public interface UsuarioService {

    boolean autenticar(String nome, String senha);

    void cadastrar(Usuario usuario, String senha);

    void remover(int userID);

    Usuario buscar(String nome);
}
