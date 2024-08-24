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

    //if current position is n unsafeSpaces arrList, set to true
    public void setIsInCheck() {
        int currRow = getPosition()[0];
        int currCol = getPosition()[1];
        for (Integer[] unsafeSpace : getUnsafeSpaces()) {
            if (unsafeSpace[0] == currRow && unsafeSpace[1] == currCol) {
                this.isInCheck = true;
                return;
            }
        }
        this.isInCheck = false;
    }
    
    public boolean getIsInCheck() {
        return this.isInCheck;
    }

    //never used, added as requirement of abstract parent
    public void findValidMoves(Piece[][] boardState){
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
            addMove(boardState, possibleMoves[i], possibleMoves[i + 1], moves, currRow, currCol);
         }
        setValidMoves(moves);
    }

    public void addMove(Piece[][] boardState, int nextRow, int nextCol, ArrayList<Integer[]> moves, int currRow, int currCol) {
        if (nextRow > 7 || nextCol > 7 || nextRow < 0 || nextCol < 0) {
            return;
        }
        
        //if space to move to is empty or opponent's piece:
        if (boardState[nextRow][nextCol] == null || 
            (boardState[nextRow][nextCol] != null && !boardState[nextRow][nextCol].getColor().equals(this.getColor()))
            ) 
            {
                moves.add(new Integer[] {nextRow, nextCol});
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
