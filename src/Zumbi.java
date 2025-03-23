public abstract class Zumbi {
    private int vida;
    private boolean estavivo;

    public Zumbi(int vida){
        this.vida = vida;
        this.estavivo = true;
    }

    public boolean getEstado(){
        return estavivo;
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

}
