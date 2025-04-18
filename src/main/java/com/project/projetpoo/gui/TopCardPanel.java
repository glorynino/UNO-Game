package com.project.projetpoo.gui;

import com.project.projetpoo.Card;
import com.project.projetpoo.*;

import javax.swing.*;
import java.awt.*;

public class TopCardPanel extends JPanel {
    private final JLabel topCardLabel;

    public TopCardPanel(Card topCard) {
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(100, 150));
        topCardLabel = new JLabel();
        add(topCardLabel);
        setTopCard(topCard);
    }

    public void setTopCard(Card topCard) {
        if (topCard != null) {
            ImageIcon icon = loadCardImage(topCard);
            if (icon != null) {
                topCardLabel.setIcon(icon);
            } else {
                topCardLabel.setText(topCard.toString()); // Fallback to text if image fails
            }
        } else {
            topCardLabel.setText("No card"); // Handle null case
        }
    }

    private ImageIcon loadCardImage(Card card) {
        String imagePath = getCardImagePath(card);
        try {
            java.net.URL imageUrl = getClass().getClassLoader().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image image = icon.getImage();
                int width = 80;  // Desired width
                int height = 120; // Desired height
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