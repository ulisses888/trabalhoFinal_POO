public class ZumbiRastejante extends Zumbi{

    public ZumbiRastejante(int vida,int x,int y){
        super(vida,x,y);
    }

    public String getTipo(){
        return "Zumbi Rastejante";
    }

    public char getTipoChar(){
        return 'R';
    }

}
