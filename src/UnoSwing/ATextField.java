package UnoSwing;
import javax.swing.*;
public class ATextField extends JTextField {
  
  public ATextField() {
    super();
    this.setLayout(null);
    this.setBackground(new java.awt.Color(0, 0, 0));
    this.setForeground(new java.awt.Color(255, 255, 255));
  }

  public ATextField(String string) {
    super(string);
  }
  
}
