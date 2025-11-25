package main.Java;

import java.util.Scanner;
import main.Java.model.*;
import main.Java.model.subClass.Administrador;
import main.Java.model.subClass.Operador;
import main.Java.service.*;
import main.Java.service.impl.UsuarioServiceImpl;

public class ApplicationMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UsuarioService userService = new UsuarioServiceImpl(); // CORRETO
        RegistroDados reg = new RegistroDados();
        Relatorio rel = new Relatorio();
        CadastroArea area = new CadastroArea();
        Drone drone = new Drone();

        boolean continua = true;

        while (continua) {

            System.out.println("""
                Escolha o que deseja fazer:
                [1] - Cadastrar-se
                [2] - Logar
            """);
            System.out.print("-> ");
            int opcao = sc.nextInt();

            if (opcao == 1) {

                System.out.print("Nome: ");
                String nome = sc.next();

                System.out.print("Email: ");
                String email = sc.next();

                System.out.print("Senha: ");
                String senha = sc.next();

                System.out.println("""
                        Cargo:
                        [1] - Operador
                        [2] - Administrador
                        """);
                System.out.print("-> ");
                int cargo = sc.nextInt();

                Usuario novo;

                if (cargo == 1) {
                    novo = new Operador(nome, 0, email);
                } else {
                    novo = new Administrador(nome, 0, email);
                }

                userService.cadastrar(novo, senha);
                System.out.println("Usuário cadastrado com sucesso!\n");
                continue;

            } else if (opcao == 2) {

                System.out.print("Nome (ou email): ");
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

                if (usuario instanceof Administrador admin) {
                    admin.gerenciarSistema(area, drone, sc); 
                } else if (usuario instanceof Operador op) {
                    op.executarOperacoes(reg, rel);
                }
                
            }
        }
        sc.close();
    }
}
