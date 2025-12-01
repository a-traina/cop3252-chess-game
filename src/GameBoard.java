import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GameBoard {
    private final Player player1;
    private final Player player2;
    private final Piece[][] board = new Piece[8][8];
    private String currTurn;
    private int moveNumber;
    private final ArrayList<String> moveHistory;
    private PawnPromoteData pawnPromoteData;
    private boolean isDraw;
    private String resigned;
    private class PawnPromoteData {
        public Position pawnPos;
        public String pawnColor;
        public Pawn pawn;
    }
    private String gameOverMsg;

    public GameBoard() {
        player1 = new Player("white");
        player2 = new Player("black");
        moveHistory = new ArrayList<>();

        currTurn = "white";
        moveNumber = 0;
        pawnPromoteData = null;
        isDraw = false;
        resigned = null;
        gameOverMsg = "";

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
        moveHistory = new ArrayList<>();

        currTurn = "white";
        moveNumber = 0;
        pawnPromoteData = null;
        isDraw = false;
        resigned = null;
        gameOverMsg = "";
        
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

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getGameOverMsg() {
        return gameOverMsg;
    }

    public ArrayList<String> getMoveHistory() {
        return moveHistory;
    }

    public boolean canPawnPromote() {
        return pawnPromoteData != null;
    }

    public String getPawnPromoteColor() {
        return pawnPromoteData.pawnColor;
    }

    public void setDraw(boolean drawStatus) {
        isDraw = drawStatus;
    }

    public void setResigned(String resignee) {
        resigned = resignee;
    }

    public void promotePawn(Piece piece) {
        if(!canPawnPromote()) return;

        Player player = pawnPromoteData.pawnColor.equals("white") ? player1 : player2;
        player.deletePiece(pawnPromoteData.pawn);
        board[pawnPromoteData.pawnPos.getX()][pawnPromoteData.pawnPos.getY()] = piece;
        player.addPiece(piece);

        pawnPromoteData = null;
    }

    public int calculateEval() {
        int p1 = 0;
        int p2 = 0;

        for (Piece x : player1.getPieces()) {
            p1 += x.pointValue;
        }

        for (Piece x : player2.getPieces()) {
            p2 += x.pointValue;
        }

        return p1 - p2;
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
        if(isDraw) {
            gameOverMsg = "Draw";
            return 1;
        }
        if(resigned != null) {
            gameOverMsg = (resigned.equals("white") ? "Black" : "White");
            return 2;
        }

        //get current player
        Player currPlayer = currTurn.equals("white") ? player1 : player2;

        if (currPlayer.getTimeRemaining() <= 0) {
            gameOverMsg = (currPlayer.getColor().equals("white") ? "Black" : "White");
            return 2;
        }

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
        if (isInCheck(this.board)) {
            gameOverMsg = (currPlayer.getColor().equals("white") ? "Black" : "White");
            return 2;
        }

        //else they have been stalemated
        gameOverMsg = "Draw";
        return 1;
    }

    private String moveRecord(char piece, Position oldPos, Position newPos, boolean isCapture) {
        StringBuilder str = new StringBuilder();
        String cols = "abcdefgh";

        if(piece != 'P') {
            str.append(piece);
        }

        if(isCapture) {
            if(piece == 'P') {
                str.append(cols.charAt(oldPos.getY()));
            }
            str.append('x').append(cols.charAt(newPos.getY())).append(8 - newPos.getX());
        }
        else {
            str.append(cols.charAt(newPos.getY())).append(8 - newPos.getX());
        }
        
        return str.toString();
    }

    public boolean makeMove(Position oldPos, Position newPos) {
        Piece piece = getPieceAt(oldPos.getX(), oldPos.getY());
        
        if (piece == null || !piece.getColor().equals(currTurn)) return false;
        if(newPos.equals(oldPos)) return false;

        int i = oldPos.getX();
        int j = oldPos.getY();

        boolean isCapture = false;

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
                isCapture = true;
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
                        if(currTurn.equals("white")) {
                            String prefix = Integer.toString(moveNumber + 1) + ". ";
                            moveHistory.add(prefix + "O-O-O");
                        }

                        if(currTurn.equals("black")) {
                            String prevRecord = moveHistory.get(moveNumber);
                            String updateRecord = prevRecord + " " + "O-O-O";
                            moveHistory.set(moveNumber, updateRecord);
                            moveNumber++;
                        }
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
                        if(currTurn.equals("white")) {
                            String prefix = Integer.toString(moveNumber + 1) + ". ";
                            moveHistory.add(prefix + "O-O");
                        }

                        if(currTurn.equals("black")) {
                            String prevRecord = moveHistory.get(moveNumber);
                            String updateRecord = prevRecord + " " + "O-O";
                            moveHistory.set(moveNumber, updateRecord);
                            moveNumber++;
                        }
                        currTurn = currTurn.equals("white") ? "black" : "white";
                        return true;
                    }
                }
            }

            //en passant logic
            if (piece.getChar() == 'P') {
                if (currTurn.equals("white") && getPieceAt(newPos.getX(), newPos.getY()) == null && oldPos.getY() != newPos.getY()) {
                    player1.capturePiece(board[newPos.getX() + 1][newPos.getY()]);
                    player2.deletePiece(board[newPos.getX() + 1][newPos.getY()]);
                    board[newPos.getX() + 1][newPos.getY()] = null;
                    isCapture = true;
                }

                if (currTurn.equals("black") && getPieceAt(newPos.getX(), newPos.getY()) == null && oldPos.getY() != newPos.getY()) {
                    player2.capturePiece(board[newPos.getX() - 1][newPos.getY()]);
                    player1.deletePiece(board[newPos.getX() - 1][newPos.getY()]);
                    board[newPos.getX() - 1][newPos.getY()] = null;
                    isCapture = true;
                }
            }

            board[newPos.getX()][newPos.getY()] = piece;
            board[i][j] = null;



            Player tempPlayer = currTurn.equals("white") ? player1 : player2;
            for (Piece x : tempPlayer.getPieces()) {
                if (x.getChar() == 'P') {
                    Pawn pawn = (Pawn) x;
                    pawn.enPassantable = false;
                }
            }

            switch(piece.getChar()) {
                case 'P' -> {
                    Pawn pawn = (Pawn) piece;
                    pawn.setIsFirstMove(false);

                    if (Math.abs(newPos.getX() - oldPos.getX()) > 1) {
                        pawn.enPassantable = true;
                    }

                    if(pawn.getColor().equals("black") && newPos.getX() == 7) {
                        pawn.setCanPromote(true);
                    }
                    else if(pawn.getColor().equals("white") && newPos.getX() == 0) {
                        pawn.setCanPromote(true);
                    }

                    if(pawn.getCanPromote()) {
                        pawnPromoteData = new PawnPromoteData();
                        pawnPromoteData.pawnColor = currTurn;
                        pawnPromoteData.pawnPos = newPos;
                        pawnPromoteData.pawn = pawn;
                    }
                }
                case 'R' -> {
                    Rook rook = (Rook) piece;
                    rook.setIsFirstMove(false);
                }
                case 'K' -> {
                     King king = (King) piece;
                    king.setIsFirstMove(false);
                }
            }
        }
        else return false;
        
        // Log move in move history
        if(currTurn.equals("white")) {
            String prefix = Integer.toString(moveNumber + 1) + ". ";
            moveHistory.add(moveNumber, prefix + moveRecord(piece.getChar(), oldPos, newPos, isCapture));
        }

        if(currTurn.equals("black")) {
            String prevRecord = moveHistory.get(moveNumber);
            String updateRecord = prevRecord + " " + moveRecord(piece.getChar(), oldPos, newPos, isCapture);
            moveHistory.set(moveNumber, updateRecord);
            moveNumber++;
        }

        // Change current player turn
        currTurn = currTurn.equals("white") ? "black" : "white";
        return true;
    }
}
