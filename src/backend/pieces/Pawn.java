package backend.pieces;

import java.util.HashSet;
import util.Position;
import backend.GameBoard;

public class Pawn extends Piece{

    //unique data members
    private boolean isFirstMove;
    private boolean canPromote;
    private boolean enPassantable;

    private boolean realSquare(int v1, int v2) {
        return v1 >= 0 && v1 <= 7 && v2 >= 0 && v2 <= 7;

    }

    public void setIsFirstMove(boolean b) {
        isFirstMove = b;
    }

    public boolean getCanPromote() {
        return canPromote;
    }

    public void setEnPassantable(boolean flag) {
        enPassantable = flag;
    }

    public void setCanPromote(boolean canPromote) {
        this.canPromote = canPromote;
    }

    public Pawn(String color) {
        this.color = color;
        pointValue = 1;
        isFirstMove = true;
        canPromote = false;
        enPassantable = false;
        this.imagePath = color.equals("white") ? "/pieces/Chess_plt60.png" : "/pieces/Chess_pdt60.png";
    }

    @Override
    public char getChar() {
        return 'P';
    }

    @Override
    public HashSet<Position> getAllMoves(Position p, GameBoard gb) {

        //local variables
        int x = p.getX();
        int y = p.getY();

        //output set
        HashSet<Position> moveSet = new HashSet<>();

        //black moves
        if (this.color.equals("black")) {

            //black passive moves
            if (realSquare(x + 1, y) && gb.getPieceAt(x + 1, y) == null) {
                moveSet.add(new Position(x + 1, y));

                //two space move
                if (realSquare(x + 2, y) && gb.getPieceAt(x + 2, y) == null && isFirstMove) {
                    moveSet.add(new Position(x + 2, y));
                }
            }

            //black hostile moves

            //right diagonal attack
            if (realSquare(x + 1, y + 1) && gb.getPieceAt(x + 1, y + 1) != null && !gb.getPieceAt(x + 1, y + 1).color.equals(this.color)) {
                moveSet.add(new Position(x + 1, y + 1));
            }

            //right en passant
            else if (realSquare(x, y + 1) && gb.getPieceAt(x, y + 1) != null && gb.getPieceAt(x, y + 1).getChar() == 'P') {
                Pawn pawn = (Pawn) gb.getPieceAt(x, y + 1);
                if (pawn.enPassantable) {
                    moveSet.add(new Position(x + 1, y + 1));
                }
            }

            //left diagonal attack
            if (realSquare(x + 1, y - 1) && gb.getPieceAt(x + 1, y - 1) != null && !gb.getPieceAt(x + 1, y - 1).color.equals(this.color)) {
                moveSet.add(new Position(x + 1, y - 1));
            }

            //left en passant
            else if (realSquare(x, y - 1) && gb.getPieceAt(x, y - 1) != null && gb.getPieceAt(x, y - 1).getChar() == 'P') {
                Pawn pawn = (Pawn) gb.getPieceAt(x, y - 1);
                if (pawn.enPassantable) {
                    moveSet.add(new Position(x + 1, y - 1));
                }
            }
        }



        //white moves
        if (this.color.equals("white")) {

            //white passive moves
            if (realSquare(x - 1, y) && gb.getPieceAt(x - 1, y) == null) {
                moveSet.add(new Position(x - 1, y));

                //two space move
                if (realSquare(x - 2, y) && gb.getPieceAt(x - 2, y) == null && isFirstMove) {
                    moveSet.add(new Position(x - 2, y));
                }
            }

            //white hostile moves

            //right diagonal attack
            if (realSquare(x - 1, y + 1) && gb.getPieceAt(x - 1, y + 1) != null && !gb.getPieceAt(x - 1, y + 1).color.equals(this.color)) {
                moveSet.add(new Position(x - 1, y + 1));
            }

            //right en passant
            else if (realSquare(x, y + 1) && gb.getPieceAt(x, y + 1) != null && gb.getPieceAt(x, y + 1).getChar() == 'P') {
                Pawn pawn = (Pawn) gb.getPieceAt(x, y + 1);
                if (pawn.enPassantable) {
                    moveSet.add(new Position(x - 1, y + 1));
                }
            }

            //left diagonal attack
            if (realSquare(x - 1, y - 1) && gb.getPieceAt(x - 1, y - 1) != null && !gb.getPieceAt(x - 1, y - 1).color.equals(this.color)) {
                moveSet.add(new Position(x - 1, y - 1));
            }

            //left en passant
            else if (realSquare(x, y - 1) && gb.getPieceAt(x, y - 1) != null && gb.getPieceAt(x, y - 1).getChar() == 'P') {
                Pawn pawn = (Pawn) gb.getPieceAt(x, y - 1);
                if (pawn.enPassantable) {
                    moveSet.add(new Position(x - 1, y - 1));
                }
            }
        }


        return moveSet;
    }
}
