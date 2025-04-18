package Unostart;

import javax.swing.*;

import NSwing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ANamingPage extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(25, 25, 25);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = new Color(76, 175, 80);
    
    private int playerCount;
    private List<JTextField> playerNameFields;
    private List<String> playerNames;
    
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
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);
    }
    
    private void setupComponents() {
        // Main content panel with some padding
        APanel contentPanel = new APanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Title label
        JLabel titleLabel = new JLabel("Enter Player Names");
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
        
        // Panel for player name fields
        APanel fieldsPanel = new APanel();
        fieldsPanel.setLayout(new GridLayout(playerCount, 2, 10, 20));
        fieldsPanel.setOpaque(false);
        fieldsPanel.setMaximumSize(new Dimension(600, playerCount * 60));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create text fields for each player
        for (int i = 1; i <= playerCount; i++) {
            ALabel playerLabel = new ALabel("Player " + i + ":");
            playerLabel.setForeground(TEXT_COLOR);
            playerLabel.setFont(new Font("Arial", Font.BOLD, 20));
            
            JTextField nameField = new JTextField("Player " + i);
            nameField.setFont(new Font("Arial", Font.PLAIN, 20));
            nameField.setPreferredSize(new Dimension(200, 40));
            
            playerNameFields.add(nameField);
            fieldsPanel.add(playerLabel);
            fieldsPanel.add(nameField);
        }
        
        contentPanel.add(fieldsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Create start button
        NButton startButton = new NButton("Start Game");
        startButton.setBackground(BUTTON_COLOR);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 50));
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectPlayerNames();
                startGame();
            }
        });
        
        contentPanel.add(startButton);
        
        // Add the content panel to the center of the frame
        APanel wrapperPanel = new APanel(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(contentPanel);
        
        getContentPane().add(wrapperPanel, BorderLayout.CENTER);
    }
    
    private void collectPlayerNames() {
        playerNames.clear();
        for (JTextField field : playerNameFields) {
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
        AOptionPane.showMessageDialog(this, 
            "Starting game with " + playerCount + " players:\n" + 
            String.join(", ", playerNames),
            "Game Starting", 
            AOptionPane.INFORMATION_MESSAGE);
        
        // Close this window and open the game window
        // this.dispose();
        // new GameWindow(playerNames);
    }
    
    // Method to get the list of player names
    public List<String> getPlayerNames() {
        return playerNames;
    }
}