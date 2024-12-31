package src;

public class Cardnormal extends Card{
    
    Cardnormal(String coleur, String symbol){
        super(coleur, symbol);
        
    }


    @Override
    public String toString() {
        return super.toString() + "symbol :"+this.getSymbol();
    }
}

