import java.util.LinkedList;

public class Player {
    private final String color;
    private final LinkedList<Piece> pieces;
    private final LinkedList<Piece> capturedPieces;

    public Player(String color) {
        this.color = color;
        pieces = new LinkedList<>();
        capturedPieces = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Player " + capitalizeFirst(color);
    }

    public String getColor() {
        return color;
    }

    public LinkedList<Piece> getPieces() {
        return pieces;
    }

    public LinkedList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void initializePieces(LinkedList<Piece> lst) {
        pieces.clear();
        pieces.addAll(lst);
    }

    public void addPiece(Piece p) {
        pieces.add(p);
    }

    public void deletePiece(Piece p) {
        pieces.remove(p);
    }

    public void capturePiece(Piece p) {
        capturedPieces.add(p);
    }

    public static String capitalizeFirst(String s) {
        if (s == null || s.isEmpty())
            return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
