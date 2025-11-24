package view;

import dao.Conexao;
import dao.UsuarioDAO;
import model.SessaoUsuario;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginUsuario extends JFrame {
    private JTextField usernameField;
    private JPasswordField senhaField;
    private JButton btnLogin;


    public LoginUsuario() {
        setTitle("Login de Usuário");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        java.net.URL urlIcone = getClass().getResource("/imagens/logo.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(urlIcone);
        setIconImage(iconeTitulo);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        JLabel lblTitulo = new JLabel("Acessar Conta", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = y++;
        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        panel.add(lblUsername, gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridy = y++;
        JLabel lblSenha = new JLabel("Senha:");
        gbc.gridx = 0;
        panel.add(lblSenha, gbc);
        senhaField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(senhaField, gbc);

        gbc.gridy = y++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnLogin = new JButton("Entrar");
        panel.add(btnLogin, gbc);

        add(panel);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String senha = new String(senhaField.getPassword()).trim();

                if (username.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha username e senha!");
                    return;
                }


                try {
                    Connection conn = Conexao.getConnection();
                    String sql = "SELECT * FROM usuarios WHERE username = ? AND password_hash = ? AND ativo = TRUE";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, username);
                    stmt.setString(2, senha);

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
                        UsuarioDAO dao = new UsuarioDAO();
                        Usuario usuarioEncontrado = dao.buscarPorNome(username);
                        SessaoUsuario.setUsuarioLogado(usuarioEncontrado);
                        FeedFrame feed = new FeedFrame();
                        feed.setVisible(true);
                        LoginUsuario.this.dispose(); // Fecha o login

                        // Aqui você pode abrir o sistema principal ou tela inicial
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos (ou usuário inativo).");
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro SQL: " + ex.getMessage());
                }
            }
        });
    }


    public static void main(String[] args) {
        try (Connection conn = Conexao.getConnection()) {
            SwingUtilities.invokeLater(() -> new LoginUsuario().setVisible(true));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}