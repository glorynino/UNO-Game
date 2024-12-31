package src;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

  ArrayList<Card> deck;
  public void createDeck() {

    deck = new ArrayList<>();
    String[] couleurs = {"red", "green", "blue", "yellow"};

    for (String coleur : couleurs) {
      deck.add(new Card (coleur, "0"));

      for (int i=1; i<=9; i++) {
        deck.add(new Card(coleur, String.valueOf(i)));
        deck.add(new Card( coleur, String.valueOf(i)));
      }

      deck.add(new Drawtwo(coleur));
      deck.add(new Drawtwo(coleur));
      deck.add(new Skip(coleur));
      deck.add(new Skip(coleur));
      deck.add(new Reverse(coleur));
      deck.add(new Reverse(coleur));

    }
    
    for(int i=0;i<4;i++){
      deck.add(new WildCard());
      deck.add(new WildDrawFourCard());
    }
  }
  public void shuffleDeck(){
    Collections.shuffle(deck);
  }
  public void drawCard(Player player, int n){
    Deck deck = new Deck();
    Card card = null;
    if(n>deck.deck.size()){
          for(int i=0;i<deck.deck.size();i++){
            card = deck.deck.remove(0);
            player.draw(card);
          }
        }else {
          for(int i=0;i<n;i++){
            card = deck.deck.remove(0);
            player.draw(card);
          }
        }
  }
  public void ressetDeck(){
    deck.clear();
    createDeck();
    shuffleDeck();
  }
}
