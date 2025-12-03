import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MenuJPanel extends JPanel {
    BufferedImage background;
    
    public MenuJPanel(MainJFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        try {
            background = ImageIO.read(getClass().getResource("/assets/menu_background.jpeg"));
        } catch (IOException e) {
            background = null;
        }

        add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel(new ImageIcon(getClass().getResource("/assets/title.png")));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        //add(Box.createVerticalStrut(50));

        SpriteButton playButton = new SpriteButton("/assets/start_button.png");
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.startGame();
            }
        });

        JPanel playButtonWrapper = new JPanel();
        playButtonWrapper.setOpaque(false);
        playButtonWrapper.setPreferredSize(new Dimension(250, 70));
        playButtonWrapper.setMinimumSize(playButtonWrapper.getPreferredSize());
        playButtonWrapper.add(playButton);

        add(playButtonWrapper);

        //add(Box.createVerticalStrut(20));

        SpriteButton quitButton = new SpriteButton("/assets/quit_button.png");
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        JPanel quitButtonWrapper = new JPanel();
        quitButtonWrapper.setOpaque(false);
        quitButtonWrapper.setPreferredSize(new Dimension(370, 100));
        quitButtonWrapper.setMinimumSize(quitButtonWrapper.getPreferredSize());
        quitButtonWrapper.add(quitButton);

        add(quitButtonWrapper);

        add(Box.createVerticalGlue());

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null)
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

    private class SpriteButton extends JPanel {
        BufferedImage button;

        public SpriteButton(String imagePath) {
            super();

            setOpaque(false);
            
            try {
                button = ImageIO.read(getClass().getResource(imagePath));
            } catch (IOException e) {
                button = null;
            }

            Dimension normalSize = new Dimension(350, 90);
            Dimension hoverSize = new Dimension(365, 96);

            setMaximumSize(normalSize);
            setPreferredSize(getMaximumSize());

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setMaximumSize(hoverSize);
                    setPreferredSize(getMaximumSize());
                    revalidate();
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setMaximumSize(normalSize);
                    setPreferredSize(getMaximumSize());
                    revalidate();
                    repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            if(button != null)
                g.drawImage(button, 0, 0, getWidth(), getHeight(), null);
        }
    }
    
}
