package Unostart;

import projetpoo.*;
import javax.swing.*;
import NSwing.*;
import java.awt.*;

public class DeckPanel extends APanel {
    private final Deck deck;
    private Image backgroundImage;
    private final ALabel cardsLabel;

    public DeckPanel(Deck deck) {
        this.deck = deck;

        // Set up panel properties
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(133, 16, 16));

        // Create card image panel
        APanel imagePanel = new APanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the UNO background image
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback if image is not found
                    g.setColor(new Color(220, 0, 0));
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Color.WHITE);
                    g.drawString("UNO", getWidth()/2 - 15, getHeight()/2);
                }
            }
        };

        imagePanel.setPreferredSize(new Dimension(120, 180));
        imagePanel.setMaximumSize(new Dimension(120, 180));
        imagePanel.setBackground(new Color(133, 16, 16));

        // Load UNO background image
        try {
            java.net.URL imageUrl = getClass().getClassLoader().getResource("images/uno_background.png");
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                backgroundImage = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
            } else {
                System.err.println("UNO background image not found");
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Error loading UNO background image");
            e.printStackTrace();
            backgroundImage = null;
        }

        // Create label for card count
        cardsLabel = new ALabel("Cards: " + deck.size());
        cardsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardsLabel.setForeground(Color.WHITE);
        cardsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cardsLabel.setOpaque(true);
        cardsLabel.setBackground(new Color(133, 16, 16));
        cardsLabel.setPreferredSize(new Dimension(120, 25));
        cardsLabel.setMaximumSize(new Dimension(120, 25));

        // Add components to panel
        add(Box.createVerticalStrut(5)); // Top margin
        add(imagePanel);
        add(Box.createVerticalStrut(5)); // Space between image and label
        add(cardsLabel);
        add(Box.createVerticalStrut(5)); // Bottom margin

        // Set overall panel dimensions
        setPreferredSize(new Dimension(120, 220));
    }

    public void updateCardCount() {
        cardsLabel.setText("Cards: " + deck.size());
    }
}