import java.util.Scanner;

public class App {
    public final GameBoard gameBoard;

    public App() {
        gameBoard = new GameBoard();
    }
    public static void main(String[] args) throws Exception {
        App app = new App();
        Scanner in = new Scanner(System.in);
        
        while (true) { 
            System.out.println();
            System.out.println();
            app.printBoard();
            Player player = app.gameBoard.getTurn();
            System.out.printf("Player %s : select your piece (row, column) >> ", player.getColor());
            int piecex = in.nextInt();
            int piecey = in.nextInt();

            System.out.print("Make your move (row, column) >> ");
            int movex = in.nextInt();
            int movey = in.nextInt();

            if(app.gameBoard.makeMove(new Position(piecex, piecey), new Position(movex, movey)) != true) {
                System.out.print("Invalid move. Try again.");
            }
        }
        
    }

    public void printBoard() {
        int rowCounter = 0;

        for(int i = 0; i < 8; i++) {
            System.out.printf("  %d ", i);
        }

        System.out.println();
        System.out.println("================================");

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j< 8; j++) {
                System.out.print("| ");
                Piece p = gameBoard.getPieceAt(i, j);
                if(p != null) System.out.print(p.getChar());
                else System.out.print(" ");
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.printf("%d\n", rowCounter++);
            System.out.println("================================");
        }
    }
}
