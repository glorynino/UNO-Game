
    public class WildDrawFourCard extends Card {
        public WildDrawFourCard() {
            super(-1, "none", "wild+4"); // Pas de couleur par défaut
        }

        @Override
        public String toString() {
            return "WildDrawFourCard{" +
                    "type='" + getType() + '\'' +
                    '}';
        }
    }

