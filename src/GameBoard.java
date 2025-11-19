import java.util.HashSet;

public class GameBoard {
    private final Player player1;
    private final Player player2;
    private final Piece[][] board = new Piece[8][8];
    private String currTurn;

    public GameBoard() {
        player1 = new Player("white");
        player2 = new Player("black");

        currTurn = "white";

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }

    public Piece getPieceAt(int x, int y) {
        return board[x][y];
    }

    public Position getKingPosition(String color) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] instanceof King) {
                    return new Position(i, j);
                }
            }
        }

        return new Position(0, 0);
    }

    public boolean makeMove(Piece piece, Position newPos) {
        if (piece == null || !piece.getColor().equals(currTurn)) return false;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == piece) {
                    if(newPos.equals(new Position(i, j))) return false;

                    HashSet<Position> moveSet = piece.getAllMoves(new Position(i, j), this);
                    if(moveSet.contains(newPos)) {
                        if(board[newPos.getX()][newPos.getY()] != null) {
                            if(currTurn.equals("white")) {
                                player1.capturePiece(board[newPos.getX()][newPos.getY()]);
                                player2.deletePiece(board[newPos.getX()][newPos.getY()]);
                            }
                            else {
                                player2.capturePiece(board[newPos.getX()][newPos.getY()]);
                                player1.deletePiece(board[newPos.getX()][newPos.getY()]);
                            }
                        }

                        board[newPos.getX()][newPos.getY()] = piece;
                        board[i][j] = null;
                        break;
                    }
                    else return false;
                }
            }
        }
        
        // Change current player turn
        currTurn = currTurn.equals("white") ? "black" : "white";
        return true;
    }
}
