import java.util.ArrayList;

public class King extends Piece {


    private boolean isInCheck;
    
    //Arr List of spaces the other color can capture, ie: king cant move to a space in this list
    private ArrayList<Integer[]> unSafeSpaces;
    
    public King(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 999;
        isInCheck = false;
        this.unSafeSpaces = new ArrayList<>();
    }

    public void setUnsafeSpaces(ArrayList<Integer[]> unsafeSpaces) {
        this.unSafeSpaces = unsafeSpaces;
    }
    
    public ArrayList<Integer[]> getUnsafeSpaces() {
        return this.unSafeSpaces;
    }
    //in GameBoard, if king is in arr list of unsafe spaces, set to true
    public void setIsInCheck() {
        isInCheck = true;
    }
    
    public boolean getIsInCheck() {
        return this.isInCheck;
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
                
                //use a boolean flag when going thru array of unsafe spaces, add move if flag is false at end. 
                boolean isSafeMove = true;
            for (Integer[] unsafeSpace : getUnsafeSpaces()) {
                //if the possible move is in list of unsafe spaces, dont add it
                if ((unsafeSpace[0] == nextRow && unsafeSpace[1] == nextCol) ) {
                    isSafeMove = false;
                    break;
                }
            }
            if (isSafeMove) {
                moves.add(new Integer[] {nextRow, nextCol});
            }
        } 
    }

    //for debugging.
    public void printUnsafeSpaces() {
        for (Integer[] space: this.unSafeSpaces) {
            System.out.println("cant move to: " + space[0] + ", " + space[1]);
        }
    }

    public String toString() {
        return "King";
    }

}
