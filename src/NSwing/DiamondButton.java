package NSwing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiamondButton extends JButton {
    private Color buttonColor;
    private int playerCount;
    private Shape diamondShape;
    private PlayerChoiceListener listener;

    // Tableau de couleurs pour différencier les joueurs
    private static final Color[] PLAYER_COLORS = {
            Color.WHITE,       // Joueur 1
            Color.YELLOW,      // Joueur 2
            Color.CYAN,        // Joueur 3
            Color.ORANGE       // Joueur 4
    };

    public int getPlayerCount() {
        return playerCount;
    }

    public DiamondButton(Color color, int players) {
        this.buttonColor = color;
        this.playerCount = players;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        // Ajouter un effet de survol pour mieux visualiser l'interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Ajouter le listener pour les actions du bouton
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Player count selected: " + playerCount + " players!");

                // Notify the listener about the selected player count
                if (listener != null) {
                    listener.onPlayerCountSelected(playerCount);
                }
            }
        });
    }

    // Method to set the listener
    public void setPlayerChoiceListener(PlayerChoiceListener listener) {
        this.listener = listener;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        // Recalculer la forme du diamant quand les dimensions changent
        updateDiamondShape();
    }

    private void updateDiamondShape() {
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int size = Math.min(width, height) - 20;

        // Forme du losange
        Polygon diamond = new Polygon();
        diamond.addPoint(centerX, centerY - size/2);  // haut
        diamond.addPoint(centerX + size/2, centerY);  // droite
        diamond.addPoint(centerX, centerY + size/2);  // bas
        diamond.addPoint(centerX - size/2, centerY);  // gauche

        diamondShape = diamond;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (diamondShape == null) {
            updateDiamondShape();
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner le losange
        g2d.setColor(buttonColor);
        g2d.fill(diamondShape);

        // Option: ajouter un contour pour mieux voir le bouton
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2f));
        g2d.draw(diamondShape);

        // Placement des icônes selon le nombre de joueurs
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        if (playerCount == 2) {
            // Joueur 1
            drawPlayerIcon(g2d, centerX - 20, centerY, PLAYER_COLORS[0]);
            // Joueur 2
            drawPlayerIcon(g2d, centerX + 20, centerY, PLAYER_COLORS[1]);
        } else if (playerCount == 3) {
            // Joueur 1
            drawPlayerIcon(g2d, centerX - 25, centerY, PLAYER_COLORS[0]);
            // Joueur 2
            drawPlayerIcon(g2d, centerX + 25, centerY, PLAYER_COLORS[1]);
            // Joueur 3
            drawPlayerIcon(g2d, centerX, centerY - 10, PLAYER_COLORS[2]);
        } else if (playerCount == 4) {
            // Disposition pour 4 joueurs en forme de carré
            // Joueur 1
            drawPlayerIcon(g2d, centerX - 20, centerY - 15, PLAYER_COLORS[0]);
            // Joueur 2
            drawPlayerIcon(g2d, centerX + 20, centerY - 15, PLAYER_COLORS[1]);
            // Joueur 3
            drawPlayerIcon(g2d, centerX - 20, centerY + 15, PLAYER_COLORS[2]);
            // Joueur 4
            drawPlayerIcon(g2d, centerX + 20, centerY + 15, PLAYER_COLORS[3]);
        }

        g2d.dispose();
    }

    private void drawPlayerIcon(Graphics2D g2d, int x, int y, Color playerColor) {
        // Sauvegarder la couleur actuelle
        Color originalColor = g2d.getColor();

        // Définir la couleur du joueur
        g2d.setColor(playerColor);

        // Dessiner le joueur avec sa couleur spécifique
        // Tête
        g2d.fillOval(x - 8, y - 20, 16, 16);
        // Corps
        g2d.fillRoundRect(x - 7, y - 5, 14, 20, 5, 5);

        // Restaurer la couleur originale
        g2d.setColor(originalColor);
    }

    // Pour la détection des clics sur le losange
    @Override
    public boolean contains(int x, int y) {
        if (diamondShape == null) {
            updateDiamondShape();
        }
        return diamondShape.contains(x, y);
    }
}