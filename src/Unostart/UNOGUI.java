package Unostart;

import NSwing.*;
import projetpoo.Player;
import projetpoo.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class UNOGUI extends NFrame {
    private Gamegui game;
    private TopCardPanel topCardPanel;
    private PlayerHandPanel playerHandPanel;
    private ALabel currentPlayerLabel;
    private final List<LogEntry> gameLogEntries;
    private final List<String> nomplayers;
    private final int numberplayer;
    public UNOGUI(int numberplayer, List<String> nom) {
        setTitle("UNO Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.nomplayers = nom;
        this.numberplayer = numberplayer;
        game = new Gamegui(numberplayer, nom);
        gameLogEntries = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        APanel mainPanel = new APanel(new BorderLayout());

        // Top panel for current player and log history button
        APanel topPanel = new APanel(new BorderLayout());
        APanel playerInfoPanel = new APanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        currentPlayerLabel = new ALabel("Current Player: " + game.getCurrentPlayer().getNom(), SwingConstants.CENTER);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        NButton logHistoryButton = new NButton("Log History");
        logHistoryButton.setFont(new Font("Arial", Font.BOLD, 14));
        logHistoryButton.addActionListener(_ -> showLogHistory());

        playerInfoPanel.add(currentPlayerLabel);
        playerInfoPanel.add(logHistoryButton);
        topPanel.add(playerInfoPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for the top card (moved from top to center)
        APanel centerPanel = new APanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 240, 240));

        // Add some padding around the top card
        APanel cardContainer = new APanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        cardContainer.setBackground(new Color(240, 240, 240));

        topCardPanel = new TopCardPanel(game.getTopCard());
        cardContainer.add(topCardPanel);
        centerPanel.add(cardContainer, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

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
        // Add the message to our log entries list
        String playerName = game.getCurrentPlayer().getNom();
        gameLogEntries.add(new LogEntry(playerName, message));
    }

    private void showLogHistory() {
        // Create a table model for the log
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Player");
        model.addColumn("Action");

        // Add log entries to the table
        for (LogEntry entry : gameLogEntries) {
            model.addRow(new Object[]{entry.playerName, entry.action});
        }

        // Create the table
        JTable logTable = new JTable(model);
        logTable.setRowHeight(25);
        logTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        logTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create a scroll pane for the table
        AScrollPane scrollPane = new AScrollPane(logTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Show the dialog
        ADialog logDialog = new ADialog(this, "Game Log History", true);
        logDialog.setLayout(new BorderLayout());
        logDialog.add(scrollPane, BorderLayout.CENTER);

        NButton closeButton = new NButton("Close");
        closeButton.addActionListener(_ -> logDialog.dispose());
        APanel buttonPanel = new APanel();
        buttonPanel.add(closeButton);
        logDialog.add(buttonPanel, BorderLayout.SOUTH);

        logDialog.pack();
        logDialog.setLocationRelativeTo(this);
        logDialog.setVisible(true);
    }

    public Point getTopCardPanelCenter() {
        // Obtenir la position du centre du panel de la carte du dessus
        Point p = topCardPanel.getLocationOnScreen();
        return new Point(
                p.x + topCardPanel.getWidth() / 2,
                p.y + topCardPanel.getHeight() / 2
        );
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
        gameLogEntries.clear();
        initializeUI();
        updateGameState();
    }

    private static class LogEntry {
        String playerName;
        String action;

        public LogEntry(String playerName, String action) {
            this.playerName = playerName;
            this.action = action;
        }
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