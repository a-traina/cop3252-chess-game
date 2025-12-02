import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
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

        add(Box.createVerticalStrut(50));

        JButton startButton = new JButton("Start");
        startButton.addActionListener((ActionEvent e) -> mainFrame.startGame());
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        startButton.setBackground(new Color(144, 238, 144));
        startButton.setFocusable(false);
        startButton.setMaximumSize(new Dimension(500, 60));
        startButton.setPreferredSize(startButton.getMaximumSize());
        add(startButton);

        add(Box.createVerticalGlue());

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }
    
}
