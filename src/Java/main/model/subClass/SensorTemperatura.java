package java.main.model.subClass;

import java.main.model.Sensor;

public class SensorTemperatura extends Sensor {
    private double temperatura;

    public boolean validaTemperatura(SensorTemperatura temperatura) {
        return true; // Apenas para não aparecer mensagem de erro
    }
}
