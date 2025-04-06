import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginTela extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    public LoginTela() {
        setTitle("Login - Sistema de Cadastro");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        campoUsuario = new JTextField();
        campoSenha = new JPasswordField();

        painelCampos.add(new JLabel("Usuário:"));
        painelCampos.add(campoUsuario);
        painelCampos.add(new JLabel("Senha:"));
        painelCampos.add(campoSenha);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnCriarConta = new JButton("Criar Conta");

        JPanel botoes = new JPanel();
        botoes.add(btnEntrar);
        botoes.add(btnCriarConta);

        add(painelCampos, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnEntrar.addActionListener(e -> autenticar());
        btnCriarConta.addActionListener(e -> new CadastroUsuario());

        setVisible(true);
    }

    private void autenticar() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());

        try (Connection conn = Conexao.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "✅ Login bem-sucedido!");
                dispose(); // fecha a tela de login
                new MenuSwing(); // abre o menu principal
            } else {
                JOptionPane.showMessageDialog(this, "❌ Usuário ou senha inválidos.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + ex.getMessage());
        }
    }

    // Ponto de entrada principal
    public static void main(String[] args) {
        new LoginTela();
    }
}
