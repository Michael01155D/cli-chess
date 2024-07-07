import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class GameBoard {

    final int BOARDSIZE = 8;
    //maps to quickly convert rows/cols as they're labeled on the board to corresponding indicies of 2d array. ex: "a6" would be Board[2, 0]
    final Map<String, Integer> COL_INDICIES = Map.of( 
        "a", 0,
        "b", 1,
        "c", 2,
        "d", 3,
        "e", 4,
        "f", 5,
        "g", 6,
        "h", 7
    );

    final Map<Integer, Integer> ROW_INDICIES = Map.of(
        8, 0,
        7, 1,
        6, 2,
        5, 3,
        4, 4,
        3, 5,
        2, 6,
        1, 7
    );

    private Piece[][] board;
    ArrayList<Piece> activeWhitePieces;
    ArrayList<Piece> activeBlackPieces;
    Scanner inputScanner;    
    public GameBoard(){
        this.inputScanner = new Scanner(System.in);
        this.board = new Piece[BOARDSIZE][BOARDSIZE];
        this.activeWhitePieces = createPieces("white");
        this.activeBlackPieces = createPieces("black");
        setUpBoard();
    }

    //return array of pieces in following order: rook, knight, bishop, queen, king, bishop, knight, rook, pawns
    private ArrayList<Piece> createPieces(String color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        int row = color.equals("white") ? 7 : 0;
        int col = 0;
        pieces.add(new Rook(color, new int[] {row, col++}));
        pieces.add(new Knight(color, new int[] {row, col++}));
        pieces.add(new Bishop(color, new int[] {row, col++}));
        pieces.add(new Queen(color, new int[] {row, col++}));
        pieces.add(new King(color, new int[] {row, col++}));
        pieces.add(new Bishop(color, new int[] {row, col++}));
        pieces.add(new Knight(color, new int[] {row, col++}));
        pieces.add(new Rook(color, new int[] {row, col}));
        row = color.equals("white") ? row - 1 : row + 1;
        for (col = 0; col < 8; col++) {
            pieces.add(new Pawn(color, new int[] {row, col}));
        }
        return pieces;

    }

    public Piece[][] getBoard() {
        return this.board;
    }

    public void setUpBoard() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = this.activeBlackPieces.get(j + (i * 8)); //on 2nd iteration, get indicies 8 -> 15
                this.board[7 - i][j] = this.activeWhitePieces.get(j + (i *8));
            }
        }
    };

    public void displayBoard() {
        String lineBreak = "   --------------------------------";
        System.out.println("    a   b   c   d   e   f   g   h");
        System.out.println(lineBreak);
        for (int i = 0; i < board.length; i++){
            System.out.print(8 - i + " |");
            for (Piece piece: board[i]){
                String toPrint = piece == null ? " " : piece.toString().substring(0, 1);
                System.out.print(" " + toPrint + " |");
            }
            System.out.println(" " + (8 - i));
            System.out.println(lineBreak);
        }
        System.out.println("    a   b   c   d   e   f   g   h");
    }

    //to (probably) be refactored into Player class:

    public Piece getPiece(int row, int col) {
        return getBoard()[row][col];
    }
    
    //get user input for next move in format "col""row" (ex: a5). then check if valid, if valid, move piece, update board
    //todos: 1. loop until input is a valid move. 2. add in the concept of check and capturing pieces. 
    public void movePiece(Piece piece) {
        piece.findValidMoves(getBoard());
        ArrayList<Integer[]> validMoves = piece.getValidMoves();
        if (validMoves.isEmpty()) {
            System.out.println("This piece has no valid moves available, please choose another");
            return;
        }
        int[] prevPosition = piece.getPosition();
        int prevRow = prevPosition[0];
        int prevCol = prevPosition[1];
        //loop through hashmap keysets to display current position of piece on the board:
        String boardCol = "";
        int boardRow = 0;
        for (String colName: COL_INDICIES.keySet()) {
            if (COL_INDICIES.get(colName) == prevCol) {
                boardCol = colName;
                break;
            }
        }
        for (int rowNum: ROW_INDICIES.keySet()) {
            if (ROW_INDICIES.get(rowNum) == prevRow) {
                boardRow = rowNum;
                break;
            }
        }
        System.out.println("Where will you move the " + piece + " positioned at " + boardCol + boardRow +"?");
        String col = "";
        String row = "-1"; //-1 to allow parseInt on first check
        while (!COL_INDICIES.containsKey(col)) {
            System.out.println("Please enter the column to move to (a to h) :");
            col = inputScanner.nextLine();
        }
        while (row.isEmpty() || !("12345678".contains(row))) {
            System.out.println("Please enter the row to move to (1 to 8)");
            row = inputScanner.nextLine();
        }
        for (Integer[] validMove: validMoves) {
            int newRowIndex = ROW_INDICIES.get(Integer.parseInt(row));
            int newColIndex = COL_INDICIES.get(col);
            if (validMove[0] == newRowIndex && validMove[1] == newColIndex) {
                System.out.println("Moving piece to " + col + row); 
                piece.setPosition(new int[] {newRowIndex, newColIndex} );
                this.board[prevRow][prevCol] = null;
                this.board[newRowIndex][newColIndex] = piece;
                displayBoard();
                return;
            }
            //todo: refactor so that the user re-enters input until a valid move is given.
            System.out.println("move wasnt valid :(");
        } 


    }

    //temporary?
    public void seeValidMoves(Piece piece) {
        piece.findValidMoves(getBoard());
        ArrayList<Integer[]> validMoves = piece.getValidMoves();
        System.out.println("valid moves of " +piece.getColor() + " " + piece + " are: ");
        for (Integer[] move : validMoves){
            System.out.print("("+ move[0] + ", " + move[1] +")");
        }
     }

    public boolean isEmpty(int row, int col) {
        return getBoard()[row][col] == null;
    }
}
