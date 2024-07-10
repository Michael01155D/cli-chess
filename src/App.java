public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        //for testing: gameboard.addTestPiece(Piece) -> Piece foo = gameboard.getPiece(row, col) -> gameBoard.seeValidMoves(foo);
        Piece pawn = gameBoard.getPiece(1, 1);
        gameBoard.movePiece(pawn);
        gameBoard.displayBoard();
    }
}
