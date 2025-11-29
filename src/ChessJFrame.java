import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChessJFrame extends JFrame{
    private final JList<String> moveJList;
    private final BannerJPanel player1Banner;
    private final BannerJPanel player2Banner;
    private final GameBoard gameBoard;
    public ChessJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);
        ((JComponent)getRootPane().getContentPane()).setBorder(new EmptyBorder(16, 16, 16, 16)); // top, left, bottom, right

        gameBoard = new GameBoard();

        Box horizontalBox = new Box(BoxLayout.X_AXIS);

        Box boardBox = new Box(BoxLayout.Y_AXIS);
        Box historyBox = new Box(BoxLayout.Y_AXIS);

        //Move History
        DefaultListModel<String> gameHistory = new DefaultListModel<>();
        moveJList = new JList<>(gameHistory);
        moveJList.setBackground(new Color(51, 50, 48));
        moveJList.setForeground(Color.WHITE);

        //Scroll Pane
        JScrollPane scrollPane = new JScrollPane(moveJList);
        scrollPane.setPreferredSize(new Dimension(350, 600));
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(51, 50, 48));

        //add move history to box
        historyBox.add(scrollPane);

        //Grid Panel (Game Board)
        GridJPanel gridJPanel = new GridJPanel(gameBoard, gameHistory, this);
        gridJPanel.setBackground(Color.DARK_GRAY);


        // Player Banners
        player1Banner = new BannerJPanel(gameBoard.getPlayer("white"));
        player2Banner = new BannerJPanel(gameBoard.getPlayer("black"));


        //add player banners and board to box
        boardBox.add(player1Banner);
        player1Banner.setAlignmentX(LEFT_ALIGNMENT);
        boardBox.add(Box.createRigidArea(new Dimension(0, 10)));
        boardBox.add(gridJPanel);
        gridJPanel.setAlignmentX(LEFT_ALIGNMENT);
        boardBox.add(Box.createRigidArea(new Dimension(0, 10)));
        boardBox.add(player2Banner);
        player2Banner.setAlignmentX(LEFT_ALIGNMENT);

        horizontalBox.add(boardBox);
        horizontalBox.add(Box.createRigidArea(new Dimension(10, 0)));
        horizontalBox.add(Box.createHorizontalGlue());
        horizontalBox.add(historyBox);

        add(horizontalBox);

    }

    public void scrollToBottom() {
        moveJList.ensureIndexIsVisible(moveJList.getModel().getSize() - 1);
    }

    public void updatedCapturedPieces(String color) {
        if(color.equals("white")) {
            player1Banner.updateCapturedPieces();
        }
        else if(color.equals("black")) {
            player2Banner.updateCapturedPieces();
        }
    }

    public static void main(String[] args) {
        ChessJFrame chessJFrame = new ChessJFrame();
        chessJFrame.setSize(800, 600);
        chessJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        chessJFrame.setVisible(true);
    }
}
