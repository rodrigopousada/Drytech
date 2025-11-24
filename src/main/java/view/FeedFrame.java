package view;

import dao.RecursoDAO;
import model.Recurso;
import model.SessaoUsuario;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FeedFrame extends JFrame {

    private JPanel panelConteudoFeed;

    // Campos de texto precisam ser acessíveis para limparmos depois de postar
    private JTextField txtAutor;
    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JTextField txtUrl;

    public FeedFrame() {
        // --- 1. CONFIGURAÇÕES DA JANELA ---
        setTitle("Sistema Curadoria - Feed");
        setSize(850, 700); // Aumentei um pouco a altura
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        java.net.URL urlIcone = getClass().getResource("/imagens/logo.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(urlIcone);
        setIconImage(iconeTitulo);

        // --- 2. CABEÇALHO (Boas Vindas) ---
        Usuario usuarioLogado = SessaoUsuario.getUsuarioLogado();
        JLabel lblBemVindo = new JLabel();
        if (usuarioLogado != null) {
            lblBemVindo.setText("Olá, " + usuarioLogado.getUsername() + "! (" + usuarioLogado.getTipo() + ")");
        } else {
            lblBemVindo.setText("Modo Visitante");
        }
        lblBemVindo.setBounds(20, 10, 500, 20);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 14));
        lblBemVindo.setForeground(Color.DARK_GRAY);
        add(lblBemVindo);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(690, 7, 100, 35);
        btnAtualizar.setBackground(new Color(70, 130, 180)); // Azul SteelBlue
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 12));
        add(btnAtualizar);

        // --- 3. ÁREA DE CRIAR NOVO POST (Topo) ---
        JPanel panelCriar = montarPainelCriacao(usuarioLogado);
        add(panelCriar);

        // --- 4. ÁREA DE LISTAGEM (Feed) ---
        // O feed começa logo abaixo da área de criação (y = 240)
        panelConteudoFeed = new JPanel();
        panelConteudoFeed.setLayout(new BoxLayout(panelConteudoFeed, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(panelConteudoFeed);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // Ajustei a posição para não ficar em cima do formulário
        scrollPane.setBounds(20, 240, 790, 400);

        add(scrollPane);

        // Carrega os dados iniciais
        carregarPosts();
    }

    /**
     * Monta o painel bonitinho onde o usuário digita os dados
     */
    private JPanel montarPainelCriacao(Usuario usuario) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 40, 790, 180); // Fica no topo
        panel.setBorder(BorderFactory.createTitledBorder("Compartilhar Conhecimento"));
        panel.setBackground(new Color(245, 245, 245)); // Um cinza bem clarinho

        // Campo Título
        JLabel lblTit = new JLabel("Título:");
        lblTit.setBounds(15, 25, 50, 20);
        panel.add(lblTit);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(70, 25, 300, 25);
        panel.add(txtTitulo);

        // Campo Link (URL)
        JLabel lblUrl = new JLabel("Link:");
        lblUrl.setBounds(390, 25, 40, 20);
        panel.add(lblUrl);

        txtUrl = new JTextField();
        txtUrl.setBounds(430, 25, 340, 25);
        panel.add(txtUrl);

        //  Campo Autor
        /*JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(390, 40, 40, 20);
        panel.add(lblAutor);

        txtAutor = new JTextField();
        txtAutor.setBounds(430, 40, 340, 25);
        panel.add(txtAutor);*/

        // Campo Descrição
        JLabel lblDesc = new JLabel("Resumo:");
        lblDesc.setBounds(15, 60, 60, 20);
        panel.add(lblDesc);

        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        scrollDesc.setBounds(70, 63, 700, 60);
        panel.add(scrollDesc);

        // Botão Postar
        JButton btnPostar = new JButton("Publicar Recurso");
        btnPostar.setBounds(620, 135, 150, 35);
        btnPostar.setBackground(new Color(70, 130, 180)); // Azul SteelBlue
        btnPostar.setForeground(Color.WHITE);
        btnPostar.setFont(new Font("Arial", Font.BOLD, 12));

        // AÇÃO DO BOTÃO
        btnPostar.addActionListener(e -> acaoSalvarPost(usuario));

        panel.add(btnPostar);

        return panel;
    }

    /**
     * Lógica separada para salvar o post
     */
    private void acaoSalvarPost(Usuario usuario) {
        // 1. Validação básica
        if (txtTitulo.getText().trim().isEmpty() || txtUrl.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha pelo menos Título e Link!");
            return;
        }

        try {
            // 2. Monta o Objeto (DTO/Model)
            Recurso novoRecurso = new Recurso();
            novoRecurso.setTitulo(txtTitulo.getText());
            novoRecurso.setDescricao(txtDescricao.getText());
            novoRecurso.setUrl(txtUrl.getText());
            //novoRecurso.setCategoriaId("1");

            // Dados automáticos da sessão
            if (usuario != null) {
                novoRecurso.setUsuarioId(usuario.getId());
                novoRecurso.setAutor(usuario.getUsername());
            }else{
                JOptionPane.showMessageDialog(this, "Você precisa estar logado para postar!");
            }

            // 3. Manda pro Banco
            RecursoDAO dao = new RecursoDAO();
            dao.inserir(novoRecurso);


            JOptionPane.showMessageDialog(this, "Recurso postado com sucesso!");

            // Limpa os campos
            txtTitulo.setText("");
            txtDescricao.setText("");
            txtUrl.setText("");

            // 5. atualiza o feed
            carregarPosts();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao postar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void atualizarPainel() {
        panelConteudoFeed.removeAll();
        carregarPosts();
    }

    private void carregarPosts() {
        // Limpa o painel antes de recarregar pra não duplicar
        panelConteudoFeed.removeAll();

        RecursoDAO dao = new RecursoDAO();
        List<Recurso> posts = dao.listarTodos();

        // Se não tiver posts, avisa
        if (posts.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhum post encontrado. Seja o primeiro!");
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelConteudoFeed.add(Box.createVerticalStrut(20));
            panelConteudoFeed.add(lblVazio);
        }

        for (Recurso recurso : posts) {
            // Criação do Card (igual ao seu código, só ajustei visuais)
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1), // Borda cinza fina
                    BorderFactory.createEmptyBorder(10, 15, 10, 15) // Margem interna
            ));
            card.setBackground(Color.WHITE);
            // Tamanho máximo fixo na altura (120px), largura infinita
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

            // Título clicável ou destacado
            JLabel lblTituloCard = new JLabel(recurso.getTitulo());
            lblTituloCard.setFont(new Font("Arial", Font.BOLD, 14));
            lblTituloCard.setForeground(new Color(33, 37, 41));

            // Descrição
            JTextArea txtDesc = new JTextArea(recurso.getDescricao());
            txtDesc.setWrapStyleWord(true);
            txtDesc.setLineWrap(true);
            txtDesc.setEditable(false);
            txtDesc.setBackground(Color.WHITE);
            txtDesc.setForeground(Color.GRAY);
            txtDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));

            // Rodapé do card (Autor + Link)
            JPanel panelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            panelRodape.setBackground(Color.WHITE);

            JLabel lblAutor = new JLabel("Por: " + recurso.getAutor() + "  |  ");
            lblAutor.setFont(new Font("Arial", Font.ITALIC, 11));

            JLabel lblLink = new JLabel(recurso.getUrl());
            lblLink.setForeground(Color.BLUE);
            lblLink.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Mãozinha ao passar o mouse

            panelRodape.add(lblAutor);
            panelRodape.add(lblLink);

            // Monta o card
            card.add(lblTituloCard);
            card.add(Box.createVerticalStrut(5));
            card.add(txtDesc);
            card.add(Box.createVerticalGlue()); // Empurra o rodapé pra baixo
            card.add(panelRodape);

            // Adiciona no Feed
            panelConteudoFeed.add(card);
            panelConteudoFeed.add(Box.createVerticalStrut(15)); // Espaço entre cards
        }

        // Atualiza a tela
        panelConteudoFeed.revalidate();
        panelConteudoFeed.repaint();
    }

    // Main para teste rápido
    public static void main(String[] args) {
        // Simula um login para teste visual
        // SessaoUsuario.setUsuarioLogado(new Usuario(1, "RodrigoDev", "admin@teste.com", "123"));
        new FeedFrame().setVisible(true);
    }
}