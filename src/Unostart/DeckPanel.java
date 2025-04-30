package Unostart;

import projetpoo.*;
import javax.swing.*;
import NSwing.*;
import java.awt.*;

public class DeckPanel extends APanel {
    private final Deck deck;
    private Image backgroundImage;
    private final ALabel cardsLabel; // Declare cardsLabel as a class member

    public DeckPanel(Deck deck) {
        this.deck = deck;
        setPreferredSize(new Dimension(120, 180));
        setBackground(new Color(0, 100, 0));
        setLayout(new BorderLayout());

        // Load UNO background image
        ImageIcon icon = null;
        try {
            java.net.URL imageUrl = getClass().getClassLoader().getResource("images/uno_background.png");
            if (imageUrl != null) {
                icon = new ImageIcon(imageUrl);
                Image image = icon.getImage();
                backgroundImage = image.getScaledInstance(120, 180, Image.SCALE_SMOOTH);
            } else {
                System.err.println("UNO background image not found");
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Error loading UNO background image");
            e.printStackTrace();
            backgroundImage = null;
        }

        // Add label showing number of cards in deck
        cardsLabel = new ALabel("Cards: " + deck.size());
        cardsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardsLabel.setForeground(Color.BLACK);
        cardsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        add(cardsLabel, BorderLayout.SOUTH);
    }

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

    public void updateCardCount() {
        if (cardsLabel != null) {
            cardsLabel.setText("Cards: " + deck.size());
        }
    }
}