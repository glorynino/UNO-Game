package Unostart;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.*;
import javax.swing.Timer;
import UnoSwing.*;
import com.formdev.flatlaf.FlatDarkLaf;

public class NMenu extends NFrame {

    private static Clip clip; // Clip rendu accessible globalement

    public NMenu() {
        this.setTitle("Menu UNO");
        this.setExtendedState(NFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(NFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        APanel fondPanel = new APanel() {
            private final Image imageFond = new ImageIcon("src/Images/imageUNO.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // === Logo animé ===
        ImageIcon logoIconBase = new ImageIcon("src/Images/LOGOpng.png");
        Image logoImage = logoIconBase.getImage();
        ALabel LogoUno = new ALabel();

        new Timer(30, new ActionListener() {
            int frame = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = 300 + (int)(Math.sin(frame * 0.1) * 20);
                Image scaled = logoImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                LogoUno.setIcon(new ImageIcon(scaled));
                frame++;
            }
        }).start();

        fondPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        fondPanel.add(LogoUno, gbc);

        // === Bouton Start ===
        NButton Bstart = new NButton();
        Bstart.setBackground(Color.YELLOW);
        Bstart.setForeground(Color.BLACK);
        Bstart.setText("Start");
        Bstart.setPreferredSize(new Dimension(200, 60));
        Bstart.addActionListener(e -> {
            new Nchoixperso();
            this.dispose();
        });
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        fondPanel.add(Bstart, gbc);

        // === Bouton Exit ===
        NButton BQuitter = new NButton();
        BQuitter.setText("Exit");
        BQuitter.setBackground(Color.RED);
        BQuitter.setForeground(Color.BLACK);
        BQuitter.setPreferredSize(new Dimension(200, 60));
        BQuitter.addActionListener(e -> {
            dispose();
        });
        gbc.gridy = 2;
        fondPanel.add(BQuitter, gbc);

        this.getContentPane().add(fondPanel);

        // === Raccourci clavier Ctrl + A pour couper la musique ===
        KeyStroke ctrlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlA, "stopMusic");
        this.getRootPane().getActionMap().put("stopMusic", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip != null && clip.isRunning()) {
                    clip.stop();
                }
            }
        });

        this.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            File fichierAudio = new File("src/music/MusicMenu.wav");
            AudioInputStream audioinput = AudioSystem.getAudioInputStream(fichierAudio);
            clip = AudioSystem.getClip();
            clip.open(audioinput);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-40.0f);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new NMenu();
    }
}
