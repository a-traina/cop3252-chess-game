import java.awt.*;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;

public class MainJFrame extends JFrame {
    private ChessJPanel chessJPanel;
    private final GameOverJPanel gameOverJPanel;
    private GameBoard gameBoard;
    private final GameSettings settings;
    public MainJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.DARK_GRAY);

        gameBoard = new GameBoard();
        settings = new GameSettings();

        chessJPanel = new ChessJPanel(gameBoard, this, settings);
        add(chessJPanel);

        gameOverJPanel = new GameOverJPanel(this);
        gameOverJPanel.setVisible(false);
        setGlassPane(gameOverJPanel);

        ChessMenuBar menuBar = new ChessMenuBar();
        setJMenuBar(menuBar);
    }

    private class ChessMenuBar extends JMenuBar {
        private final JMenu settingsMenu;
        private final JMenu appearanceMenu;
        private final JMenu colorMenu;

        public ChessMenuBar() {
            super();
            setMargin(new Insets(1, 1, 1, 1));

            settingsMenu = new JMenu("Settings");
            appearanceMenu = new JMenu("Appearance");
            colorMenu = new JMenu("Board Colors");

            JColorChooser whiteColorChooser = new JColorChooser();
            AbstractColorChooserPanel[] panels = whiteColorChooser.getChooserPanels();
            for (AbstractColorChooserPanel panel : panels) {
                if (!panel.getDisplayName().equals("RGB")) {
                    whiteColorChooser.removeChooserPanel(panel);
                }
            }
            whiteColorChooser.setPreviewPanel(new JPanel());
            colorMenu.add(whiteColorChooser);

            whiteColorChooser.getSelectionModel().addChangeListener((ChangeEvent e) -> {
                    Color newColor = whiteColorChooser.getColor();
                    settings.setLightBoardColor(newColor);
                    chessJPanel.setLightSquares(newColor);
                }
            );

            JColorChooser blackColorChooser = new JColorChooser();
            panels = blackColorChooser.getChooserPanels();
            for (AbstractColorChooserPanel panel : panels) {
                if (!panel.getDisplayName().equals("RGB")) {
                    blackColorChooser.removeChooserPanel(panel);
                }
            }
            blackColorChooser.setPreviewPanel(new JPanel());
            colorMenu.add(blackColorChooser);

            blackColorChooser.getSelectionModel().addChangeListener((ChangeEvent e) -> {
                    Color newColor = blackColorChooser.getColor();
                    settings.setDarkBoardColor(newColor);
                    chessJPanel.setDarkSquares(newColor);
                }
            );
            appearanceMenu.add(colorMenu);

            JMenu pieceMenu = new JMenu("Piece Skins", true);
            appearanceMenu.add(pieceMenu);

            add(settingsMenu);
            add(appearanceMenu);
        }
    }

    public void showGameOver(boolean flag) {
        String result = gameBoard.getGameOverMsg();
        gameOverJPanel.setGameResult(result);
        gameOverJPanel.setVisible(flag);
    }

    public void resetGame() {
        remove(chessJPanel);

        gameBoard = new GameBoard();
        chessJPanel = new ChessJPanel(gameBoard, this, settings);
        add(chessJPanel);

        showGameOver(false);

        revalidate();
    }

    public static void main(String[] args) {
        MainJFrame mainJFrame = new MainJFrame();
        mainJFrame.setVisible(true);
    }
}
