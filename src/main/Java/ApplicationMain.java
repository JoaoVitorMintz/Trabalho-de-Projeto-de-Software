package main.Java;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import main.Java.model.*;
import main.Java.model.subClass.SensorInfravermelho;
import main.Java.model.subClass.SensorTemperatura;
import main.Java.model.subClass.SensorUmidade;
import main.Java.service.*;

public class ApplicationMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Autorizacao auth = new Autorizacao();
        CadastroArea area = new CadastroArea();
        Drone drone = new Drone();


        int opcao;
        boolean continua = true;
        String username, password;

        do { 
            System.out.println("Faça login:\n");
            System.out.print("\nUsername: ");
            username = sc.next();
            System.out.print("\nSenha: ");
            password = sc.next();

            if (auth.verificarRole(username) == "Administrador") {
                System.out.print("Escolha o que deseja fazer:\n");
                System.out.print("[1] - Cadastrar área agrícola\n");
                System.out.print("[2] - Cadastrar drone\n");
                System.out.print("[3] - Agendar missão de voo\n");
                System.out.print("[4] - Sair\n");
                opcao = sc.nextInt();

                switch(opcao) {
                    case 1:
                        Double tam;
                        String localizacao, cultivo;
                        System.out.print("Insira tamanho (em KM²): ");
                        tam = sc.nextDouble();
                        System.out.print("Insira localização: ");
                        localizacao = sc.next();
                        System.out.print("Insira tipo de cultivo: ");
                        cultivo = sc.next();

                        area.cadastrar(tam, localizacao, cultivo);
                        System.out.print("Cadastro realizado!\n");
                        break;
                    case 2:
                        int ID, qtdSensores, escolha;
                        List<Sensor> sensores = new LinkedList();
                        String status;

                        System.out.print("Insira o ID: ");
                        ID = sc.nextInt();

                        // Inserção dos sensores em uma lista ligada
                        System.out.print("Quantos sensores há no drone?");
                        qtdSensores = sc.nextInt();
                        for (int i = 0; i < qtdSensores; i++) {
                            System.out.print("""
                                Insira sensores presentes nele:
                                [1] - Térmico
                                [2] - Umidade
                                [3] - Infravermelho
                                """);
                            escolha = sc.nextInt();
                            if (escolha == 1) {
                                sensores.add(new SensorTemperatura());
                            } else if (escolha == 2) {
                                sensores.add(new SensorUmidade());
                            } else {
                                sensores.add(new SensorInfravermelho());
                            }
                        }
                        System.out.print("Insira status do drone (ativo/inativo): ");
                        status = sc.next();

                        drone.cadastrarDrone(ID, sensores, status);
                        break;
                    case 3:
                        break;
                    case 4:
                        System.out.print("\n Programa finalizado! \n");
                        continua = false;
                        break;
                    default:
                        System.out.print("Digite um valor válido.");
                        break;
                }

            
            } else if (auth.verificarRole(username) == "Operador") {
                System.out.print("Escolha o que deseja fazer:\n");
                System.out.print("[1] - Registrar dados coletados\n");
                System.out.print("[2] - Relatório básico\n");
                System.out.print("[3] - Sair\n");
                opcao = sc.nextInt();
            }

        } while (continua);
    }
}
