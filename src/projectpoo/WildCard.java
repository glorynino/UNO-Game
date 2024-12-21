package projectpoo;
public class WildCard extends Card {
    public WildCard() {
        super(-1, "none", "wild"); // Pas de couleur par défaut
    }

    @Override
    public String toString() {
        return "WildCard{" +
                "type='" + getType() + '\'' +
                '}';
    }
}
