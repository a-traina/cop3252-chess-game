import java.util.HashSet;
import java.util.LinkedList;
import java.lang.Math;

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

        initializePieces(player1);
        initializePieces(player2);

        loadBoard(player1);
        loadBoard(player2);
    }

    public GameBoard(Piece[][] board) {
        if(board.length != 8)
            throw new IllegalArgumentException("Argument must be 8x8 matrix");
        for(Piece[] row : board) {
            if(row.length != 8)
                throw new IllegalArgumentException("Argument must be 8x8 matrix");
        }

        player1 = new Player("white");
        player2 = new Player("black");

        currTurn = "white";
        
        for(int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, board[i].length);

            for(int j = 0; j < 8; j++) {
                Piece p = board[i][j];
                
                if( p == null) continue;

                if(p.getColor().equals("white")) {
                    player1.addPiece(p);
                }
                else {
                    player2.addPiece(p);
                }
            }
        }

    }

    private void initializePieces(Player p) {
        LinkedList<Piece> piecelst = new LinkedList<>();

        piecelst.add(new King(p.getColor()));
        piecelst.add(new Queen(p.getColor()));
        
        for(int i = 0; i < 2; i++) {
            piecelst.add(new Knight(p.getColor()));
            piecelst.add(new Rook(p.getColor()));
            piecelst.add(new Bishop(p.getColor()));
        }

        for(int i = 0; i < 8; i++) {
            piecelst.add(new Pawn(p.getColor()));
        }

        p.initializePieces(piecelst);
    }

    private void loadBoard(Player player) {
        LinkedList<Piece> pieces = player.getPieces();
        int backRow = player.getColor().equals("white") ? 7 : 0;
        int pawnRow = player.getColor().equals("white") ? 6 : 1;

        int pawnCount = 0, knightCount = 0, bishopCount = 0, rookCount = 0;

        for(Piece p : pieces) {
            char c = p.getChar();
            switch(c) {
                case 'K' -> board[backRow][4] = p;
                case 'Q'-> board[backRow][3] = p;
                case 'R'-> {
                    if(++rookCount == 1) {
                        board[backRow][0] = p;
                    }
                    else {
                        board[backRow][7] = p;
                    }
                }
                case 'N' -> {
                    if(++knightCount == 1) {
                        board[backRow][1] = p;
                    }
                    else {
                        board[backRow][6] = p;
                    }
                }
                case 'B' -> {
                    if(++bishopCount == 1) {
                        board[backRow][2] = p;
                    }
                    else {
                        board[backRow][5] = p;
                    }
                }
                case 'P'-> board[pawnRow][pawnCount++] = p;
            }
        }
    }

    public Piece getPieceAt(int x, int y) {
        return board[x][y];
    }

    public Position getPosition(Piece p) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == p) {
                    return new Position(i, j);
                }
            }
        }

        return null;
    }

    public Position getKingPosition(String color) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == null) continue;
                if(board[i][j].getChar() == 'K' && board[i][j].getColor().equals(color)) {
                    return new Position(i, j);
                }
            }
        }

        return new Position(0, 0);
    }

    public Player getPlayer(String color) {
        return color.equals("white") ? player1 : player2;
    }

    public Player getTurn() {
        return currTurn.equals("white") ? player1 : player2;
    }

    public boolean isInCheck(Piece[][] board) {
        // Make game board object for board passed in
        GameBoard simulate = new GameBoard(board);

        // Get opponent
        Player opponent = currTurn.equals("white") ? simulate.getPlayer("black") : simulate.getPlayer("white");

        // Get current player's king position
        Position kingPos = simulate.getKingPosition(currTurn);

        // Iterate through opponent pieces
        for(Piece p : opponent.getPieces()) {
            // Get opponent piece's position in simulated board
            Position piecePos = simulate.getPosition(p);
            
            // If no piece, skip
            if(piecePos == null) continue;

            // Get piece's attack rays
            HashSet<Position> moves = p.getAllMoves(piecePos, simulate);

            // if current player's king can be attacked, player in check
            if(moves.contains(kingPos)) return true;
        }

        // Player not in check
        return false;

    }

    //0 = game on; 1 = draw; 2 = currPlayer is lose
    public int gameOver() {

        //get current player
        Player currPlayer = currTurn.equals("white") ? player1 : player2;

        //get current players pieces
        LinkedList<Piece> currPieces = currPlayer.getPieces();

        //iterate through pieces
        for (Piece p : currPieces) {

            Position piecePos = this.getPosition(p);

            if (piecePos == null) continue;

            HashSet<Position> moves = p.getValidMoves(piecePos, this);

            //if piece has any valid move game continues
            if (!moves.isEmpty()) return 0;
        }

        //if no piece has a valid move and player is in check, they have been checkmated
        if (isInCheck(this.board)) return 2;

        //else they have been stalemated
        return 1;
    }

    public boolean makeMove(Position oldPos, Position newPos) {
        Piece piece = getPieceAt(oldPos.getX(), oldPos.getY());
        
        if (piece == null || !piece.getColor().equals(currTurn)) return false;
        if(newPos.equals(oldPos)) return false;

        int i = oldPos.getX();
        int j = oldPos.getY();

        HashSet<Position> moveSet = piece.getValidMoves(oldPos, this);
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

            //castle logic
            if (piece.getChar() == 'K') {

                //reset first move
                King king = (King) getPieceAt(oldPos.getX(), oldPos.getY());
                king.setIsFirstMove(false);

                if (Math.abs(newPos.getY() - oldPos.getY()) > 1) {
                    //Queenside
                    if (newPos.getY() < oldPos.getY()) {
                        board[newPos.getX()][newPos.getY()] = piece;
                        board[i][j] = null;

                        Piece rook = getPieceAt(newPos.getX(), 0);
                        board[newPos.getX()][3] = rook;
                        board[newPos.getX()][0] = null;
                        Rook r = (Rook) rook;
                        r.setIsFirstMove(false);

                        // Change current player turn
                        currTurn = currTurn.equals("white") ? "black" : "white";
                        return true;
                    }

                    //Kingside
                    if (newPos.getY() > oldPos.getY()) {
                        board[newPos.getX()][newPos.getY()] = piece;
                        board[i][j] = null;

                        Piece rook = getPieceAt(newPos.getX(), 7);
                        board[newPos.getX()][5] = rook;
                        board[newPos.getX()][7] = null;
                        Rook r = (Rook) rook;
                        r.setIsFirstMove(false);

                        // Change current player turn
                        currTurn = currTurn.equals("white") ? "black" : "white";
                        return true;
                    }
                }
            }

            board[newPos.getX()][newPos.getY()] = piece;
            board[i][j] = null;
        }
        else return false;
        
        // Change current player turn
        currTurn = currTurn.equals("white") ? "black" : "white";
        return true;
    }
}
