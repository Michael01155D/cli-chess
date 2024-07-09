public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        Piece pawn = gameBoard.getPiece(6, 3);
        //gameBoard.seeValidMoves(pawn);
        //gameBoard.movePiece(pawn);
        // gameBoard.addTestPiece(new Pawn("black", new int[] {5, 4}));
        // gameBoard.addTestPiece(new Pawn("black", new int[] {5, 2}));
        // gameBoard.addTestPiece(new Pawn("black", new int[] {5, 3}));
        gameBoard.displayBoard();
        gameBoard.seeValidMoves(pawn);
    }
}
