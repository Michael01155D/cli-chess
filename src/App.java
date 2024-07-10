public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        //for testing: gameboard.addTestPiece(Piece) -> Piece foo = gameboard.getPiece(row, col) -> gameBoard.seeValidMoves(foo);
        Piece pawn = gameBoard.getPiece(6, 3);
        gameBoard.addTestPiece(new Pawn("black", new int[] {5, 2}));
        gameBoard.addTestPiece(new Pawn("white", new int[] {5, 4}));
        gameBoard.displayBoard();
        gameBoard.seeValidMoves(pawn);
    }
}
