import java.util.HashSet;

public class Pawn extends Piece{

    //unique data members
    boolean isFirstMove;

    Pawn(String color) {
        this.color = color;
        pointValue = 1;
        isFirstMove = true;
    }

    HashSet<Position> getAllMoves(Position p, GameBoard gb) {

        //local variables
        int x = p.getX();
        int y = p.getY();

        //output set
        HashSet<Position> moveSet = new HashSet<Position>();

        //black moves
        if (this.color.equals("black")) {

            //black passive moves
            if (gb.getPieceAt(x + 1, y) == null) {
                moveSet.add(new Position(x + 1, y));

                //two space move
                if (gb.getPieceAt(x + 2, y) == null && isFirstMove) {
                    moveSet.add(new Position(x + 2, y));
                }
            }

            //black hostile moves

            //right diagonal attack
            if (!gb.getPieceAt(x + 1, y + 1).color.equals(this.color)) {
                moveSet.add(new Position(x + 1, y + 1));
            }

            //left diagonal attack
            if (!gb.getPieceAt(x + 1, y - 1).color.equals(this.color)) {
                moveSet.add(new Position(x + 1, y - 1));
            }
        }



        //white moves
        if (this.color.equals("white")) {

            //white passive moves
            if (gb.getPieceAt(x - 1, y) == null) {
                moveSet.add(new Position(x - 1, y));

                //two space move
                if (gb.getPieceAt(x - 2, y) == null && isFirstMove) {
                    moveSet.add(new Position(x - 2, y));
                }
            }

            //white hostile moves

            //right diagonal attack
            if (!gb.getPieceAt(x - 1, y + 1).color.equals(this.color)) {
                moveSet.add(new Position(x - 1, y + 1));
            }

            //left diagonal attack
            if (!gb.getPieceAt(x - 1, y - 1).color.equals(this.color)) {
                moveSet.add(new Position(x - 1, y - 1));
            }
        }


        return moveSet;
    }
}
