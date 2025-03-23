public abstract class Zumbi {
    private int vida;
    private boolean estavivo;
    private int cordenadaX;
    private int cordenadaY;

    public Zumbi(int vida,int cordenadaX,int cordenadaY){
        this.vida = vida;
        this.estavivo = true;
        this.cordenadaY = cordenadaY;
        this.cordenadaX = cordenadaX;

    }

    public boolean getEstado(){
        if(estavivo && getVida() > 0){
            return true;
        } else {
            return false;
        }
    }

    public void matarZumbi(){
        this.estavivo = false;
    }

    public int getVida(){
        return this.vida;
    }

    abstract public String getTipo();

    public void atacado(int dano){
        if(this.vida > dano){
            this.vida -= dano;
            System.out.println("Zumbi atacado tomou " + dano + " de dano");
        } else {
            this.vida = 0;
            this.estavivo = false;
            System.out.println("Zumbi morto");
        }
    }

    public int getCordenadaX() {
        return cordenadaX;
    }

    public int getCordenadaY() {
        return cordenadaY;
    }

    public abstract char getTipoChar();

}
