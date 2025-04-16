package NSwing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.InputStream;

public class NBouton extends JButton {
    public NBouton(){

        this.setContentAreaFilled(true);
        this.setOpaque(true);
        this.setFocusPainted(false);



        try {
            InputStream is = getClass().getResourceAsStream("src/Fonts/Fredoka-VariableFont_wdth,wght.ttf");
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

    @Override
    public void setBorder(Border border) {
        super.setBorder(border);
    }


}
