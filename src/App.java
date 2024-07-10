public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        //for testing: gameboard.addTestPiece(Piece) -> Piece foo = gameboard.getPiece(row, col) -> gameBoard.seeValidMoves(foo);
        Piece knight = gameBoard.getPiece(0, 1);
        Piece testKnight = gameBoard.getPiece(3, 1);
        gameBoard.displayBoard();
        gameBoard.seeValidMoves(knight);
    }
}
