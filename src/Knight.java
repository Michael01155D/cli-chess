import java.util.ArrayList;

public class Knight extends Piece {

    public Knight (String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 3;
    }
    public void move(int row, int col){
        
    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        /*
        knight can move (row, col): 
        - (+2, + 1)
        - (+2, -1)
        - (+1, +2)
        - (+1, -2)
        - (-2, + 1)
        - (-2, -1)
        - (-1, +2)
        - (-1, -2)
        */
        int currRow = getPosition()[0];
        int currCol = getPosition()[1];
        
        if (currRow < boardState.length -2 && currCol < boardState.length - 1 && isAvailable(currRow + 2, currCol + 1, boardState)) {
            moves.add(new Integer[] {currRow + 2, currCol + 1}); 
        }
        if (currRow < boardState.length - 1 && currCol < boardState.length - 2  && isAvailable(currRow + 1, currCol + 2, boardState)) {
            moves.add(new Integer[] {currRow + 1, currCol + 2});
        }
        if (currRow < boardState.length - 2 && currCol > 0  && isAvailable(currRow + 2, currCol - 1, boardState)) {
            moves.add(new Integer[] {currRow +2, currCol -1});
        }
        if (currRow < boardState.length -1 && currCol > 1  && isAvailable(currRow + 1, currCol -2, boardState)) {
            moves.add(new Integer[] {currRow + 1, currCol - 2});
        }
        if (currRow > 1 && currCol < boardState.length -1  && isAvailable(currRow - 2, currCol + 1, boardState)) {
            moves.add(new Integer[] {currRow - 2, currCol + 1});
        }
        if (currRow > 0 && currCol < boardState.length - 2  && isAvailable(currRow - 1, currCol + 2, boardState)) {
            moves.add(new Integer[] {currRow - 1, currCol + 2});
        }
        if (currRow > 1 && currCol > 0  && isAvailable(currRow - 2, currCol - 1, boardState)) {
            moves.add(new Integer[] {currRow - 2, currCol - 1});
        }
        if (currRow > 0 && currCol > 1  && isAvailable(currRow - 1, currCol - 2, boardState)) {
            moves.add(new Integer[] {currRow - 1, currCol - 2});
        }

        setValidMoves(moves);
    }

    //helper fn to check if legal board space is also valid. ie: is empty or occupied by enemy piece
    private boolean isAvailable(int row, int col, Piece[][] board) {
        return board[row][col] == null || !(board[row][col].getColor().equals(this.getColor()));
    }
    public String toString() {
        return "knight";
    }

}
