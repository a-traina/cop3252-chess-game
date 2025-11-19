import java.util.HashSet;

public abstract class Piece {
    protected int pointValue;
    protected String color;
    abstract public HashSet<Position> getAllMoves(Position p, GameBoard gb);
    public String getColor() {
        return color;
    }
}
