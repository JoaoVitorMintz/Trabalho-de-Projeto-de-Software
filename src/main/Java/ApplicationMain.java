package main.Java;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import main.Java.model.*;
import main.Java.model.subClass.Administrador;
import main.Java.model.subClass.Operador;
import main.Java.model.subClass.SensorInfravermelho;
import main.Java.model.subClass.SensorTemperatura;
import main.Java.model.subClass.SensorUmidade;
import main.Java.service.UsuarioService;
import main.Java.service.impl.UsuarioServiceImpl;

public class ApplicationMain {

    // --- Estruturas internas para gerenciar estado em memória ---
    private static final List<AreaRecord> areas = new ArrayList<>();
    private static final List<DroneRecord> drones = new ArrayList<>();
    private static final List<MissionRecord> missions = new ArrayList<>();
    private static final List<DataRecord> dataRecords = new ArrayList<>();

    // Small helpers / records
    private static class AreaRecord {
        final int id;
        final double tamanho;
        final String localizacao;
        final String tipoCultivo;
        final CadastroArea areaObj; // referência à sua classe se existir

        AreaRecord(int id, double tamanho, String localizacao, String tipoCultivo, CadastroArea areaObj) {
            this.id = id;
            this.tamanho = tamanho;
            this.localizacao = localizacao;
            this.tipoCultivo = tipoCultivo;
            this.areaObj = areaObj;
        }
    }

    private static class DroneRecord {
        final int id;
        final List<Sensor> sensores;
        String status;

        DroneRecord(int id, List<Sensor> sensores, String status) {
            this.id = id;
            this.sensores = sensores;
            this.status = status;
        }
    }

    private static class MissionRecord {
        final String idMissao;
        final LocalDate data;
        final int duracaoHoras;
        final int droneId;
        final int areaId;

        MissionRecord(String idMissao, LocalDate data, int duracaoHoras, int droneId, int areaId) {
            this.idMissao = idMissao;
            this.data = data;
            this.duracaoHoras = duracaoHoras;
            this.droneId = droneId;
            this.areaId = areaId;
        }
    }

    private static class DataRecord {
        final String missionId;
        final LocalDateTime timestamp;
        final Map<String, Double> sensorValues; // chave: tipo do sensor ou nome, valor: leitura

        DataRecord(String missionId, LocalDateTime timestamp, Map<String, Double> sensorValues) {
            this.missionId = missionId;
            this.timestamp = timestamp;
            this.sensorValues = sensorValues;
        }
    }

    // --- Main ---
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UsuarioService userService = new UsuarioServiceImpl(); // seu serviço de usuário
        CadastroArea area = new CadastroArea();
        Drone drone = new Drone();

        boolean continua = true;
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (continua) {

            System.out.println("""
                \nEscolha o que deseja fazer:
                [1] - Cadastrar-se
                [2] - Logar
                [0] - Sair
                """);

            int opcao = safeNextInt(sc);

            if (opcao == 0) {
                System.out.println("Encerrando aplicação...");
                break;
            }

            if (opcao == 1) {
                System.out.print("Nome: ");
                String nome = safeNextLine(sc);

                System.out.print("Email: ");
                String email = safeNextLine(sc);

                System.out.print("Senha: ");
                String senha = safeNextLine(sc);

                int id = new Random().nextInt(9999);

                System.out.println("Cargo [1] Operador | [2] Administrador");
                int cargo = safeNextInt(sc);

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
                String nome = safeNextLine(sc);

                System.out.print("Senha: ");
                String senha = safeNextLine(sc);

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
                            \nAdmin - Escolha o que deseja fazer:
                            [1] - Cadastrar área agrícola
                            [2] - Cadastrar drone
                            [3] - Agendar missão de voo
                            [4] - Listar missões
                            [5] - Listar drones e áreas
                            [0] - Voltar
                        """);

                        int escolha = safeNextInt(sc);

                        switch (escolha) {

                            case 1 -> {
                                System.out.print("Tamanho (KM²): ");
                                double tam = safeNextDouble(sc);

                                System.out.print("Localização: ");
                                String localizacao = safeNextLine(sc);

                                System.out.print("Tipo de cultivo: ");
                                String cultivo = safeNextLine(sc);

                                // chama seu método (se existir) para lógica interna
                                try {
                                    area.cadastrar(tam, localizacao, cultivo);
                                } catch (Exception ignored) {
                                }

                                int newId = areas.size() + 1;
                                AreaRecord ar = new AreaRecord(newId, tam, localizacao, cultivo, area);
                                areas.add(ar);

                                System.out.println("Área cadastrada! ID = " + newId);
                            }

                            case 2 -> {
                                System.out.print("ID do drone (numérico): ");
                                int idDrone = safeNextInt(sc);

                                System.out.print("Quantidade de sensores: ");
                                int qtd = safeNextInt(sc);

                                List<Sensor> sensores = new LinkedList<>();

                                for (int i = 0; i < qtd; i++) {
                                    System.out.println("""
                                        Escolha o sensor:
                                        [1] - Temperatura
                                        [2] - Umidade
                                        [3] - Infravermelho
                                    """);
                                    int s = safeNextInt(sc);

                                    if (s == 1) sensores.add(new SensorTemperatura());
                                    else if (s == 2) sensores.add(new SensorUmidade());
                                    else sensores.add(new SensorInfravermelho());
                                }

                                System.out.print("Status (ativo/inativo): ");
                                String status = safeNextLine(sc);

                                // chama seu método (se existir)
                                try {
                                    drone.cadastrarDrone(idDrone, sensores, status);
                                } catch (Exception ignored) {
                                }

                                DroneRecord dr = new DroneRecord(idDrone, sensores, status);
                                drones.add(dr);

                                System.out.println("Drone cadastrado! ID = " + idDrone);
                            }

                            case 3 -> {
                                System.out.print("ID da missão (string): ");
                                String idMissao = safeNextLine(sc);

                                System.out.print("Data da missão (yyyy-MM-dd): ");
                                String dataStr = safeNextLine(sc);
                                LocalDate data;
                                try {
                                    data = LocalDate.parse(dataStr, dateFmt);
                                } catch (Exception e) {
                                    System.out.println("Data inválida. Use o formato yyyy-MM-dd.");
                                    break;
                                }

                                System.out.print("Duração em horas (inteiro): ");
                                int duracao = safeNextInt(sc);

                                System.out.print("ID do drone a utilizar: ");
                                int droneId = safeNextInt(sc);

                                System.out.print("ID da área (número): ");
                                int areaId = safeNextInt(sc);

                                // validações básicas
                                Optional<DroneRecord> maybeDrone = drones.stream().filter(d -> d.id == droneId).findFirst();
                                Optional<AreaRecord> maybeArea = areas.stream().filter(a -> a.id == areaId).findFirst();

                                if (maybeDrone.isEmpty()) {
                                    System.out.println("Drone não encontrado.");
                                    break;
                                }
                                if (maybeArea.isEmpty()) {
                                    System.out.println("Área não encontrada.");
                                    break;
                                }

                                // checar sobreposição: para o mesmo drone, não aceitar duas missões na mesma data
                                boolean conflito = missions.stream()
                                        .anyMatch(m -> m.droneId == droneId && m.data.equals(data));

                                if (conflito) {
                                    System.out.println("Erro: o drone já possui missão agendada nessa data.");
                                    break;
                                }

                                MissionRecord mr = new MissionRecord(idMissao, data, duracao, droneId, areaId);
                                missions.add(mr);
                                System.out.println("Missão agendada com sucesso: " + idMissao);
                            }

                            case 4 -> {
                                if (missions.isEmpty()) {
                                    System.out.println("Nenhuma missão agendada.");
                                } else {
                                    System.out.println("\nMissões agendadas:");
                                    for (MissionRecord m : missions) {
                                        System.out.println("ID: " + m.idMissao + " | Data: " + m.data + " | Drone: " + m.droneId + " | Area: " + m.areaId);
                                    }
                                }
                            }

                            case 5 -> {
                                System.out.println("\nÁreas cadastradas:");
                                for (AreaRecord a : areas) {
                                    System.out.println("ID: " + a.id + " | Tam: " + a.tamanho + " | Local: " + a.localizacao + " | Tipo: " + a.tipoCultivo);
                                }
                                System.out.println("\nDrones cadastrados:");
                                for (DroneRecord d : drones) {
                                    System.out.println("ID: " + d.id + " | status: " + d.status + " | sensores: " + d.sensores.size());
                                }
                            }

                            case 0 -> menuAdmin = false;
                            default -> System.out.println("Opção inválida!");
                        }
                    }
                } else if (usuario instanceof Operador) {
                    boolean menuOp = true;

                    while (menuOp) {

                        System.out.println("""
                            \nOperador - Escolha o que deseja fazer:
                            [1] - Registrar dados coletados
                            [2] - Relatório básico
                            [0] - Voltar
                        """);

                        int escolha = safeNextInt(sc);

                        switch (escolha) {

                            case 1 -> {
                                if (missions.isEmpty()) {
                                    System.out.println("Nenhuma missão disponível para registrar dados.");
                                    break;
                                }
                                System.out.println("Escolha a missão para registrar dados (informe o ID):");
                                for (MissionRecord m : missions) {
                                    System.out.println(" - " + m.idMissao + " (Data: " + m.data + ", Drone: " + m.droneId + ")");
                                }
                                String idMissao = safeNextLine(sc);
                                Optional<MissionRecord> maybeMission = missions.stream().filter(m -> m.idMissao.equals(idMissao)).findFirst();
                                if (maybeMission.isEmpty()) {
                                    System.out.println("Missão não encontrada.");
                                    break;
                                }

                                // Simula leituras dos sensores do drone
                                MissionRecord mission = maybeMission.get();
                                Optional<DroneRecord> drOpt = drones.stream().filter(d -> d.id == mission.droneId).findFirst();
                                if (drOpt.isEmpty()) {
                                    System.out.println("Drone da missão não encontrado.");
                                    break;
                                }
                                DroneRecord drUsed = drOpt.get();

                                Map<String, Double> leituras = new HashMap<>();
                                Random rnd = new Random();
                                for (Sensor s : drUsed.sensores) {
                                    String tipo = (s instanceof SensorTemperatura) ? "temperatura" :
                                                  (s instanceof SensorUmidade) ? "umidade" :
                                                  (s instanceof SensorInfravermelho) ? "infravermelho" : "sensor";
                                    double valor;
                                    if (tipo.equals("temperatura")) valor = -5 + rnd.nextDouble() * 45; // -5..40
                                    else if (tipo.equals("umidade")) valor = rnd.nextDouble() * 100; // 0..100
                                    else valor = (rnd.nextBoolean()) ? 1.0 : 0.0; // infravermelho ativo/inativo
                                    leituras.put(tipo + "_" + UUID.randomUUID().toString().substring(0,4), valor);
                                }

                                DataRecord rec = new DataRecord(idMissao, LocalDateTime.now(), leituras);
                                dataRecords.add(rec);

                                System.out.println("Dados registrados com sucesso para a missão " + idMissao + " às " + rec.timestamp);
                            }

                            case 2 -> {
                                // relatório básico:
                                System.out.println("\n===== RELATÓRIO BÁSICO =====");
                                System.out.println("Total de áreas cadastradas: " + areas.size());
                                System.out.println("Total de drones cadastrados: " + drones.size());
                                System.out.println("Total de missões agendadas: " + missions.size());
                                System.out.println("Total de registros coletados: " + dataRecords.size());

                                if (!dataRecords.isEmpty()) {
                                    System.out.println("\nÚltimos registros (até 5):");
                                    int start = Math.max(0, dataRecords.size() - 5);
                                    for (int i = start; i < dataRecords.size(); i++) {
                                        DataRecord r = dataRecords.get(i);
                                        System.out.println("Missão: " + r.missionId + " | Horário: " + r.timestamp);
                                        r.sensorValues.forEach((k, v) -> System.out.printf("   %s = %.2f%n", k, v));
                                    }
                                }
                                System.out.println("============================\n");
                            }

                            case 0 -> menuOp = false;
                            default -> System.out.println("Opção inválida!");
                        }
                    }
                }
            }
        }
        sc.close();
    }

    // --- auxiliares para leitura robusta ---
    private static int safeNextInt(Scanner sc) {
        while (true) {
            try {
                String line = sc.nextLine();
                if (line.isBlank()) continue;
                return Integer.parseInt(line.trim());
            } catch (Exception e) {
                System.out.print("Entrada inválida. Tente novamente: ");
            }
        }
    }

    private static double safeNextDouble(Scanner sc) {
        while (true) {
            try {
                String line = sc.nextLine();
                if (line.isBlank()) continue;
                return Double.parseDouble(line.trim());
            } catch (Exception e) {
                System.out.print("Entrada inválida. Tente novamente: ");
            }
        }
    }

    private static String safeNextLine(Scanner sc) {
        String line = sc.nextLine();
        while (line == null) {
            line = sc.nextLine();
        }
        return line.trim();
    }
}
