package Unostart;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import NSwing.*;

public class Nchoixperso extends NFrame implements PlayerChoiceListener {
    private static final Color GREEN_BUTTON = new Color(76, 175, 80);
    private static final Color YELLOW_BUTTON = new Color(255, 193, 7);
    private static final Color RED_BUTTON = new Color(244, 67, 54);
    
    // Variable to store the selected player count
    private int selectedPlayerCount = 0;
    
    public Nchoixperso() {
        this.setTitle("Choose the Number of Players");
        this.setExtendedState(NFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(NFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Create background panel with image
        APanel backgroundPanel = new APanel() {
            private final Image backgroundImage = new ImageIcon("src/Images/fondchoixpersonnage.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        // Use BorderLayout for the main panel
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Create a panel to hold centered content
        APanel contentPanel = new APanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        // Create title label
        ALabel titleLabel = new ALabel("Select the number of players!");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Apply font
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
        
        // Add title to content panel
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 60))); // Vertical spacing
        
        // Create panel for diamond buttons
        APanel buttonPanel = new APanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20)); // Center with horizontal spacing
        buttonPanel.setOpaque(false);
        
        // Create the diamond buttons
        DiamondButton twoPlayersButton = new DiamondButton(GREEN_BUTTON, 2);
        DiamondButton threePlayersButton = new DiamondButton(YELLOW_BUTTON, 3);
        DiamondButton fourPlayersButton = new DiamondButton(RED_BUTTON, 4);

        // Set listeners
        twoPlayersButton.setPlayerChoiceListener(this);
        threePlayersButton.setPlayerChoiceListener(this);
        fourPlayersButton.setPlayerChoiceListener(this);

        // Set button sizes
        Dimension buttonSize = new Dimension(140, 140);
        twoPlayersButton.setPreferredSize(buttonSize);
        threePlayersButton.setPreferredSize(buttonSize);
        fourPlayersButton.setPreferredSize(buttonSize);

        // Add buttons to panel
        buttonPanel.add(twoPlayersButton);
        buttonPanel.add(threePlayersButton);
        buttonPanel.add(fourPlayersButton);
        
        // Add button panel to content panel
        contentPanel.add(buttonPanel);
        
        // Center the content panel vertically
        APanel wrapperPanel = new APanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(contentPanel);
        
        // Add wrapper panel to background panel
        backgroundPanel.add(wrapperPanel, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    // Implement the method from PlayerChoiceListener interface
    @Override
    public void onPlayerCountSelected(int playerCount) {
        this.selectedPlayerCount = playerCount;
        System.out.println("Player count selected in Nchoixperso: " + playerCount);
        
        // Close this window and open the player naming page
        this.dispose();
        new ANamingPage(playerCount);
    }
    
    // Method to get the selected player count
    public int getSelectedPlayerCount() {
        return selectedPlayerCount;
    }
}