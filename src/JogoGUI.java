import javax.swing.*;
import java.awt.*;

public class JogoGUI extends JFrame {
    private JPanel panelMapa;
    private JButton[][] botoesMapa = new JButton[10][10];
    private JLabel labelSaude, labelPercepcao, labelMunicao, labelAtaduras;
    private JButton botaoMover, botaoCurar, botaoSair;
    private Personagem jogador;
    private Mapa mapa;
    private boolean debugMode;


    public JogoGUI(Mapa mapa, Personagem jogador, boolean debugMode) {
        super("Java Zombies");
        this.mapa = mapa;
        this.jogador = jogador;
        this.debugMode = debugMode;

        mapa.posicionarJogador(jogador);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelStatus = new JPanel();
        labelSaude = new JLabel("Saúde: " + jogador.getSaude());
        labelPercepcao = new JLabel(" | Percepcao: " + jogador.getPercepcao());
        labelMunicao = new JLabel(" | Municao: " +jogador.getRevolver().getMunicao());
        labelAtaduras = new JLabel(" | Ataduras: " + jogador.getNumeroAtaduras());
        panelStatus.add(labelSaude);
        panelStatus.add(labelPercepcao);
        panelStatus.add(labelMunicao);
        panelStatus.add(labelAtaduras);
        add(panelStatus, BorderLayout.NORTH);

        panelMapa = new JPanel(new GridLayout(10, 10));
        inicializarBotoesMapa();
        add(panelMapa, BorderLayout.CENTER);

        JPanel panelAcoes = new JPanel();
        botaoMover = new JButton("Mover");
        botaoCurar = new JButton("Curar");
        botaoSair = new JButton("Sair");
        botaoCurar.setEnabled( jogador.temAtadura() );
        botaoMover.addActionListener(e -> habilitarMovimento());
        botaoCurar.addActionListener(e -> {
            if (jogador.curar()) {
                jogador.setSaude(jogador.getSaude());
                botaoCurar.setEnabled( jogador.temAtadura() );
                atualizarStatus();
            }
        });
        botaoSair.addActionListener(e -> {
            dispose();
            // Exibir opções "Reiniciar Jogo" e "Novo Jogo" (implementar)
        });

        panelAcoes.add(botaoMover);
        panelAcoes.add(botaoCurar);
        panelAcoes.add(botaoSair);
        add(panelAcoes, BorderLayout.SOUTH);
        setSize(1366, 768);
        //pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarBotoesMapa() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton btn = new JButton();
                btn.setEnabled(false);

                // Configurações para remover bordas e fundo
//                btn.setBorderPainted(false);
//                btn.setContentAreaFilled(false);
//                btn.setFocusPainted(false);
//                btn.setMargin(new Insets(0, 0, 0, 0));
//                btn.setPreferredSize(new Dimension(50, 50));

                if (estaNaLinhaDeVisao(i, j) || debugMode) {
                    Object ocupante = mapa.getCelula(i, j).getOcupante();
                    if (ocupante instanceof ZumbiRastejante && !debugMode) {
                        btn.setText("V");
                    } else {
                        char conteudo = mapa.getCelula(i, j).getConteudo();
                        btn.setText(String.valueOf(conteudo));

                        ImageIcon icon = Icones.retornaIcone(conteudo);
                        if (icon != null) {
                            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            ImageIcon scaledIcon = new ImageIcon(img);
                            btn.setIcon(scaledIcon);
                            btn.setDisabledIcon(scaledIcon);
                        }
                    }
                } else {
                    btn.setText("?");
                    ImageIcon icon = Icones.retornaIcone('?');
                    if (icon != null) {
                        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(img);
                        btn.setIcon(scaledIcon);
                        btn.setDisabledIcon(scaledIcon);
                    }
                }

                botoesMapa[i][j] = btn;
                panelMapa.add(btn);
            }
        }
    }

    private boolean estaNaLinhaDeVisao(int linha, int coluna) {

        if (linha == jogador.getCordenadaY()) {
            int passo = coluna > jogador.getCordenadaX() ? 1 : -1;
            for (int x = jogador.getCordenadaX(); x != coluna; x += passo) {
                char c = mapa.getCelula(linha, x).getConteudo();
                if ((c != 'V') && (c != 'J')) return false;
            }
            return true;
        } else if (coluna == jogador.getCordenadaX()) {
            int passo = linha > jogador.getCordenadaY() ? 1 : -1;
            for (int y = jogador.getCordenadaY(); y != linha; y += passo) {
                char c = mapa.getCelula(y, coluna).getConteudo();
                if (c != 'V' && c != 'J') return false;
            }
            return true;
        }
        return false;
    }

    private void habilitarMovimento() {
        //int x = jogador.getCordenadaX();
        //int y = jogador.getCordenadaY();

        //if()


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton btn = botoesMapa[i][j];
                int dist = Math.abs(i - jogador.getCordenadaY())
                        + Math.abs(j - jogador.getCordenadaX());
                if (dist == 1 && podeMover(i, j)) {
                    btn.setEnabled(true);
                    final int r = i, c = j;
                    btn.addActionListener(e -> moverJogador(r, c));
                } else {
                    btn.setEnabled(false);
                }
            }
        }
    }

    private boolean podeMover(int linha, int coluna) {
        Object ocupante = mapa.getCelula(linha, coluna).getOcupante();
        if (ocupante instanceof Parede) {
            return false;
        }
        return true;
    }

    private void moverJogador(int linha, int coluna) {
        Celula newCell = mapa.getCelula(linha, coluna);
        Object conteudo = newCell.getOcupante();
        if(conteudo instanceof Zumbi){
            iniciarCombate((Zumbi) conteudo, newCell);
            return;
        }
        if(conteudo instanceof Bau){
            aberturaBau((Bau) conteudo, newCell);
        }

        int oldX = jogador.getCordenadaX();
        int oldY = jogador.getCordenadaY();


        Celula oldCell = mapa.getCelula(oldY, oldX);
        oldCell.setConteudo('V');
        oldCell.setOcupante(new Vazio());

        jogador.setCordenada(coluna, linha);


        newCell.setConteudo('J');
        newCell.setOcupante(jogador);

        atualizarStatus();
        panelMapa.removeAll();
        inicializarBotoesMapa();
        panelMapa.revalidate();
        panelMapa.repaint();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                botoesMapa[i][j].setEnabled(false);
            }
        }
    }

    private void atualizarStatus() {
        labelSaude.setText("Saúde: " + jogador.getSaude());
        labelPercepcao.setText(" | Percepção: " + jogador.getPercepcao());
    }
    // 1 - Revovler 2 - Taco 3 - Atadura
    public void aberturaBau(Bau bau, Celula cel) {
        int conteudo = bau.abrirBau();
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Você encontrou um Bau!"
        );
        switch (conteudo) {
            case 1:
                if(jogador.isTemRevolver()){
                    jogador.getRevolver().adicionarMunicao();
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Você encontrou mais uma municao, use com cautela!"
                    );
                } else {
                    jogador.adquirirRevolver();
                    jogador.getRevolver().adicionarMunicao();
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Você encontrou um revolver com uma munição faça bom uso!"
                    );
                }
                break;
            case 2:
                if(jogador.isTemBastao()){
                    System.out.println("Bau vazio");
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Esse bau estava vazio"
                    );
                } else {
                    jogador.adquirirTaco();
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Você encontrou um Taco!"
                    );
                }
                break;
            case 3:
                jogador.adquirirAtadura();
                botaoCurar.setEnabled( jogador.temAtadura() );
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Você encontrou uma atadura isso te permite curar!"
                );
                break;
            default:
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Esse bau está vazio"
                );
                System.out.println("Bau vazio");
                break;
        }
    }



    public void iniciarCombate(Zumbi zumbi, Celula cel) {
        while (zumbi.getEstado()) {
            java.util.List<String> opcoes = new java.util.ArrayList<>();

            if (jogador.isTemBastao()) {
                opcoes.add("Taco");
            } else {
                opcoes.add("Soco");
            }
            if (jogador.isTemRevolver()) {
                opcoes.add("Revolver");
            }
            opcoes.add("Fugir");

            String[] escolha = opcoes.toArray(new String[0]);
            int opcao = javax.swing.JOptionPane.showOptionDialog(
                    this,
                    "Escolha seu ataque:",
                    "Combate com " + zumbi.getTipo(),
                    javax.swing.JOptionPane.DEFAULT_OPTION,
                    javax.swing.JOptionPane.PLAIN_MESSAGE,
                    null,
                    escolha,
                    null
            );

            if (opcao < 0 || opcao >= escolha.length) {
                return;
            }

            int danoJogador = 0;
            String acao = escolha[opcao];

            // Chama metodo adequando de acordo com a arma
            switch (acao) {
                case "Soco":
                    danoJogador = jogador.getSoco().atacar(zumbi);
                    break;
                case "Taco":
                    danoJogador = jogador.getTaco().atacar(zumbi);
                    break;
                case "Revolver":
                    danoJogador = jogador.getRevolver().atacar(zumbi);
                    break;
                case "Fugir":
                    habilitarMovimento();
                    return;
                default:
                    break;
            }

            // Aplica dano no zumbi e exibe mensagem
            zumbi.atacado(danoJogador);
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Você causou " + danoJogador + " de dano ao zumbi!"
            );

            if(!zumbi.getEstado()){
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Você matou o Zumbi!"
                );

                cel.setConteudo('V');
                cel.setOcupante(new Vazio());
                atualizarStatus();
                panelMapa.removeAll();
                inicializarBotoesMapa();
                panelMapa.revalidate();
                panelMapa.repaint();

            } else {
                int testePercepcao = RolarDados.rolarDado3Lados();
                if (testePercepcao <= jogador.getPercepcao()) {
                    jogador.setSaude(jogador.getSaude() - 1);
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "O zumbi causou 1 de dano em você!"
                    );
                    atualizarStatus();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Você esquivou do golpe do zumbi!"
                    );
                }
            }
        }
    }
}