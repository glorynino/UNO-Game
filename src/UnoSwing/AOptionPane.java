package UnoSwing;
import javax.swing.*;
public class AOptionPane extends JOptionPane {

    public AOptionPane() {
        super();
        this.setLayout(null);
        this.setBackground(new java.awt.Color(0, 0, 0));
        this.setForeground(new java.awt.Color(255, 255, 255));
    }

    public AOptionPane(String message) {
        super(message);
        this.setLayout(null);
        this.setBackground(new java.awt.Color(0, 0, 0));
        this.setForeground(new java.awt.Color(255, 255, 255));
    }
  
}
