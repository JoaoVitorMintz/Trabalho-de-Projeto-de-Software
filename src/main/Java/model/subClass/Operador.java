package main.Java.model.subClass;

import main.Java.model.Usuario;

public class Operador extends Usuario {

    public Operador(String nome, int userID, String email) {
        super(nome, userID, email);
    }

    @Override
    public boolean fazerLogin(String usuario, String senha) {
        return true;
    }

    public void operarDrone(int escolha) {
        System.out.print("\nOperando drone...\n");

        if (escolha == 1) {
            registrarDados();
        } else if (escolha == 2) {
            relatorioBasico();
        }
    }

    private void registrarDados() {
        return; // Implementar método para registrar os dados no banco
    }

    private void relatorioBasico() {
        System.out.print("RELATÓRIO DA OPERAÇÃO:\n");
        
    }
}