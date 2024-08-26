import java.util.ArrayList;
import java.util.Map;

public class GameBoard {

    final int BOARDSIZE = 8;
    //used for displayBoard(): 
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

        //maps to quickly convert between rows/cols as their labelled on board to and from array indicies. ex: "a6" (col, row) would be Board[2, 0] (row, col)
        //todo: refactor so that these maps arent needed here, only in GameHandler class
            
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
        // King wKing = ((King)getBoard()[7][4]);
        // King bKing = ((King)getBoard()[0][4]);
        // wKing.setUnsafeSpaces(getSpacesUnderAttack("black"));
        // bKing.setUnsafeSpaces(getSpacesUnderAttack("white"));
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

    // used for pawn promotion
    public void addActivePiece(Piece piece) {
        if (piece.getColor().equals("white")) {
            this.activeWhitePieces.add(piece);
            return;
        }
        this.activeBlackPieces.add(piece);
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
        this.board[newRow][newCol] = piece;

        //update lists of spaces that kings can't access:
        setSpacesUnderAttack("white");
        setSpacesUnderAttack("black");
        return;
    } 
    

    //print out list of valid moves for a selected piece
    public void seeValidMoves(Piece piece) {
        int[] currPosition = piece.getPosition();
        String currPositionString = "(" + INDEX_TO_BOARD_COL.get(currPosition[1]) + INDEX_TO_BOARD_ROW.get(currPosition[0]) + ")";
        ArrayList<Integer[]> validMoves = piece.getValidMoves();
        System.out.println("valid moves of " + piece.getColor() + " " + piece + " at position " + currPositionString + " are:");
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

    public void removeActivePiece(Piece piece) {
        String color = piece.getColor();
        getActivePieces(color).remove(piece);
    }

    public void displayBoard() {
        String whiteSpaces = "                                      ";
        System.out.println();
        String lineBreak = whiteSpaces + "   --------------------------------";
        System.out.println(whiteSpaces + "    a   b   c   d   e   f   g   h");
        System.out.println(lineBreak);
        for (int i = 0; i < board.length; i++){
            System.out.print(whiteSpaces + (8 - i) + " |");
            for (Piece piece: board[i]){
                String toPrint = piece == null ? " " : piece.toString().substring(0, 1);
                if (piece != null && piece.getColor().equals("white")) {
                    System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + " " + toPrint + " " + ANSI_RESET + "|");
                } else if (piece !=null) {
                    System.out.print(ANSI_BLACK_BACKGROUND + ANSI_WHITE + " " + toPrint + " " + ANSI_RESET + "|");
                } 
                else {
                    System.out.print(" " + toPrint + " |");
                }
            }
            System.out.println(" " + (8 - i));
            System.out.println(lineBreak);
        }
        System.out.println(whiteSpaces + "    a   b   c   d   e   f   g   h");
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
        // for (Piece whitePiece : getActivePieces("white")) {
        //     if (whitePiece instanceof King) {
        //         ((King)whitePiece).setUnsafeSpaces(getSpacesUnderAttack("black"));
        //     }
        // }

        // for (Piece blackPiece : getActivePieces("black")) {
        //     if (blackPiece instanceof King) {
        //         ((King)blackPiece).setUnsafeSpaces(getSpacesUnderAttack("white"));
        //     }
        // }


    }
}
