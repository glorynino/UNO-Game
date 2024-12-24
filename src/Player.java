package src;
import java.util.ArrayList;

public class Player {
    private String nom;
    private ArrayList<Card> hand = new ArrayList<Card>();

    public Player() {
        this.nom = "indefined";
    }

    public Player(String nom) {
        this.nom = nom;
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
