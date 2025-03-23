import javax.swing.*;

public class Icones {
    static ImageIcon desconhecido = new ImageIcon("sprites/jogo/desconhecido.png");
    static ImageIcon baufechado = new ImageIcon("sprites/jogo/baufechado.png");
    static ImageIcon jogador = new ImageIcon("sprites/jogo/jogador.png");
    static ImageIcon parede = new ImageIcon("sprites/jogo/parede.png");
    static ImageIcon vazio = new ImageIcon("sprites/jogo/vazio.png");
    static ImageIcon zumbiComum = new ImageIcon("sprites/jogo/zumbiComum.png");
    static ImageIcon zumbiCorredor = new ImageIcon("sprites/jogo/zumbiCorredor.png");
    static ImageIcon zumbiGigante = new ImageIcon("sprites/jogo/zumbiGigante.png");
    static ImageIcon zumbiRastejante = new ImageIcon("sprites/jogo/zumbiRastejante.png");

    public static ImageIcon retornaIcone(char nomeIcone) {
        switch (nomeIcone) {
            case '?':
                return desconhecido;
            case 'B':
                return baufechado;
            case 'J':
                return jogador;
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
