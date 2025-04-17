package NSwing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiamondButton extends JButton {
    private final Color buttonColor;
    private final int playerCount;
    private Shape diamondShape;

    public DiamondButton(Color color, int players) {
        this.buttonColor = color;
        this.playerCount = players;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        // Ajouter une bordure pour voir les limites du bouton (à des fins de débogage)
        // setBorder(BorderFactory.createLineBorder(Color.RED, 1));

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
        addActionListener(e -> {
            System.out.println("Vous avez sélectionné " + playerCount + " joueurs!");
            // Ajoutez ici le code pour passer à l'écran suivant ou effectuer une action
            JOptionPane.showMessageDialog(null, "Vous avez sélectionné " + playerCount + " joueurs!");
        });
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

        // Dessiner les icônes de personnages en blanc
        g2d.setColor(Color.WHITE);

        // Placement des icônes selon le nombre de joueurs
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        if (playerCount == 2) {
            drawPlayerIcon(g2d, centerX - 20, centerY);
            drawPlayerIcon(g2d, centerX + 20, centerY);
        } else if (playerCount == 3) {
            drawPlayerIcon(g2d, centerX - 25, centerY);
            drawPlayerIcon(g2d, centerX + 25, centerY);
            drawPlayerIcon(g2d, centerX, centerY - 10);
        } else if (playerCount == 4) {
            // Disposition pour 4 joueurs en forme de carré
            drawPlayerIcon(g2d, centerX - 20, centerY - 15);
            drawPlayerIcon(g2d, centerX + 20, centerY - 15);
            drawPlayerIcon(g2d, centerX - 20, centerY + 15);
            drawPlayerIcon(g2d, centerX + 20, centerY + 15);
        }

        g2d.dispose();
    }

    private void drawPlayerIcon(Graphics2D g2d, int x, int y) {
        // Tête
        g2d.fillOval(x - 8, y - 20, 16, 16);
        // Corps
        g2d.fillRoundRect(x - 7, y - 5, 14, 20, 5, 5);
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