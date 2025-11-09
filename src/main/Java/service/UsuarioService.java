package main.Java.service;

import main.Java.model.Usuario;

public interface UsuarioService {
    boolean autenticar(Usuario usuario, String senha);
    void cadastrar(Usuario usuario);
    void remover(int userID);
}
