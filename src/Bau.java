public class Bau extends ObjetosMapa {

    private int conteudo;
    private boolean aberto;


    //1 - Revolver
    //2 - Taco
    //3 - Atadura

    public Bau(int conteudo){
        this.conteudo = conteudo;
        this.aberto = false;
        this.transitavel = true;
    }

    public int abrirBau(){
        if(this.aberto == false){
            System.out.println("Abrindo Bau");
            return this.conteudo;
        } else {
            System.out.println("Bau ja foi aberto");
            return 0;
        }
    }


}
