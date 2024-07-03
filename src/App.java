public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        gameBoard.displayBoard();
        Piece pawn = gameBoard.getPiece(6, 6);
        //current todo: make sure pawn's validMoves are valid, then test w other pawns
        gameBoard.seeValidMoves(pawn);
    }
}
