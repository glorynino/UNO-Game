package src;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>(); // Initialize the deck in the constructor
        createDeck();
        shuffleDeck();
    }

    public void createDeck() {
        String[] couleurs = {"red", "green", "blue", "yellow"};

        for (String coleur : couleurs) {
            deck.add(new Card(coleur, "0"));

            for (int i = 1; i <= 9; i++) {
                deck.add(new Card(coleur, String.valueOf(i)));
                deck.add(new Card(coleur, String.valueOf(i)));
            }

            deck.add(new Drawtwo(coleur));
            deck.add(new Drawtwo(coleur));
            deck.add(new Skip(coleur));
            deck.add(new Skip(coleur));
            deck.add(new Reverse(coleur));
            deck.add(new Reverse(coleur));
        }

        for (int i = 0; i < 4; i++) {
            deck.add(new WildCard());
            deck.add(new WildDrawFourCard());
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void drawCard(Player player, int n) {
        if (deck == null || deck.isEmpty()) {
            System.out.println("Deck is empty. Resetting the deck...");
            ressetDeck();
        }

        for (int i = 0; i < n; i++) {
            if (!deck.isEmpty()) {
                Card card = deck.remove(0);
                player.draw(card);
            } else {
                System.out.println("No more cards in the deck.");
                break;
            }
        }
    }

    public void ressetDeck() {
        deck.clear();
        createDeck();
        shuffleDeck();
    }
}