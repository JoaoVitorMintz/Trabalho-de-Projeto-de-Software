package main.Java.model.subClass;

import main.Java.model.Sensor;

public class SensorTemperatura extends Sensor {

    private double temperatura;

    public SensorTemperatura() {
        super("Térmico");
    }

    public SensorTemperatura(double temperatura) {
        super("Térmico");
        this.temperatura = temperatura;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public boolean validarTemperatura() {
        // Apenas exemplo
        return temperatura >= -20 && temperatura <= 60;
    }
}