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
        
        if (currRow < boardState.length -2 && currCol < boardState.length - 1) {
            moves.add(new Integer[] {currRow + 2, currCol + 1}); 
        }
        if (currRow < boardState.length - 1 && currCol < boardState.length - 2) {
            moves.add(new Integer[] {currRow + 1, currCol + 2});
        }
        if (currRow < boardState.length - 2 && currCol > 0) {
            moves.add(new Integer[] {currRow +2, currCol -1});
        }
        if (currRow < boardState.length -1 && currCol > 1) {
            moves.add(new Integer[] {currRow + 1, currCol - 2});
        }
        if (currRow > 1 && currCol < boardState.length -1) {
            moves.add(new Integer[] {currRow - 2, currCol + 1});
        }
        if (currRow > 0 && currCol < boardState.length - 2) {

        }

        setValidMoves(moves);
    }

    public String toString() {
        return "knight";
    }

}
