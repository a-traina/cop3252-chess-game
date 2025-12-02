import java.awt.*;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

        JMenuBar menuBar = new JMenuBar();
        menuBar.setMargin(new Insets(1, 1, 1, 1));
        setJMenuBar(menuBar);

        JMenu settingsMenu = new JMenu("Settings", true);
        menuBar.add(settingsMenu);

        JMenu appearanceMenu = new JMenu("Appearance", true);
        JMenu colorMenu = new JMenu("Board Colors", true);

        JColorChooser whiteColorChooser = new JColorChooser();
        AbstractColorChooserPanel[] panels = whiteColorChooser.getChooserPanels();
        for (AbstractColorChooserPanel panel : panels) {
            if (!panel.getDisplayName().equals("RGB")) {
                whiteColorChooser.removeChooserPanel(panel);
            }
        }
        whiteColorChooser.setPreviewPanel(new JPanel());
        colorMenu.add(whiteColorChooser);

        whiteColorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color newColor = whiteColorChooser.getColor();
                chessJPanel.setLightSquares(newColor);
            }
        });

        JColorChooser blackColorChooser = new JColorChooser();
        panels = blackColorChooser.getChooserPanels();
        for (AbstractColorChooserPanel panel : panels) {
            if (!panel.getDisplayName().equals("RGB")) {
                blackColorChooser.removeChooserPanel(panel);
            }
        }
        blackColorChooser.setPreviewPanel(new JPanel());
        colorMenu.add(blackColorChooser);

        blackColorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color newColor = blackColorChooser.getColor();
                chessJPanel.setDarkSquares(newColor);
            }
        });
        appearanceMenu.add(colorMenu);

        JMenu pieceMenu = new JMenu("Piece Skins", true);
        appearanceMenu.add(pieceMenu);

        menuBar.add(appearanceMenu);
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

    public static void main(String[] args) {
        MainJFrame mainJFrame = new MainJFrame();
        mainJFrame.setVisible(true);
    }
}
