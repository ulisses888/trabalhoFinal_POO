public class Taco extends Arma{
    public int atacar(Zumbi alvo){

        int resultado = RolarDados.rolarDados6Lados();
        if (resultado != 6) {
            return 1;
        } else {
            return 3;
        }
    }
}
