import java.util.HashSet;

public class King extends Piece {
    private boolean isFirstMove;

    public King(String color) {
        pointValue = 0;
        this.color = color;
        isFirstMove = true;
    }

    @Override
    public HashSet<Position> getAllMoves(Position p, GameBoard gb) {
        HashSet<Position> moveSet = new HashSet<>();

        int x = p.getX();
        int y = p.getY();

        // Check South
        if(x < 7 && (gb.getPieceAt(x + 1, y) == null || !gb.getPieceAt(x + 1, y).color.equals(this.color))) {
            moveSet.add(new Position(x + 1, y));
        }
        // Check Southeast
        if(x < 7 && y < 7 && (gb.getPieceAt(x + 1, y + 1) == null || !gb.getPieceAt(x + 1, y + 1).color.equals(this.color))) {
            moveSet.add(new Position(x + 1, y + 1));
        }
        // Check Southwest
        if(x < 7 && y > 0 && (gb.getPieceAt(x + 1, y - 1) == null || !gb.getPieceAt(x + 1, y - 1).color.equals(this.color))) {
            moveSet.add(new Position(x + 1, y - 1));
        }
        // Check East
        if(y < 7 && (gb.getPieceAt(x, y + 1) == null || !gb.getPieceAt(x, y + 1).color.equals(this.color))) {
            moveSet.add(new Position(x, y + 1));
        }
        // Check West
        if(y > 0 && (gb.getPieceAt(x, y - 1) == null || !gb.getPieceAt(x, y - 1).color.equals(this.color))) {
            moveSet.add(new Position(x, y - 1));
        }
        // Check North
        if(x > 0 && (gb.getPieceAt(x - 1, y) == null || !gb.getPieceAt(x - 1, y).color.equals(this.color))) {
            moveSet.add(new Position(x - 1, y));
        }
        // Check Northeast
        if(x > 0 && y < 7 && (gb.getPieceAt(x - 1, y + 1) == null || !gb.getPieceAt(x - 1, y + 1).color.equals(this.color))) {
            moveSet.add(new Position(x - 1, y + 1));
        }
        // Check Northwest
        if(x > 0 && y > 0 && (gb.getPieceAt(x - 1, y - 1) == null || !gb.getPieceAt(x - 1, y - 1).color.equals(this.color))) {
            moveSet.add(new Position(x - 1, y - 1));
        }

        return moveSet;
    }

    public void setIsFirstMove(boolean fMove) {
        isFirstMove = fMove;
    }

    public boolean canCastle(GameBoard gb) {
        return isFirstMove;
    }
}
