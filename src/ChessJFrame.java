import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ChessJFrame extends JFrame{
    private final JTable moveTable;
    private final BannerJPanel player1Banner;
    private final BannerJPanel player2Banner;
    private final GameBoard gameBoard;
    private final JButton drawButton;
    private final JButton resignButton;
    public ChessJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);
        ((JComponent)getRootPane().getContentPane()).setBorder(new EmptyBorder(16, 16, 16, 16)); // top, left, bottom, right

        gameBoard = new GameBoard();

        Box horizontalBox = Box.createHorizontalBox();

        Box boardBox = Box.createVerticalBox();
        Box historyBox = Box.createVerticalBox();

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
        scrollPane.getViewport().setBackground(new Color(51, 50, 48));
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(30, 30, 30)));

         // Draw Button
        drawButton = new JButton("Offer Draw");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(ChessJFrame.this, "Confirm Draw", "Draw", JOptionPane.YES_NO_OPTION);

                if(result == JOptionPane.YES_OPTION) {
                    gameBoard.setDraw(true);
                }
            }
        });

        // Resign Button
        resignButton = new JButton("Resign");
        resignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(ChessJFrame.this, gameBoard.getTurn().toString() + ": Confirm Resign", "Resign", JOptionPane.YES_NO_OPTION);

                if(result == JOptionPane.YES_OPTION) {
                    gameBoard.setResigned(gameBoard.getTurn().getColor());
                }
            }
        });

        // Button formatting
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(drawButton);
        buttonPanel.add(resignButton);

        //add move history to box
        historyBox.add(scrollPane);
        historyBox.add(Box.createRigidArea(new Dimension(0, 5)));
        historyBox.add(buttonPanel);

        //Grid Panel (Game Board)
        GridJPanel gridJPanel = new GridJPanel(gameBoard, gameHistoryTable, this);
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
