public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        //for testing: gameboard.addTestPiece(Piece) -> Piece foo = gameboard.getPiece(row, col) -> gameBoard.seeValidMoves(foo);
        //King testKing = new King("black", new int[] {3, 2});
        //gameBoard.addTestPiece(testKing);
        gameBoard.displayBoard();
        //gameBoard.seeValidMoves(gameBoard.getPiece(3, 2));
        gameBoard.printUnsafeSpaces("black");
    }
}
