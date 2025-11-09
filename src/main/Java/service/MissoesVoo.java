package main.Java.service;

import java.time.LocalDate;
import main.Java.model.CadastroArea;
import main.Java.model.Drone;

public class MissoesVoo {
    
    private LocalDate Data;
    private String idMissao;
    private CadastroArea area;
    private Drone drone;


    public void atribuirDrone(Drone drone) {

    }

    public boolean verificarSopreposicao() {
        return true; // apenas para sumir o erro.
    }
}
