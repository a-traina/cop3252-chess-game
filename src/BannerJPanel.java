import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class BannerJPanel extends JPanel {
    private final JLabel playerJLabel;
    private final Player player;
    private final CapturedPiecesPanel capturedPiecesPanel;
    private final JLabel clockLabel;
    
    public BannerJPanel(Player player, boolean toggleClock) {
        this.player = player;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topRow = new JPanel();
        topRow.setLayout(new BoxLayout(topRow, BoxLayout.X_AXIS));
        topRow.setAlignmentX(LEFT_ALIGNMENT);
        topRow.setOpaque(false);

        playerJLabel = new JLabel(player.toString() + ":");
        playerJLabel.setForeground(Color.GRAY);
        playerJLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        add(playerJLabel);
        topRow.add(playerJLabel);

        clockLabel = new JLabel(player.timeToString()) {
            @Override
            public void revalidate() {
                repaint();
            }
        };
        clockLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        clockLabel.setForeground(Color.GRAY);
        clockLabel.setOpaque(true);
        clockLabel.setBackground(new Color(51, 50, 48));
        clockLabel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(30, 30, 30)), new EmptyBorder(5, 5, 5, 5)));

        topRow.add(Box.createHorizontalGlue());
        topRow.add(clockLabel);

        if(!toggleClock)
            clockLabel.setVisible(false);

        add(topRow);

        capturedPiecesPanel = new CapturedPiecesPanel();
        add(capturedPiecesPanel);

        setOpaque(false);
    }

    private class CapturedPiecesPanel extends JPanel {
        public CapturedPiecesPanel() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setAlignmentX(LEFT_ALIGNMENT);
            setOpaque(false);
            setPreferredSize(new Dimension(2, 30));
        }

        public void updateCapturedPieces() {
            removeAll();
            for(Piece p : player.getCapturedPieces()) {
                ImageIcon icon = new ImageIcon(getClass().getResource(p.getImagePath()));

                Image scaled = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(scaled));

                add(label);
            }

            revalidate();
            repaint();
        }
    }

    public void updateCapturedPieces() {
        capturedPiecesPanel.updateCapturedPieces();
    }

    public JLabel getClockLabel() {
        return clockLabel;
    }

    public JLabel getPlayerJLabel() {
        return playerJLabel;
    }

    public void toggleClockLabel(boolean flag) {
        clockLabel.setVisible(flag);
        clockLabel.setText(player.timeToString());
    }
    
}
