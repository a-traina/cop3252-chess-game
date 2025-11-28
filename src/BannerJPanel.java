import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BannerJPanel extends JPanel {
    private final JLabel playerJLabel;
    private final Player player;
    private final CapturedPiecesPanel capturedPiecesPanel;
    
    public BannerJPanel(Player player) {
        this.player = player;

        playerJLabel = new JLabel(player.toString());
        playerJLabel.setForeground(Color.WHITE);
        capturedPiecesPanel = new CapturedPiecesPanel();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        setPreferredSize(new Dimension(400, 45));
        add(playerJLabel);
        add(capturedPiecesPanel);

        setBackground(new Color(51, 50, 48));
    }

    private class CapturedPiecesPanel extends JPanel {
        public CapturedPiecesPanel() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setAlignmentX(LEFT_ALIGNMENT);
            setBackground(Color.DARK_GRAY);
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
    
}
