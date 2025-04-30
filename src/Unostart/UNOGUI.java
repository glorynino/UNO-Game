
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
    TopCardPanel topCardPanel;
    private DeckPanel deckPanel;
    private List<PlayerHandPanel> playerHandPanels;
    private ALabel currentPlayerLabel;
    private final List<LogEntry> gameLogEntries;
    private final List<String> nomplayers;
    private final int numberplayer;

    public UNOGUI(int numberplayer, List<String> nom) {
        setTitle("UNO Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setExtendedState(NFrame.MAXIMIZED_BOTH);

        this.nomplayers = nom;
        this.numberplayer = numberplayer;
        game = new Gamegui(numberplayer, nom);
        gameLogEntries = new ArrayList<>();
        playerHandPanels = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        APanel mainPanel = new APanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 0));

        APanel topPanel = new APanel(new BorderLayout());
        APanel playerInfoPanel = new APanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        currentPlayerLabel = new ALabel("Current Player: " + game.getCurrentPlayer().getNom(), SwingConstants.CENTER);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerInfoPanel.add(currentPlayerLabel);

        topPanel.add(playerInfoPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        APanel centerPanel = new APanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(136, 115, 0));

        APanel gameBoardPanel = new APanel();
        gameBoardPanel.setLayout(new BorderLayout());
        gameBoardPanel.setBackground(new Color(133, 16, 16));

        // Center game area with GridBagLayout for true centering
        APanel centerGameArea = new APanel(new GridBagLayout());
        centerGameArea.setBackground(new Color(133, 16, 16));

        // Create a panel specifically for the cards
        APanel cardsPanel = new APanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        cardsPanel.setBackground(new Color(133, 16, 16));

        topCardPanel = new TopCardPanel(game.getTopCard());
        deckPanel = new DeckPanel(game.getDeck());

        cardsPanel.add(topCardPanel);
        cardsPanel.add(deckPanel);

        // Add the cards panel to the center game area
        centerGameArea.add(cardsPanel);

        // Add log history button below the cards
        APanel buttonPanel = new APanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(133, 16, 16));

        NButton logHistoryButton = new NButton("Log History");
        logHistoryButton.setFont(new Font("Arial", Font.BOLD, 14));
        logHistoryButton.addActionListener(_ -> showLogHistory());

        buttonPanel.add(logHistoryButton);

        // Add the main components to the game board
        gameBoardPanel.add(centerGameArea, BorderLayout.CENTER);
        gameBoardPanel.add(buttonPanel, BorderLayout.SOUTH);

        createPlayerHandPanels();

        // Configure player hand layouts based on number of players
        switch (numberplayer) {
            case 2:
                gameBoardPanel.add(playerHandPanels.get(0), BorderLayout.SOUTH);
                gameBoardPanel.add(playerHandPanels.get(1), BorderLayout.NORTH);
                break;
            case 3:
                // Remove the buttonPanel from CENTER_SOUTH for 3 players
                gameBoardPanel.remove(buttonPanel);

                // Add the button panel to the main player's panel
                APanel mainPlayerPanel = new APanel(new BorderLayout());
                mainPlayerPanel.add(playerHandPanels.get(0), BorderLayout.CENTER);
                mainPlayerPanel.add(buttonPanel, BorderLayout.NORTH);
                gameBoardPanel.add(mainPlayerPanel, BorderLayout.SOUTH);

                // Panel for the vertical player hand on the West side
                APanel verticalWestPanel = new APanel(new BorderLayout());
                verticalWestPanel.add(playerHandPanels.get(1), BorderLayout.CENTER);
                gameBoardPanel.add(verticalWestPanel, BorderLayout.WEST);

                // Panel for the vertical player hand on the East side
                APanel verticalEastPanel = new APanel(new BorderLayout());
                verticalEastPanel.add(playerHandPanels.get(2), BorderLayout.CENTER);
                gameBoardPanel.add(verticalEastPanel, BorderLayout.EAST);
                break;
            case 4:
                // Remove the buttonPanel from CENTER_SOUTH for 4 players
                gameBoardPanel.remove(buttonPanel);

                // Add the button panel to the main player's panel
                APanel mainPlayerPanel4 = new APanel(new BorderLayout());
                mainPlayerPanel4.add(playerHandPanels.get(0), BorderLayout.CENTER);
                mainPlayerPanel4.add(buttonPanel, BorderLayout.NORTH);
                gameBoardPanel.add(mainPlayerPanel4, BorderLayout.SOUTH);

                // Create a panel for the left (West) player
                APanel verticalWestPanel4 = new APanel(new BorderLayout());
                verticalWestPanel4.add(playerHandPanels.get(1), BorderLayout.CENTER);
                gameBoardPanel.add(verticalWestPanel4, BorderLayout.WEST);

                // Create a panel for the top (North) player
                gameBoardPanel.add(playerHandPanels.get(2), BorderLayout.NORTH);

                // Create a panel for the right (East) player
                APanel verticalEastPanel4 = new APanel(new BorderLayout());
                verticalEastPanel4.add(playerHandPanels.get(3), BorderLayout.CENTER);
                gameBoardPanel.add(verticalEastPanel4, BorderLayout.EAST);
                break;
        }

        centerPanel.add(gameBoardPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void createPlayerHandPanels() {
        playerHandPanels.clear();

        PlayerNode currentNode = game.getPlayersList().getFirstNode();
        if (currentNode != null) {
            int i = 0;
            do {
                Player player = currentNode.getPlayer();
                boolean isCurrentPlayer = player == game.getCurrentPlayer();

                // Determine if the player hand should be displayed vertically
                boolean isVertical = false;
                if (numberplayer == 3) {
                    // For 3 players, make positions 1 and 2 vertical (side panels)
                    isVertical = (i == 1 || i == 2);
                } else if (numberplayer == 4) {
                    // For 4 players, make positions 1 and 3 vertical (side panels)
                    isVertical = (i == 1 || i == 3);
                }

                PlayerHandPanel handPanel = new PlayerHandPanel(player, game, this, isCurrentPlayer, isVertical);
                playerHandPanels.add(handPanel);

                currentNode = currentNode.getNext();
                i++;
            } while (currentNode != game.getPlayersList().getFirstNode());
        }
    }

    public void updateGameState() {
        topCardPanel.setTopCard(game.getTopCard());

        currentPlayerLabel.setText("Current Player: " + game.getCurrentPlayer().getNom());

        PlayerNode currentNode = game.getPlayersList().getFirstNode();
        if (currentNode != null) {
            int i = 0;
            do {
                Player player = currentNode.getPlayer();
                boolean isCurrentPlayer = player == game.getCurrentPlayer();

                playerHandPanels.get(i).setPlayer(player);
                playerHandPanels.get(i).setIsCurrentPlayer(isCurrentPlayer);
                playerHandPanels.get(i).updateHand();

                currentNode = currentNode.getNext();
                i++;
            } while (currentNode != game.getPlayersList().getFirstNode());
        }

        checkGameOver();
    }

    public void logMessage(String message) {
        String playerName = game.getCurrentPlayer().getNom();
        gameLogEntries.add(new LogEntry(playerName, message));
    }

    private void showLogHistory() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Player");
        model.addColumn("Action");

        for (LogEntry entry : gameLogEntries) {
            model.addRow(new Object[]{entry.playerName, entry.action});
        }

        JTable logTable = new JTable(model);
        logTable.setRowHeight(25);
        logTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        logTable.setFont(new Font("Arial", Font.PLAIN, 14));

        AScrollPane scrollPane = new AScrollPane(logTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

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
        playerHandPanels.clear();
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
        UNOGUI gui = new UNOGUI(n, nom);
        gui.setVisible(true);
        System.out.println("Début de l'application");
    }

    public void updateDeckCardCount() {
        deckPanel.updateCardCount();
    }
}