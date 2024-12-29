
package src;
import java.util.ArrayList;
import java.util.Scanner;
class Game {
    private ArrayList<Card> cardsupcard = new ArrayList<>();
    private Deck deck;
    private PlayersList players; // Circular doubly linked list for players
    private boolean isGameOver;     // Indicates the end of the game
    private boolean isClockwise;    // Game direction

    // Constructor
    public Game(PlayersList players) {
        this.players = players;
        this.isGameOver = false;
        this.isClockwise = true; // Default direction is clockwise
    }

    // Getter for game state
    public boolean isGameOver() {
        return isGameOver;
    }

    // Method to start the game
    public void start(Card cardsup) {
        System.out.println("The game begins!");
        while (!isGameOver) {
            takeTurn(cardsup);
        }
        System.out.println("The game is over!");
    }

    // Handle each player's turn
    private void takeTurn(Card cardsup) {
        Player currentPlayer = players.getCurrentPlayer();
        System.out.println("It's " + currentPlayer.getNom() + "'s turn.");
        playTurn(currentPlayer, cardsup);

        // Move to the next or previous player based on the direction
        if (isClockwise) {
            players.nextPlayer();
        } else {
            players.previousPlayer();
        }

        checkGameOver();
    }

    private String chooseColor(Player player) {
        System.out.println(player.getNom() + ", choose a color to continue: 1.Red 2.Blue 3.Yellow 4.Green");
        Scanner sc = new Scanner(System.in);
        String color = sc.nextLine();
        sc.close();
        switch (color) {
            case "1": return "red";
            case "2": return "blue";
            case "3": return "yellow";
            case "4": return "green";
            default:
                System.out.println("Invalid choice. Please try again.");
                return chooseColor(player);
        }
        
    }

    private void playTurn(Player player, Card cardsup) {
        cardsupcard.add(cardsup);

        if (player.getHand().isEmpty()) {
            System.out.println(player.getNom() + " has no more cards!");
        } else {
            if (cardsup instanceof WildCard) {
                Player previousPlayer = players.getPreviousPlayer();
                String color = chooseColor(players.getPreviousPlayer());
                System.out.println(previousPlayer.getNom() + " chose the color " + color + ".");
            } else if (cardsup instanceof WildDrawFourCard) {
                WildDrawFour(player);
            } else if (cardsup instanceof Drawtwo) {
                deck.drawCard(player, 2);
            } else if (cardsup instanceof Skip) {
                players.nextPlayer(); // Skip to the next player
            } else if (cardsup instanceof Reverse) {
                isClockwise = !isClockwise; // Reverse direction
                System.out.println("Direction reversed!");
            }

            // Display playable cards for the current player
            for (Card card : player.getHand()) {
                if (card.getCouleur().equalsIgnoreCase(cardsup.getCouleur()) || card.getSymbol() == cardsup.getSymbol()) {
                    System.out.println("Playable card: " + card);
                }
            }
        }
    }

    private void WildDrawFour(Player player) {
        Player previousPlayer = players.getPreviousPlayer();
        String color = chooseColor(players.getPreviousPlayer());
        System.out.println(previousPlayer.getNom() + " chose the color " + color + ".");
        if (deck.deck.size() >= 4) {
            deck.drawCard(player, 4);
        } else {
            System.out.println("Not enough cards in the deck. Shuffling the discard pile...");
            cardsupcard.addAll(deck.deck);
            deck.shuffleDeck();
            cardsupcard.clear();
            deck.drawCard(player, 4);
        }
    }

    private void checkGameOver() {
        isGameOver = players.getCurrentPlayer().getHand().isEmpty();
    }
}