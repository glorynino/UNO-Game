package NSwing;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class APanel extends JPanel {

  private BufferedImage imageFond;
  
  public APanel() {
    this.setLayout(null);
  }

  public APanel (BufferedImage image) {
    this.setLayout(null);
    this.imageFond = image;
  
  }
  
  public APanel(FlowLayout flowLayout) {
    super(flowLayout);
  }

  public APanel(GridBagLayout gridBagLayout) {
    super(gridBagLayout);
  }

  public APanel(BorderLayout borderLayout) {
    super(borderLayout);
  }

  @Override
  protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
  }
  
}
