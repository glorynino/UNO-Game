
package projectpoo;
import java.util.ArrayList;
import java.util.Scanner;
public class Game {
    private ArrayList<Card> cardsupcard;
    Deck deck = new Deck();
    private ArrayList<Player> players; // Liste des joueurs
    private int currentPlayerIndex;   // Indice du joueur actuel
    private boolean isGameOver;       // Indicateur de fin de jeu
    private boolean isclockwise;
    
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
        if (isclockwise) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
        


        checkGameOver();
    }

    private String choosecolor (Player player) {
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
        return color;


    }

    // Méthode pour gérer le tour d'un joueur
    private void playTurn(Player player,Card cardsup) {
        int j;
        cardsupcard.add(cardsup);
        // Logique du tour d'un joueur
        if (player.getHand().isEmpty()) {
            System.out.println(player.getNom() + " n'a plus de cartes !");
        } else {
            if (cardsup instanceof WildCard) {
                Player previousplayer = players.get(currentPlayerIndex - 1);
                String color = choosecolor(previousplayer);
                for (int i = 0; i < player.getHand().size(); i++) {
                    System.out.println("les carte que le joueur peu jouer ");
                    if (player.getHand().get(i).getCouleur().equalsIgnoreCase(color)) {
                        System.out.println("carte numero "+i+":"+player.getHand().get(i));
                    }
                }

            }
            if (cardsup instanceof WildDrawFourCard) {
                // Vérifier que le deck contient au moins 4 cartes
                if (deck.deck.size() >= 4) {
                    deck.drawCard(player, 4);

                } else {
                    System.out.println("Le deck ne contient pas assez de cartes. Mélange des cartes défaussées dans le deck...");
                    cardsupcard.addAll(deck.deck);
                    deck.shuffleDeck();
                    cardsupcard.clear();
                    // Gestion du manque de cartes (exemple : mélanger la pile de défausse dans le deck)
                    //    refillDeckFromDiscardPile(); // Implémente une méthode pour cela si nécessaire
                }


                Player previousplayer = players.get(currentPlayerIndex - 1);
                String color = choosecolor(previousplayer);
                for (int i = 0; i < player.getHand().size(); i++) {
                    if (player.getHand().get(i).getCouleur().equalsIgnoreCase(color)) {
                        System.out.println("Les cartes du joueur sont : " + player.getHand());
                    }

                }

            }
            else {
                if (cardsup instanceof Drawtwo) {
                    deck.drawCard(player, 2);
                }
                if (cardsup instanceof Skip) {
                    player = players.get((currentPlayerIndex + 1) % players.size());
                }
                if (cardsup instanceof Reverse) {
                    isclockwise = !isclockwise;
                    player = players.get((currentPlayerIndex - 1) % players.size());                    
                } //reverse le tour de jeu
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
    @SuppressWarnings("unused")
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
        @SuppressWarnings("unused")
        int i =0;
        for (int j = 0; j < players.size() ; j++) {
            if (players.get(j).getHand().isEmpty()) {
                i ++;
            }
        }

        isGameOver = players.stream().allMatch(player -> player.getHand().isEmpty());
    }
}