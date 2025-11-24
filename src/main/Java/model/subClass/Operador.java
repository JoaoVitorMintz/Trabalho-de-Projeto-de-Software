package main.Java.model.subClass;

import main.Java.model.Usuario;

public class Operador extends Usuario {

    public Operador(String nome, int userID, String email) {
        super(nome, userID, email, "OPERADOR");
    }


    public void operarDrone(int escolha) {
        System.out.print("\nOperando drone...\n");
    }

    private void relatorioBasico() {
        System.out.print("RELATÓRIO DA OPERAÇÃO:\n");
    }
}