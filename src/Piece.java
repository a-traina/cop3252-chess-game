import java.util.HashSet;

public abstract class Piece {
    protected int pointValue;
    protected String color;
    abstract public HashSet<Position> getAllMoves(Position p, GameBoard gb);
    abstract public char getChar();
    public String getColor() {
        return color;
    }
}
