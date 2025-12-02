import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

public class MainJFrame extends JFrame {
    private ChessJPanel chessJPanel;
    private final MenuJPanel menuPanel;
    private final GameOverJPanel gameOverJPanel;
    private GameBoard gameBoard;
    public MainJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        getContentPane().setBackground(Color.DARK_GRAY);

        gameBoard = new GameBoard();

        menuPanel = new MenuJPanel(this);
        add(menuPanel);

        gameOverJPanel = new GameOverJPanel(this);
        gameOverJPanel.setVisible(false);
        setGlassPane(gameOverJPanel);
    }

    public void showGameOver(boolean flag) {
        String result = gameBoard.getGameOverMsg();
        gameOverJPanel.setGameResult(result);
        gameOverJPanel.setVisible(flag);
    }

    public void resetGame() {
        remove(chessJPanel);

        gameBoard = new GameBoard();
        chessJPanel = new ChessJPanel(gameBoard, this);
        add(chessJPanel);

        showGameOver(false);

        revalidate();
    }

    public void startGame() {
        remove(menuPanel);

        chessJPanel = new ChessJPanel(gameBoard, this);
        add(chessJPanel);

        revalidate();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {}

        MainJFrame mainJFrame = new MainJFrame();
        mainJFrame.setVisible(true);
    }
}
