package NSwing;

import javax.swing.*;
import java.awt.*;

public class AScrollPane extends JScrollPane {
    public AScrollPane(ATextArea text) {
        super(text);
    }

    public AScrollPane(Component view) {
        super(view);
    }
}
