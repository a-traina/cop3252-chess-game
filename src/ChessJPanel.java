import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ChessJPanel extends JPanel{
    private final JTable moveTable;
    private final BannerJPanel player1Banner;
    private final BannerJPanel player2Banner;
    private final GameBoard gameBoard;
    private final JButton drawButton;
    private final JButton resignButton;
    private final Timer clock;
    private final EvalBar evalBar;
    private MainJFrame mainFrame;

    public ChessJPanel(GameBoard gb, MainJFrame mainFrame) {
        setOpaque(false);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        gameBoard = gb;
        this.mainFrame = mainFrame;

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

        //Grid Panel (Game Board)
        GridJPanel gridJPanel = new GridJPanel(gameBoard, gameHistoryTable, this);
        gridJPanel.setOpaque(false);

        // Player Banners
        player1Banner = new BannerJPanel(gameBoard.getPlayer("white"));
        player2Banner = new BannerJPanel(gameBoard.getPlayer("black"));

        player1Banner.getClockLabel().setForeground(Color.WHITE);
        player1Banner.getPlayerJLabel().setForeground(Color.WHITE);

         // Draw Button
        ImageIcon icon = new ImageIcon(getClass().getResource("assets/drawIcon.png"));
        Image scaledDrawIcon = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        final ImageIcon drawIcon = new ImageIcon(scaledDrawIcon);

        drawButton = new JButton("Offer Draw");

        // Resign Button
        icon = new ImageIcon(getClass().getResource("assets/resignIcon.png"));
        Image scaledResignIcon = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        final ImageIcon resignIcon = new ImageIcon(scaledResignIcon);

        resignButton = new JButton("Resign");

        drawButton.addActionListener((ActionEvent e) -> {
                int result = JOptionPane.showConfirmDialog(ChessJPanel.this, 
                    "Confirm Draw", 
                    "Draw", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    drawIcon
                );

                if(result == JOptionPane.YES_OPTION) {
                    gameBoard.setDraw(true);
                    drawButton.setEnabled(false);
                    resignButton.setEnabled(false);
                    gridJPanel.clearCellHighlighting();
                    if(gameBoard.gameOver() != 0)
                        showGameOver();
                }
            }
        );
        resignButton.addActionListener((ActionEvent e) -> {
                int result = JOptionPane.showConfirmDialog(ChessJPanel.this, 
                    gameBoard.getTurn().toString() + ": Confirm Resign", 
                    "Resign", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    resignIcon
                );

                if(result == JOptionPane.YES_OPTION) {
                    gameBoard.setResigned(gameBoard.getTurn().getColor());
                    resignButton.setEnabled(false);
                    drawButton.setEnabled(false);
                    gridJPanel.clearCellHighlighting();
                    if(gameBoard.gameOver() != 0)
                        showGameOver();
                }
            }
        );

        // Button formatting
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.setOpaque(false);
        buttonPanel.add(drawButton);
        buttonPanel.add(resignButton);

        setLayout(new BorderLayout(20, 20));
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout(5, 5));
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

        evalBar = new EvalBar();
        add(evalBar, BorderLayout.WEST);

    
        clock = new Timer(1000, e -> {
            long time = gameBoard.getTurn().getTimeRemaining() - 1000;
            gameBoard.getTurn().setTimeRemaining(time);

            if (gameBoard.gameOver() != 0) {
                ((Timer) e.getSource()).stop();
                player1Banner.getClockLabel().setForeground(Color.GRAY);
                player1Banner.getPlayerJLabel().setForeground(Color.GRAY);
                player2Banner.getClockLabel().setForeground(Color.GRAY);
                player2Banner.getPlayerJLabel().setForeground(Color.GRAY);
                deactivateButtons();
                gridJPanel.clearCellHighlighting();
                showGameOver();
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

    public void showGameOver() {
        mainFrame.showGameOver(true);
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

    public void deactivateButtons() {
        resignButton.setEnabled(false);
        drawButton.setEnabled(false);
    }

    public void updateEvalBar() {
        evalBar.setEval(gameBoard.calculateEval());
    }

    private class EvalBar extends JPanel {
        //local variables
        private double evaluation;
        private int targetEvaluation;
        private final int barWidth = 50;
        private Timer animationClock;

        public EvalBar() {
            evaluation = 0;
            targetEvaluation = 0;

            setPreferredSize(new Dimension(barWidth, getHeight()));
            setOpaque(false);

            animationClock = new Timer(16, e ->{

                if (evaluation != targetEvaluation) {
                    double difference = targetEvaluation - evaluation;
                    double step = Math.max(0.1, Math.abs(difference) / 10);

                    if (Math.abs(difference) <= step) {
                        evaluation = targetEvaluation;
                    }
                    else {
                        evaluation += (difference > 0) ? step : -step;
                    }
                    repaint();
                }
                else {
                    animationClock.stop();
                }
            });
        }

        public void setEval(int eval) {
            targetEvaluation = eval;
            if (!animationClock.isRunning()) {
                animationClock.start();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int height = getHeight();
            int width = getWidth();
            double rangedEval = Math.max(-20, Math.min(20, evaluation));
            double percentage = (rangedEval + 20) / 40.0;

            int whiteSection = (int) (height * percentage);

            //draw white section
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, getHeight() - whiteSection, barWidth, whiteSection);

            //draw black section
            g2d.setColor(new Color(40, 40, 40));
            g2d.fillRect(0, 0, barWidth, getHeight() - whiteSection);

            //draw point value
            if (evaluation > 0) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD | Font.ITALIC, 15));
                String textEval = "+" + ((Integer) (int) evaluation);
                FontMetrics fm = g2d.getFontMetrics();
                int textHeight = fm.getDescent();
                int textWidth = fm.stringWidth(textEval);
                g2d.drawString(textEval, (width - textWidth) / 2, this.getHeight() - textHeight - 5);
            }

            if (evaluation < 0) {
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD | Font.ITALIC, 15));
                String textEval = "+" + ((Integer) (int) (evaluation * -1));
                FontMetrics fm = g2d.getFontMetrics();
                int textHeight = fm.getAscent();
                int textWidth = fm.stringWidth(textEval);
                g2d.drawString(textEval, (width - textWidth) / 2, textHeight + 5);
            }
        }
    }
}
