import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 5;
    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        int startRow = this.getPosition()[0];
        int startCol = this.getPosition()[1];
        //add (currRow -1, currCol) to moves until reach edge of board or a piece is in the way. if piece is enemy, add that space too
        //^same logic for (currRow +1, currCol). and (currRow, currCol-1) and (currRow, currCol+1)
        int nextRow = startRow - 1;
        //first find moves going up the row indices
        while (nextRow >= 0 && boardState[nextRow][startCol] == null) {
            moves.add(new Integer[] {nextRow, startCol});
            nextRow--;
        }
        //if nextRow exists and either nextRow is empty or its a piece of the opposite color
        if (nextRow >= 0 && 
        (boardState[nextRow][startCol] == null || !(boardState[nextRow][startCol].getColor().equals(this.getColor())))
        ) {
            moves.add(new Integer[] {nextRow, startCol});
        }
        //set next row to begin checking down the board
        nextRow = startRow + 1;
        
        while (nextRow < boardState.length -1 && boardState[nextRow][startCol] == null) {
            moves.add(new Integer[] {nextRow, startCol});
            nextRow++;
        }
    
        if (nextRow <= boardState.length -1 && 
        (boardState[nextRow][startCol] == null || !(boardState[nextRow][startCol].getColor().equals(this.getColor())))
        ) {
            moves.add(new Integer[] {nextRow, startCol});
        }
        //now find columns to the left
        int nextCol = startCol -1;

        while ( nextCol >= 0 && boardState[startRow][nextCol] == null) {
            moves.add(new Integer[] {startRow, nextCol});
            nextCol--;
        }

        if (nextCol >= 0 && 
        (boardState[startRow][nextCol] == null || !(boardState[startRow][nextCol].getColor().equals(this.getColor())))
        ) {
            moves.add(new Integer[] {startRow, nextCol});
        }
        //now check columns to the right
        nextCol = startCol + 1;
        
        while (nextCol < boardState.length -1 && boardState[startRow][nextCol] == null) {
            moves.add(new Integer[] {startRow, nextCol});
            nextCol++;
        }
    
        if (nextCol <= boardState.length -1 && 
            (boardState[startRow][nextCol] == null || !(boardState[startRow][nextCol].getColor().equals(this.getColor())))
        ) {
            moves.add(new Integer[] {startRow, nextCol});
        }
       
        setValidMoves(moves);
    }

    public String toString() {
        return "Rook";
    }
}
