import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public final GameBoard gameBoard;

    public App() {
        gameBoard = new GameBoard();
    }
    public static void main(String[] args) throws Exception {
        App app = new App();
        try(Scanner in = new Scanner(System.in)) {
        
        while (app.gameBoard.gameOver() == 0) { 
            System.out.println();
            System.out.println();
            app.printBoard();
            Player player = app.gameBoard.getTurn();
            System.out.printf("Player %s : select your piece (row, column) >> ", player.getColor());
            int piecex = in.nextInt();
            int piecey = in.nextInt();

            if(app.gameBoard.getPieceAt(piecex, piecey) == null) {
                continue;
            }

            // System.out.println("Valid Moves:");
            // for(Position pos : app.gameBoard.getPieceAt(piecex, piecey).getValidMoves(new Position(piecex, piecey), app.gameBoard)) {
            //     System.out.printf("(%d, %d)\n", pos.getX(), pos.getY());
            // }

            // System.out.println("ALL Moves (some invalid):");
            // for(Position pos : app.gameBoard.getPieceAt(piecex, piecey).getAllMoves(new Position(piecex, piecey), app.gameBoard)) {
            //     System.out.printf("(%d, %d)\n", pos.getX(), pos.getY());
            // }

            System.out.print("Make your move (row, column) >> ");
            int movex = in.nextInt();
            int movey = in.nextInt();

            if(app.gameBoard.makeMove(new Position(piecex, piecey), new Position(movex, movey)) != true) {
                System.out.print("Invalid move. Try again.");
                continue;
            }

            System.out.println("Move History: ");
            ArrayList<String> moveHistory = app.gameBoard.getMoveHistory();
            moveHistory.forEach( item -> System.out.println(item));
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
