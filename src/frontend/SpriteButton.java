package frontend;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SpriteButton extends JPanel {
        BufferedImage button;

        public SpriteButton(String imagePath) {
            super();

            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
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
