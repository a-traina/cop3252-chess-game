package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import util.GameSettings;
import util.SoundEffect;

public class GameOverJPanel extends JPanel {
    private final ImageIcon whiteWinsImg;
    private final ImageIcon blackWinsImg;
    private final ImageIcon drawGameImg;
    private final JLabel gameResultIcon;
    private SoundEffect buttonSound;

    public GameOverJPanel(MainJFrame mainFrame, GameSettings settings) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        buttonSound = new SoundEffect(getClass().getResource("/sounds/buttonPressedSound.wav"));

        add(Box.createVerticalGlue());

        ImageIcon gameOverImg = new ImageIcon(getClass().getResource("/gameOver/game_over.png"));
        JLabel gameOverIcon = new JLabel(gameOverImg);
        add(gameOverIcon);
        gameOverIcon.setAlignmentX(CENTER_ALIGNMENT);

        int scaledWidth = (int)(gameOverImg.getIconWidth() * 0.5);
        int scaledHeight = (int)(gameOverImg.getIconHeight() * 0.3);

        whiteWinsImg = new ImageIcon(new ImageIcon(getClass().getResource("/gameOver/white_wins.png"))
            .getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        blackWinsImg = new ImageIcon(new ImageIcon(getClass().getResource("/gameOver/black_wins.png"))
            .getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        drawGameImg = new ImageIcon(new ImageIcon(getClass().getResource("/gameOver/draw_game.png"))
                .getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));

        gameResultIcon = new JLabel();
        add(gameResultIcon);
        gameResultIcon.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(25));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new Dimension(800, 135));
        buttonsPanel.setMaximumSize(buttonsPanel.getPreferredSize());

        SpriteButton playButton = new SpriteButton("/buttons/new_game_button.png");
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (settings.getToggleSoundFX())
                  buttonSound.play();
                mainFrame.resetGame();
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
        add(buttonsPanel);

        add(Box.createVerticalGlue());

    }

    public void setGameResult(String result) {
        switch(result) {
            case "Draw" -> {
                gameResultIcon.setIcon(drawGameImg);
            }
            case "White" -> {
                gameResultIcon.setIcon(whiteWinsImg);
            }
            case "Black" -> {
                gameResultIcon.setIcon(blackWinsImg);
            }
            default -> {
                gameResultIcon.setIcon(null);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0,0,0,190));
        g.fillRect(0,0, getWidth(), getHeight());
    }
}
