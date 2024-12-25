package src;

    public class WildDrawFourCard extends Card {
        public WildDrawFourCard() {
            super( "none", "wild+4", "wild+4"); // Pas de couleur par défaut
        }

        @Override
        public void setCouleur(String couleur) {
            super.setCouleur(couleur);
        }

        @Override
        public String getCouleur() {
            return super.getCouleur();
        }

        @Override
        public String toString() {
            return "WildDrawFourCard{" +
                    "type='" + getType() + '\'' +
                    '}';
        }
    }

