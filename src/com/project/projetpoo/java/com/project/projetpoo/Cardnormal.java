package com.project.projetpoo.java.com.project.projetpoo;

public class Cardnormal extends Card{
    
    public Cardnormal(String coleur, String symbol){
        super(coleur, symbol);
    }


    @Override
    public String toString() {
        return super.toString() + "symbol :"+this.getSymbol();
    }
}

