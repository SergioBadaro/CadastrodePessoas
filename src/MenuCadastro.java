import java.sql.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class MenuCadastro {

    static final String URL = "jdbc:mysql://localhost:3306/cadastro_db";
    static final String USUARIO = "root";
    static final String SENHA = "#1K2i3ara2025#";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU DE CADASTRO ---");
            System.out.println("1 - Cadastrar nova pessoa");
            System.out.println("2 - Consultar pessoas cadastradas");
            System.out.println("3 - Editar/alterar dados cadastrados");
            System.out.println("4 - Apagar pessoa cadastrada");
            System.out.println("5 - Extrair dados em planilha do banco de dados");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1 -> cadastrarPessoa(scanner);
                case 2 -> consultarPessoas();
                case 3 -> editarPessoa(scanner);
                case 4 -> apagarPessoa(scanner);
                case 5 -> exportarParaCSV();
                case 6 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 6);

        scanner.close();
    }

    public static void cadastrarPessoa(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();
        System.out.print("Profissão: ");
        String profissao = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        String sql = "INSERT INTO pessoas (nome, sobrenome, profissao, idade, telefone, email, genero, cidade, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);
            stmt.setString(3, profissao);
            stmt.setInt(4, idade);
            stmt.setString(5, telefone);
            stmt.setString(6, email);
            stmt.setString(7, genero);
            stmt.setString(8, cidade);
            stmt.setString(9, estado);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("✅ Pessoa cadastrada com sucesso! Linhas inseridas: " + linhasAfetadas);

        } catch (SQLException e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    public static void consultarPessoas() {
        String sql = "SELECT * FROM pessoas";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Pessoas Cadastradas ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Nome: " + rs.getString("nome") +
                        ", Sobrenome: " + rs.getString("sobrenome") +
                        ", Profissão: " + rs.getString("profissao") +
                        ", Idade: " + rs.getInt("idade") +
                        ", Telefone: " + rs.getString("telefone") +
                        ", Email: " + rs.getString("email") +
                        ", Gênero: " + rs.getString("genero") +
                        ", Cidade: " + rs.getString("cidade") +
                        ", Estado: " + rs.getString("estado"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao consultar: " + e.getMessage());
        }
    }

    public static void editarPessoa(Scanner scanner) {
        System.out.print("Digite o ID da pessoa que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite os novos dados:");
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo sobrenome: ");
        String sobrenome = scanner.nextLine();
        System.out.print("Nova profissão: ");
        String profissao = scanner.nextLine();
        System.out.print("Nova idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Novo email: ");
        String email = scanner.nextLine();
        System.out.print("Novo gênero: ");
        String genero = scanner.nextLine();
        System.out.print("Nova cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Novo estado: ");
        String estado = scanner.nextLine();

        String sql = "UPDATE pessoas SET nome = ?, sobrenome = ?, profissao = ?, idade = ?, telefone = ?, email = ?, genero = ?, cidade = ?, estado = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);
            stmt.setString(3, profissao);
            stmt.setInt(4, idade);
            stmt.setString(5, telefone);
            stmt.setString(6, email);
            stmt.setString(7, genero);
            stmt.setString(8, cidade);
            stmt.setString(9, estado);
            stmt.setInt(10, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Dados atualizados com sucesso!");
            } else {
                System.out.println("⚠️ Nenhuma pessoa encontrada com esse ID.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao editar: " + e.getMessage());
        }
    }

    public static void apagarPessoa(Scanner scanner) {
        System.out.print("Digite o ID da pessoa que deseja apagar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM pessoas WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("🗑️ Pessoa removida com sucesso!");
            } else {
                System.out.println("⚠️ Nenhuma pessoa encontrada com esse ID.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao apagar: " + e.getMessage());
        }
    }

    public static void exportarParaCSV() {
        String sql = "SELECT * FROM pessoas";
        String nomeArquivo = "pessoas_exportadas.csv";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter writer = new FileWriter(nomeArquivo)) {

            writer.append("ID;Nome;Sobrenome;Profissão;Idade;Telefone;Email;Gênero;Cidade;Estado\n");

            while (rs.next()) {
                writer.append(rs.getInt("id") + ";");
                writer.append(rs.getString("nome") + ";");
                writer.append(rs.getString("sobrenome") + ";");
                writer.append(rs.getString("profissao") + ";");
                writer.append(rs.getInt("idade") + ";");
                writer.append(rs.getString("telefone") + ";");
                writer.append(rs.getString("email") + ";");
                writer.append(rs.getString("genero") + ";");
                writer.append(rs.getString("cidade") + ";");
                writer.append(rs.getString("estado") + "\n");
            }

            writer.flush();
            System.out.println("✅ Dados exportados com sucesso para o arquivo: " + nomeArquivo);

        } catch (SQLException | IOException e) {
            System.out.println("❌ Erro ao exportar: " + e.getMessage());
        }
    }
}
