import java.util.ArrayList;
public class GameBoard {

    final int BOARDSIZE = 8;
    private Piece[][] board;
    ArrayList<Piece> activeWhitePieces;
    ArrayList<Piece> activeBlackPieces;
    
    public GameBoard(){
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
        System.out.println("   a   b   c   d   e   f   g   h");
        System.out.println(lineBreak);
        for (int i = 0; i < board.length; i++){
            System.out.print(8 - i + " |");
            for (Piece piece: board[i]){
                String toPrint = piece == null ? " " : piece.toString();
                System.out.print(" " + toPrint + " |");
            }
            System.out.println(" " + (8 - i));
            System.out.println(lineBreak);
        }
        System.out.println("   a   b   c   d   e   f   g   h");
    }

    //to (probably) be refactored into Player class:

    public Piece getPiece(int row, int col) {
        return getBoard()[row][col];
    }
    //temporary?
    public void seeValidMoves(Piece piece) {
        piece.findValidMoves(getBoard());
        ArrayList<Integer[]> validMoves = piece.getValidMoves();
        System.out.println("valid moves of " + piece + " are: " + validMoves);
    }
}
