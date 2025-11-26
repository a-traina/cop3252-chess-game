import java.util.HashSet;

public class Bishop extends Piece {

    public Bishop(String color) {
        pointValue = 3;
        this.color = color;
        this.imagePath = color.equals("white") ? "/assets/Chess_blt60.png" : "/assets/Chess_bdt60.png";
    }

    @Override
    public char getChar() {
        return 'B';
    }

    @Override
    public HashSet<Position> getAllMoves(Position p, GameBoard gb) {

        //local variables
        int x = p.getX();
        int y = p.getY();

        //output set
        HashSet<Position> moveSet = new HashSet<>();

        //check for NE moves
        for (int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++) {
            Piece pieceAt = gb.getPieceAt(i, j);

            if (pieceAt == null) {
                moveSet.add(new Position(i, j));
            }
            else if (!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, j));
                break;
            }
            else
                break;
        }

        //check for NW moves
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            Piece pieceAt = gb.getPieceAt(i, j);

            if (pieceAt == null) {
                moveSet.add(new Position(i, j));
            }
            else if (!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, j));
                break;
            }
            else
                break;
        }

        //check for SE moves
        for (int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
            Piece pieceAt = gb.getPieceAt(i, j);

            if (pieceAt == null) {
                moveSet.add(new Position(i, j));
            }
            else if (!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, j));
                break;
            }
            else
                break;
        }

        //check for SW moves
        for (int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
            Piece pieceAt = gb.getPieceAt(i, j);

            if (pieceAt == null) {
                moveSet.add(new Position(i, j));
            }
            else if (!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, j));
                break;
            }
            else
                break;
        }

        return moveSet;
    }

}
