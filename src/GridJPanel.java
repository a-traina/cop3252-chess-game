import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class GridJPanel extends JPanel {
    private final GameBoard gameBoard;
    private final DefaultTableModel gameHistory;
    private final ChessJPanel parentFrame;
    private Position selectedPosition;
    private HashSet<Position> highlightedMoves = new HashSet<>();

    private static final Map<String, BufferedImage> imageCache = new HashMap<>();

    public GridJPanel(GameBoard gameBoard, DefaultTableModel gameHistory, ChessJPanel parentFrame) {
        super();

        this.gameBoard = gameBoard;
        this.gameHistory = gameHistory;
        this.parentFrame = parentFrame;

        setLayout(new GridLayout(8, 8));

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                String color;
                if((i + j) % 2 == 0)
                    color = "white";
                else
                    color = "black";

                add(new SquareJPanel(i, j, color));
            }
        }
    }

    private class SquareJPanel extends JPanel {
        private final int row;
        private final int col;

        public SquareJPanel(int i, int j, String squareColor) {
            super();

            row = i;
            col = j;

            if (squareColor.equals("black")) {
                setBackground(new Color(185, 134, 99));
            } else {
                setBackground(new Color(236, 214, 177));
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (gameBoard.gameOver() != 0) {
                        return;
                    }

                    Piece clickedPiece = gameBoard.getPieceAt(row, col);

                    if (selectedPosition == null) {
                        if(clickedPiece == null || !clickedPiece.getColor().equals(gameBoard.getTurn().getColor())) {
                            return;
                        }
                        selectedPosition = new Position(row, col);
                        highlightedMoves = clickedPiece.getValidMoves(selectedPosition, gameBoard);
                    }
                    else {
                        if (clickedPiece != null && clickedPiece.getColor().equals(gameBoard.getTurn().getColor())) {
                            selectedPosition = new Position(row, col);
                            highlightedMoves = clickedPiece.getValidMoves(selectedPosition, gameBoard);
                        }
                        else {
                            Position target = new Position(row, col);
                            if (highlightedMoves.contains(target)) {
                                if (gameBoard.makeMove(selectedPosition, target)) {
                                    if (gameBoard.getTurn().getColor().equals("black")) {
                                        String move = gameBoard.getMoveHistory().get(gameBoard.getMoveNumber());
                                        String[] row = move.split(" ");
                                        gameHistory.addRow(row);
                                    } else {
                                        int lastIndex = gameHistory.getRowCount() - 1;
                                        String move = gameBoard.getMoveHistory().get(gameBoard.getMoveNumber() - 1);
                                        String[] row = move.split(" ");
                                        gameHistory.setValueAt(row[2], lastIndex, 2);
                                    }

                                    if(gameBoard.canPawnPromote()) {
                                        String choice = new PieceJOptionPane(gameBoard.getPawnPromoteColor()).showDialog(GridJPanel.this);

                                        switch(choice) {
                                            case "queen" -> {gameBoard.promotePawn(new Queen(gameBoard.getPawnPromoteColor()));}
                                            case "knight" -> {gameBoard.promotePawn(new Knight(gameBoard.getPawnPromoteColor()));}
                                            case "bishop" -> {gameBoard.promotePawn(new Bishop(gameBoard.getPawnPromoteColor()));}
                                            case "rook" -> {gameBoard.promotePawn(new Rook(gameBoard.getPawnPromoteColor()));}
                                        }
                                    }

                                    parentFrame.updatedCapturedPieces(gameBoard.getTurn().getColor().equals("white") ? "black" : "white");

                                    parentFrame.scrollToBottom();
                                    parentFrame.updateClockColors();

                                    if(gameBoard.gameOver() != 0) {
                                        parentFrame.deactivateButtons();
                                        parentFrame.showGameOver();
                                    }
                                }
                            }

                            selectedPosition = null;
                            highlightedMoves.clear();
                        }
                    }

                    GridJPanel.this.repaint();
                    parentFrame.updateEvalBar();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            if(col == 0) {
                String rowNum = Integer.toString(8 - row);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, getHeight() / 4));
                g2d.setColor((row + col) % 2 != 0 ? new Color(236, 214, 177) : new Color(185, 134, 99));

                FontMetrics fontMetrics = g2d.getFontMetrics();

                g2d.drawString(rowNum, 1, fontMetrics.getAscent());
            }

            if(row == 7) {
                String colLetters = "abcdefgh";
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, getHeight() / 4));
                g2d.setColor((row + col) % 2 != 0 ? new Color(236, 214, 177) : new Color(185, 134, 99));

                FontMetrics fontMetrics = g2d.getFontMetrics();

                String letter = Character.toString(colLetters.charAt(col));
                g2d.drawString(letter, getWidth() - fontMetrics.stringWidth(letter) - 1, getHeight() - fontMetrics.getDescent());
            }

            if (highlightedMoves.contains(new Position(row, col))) {
                g2d.setColor(new Color(0, 255, 0, 120));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            if (selectedPosition != null && selectedPosition.getX() == row && selectedPosition.getY() == col) {
                g2d.setColor(new Color(255, 215, 0, 180)); 
                g2d.setStroke(new BasicStroke(10));
                g2d.drawRect(0, 0, getWidth(), getHeight());
            }

            Piece piece = gameBoard.getPieceAt(row, col);
            if (piece != null) {
                BufferedImage pieceImage = loadPieceImage(piece.getImagePath());
                if (pieceImage != null) {
                    g2d.drawImage(pieceImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        }

        private BufferedImage loadPieceImage(String path) {
            if (imageCache.containsKey(path)) {
                return imageCache.get(path);
            }

            try {
                URL url = getClass().getResource(path);
                if (url == null) {
                    return null;
                }
                BufferedImage img = ImageIO.read(url);
                imageCache.put(path, img);
                return img;
            } catch (IOException e) {
                return null;
            }
        }
    }

    public void clearCellHighlighting() {
        selectedPosition = null;
        highlightedMoves.clear();
        repaint();
    }

    @Override
    public void doLayout() {
        int size = Math.min(getWidth(), getHeight());
        if(size >= 200)
            setSize(size, size);
        else
            setSize(200, 200);
        super.doLayout();
    }

}
