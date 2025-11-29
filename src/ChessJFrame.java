import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ChessJFrame extends JFrame{
    private final JTable moveTable;
    private final BannerJPanel player1Banner;
    private final BannerJPanel player2Banner;
    private final GameBoard gameBoard;
    private final Timer clock;

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
        DefaultTableModel gameHistoryTable = new DefaultTableModel(new String[]{"Turn", "White", "Black"}, 0);
        moveTable = new JTable(gameHistoryTable) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        moveTable.setShowGrid(false);
        moveTable.setIntercellSpacing(new Dimension(0, 0));
        moveTable.setCellSelectionEnabled(false);
        moveTable.setFocusable(false);
        moveTable.setTableHeader(null);
        moveTable.setRowHeight(25);
        moveTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if(row % 2 == 0) setBackground(new Color(51, 50, 48));
                else setBackground(new Color(78, 77, 76));

                setForeground(Color.LIGHT_GRAY);
                setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
                setBorder(new EmptyBorder(0, 7, 0, 0));

                return this;
            }
        });

        //Scroll Pane
        JScrollPane scrollPane = new JScrollPane(moveTable);
        scrollPane.setPreferredSize(new Dimension(450, 600));
        scrollPane.getViewport().setBackground(new Color(51, 50, 48));
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(30, 30, 30)));

        //add move history to box
        historyBox.add(scrollPane);

        //Grid Panel (Game Board)
        GridJPanel gridJPanel = new GridJPanel(gameBoard, gameHistoryTable, this);
        gridJPanel.setBackground(Color.DARK_GRAY);


        // Player Banners
        player1Banner = new BannerJPanel(gameBoard.getPlayer("white"));
        player2Banner = new BannerJPanel(gameBoard.getPlayer("black"));

        JLabel player1Clock = new JLabel(gameBoard.getPlayer("white").timeToString());
        JLabel player2Clock = new JLabel(gameBoard.getPlayer("black").timeToString());


        //add player banners and board to box
        boardBox.add(player2Banner);
        player2Banner.setAlignmentX(LEFT_ALIGNMENT);
        boardBox.add(Box.createRigidArea(new Dimension(0, 10)));
        boardBox.add(gridJPanel);
        gridJPanel.setAlignmentX(LEFT_ALIGNMENT);
        boardBox.add(Box.createRigidArea(new Dimension(0, 10)));
        boardBox.add(player1Banner);
        player1Banner.setAlignmentX(LEFT_ALIGNMENT);

        horizontalBox.add(boardBox);
        horizontalBox.add(Box.createRigidArea(new Dimension(10, 0)));
        horizontalBox.add(Box.createHorizontalGlue());
        horizontalBox.add(historyBox);

        add(horizontalBox);

        clock = new Timer(1000, e -> {
            long time = gameBoard.getTurn().getTimeRemaining() - 1000;
            gameBoard.getTurn().setTimeRemaining(time);
            if (gameBoard.getTurn().getColor().equals("white")) {
                player1Banner.getClockLabel().setText(gameBoard.getTurn().timeToString());
            }
            else {
                player2Banner.getClockLabel().setText(gameBoard.getTurn().timeToString());
            }

            if (time <= 0) {
                ((Timer) e.getSource()).stop();
                gameBoard.gameOver();
            }
        });
        clock.start();
    }

    public void scrollToBottom() {
        int rows = moveTable.getRowCount();
        if(rows > 0) {
            Rectangle rect = new Rectangle(moveTable.getCellRect(rows - 1, 0, true));
            moveTable.scrollRectToVisible(rect);
        }
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
