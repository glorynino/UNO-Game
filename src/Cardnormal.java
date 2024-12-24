package src;

public class Cardnormal extends Card{
    private int numero;
    Cardnormal(int numero,String coleur,String type){
        super(coleur,type);
        this.numero = numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return super.toString() + "numero :"+this.numero;
    }
}

