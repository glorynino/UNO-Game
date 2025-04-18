package com.project.projetpoo;
public class Card {
    private String couleur,symbol;

     // hadi surtout f les carte speciale car makach une carte avec un numero -1 donc j'ai les est representer haka
    public Card() {
        this.couleur = "undefined";
        this.symbol = "undefined";
    }


   // hna c pour inisialiser les couleur ou kolach
    public Card( String couleur, String symbol) {

        this.couleur = couleur;
        this.symbol = symbol;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getSymbol() {
        return symbol;
    }


    @Override
    public String toString() {
        return "Card{" +
                "couleur='" + couleur + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}

    
