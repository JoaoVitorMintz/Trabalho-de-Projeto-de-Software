package main.Java.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RegistroDados {

    // Estes atributos armazenam os valores inseridos
    private List<Double> valoresTerm;
    private List<Double> valoresUmid;
    private List<Double> valoresInfra;

    public void salvarDados(String nomeArquivo,
                           List<Double> valoresTerm,
                           List<Double> valoresUmid,
                           List<Double> valoresInfra) {

        // Salva internamente (para uso no relatório)
        this.valoresTerm = valoresTerm;
        this.valoresUmid = valoresUmid;
        this.valoresInfra = valoresInfra;

        File pasta = new File("dados");
        if (!pasta.exists()) pasta.mkdir();

        File arquivo = new File("dados/" + nomeArquivo + ".txt");

        try (FileWriter fw = new FileWriter(arquivo)) {
            fw.write("Valores Térmicos:\n");
            for (Double v : valoresTerm) fw.write(v + "\n");

            fw.write("\nValores de Umidade:\n");
            for (Double v : valoresUmid) fw.write(v + "\n");

            fw.write("\nValores Infravermelhos:\n");
            for (Double v : valoresInfra) fw.write(v + "\n");

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados", e);
        }
    }

    public List<Double> getValoresTerm() { return valoresTerm; }
    public List<Double> getValoresUmid() { return valoresUmid; }
    public List<Double> getValoresInfra() { return valoresInfra; }
}
