package frontend;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import util.GameSettings;
import util.SoundEffect;

public class MenuJPanel extends JPanel {
    BufferedImage background;
    SoundEffect buttonSound;
    
    public MenuJPanel(MainJFrame mainFrame, GameSettings settings) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        try {
            background = ImageIO.read(getClass().getResource("/menu/menu_background.jpeg"));
        } catch (IOException e) {
            background = null;
        }

        buttonSound = new SoundEffect(getClass().getResource("/sounds/buttonPressedSound.wav"));

        add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel(new ImageIcon(getClass().getResource("/menu/title.png")));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        JLabel subtitleLabel = new JLabel(new ImageIcon(getClass().getResource("/menu/subtitle.png")));
        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(5));
        add(subtitleLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new Dimension(800, 135));
        buttonsPanel.setMaximumSize(buttonsPanel.getPreferredSize());

        SpriteButton playButton = new SpriteButton("/buttons/start_button.png");
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (settings.getToggleSoundFX())
                    buttonSound.play();
                mainFrame.startGame();
            }
        });

        JPanel playButtonWrapper = new JPanel();
        playButtonWrapper.setOpaque(false);
        playButtonWrapper.setPreferredSize(new Dimension(370, 100));
        playButtonWrapper.setMinimumSize(playButtonWrapper.getPreferredSize());
        playButtonWrapper.add(playButton);

        buttonsPanel.add(playButtonWrapper);

        SpriteButton quitButton = new SpriteButton("/buttons/quit_button.png");
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (settings.getToggleSoundFX())
                    buttonSound.play();
                System.exit(0);
            }
        });

        JPanel quitButtonWrapper = new JPanel();
        quitButtonWrapper.setOpaque(false);
        quitButtonWrapper.setPreferredSize(new Dimension(370, 100));
        quitButtonWrapper.setMinimumSize(quitButtonWrapper.getPreferredSize());
        quitButtonWrapper.add(quitButton);

        buttonsPanel.add(quitButtonWrapper);

        add(Box.createVerticalStrut(25));
        add(buttonsPanel);
        add(Box.createVerticalGlue());

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null)
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }
}
