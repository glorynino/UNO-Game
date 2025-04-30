package Unostart;

import NSwing.ALabel;
import NSwing.APanel;
import projetpoo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TopCardPanel extends APanel {
    private final ALabel topCardLabel;
    private Card currentCard;
    private final Map<String, ImageIcon> colorIcons = new HashMap<>(); // Store color images
    private ImageIcon currentTopCardIcon;

    public TopCardPanel(Card topCard) {
        // Amélioration du style pour le panel de carte
        setBackground(new Color(0, 100, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(150, 200));
        setLayout(new BorderLayout());
        loadColorImages();
        // Ajout d'un effet d'ombre à la carte
        APanel cardPanel = new APanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Dessiner une ombre légère
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, 15, 15);
            }
        };
        cardPanel.setBackground(new Color(0, 100, 0));
        cardPanel.add(topCardLabel = new ALabel(), BorderLayout.CENTER);
        add(cardPanel, BorderLayout.CENTER);

        setTopCard(topCard);
    }

    private void loadColorImages() {
        final String[] colors = {"red", "green", "blue", "yellow"};
        for (String color : colors) {
            colorIcons.put(color, loadCardImage("images/" + color + "_empty.png"));
        }
    }

    public void setTopCard(Card card) {
        currentCard = card;
        updateTopCardImage();
    }

    public void setTopCardColorImage(String color) {
        System.out.println("setTopCardColorImage called with color: " + color);
        ImageIcon colorIcon = colorIcons.get(color);
        if (colorIcon != null) {
            System.out.println("  Found colorIcon for " + color);
            currentTopCardIcon = colorIcon;
            topCardLabel.setIcon(currentTopCardIcon);
            System.out.println("  Updated topCardLabel icon");
        } else {
            System.out.println("  No colorIcon found for " + color);
        }
    }

    private void updateTopCardImage() {
        String imagePath = getCardImagePath(currentCard);
        currentTopCardIcon = loadCardImage(imagePath);
        if (currentTopCardIcon != null) {
            topCardLabel.setIcon(currentTopCardIcon);
        } else {
            // Handle the case where the image failed to load.
            // You might want to display a default image or log an error.
            System.err.println("Failed to load image for: " + currentCard.toString());
            topCardLabel.setText("Image Not Found"); // Or set a default image
        }
        repaint();
    }

    private ImageIcon loadCardImage(String imagePath) {
        try {
            java.net.URL imageUrl = getClass().getClassLoader().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image image = icon.getImage();
                int width = 100;  // Desired width
                int height = 150; // Desired height
                Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
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
            return "images/wild.png";
        } else if (card instanceof WildDrawFourCard) {
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