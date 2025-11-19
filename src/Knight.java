import java.util.HashSet;

public class Knight extends Piece {

    private boolean realSquare(int v1, int v2) {
        return v1 >= 0 && v1 <= 7 && v2 >= 0 && v2 <= 7;

    }

    public Knight(String color) {
        this.color = color;
        pointValue = 3;
    }

    @Override
    public HashSet<Position> getAllMoves(Position p, GameBoard gb) {

        //local variables
        int x = p.getX();
        int y = p.getY();

        //output set
        HashSet<Position> moveSet = new HashSet<>();

        //move logic
        if (realSquare(x - 1, y - 2) && (gb.getPieceAt(x - 1, y - 2) == null || !gb.getPieceAt(x - 1, y - 2).color.equals(this.color))) {
            moveSet.add(new Position(x - 1, y - 2));
        }

        if (realSquare(x - 2, y - 1) && (gb.getPieceAt(x - 2, y - 1) == null || !gb.getPieceAt(x - 2, y - 1).color.equals(this.color))) {
            moveSet.add(new Position(x - 2, y - 1));
        }

        if (realSquare(x - 2, y + 1) && (gb.getPieceAt(x - 2, y + 1) == null || !gb.getPieceAt(x - 2, y + 1).color.equals(this.color))) {
            moveSet.add(new Position(x - 2, y + 1));
        }

        if (realSquare(x - 1, y + 2) && (gb.getPieceAt(x - 1, y + 2) == null || !gb.getPieceAt(x - 1, y + 2).color.equals(this.color))) {
            moveSet.add(new Position(x - 1, y + 2));
        }

        if (realSquare(x + 1, y - 2) && (gb.getPieceAt(x + 1, y - 2) == null || !gb.getPieceAt(x + 1, y - 2).color.equals(this.color))) {
            moveSet.add(new Position(x + 1, y - 2));
        }

        if (realSquare(x + 2, y - 1) && (gb.getPieceAt(x + 2, y - 1) == null || !gb.getPieceAt(x + 2, y - 1).color.equals(this.color))) {
            moveSet.add(new Position(x + 2, y - 1));
        }

        if (realSquare(x + 2, y + 1) && (gb.getPieceAt(x + 2, y + 1) == null || !gb.getPieceAt(x + 2, y + 1).color.equals(this.color))) {
            moveSet.add(new Position(x + 2, y + 1));
        }

        if (realSquare(x + 1, y + 2) && (gb.getPieceAt(x + 1, y + 2) == null || !gb.getPieceAt(x + 1, y + 2).color.equals(this.color))) {
            moveSet.add(new Position(x + 1, y + 2));
        }

        return moveSet;
    }
}
