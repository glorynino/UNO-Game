package Unostart;

import NSwing.*;
import projetpoo.Player;
import projetpoo.Card;
import projetpoo.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class UNOGUI extends NFrame {
    private Gamegui game;
    private TopCardPanel topCardPanel;
    private PlayerHandPanel playerHandPanel;
    private ALabel currentPlayerLabel;
    private ATextArea gameLog;
    private final List<String> nomplayers;
    private final int numberplayer;
    public UNOGUI(int numberplayer, List<String> nom) {
        setTitle("UNO Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.nomplayers = nom;
        this.numberplayer = numberplayer;
        game = new Gamegui(numberplayer,nom);
        initializeUI();
    }

    private void initializeUI() {
        APanel mainPanel = new APanel(new BorderLayout());

        // Top panel for the top card and current player
        APanel topPanel = new APanel(new BorderLayout());
        topCardPanel = new TopCardPanel(game.getTopCard());
        currentPlayerLabel = new ALabel("Current Player: " + game.getCurrentPlayer().getNom(), SwingConstants.CENTER);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(topCardPanel, BorderLayout.CENTER);
        topPanel.add(currentPlayerLabel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for the game log
        gameLog = new ATextArea();
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        AScrollPane scrollPane = new AScrollPane(gameLog);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for the player's hand
        Player currentPlayer = game.getCurrentPlayer();
        playerHandPanel = new PlayerHandPanel(currentPlayer, game, this);
        mainPanel.add(playerHandPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void updateGameState() {

        // Update the top card display
        topCardPanel.setTopCard(game.getTopCard());

        // Update the current player label
        currentPlayerLabel.setText("Current Player: " + game.getCurrentPlayer().getNom());

        // Update the player's hand
        playerHandPanel.setPlayer(game.getCurrentPlayer());
        playerHandPanel.updateHand();

        // Check if the game is over
        checkGameOver();
    }

    public void logMessage(String message) {
        gameLog.append(message + "\n");
    }

    public Point getTopCardPanelCenter() {
        // Obtenir la position du centre du panel de la carte du dessus
        Point p = topCardPanel.getLocationOnScreen();
        return new Point(
                p.x + topCardPanel.getWidth() / 2,
                p.y + topCardPanel.getHeight() / 2
        );
    }

    private void handleSpecialCardEffects() {
        Card topCard = game.getTopCard();
        System.out.println("Handling special card: " + topCard); // Debug

        if (topCard instanceof WildCard) {
            System.out.println("Wild Card detected"); // Debug
            handleWildCard();
        } else if (topCard instanceof WildDrawFourCard) {
            System.out.println("Wild Draw Four detected"); // Debug
            handleWildDrawFour();
        } else if (topCard instanceof Skip) {
            System.out.println("Skip Card detected"); // Debug
            handleSkipCard();
        } else if (topCard instanceof Reverse) {
            System.out.println("Reverse Card detected"); // Debug
            handleReverseCard();
        } else if (topCard instanceof Drawtwo) {
            System.out.println("Draw Two Card detected"); // Debug
            handleDrawTwoCard();
        }

        // Reset the special card effect after handling it
        game.setTopCard(topCard); // Ensure the top card is updated
        System.out.println("Special card effect handled"); // Debug
    }

    private void handleWildCard() {
        SwingUtilities.invokeLater(() -> {
            String color = AOptionPane.showInputDialog(this, "Choose a color (red, green, blue, yellow):");
            if (color != null && (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("green") ||
                    color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("yellow"))) {
                game.getTopCard().setCouleur(color);
                topCardPanel.setTopCard(game.getTopCard());
                logMessage(game.getCurrentPlayer().getNom() + " chose " + color + " for the Wild Card.");
                updateGameState(); // Refresh the GUI
            } else {
                AOptionPane.showMessageDialog(this, "Invalid color! Please choose again.", "Error", AOptionPane.ERROR_MESSAGE);
                handleWildCard(); // Retry (this is the only recursive call)
            }
        });
    }

    private void handleWildDrawFour() {
        SwingUtilities.invokeLater(() -> {
            String color = AOptionPane.showInputDialog(this, "Choose a color (red, green, blue, yellow):");
            if (color != null && (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("green") ||
                    color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("yellow"))) {
                game.getTopCard().setCouleur(color);
                topCardPanel.setTopCard(game.getTopCard());
                Player nextPlayer = game.getNextPlayer();
                game.getDeck().drawCard(nextPlayer, 4);
                logMessage(nextPlayer.getNom() + " draws 4 cards!");
                logMessage(game.getCurrentPlayer().getNom() + " chose " + color + " for the Wild Draw Four Card.");
                updateGameState(); // Refresh the GUI
            } else {
                AOptionPane.showMessageDialog(this, "Invalid color! Please choose again.", "Error", AOptionPane.ERROR_MESSAGE);
                handleWildDrawFour(); // Retry (this is the only recursive call)
            }
        });
    }

    private void handleSkipCard() {
        SwingUtilities.invokeLater(() -> {
            logMessage("Next player is skipped!");
            game.moveToNextPlayer();
            updateGameState(); // Refresh the GUI
        });
    }

    private void handleReverseCard() {
        SwingUtilities.invokeLater(() -> {
            logMessage("Turn order reversed!");
            game.setClockwise(!game.isClockwise());
            updateGameState(); // Refresh the GUI
        });
    }

    private void handleDrawTwoCard() {
        SwingUtilities.invokeLater(() -> {
            Player nextPlayer = game.getNextPlayer();
            game.getDeck().drawCard(nextPlayer, 2);
            logMessage(nextPlayer.getNom() + " draws 2 cards!");
            updateGameState(); // Refresh the GUI
        });
    }


    public void checkGameOver() {
        if (game.isGameOver()) {
            Player winner = game.getPreviousPlayer();
            logMessage(winner.getNom() + " wins the game! Congratulations!");
            int choice = AOptionPane.showConfirmDialog(this, winner.getNom() + " wins! Do you want to play again?", "Game Over", AOptionPane.YES_NO_OPTION);
            if (choice == AOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }

    private void resetGame() {
        game = new Gamegui(numberplayer, nomplayers);
        initializeUI();
        updateGameState();
    }

    public static void main(String[] args) {
        List<String> nom = List.of("neil","anes");
        int n = 2;
        System.out.println("Début de l'application");
        UNOGUI gui = new UNOGUI(n, nom);  // Crée l'objet UNOGUI
        gui.setVisible(true);  // Affiche la fenêtre
        System.out.println("Début de l'application");
    }
}