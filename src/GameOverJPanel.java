import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameOverJPanel extends JPanel {
    private final ImageIcon whiteWinsImg;
    private final ImageIcon blackWinsImg;
    private final JLabel gameResultIcon;
    public GameOverJPanel(MainJFrame mainFrame) {
        setOpaque(false);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        JLabel gameOverIcon = new JLabel(new ImageIcon(getClass().getResource("/assets/game_over.png")));
        add(gameOverIcon);
        gameOverIcon.setAlignmentX(CENTER_ALIGNMENT);

        whiteWinsImg = new ImageIcon(getClass().getResource("/assets/white_wins.png"));
        blackWinsImg = new ImageIcon(getClass().getResource("/assets/black_wins.png"));

        gameResultIcon = new JLabel();
        add(gameResultIcon);
        gameResultIcon.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener((ActionEvent e) -> mainFrame.resetGame());
        add(newGameButton);
        newGameButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton quitButton = new JButton("Quit Game");
        quitButton.addActionListener((ActionEvent e) -> System.exit(0));
        add(quitButton);
        quitButton.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());

    }

    public void setGameResult(String result) {
        switch(result) {
            case "Draw" -> {
                gameResultIcon.setIcon(null);
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
