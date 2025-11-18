public class GameBoard {
    private Player player1;
    private Player player2;
    private Piece[][] board = new Piece[8][8];

    public GameBoard() {
        player1 = new Player("white");
        player2 = new Player("black");

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }

    public Piece getPieceAt(int x, int y) {
        return board[x][y];
    }
}
