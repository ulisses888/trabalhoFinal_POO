public class Revolver extends Arma{
    private int municao;

    public Revolver(){
        this.municao = 0;
    }

    public int atacar(Zumbi alvo){
        if (this.municao > 0 && !(alvo instanceof ZumbiCorredor)) {
            this.municao--;
            return 2;
        } if ((alvo instanceof ZumbiCorredor)){
            this.municao--;
            return 0;
        }
        return 0;
    }

    public int getMunicao(){
        return municao;
    }
    public void adicionarMunicao(){
        municao++;
    }

}
