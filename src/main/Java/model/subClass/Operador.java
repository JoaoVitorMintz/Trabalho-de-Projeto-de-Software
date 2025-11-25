package main.Java.model.subClass;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import main.Java.model.Usuario;
import main.Java.service.RegistroDados;
import main.Java.service.Relatorio;

public class Operador extends Usuario {

    public Operador(String nome, int userID, String email) {
        super(nome, userID, email, "OPERADOR");
    }

    public void executarOperacoes(RegistroDados reg, Relatorio rel) {
        Scanner sc = new Scanner(System.in);
        boolean menuOp = true;

        while (menuOp) {

            System.out.println("""
                Escolha o que deseja fazer:
                [1] - Registrar dados coletados
                [2] - Relatório básico
                [3] - Sair
            """);
            System.out.print("-> ");
            int escolha = sc.nextInt();

            switch (escolha) {

                case 1:
                    List<Double> valoresTerm = new LinkedList<>();
                    List<Double> valoresUmid = new LinkedList<>();
                    List<Double> valoresInfra = new LinkedList<>();
                    System.out.println("Insira 5 valores para cada sensor (0 caso não utilizado):\n");

                    for (int i = 0; i < 5; i++) {
                        System.out.print("Valor " + (i + 1) + " do sensor térmico: ");
                        valoresTerm.add(sc.nextDouble());

                        System.out.print("Valor " + (i + 1) + " do sensor de umidade: ");
                        valoresUmid.add(sc.nextDouble());

                        System.out.print("Valor " + (i + 1) + " do sensor infravermelho: ");
                        valoresInfra.add(sc.nextDouble());
                    }

                    System.out.print("Nome do arquivo para salvar: ");
                    String nomeArq = sc.next();

                    reg.salvarDados(nomeArq, valoresTerm, valoresUmid, valoresInfra);
                    break;

                case 2:
                    rel.gerarRelatorio(reg.getValoresTerm(), reg.getValoresUmid(), reg.getValoresInfra());
                    break;

                case 3:
                    menuOp = false;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
