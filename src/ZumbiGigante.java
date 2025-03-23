public class ZumbiGigante extends Zumbi{

    public ZumbiGigante(int vida,int x,int y){
        super(vida,x,y);
    }

    public String getTipo(){
        return "Zumbi Gigante";
    }

    public char getTipoChar(){
        return 'G';
    }

}
