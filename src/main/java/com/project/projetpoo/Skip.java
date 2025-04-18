package com.project.projetpoo;

public class Skip extends Card {
    public Skip(String couleur) {
        super(couleur, "Skip");
    }

    @Override
    public String toString() {
        return "Card{couleur='" + getCouleur() + "', symbol=" + getSymbol() + '}';
    }
}