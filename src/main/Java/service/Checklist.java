package main.Java.service;

import java.util.List;

public class Checklist {
    private double bateria;
    private List<Boolean> sensoresFunc;

    public Checklist(double bateria, List<Boolean> sensoresFunc) {
        this.bateria = bateria;
        this.sensoresFunc = sensoresFunc;
    }

    public boolean validarChecklist() {
        // Definir um limite mínimo genérico para a bateria
        final double LIMITE_MINIMO_BATERIA = 20.0;

        // Verifica bateria
        if (bateria < LIMITE_MINIMO_BATERIA) {
            System.out.println("Bateria abaixo do nível mínimo permitido!");
            return false;
        }

        // Verifica se todos os sensores estão funcionando
        if (sensoresFunc == null || sensoresFunc.isEmpty()) {
            System.out.println("Nenhum sensor informado!");
            return false;
        }

        for (int i = 0; i < sensoresFunc.size(); i++) {
            if (!sensoresFunc.get(i)) {
                System.out.println("Sensor " + (i + 1) + " com falha!");
                return false;
            }
        }

        // Se passou por todas as verificações
        System.out.println("Checklist validado com sucesso!");
        return true;
    }

    // Getters e Setters
    public double getBateria() {
        return bateria;
    }

    public void setBateria(double bateria) {
        this.bateria = bateria;
    }

    public List<Boolean> getSensoresFunc() {
        return sensoresFunc;
    }

    public void setSensoresFunc(List<Boolean> sensoresFunc) {
        this.sensoresFunc = sensoresFunc;
    }
}
