import java.util.ArrayList;

public class King extends Piece {

    private boolean canCastle;
    private boolean isInCheck;
    
    public King(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 999;
        this.canCastle = true;
        this.isInCheck = false;
    }

    public boolean getCanCastle() {
        return this.canCastle;
    }

    public void setCanCastle() {
        this.canCastle = false;
    }

    public boolean getIsInCheck() {
        return this.isInCheck;
    }

    public boolean setIsInCheck(ArrayList<Integer[]> unsafeSpaces) {
        for (Integer[] unsafeSpace: unsafeSpaces) {
            if (unsafeSpace[0] == this.getPosition()[0] && unsafeSpace[1] == this.getPosition()[1]) {
                return true;
            }
        }
        return false;
    }

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
            ) {
                moves.add(new Integer[] {nextRow, nextCol});
        }
    } 


    //for debugging.
    // public void printUnsafeSpaces() {
    //     for (Integer[] space: this.unSafeSpaces) {
    //         System.out.println("cant move to: " + space[0] + ", " + space[1]);
    //     }
    // }

    public String toString() {
        return "King";
    }

}
