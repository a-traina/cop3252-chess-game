import java.awt.Color;
import java.awt.Graphics;
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
import javax.swing.DefaultListModel;
import javax.swing.JPanel;

public class GridJPanel extends JPanel {
    private final GameBoard gameBoard;
    private final DefaultListModel<String> gameHistory;
    private Position selectedPosition;
    private HashSet<Position> highlightedMoves = new HashSet<>();

    // Cache images by path to avoid reloading each paint
    private static final Map<String, BufferedImage> imageCache = new HashMap<>();

    public GridJPanel(GameBoard gameBoard, DefaultListModel<String> gameHistory) {
        super();

        this.gameBoard = gameBoard;
        this.gameHistory = gameHistory;

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
                setBackground(new Color(125, 135, 150));
            } else {
                setBackground(new Color(232, 235, 239));
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (gameBoard.gameOver() != 0) return;

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
                                    gameHistory.addElement(gameBoard.getMoveHistory().getLast());

                                }
                            }

                            selectedPosition = null;
                            highlightedMoves.clear();
                        }
                    }

                    GridJPanel.this.repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (highlightedMoves.contains(new Position(row, col))) {
                g.setColor(new Color(0, 255, 0, 120));
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            if (selectedPosition != null && selectedPosition.getX() == row && selectedPosition.getY() == col) {
                g.setColor(new Color(255, 215, 0, 180)); // translucent gold
                g.fillRect(0, 0, getWidth(), 6); // top bar
                g.fillRect(0, 0, 6, getHeight()); // left bar
                g.fillRect(getWidth() - 6, 0, 6, getHeight()); // right bar
                g.fillRect(0, getHeight() - 6, getWidth(), 6); // bottom bar
            }

            Piece piece = gameBoard.getPieceAt(row, col);
            if (piece != null) {
                BufferedImage pieceImage = loadPieceImage(piece.getImagePath());
                if (pieceImage != null) {
                    g.drawImage(pieceImage, 0, 0, getWidth(), getHeight(), null);
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
}
