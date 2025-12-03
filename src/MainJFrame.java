import java.awt.*;
import java.awt.event.ItemEvent;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;

public class MainJFrame extends JFrame {
    private ChessJPanel chessJPanel;
    private final GameOverJPanel gameOverJPanel;
    private final MenuJPanel menuPanel;
    private GameBoard gameBoard;
    private final GameSettings settings;
    public MainJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        getContentPane().setBackground(Color.DARK_GRAY);

        gameBoard = new GameBoard();
        settings = new GameSettings();

        menuPanel = new MenuJPanel(this);
        add(menuPanel);

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
        private final JMenu generalSettings;

        public ChessMenuBar() {
            super();
            setMargin(new Insets(1, 1, 1, 1));

            settingsMenu = new JMenu("Settings");
            appearanceMenu = new JMenu("Appearance");
            colorMenu = new JMenu("Board Colors");
            generalSettings = new JMenu("General");

            JRadioButton evalBarToggleButton = new JRadioButton("Toggle Evaluation Bar");
            evalBarToggleButton.setSelected(true);
            evalBarToggleButton.addItemListener((ItemEvent e) -> {
                settings.setToggleEvalBar(!settings.getToggleEvalBar());
                if(chessJPanel != null)
                    chessJPanel.toggleEvalBar(settings.getToggleEvalBar());
            });

           generalSettings.add(evalBarToggleButton);

            JRadioButton timerToggleButton = new JRadioButton("Toggle Timer");
            timerToggleButton.setSelected(true);
            timerToggleButton.addItemListener((ItemEvent e) -> {
                settings.setToggleTimer(!settings.getToggleTimer());
                if(chessJPanel != null)
                    chessJPanel.toggleTimer(settings.getToggleTimer());
            });

            generalSettings.add(timerToggleButton);
            settingsMenu.add(generalSettings);

            JMenuItem lightLabel = new JMenuItem("Light Square Color Selector:");
            lightLabel.setEnabled(false);
            colorMenu.add(lightLabel);

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
                    if(chessJPanel != null)
                        chessJPanel.setLightSquares(newColor);
                }
            );

            JMenuItem darkLabel = new JMenuItem("Dark Square Color Selector:");
            darkLabel.setEnabled(false);
            colorMenu.add(darkLabel);

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
                    if(chessJPanel != null)
                        chessJPanel.setDarkSquares(newColor);
                }
            );

            JMenuItem defaultColors = new JMenuItem("Reset to Default");
            defaultColors.addActionListener((e) -> {
                settings.resetColors();
                if(chessJPanel != null){
                    chessJPanel.setLightSquares(settings.getLightBoardColor());
                    chessJPanel.setDarkSquares(settings.getDarkBoardColor());
                }
            });

            colorMenu.add(defaultColors);

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

    public void startGame() {
        remove(menuPanel);

        chessJPanel = new ChessJPanel(gameBoard, this, settings);
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
