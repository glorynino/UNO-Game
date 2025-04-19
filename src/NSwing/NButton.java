package NSwing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.InputStream;



public class NButton extends JButton {


    public NButton(){
        this.setContentAreaFilled(true);
        this.setOpaque(true);
        this.setFocusPainted(false);

        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/VT323-Regular.ttf");
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

        this.setToolTipText("Start");
    }

    public NButton(String string) {
        super(string);
        this.setContentAreaFilled(true);
        this.setOpaque(true);
        this.setFocusPainted(false);

        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/VT323-Regular.ttf");
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

    @Override
    public void setBorder(Border border) {
        super.setBorder(border);
        this.setContentAreaFilled(true);
        this.setOpaque(true);
        this.setFocusPainted(false);

        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/VT323-Regular.ttf");
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
