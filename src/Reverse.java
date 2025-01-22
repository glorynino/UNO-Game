package src;
public class Reverse extends Card{
    public Reverse(String couleur){
        super(couleur, "reverse");
    }


    @Override
    public String toString() {
        return "Card{couleur='" + getCouleur() + "', symbol=" + getSymbol() + '}';
    }
}

