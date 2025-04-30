package Unostart;

import UnoSwing.*;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ANamingPage extends NFrame {
    private static final Color BACKGROUND_COLOR = new Color(25, 25, 25);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = new Color(76, 175, 80);
    private static final Color BACK_BUTTON_COLOR = new Color(244, 67, 54); // Red color for back button

    private final int playerCount;
    private final List<ATextField> playerNameFields;
    private final List<String> playerNames;

    public ANamingPage(int playerCount) {
        this.playerCount = playerCount;
        this.playerNameFields = new ArrayList<>();
        this.playerNames = new ArrayList<>();

        setupFrame();
        setupComponents();


        this.setVisible(true);
    }

    private void setupFrame() {
        this.setTitle("Name Your Players");
        this.setExtendedState(NFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(NFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Create the background panel
        APanel backgroundPanel = new APanel() {
            private final Image backgroundImage = new ImageIcon("src/Images/fondchoixpersonnage.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);
    }

    private void setupComponents() {
        // Main content panel
        APanel contentPanel = new APanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        // Title label
        ALabel titleLabel = new ALabel("Enter Player Names");
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Apply custom font if available
        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/Orbitron-VariableFont_wght.ttf");
            if (is != null) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(customFont);
                titleLabel.setFont(customFont);
            } else {
                titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
                System.err.println("Custom font not found!");
            }
        } catch (Exception e) {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
            e.printStackTrace();
        }

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Create a fixed-width panel for the player name fields
        APanel fieldsContainer = new APanel();
        fieldsContainer.setLayout(new BoxLayout(fieldsContainer, BoxLayout.Y_AXIS));
        fieldsContainer.setOpaque(false);
        fieldsContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsContainer.setMaximumSize(new Dimension(600, playerCount * 70));

        // Create text fields for each player
        for (int i = 1; i <= playerCount; i++) {
            APanel playerRow = new APanel(new BorderLayout(10, 0));
            playerRow.setOpaque(false);

            ALabel playerLabel = new ALabel("Player " + i + ":");
            playerLabel.setForeground(TEXT_COLOR);
            playerLabel.setFont(new Font("Arial", Font.BOLD, 20));
            playerLabel.setPreferredSize(new Dimension(100, 40));

            ATextField nameField = new ATextField("Player " + i);
            nameField.setFont(new Font("Arial", Font.PLAIN, 20));

            final int currentIndex = i - 1;
            nameField.addActionListener(_ -> {
                if (currentIndex + 1 < playerNameFields.size()) {
                    playerNameFields.get(currentIndex + 1).requestFocusInWindow();
                } else {
                    // Optionnel : lancer le jeu si c’est le dernier champ
                    collectPlayerNames();
                    startGame();
                }
            });

            playerRow.add(playerLabel, BorderLayout.WEST);
            playerRow.add(nameField, BorderLayout.CENTER);

            playerNameFields.add(nameField);
            fieldsContainer.add(playerRow);
            fieldsContainer.add(Box.createRigidArea(new Dimension(0, 15))); // Space between rows
        }

        contentPanel.add(fieldsContainer);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Create a centered panel for buttons
        APanel buttonContainer = new APanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setOpaque(false);
        buttonContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonContainer.setMaximumSize(new Dimension(400, 60));

        // Create back button
        NButton backButton = new NButton("Back");
        backButton.setBackground(BACK_BUTTON_COLOR);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        backButton.addActionListener(_ -> returnToPlayerSelection());

        // Create start button
        NButton startButton = new NButton("Start Game");
        startButton.setBackground(BUTTON_COLOR);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);

        startButton.addActionListener(_ -> {
            collectPlayerNames();
            startGame();
        });

        // Add buttons to button panel with proper spacing
        buttonContainer.add(backButton);
        buttonContainer.add(Box.createHorizontalGlue()); // Space between buttons
        buttonContainer.add(startButton);

        contentPanel.add(buttonContainer);

        // Add the content panel to the center of the frame
        getContentPane().add(contentPanel);
    }


    private void collectPlayerNames() {
        playerNames.clear();
        for (ATextField field : playerNameFields) {
            String name = field.getText().trim();
            // If empty, use a default name
            if (name.isEmpty()) {
                name = "Player " + (playerNames.size() + 1);
            }
            playerNames.add(name);
        }

        // Print collected names (for testing)
        System.out.println("Player names collected:");
        for (int i = 0; i < playerNames.size(); i++) {
            System.out.println("Player " + (i + 1) + ": " + playerNames.get(i));
        }
    }

    private void startGame() {
        // Here you would start the actual game with the collected player names

        this.dispose();

        UNOGUI gui = new UNOGUI(playerCount, getPlayerNames());  // Crée l'objet UNOGUI
        gui.setVisible(true);  // Affiche la fenêtre
    }

    private void returnToPlayerSelection() {
        // Close this window and reopen the player selection window
        this.dispose();
        new Nchoixperso();
    }

    // Method to get the list of player names
    public List<String> getPlayerNames() {
        return playerNames;
    }
}