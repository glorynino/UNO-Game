package projetpoo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Gamegui {
    private final Deck deck;
    private final PlayersList players;
    private final ArrayList<Card> cardsUpCard;
    private Card topCard;
    private boolean isGameOver;
    private boolean isClockwise;
    private final Scanner scanner;
     private int numberplayer;
    public Gamegui(int numberplayer,List nom) {
        this.deck = new Deck();
        this.players = new PlayersList();
        this.cardsUpCard = new ArrayList<>();
        this.isGameOver = false;
        this.isClockwise = true;
        this.scanner = new Scanner(System.in);
        initializePlayers(numberplayer,nom);
        // Draw initial top card from deck
        this.topCard = deck.deck.removeFirst();
    }

    public Deck getDeck(){
        return deck;
    }

    public Card getTopCard(){
        return topCard;
    }

    public Player getCurrentPlayer(){
        return players.getCurrentPlayer();
    }


    public boolean isGameOver() {
        return players.getPreviousPlayer().getHand().isEmpty();
    }

    public boolean isClockwise(){
        return isClockwise;
    }

    public Player getPreviousPlayer(){
        return players.getPreviousPlayer();
    }

    public void setClockwise(boolean isClockwise){
        this.isClockwise = isClockwise;
    }



    public void initializePlayers(int numberplayer, List nom) {
        System.out.println("\n=============== Welcome to UNO! ===============");

        int numPlayers = numberplayer;


        for (int i = 0; i < numPlayers; i++) {
            if (nom.get(i).equals("bot")) {
                players.addPlayer(new Bot("Bot-" + (i + 1)));
            } else {
                players.addPlayer(new Player((String) nom.get(i), false));
            }
        }


        // Fixed card distribution
        PlayerNode current = players.getFirstNode();
        for (int i = 0; i < numPlayers; i++) {
            Player player = current.player;
            deck.drawCard(player, 7);
            current = current.next;
        }
    }

    public void startGame() {
        System.out.println("The game begins!");
        while (topCard instanceof WildCard || topCard instanceof WildDrawFourCard) topCard = deck.deck.removeFirst();

        while (!isGameOver) {
            takeTurn();
        }
        System.out.println("The game is over! " + players.getPreviousPlayer().getNom() + " wins!");
    }

    public void takeTurn() {
        Player currentPlayer = players.getCurrentPlayer();
        System.out.println("\nTop card: " + topCard);
        System.out.println("It's " + currentPlayer.getNom() + "'s turn.");

        if (currentPlayer.getIsBot()) {
            playBotTurn((Bot) currentPlayer);
        } else {
            playHumanTurn(currentPlayer);
        }

        checkGameOver();
        //moveToNextPlayer();
    }

    public void playBotTurn(Bot bot) {
        // Update the bot's reference card and deck
        bot.setCardsup(topCard);
        bot.setDeck(deck);

        System.out.println("the bot's name is :" + bot.getNom() + "'s hand size: " + bot.getHand().size());
        bot.displayPlayableCards(topCard);
        Card cardToPlay = bot.playplayableCard();
        if (cardToPlay != null) {
            System.out.println(bot.getNom() + " plays " + cardToPlay);
            playCard(bot, cardToPlay);
        } else {
            System.out.println(bot.getNom() + " draws a card");
            deck.drawCard(bot, 1);
        }
    }

    public void playHumanTurn(Player player) {
        ArrayList<Card> playableCards = getPlayableCards(player);
        System.out.println("\nYour hand:");
        for (int i = 0; i < player.getHand().size(); i++) {
            System.out.println((i + 1) + ": " + player.getHand().get(i));
        }

        System.out.println("\nPlayable cards:");
        for (int i = 0; i < playableCards.size(); i++) {
            System.out.println((i + 1) + ": " + playableCards.get(i));
        }

        if (!playableCards.isEmpty()) {
            System.out.print("Enter the number of the card you want to play (0 to draw): ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                deck.drawCard(player, 1);
                System.out.println("You drew a card");
            } else if (choice > 0 && choice <= playableCards.size()) {
                Card selectedCard = playableCards.get(choice - 1);
                playCard(player, selectedCard);
            } else {
                System.out.println("Invalid choice!");
            }
        } else {
            System.out.println("No playable cards. Drawing a card...");
            deck.drawCard(player, 1);
        }
    }

    public void playCard(Player player, Card card) {
        player.play(card); // Remove the card from the player's hand
        cardsUpCard.add(topCard); // Add the previous top card to the discard pile
        topCard = card; // Set the new top card
        //handleSpecialCard(topCard);
        checkGameOver();
        moveToNextPlayer(); // Move to the next player
    }

    public ArrayList<Card> getPlayableCards(Player player) {
        ArrayList<Card> playableCards = new ArrayList<>();
        for (Card card : player.getHand()) {
            if (isCardPlayable(card, topCard)) {
                playableCards.add(card);
            }
        }
        return playableCards;
    }

    public void handleSpecialCard(Card card) {
        if (card instanceof WildCard) {
            handleWildCard();
        } else if (card instanceof WildDrawFourCard) {
            handleWildDrawFour();
        } else if (card instanceof Drawtwo) {
            Player nextPlayer = players.getNextPlayer();
            deck.drawCard(nextPlayer, 2);
        } else if (card instanceof Skip) {
            System.out.println("Skipping next player!");
            moveToNextPlayer();
        } else if (card instanceof Reverse) {
            System.out.println("Reversing turn order!");
            isClockwise = !isClockwise;
        }
    }

    public void handleWildCard() {
        String color = chooseColor(players.getCurrentPlayer());
        System.out.println(players.getCurrentPlayer().getNom() + " chose " + color);
        topCard.setCouleur(color);
    }

    public void handleWildDrawFour() {
        String color = chooseColor(players.getCurrentPlayer());
        System.out.println(players.getCurrentPlayer().getNom() + " chose " + color);
        topCard.setCouleur(color);

        Player nextPlayer = players.getNextPlayer();
        deck.drawCard(nextPlayer, 4);
    }

    public String chooseColor(Player player) {
        String[] colors = {"red", "green", "blue", "yellow"};
        if (player.getIsBot()) {
            return colors[new Random().nextInt(colors.length)];
        } else {
            System.out.println("Choose a color: 1.Red 2.Green 3.Blue 4.Yellow");
            int choice = scanner.nextInt();
            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice!");
                return chooseColor(player);
            }
            return colors[Math.min(Math.max(choice - 1, 0), colors.length - 1)];
        }
    }

    public void moveToNextPlayer() {
        if (isClockwise) {
            players.nextPlayer();
        } else {
            players.previousPlayer();
        }
        System.out.println("Current Player: " + getCurrentPlayer().getNom()); // Debug
    }

    public void checkGameOver() {
        isGameOver = players.getCurrentPlayer().getHand().isEmpty();
    }

    public boolean isCardPlayable(Card card, Card topCard) {
        // Wild cards are always playable
        if (card instanceof WildCard || card instanceof WildDrawFourCard) {
            return true;
        }

        // Match color or symbol for non-wild cards
        return card.getCouleur().equals(topCard.getCouleur()) ||
                card.getSymbol().equals(topCard.getSymbol());
    }

    public Player getNextPlayer() {
        return players.getNextPlayer();
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }
}