import java.util.HashSet;

public class Queen extends Piece {
    public Queen(String color) {
        pointValue = 9;
        this.color = color;
    }

    @Override
    public HashSet<Position> getAllMoves(Position p, GameBoard gb) {
        HashSet<Position> set = new HashSet<>();

        return set;
    }
}
