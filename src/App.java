public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        //for testing: gameboard.addTestPiece(Piece) -> Piece foo = gameboard.getPiece(row, col) -> gameBoard.seeValidMoves(foo);
        Piece rook = gameBoard.getPiece(7, 0);
        Rook testRook = new Rook("black", new int[] {4, 4});
        gameBoard.addTestPiece(testRook);
        gameBoard.displayBoard();
        gameBoard.seeValidMoves(gameBoard.getPiece(4, 4));
        gameBoard.movePiece(testRook);
    }
}
