package Unostart;

import UnoSwing.ALabel;
import UnoSwing.APanel;
import projetpoo.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TopCardPanel extends APanel {
    private final ALabel topCardLabel;
    private Card currentCard;
    private final Map<String, ImageIcon> cardImages = new HashMap<>();
    private final Map<String, ImageIcon> colorImages = new HashMap<>();

    public TopCardPanel(Card topCard) {
        // Panel setup
        setBackground(new Color(136, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(150, 200));
        setLayout(new BorderLayout());

        // Create card container with shadow effect
        APanel cardContainer = new APanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, 15, 15);
            }
        };
        cardContainer.setBackground(new Color(136, 8, 8));

        // Create the label for displaying the card
        topCardLabel = new ALabel();
        topCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topCardLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Add components
        cardContainer.add(topCardLabel, BorderLayout.CENTER);
        add(cardContainer, BorderLayout.CENTER);

        // Preload color images
        preloadColorImages();

        // Set initial top card
        setTopCard(topCard);
    }

    private void preloadColorImages() {
        final String[] colors = {"red", "green", "blue", "yellow"};
        for (String color : colors) {
            String imagePath = "images/" + color + "_empty.png";
            ImageIcon icon = loadImage(imagePath, 100, 150);
            if (icon != null) {
                colorImages.put(color, icon);
                System.out.println("Preloaded color image: " + color);
            }
        }
    }

    public void setTopCard(Card card) {
        this.currentCard = card;
        updateCardDisplay();
    }

    public void setTopCardColorImage(String color) {
        if (color == null || color.isEmpty()) {
            System.out.println("Cannot set top card color: color is null or empty");
            return;
        }

        color = color.toLowerCase();
        ImageIcon colorIcon = colorImages.get(color);

        if (colorIcon != null) {
            System.out.println("Setting top card to color: " + color);
            topCardLabel.setIcon(colorIcon);
            revalidate();
            repaint();
        } else {
            System.out.println("Color image not found in cache for: " + color + ", trying to load it directly");

            // Try to load it on demand
            String imagePath = "images/" + color + "_empty.png";
            ImageIcon icon = loadImage(imagePath, 100, 150);

            if (icon != null) {
                colorImages.put(color, icon); // Cache it for future use
                topCardLabel.setIcon(icon);
                revalidate();
                repaint();
                System.out.println("Successfully loaded and set color image: " + color);
            } else {
                System.err.println("Failed to load color image: " + color);
            }
        }
    }

    private void updateCardDisplay() {
        if (currentCard == null) {
            topCardLabel.setIcon(null);
            return;
        }

        String imagePath = getCardImagePath(currentCard);
        String cacheKey = imagePath;

        // Check if we already have this image cached
        ImageIcon cardIcon = cardImages.get(cacheKey);

        if (cardIcon == null) {
            // Not in cache, load it
            cardIcon = loadImage(imagePath, 100, 150);
            if (cardIcon != null) {
                cardImages.put(cacheKey, cardIcon);
            }
        }

        if (cardIcon != null) {
            topCardLabel.setIcon(cardIcon);
        } else {
            System.err.println("Failed to load card image: " + imagePath);
            topCardLabel.setText(currentCard.toString());
        }

        revalidate();
        repaint();
    }

    private ImageIcon loadImage(String imagePath, int width, int height) {
        try {
            java.net.URL imageUrl = getClass().getClassLoader().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            } else {
                System.err.println("Image not found: " + imagePath);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }

    private String getCardImagePath(Card card) {
        if (card instanceof WildCard) {
            if (card.getCouleur() != null && !card.getCouleur().isEmpty()) {
                // If the wild card has a color assigned, use the empty color card
                return "images/" + card.getCouleur().toLowerCase() + "_empty.png";
            }
            return "images/wild.png";
        } else if (card instanceof WildDrawFourCard) {
            if (card.getCouleur() != null && !card.getCouleur().isEmpty()) {
                // If the wild draw four card has a color assigned, use the empty color card
                return "images/" + card.getCouleur().toLowerCase() + "_empty.png";
            }
            return "images/wild_draw_four.png";
        } else if (card instanceof Skip) {
            return "images/skip_" + card.getCouleur().toLowerCase() + ".png";
        } else if (card instanceof Reverse) {
            return "images/reverse_" + card.getCouleur().toLowerCase() + ".png";
        } else if (card instanceof Drawtwo) {
            return "images/draw_two_" + card.getCouleur().toLowerCase() + ".png";
        } else {
            return "images/" + card.getCouleur().toLowerCase() + "_" + card.getSymbol() + ".png";
        }
    }
}