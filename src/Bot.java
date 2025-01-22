package src;

public class Bot extends Player {
  private Card cardsup;
  private Deck deck;

  public Bot(String nom) {
      super(nom, true);
  }

  public String getNom() {
      return super.getNom();
  }

  public Card playplayableCard() {
      for (Card c : getHand()) {
          if (c.getCouleur().equals(cardsup.getCouleur()) || 
              c.getSymbol().equals(cardsup.getSymbol()) ||
              c.getCouleur().equals("Wild")) {
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
      } else {
          super.play(playplayableCard);
      }
  }

  public void setCardsup(Card cardsup) {
      this.cardsup = cardsup;
  }

  public void setDeck(Deck deck) {
      this.deck = deck;
  }
}