public class Soco extends Arma{
    public int atacar(Zumbi alvo){
        if(!(alvo instanceof ZumbiRastejante)){
            int resultado = RolarDados.rolarDados6Lados();
            if(resultado != 6){
                return 1;
            } else {
                return 2;
            }

        } else {
            return 0;
        }
    }
}
