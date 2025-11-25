package main.Java.model.subClass;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import main.Java.model.CadastroArea;
import main.Java.model.Drone;
import main.Java.model.Sensor;
import main.Java.model.Usuario;
import main.Java.service.MissoesVoo;
import main.Java.service.impl.MissoesVooImpl;

public class Administrador extends Usuario {
    

    public Administrador(String nome, int userID, String email) {
        super(nome, userID, email, "ADMINISTRADOR");
    }

    public void gerenciarSistema(CadastroArea area, Drone drone, Scanner sc) {
        boolean menuAdmin = true;

        while (menuAdmin) {

            System.out.println("""
                Escolha o que deseja fazer:
                [1] - Cadastrar área agrícola
                [2] - Cadastrar drone
                [3] - Agendar missão de voo
                [4] - Sair
            """);
            System.out.print("-> ");
            int escolha = sc.nextInt();

            switch (escolha) {

                case 1:
                    System.out.print("Tamanho (KM²): ");
                    double tam = sc.nextDouble();

                    System.out.print("Localização: ");
                    String localizacao = sc.next();

                    System.out.print("Tipo de cultivo: ");
                    String cultivo = sc.next();

                    area.cadastrar(tam, localizacao, cultivo);
                    System.out.println("Área cadastrada!");
                    break;

                case 2:
                    System.out.print("ID do drone: ");
                    int idDrone = sc.nextInt();

                    System.out.print("Quantidade de sensores: ");
                    int qtd = sc.nextInt();

                    List<Sensor> sensores = new LinkedList<>();

                    for (int i = 0; i < qtd; i++) {
                        System.out.println("""
                            Escolha o sensor:
                            [1] - Temperatura
                            [2] - Umidade
                            [3] - Infravermelho
                        """);
                        System.out.print("-> ");
                        int s = sc.nextInt();

                        if (s == 1) sensores.add(new SensorTemperatura());
                        else if (s == 2) sensores.add(new SensorUmidade());
                        else sensores.add(new SensorInfravermelho());
                    }

                    System.out.print("Status (ativo/inativo): ");
                    String status = sc.next();

                    drone.cadastrarDrone(idDrone, sensores, status);
                    System.out.println("Drone cadastrado!");
                    break;

                case 3:
                    System.out.println("Indique a data (DD/MM/YYYY): ");
                    String data = sc.next();
                    System.out.print("Nome da área: ");
                    String nomeArea = sc.next();

                    System.out.print("Indique o número de sensores necessários: ");
                    int qtdAgendar = sc.nextInt();

                    MissoesVoo ms = new MissoesVooImpl();
                    List<Sensor> sensoresMissao = new LinkedList<>();

                    for (int i = 0; i < qtdAgendar; i++) {
                        System.out.println("""
                            Escolha o sensore a ser usado:
                            [1] - Temperatura
                            [2] - Umidade
                            [3] - Infravermelho
                        """);
                        System.out.print("-> ");
                        int sAgendar = sc.nextInt();

                        if (sAgendar == 1) sensoresMissao.add(new SensorTemperatura());
                        else if (sAgendar == 2) sensoresMissao.add(new SensorUmidade());
                        else sensoresMissao.add(new SensorInfravermelho());
                    }

                    if (ms.verificarSobreposicao(data, nomeArea, sensoresMissao)) {
                        System.out.print("Erro ao agendar, já há uma missão agendada.");
                        break;
                    } else {
                        ms.agendarMissao(data, nomeArea, sensoresMissao);
                    }

                    break;

                case 4:
                    menuAdmin = false;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
