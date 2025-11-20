package main.Java.model.subClass;

import main.Java.model.Sensor;

public class SensorInfravermelho extends Sensor {

    private boolean ativo;

    public SensorInfravermelho() {
        super("Infravermelho");
        this.ativo = true;
    }

    public SensorInfravermelho(boolean ativo) {
        super("Infravermelho");
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean detectarPragas() {
        return ativo; // apenas simula detecção
    }
}
