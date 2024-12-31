package src;

import java.util.ArrayList;

public class Bot extends Player{
  private ArrayList<Card> hand = new ArrayList<Card>();
  Card cardsup;
  Deck deck;

  public Bot(String nom) {
    super(nom, true);
  }

  
  public String getNom() {
    return super.getNom();
  }

  public Card playplayableCard() {
    for (Card c : hand) {
      if (c.getCouleur() == cardsup.getCouleur() || c.getSymbol() == cardsup.getSymbol()) {
        return c;
      }
    }
    return null;  
  }

  
  @Override
  public void play(Card playplayableCard) {
    if (playplayableCard == null) {
      System.out.println("No playable card");
      deck.drawCard(this, 1);
    }
    hand.remove(playplayableCard);
  }
}

