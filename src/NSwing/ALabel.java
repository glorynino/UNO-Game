package NSwing;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.*;

public class ALabel extends JLabel{

  public ALabel() {
    this.setLayout(null);
    this.setForeground(Color.WHITE);
    try {
      InputStream is = getClass().getResourceAsStream("/Fonts/Orbitron-VariableFont_wght.ttf");
      if (is != null) {
          Font luckiestGuyFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
          GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
          ge.registerFont(luckiestGuyFont);
          this.setFont(luckiestGuyFont);
      } else {
          System.err.println("La police n'a pas été trouvée !");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ALabel(String text) {
    super(text);
    this.setLayout(null);
    this.setForeground(Color.WHITE);
    try {
      InputStream is = getClass().getResourceAsStream("/Fonts/Orbitron-VariableFont_wght.ttf");
      if (is != null) {
          Font luckiestGuyFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
          GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
          ge.registerFont(luckiestGuyFont);
          this.setFont(luckiestGuyFont);
      } else {
          System.err.println("La police n'a pas été trouvée !");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public ALabel(ImageIcon icon) {
    super(icon);
    this.setLayout(null);
  }

  public ALabel(String s, int center) {
      super(s, center);
      this.setLayout(null);
      this.setForeground(Color.WHITE);
      try {
        InputStream is = getClass().getResourceAsStream("/Fonts/Orbitron-VariableFont_wght.ttf");
        if (is != null) {
            Font luckiestGuyFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(luckiestGuyFont);
            this.setFont(luckiestGuyFont);
        } else {
            System.err.println("La police n'a pas été trouvée !");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
