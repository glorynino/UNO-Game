package NSwing;

import javax.swing.*;
import java.awt.*;

public class NBackground extends JFrame {
     NBackground(String message){
         this.setTitle(message);
         this.setExtendedState(JFrame.MAXIMIZED_BOTH);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setLocationRelativeTo(null); // centre la fenêtre

         JPanel fondPanel = new JPanel() {
             private final Image imageFond = new ImageIcon("src/Images/imageUNO.png").getImage();

             @Override
             protected void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
             }
         };

         fondPanel.setLayout(null);


         this.getContentPane().add(fondPanel);
         this.setVisible(true);
     }
}
