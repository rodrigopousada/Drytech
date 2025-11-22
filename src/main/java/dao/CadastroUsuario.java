import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CadastroUsuario extends JFrame {
    private JTextField nomeField;
    private JTextField idadeField;
    private JComboBox<String> tipoBox;
    private JTextField usernameField;
    private JPasswordField senhaField;
    private JButton btnCadastrar;

    public CadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        JLabel lblTitulo = new JLabel("Cadastro de Usuário", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = y++;
        JLabel lblNome = new JLabel("Nome:");
        gbc.gridx = 0;
        panel.add(lblNome, gbc);
        nomeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        gbc.gridy = y++;
        JLabel lblIdade = new JLabel("Idade:");
        gbc.gridx = 0;
        panel.add(lblIdade, gbc);
        idadeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(idadeField, gbc);

        gbc.gridy = y++;
        JLabel lblTipo = new JLabel("Tipo:");
        gbc.gridx = 0;
        panel.add(lblTipo, gbc);
        tipoBox = new JComboBox<>(new String[]{"COMUM", "ADMIN"});
        gbc.gridx = 1;
        panel.add(tipoBox, gbc);

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
        gbc.gridx = 0; gbc.gridwidth = 2;
        btnCadastrar = new JButton("Cadastrar");
        panel.add(btnCadastrar, gbc);

        add(panel);

        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText().trim();
                String idadeStr = idadeField.getText().trim();
                String tipo = (String) tipoBox.getSelectedItem();
                String username = usernameField.getText().trim();
                String senha = new String(senhaField.getPassword()).trim();

                if (nome.isEmpty() || idadeStr.isEmpty() || tipo == null || username.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return;
                }
                int idade;
                try {
                    idade = Integer.parseInt(idadeStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Idade inválida!");
                    return;
                }

                String url = "jdbc:mysql://localhost:3306/sistema_curadoria"; // troque se necessário
                String usuario = "root"; // troque se necessário
                String password = "07132406"; // coloque sua senha do mysql

                try {
                    Connection conn = DriverManager.getConnection(url, usuario, password);
                    String sql = "INSERT INTO usuarios (nome, idade, tipo, username, password_hash, ativo) VALUES (?, ?, ?, ?, ?, TRUE)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nome);
                    stmt.setInt(2, idade);
                    stmt.setString(3, tipo);
                    stmt.setString(4, username);
                    stmt.setString(5, senha); // pode colocar hash da senha aqui futuramente

                    int res = stmt.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        nomeField.setText(""); idadeField.setText(""); usernameField.setText("");
                        senhaField.setText(""); tipoBox.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar!");
                    }
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
        SwingUtilities.invokeLater(() -> new CadastroUsuario().setVisible(true));
    }
}