package main.Java;

import java.util.*;
import main.Java.model.*;
import main.Java.model.subClass.*;
import main.Java.service.UsuarioService;
import main.Java.service.impl.UsuarioServiceImpl;

public class ApplicationMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UsuarioService userService = new UsuarioServiceImpl();

        boolean loopPrincipal = true;

        while (loopPrincipal) {

            System.out.println("""
                [1] Cadastrar
                [2] Login
            """);

            int opc = sc.nextInt();

            // CADASTRO
            if (opc == 1) {

                System.out.print("Nome: ");
                String nome = sc.next();

                System.out.print("Email: ");
                String email = sc.next();

                System.out.print("Senha: ");
                String senha = sc.next();

                System.out.println("Cargo: [1] Operador | [2] Administrador");
                int cargo = sc.nextInt();

                int id = new Random().nextInt(9999);

                Usuario u = (cargo == 1)
                        ? new Operador(nome, id, email)
                        : new Administrador(nome, id, email);

                userService.cadastrar(u, senha);
                System.out.println("Usuário cadastrado!\n");
                continue;
            }

            // LOGIN
            if (opc == 2) {

                System.out.print("Nome: ");
                String nome = sc.next();
                System.out.print("Senha: ");
                String senha = sc.next();

                if (!userService.autenticar(nome, senha)) {
                    System.out.println("Login inválido.\n");
                    continue;
                }

                Usuario usuario = userService.buscar(nome);
                System.out.println("Login OK!\n");

                // MENU ADMIN
                if (usuario instanceof Administrador) {

                    boolean adminLoop = true;

                    while (adminLoop) {

                        System.out.println("""
                            [1] Cadastrar área
                            [2] Cadastrar drone
                            [3] Agendar missão
                            [4] Sair
                        """);

                        int esc = sc.nextInt();

                        switch (esc) {

                            case 1 -> {
                                System.out.print("Tamanho: ");
                                double t = sc.nextDouble();
                                System.out.print("Localização: ");
                                String loc = sc.next();
                                System.out.print("Cultivo: ");
                                String tipo = sc.next();

                                new CadastroArea().cadastrar(t, loc, tipo);
                                System.out.println("Área cadastrada.\n");
                            }

                            case 2 -> {
                                System.out.print("ID Drone: ");
                                int idD = sc.nextInt();
                                System.out.print("Qtd sensores: ");
                                int qtd = sc.nextInt();

                                List<Sensor> sensores = new ArrayList<>();

                                for (int i = 0; i < qtd; i++) {
                                    System.out.println("[1] Temp  [2] Umidade  [3] IR");
                                    int s = sc.nextInt();
                                    sensores.add(
                                            s == 1 ? new SensorTemperatura() :
                                            s == 2 ? new SensorUmidade() :
                                                     new SensorInfravermelho()
                                    );
                                }

                                System.out.print("Status: ");
                                String st = sc.next();

                                new Drone().cadastrarDrone(idD, sensores, st);
                                System.out.println("Drone cadastrado.\n");
                            }

                            case 3 -> System.out.println("Agendamento não implementado.\n");

                            case 4 -> adminLoop = false;

                            default -> System.out.println("Opção inválida.\n");
                        }
                    }
                }

                // MENU OPERADOR
                else if (usuario instanceof Operador operador) {

                    boolean opLoop = true;

                    while (opLoop) {

                        System.out.println("""
                            [1] Registrar dados
                            [2] Relatório básico
                            [3] Sair
                        """);

                        int esc = sc.nextInt();

                        switch (esc) {

                            case 1 -> {
                                System.out.println("Função não implementada.\n");
                                operador.operarDrone(1);
                            }

                            case 2 -> {
                                System.out.println("Função não implementada.\n");
                                operador.operarDrone(2);
                            }

                            case 3 -> opLoop = false;

                            default -> System.out.println("Opção inválida.\n");
                        }
                    }
                }
            }
        }

        sc.close();
    }
}
