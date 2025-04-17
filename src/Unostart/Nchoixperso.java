package Unostart;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import NSwing.*;
public class Nchoixperso extends JFrame {
    private static final Color GREEN_BUTTON = new Color(76, 175, 80);
    private static final Color YELLOW_BUTTON = new Color(255, 193, 7);
    private static final Color RED_BUTTON = new Color(244, 67, 54);
    public Nchoixperso() {

        this.setTitle("Choice the Number of Player");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // maximise comme Google ou IntelliJ
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Création du fond avec l'image
        JPanel fondPanel = new JPanel() {
            private final Image imageFond = new ImageIcon("src/Images/fondchoixpersonnage.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
            }
        };

        fondPanel.setLayout(null);
        setContentPane(fondPanel);

        // Création du JLabel
        JLabel label = new JLabel("Select the number of players !");
        label.setBounds(300, 100, 700, 60);
        // Position et taille élargies
        label.setForeground(Color.WHITE);

        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/Orbitron-VariableFont_wght.ttf");
            if (is != null) {
                Font luckiestGuyFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(luckiestGuyFont);
                label.setFont(luckiestGuyFont);
            } else {
                System.err.println("La police n'a pas été trouvée !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        fondPanel.add(label); // Ajout du JLabel dans le panel fondPanel
        // Création des boutons en forme de losange
        DiamondButton twoPlayersButton = new DiamondButton(GREEN_BUTTON, 2);
        DiamondButton threePlayersButton = new DiamondButton(YELLOW_BUTTON, 3);
        DiamondButton fourPlayersButton = new DiamondButton(RED_BUTTON, 4); // 4 joueurs au lieu de 5

// Positionnement des boutons
        twoPlayersButton.setBounds(350, 250, 140, 140);
        threePlayersButton.setBounds(550, 250, 140, 140);
        fourPlayersButton.setBounds(750, 250, 140, 140);

// Ajout des boutons au panel
        fondPanel.add(twoPlayersButton);
        fondPanel.add(threePlayersButton);
        fondPanel.add(fourPlayersButton);
        //NBouton bouton = new NBouton();

        this.setVisible(true);
    }

}
