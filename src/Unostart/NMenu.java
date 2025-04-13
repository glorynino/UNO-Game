import javax.swing.*;
import java.awt.*;

public class NMenu extends JFrame {

    public NMenu() {
        this.setTitle("Menu UNO");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // centre la fenêtre

        JPanel fondPanel = new JPanel() {
            private Image imageFond = new ImageIcon("Unostart//src//ImageMenuLOGO.jpg").getImage();

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

    public static void main(String[] args) {
        new NMenu();
    }
}
