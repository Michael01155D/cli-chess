import java.util.ArrayList;

public class King extends Piece {

    /*
    current step: need to see if king is in check...
    this affects the entire game, so put in board?
    after a non-king piece moves, check to see if oppo color king is in its move list
    if yes, that king is in check and must move, if no valid moves, checkmate

    ^this relies on king needing to only be able to move in safe spaces

    Key: King needs to know which spaces are dangerous.
    ie: what possible moves are there for every other oppo color piece

    idea: give Gameboard an ArrayList of all pieces valid moves
          that updates on each move. 
          
    */

    private boolean isInCheck;
    
    public King(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 999;
        isInCheck = false;
    }
    
    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        int currRow = this.getPosition()[0];
        int currCol = this.getPosition()[1];
        //check all 8 positions stored as pairs of 2 elements
        int[] possibleMoves = new int[] {
            currRow, currCol + 1, 
            currRow, currCol - 1, 
            currRow + 1, currCol,
            currRow + 1, currCol + 1,
            currRow + 1, currCol -1,
            currRow -1, currCol,
            currRow -1, currCol + 1,
            currRow -1, currCol -1
         };
         for (int i = 0; i < possibleMoves.length; i+=2) {
            addMove(boardState, possibleMoves[i], possibleMoves[i +1], moves);
         }
        setValidMoves(moves);
    }

    public void addMove(Piece[][] boardState, int nextRow, int nextCol, ArrayList<Integer[]> moves) {
        if (nextRow > 7 || nextCol > 7 || nextRow < 0 || nextCol < 0) {
            return;
        }

        if (boardState[nextRow][nextCol] == null ||
            (boardState[nextRow][nextCol] != null && !boardState[nextRow][nextCol].getColor().equals(this.getColor()))
            ) {
            moves.add(new Integer[] {nextRow, nextCol});
        } 
    }

    public String toString() {
        return "King";
    }

}
