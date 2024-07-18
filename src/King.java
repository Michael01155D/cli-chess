import java.util.ArrayList;

public class King extends Piece {

    /*
    current step: need to see if king is in check...
    this affects the entire game, so put in board?

    ^this relies on king needing to only be able to move in safe spaces

    Key: King needs to know which spaces are dangerous.
    ie: what possible moves are there for every other oppo color piece

    idea: give Gameboard an ArrayList of all pieces valid moves
          that updates on each move. 
        board has an unsafe for black and unsafe for white array
        for each piece in both color arrays, add their possible moves to 
        opposite color's unsafe array (for pawn just diagonals)
        after a piece moves, remove its possible moves from that array and re-add
        ^problem: how to know which moves in the array are associated with each piece?
        ^solution? hashmap, <Piece, moves arrList> 
        in each Piece's findValid moves, also add them to the map
        problem2: a piece moving can impact a diff. piece's attack area
        ^ex: pawn moves, suddenly bishop can access more spaces. 
        //solution2? just loop thru each piece after each move. redo the maps after every change in board state 
        since looping thru all pieces, hashmap is unecessary, just an arraylist
        //problem3: duplicate values in arr list, not a deal breaker but not great code

    */

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
                
                //use a boolean flag when going thru array of unsafe spaces, add if flag is false at end. 
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

    public void printUnsafeSpaces() {
        for (Integer[] space: this.unSafeSpaces) {
            System.out.println("cant move to: " + space[0] + ", " + space[1]);
        }
    }

    public String toString() {
        return "King";
    }

}
