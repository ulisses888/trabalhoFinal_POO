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
    private int numeroZumbisVivos;


    public JogoGUI(Mapa mapa, Personagem jogador, boolean debugMode) {
        super("Java Zombies");
        this.mapa = mapa;
        this.jogador = jogador;
        this.debugMode = debugMode;
        this.numeroZumbisVivos = 10;

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
            if (jogador.temAtadura()) {
                jogador.curar();
                botaoCurar.setEnabled( jogador.temAtadura() );
                moverZumbis();
                atualizarStatus();
            }
        });
        botaoSair.addActionListener(e -> {
            dispose();
            java.util.List<String> opcoes = new java.util.ArrayList<>();
            opcoes.add("Sair");
            opcoes.add("Reiniciar");
            opcoes.add("Novo Jogo");
            String[] escolha = opcoes.toArray(new String[0]);
            int opcao = javax.swing.JOptionPane.showOptionDialog(
                    this,
                    "Oque deseja fazer?",
                    "Sair do Jogo",
                    javax.swing.JOptionPane.DEFAULT_OPTION,
                    javax.swing.JOptionPane.PLAIN_MESSAGE,
                    null,
                    escolha,
                    null
            );

            if (opcao < 0 || opcao >= escolha.length) {
                return;
            }


            String acao = escolha[opcao];

            switch (acao) {
                case "Sair":
                    JOptionPane.showMessageDialog(null, "Saindo do programa!");
                    System.exit(0);
                    break;
                case "Reiniciar":
                    dispose();
                    Mapa novoMapa = new Mapa();
                    novoMapa.carregarMapa(10,10,mapa.getLocalArquivo());
                    Personagem novoJogador = new Personagem(jogador.getPercepcao());
                    JogoGUI jogo = new JogoGUI(novoMapa, novoJogador,this.debugMode);
                    jogo.setVisible(true);
                    break;
                case "Novo Jogo":
                    dispose();
                    Mapa novoMapa1 = new Mapa();
                    novoMapa1.carregarMapa(10, 10, "tabuleiros/tabuleiro"+ RolarDados.rolarDado3Lados()+ ".txt");
                    Personagem novoJogador1 = new Personagem(jogador.getPercepcao());
                    JogoGUI jogo1 = new JogoGUI(novoMapa1, novoJogador1,this.debugMode);
                    jogo1.setVisible(true);
                    break;
                default:
                    break;
            }
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

    public void inicializarBotoesMapa() {
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

    public boolean estaNaLinhaDeVisao(int linha, int coluna) {

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

    public void habilitarMovimento() {
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

    public boolean podeMover(int linha, int coluna) {
        Object ocupante = mapa.getCelula(linha, coluna).getOcupante();
        if (ocupante instanceof Parede) {
            return false;
        }
        return true;
    }

    public void moverJogador(int linha, int coluna) {
        Celula newCell = mapa.getCelula(linha, coluna);
        Object conteudo = newCell.getOcupante();
        if(conteudo instanceof Zumbi){
            iniciarCombate((Zumbi) conteudo, newCell,0);
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
        moverZumbis();

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

    public void atualizarStatus() {
        labelSaude.setText("Saúde: " + jogador.getSaude());
        labelPercepcao.setText(" | Percepção: " + jogador.getPercepcao());
        labelMunicao.setText(" | Municao: " +jogador.getRevolver().getMunicao());
        labelAtaduras.setText(" | Ataduras:" + jogador.getNumeroAtaduras());
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
                    atualizarStatus();
                } else {
                    jogador.adquirirRevolver();
                    jogador.getRevolver().adicionarMunicao();
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Você encontrou um revolver com uma munição faça bom uso!"
                    );
                    atualizarStatus();
                }
                break;
            case 2:
                if(jogador.isTemBastao()){
                    System.out.println("Bau vazio");
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Esse bau estava vazio"
                    );
                    atualizarStatus();
                } else {
                    jogador.adquirirTaco();
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Você encontrou um Taco!"
                    );
                    atualizarStatus();
                }
                break;
            case 3:
                jogador.adquirirAtadura();
                botaoCurar.setEnabled( jogador.temAtadura() );
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Você encontrou uma atadura isso te permite curar!"
                );
                atualizarStatus();
                break;
            default:
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Esse bau está vazio"
                );
                System.out.println("Bau vazio");
                atualizarStatus();
                break;
        }
    }



    public void iniciarCombate(Zumbi zumbi, Celula cel, int surpresa) {
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
                    atualizarStatus();
                    break;
                case "Fugir":
                    if(surpresa == 1){
                        javax.swing.JOptionPane.showMessageDialog(
                                this,
                                "Você foi surprendido e não pode fugir"
                        );
                        break;
                    } else {
                        habilitarMovimento();
                        return;
                    }
                default:
                    break;
            }


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
                this.numeroZumbisVivos--;

                if(surpresa == 0) {
                    cel.setConteudo('V');
                    cel.setOcupante(new Vazio());

                }
                if(surpresa == 1){
                    cel.setConteudo('J');
                    cel.setOcupante(jogador);
                }

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

            } else {
                int testePercepcao = RolarDados.rolarDado3Lados();
                if (testePercepcao <= jogador.getPercepcao()) {
                    jogador.setSaude(jogador.getSaude() - 1);
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "O zumbi causou 1 de dano em você!"
                    );
                    atualizarStatus();

                    vitoriaOuDerrota();

                } else {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Você esquivou do golpe do zumbi!"
                    );
                }
            }
        }
    }

    public void moverZumbis() {
        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++){
                Object ocupante = mapa.getCelula(y,x).getOcupante();
                if(ocupante instanceof Zumbi && !(ocupante instanceof ZumbiGigante)){
                    Zumbi zumbi = (Zumbi) ocupante;
                    int nPassos = (zumbi instanceof ZumbiCorredor) ? 2 : 1;
                    int dx = Integer.compare(jogador.getCordenadaX(), x);
                    int dy = Integer.compare(jogador.getCordenadaY(), y);

                    for(int i = 0; i < nPassos; i++){
                        int novoX = x + dx;
                        int novoY = y + dy;


                    if(!limiteMapa(novoX,novoY) || mapa.getCelula(novoY, novoX).getOcupante() instanceof Zumbi
                            ||  mapa.getCelula(novoY, novoX).getOcupante() instanceof Bau
                            ||  mapa.getCelula(novoY, novoX).getOcupante() instanceof Parede){
                        break;
                    }

                    mapa.getCelula(y, x).setOcupante(new Vazio());
                    mapa.getCelula(y,x).setConteudo('V');
                    mapa.getCelula(novoY, novoX).setOcupante(zumbi);
                    mapa.getCelula(novoY,novoX).setConteudo(zumbi.getTipoChar());
                    y = novoY;
                    x = novoX;

                    if (jogador.getCordenadaX() == x && jogador.getCordenadaY() == y) {
                        iniciarCombatePrimeiroGolpeZumbi(zumbi);
                        break;
                    }

                }
            }
            }
        }
    }

    public boolean limiteMapa(int x, int y){
        return (x >= 0 && x < 10 && y >= 0 && y < 10);
    }


    public void vitoriaOuDerrota(){
        if(numeroZumbisVivos == 0){
            System.out.println("Jogador Venceu");
        }
        if(jogador.getSaude() == 0){
            System.out.println("Jogador Perdeu");
        }

    }

    public void iniciarCombatePrimeiroGolpeZumbi(Zumbi zumbi){
        int testePercepcao = RolarDados.rolarDado3Lados();
        if (testePercepcao <= jogador.getPercepcao()) {
            jogador.setSaude(jogador.getSaude() - 1);
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Voce foi atacado de surpresa e tomou 1 de dano!"
            );
            atualizarStatus();

            vitoriaOuDerrota();

        } else {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Você foi atacado de surpresa mas desviou do ataque!"
            );
    }
        if(jogador.getSaude() > 0){
        iniciarCombate(zumbi, mapa.getCelula(zumbi.getCordenadaX(), zumbi.getCordenadaY()),1);
        }

        if (!zumbi.getEstado()) {
            Celula cel = mapa.getCelula(jogador.getCordenadaY(), jogador.getCordenadaX());
            cel.setConteudo('J');
            cel.setOcupante(jogador);
            panelMapa.removeAll();
            inicializarBotoesMapa();
            panelMapa.revalidate();
            panelMapa.repaint();
        }
    }
}


