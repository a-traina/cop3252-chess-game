import java.util.HashSet;

public class Rook extends Piece {
    private boolean isFirstMove;

    public Rook(String color) {
        pointValue = 5;
        this.color = color;
        isFirstMove = true;
        this.imagePath = color.equals("white") ? "/assets/Chess_rlt60.png" : "/assets/Chess_rdt60.png";
    }

    @Override
    public char getChar() {
        return 'R';    
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

        return moveSet;
    }

    public void setIsFirstMove(boolean fMove) {
        this.isFirstMove = fMove;
    }

    public boolean canCastle(Position p, GameBoard gb) {
        // TODO: Implement the logic to check if King will be in check

        int x = p.getX();
        int y = p.getY();

        int kingy = gb.getKingPosition(this.color).getY();

        if(isFirstMove) {
            if(y < kingy) {
                for(int j = y; j < kingy; j++) {
                    if(gb.getPieceAt(x, j) != null) {
                        return false;
                    }
                }
            }
            else {
                for(int j = y; j > kingy; j--) {
                    if(gb.getPieceAt(x, j) != null) {
                        return false;
                    }
                }
            }

            return true;
        }
        return false;
    }
}
