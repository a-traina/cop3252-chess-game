import java.awt.*;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class ChessJFrame extends JFrame{
    private final JList<String> moveJList;
    private final GameBoard gameBoard;
    public ChessJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameBoard = new GameBoard();

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        DefaultListModel<String> gameHistory = new DefaultListModel<>();
        moveJList = new JList<>(gameHistory);

        //color formatting for move history
        moveJList.setBackground(Color.DARK_GRAY);
        moveJList.setForeground(Color.WHITE);
        moveJList.setSelectionBackground(Color.GRAY);
        moveJList.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(moveJList);

        //color formattng for scroll pane
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        scrollPane.setBackground(Color.DARK_GRAY);

        add(scrollPane, BorderLayout.EAST);

        GridJPanel gridJPanel = new GridJPanel(gameBoard, gameHistory);
        add(gridJPanel, BorderLayout.CENTER);

    }
    public static void main(String[] args) {

        //testing dark mode
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");

        ChessJFrame chessJFrame = new ChessJFrame();
        chessJFrame.setSize(800, 600);
        chessJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        chessJFrame.setVisible(true);
    }
}
