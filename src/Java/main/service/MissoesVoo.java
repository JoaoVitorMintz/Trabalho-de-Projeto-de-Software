package java.main.service;

import java.main.model.CadastroArea;
import java.main.model.Drone;
import java.time.LocalDate;

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
