import javax.swing.*;

public class Icones {
    static ImageIcon desconhecido = new ImageIcon("src/sprites/jogo/desconhecido.png");
    static ImageIcon baufechado = new ImageIcon("src/sprites/jogo/baufechado.png");
    static ImageIcon jogadorDesarmado = new ImageIcon("src/sprites/jogo/jogadorDesarmado.png");
    static ImageIcon jogadorTaco = new ImageIcon("src/sprites/jogo/jogadorTaco.png");
    static ImageIcon jogadorRevolver = new ImageIcon("src/sprites/jogo/jogadorRevolver.png");
    static ImageIcon parede = new ImageIcon("src/sprites/jogo/parede.png");
    static ImageIcon vazio = new ImageIcon("src/sprites/jogo/vazio.png");
    static ImageIcon zumbiComum = new ImageIcon("src/sprites/jogo/zumbiComum.png");
    static ImageIcon zumbiCorredor = new ImageIcon("src/sprites/jogo/zumbiCorredor.png");
    static ImageIcon zumbiGigante = new ImageIcon("src/sprites/jogo/zumbiGigante.png");
    static ImageIcon zumbiRastejante = new ImageIcon("src/sprites/jogo/zumbiRastejante.png");

    public static ImageIcon retornaIcone(char nomeIcone, Personagem jogador) {
        switch (nomeIcone) {
            case '?':
                return desconhecido;
            case 'B':
                return baufechado;
            case 'J':
                if(jogador.isTemRevolver() && jogador.getRevolver().getMunicao() > 0){
                    return jogadorRevolver;
                }
                if(jogador.isTemBastao()){
                    return jogadorTaco;
                }
                return jogadorDesarmado;

            case 'P':
                return parede;
            case 'V':
                return vazio;
            case 'Z':
                return zumbiComum;
            case 'C':
                return zumbiCorredor;
            case 'G':
                return zumbiGigante;
            case 'R':
                return zumbiRastejante;
            default:
                return null;
        }
    }
}
