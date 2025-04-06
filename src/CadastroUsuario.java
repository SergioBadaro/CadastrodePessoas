import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CadastroUsuario extends JFrame {

    private JTextField campoNome, campoUsuario;
    private JPasswordField campoSenha;

    public CadastroUsuario() {
        setTitle("Criar Novo Usuário");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        campoNome = new JTextField();
        campoUsuario = new JTextField();
        campoSenha = new JPasswordField();

        add(new JLabel("Nome completo:"));
        add(campoNome);
        add(new JLabel("Usuário:"));
        add(campoUsuario);
        add(new JLabel("Senha:"));
        add(campoSenha);

        JButton btnCadastrar = new JButton("Cadastrar");
        add(new JLabel(""));
        add(btnCadastrar);

        btnCadastrar.addActionListener(e -> cadastrarUsuario());

        setVisible(true);
    }

    private void cadastrarUsuario() {
        String nome = campoNome.getText();
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());

        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        try (Connection conn = Conexao.conectar()) {
            String sql = "INSERT INTO usuarios (nome, usuario, senha) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, usuario);
            stmt.setString(3, senha);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Usuário cadastrado com sucesso!");
            dispose();

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Usuário já existe. Escolha outro.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }
}
