package com.project.projetpoo.java.com.project.projetpoo.gui;

import com.project.projetpoo.java.com.project.projetpoo.Player;
import com.project.projetpoo.java.com.project.projetpoo.Card;
import com.project.projetpoo.java.com.project.projetpoo.*;

import javax.swing.*;
import java.awt.*;


public class UNOGUI extends JFrame {
    private Gamegui game;
    private TopCardPanel topCardPanel;
    private PlayerHandPanel playerHandPanel;
    private JLabel currentPlayerLabel;
    private JTextArea gameLog;

    public UNOGUI() {
        setTitle("UNO Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        game = new Gamegui();
        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top panel for the top card and current player
        JPanel topPanel = new JPanel(new BorderLayout());
        topCardPanel = new TopCardPanel(game.getTopCard());
        currentPlayerLabel = new JLabel("Current Player: " + game.getCurrentPlayer().getNom(), SwingConstants.CENTER);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(topCardPanel, BorderLayout.CENTER);
        topPanel.add(currentPlayerLabel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for the game log
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(gameLog);
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
            String color = JOptionPane.showInputDialog(this, "Choose a color (red, green, blue, yellow):");
            if (color != null && (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("green") ||
                    color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("yellow"))) {
                game.getTopCard().setCouleur(color);
                topCardPanel.setTopCard(game.getTopCard());
                logMessage(game.getCurrentPlayer().getNom() + " chose " + color + " for the Wild Card.");
                updateGameState(); // Refresh the GUI
            } else {
                JOptionPane.showMessageDialog(this, "Invalid color! Please choose again.", "Error", JOptionPane.ERROR_MESSAGE);
                handleWildCard(); // Retry (this is the only recursive call)
            }
        });
    }

    private void handleWildDrawFour() {
        SwingUtilities.invokeLater(() -> {
            String color = JOptionPane.showInputDialog(this, "Choose a color (red, green, blue, yellow):");
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
                JOptionPane.showMessageDialog(this, "Invalid color! Please choose again.", "Error", JOptionPane.ERROR_MESSAGE);
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
            int choice = JOptionPane.showConfirmDialog(this, winner.getNom() + " wins! Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }

    private void resetGame() {
        game = new Gamegui();
        initializeUI();
        updateGameState();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UNOGUI unoGUI = new UNOGUI();
            unoGUI.setVisible(true);
        });
    }
}