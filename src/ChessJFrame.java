import java.awt.*;
import javax.swing.*;

public class ChessJFrame extends JFrame{
    private final JList<String> moveJList;
    private final GameBoard gameBoard;
    public ChessJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameBoard = new GameBoard();

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        //Move History
        DefaultListModel<String> gameHistory = new DefaultListModel<>();
        moveJList = new JList<>(gameHistory);

        //Move History Visual Formatting
        moveJList.setBackground(Color.DARK_GRAY);
        moveJList.setForeground(Color.WHITE);
        moveJList.setSelectionBackground(Color.GRAY);
        moveJList.setSelectionForeground(Color.WHITE);

        //Scroll Pane
        JScrollPane scrollPane = new JScrollPane(moveJList);

        //Scroll Pane Visual Formatting
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.DARK_GRAY);

        add(scrollPane, BorderLayout.EAST);

        //Grid Panel (Game Board)
        GridJPanel gridJPanel = new GridJPanel(gameBoard, gameHistory, this);
        gridJPanel.setBackground(Color.DARK_GRAY);

        add(gridJPanel, BorderLayout.CENTER);

    }

    public void scrollToBottom() {
        moveJList.ensureIndexIsVisible(moveJList.getModel().getSize() - 1);
    }

    public static void main(String[] args) {

        //dark mode for macOS
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");

        ChessJFrame chessJFrame = new ChessJFrame();
        chessJFrame.setSize(800, 600);
        chessJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        chessJFrame.setVisible(true);
    }
}
