import java.awt.Color;
import javax.swing.*;

public class MainJFrame extends JFrame {
    private ChessJPanel chessJPanel;
    private final GameOverJPanel gameOverJPanel;
    private GameBoard gameBoard;
    public MainJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.DARK_GRAY);

        gameBoard = new GameBoard();

        chessJPanel = new ChessJPanel(gameBoard, this);
        add(chessJPanel);

        gameOverJPanel = new GameOverJPanel(this);
        gameOverJPanel.setVisible(false);
        setGlassPane(gameOverJPanel);
    }

    public void showGameOver(boolean flag) {
        String result = gameBoard.getGameOverMsg();
        gameOverJPanel.setGameResult(result);
        gameOverJPanel.revalidate();
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

    public static void main(String[] args) {
        MainJFrame mainJFrame = new MainJFrame();
        mainJFrame.setVisible(true);
    }
}
