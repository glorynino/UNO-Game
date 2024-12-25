package src;
public class Card {
    private String couleur,type,symbol;

     // hadi surtout f les carte speciale car makach une carte avec un numero -1 donc j'ai les est representer haka
    public Card() {
        this.couleur = "undefined";
        this.type = "undefined";
        this.symbol = "undefined";
    }


   // hna c pour inisialiser les couleur ou kolach
    public Card( String couleur, String type, String symbol) {

        this.couleur = couleur;
        this.type = type;
        this.symbol = symbol;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }


    @Override
    public String toString() {
        return "Card{" +
                "couleur='" + couleur + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

    
