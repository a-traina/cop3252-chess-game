import java.util.HashSet;

public class Queen extends Piece {
    public Queen(String color) {
        pointValue = 9;
        this.color = color;
    }

    @Override
    public HashSet<Position> getAllMoves(Position p, GameBoard gb) {
        HashSet<Position> moveSet = new HashSet<>();

        int x = p.getX();
        int y = p.getY();

        for(int i = x + 1; i < 8; i++) {
            Piece pieceAt = gb.getPieceAt(i, y);
            if(pieceAt == null) {
                moveSet.add(new Position(i, y));
            }
            else if(!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, y));
                break;
            }
            else {
                break;
            }
        }

        for(int i = x - 1; i >= 0; i--) {
            Piece pieceAt = gb.getPieceAt(i, y);
            if(pieceAt == null) {
                moveSet.add(new Position(i, y));
            }
            else if(!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, y));
                break;
            }
            else {
                break;
            }
        }

        for(int j = y - 1; j >= 0; j--) {
            Piece pieceAt = gb.getPieceAt(x, j);
            if(pieceAt == null) {
                moveSet.add(new Position(x, j));
            }
            else if(!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(x, j));
                break;
            }
            else {
                break;
            }
        }

        for(int j = y + 1; j < 8; j++) {
            Piece pieceAt = gb.getPieceAt(x, j);
            if(pieceAt == null) {
                moveSet.add(new Position(x, j));
            }
            else if(!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(x, j));
                break;
            }
            else {
                break;
            }
        }

        for(int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
            Piece pieceAt = gb.getPieceAt(i, j);
            if(pieceAt == null) {
                moveSet.add(new Position(i, j));
            }
            else if(!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, j));
                break;
            }
            else {
                break;
            }
        }

        for(int i = x - 1, j = y + 1; i >=0 && j < 8; i--, j++) {
            Piece pieceAt = gb.getPieceAt(i, j);
            if(pieceAt == null) {
                moveSet.add(new Position(i, j));
            }
            else if(!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, j));
                break;
            }
            else {
                break;
            }
        }

        for(int i = x - 1, j = y - 1; i >=0 && j >= 0; i--, j--) {
            Piece pieceAt = gb.getPieceAt(i, j);
            if(pieceAt == null) {
                moveSet.add(new Position(i, j));
            }
            else if(!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, j));
                break;
            }
            else {
                break;
            }
        }

        for(int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
            Piece pieceAt = gb.getPieceAt(i, j);
            if(pieceAt == null) {
                moveSet.add(new Position(i, j));
            }
            else if(!pieceAt.color.equals(this.color)) {
                moveSet.add(new Position(i, j));
                break;
            }
            else {
                break;
            }
        }

        return moveSet;
    }
}
