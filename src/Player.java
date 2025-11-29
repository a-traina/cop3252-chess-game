import java.util.LinkedList;

public class Player {
    private final String color;
    private final LinkedList<Piece> pieces;
    private final LinkedList<Piece> capturedPieces;
    private long timeRemaining;

    public Player(String color) {
        this.color = color;
        pieces = new LinkedList<>();
        capturedPieces = new LinkedList<>();
        timeRemaining = 1000 * 60 * 10;
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

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long t) {
        timeRemaining = t;
    }

    public String timeToString() {
        long totalSeconds = timeRemaining / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
    }
}
