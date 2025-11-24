package view;
import dao.Conexao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        // 1. Configurações da Janela
        setTitle("Drytech - Início");
        setSize(400, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(null); // Layout manual pra ser fácil de entender
        java.net.URL urlIcone = getClass().getResource("/imagens/logo.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(urlIcone);
        setIconImage(iconeTitulo);

        java.net.URL urlLogo = getClass().getResource("/imagens/logo.png");
        if (urlLogo == null) {
            System.out.println("ERRO: Não achei a imagem do logo!");
        } else {
            ImageIcon imagemLogo = new ImageIcon(urlLogo);

            Image imgRedimensionada = imagemLogo.getImage().getScaledInstance(200, 182, Image.SCALE_SMOOTH);
            imagemLogo = new ImageIcon(imgRedimensionada);
            JLabel labelImagem = new JLabel(imagemLogo);
            labelImagem.setBounds(100, 70, 200, 150);
            add(labelImagem);
        }

        // 2. Título ou Logo
        JLabel lblTitulo = new JLabel("Drytech");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 30, 300, 30);
        add(lblTitulo);

        // 3. Botão de Login
        JButton btnLogin = new JButton("Já tenho uma conta");
        btnLogin.setBounds(80, 250, 240, 40);
        btnLogin.setBackground(new Color(42, 95, 150)); // Um azulzinho
        btnLogin.setForeground(Color.WHITE);

        btnLogin.addActionListener(e -> {
            // AQUI É O PULO DO GATO
            LoginUsuario telaLogin = new LoginUsuario();
            telaLogin.setVisible(true);
            dispose();
        });
        add(btnLogin);

        // 4. Botão de Cadastro
        JButton btnCadastro = new JButton("Criar nova conta");
        btnCadastro.setBounds(80, 305, 240, 40);

        btnCadastro.addActionListener(e -> {
            // ROTEAMENTO PARA CADASTRO
            CadastroUsuario telaCadastro = new CadastroUsuario();
            telaCadastro.setVisible(true);
            dispose();
        });
        add(btnCadastro);
    }


    public static void main(String[] args) {
        new TelaInicial().setVisible(true);
    }
}