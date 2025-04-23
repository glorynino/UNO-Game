package com.project.projetpoo.java.com.project.projetpoo;
public class Reverse extends Card{
    public Reverse(String couleur){
        super(couleur, "reverse");
    }


    @Override
    public String toString() {
        return "Card{couleur='" + getCouleur() + "', symbol=" + getSymbol() + '}';
    }
}

