package main.Java.model.subClass;

import main.Java.model.Sensor;

public class SensorTemperatura extends Sensor {
    private double temperatura;

    public boolean validaTemperatura(SensorTemperatura temperatura) {
        return true; // Apenas para não aparecer mensagem de erro
    }
}
