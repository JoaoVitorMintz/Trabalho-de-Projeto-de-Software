package main;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ApplicationMain {

    static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    static final String USER = "app_user";
    static final String PASS = "app_pwd";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n[1] Cadastrar Usuário  [2] Login");
            int op = sc.nextInt();

            if (op == 1) cadastrarUsuario(sc);
            else if (op == 2) realizarLogin(sc);
        }
    }

    // ------------------- USUÁRIO -------------------------

    private static void cadastrarUsuario(Scanner sc) {
        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.print("Nome: "); String nome = sc.next();
            System.out.print("Senha: "); String senha = sc.next();
            System.out.print("Role [admin/operador]: "); String role = sc.next();

            String insertUser = "INSERT INTO Usuario (nome,setor) VALUES (?,?)";
            PreparedStatement ps = c.prepareStatement(insertUser, new String[]{"id"});
            ps.setString(1, nome);
            ps.setString(2, "Agricultura");
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys(); rs.next(); int id = rs.getInt(1);

            String auth = "INSERT INTO Autorizacao (usuario_id,senhaHash,role) VALUES (?,?,?)";
            PreparedStatement pa = c.prepareStatement(auth);
            pa.setInt(1, id);
            pa.setString(2, senha);
            pa.setString(3, role);
            pa.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private static void realizarLogin(Scanner sc) {
        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.print("Nome: "); String nome = sc.next();
            System.out.print("Senha: "); String senha = sc.next();

            String sql = "SELECT u.id, a.role FROM Usuario u JOIN Autorizacao a ON a.usuario_id=u.id WHERE nome=? AND senhaHash=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) { System.out.println("Login inválido."); return; }

            int id = rs.getInt("id");
            String role = rs.getString("role");

            System.out.println("Login OK! Role = " + role);

            if (role.equals("admin")) menuAdmin(sc, c);
            else menuOperador(sc, c, id);

        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ------------------- ADMIN -------------------------

    private static void menuAdmin(Scanner sc, Connection c) throws SQLException {
        while (true) {
            System.out.println("\n[1] Cadastrar Área  [2] Cadastrar Drone  [3] Agendar Missão  [4] Sair");
            int op = sc.nextInt();

            switch (op) {
                case 1 -> cadastrarArea(sc, c);
                case 2 -> cadastrarDrone(sc, c);
                case 3 -> agendarMissao(sc, c);
                case 4 -> { return; }
            }
        }
    }

    private static void cadastrarArea(Scanner sc, Connection c) throws SQLException {
        System.out.print("Tamanho: "); double t = sc.nextDouble();
        System.out.print("Localização: "); String loc = sc.next();
        System.out.print("Cultivo: "); String cul = sc.next();

        PreparedStatement ps = c.prepareStatement("INSERT INTO CadastroAreas (tamanho,localizacao,tipoCultivo) VALUES (?,?,?)");
        ps.setDouble(1, t); ps.setString(2, loc); ps.setString(3, cul);
        ps.executeUpdate();

        System.out.println("Área cadastrada!");
    }

    private static void cadastrarDrone(Scanner sc, Connection c) throws SQLException {
        System.out.print("Modelo: "); String modelo = sc.next();
        System.out.print("Status: "); String status = sc.next();

        PreparedStatement ps = c.prepareStatement("INSERT INTO Drone (status,modelo) VALUES (?,?)", new String[]{"id"});
        ps.setString(1, status); ps.setString(2, modelo);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys(); rs.next(); int droneId = rs.getInt(1);

        System.out.println("Quantos sensores? "); int qtd = sc.nextInt();
        for (int i = 0; i < qtd; i++) {
            System.out.println("1-Temp 2-Umidade 3-IR");
            int tipo = sc.nextInt();

            PreparedStatement s = c.prepareStatement("INSERT INTO Sensor (nome,drone_id) VALUES (?,?)", new String[]{"id"});
            String nome = switch (tipo) {
                case 1 -> "Temperatura";
                case 2 -> "Umidade";
                default -> "Infravermelho";
            };
            s.setString(1, nome); s.setInt(2, droneId);
            s.executeUpdate();
            s.getGeneratedKeys();
        }

        // Checklist inicial
        PreparedStatement chk = c.prepareStatement("INSERT INTO Checklist (bateria, sensoresFuncionais, drone_id) VALUES (?,?,?)");
        chk.setDouble(1, 100); chk.setInt(2, 1); chk.setInt(3, droneId);
        chk.executeUpdate();

        System.out.println("Drone cadastrado");
    }

    private static void agendarMissao(Scanner sc, Connection c) {
        try {
            System.out.print("Drone id: "); int droneId = sc.nextInt();
            System.out.print("Área id: "); int area = sc.nextInt();
            System.out.print("Data (AAAA-MM-DD): "); LocalDate data = LocalDate.parse(sc.next());

            // check checklist
            PreparedStatement chk = c.prepareStatement("SELECT bateria,sensoresFuncionais FROM Checklist WHERE drone_id=?");
            chk.setInt(1, droneId);
            ResultSet r = chk.executeQuery();
            if (!r.next() || r.getDouble(1) < 20 || r.getInt(2) == 0) {
                System.out.println("Checklist reprovado. Missão proibida.");
                return;
            }

            String sql = "INSERT INTO MissoesVoo (idMissao,data,drone_id,area_id) VALUES (?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, UUID.randomUUID().toString());
            ps.setDate(2, java.sql.Date.valueOf(data));
            ps.setInt(3, droneId);
            ps.setInt(4, area);
            ps.executeUpdate();

            System.out.println("Missão agendada!");

        } catch (SQLException e) {
            if (e.getMessage().contains("uq_missao_drone_data"))
                System.out.println("ERRO: Drone já possui missão nesta data!");
            else e.printStackTrace();
        }
    }

    // ------------------- OPERADOR -------------------------

    private static void menuOperador(Scanner sc, Connection c, int userId) throws SQLException {
        while (true) {
            System.out.println("\n[1] Registrar Dados  [2] Relatório  [3] Sair");
            int op = sc.nextInt();

            switch (op) {
                case 1 -> registrarDados(sc, c);
                case 2 -> gerarRelatorio(c);
                case 3 -> { return; }
            }
        }
    }

    private static void registrarDados(Scanner sc, Connection c) throws SQLException {
        System.out.print("ID Missão: "); int mid = sc.nextInt();

        PreparedStatement ps = c.prepareStatement("INSERT INTO RegistroDados (missao_id) VALUES (?)", new String[]{"id"});
        ps.setInt(1, mid);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys(); rs.next(); int rid = rs.getInt(1);

        // salvar relatório
        PreparedStatement rel = c.prepareStatement("INSERT INTO Relatorio (medicoes,qtd_voo,registro_id) VALUES (?,?,?)");
        rel.setDouble(1, Math.random()*50);
        rel.setInt(2, 1);
        rel.setInt(3, rid);
        rel.executeUpdate();

        System.out.println("Dados registrados!");
    }

    private static void gerarRelatorio(Connection c) throws SQLException {
        Statement st = c.createStatement();
        ResultSet r = st.executeQuery("SELECT * FROM Relatorio");

        System.out.println("=== RELATÓRIOS ===");
        while (r.next()) {
            System.out.println("ID:"+r.getInt("id")+" | Última medição:"+r.getDouble("medicoes"));
        }
    }
}
