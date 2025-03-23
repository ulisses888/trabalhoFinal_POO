import javax.swing.*;

public class Icones {
    static ImageIcon desconhecido = new ImageIcon("sprites/jogo/desconhecido.png");
    static ImageIcon baufechado = new ImageIcon("sprites/jogo/baufechado.png");
    static ImageIcon jogadorDesarmado = new ImageIcon("sprites/jogo/jogadorDesarmado.png");
    static ImageIcon jogadorTaco = new ImageIcon("sprites/jogo/jogadorTaco.png");
    static ImageIcon jogadorRevolver = new ImageIcon("sprites/jogo/jogadorRevolver.png");
    static ImageIcon parede = new ImageIcon("sprites/jogo/parede.png");
    static ImageIcon vazio = new ImageIcon("sprites/jogo/vazio.png");
    static ImageIcon zumbiComum = new ImageIcon("sprites/jogo/zumbiComum.png");
    static ImageIcon zumbiCorredor = new ImageIcon("sprites/jogo/zumbiCorredor.png");
    static ImageIcon zumbiGigante = new ImageIcon("sprites/jogo/zumbiGigante.png");
    static ImageIcon zumbiRastejante = new ImageIcon("sprites/jogo/zumbiRastejante.png");

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
