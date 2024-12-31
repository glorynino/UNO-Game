package src;
import java.util.ArrayList;

public class Player {
    private String nom;
    private ArrayList<Card> hand = new ArrayList<Card>();
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
}
