public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        //for testing: gameboard.addTestPiece(Piece) -> Piece foo = gameboard.getPiece(row, col) -> gameBoard.seeValidMoves(foo);
        Queen testQueen = new Queen("black", new int[] {4, 0});
        gameBoard.addTestPiece(testQueen);
        gameBoard.displayBoard();
        gameBoard.seeValidMoves(gameBoard.getPiece(4, 0));
    }
}
