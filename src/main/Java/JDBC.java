package main.Java;
// package main.Java.main;
// package main.Java;
// import java.sql.*;

// // Apenas de exemplo, tive dificuldade de usar o OracleXE, travou meu PC.
// public class JDBC {
//     public static void main(String[] args) {
//         // Ajuste a URL para sua instância Oracle
//         String url = "jdbc:oracle:thin:@localhost:1521:XE";
//         String user = "app_user";   // seu usuário do Oracle
//         String pass = "app_pwd";    // sua senha do Oracle

//         try (Connection conn = DriverManager.getConnection(url, user, pass)) {
//             System.out.println("Conexão estabelecida com sucesso!");

//             // INSERIR uma área agrícola
//             String insertSql = "INSERT INTO CadastroAreas (tamanho, localizacao, tipoCultivo) VALUES (?, ?, ?)";
//             try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
//                 ps.setDouble(1, 200.50);                  // tamanho em hectares (exemplo)
//                 ps.setString(2, "Sítio São José");        // localização
//                 ps.setString(3, "Milho");                 // tipo de cultivo
//                 ps.executeUpdate();
//                 System.out.println("Área agrícola cadastrada com sucesso!");
//             }

//             // CONSULTAR áreas agrícolas
//             String selectSql = "SELECT id, tamanho, localizacao, tipoCultivo FROM CadastroAreas";
//             try (Statement stmt = conn.createStatement();
//                  ResultSet rs = stmt.executeQuery(selectSql)) {
//                 System.out.println("\n=== Áreas Cadastradas ===");
//                 while (rs.next()) {
//                     System.out.println(
//                             "ID: " + rs.getLong("id") +
//                                     " | Tamanho: " + rs.getDouble("tamanho") + " ha" +
//                                     " | Localização: " + rs.getString("localizacao") +
//                                     " | Tipo de Cultivo: " + rs.getString("tipoCultivo")
//                     );
//                 }
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }
// }

