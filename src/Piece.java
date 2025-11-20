import java.util.HashSet;

public abstract class Piece {
    protected int pointValue;
    protected String color;
    abstract public HashSet<Position> getAllMoves(Position p, GameBoard gb);
    abstract public char getChar();

    public HashSet<Position> getValidMoves(Position p, GameBoard gb) {
        HashSet<Position> moves = getAllMoves(p, gb);
        Piece piece = gb.getPieceAt(p.getX(), p.getY());
        Piece[][] board = new Piece[8][8];
        
        // Copy game board
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = gb.getPieceAt(i, j);
            }
        }

        // For each move, update board copy and check for inCheck
        for(Position move : moves) {
            // Keep copy of old piece
            Piece tmp = board[move.getX()][move.getY()];

            // Simulate move
            board[move.getX()][move.getY()] = piece;
            board[p.getX()][p.getY()] = null;

            // Check for inCheck
            gb.isInCheck(board);

            // Reset board for next move
            board[p.getX()][p.getY()] = piece;
            board[move.getX()][move.getY()] = tmp;
        }

        return moves;
    }


    public String getColor() {
        return color;
    }
}
