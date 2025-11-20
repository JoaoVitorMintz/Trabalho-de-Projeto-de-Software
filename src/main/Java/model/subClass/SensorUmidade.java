package main.Java.model.subClass;

import main.Java.model.Sensor;

public class SensorUmidade extends Sensor {

    private double umidade;

    public SensorUmidade() {
        super("Umidade");
    }

    public SensorUmidade(double umidade) {
        super("Umidade");
        this.umidade = umidade;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public boolean validarUmidade() {
        return umidade >= 0 && umidade <= 100;
    }
}
