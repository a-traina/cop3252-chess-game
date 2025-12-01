import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameOverJPanel extends JPanel {
    private String gameOverResult;
    private final JLabel whiteWinsIcon;
    private final JLabel blackWinsIcon;
    public GameOverJPanel(MainJFrame mainFrame) {
        setOpaque(false);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        JLabel gameOverIcon = new JLabel(new ImageIcon(getClass().getResource("/assets/game_over.png")));
        gameOverIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(gameOverIcon);

        whiteWinsIcon = new JLabel(new ImageIcon(getClass().getResource("/assets/white_wins.png")));
        blackWinsIcon = new JLabel(new ImageIcon(getClass().getResource("/assets/black_wins.png")));

        whiteWinsIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        blackWinsIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(whiteWinsIcon);
        add(blackWinsIcon);

        add(Box.createVerticalGlue());

        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener((ActionEvent e) -> mainFrame.resetGame());
        add(newGameButton);

         JButton quitButton = new JButton("Quit Game");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.addActionListener((ActionEvent e) -> System.exit(0));
        add(quitButton);
        add(Box.createVerticalGlue());

    }

    public void setGameResult(String result) {
        gameOverResult = result;
        switch(gameOverResult) {
            case "Draw" -> {
                whiteWinsIcon.setVisible(false);
                blackWinsIcon.setVisible(false);
            }
            case "White" -> {
                whiteWinsIcon.setVisible(true);
                blackWinsIcon.setVisible(false);
            }
            case "Black" -> {
                whiteWinsIcon.setVisible(false);
                blackWinsIcon.setVisible(true);
            }
            default -> {
                whiteWinsIcon.setVisible(false);
                blackWinsIcon.setVisible(false);
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
