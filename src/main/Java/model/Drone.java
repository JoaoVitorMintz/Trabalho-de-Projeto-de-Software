package main.Java.model;
import java.util.List;

public class Drone {
    
    private int ID;
    private List<Sensor> sensores;
    private String Status;


    public void atualizarStatus(String novoStatus) {
        this.Status = novoStatus;
    }

    public void cadastrarDrone(int ID, List<Sensor> sensores, String status) {
        this.ID = ID;
        this.sensores = sensores;
        this.Status = status;
    }
}
