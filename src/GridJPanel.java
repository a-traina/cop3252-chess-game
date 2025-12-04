import java.awt.*;
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
    private final GameSettings settings;
    private final DefaultTableModel gameHistory;
    private final ChessJPanel parentFrame;
    private Position selectedPosition;
    private HashSet<Position> highlightedMoves = new HashSet<>();
    private final SoundEffect moveSound;
    private final SoundEffect captureSound;
    private final SoundEffect invalidSound;

    private static final Map<String, BufferedImage> imageCache = new HashMap<>();

    public GridJPanel(GameBoard gameBoard, DefaultTableModel gameHistory, ChessJPanel parentFrame, GameSettings settings) {
        super();

        this.gameBoard = gameBoard;
        this.settings = settings;
        this.gameHistory = gameHistory;
        this.parentFrame = parentFrame;
        moveSound = new SoundEffect(getClass().getResource("/assets/moveSound.wav"));
        captureSound = new SoundEffect(getClass().getResource("/assets/captureSound.wav"));
        invalidSound = new SoundEffect(getClass().getResource("/assets/invalidSound.wav"));



        setLayout(new GridLayout(8, 8));

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Color color;
                if((i + j) % 2 == 0)
                    color = settings.getLightBoardColor();
                else
                    color = settings.getDarkBoardColor();

                add(new SquareJPanel(i, j, color));
            }
        }
    }

    public void setLightBoardColors(Color c) {
        for (Component comp : getComponents()) {
            if(comp instanceof SquareJPanel square) {
                square.setLightColor(c);
            }
        }
    }

    public void setDarkBoardColors(Color c) {
        for (Component comp : getComponents()) {
            if(comp instanceof SquareJPanel square) {
                square.setDarkColor(c);
            }
        }
    }

    public void toggleSoundFx(boolean flag) {
        settings.setToggleSoundFX(flag);
    }

    private class SquareJPanel extends JPanel {
        private final int row;
        private final int col;
        private Color lightColor;
        private Color darkColor;

        public SquareJPanel(int i, int j, Color squareColor) {
            super();

            row = i;
            col = j;

            lightColor = settings.getLightBoardColor();
            darkColor = settings.getDarkBoardColor();

            setBackground(squareColor);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Piece p = gameBoard.getPieceAt(row, col);

                    if (p != null && p.getColor().equals(gameBoard.getTurn().getColor()) && selectedPosition == null 
                        || selectedPosition != null && highlightedMoves.contains(new Position(row, col))) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(Cursor.getDefaultCursor());
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });


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

                                    if(settings.getToggleSoundFX() && !gameBoard.canPawnPromote() && !gameBoard.getIsCaptureMove())
                                        moveSound.play();
                                    else if (settings.getToggleSoundFX() && !gameBoard.canPawnPromote() && gameBoard.getIsCaptureMove())
                                        captureSound.play();

                                    setCursor(Cursor.getDefaultCursor());
                                    if (gameBoard.getTurn().getColor().equals("white")) {
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
                                        if (settings.getToggleSoundFX())
                                            moveSound.play();
                                    }

                                    parentFrame.updatedCapturedPieces(gameBoard.getTurn().getColor());
                                    parentFrame.updateEvalBar();
                                    parentFrame.scrollToBottom();
                                    gameBoard.switchTurns();

                                    if(gameBoard.gameOver() != 0) {
                                        parentFrame.stopClock();
                                        parentFrame.deactivateButtons();
                                        parentFrame.showGameOver();
                                        clearCellHighlighting();
                                        return;
                                    }

                                    parentFrame.updateBannerLabels();
                                }
                            }
                            else {
                                if (settings.getToggleSoundFX())
                                    invalidSound.play();
                            }

                            selectedPosition = null;
                            highlightedMoves.clear();
                        }
                    }

                    GridJPanel.this.repaint();
                }
            });
        }

        public void setLightColor(Color c) {
            lightColor = c;
            if ((row + col) % 2 == 0) {
                setBackground(lightColor);
            }
            repaint();
        }

        public void setDarkColor(Color c) {
            darkColor = c;
            if ((row + col) % 2 != 0) {
                setBackground(darkColor);
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            if(col == 0) {
                String rowNum = Integer.toString(8 - row);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, getHeight() / 4));
                g2d.setColor((row + col) % 2 != 0 ? lightColor : darkColor);

                FontMetrics fontMetrics = g2d.getFontMetrics();

                g2d.drawString(rowNum, 1, fontMetrics.getAscent());
            }

            if(row == 7) {
                String colLetters = "abcdefgh";
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, getHeight() / 4));
                g2d.setColor((row + col) % 2 != 0 ? lightColor : darkColor);

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
