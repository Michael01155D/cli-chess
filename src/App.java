public class App {
    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard();
        gameBoard.displayBoard();
        Piece pawn = gameBoard.getPiece(6, 3);
        //current todo: move pawn legally
        gameBoard.movePiece(pawn);
    }
}
