package Unostart;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import NSwing.*;

public class NMenu extends JFrame {

    public NMenu() {
        this.setTitle("Menu UNO");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centre la fenêtre

        JPanel fondPanel = new JPanel() {
            private final Image imageFond = new ImageIcon("src/Images/imageUNO.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Charger l'image du logo
        ImageIcon logoIcon = new ImageIcon("src/Images/LOGOpng.png");

        // Redimensionner l'image du logo
        Image logoImage = logoIcon.getImage();
        Image resizedLogo = logoImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Taille désirée : 300x150 (ajuste à ta convenance)
        logoIcon = new ImageIcon(resizedLogo);

        ALabel LogoUno = new ALabel(logoIcon);

        fondPanel.setLayout(new GridBagLayout()); // Layout flexible
        GridBagConstraints gbc = new GridBagConstraints();

        // Position du logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0); // Espace vertical entre le logo et le bouton
        gbc.anchor = GridBagConstraints.CENTER;  // Centrer le logo
        fondPanel.add(LogoUno, gbc);

        // Position du bouton
        NBouton Bstart = new NBouton();
        Bstart.setBackground(Color.YELLOW);
        Bstart.setForeground(Color.BLACK);
        Bstart.setText("Start");

        Bstart.setPreferredSize(new Dimension(200, 60)); // Taille fixe
        gbc.gridy = 1; // Le bouton en dessous du logo
        gbc.insets = new Insets(20, 0, 0, 0);// Espacement au-dessus du bouton
        Bstart.addActionListener(e -> {
            try {
                new Nchoixperso();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.dispose();           // qu'on tu ecrit "e" veut dire "action event" et donc le bloc represente les intruction executer
                                     // et qui remplace la methode " actionPerformed" action listener est une methode abstraire !!!
        });
        fondPanel.add(Bstart, gbc);

        NBouton BQuitter = new NBouton();
        BQuitter.setText("Exit");
        BQuitter.setBackground(Color.RED);
        BQuitter.setForeground(Color.BLACK);
        BQuitter.setPreferredSize(new Dimension(200, 60)); // Taille fixe
        gbc.gridy = 2; // Le bouton en dessous du logo
        gbc.insets = new Insets(20, 0, 0, 0); // Espacement au-dessus du bouton
        BQuitter.addActionListener(e->{
            dispose();
        });
        fondPanel.add(BQuitter, gbc);



        this.getContentPane().add(fondPanel);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        try {
            // Appliquer le look du système une seule fois
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        new NMenu();
    }
}
