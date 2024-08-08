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
    
    public GameBoard(){
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
        

    //called in the GameHandler class. Before calling, piece is guaranteed to have at least 1 valid move, which is passed as 2nd arg [row, col]
    public void movePiece(Piece piece, int[] validMove) {
        int prevRow = piece.getPosition()[0];
        int prevCol = piece.getPosition()[1];
        int newRow = validMove[0];
        int newCol = validMove[1];
        piece.setPosition(new int[] {newRow, newCol} );
            this.board[prevRow][prevCol] = null;
            //***TODO: If this.board[newRow][newCol] is not null, call capturePiece() method
            this.board[newRow][newCol] = piece;
            //***TODO: add method to Check if Piece is Pawn and newRow is 0 or 7, if yes turn into queen (or let player choose via input)

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
