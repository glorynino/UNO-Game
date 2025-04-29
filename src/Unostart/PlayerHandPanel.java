package Unostart;

import projetpoo.*;

import javax.swing.*;
import NSwing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerHandPanel extends APanel {
    private Player player;
    private final Gamegui game;
    private final UNOGUI gui;
    private APanel cardsPanel;
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;
    private static final int CARD_OVERLAP = 30; // Cartes se chevauchant pour économiser de l'espace

    public PlayerHandPanel(Player player, Gamegui game, UNOGUI gui) {
        this.player = player;
        this.game = game;
        this.gui = gui;
        setLayout(new BorderLayout());
        initializeComponents();
        updateHand();
    }

    private void initializeComponents() {
        // Panel pour les cartes avec un layout personnalisé
        cardsPanel = new APanel() {
            @Override
            public Dimension getPreferredSize() {
                int cardCount = player.getHand().size();
                if (cardCount == 0) return new Dimension(0, CARD_HEIGHT);
                return new Dimension(CARD_WIDTH + (cardCount - 1) * (CARD_WIDTH - CARD_OVERLAP), CARD_HEIGHT);
            }
        };
        cardsPanel.setLayout(null); // Layout null pour positionner manuellement
        cardsPanel.setBackground(Color.WHITE);

        AScrollPane scrollPane = new AScrollPane(cardsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(800, 150));

        NButton drawCardButton = new NButton("Draw Card");
        drawCardButton.setFont(new Font("Arial", Font.BOLD, 14));
        drawCardButton.addActionListener(_ -> {
            game.getDeck().drawCard(player, 1);
            gui.logMessage(player.getNom() + " draws a card.");
            gui.updateGameState();
        });

        add(scrollPane, BorderLayout.CENTER);
        add(drawCardButton, BorderLayout.SOUTH);
    }

    public void updateHand() {
        cardsPanel.removeAll();

        int cardCount = player.getHand().size();
        int xPos = 0;

        for (int i = 0; i < cardCount; i++) {
            Card card = player.getHand().get(i);
            NButton cardButton = createCardButton(card);

            // Positionner chaque carte avec chevauchement
            cardButton.setBounds(xPos, 0, CARD_WIDTH, CARD_HEIGHT);
            cardsPanel.add(cardButton);

            xPos += CARD_WIDTH - CARD_OVERLAP;
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private NButton createCardButton(Card card) {
        ImageIcon icon = loadCardImage(card);
        NButton cardButton = new NButton(icon);
        cardButton.setBorderPainted(false);
        cardButton.setContentAreaFilled(false);
        cardButton.setFocusPainted(false);

        // Ajouter des effets de survol
        cardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Animation d'élévation de la carte au survol
                cardButton.setBounds(cardButton.getX(), -15, CARD_WIDTH, CARD_HEIGHT);
                cardButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Retour à la position normale
                cardButton.setBounds(cardButton.getX(), 0, CARD_WIDTH, CARD_HEIGHT);
                cardButton.repaint();
            }
        });

        cardButton.addActionListener(_ -> {
            if (game.isCardPlayable(card, game.getTopCard())) {
                // Animation de la carte jouée
                animateCardPlay(cardButton, card);
            } else {
                AOptionPane.showMessageDialog(PlayerHandPanel.this,
                        "You cannot play this card!", "Invalid Move", AOptionPane.WARNING_MESSAGE);
            }
        });

        return cardButton;
    }

    private void animateCardPlay(NButton cardButton, Card card) {
        // Créer un Timer pour animer la carte
        Timer timer = new Timer(10, null);
        final int[] step = {0};
        final int maxSteps = 20;
        final int buttonX = cardButton.getX();
        final int buttonY = cardButton.getY();

        // Calculer la position centrale où la carte doit se déplacer
        Point cardsPanelLocation = cardsPanel.getLocationOnScreen();
        Point targetLocation = gui.getTopCardPanelCenter();
        int targetX = targetLocation.x - cardsPanelLocation.x;
        int targetY = targetLocation.y - cardsPanelLocation.y;

        timer.addActionListener(_ -> {
            step[0]++;

            if (step[0] <= maxSteps) {
                // Déplacer la carte progressivement vers le centre
                int newX = buttonX + (targetX - buttonX) * step[0] / maxSteps;
                int newY = buttonY + (targetY - buttonY) * step[0] / maxSteps;

                cardButton.setBounds(newX, newY, CARD_WIDTH, CARD_HEIGHT);
                cardsPanel.repaint();
            } else {
                // Animation terminée, jouer la carte
                timer.stop();
                handleSpecialCardEffect(card);
                game.playCard(player, card);
                gui.logMessage(player.getNom() + " plays " + getCardDisplayName(card));

                if (player.getHand().isEmpty()) {
                    gui.checkGameOver();
                }
                gui.updateGameState();
            }
        });

        timer.start();
    }

    private ImageIcon loadCardImage(Card card) {
        String imagePath = getCardImagePath(card); // Get the image path for the card
        try {
            // Use ClassLoader to load the image from the resources folder
            java.net.URL imageUrl = getClass().getClassLoader().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image image = icon.getImage();

                // Resize the image to fit the card dimensions
                Image resizedImage = image.getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);

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

    private void handleSpecialCardEffect(Card card) {
        if (card instanceof WildCard) {
            handleWildCard((WildCard) card);
        } else if (card instanceof WildDrawFourCard) {
            handleWildDrawFour((WildDrawFourCard) card);
        } else if (card instanceof Skip) {
            handleSkipCard();
        } else if (card instanceof Reverse) {
            handleReverseCard();
        } else if (card instanceof Drawtwo) {
            handleDrawTwoCard();
        }
    }

    private void handleWildCard(WildCard card) {
        String color = AOptionPane.showInputDialog(this, "Choose a color (red, green, blue, yellow):");
        if (color != null && (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("green") ||
                color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("yellow"))) {
            // Update the card's color BEFORE playing it
            card.setCouleur(color); // Update the card being played
            gui.logMessage(player.getNom() + " chose " + color + " for the Wild Card.");
        } else {
            AOptionPane.showMessageDialog(this, "Invalid color! Please choose again.", "Error", AOptionPane.ERROR_MESSAGE);
            handleWildCard(card); // Retry
        }
    }

    private void handleWildDrawFour(WildDrawFourCard card) {
        String color = AOptionPane.showInputDialog(this, "Choose a color (red, green, blue, yellow):");
        if (color != null && (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("green") ||
                color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("yellow"))) {
            // Update the card's color BEFORE playing it
            card.setCouleur(color); // Update the card being played
            Player nextPlayer = game.getNextPlayer();
            game.getDeck().drawCard(nextPlayer, 4);
            gui.logMessage(nextPlayer.getNom() + " draws 4 cards!");
            gui.logMessage(player.getNom() + " chose " + color + " for the Wild Draw Four Card.");
        } else {
            AOptionPane.showMessageDialog(this, "Invalid color! Please choose again.", "Error", JOptionPane.ERROR_MESSAGE);
            handleWildDrawFour(card); // Retry
        }
    }

    private void handleSkipCard() {
        gui.logMessage("Next player is skipped!");
        game.moveToNextPlayer();
    }

    private void handleReverseCard() {
        gui.logMessage("Turn order reversed!");
        game.setClockwise(!game.isClockwise());
    }

    private void handleDrawTwoCard() {
        Player nextPlayer = game.getNextPlayer();
        game.getDeck().drawCard(nextPlayer, 2);
        gui.logMessage(nextPlayer.getNom() + " draws 2 cards!");
    }

    // Helper method to get a user-friendly display name for cards
    private String getCardDisplayName(Card card) {
        if (card instanceof WildCard) {
            return "Wild";
        } else if (card instanceof WildDrawFourCard) {
            return "Wild Draw Four";
        } else if (card instanceof Skip) {
            return "Skip (" + card.getCouleur() + ")";
        } else if (card instanceof Reverse) {
            return "Reverse (" + card.getCouleur() + ")";
        } else if (card instanceof Drawtwo) {
            return "Draw Two (" + card.getCouleur() + ")";
        } else {
            return card.getSymbol() + " (" + card.getCouleur() + ")";
        }
    }

    public void setPlayer(Player player) {
        this.player = player; // Update the player
        updateHand(); // Refresh the hand display
    }
}