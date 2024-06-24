public class GameBoard {
    final int BOARDSIZE = 8;
    private Piece[][] board;
    
    public GameBoard(){
        this.board = new Piece[BOARDSIZE][BOARDSIZE];
        Piece[] blackPieces = createPieces("black");
        Piece[] whitePieces = createPieces("white");
    }

    //return array of pieces in following order: rook, knight, bishop, queen, king, bishop, knight, rook, pawns
    private Piece[] createPieces(String color) {
        Piece[] pieces = new Piece[BOARDSIZE * 2];

        return pieces;

    }

}
