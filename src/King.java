import java.util.HashSet;

public class King extends Piece {
    private boolean isFirstMove;

    public King(String color) {
        pointValue = 0;
        this.color = color;
        isFirstMove = true;
        this.imagePath = color.equals("white") ? "/assets/Chess_klt60.png" : "/assets/Chess_kdt60.png";
    }

    @Override
    public char getChar() {
        return 'K';
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

        //castle
        //Queenside
        Piece piece;
        if (gb.getPieceAt(p.getX(), 0) != null) {
            piece = gb.getPieceAt(p.getX(), 0);
            if (piece.getChar() == 'R') {
                Rook r = (Rook) piece;
                if (r.canCastle(new Position(p.getX(), 0), gb) && this.canCastle(gb)) {
                    moveSet.add(new Position(p.getX(), p.getY() - 2));
                }
            }
        }

        //Kingside
        if (gb.getPieceAt(p.getX(), 7) != null) {
            piece = gb.getPieceAt(p.getX(), 7);
            if (piece.getChar() == 'R') {
                Rook r = (Rook) piece;
                if (r.canCastle(new Position(p.getX(), 7), gb) && this.canCastle(gb)) {
                    moveSet.add(new Position(p.getX(), p.getY() + 2));
                }
            }
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
