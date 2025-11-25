import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;

public class GridJPanel extends JPanel {
    private final GameBoard gameBoard;
    private final DefaultListModel<String> gameHistory;
    public GridJPanel(GameBoard gameBoard, DefaultListModel<String> gameHistory) {
        super();

        this.gameBoard = gameBoard;
        this.gameHistory = gameHistory;

        GridLayout gridLayout = new GridLayout(8, 8);
        setLayout(gridLayout);

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                String color;
                if((i + j) % 2 == 0)
                    color = "white";
                else
                    color = "black";

                add(new SquareJPanel(color, gameBoard.getPieceAt(i, j)));
            }
        }
    }

    private class SquareJPanel extends JPanel {
        private BufferedImage pieceImage;

        public SquareJPanel(String squareColor, Piece piece) {
            super();

            // Set background color
            if(squareColor.equals("black")) {
                setBackground(new Color(125, 135, 150));
            } else {
                setBackground(new Color(232, 235, 239));
            }

            // Load image if piece exists
            if(piece != null) {
                try {
                    pieceImage = ImageIO.read(getClass().getResource(piece.getImagePath()));
                }
                catch(IOException e) {
                    pieceImage = null;
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the piece image if it exists
            if(pieceImage != null) {
                g.drawImage(pieceImage, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }

}
