public class Card {
    private int numcard;
    private String couleur,type;

     // hadi surtout f les carte speciale car makach une carte avec un numero -1 donc j'ai les est representer haka
    public Card() {
        this.numcard = -1; // Valeur par défaut
        this.couleur = "undefined";
        this.type = "undefined";
    }


   // hna c pour inisialiser les couleur ou kolach
    public Card(int numcard, String couleur, String type) {
        this.numcard = numcard;
        this.couleur = couleur;
        this.type = type;
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

    public int getNumcard() {
        return numcard;
    }

    public void setNumcard(int numcard) {
        this.numcard = numcard;
    }


}
