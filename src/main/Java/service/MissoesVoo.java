package main.Java.service;

import main.Java.model.Sensor;

public class MissoesVoo {

    void agendarMissao(String data, String nomeArea, List<Sensor> sensores);

    boolean verificarSobreposicao(String data, String nomeArea, List<Sensor> sensores);
}
