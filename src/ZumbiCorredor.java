public class ZumbiCorredor extends Zumbi{

    public ZumbiCorredor(int vida,int x,int y){
        super(vida,x,y);
    }

    public String getTipo(){
        return "Zumbi Corredor";
    }

    public char getTipoChar(){
        return 'C';
    }

}
