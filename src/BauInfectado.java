public class BauInfectado extends Bau {
    private ZumbiRastejante zumbiEscondido;

    public BauInfectado(int conteudo, int x, int y) {
        super(conteudo);
        this.zumbiEscondido = new ZumbiRastejante(1, x, y);
    }

    public ZumbiRastejante getZumbiEscondido() {
        return zumbiEscondido;
    }
}