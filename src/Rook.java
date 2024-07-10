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
        //add (currRow--, currCol) to moves until reach edge of board or a piece is in the way. if piece is enemy, add that space too
        //^same logic for (currRow++, currCol). and (currRow, currCol--) and (currRow, currCol++)
        int currRow = startRow;
        int currCol = startCol; 
        int totals = spacesAvailable(boardState);
        System.out.println("Total rows rook can move is: " + totals);
        //first add (currRow--, currCol) moves:
        while (--currRow >= 0) {
            Piece nextDown = boardState[currRow][currCol];
            if (nextDown == null) {
                moves.add(new Integer[] {currRow, currCol});
                continue;
            }
            
            if (!nextDown.getColor().equals(this.getColor())){
                moves.add(new Integer[] {currRow, currCol});
            }

            break;
        }
        //logic is the same for all 4 move types, should be able to loop
        setValidMoves(moves);
    }

    //first find down rows, then find up rows
    private int spacesAvailable(Piece[][] boardState) {
        int nextRow = getPosition()[0] -1;
        int currCol = getPosition()[1];
        int totalSpaces = 0;
        
        //first find num of moves can go up (ie lowering row index)
        while ( nextRow >= 0 && boardState[nextRow][currCol] == null) {
            totalSpaces++;
            nextRow--;
        }
        //if nextRow is valid and either nextRow is empty or its a piece of the opposite color
        if (nextRow >= 0 && 
        (boardState[nextRow][currCol] == null || !(boardState[nextRow][currCol].getColor().equals(this.getColor())))
        ) {
            totalSpaces++;
        }
        nextRow = getPosition()[0] + 1;
        
        while (nextRow < boardState.length -1 && boardState[nextRow][currCol] == null) {
            totalSpaces++;
            nextRow++;
        }
    
        if (nextRow <= boardState.length -1 && 
        (boardState[nextRow][currCol] == null || !(boardState[nextRow][currCol].getColor().equals(this.getColor())))
        ) {
            totalSpaces++;
        }

        int currRow = this.getPosition()[0];
        int nextCol = currCol -1;

        while ( nextCol >= 0 && boardState[currRow][nextCol] == null) {
            totalSpaces++;
            nextCol--;
        }

        if (nextCol >= 0 && 
        (boardState[currRow][nextCol] == null || !(boardState[currRow][nextCol].getColor().equals(this.getColor())))
        ) {
            totalSpaces++;
        }
        nextCol = currCol + 1;
        
        while (nextCol < boardState.length -1 && boardState[currRow][nextCol] == null) {
            totalSpaces++;
            nextCol++;
        }
    
        if (nextCol <= boardState.length -1 && 
            (boardState[currRow][nextCol] == null || !(boardState[currRow][nextCol].getColor().equals(this.getColor())))
        ) {
            totalSpaces++;
        }
        return totalSpaces;
    }

    public String toString() {
        return "Rook";
    }
}
