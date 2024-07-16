public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        //for testing: gameboard.addTestPiece(Piece) -> Piece foo = gameboard.getPiece(row, col) -> gameBoard.seeValidMoves(foo);
        Bishop testBishop = new Bishop("black", new int[] {4, 4});
        gameBoard.addTestPiece(testBishop);
        gameBoard.displayBoard();
        gameBoard.seeValidMoves(gameBoard.getPiece(4, 4));
    }
}
