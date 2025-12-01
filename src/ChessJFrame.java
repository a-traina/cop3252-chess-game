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
    private final Timer clock;

    public ChessJFrame() {
        super("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);
        ((JComponent)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        gameBoard = new GameBoard();

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
        drawButton.addActionListener((ActionEvent e) -> {
                int result = JOptionPane.showConfirmDialog(ChessJFrame.this, "Confirm Draw", "Draw", JOptionPane.YES_NO_OPTION);

                if(result == JOptionPane.YES_OPTION) {
                    gameBoard.setDraw(true);
                }

                drawButton.setEnabled(false);
                drawButton.setEnabled(false);
            }
        );

        // Resign Button
        resignButton = new JButton("Resign");
        resignButton.addActionListener((ActionEvent e) -> {
                int result = JOptionPane.showConfirmDialog(ChessJFrame.this, gameBoard.getTurn().toString() + ": Confirm Resign", "Resign", JOptionPane.YES_NO_OPTION);

                if(result == JOptionPane.YES_OPTION) {
                    gameBoard.setResigned(gameBoard.getTurn().getColor());
                }

                resignButton.setEnabled(false);
                drawButton.setEnabled(false);
            }
        );

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
        // historyBox.setBorder(new EmptyBorder(0, 45, 0, 0));

        //Grid Panel (Game Board)
        GridJPanel gridJPanel = new GridJPanel(gameBoard, gameHistoryTable, this);
        gridJPanel.setBackground(Color.DARK_GRAY);


        // Player Banners
        player1Banner = new BannerJPanel(gameBoard.getPlayer("white"));
        player2Banner = new BannerJPanel(gameBoard.getPlayer("black"));

        player1Banner.getClockLabel().setForeground(Color.WHITE);
        player1Banner.getPlayerJLabel().setForeground(Color.WHITE);

        setLayout(new BorderLayout(10, 10));
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        center.add(gridJPanel, BorderLayout.CENTER);
        center.add(player1Banner, BorderLayout.SOUTH);
        center.add(player2Banner, BorderLayout.NORTH);
        center.setOpaque(false);
        add(center, BorderLayout.CENTER);

        JPanel east = new JPanel(new BorderLayout());
        east.add(scrollPane, BorderLayout.CENTER);
        east.add(buttonPanel, BorderLayout.SOUTH);
        east.setOpaque(false);
        add(east, BorderLayout.EAST);

    
        clock = new Timer(1000, e -> {
            long time = gameBoard.getTurn().getTimeRemaining() - 1000;
            gameBoard.getTurn().setTimeRemaining(time);

            if (gameBoard.gameOver() != 0) {
                ((Timer) e.getSource()).stop();
                player1Banner.getClockLabel().setForeground(Color.GRAY);
                player1Banner.getPlayerJLabel().setForeground(Color.GRAY);
                player2Banner.getClockLabel().setForeground(Color.GRAY);
                player2Banner.getPlayerJLabel().setForeground(Color.GRAY);
            }
            if (gameBoard.getTurn().getColor().equals("white")) {
                player1Banner.getClockLabel().setText(gameBoard.getTurn().timeToString());
            }
            else {
                player2Banner.getClockLabel().setText(gameBoard.getTurn().timeToString());
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

    public void updateClockColors() {
        if (gameBoard.getTurn().getColor().equals("white")) {
            player1Banner.getClockLabel().setForeground(Color.WHITE);
            player1Banner.getPlayerJLabel().setForeground(Color.WHITE);
            player2Banner.getClockLabel().setForeground(Color.GRAY);
            player2Banner.getPlayerJLabel().setForeground(Color.GRAY);
        }
        else {
            player2Banner.getClockLabel().setForeground(Color.WHITE);
            player2Banner.getPlayerJLabel().setForeground(Color.WHITE);
            player1Banner.getClockLabel().setForeground(Color.GRAY);
            player1Banner.getPlayerJLabel().setForeground(Color.GRAY);
        }
    }

    public static void main(String[] args) {
        ChessJFrame chessJFrame = new ChessJFrame();
        chessJFrame.setSize(800, 600);
        chessJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        chessJFrame.setVisible(true);
    }
}
