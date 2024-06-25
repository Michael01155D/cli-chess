public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        gameBoard.displayBoard();
        Piece pawn = gameBoard.getPiece(1, 0);
        gameBoard.seeValidMoves(pawn);
    }
}
