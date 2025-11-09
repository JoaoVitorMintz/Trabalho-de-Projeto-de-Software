package main.Java.service.impl;

import main.Java.model.Usuario;
import main.Java.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {
    
    @Override
    public boolean autenticar(Usuario usuario, String senha) {
        return usuario.fazerLogin(usuario.getNome(), senha);
    }

    @Override
    public void cadastrar(Usuario usuario) {

    }

    @Override
    public void remover(int userID) {
        
    }
}
