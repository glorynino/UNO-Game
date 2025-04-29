package Unostart;

import NSwing.ALabel;
import NSwing.APanel;
import projetpoo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TopCardPanel extends APanel {
    private final ALabel topCardLabel;
    private Card currentCard;

    public TopCardPanel(Card topCard) {
        // Amélioration du style pour le panel de carte
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(150, 200));
        setLayout(new BorderLayout());

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
        cardPanel.setBackground(new Color(240, 240, 240));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        topCardLabel = new ALabel();
        topCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardPanel.add(topCardLabel, BorderLayout.CENTER);

        add(cardPanel, BorderLayout.CENTER);

        // Ajout d'un titre pour clarifier que c'est la carte du dessus
        ALabel titleLabel = new ALabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        setTopCard(topCard);
    }

    public void setTopCard(Card topCard) {
        if (topCard != null) {
            currentCard = topCard;

            // Animation de la nouvelle carte
            animateNewCard();
        } else {
            topCardLabel.setText("No card");
        }
    }

    private void animateNewCard() {
        ImageIcon icon = loadCardImage(currentCard);
        if (icon != null) {
            // Effets spéciaux selon le type de carte
            if (currentCard instanceof Reverse) {
                animateRotation(icon);
            } else if (currentCard instanceof Skip) {
                animateFlash(icon);
            } else if (currentCard instanceof Drawtwo || currentCard instanceof WildDrawFourCard) {
                animateGrow(icon);
            } else {
                // Animation standard pour les cartes normales
                topCardLabel.setIcon(icon);
            }
        } else {
            topCardLabel.setText(currentCard.toString());
        }
    }

    private void animateRotation(ImageIcon icon) {
        final Timer[] timer = {null};
        final int[] angle = {0};

        timer[0] = new Timer(20, e -> {
            angle[0] += 10;

            if (angle[0] <= 360) {
                // Rotation de l'image
                ImageIcon rotated = rotateIcon(icon, angle[0]);
                topCardLabel.setIcon(rotated);
            } else {
                timer[0].stop();
                topCardLabel.setIcon(icon); // Restaurer l'image normale
            }
        });

        timer[0].start();
    }

    private void animateFlash(ImageIcon icon) {
        final Timer[] timer = {null};
        final int[] step = {0};

        timer[0] = new Timer(100, e -> {
            step[0]++;

            if (step[0] <= 6) {
                if (step[0] % 2 == 0) {
                    topCardLabel.setIcon(icon);
                } else {
                    topCardLabel.setIcon(null);
                }
            } else {
                timer[0].stop();
                topCardLabel.setIcon(icon);
            }
        });

        timer[0].start();
    }

    private void animateGrow(ImageIcon icon) {
        final Timer[] timer = {null};
        final float[] scale = {0.5f};

        timer[0] = new Timer(50, e -> {
            scale[0] += 0.1f;

            if (scale[0] <= 1.2f) {
                ImageIcon scaled = scaleIcon(icon, scale[0]);
                topCardLabel.setIcon(scaled);
            } else if (scale[0] <= 1.4f) {
                ImageIcon scaled = scaleIcon(icon, 1.4f - (scale[0] - 1.2f));
                topCardLabel.setIcon(scaled);
            } else {
                timer[0].stop();
                topCardLabel.setIcon(icon);
            }
        });

        timer[0].start();
    }

    private ImageIcon rotateIcon(ImageIcon icon, int angle) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage image = new BufferedImage(w, h, type);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.rotate(Math.toRadians(angle), w/2, h/2);
        g2.drawImage(icon.getImage(), 0, 0, null);
        g2.dispose();

        return new ImageIcon(image);
    }

    private ImageIcon scaleIcon(ImageIcon icon, float scale) {
        int w = (int)(icon.getIconWidth() * scale);
        int h = (int)(icon.getIconHeight() * scale);

        Image scaledImage = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private ImageIcon loadCardImage(Card card) {
        String imagePath = getCardImagePath(card);
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