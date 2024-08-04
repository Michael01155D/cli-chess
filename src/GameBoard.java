import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

 /*
    current step: need to see if king is in check...
    using unsafeSpaces arr list,  iterate through them and see if king is present
    if yes, set king's isInCheck to true.

    Then at start of move method, check if that player's king isInCheck, if yes, king must be chosen, if king has no valid moves, checkmate.
*/

//At the point where player class seems advisable. Gameplay loop would be: Select Piece (check king in this method), move piece, change player

public class GameBoard {

    final int BOARDSIZE = 8;

        //maps to quickly convert between rows/cols as their labelled on board to and from array indicies. ex: "a6" (col, row) would be Board[2, 0] (row, col)
        //todo: refactor so that these maps arent needed here, only in GameHandler class
        final Map<String, Integer> BOARD_COL_TO_INDEX = Map.of( 
            "a", 0,
            "b", 1,
            "c", 2,
            "d", 3,
            "e", 4,
            "f", 5,
            "g", 6,
            "h", 7
        );
    
        final Map<Integer, String> INDEX_TO_BOARD_COL = Map.of(
            0, "a",
            1, "b",
            2, "c",
            3, "d",
            4, "e",
            5, "f",
            6, "g",
            7, "h"
        );
    
        final Map<Integer, Integer> BOARD_ROW_TO_INDEX = Map.of(
            8, 0,
            7, 1,
            6, 2,
            5, 3,
            4, 4,
            3, 5,
            2, 6,
            1, 7
        );
    
        final Map <Integer, Integer> INDEX_TO_BOARD_ROW = Map.of(
            0, 8,
            1, 7,
            2, 6,
            3, 5,
            4, 4,
            5, 3,
            6, 2,
            7, 1
        );
    

    private Piece[][] board;
    private ArrayList<Piece> activeWhitePieces;
    private ArrayList<Piece> activeBlackPieces;
    //maps for each board space that is unsafe for opposite color king;
    private ArrayList<Integer[]> underAttackByWhite;
    private ArrayList<Integer[]> underAttackByBlack; 

    private Scanner inputScanner;    
    
    public GameBoard(){
        this.inputScanner = new Scanner(System.in);
        this.board = new Piece[BOARDSIZE][BOARDSIZE];
        this.activeWhitePieces = createPieces("white");
        this.activeBlackPieces = createPieces("black");
        setUpBoard();
        this.underAttackByBlack = new ArrayList<>();
        this.underAttackByWhite = new ArrayList<>();
        // populate underAttack lists with the spaces that opposite color king can't move into.
        setSpacesUnderAttack("white");
        setSpacesUnderAttack("black");
        //initialize each King's unsafe spaces:
        King wKing = ((King)getBoard()[7][4]);
        King bKing = ((King)getBoard()[0][4]);
        wKing.setUnsafeSpaces(getSpacesUnderAttack("black"));
        bKing.setUnsafeSpaces(getSpacesUnderAttack("white"));
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

    public ArrayList<Piece> getActivePieces(String color) {
        return color.equals("white") ? this.activeWhitePieces : this.activeBlackPieces;
    }

    //use color white to determine black king's unsafe spaces, and vice versa. 
    public ArrayList<Integer[]> getSpacesUnderAttack (String color) {
        return color.equals("white") ? this.underAttackByWhite : this.underAttackByBlack;
    }



    public void setUpBoard() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = this.activeBlackPieces.get(j + (i * 8)); //on 2nd iteration, get indicies 8 -> 15
                this.board[7 - i][j] = this.activeWhitePieces.get(j + (i *8));
            }
        }
    };

    //update underAttack lists to contain each Piece possible attacking spaces, to use in determining check/mate or king movement:
    public void setSpacesUnderAttack(String color){
        Piece[][] board = getBoard();
        ArrayList<Integer[]> spacesUnderAttack = getSpacesUnderAttack(color);
        //reset the list before repopulating it
        spacesUnderAttack.clear();
        for (Piece piece : getActivePieces(color)) {
            if (piece instanceof Pawn) {
                Pawn currPiece = (Pawn) piece;
                for (Integer[] diagonal: currPiece.getDiagonals()) {
                    spacesUnderAttack.add(diagonal);
                }
            } else {
                piece.findValidMoves(board);
                for (Integer[] move: piece.getValidMoves()) {
                    spacesUnderAttack.add(move);
                }
            }
        }
    }

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
        //use maps to convert indicies of piece to labels used on board
        String boardCol = INDEX_TO_BOARD_COL.get(prevCol);
        int boardRow = INDEX_TO_BOARD_ROW.get(prevRow);
    
        System.out.println("Where will you move the " + piece + " positioned at " + boardCol + boardRow +"?");
        String col = "";
        String row = "-1"; //-1 to allow parseInt on first check
        while (!BOARD_COL_TO_INDEX.containsKey(col)) {
            System.out.println("Please enter the column to move to (a to h) :");
            col = inputScanner.nextLine();
        }
        while (row.isEmpty() || !("12345678".contains(row))) {
            System.out.println("Please enter the row to move to (1 to 8)");
            row = inputScanner.nextLine();
        }
        //check all possible valid moves to see if userinput is valid
        for (Integer[] validMove: validMoves) {
            int newRowIndex = BOARD_ROW_TO_INDEX.get(Integer.parseInt(row));
            int newColIndex = BOARD_COL_TO_INDEX.get(col);
            if (validMove[0] == newRowIndex && validMove[1] == newColIndex) {
                System.out.println("Moving piece to " + col + row);
                piece.setPosition(new int[] {newRowIndex, newColIndex} );
                this.board[prevRow][prevCol] = null;
                this.board[newRowIndex][newColIndex] = piece;
                displayBoard();
                //update lists of spaces that kings can't access:
                setSpacesUnderAttack("white");
                setSpacesUnderAttack("black");
                //update each King's list of unsafe spaces:
                for (Piece whitePiece : getActivePieces("white")) {
                    if (whitePiece instanceof King) {
                        ((King)whitePiece).setUnsafeSpaces(getSpacesUnderAttack("black"));
                    }
                }

                for (Piece blackPiece : getActivePieces("black")) {
                    if (blackPiece instanceof King) {
                        ((King)blackPiece).setUnsafeSpaces(getSpacesUnderAttack("white"));
                    }
                }

                return;
            }
        } 
         //todo: refactor so that the user re-enters input until a valid move is given.
        System.out.println("move wasnt valid :(");
    }

    //probably a temporary method for debugging
    public void seeValidMoves(Piece piece) {
        piece.findValidMoves(getBoard());
        int[] currPosition = piece.getPosition();
        String currPositionString = "(" + INDEX_TO_BOARD_COL.get(currPosition[1]) + INDEX_TO_BOARD_ROW.get(currPosition[0]) + ")";
        ArrayList<Integer[]> validMoves = piece.getValidMoves();
        System.out.println("valid moves of " +piece.getColor() + " " + piece + " at position " + currPositionString + " are:");
        for (Integer[] move : validMoves){
            //convert array indicies (values) to board labels (keys)
            String col = INDEX_TO_BOARD_COL.get(move[1]);
            int row = INDEX_TO_BOARD_ROW.get(move[0]);
            System.out.println("(" + col + row +")");
        }
     }

    public boolean isEmpty(int row, int col) {
        return getBoard()[row][col] == null;
    }

    //for debugging and testing moves
    public void addTestPiece(Piece piece) {
        int row = piece.getPosition()[0];
        int col = piece.getPosition()[1];
        this.board[row][col] = piece;
        if (piece.getColor().equals("white")){
            activeWhitePieces.add(piece);
        } else {
            activeBlackPieces.add(piece);
        }
        setSpacesUnderAttack("white");
        setSpacesUnderAttack("black");
        for (Piece whitePiece : getActivePieces("white")) {
            if (whitePiece instanceof King) {
                ((King)whitePiece).setUnsafeSpaces(getSpacesUnderAttack("black"));
            }
        }

        for (Piece blackPiece : getActivePieces("black")) {
            if (blackPiece instanceof King) {
                ((King)blackPiece).setUnsafeSpaces(getSpacesUnderAttack("white"));
            }
        }


    }
}
