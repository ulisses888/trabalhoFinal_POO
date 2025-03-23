import javax.swing.*;
import java.awt.*;


public class Menu extends JFrame {

    private JPanel mainPanel;
    private JButton iniciarJogoButton, debugModeButton, sairDoJogoButton;

    public Menu() {
        super("Java Zombies");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        ImageIcon logoIcon = new ImageIcon("src/sprites/menu/ZombicideLogo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridy = 0;
        mainPanel.add(logoLabel, gbc);

        iniciarJogoButton = new JButton("Iniciar Jogo");
        debugModeButton = new JButton("DEBUG MODE");
        sairDoJogoButton = new JButton("Sair do Jogo");

        Dimension buttonSize = new Dimension(300, 50);
        iniciarJogoButton.setPreferredSize(buttonSize);
        debugModeButton.setPreferredSize(buttonSize);
        sairDoJogoButton.setPreferredSize(buttonSize);

        gbc.gridy = 1;
        mainPanel.add(iniciarJogoButton, gbc);

        gbc.gridy = 2;
        mainPanel.add(debugModeButton, gbc);

        gbc.gridy = 3;
        mainPanel.add(sairDoJogoButton, gbc);

        ImageIcon backgroundImage = new ImageIcon("src/sprites/menu/gifChuva.gif");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());
        backgroundLabel.add(mainPanel, gbc);

        setContentPane(backgroundLabel);
        setVisible(true);

        sairDoJogoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Saindo do programa!");
            System.exit(0);
        });

        iniciarJogoButton.addActionListener(e -> mostrarOpcoesDificuldade());

        debugModeButton.addActionListener(e -> {
            dispose();
            Mapa mapa = new Mapa();
            mapa.carregarMapa(10, 10, "src/tabuleiros/tabuleiro"+ RolarDados.rolarDado3Lados()+ ".txt");
            Personagem jogador = new Personagem(0);
            JogoGUI jogo = new JogoGUI(mapa, jogador, true);
            jogo.setVisible(true);
        });

        setContentPane(backgroundLabel);
        setVisible(true);
    }

    private void mostrarOpcoesDificuldade() {

        iniciarJogoButton.setVisible(false);
        debugModeButton.setVisible(false);
        sairDoJogoButton.setVisible(false);


        JButton facilButton = new JButton("Fácil");
        JButton medioButton = new JButton("Médio");
        JButton dificilButton = new JButton("Difícil");
        JButton voltarButton = new JButton("Voltar");

        Dimension buttonSize = new Dimension(300, 50);
        facilButton.setPreferredSize(buttonSize);
        medioButton.setPreferredSize(buttonSize);
        dificilButton.setPreferredSize(buttonSize);
        voltarButton.setPreferredSize(buttonSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 1;
        mainPanel.add(facilButton, gbc);
        gbc.gridy = 2;
        mainPanel.add(medioButton, gbc);
        gbc.gridy = 3;
        mainPanel.add(dificilButton, gbc);
        gbc.gridy = 4;
        mainPanel.add(voltarButton, gbc);
//mapa.carregarMapa(10, 10, "tabuleiros/tabuleiro"+ RolarDados.rolarDado3Lados()+ ".txt");
        facilButton.addActionListener(e -> {
            dispose();
            Mapa mapa = new Mapa();
            mapa.carregarMapa(10, 10, "src/tabuleiros/tabuleiro"+ RolarDados.rolarDado3Lados()+ ".txt");
            Personagem jogador = new Personagem(3);
            JogoGUI jogo = new JogoGUI(mapa, jogador, false);
            jogo.setVisible(true);
        });

        medioButton.addActionListener(e -> {
            dispose();
            Mapa mapa = new Mapa();
            mapa.carregarMapa(10, 10, "src/tabuleiros/tabuleiro"+ RolarDados.rolarDado3Lados()+ ".txt");
            Personagem jogador = new Personagem(2);
            JogoGUI jogo = new JogoGUI(mapa, jogador, false);
            jogo.setVisible(true);
        });

        dificilButton.addActionListener(e -> {
            dispose();
            Mapa mapa = new Mapa();
            mapa.carregarMapa(10, 10, "src/tabuleiros/tabuleiro"+ RolarDados.rolarDado3Lados()+ ".txt");
            Personagem jogador = new Personagem(1);
            JogoGUI jogo = new JogoGUI(mapa, jogador, false);
            jogo.setVisible(true);
        });


        voltarButton.addActionListener(e -> {
            mainPanel.removeAll();

            GridBagConstraints gbcBack = new GridBagConstraints();
            gbcBack.gridx = 0;
            gbcBack.fill = GridBagConstraints.HORIZONTAL;
            gbcBack.insets = new Insets(10, 0, 10, 0);

            gbcBack.gridy = 0;
            ImageIcon logoIcon = new ImageIcon("src/sprites/menu/ZombicideLogo.png");
            JLabel logoLabel = new JLabel(logoIcon);
            mainPanel.add(logoLabel, gbcBack);

            iniciarJogoButton.setVisible(true);
            debugModeButton.setVisible(true);
            sairDoJogoButton.setVisible(true);

            gbcBack.gridy = 1;
            mainPanel.add(iniciarJogoButton, gbcBack);

            gbcBack.gridy = 2;
            mainPanel.add(debugModeButton, gbcBack);

            gbcBack.gridy = 3;
            mainPanel.add(sairDoJogoButton, gbcBack);

            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }


}
