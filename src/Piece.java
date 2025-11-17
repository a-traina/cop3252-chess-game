import java.util.HashSet;

public abstract class Piece {
    protected int pointValue;
    protected String color;
    abstract HashSet<Position> getAllMoves(Position p, GameBoard gb);
}
