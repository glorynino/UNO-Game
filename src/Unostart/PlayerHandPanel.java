package Unostart;

import projetpoo.*;

import javax.swing.*;
import UnoSwing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerHandPanel extends APanel {
    private Player player;
    private final Gamegui game;
    private final UNOGUI gui;
    private APanel cardsPanel;
    private boolean isCurrentPlayer;
    private ALabel playerNameLabel;
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;
    private static final int CARD_OVERLAP = 20;
    private ImageIcon backCardImage;
    private boolean isVertical = false;

    public PlayerHandPanel(Player player, Gamegui game, UNOGUI gui, boolean isCurrentPlayer) {
        this(player, game, gui, isCurrentPlayer, false);
    }

    public PlayerHandPanel(Player player, Gamegui game, UNOGUI gui, boolean isCurrentPlayer, boolean isVertical) {
        this.player = player;
        this.game = game;
        this.gui = gui;
        this.isCurrentPlayer = isCurrentPlayer;
        this.isVertical = isVertical;

        try {
            java.net.URL imageUrl = getClass().getClassLoader().getResource("images/uno_background.png");
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image image = originalIcon.getImage();
                Image resizedImage = image.getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
                backCardImage = new ImageIcon(resizedImage);
            } else {
                System.err.println("Back card image not found");
                backCardImage = null;
            }
        } catch (Exception e) {
            System.err.println("Error loading back card image");
            e.printStackTrace();
            backCardImage = null;
        }

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerNameLabel = new ALabel(player.getNom());
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        if (isCurrentPlayer) {
            playerNameLabel.setForeground(new Color(136, 8, 8));
            setBorder(BorderFactory.createLineBorder(new Color(136, 8, 8), 3));
        } else {
            playerNameLabel.setForeground(Color.BLACK);
        }

        add(playerNameLabel, BorderLayout.NORTH);

        initializeComponents();
        updateHand();
    }

    private void initializeComponents() {
        cardsPanel = new APanel() {
            @Override
            public Dimension getPreferredSize() {
                int cardCount = player.getHand().size();
                if (cardCount == 0) return new Dimension(0, CARD_HEIGHT);
                if (isVertical) {
                    return new Dimension(CARD_WIDTH + 50, CARD_HEIGHT + (cardCount - 1) * (CARD_HEIGHT - CARD_OVERLAP));
                } else {
                    return new Dimension(CARD_WIDTH + (cardCount - 1) * (CARD_WIDTH - CARD_OVERLAP), CARD_HEIGHT + 50);
                }
            }
        };
        cardsPanel.setLayout(null);
        cardsPanel.setBackground(new Color(200, 200, 200));

        AScrollPane scrollPane = new AScrollPane(cardsPanel);
        scrollPane.setHorizontalScrollBarPolicy(isVertical ? JScrollPane.HORIZONTAL_SCROLLBAR_NEVER : JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(isVertical ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED : JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(isVertical ? new Dimension(CARD_WIDTH + 10, 600) : new Dimension(600, CARD_HEIGHT + 10));

        APanel controlPanel = new APanel(new FlowLayout(FlowLayout.CENTER));

        if (isCurrentPlayer) {
            NButton drawCardButton = new NButton("Draw Card");
            drawCardButton.setFont(new Font("Arial", Font.BOLD, 14));
            drawCardButton.addActionListener(_ -> {
                if (isCurrentPlayer) {
                    game.getDeck().drawCard(player, 1);
                    gui.logMessage(player.getNom() + " draws a card.");
                    gui.updateGameState();
                    gui.updateDeckCardCount();
                }
            });
            controlPanel.add(drawCardButton);
        }

        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void updateHand() {
        cardsPanel.removeAll();

        int cardCount = player.getHand().size();
        int xPos = 0;
        int yPos = 0;

        for (int i = 0; i < cardCount; i++) {
            Card card = player.getHand().get(i);
            JComponent cardComponent;

            if (isCurrentPlayer) {
                cardComponent = createCardButton(card);
            } else {
                cardComponent = createCardBackLabel();
            }

            if (isVertical) {
                cardComponent.setBounds(0, yPos, CARD_WIDTH, CARD_HEIGHT);
                yPos += CARD_HEIGHT - CARD_OVERLAP;
            } else {
                cardComponent.setBounds(xPos, 0, CARD_WIDTH, CARD_HEIGHT);
                xPos += CARD_WIDTH - CARD_OVERLAP;
            }
            cardsPanel.add(cardComponent);
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();

        updatePlayerNameDisplay();
    }

    private void updatePlayerNameDisplay() {
        String displayText = player.getNom() + " (" + player.getHand().size() + " cards)";
        playerNameLabel.setText(displayText);

        if (isCurrentPlayer) {
            playerNameLabel.setForeground(new Color(244, 244, 244));
            setBorder(BorderFactory.createLineBorder(new Color(136, 8, 8), 3));
        } else {
            playerNameLabel.setForeground(Color.BLACK);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
    }

    private NButton createCardButton(Card card) {
        ImageIcon icon = loadCardImage(card);
        NButton cardButton = new NButton(icon);
        cardButton.setBorderPainted(false);
        cardButton.setContentAreaFilled(false);
        cardButton.setFocusPainted(false);

        cardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isVertical) {
                    cardButton.setBounds(cardButton.getX(), cardButton.getY() - 15, CARD_WIDTH, CARD_HEIGHT);
                } else {
                    cardButton.setBounds(cardButton.getX(), -15, CARD_WIDTH, CARD_HEIGHT);
                }
                cardButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardButton.setBounds(cardButton.getX(), 0, CARD_WIDTH, CARD_HEIGHT);
                cardButton.repaint();
            }
        });

        cardButton.addActionListener(_ -> {
            if (isCurrentPlayer && game.isCardPlayable(card, game.getTopCard())) {
                animateCardPlay(cardButton, card);
            } else if (isCurrentPlayer) {
                AOptionPane.showMessageDialog(PlayerHandPanel.this,
                        "You cannot play this card!", "Invalid Move", AOptionPane.WARNING_MESSAGE);
            }
        });

        return cardButton;
    }

    private ALabel createCardBackLabel() {
        ALabel cardLabel = new ALabel();
        if (backCardImage != null) {
            cardLabel.setIcon(backCardImage);
        } else {
            cardLabel.setOpaque(true);
            cardLabel.setBackground(Color.RED);
            cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        return cardLabel;
    }

    private void animateCardPlay(NButton cardButton, Card card) {
        Timer timer = new Timer(10, null);
        final int[] step = {0};
        final int maxSteps = 20;
        final int buttonX = cardButton.getX();
        final int buttonY = cardButton.getY();

        Point cardsPanelLocation = cardsPanel.getLocationOnScreen();
        Point targetLocation = gui.getTopCardPanelCenter();
        int targetX = targetLocation.x - cardsPanelLocation.x;
        int targetY = targetLocation.y - cardsPanelLocation.y;

        timer.addActionListener(_ -> {
            step[0]++;

            if (step[0] <= maxSteps) {
                int newX = buttonX + (targetX - buttonX) * step[0] / maxSteps;
                int newY = buttonY + (targetY - buttonY) * step[0] / maxSteps;

                cardButton.setBounds(newX, newY, CARD_WIDTH, CARD_HEIGHT);
                cardsPanel.repaint();
            } else {
                timer.stop();
                handleSpecialCardEffect(card);
                game.playCard(player, card);
                gui.logMessage(player.getNom() + " plays " + getCardDisplayName(card));

                if (player.getHand().isEmpty()) {
                    gui.checkGameOver();
                }
                gui.updateGameState();
                gui.updateDeckCardCount();
            }
        });

        timer.start();
    }

    private ImageIcon loadCardImage(Card card) {
        String imagePath = getCardImagePath(card);
        try {
            java.net.URL imageUrl = getClass().getClassLoader().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image image = icon.getImage();
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
        showColorChoiceDialog(card);
    }

    private void handleWildDrawFour(WildDrawFourCard card) {
        Player nextPlayer = game.getNextPlayer();
        game.getDeck().drawCard(nextPlayer, 4);
        gui.logMessage(nextPlayer.getNom() + " draws 4 cards!");
        showColorChoiceDialog(card);
    }

    private void showColorChoiceDialog(Card card) {
        final ADialog colorDialog = new ADialog(gui, "Choose a color", true);
        APanel contentPanel = new APanel(new GridLayout(2, 2));
        contentPanel.setBackground(new Color(50, 50, 50));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Dimension dialogSize = new Dimension(400, 400);
        contentPanel.setPreferredSize(dialogSize);

        final String[] colors = {"red", "green", "blue", "yellow"};

        for (final String color : colors) {
            APanel colorButtonPanel = new APanel(new BorderLayout());
            colorButtonPanel.setBackground(new Color(50, 50, 50));
            colorButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Create the color button with the corresponding color image
            String imagePath = "images/" + color + "_empty.png";
            ImageIcon colorIcon = null;

            try {
                java.net.URL imageUrl = getClass().getClassLoader().getResource(imagePath);
                if (imageUrl != null) {
                    ImageIcon originalIcon = new ImageIcon(imageUrl);
                    Image image = originalIcon.getImage();
                    Image resizedImage = image.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                    colorIcon = new ImageIcon(resizedImage);
                }
            } catch (Exception e) {
                System.err.println("Error loading color image: " + imagePath);
                e.printStackTrace();
            }

            NButton colorButton = new NButton();
            colorButton.setIcon(colorIcon);
            colorButton.setText(color.toUpperCase());
            colorButton.setFont(new Font("Arial", Font.BOLD, 16));
            colorButton.setHorizontalTextPosition(SwingConstants.CENTER);
            colorButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            colorButton.setFocusPainted(false);
            colorButton.setContentAreaFilled(false);
            colorButton.setBorderPainted(false);

            // Set the foreground color that contrasts well with the button color
            colorButton.setForeground(Color.WHITE);

            colorButton.addActionListener(e -> {
                card.setCouleur(color);
                String cardName = (card instanceof WildDrawFourCard) ? "Wild Draw Four Card" : "Wild Card";
                gui.logMessage(player.getNom() + " chose " + color + " for the " + cardName + ".");

                // Key fix: Update the top card with the selected color
                gui.topCardPanel.setTopCardColorImage(color);

                colorDialog.dispose();
                gui.updateGameState();
            });

            colorButtonPanel.add(colorButton, BorderLayout.CENTER);
            contentPanel.add(colorButtonPanel);
        }

        colorDialog.setLayout(new BorderLayout());
        colorDialog.add(contentPanel, BorderLayout.CENTER);
        colorDialog.pack();
        colorDialog.setLocationRelativeTo(gui);
        colorDialog.setVisible(true);
    }

    private ImageIcon[] loadColorIcons() {
        final String[] colorNames = {"red", "green", "blue", "yellow"};
        final ImageIcon[] colorIcons = new ImageIcon[4];

        for (int i = 0; i < colorNames.length; i++) {
            colorIcons[i] = loadImageIcon("images/" + colorNames[i] + "_empty.png");
        }
        return colorIcons;
    }

    private ImageIcon loadEmptyColorIcon() {
        return loadImageIcon("images/uno_background.png");
    }

    private ImageIcon loadImageIcon(String path) {
        try {
            java.net.URL imageUrl = getClass().getClassLoader().getResource(path);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl);
            } else {
                System.err.println("Image not found: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
            return null;
        }
    }

    private ImageIcon resizeIcon(ImageIcon icon, Dimension targetSize) {
        if (icon != null) {
            Image image = icon.getImage();
            Image resizedImage = image.getScaledInstance(targetSize.width, targetSize.height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        }
        return null;
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
        this.player = player;
    }

    public void setIsCurrentPlayer(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }
}
