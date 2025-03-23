public class Celula {
    private Object ocupante;
    private char conteudo;

    public void setConteudo(char conteudo) {
        this.conteudo = conteudo;
    }

    public char getConteudo() {
        return conteudo;
    }

    public void setOcupante(Object ocupante) {
        this.ocupante = ocupante;
    }

    public Object getOcupante() {
        return ocupante;
    }
}