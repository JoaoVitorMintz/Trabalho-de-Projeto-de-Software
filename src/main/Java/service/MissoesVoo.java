package main.Java.service;

import java.util.List;
import main.Java.model.Sensor;

public interface MissoesVoo {

    boolean verificarSobreposicao(String data, String nomeArea, List<Sensor> sensores);

    void agendarMissao(String data, String nomeArea, List<Sensor> sensores);

}
