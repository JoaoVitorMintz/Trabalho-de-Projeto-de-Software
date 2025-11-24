package main.Java.service;

import java.util.List;

public class Relatorio {

    public void gerarRelatorio(List<Double> term,
                               List<Double> umid,
                               List<Double> infra) {

        // Verifica se recebeu listas válidas
        if (term == null || umid == null || infra == null ||
            term.isEmpty() || umid.isEmpty() || infra.isEmpty()) {

            System.out.println("❌ Nenhum dado foi registrado ainda!");
            return;
        }

        System.out.println("\n========= RELATÓRIO DE MEDIÇÕES =========\n");

        System.out.println("Valores Térmicos: " + term);
        System.out.println("Média Térmica: " + media(term));

        System.out.println("\nValores de Umidade: " + umid);
        System.out.println("Média de Umidade: " + media(umid));

        System.out.println("\nValores Infravermelho: " + infra);
        System.out.println("Média Infravermelho: " + media(infra));

        System.out.println("\n=========================================\n");
    }

    private double media(List<Double> lista) {
        double soma = 0;
        for (double v : lista) soma += v;
        return soma / lista.size();
    }
}
