import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Mapa {
    private Celula[][] grid;
    private int conteudoBau = 3;
    private String localArquivo;
    //1 - Revolver
    //2 - Taco
    //3 - Atadura

    public void carregarMapa(int altura, int largura, String localarquivo) {
        grid = new Celula[altura][largura];
        this.localArquivo = localarquivo;

        try (BufferedReader br = new BufferedReader(new FileReader(localarquivo))) {
            for (int i = 0; i < altura; i++) {
                String line = br.readLine();
                if (line != null) {
                    String[] tokens = line.split(" ");
                    for (int j = 0; j < largura; j++) {
                        Celula celula = new Celula();
                        char ch = tokens[j].charAt(0);
                        celula.setConteudo(ch);

                        switch (ch) {
                            case 'V':
                                celula.setOcupante(new Vazio());
                                break;
                            case 'P':
                                celula.setOcupante(new Parede());
                                break;
                            case 'B':
                                if(this.conteudoBau == 3){
                                    celula.setOcupante(new Bau(3));
                                    this.conteudoBau--;
                                    break;
                                }
                                if(this.conteudoBau == 2){
                                    celula.setOcupante(new Bau(2));
                                    this.conteudoBau--;
                                    break;
                                }
                                if(this.conteudoBau == 1){
                                celula.setOcupante(new Bau(1));
                                this.conteudoBau--;
                                break;
                                }
                                if(this.conteudoBau < 1){
                                    celula.setOcupante(new BauInfectado(1,j,i));
                                    this.conteudoBau--;
                                    break;
                                }
                                break;
                            case 'R':
                                celula.setOcupante(new ZumbiRastejante(1,j,i));
                                break;
                            case 'Z':
                                celula.setOcupante(new ZumbiComum(2,j,i));
                                break;
                            case 'G':
                                celula.setOcupante(new ZumbiGigante(3,j,i));
                                break;
                            case 'C':
                                celula.setOcupante(new ZumbiCorredor(2,j,i));
                                break;
                            default:
                                System.out.println("Caracter nao reconhecido no mapa");
                                break;
                        }

                        grid[i][j] = celula;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAltura() {
        return grid.length;
    }

    public int getLargura() {
        return grid[0].length;
    }

    public Celula getCelula(int r, int c) {
        return grid[r][c];
    }

    public void posicionarJogador(Personagem jogador) {
        Celula cell = grid[0][0];
        cell.setConteudo('J');
        cell.setOcupante(jogador);
    }

    public void moverJogador(Personagem jogador,int x, int y){
        Celula cell = grid[x][y];
        cell.setConteudo('J');
        cell.setOcupante(jogador);
    }

    public String getLocalArquivo() {
        return localArquivo;
    }
}