public class Personagem {
    private int saude;
    private int percepcao;
    private boolean temRevolver;
    private boolean temBastao;
    private int cordenadaX;
    private int cordenadaY;
    private int numeroAtaduras;
    private Revolver revolver = new Revolver();
    private Soco soco = new Soco();
    private Taco taco = new Taco();


    //Dificuldade Hard: 1 percepcao, Medio: 2 percepcao, Facil: 3 percepcao
    public Personagem(int percepcao) {
        this.saude = 5;
        this.percepcao = percepcao;
        this.temRevolver = false;
        this.temBastao = false;
        this.numeroAtaduras = 0;
        this.cordenadaX = 0;
        this.cordenadaY = 0;

    }

    public boolean isTemBastao(){
        return temBastao;
    }

    public boolean isTemRevolver(){
        return temRevolver;
    }

    public int getSaude() {
        return saude;
    }

    public int getPercepcao() {
        return percepcao;
    }

    public void setSaude(int saude) {
        this.saude = saude;
    }

    public int getCordenadaX() {
        return cordenadaX;
    }

    public int getCordenadaY() {
        return cordenadaY;
    }

    public boolean temAtadura(){
        if(this.numeroAtaduras > 0){
            return true;
        } else {
            return false;
        }
    }

    public void adquirirAtadura(){
        this.numeroAtaduras++;
    }

    public int getNumeroAtaduras(){
        return numeroAtaduras;
    }


    public boolean curar() {
        if (this.numeroAtaduras > 0) {
            this.saude++;
            System.out.println("Curado com sucesso");
            return true;
        } else {
            System.out.println("Impossivel Curar nao tem ataduras");
            return false;
        }
    }

    public void setCordenada(int x, int y){
        this.cordenadaY = y;
        this.cordenadaX = x;
    }

    public Soco getSoco() {
        return soco;
    }

    public Taco getTaco() {
        return taco;
    }

    public Revolver getRevolver() {
        return revolver;
    }
    public void adquirirRevolver(){
        this.temRevolver = true;
    }
    public void adquirirTaco(){
        this.temBastao = true;
    }





}

