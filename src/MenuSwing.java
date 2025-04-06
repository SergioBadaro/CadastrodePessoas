import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MenuSwing extends JFrame {

    private JComboBox<String> comboProfissao, comboCidade;
    private JTextArea resultadoArea;

    public MenuSwing() {
        setTitle("Sistema de Cadastro de Pessoas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Cadastrar", painelCadastro());
        abas.addTab("Relatórios", painelRelatorio());

        getContentPane().add(abas, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel painelCadastro() {
        JPanel painel = new JPanel(new GridLayout(11, 2, 5, 5));

        JTextField nome = new JTextField();
        JTextField sobrenome = new JTextField();
        JTextField idade = new JTextField();
        JTextField telefone = new JTextField();
        JTextField email = new JTextField();
        JTextField genero = new JTextField();
        JTextField profissao = new JTextField();
        JTextField cidade = new JTextField();
        JTextField estado = new JTextField();

        JButton btnSalvar = new JButton("Salvar");

        painel.add(new JLabel("Nome:")); painel.add(nome);
        painel.add(new JLabel("Sobrenome:")); painel.add(sobrenome);
        painel.add(new JLabel("Idade:")); painel.add(idade);
        painel.add(new JLabel("Telefone:")); painel.add(telefone);
        painel.add(new JLabel("Email:")); painel.add(email);
        painel.add(new JLabel("Gênero:")); painel.add(genero);
        painel.add(new JLabel("Profissão:")); painel.add(profissao);
        painel.add(new JLabel("Cidade:")); painel.add(cidade);
        painel.add(new JLabel("Estado:")); painel.add(estado);
        painel.add(new JLabel("")); painel.add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try (Connection conn = Conexao.conectar()) {
                String sql = "INSERT INTO pessoas (nome, sobrenome, idade, telefone, email, genero, profissao, cidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome.getText());
                stmt.setString(2, sobrenome.getText());
                stmt.setInt(3, Integer.parseInt(idade.getText()));
                stmt.setString(4, telefone.getText());
                stmt.setString(5, email.getText());
                stmt.setString(6, genero.getText());
                stmt.setString(7, profissao.getText());
                stmt.setString(8, cidade.getText());
                stmt.setString(9, estado.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "✅ Pessoa cadastrada com sucesso!");

                nome.setText(""); sobrenome.setText(""); idade.setText("");
                telefone.setText(""); email.setText(""); genero.setText("");
                profissao.setText(""); cidade.setText(""); estado.setText("");

                carregarDropdowns();

            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        return painel;
    }

    private JPanel painelRelatorio() {
        JPanel painel = new JPanel(new BorderLayout());

        JPanel filtros = new JPanel(new GridLayout(1, 4, 5, 5));
        comboProfissao = new JComboBox<>();
        comboCidade = new JComboBox<>();

        carregarDropdowns();

        filtros.add(new JLabel("Profissão:")); filtros.add(comboProfissao);
        filtros.add(new JLabel("Cidade:")); filtros.add(comboCidade);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> aplicarFiltro());

        resultadoArea = new JTextArea(25, 70); // Aumentado
        resultadoArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultadoArea);

        JPanel botoesPainel = new JPanel();
        botoesPainel.add(btnFiltrar);

        JButton btnExportar = new JButton("Exportar para CSV");
        btnExportar.addActionListener(e -> exportarParaCSV());
        botoesPainel.add(btnExportar);

        painel.add(filtros, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(botoesPainel, BorderLayout.SOUTH);

        return painel;
    }

    private void carregarDropdowns() {
        comboProfissao.removeAllItems();
        comboCidade.removeAllItems();

        comboProfissao.addItem("Exibir Todos");
        comboCidade.addItem("Exibir Todos");

        try (Connection conn = Conexao.conectar()) {
            Statement stmt = conn.createStatement();

            ResultSet rs1 = stmt.executeQuery("SELECT DISTINCT profissao FROM pessoas ORDER BY profissao");
            while (rs1.next()) comboProfissao.addItem(rs1.getString("profissao"));

            ResultSet rs2 = stmt.executeQuery("SELECT DISTINCT cidade FROM pessoas ORDER BY cidade");
            while (rs2.next()) comboCidade.addItem(rs2.getString("cidade"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar filtros: " + ex.getMessage());
        }
    }

    private void aplicarFiltro() {
        String profissao = (String) comboProfissao.getSelectedItem();
        String cidade = (String) comboCidade.getSelectedItem();

        boolean todosProf = profissao.equals("Exibir Todos");
        boolean todosCid = cidade.equals("Exibir Todos");

        String sql = "SELECT * FROM pessoas";
        if (!todosProf && !todosCid) {
            sql += " WHERE profissao = ? AND cidade = ?";
        } else if (!todosProf) {
            sql += " WHERE profissao = ?";
        } else if (!todosCid) {
            sql += " WHERE cidade = ?";
        }

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (!todosProf && !todosCid) {
                stmt.setString(1, profissao);
                stmt.setString(2, cidade);
            } else if (!todosProf) {
                stmt.setString(1, profissao);
            } else if (!todosCid) {
                stmt.setString(1, cidade);
            }

            ResultSet rs = stmt.executeQuery();
            resultadoArea.setText("");

            while (rs.next()) {
                resultadoArea.append(
                        "ID: " + rs.getInt("id") +
                                " | Nome: " + rs.getString("nome") + " " + rs.getString("sobrenome") +
                                " | Idade: " + rs.getInt("idade") +
                                " | Telefone: " + rs.getString("telefone") +
                                " | Email: " + rs.getString("email") +
                                " | Gênero: " + rs.getString("genero") +
                                " | Profissão: " + rs.getString("profissao") +
                                " | Cidade: " + rs.getString("cidade") +
                                " | Estado: " + rs.getString("estado") + "\n"
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar: " + ex.getMessage());
        }
    }

    private void exportarParaCSV() {
        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Salvar arquivo como...");
        int userSelection = seletor.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String caminho = seletor.getSelectedFile().getAbsolutePath();
            if (!caminho.endsWith(".csv")) caminho += ".csv";

            try (Connection conn = Conexao.conectar();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM pessoas");
                 java.io.FileWriter writer = new java.io.FileWriter(caminho)) {

                writer.append("ID,Nome,Sobrenome,Idade,Telefone,Email,Gênero,Profissão,Cidade,Estado\n");

                while (rs.next()) {
                    writer.append(rs.getInt("id") + ",");
                    writer.append(rs.getString("nome") + ",");
                    writer.append(rs.getString("sobrenome") + ",");
                    writer.append(rs.getInt("idade") + ",");
                    writer.append(rs.getString("telefone") + ",");
                    writer.append(rs.getString("email") + ",");
                    writer.append(rs.getString("genero") + ",");
                    writer.append(rs.getString("profissao") + ",");
                    writer.append(rs.getString("cidade") + ",");
                    writer.append(rs.getString("estado") + "\n");
                }

                writer.flush();
                JOptionPane.showMessageDialog(this, "✅ Dados exportados com sucesso para:\n" + caminho);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuSwing());
    }
}