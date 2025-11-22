package main.Java;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import main.Java.model.*;
import main.Java.model.subClass.Administrador;
import main.Java.model.subClass.Operador;
import main.Java.model.subClass.SensorInfravermelho;
import main.Java.model.subClass.SensorTemperatura;
import main.Java.model.subClass.SensorUmidade;
import main.Java.service.*;
import main.Java.service.impl.UsuarioServiceImpl;

public class ApplicationMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UsuarioService userService = new UsuarioServiceImpl(); // CORRETO
        CadastroArea area = new CadastroArea();
        Drone drone = new Drone();

        boolean continua = true;

        while (continua) {

            System.out.println("""
                Escolha o que deseja fazer:
                [1] - Cadastrar-se
                [2] - Logar
            """);

            int opcao = sc.nextInt();

            if (opcao == 1) {

                System.out.print("Nome: ");
                String nome = sc.next();

                System.out.print("Email: ");
                String email = sc.next();

                System.out.print("Senha: ");
                String senha = sc.next();

                int id = new Random().nextInt(9999);

                System.out.println("Cargo [1] Operador | [2] Administrador");
                int cargo = sc.nextInt();

                Usuario novo;

                if (cargo == 1) {
                    novo = new Operador(nome, id, email);
                } else {
                    novo = new Administrador(nome, id, email);
                }

                userService.cadastrar(novo, senha);
                System.out.println("Usuário cadastrado com sucesso!");
                continue;
            }

            else if (opcao == 2) {

                System.out.print("Nome: ");
                String nome = sc.next();

                System.out.print("Senha: ");
                String senha = sc.next();

                boolean autenticado = userService.autenticar(nome, senha);

                if (!autenticado) {
                    System.out.println("Login inválido!");
                    continue;
                }

                Usuario usuario = userService.buscar(nome);

                System.out.println("\nLogin realizado com sucesso!\n");

                if (usuario instanceof Administrador) {
                    boolean menuAdmin = true;

                    while (menuAdmin) {

                        System.out.println("""
                            Escolha o que deseja fazer:
                            [1] - Cadastrar área agrícola
                            [2] - Cadastrar drone
                            [3] - Agendar missão de voo
                            [4] - Sair
                        """);

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
                                System.out.println("Indique a data (DD/MM/YYYY ou DD.MM.YYYY): ");
                                String data = sc.next();
                                System.out.print("Nome da área: ");
                                String nomeArea = sc.next();

                                System.out.print("Indique o número de sensores necessários: ");
                                int qtdAgendar = sc.nextInt();

                                List<Sensor> sensoresUsados = new LinkedList<>();

                                for (int i = 0; i < qtdAgendar; i++) {
                                    System.out.println("""
                                        Escolha o sensore a ser usado:
                                        [1] - Temperatura
                                        [2] - Umidade
                                        [3] - Infravermelho
                                    """);
                                int sAgendar = sc.nextInt();
                                }

                                MissoesVoo ms = new MissoesVoo();

                                if(ms.verificarSobreposicao(data, nomeArea, sAgendar)) {
                                    System.out.print("Erro ao agendar, já há uma missão agendada.");
                                    break;
                                }

                                agendarMissao(data, nomeArea, sAgendar);
                                break;
                            case 4:
                                menuAdmin = false;
                                break;

                            default:
                                System.out.println("Opção inválida!");
                        }
                    }
                } else if (usuario instanceof Operador) {
                    boolean menuOp = true;

                    while (menuOp) {

                        System.out.println("""
                            Escolha o que deseja fazer:
                            [1] - Registrar dados coletados
                            [2] - Relatório básico
                            [3] - Sair
                        """);

                        int escolha = sc.nextInt();

                        switch (escolha) {

                            case 1:
                                System.out.println("Insira o nome do arquivo em imagem (JPG): ");
                                String nomeArq = sc.next();
                                System.out.print("Para a próxima parte, caso não tenha usado determinado sensor, coloque 0.\n");
                                for (int i = 0; i < 10; i++) {
                                    System.out.print("Insira os dados " + i+1 + " do sensor Térmico (" + i+1 + " de 10): ");
                                    List<Double> dadosTerm;
                                    System.out.print("Insira os dados do sensor de Umidade (" + i+1 + " de 10): ");
                                    List<Double> dadosUmid;
                                    System.out.print("Insira os dados " + i+1 + " do sensor Térmico (" + i+1 + " de 10): ");
                                    List<Double> dadosInfraVerm;
                                }
                                break;

                            case 2:
                                System.out.println("Geração de relatório ainda não implementado.");
                                break;

                            case 3:
                                menuOp = false;
                                break;

                            default:
                                System.out.println("Opção inválida!");
                        }
                    }
                }
            }
        }
        sc.close();
    }
}
