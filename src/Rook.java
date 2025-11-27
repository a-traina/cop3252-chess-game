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
        int x = p.getX();
        int y = p.getY();

        int kingy = gb.getKingPosition(this.color).getY();

        if(isFirstMove) {

            //Queen side castle
            if (y < kingy) {

                //check for clear path
                for(int j = y + 1; j < kingy; j++) {
                    if(gb.getPieceAt(x, j) != null) {
                        return false;
                    }
                }

                //check if king moves through attack
                for (int k = 1; k < 3; k++) {

                    Piece[][] board = new Piece[8][8];

                    // Copy game board
                    for(int i = 0; i < 8; i++) {
                        for(int j = 0; j < 8; j++) {
                            if (gb.getPieceAt(i, j) != null) {
                                if (gb.getPieceAt(i, j).getChar() == 'K' && gb.getPieceAt(i, j).getColor().equals(this.color)) {
                                    continue;
                                }
                            }

                            Piece piece = gb.getPieceAt(i, j);

                            // If it's a rook, create a new rook with isFirstMove = false
                            if (piece != null && piece.getChar() == 'R') {
                                Rook tempRook = new Rook(piece.getColor());
                                tempRook.setIsFirstMove(false);
                                board[i][j] = tempRook;
                            } else {
                                board[i][j] = piece;
                            }
                        }
                    }

                    //for black king
                    if (this.color.equals("black")) {
                        King king = new King("black");
                        king.setIsFirstMove(false);
                        board[0][4 - k] = king;
                    }

                    //for white king
                    if (this.color.equals("white")) {
                        King king = new King("white");
                        king.setIsFirstMove(false);
                        board[7][4 - k] = king;
                    }

                    if (gb.isInCheck(board)) {
                        return false;
                    }
                }
                return true;
            }

            //King side castle
            else {

                //check for clear path
                for(int j = y - 1; j > kingy; j--) {
                    if(gb.getPieceAt(x, j) != null) {
                        return false;
                    }
                }

                //check if king moves through attack
                for (int k = 1; k < 3; k++) {

                    Piece[][] board = new Piece[8][8];

                    // Copy game board
                    for(int i = 0; i < 8; i++) {
                        for(int j = 0; j < 8; j++) {
                            if (gb.getPieceAt(i, j) != null) {
                                if (gb.getPieceAt(i, j).getChar() == 'K' && gb.getPieceAt(i, j).getColor().equals(this.color)) {
                                    continue;
                                }
                            }

                            Piece piece = gb.getPieceAt(i, j);

                            // If it's a rook, create a new rook with isFirstMove = false
                            if (piece != null && piece.getChar() == 'R') {
                                Rook tempRook = new Rook(piece.getColor());
                                tempRook.setIsFirstMove(false);
                                board[i][j] = tempRook;
                            } else {
                                board[i][j] = piece;
                            }
                        }
                    }

                    //for black king
                    if (this.color.equals("black")) {
                        King king = new King("black");
                        king.setIsFirstMove(false);
                        board[0][4 + k] = king;
                    }

                    //for white king
                    if (this.color.equals("white")) {
                        King king = new King("white");
                        king.setIsFirstMove(false);
                        board[7][4 + k] = king;
                    }

                    if (gb.isInCheck(board)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
