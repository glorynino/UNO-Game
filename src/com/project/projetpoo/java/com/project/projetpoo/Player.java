package com.project.projetpoo.java.com.project.projetpoo;
import java.util.ArrayList;

public class Player {
    private String nom;
    private ArrayList<Card> hand = new ArrayList<>();
    private boolean isBot;


    public Player(String nom, boolean isBot) {
        this.nom = nom;
        this.isBot = isBot;
    }

    public boolean getIsBot() {
        return isBot;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void draw(Card card) {
        hand.add(card);
    }

    public void play(Card card) {
        hand.remove(card);
    }

    public void displayPlayableCards(Card topCard) {
        System.out.println(nom + "'s playable cards:");
        int index = 1;
        ArrayList<Card> playableCards = new ArrayList<>();
        
        for (Card card : hand) {
            if (isValidCard(card, topCard)) {
                System.out.println(index + ": " + card);
                playableCards.add(card);
                index++;
            }
        }
        
        if (playableCards.isEmpty()) {
            System.out.println("No playable cards. Must draw a card.");
        }
    }

    private boolean isValidCard(Card card, Card topCard) {
        if (card instanceof WildCard || card instanceof WildDrawFourCard) {
            return true;
        }
        return card.getCouleur().equals(topCard.getCouleur()) || card.getSymbol().equals(topCard.getSymbol());
    }

}

