package projectpoo;
import java.util.ArrayList;
import java.util.Scanner;
public class Game {


        private ArrayList<Player> players; // Liste des joueurs
        private int currentPlayerIndex;   // Indice du joueur actuel
        private boolean isGameOver;       // Indicateur de fin de jeu

        // Constructeur
        public Game(ArrayList<Player> players) {
            this.players = players;
            this.currentPlayerIndex = 0;  // Le premier joueur commence
            this.isGameOver = false;
        }

        // Getter pour l'état du jeu
        public boolean isGameOver() {

            return isGameOver;
        }

        // Méthode pour démarrer la partie
        public void start(Card cardsup) {
            System.out.println("La partie commence !");
            while (!isGameOver) {
                takeTurn(cardsup);
            }
            System.out.println("La partie est terminée !");
        }

        // had la fonction c le tour de chaque joueur lazem f l main en verifie ida il a encore des carte wla non
        private void takeTurn(Card cardsup) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("C'est au tour de " + currentPlayer.getNom());
            playTurn(currentPlayer,cardsup);

            // Passer au joueur suivant
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();


            checkGameOver();
        }

        private void choosecolor (Player player) {
            System.out.println("choisissez une color pour continuer: 1.Rouge 2.Blue 3.Jaune 4.Vert");
            Scanner sc = new Scanner(System.in);
            Card cardsup = new Card();
            String color = sc.nextLine();
            sc.close();
            if (color.equals("1")) {
                cardsup.setCouleur("red");
                } else if (color.equals("2")) {
                cardsup.setCouleur("blue");
                } else if (color.equals("3")) {
                cardsup.setCouleur("yellow");
                } else if (color.equals("4")) {
                cardsup.setCouleur("green");
                } else {
                System.out.println("Veuillez choisir une couleur valide.");
            }



        }

        // Méthode pour gérer le tour d'un joueur
        private void playTurn(Player player,Card cardsup) {
            int j;
            // Logique du tour d'un joueur
            if (player.getHand().isEmpty()) {
                System.out.println(player.getNom() + " n'a plus de cartes !");
            } else {
                if (cardsup instanceof WildCard) {
                    Player previousplayer = players.get(currentPlayerIndex - 1);
                    choosecolor(previousplayer);

                }
                if ( cardsup instanceof WildDrawFourCard) {
                    // il faut comprendre belik gethand elle va afficher les ellement ta3 l array de la façon toString dans le card
                    System.out.println("les carte du joueur son tu peux choisir n'importe  " + player.getHand());
                    Player previousplayer = players.get(currentPlayerIndex - 1);
                    choosecolor(previousplayer);

                }else {
                    j= 1;
                    for (int i = 0; i < player.getHand().size() ; i++) {
                        if (player.getHand().get(i).getCouleur().equalsIgnoreCase(cardsup.getCouleur()) || player.getHand().get(i).getNumcard() == cardsup.getNumcard()) {
                                  System.out.println("carte"+j+player.getHand().get(i));
                                  j++;
                        }
                    }
                }



            }
        }
        //nsuprimi les player qui ont plus de carte dans l array
        private void deletejoueur(ArrayList<Player> players){
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getHand().isEmpty()) {
                    players.remove(i);
                    i--;
                }
            }
        }

        // Vérification de la fin du jeu
        private void checkGameOver() {
            // si tous les joueurs n'ont plus de cartes
            /*stream elle va servir bch tahder 3la 9a3 les ellement de "players" et "allmatch" servira pour mettre une
            condition sur tout c'est element      */
            isGameOver = players.stream().allMatch(player -> player.getHand().isEmpty());
        }
}


