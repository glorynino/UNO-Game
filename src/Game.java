
package src;
import java.util.ArrayList;
import java.util.Random;
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
        if (currentPlayer.getIsBot()) {
            playTurn((Bot) currentPlayer, cardsup);
        }else {
            playTurn(currentPlayer, cardsup);
        }
        // Move to the next or previous player based on the direction
        if (isClockwise) {
            players.nextPlayer();
        } else {
            players.previousPlayer();
        }

        checkGameOver();
    }

    /*private String chooseColor(Bot bot) {
        System.out.println(bot.getNom() + ", choose a color to continue: 1.Red 2.Blue 3.Yellow 4.Green");
        Random random = new Random();
        String color = String.valueOf(random.nextInt(4) + 1);
        switch (color) {
            case "1": return "red";
            case "2": return "blue";
            case "3": return "yellow";
            case "4": return "green";
            default:
                System.out.println("Invalid choice. Please try again.");
                return chooseColor(bot);
        }
        
    }*/

    private String chooseColor(Player player) {

        if (player.getIsBot() == false) {
            System.out.println(player.getNom() + ", choose a color to continue: 1.Red 2.Blue 3.Yellow 4.Green");
            Random random = new Random();
            String color = String.valueOf(random.nextInt(4) + 1);
            switch (color) {
                case "1": return "red";
                case "2": return "blue";
                case "3": return "yellow";
                case "4": return "green";
                default:
                    System.out.println("Invalid choice. Please try again.");
                    return chooseColor(player);
            }
        }else {
            System.out.println(player.getNom() + ", choose a color to continue: 1.Red 2.Blue 3.Yellow 4.Green");
            Random random = new Random();
            String color = String.valueOf(random.nextInt(4) + 1);
            switch (color) {
            case "1": return "red";
            case "2": return "blue";
            case "3": return "yellow";
            case "4": return "green";
            default:
                return chooseColor(player);
        }
            
        }   
        
    }

    private void playTurn(Bot bot, Card cardsup) {
        cardsupcard.add(cardsup);

        if (bot.getHand().isEmpty()) {
            System.out.println(bot.getNom() + " has no more cards!");
        } else {
            if (cardsup instanceof WildCard) {
                Player previousPlayer = players.getPreviousPlayer();
                String color = chooseColor(players.getPreviousPlayer());
                System.out.println(previousPlayer.getNom() + " chose the color " + color + ".");
            } else if (cardsup instanceof WildDrawFourCard) {
                WildDrawFour(bot);
            } else if (cardsup instanceof Drawtwo) {
                deck.drawCard(bot, 2);
            } else if (cardsup instanceof Skip) {
                players.nextPlayer(); // Skip to the next player
            } else if (cardsup instanceof Reverse) {
                isClockwise = !isClockwise; // Reverse direction
                System.out.println("Direction reversed!");
            }
            // Display playable cards for the current player
            Card playplayableCard = bot.playplayableCard();
            System.out.println(bot.getNom() + " plays " + playplayableCard + ".");
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