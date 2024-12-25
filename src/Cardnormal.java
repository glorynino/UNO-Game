package src;

public class Cardnormal extends Card{
    
    Cardnormal(String coleur,String type,String symbol){
        super(coleur,type,symbol);
        
    }


    @Override
    public String toString() {
        return super.toString() + "symbol :"+this.getSymbol();
    }
}

