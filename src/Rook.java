import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 5;
    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();

        String[] dirs = {"up", "down", "left", "right"};
        for (String dir: dirs) {
            addMoves(dir, boardState, moves);
        }
        
        // ***Previous logic (works but repetitive): 
        // int startRow = this.getPosition()[0];
        // int startCol = this.getPosition()[1];
        // //add (currRow -1, currCol) to moves until reach edge of board or a piece is in the way. if piece is enemy, add that space too
        // //^same logic for (currRow +1, currCol). and (currRow, currCol-1) and (currRow, currCol+1)
        // int nextRow = startRow - 1;
        // //first find moves going up the row indices
        // while (nextRow >= 0 && boardState[nextRow][startCol] == null) {
        //     moves.add(new Integer[] {nextRow, startCol});
        //     nextRow--;
        // }
        // //if nextRow exists and either nextRow is empty or its a piece of the opposite color
        // if (nextRow >= 0 && 
        // (boardState[nextRow][startCol] == null || !(boardState[nextRow][startCol].getColor().equals(this.getColor())))
        // ) {
        //     moves.add(new Integer[] {nextRow, startCol});
        // }
        // //set next row to begin checking down the board
        // nextRow = startRow + 1;
        
        // while (nextRow < boardState.length -1 && boardState[nextRow][startCol] == null) {
        //     moves.add(new Integer[] {nextRow, startCol});
        //     nextRow++;
        // }
    
        // if (nextRow <= boardState.length -1 && 
        // (boardState[nextRow][startCol] == null || !(boardState[nextRow][startCol].getColor().equals(this.getColor())))
        // ) {
        //     moves.add(new Integer[] {nextRow, startCol});
        // }
        // //now find columns to the left
        // int nextCol = startCol -1;

        // while ( nextCol >= 0 && boardState[startRow][nextCol] == null) {
        //     moves.add(new Integer[] {startRow, nextCol});
        //     nextCol--;
        // }

        // if (nextCol >= 0 && 
        // (boardState[startRow][nextCol] == null || !(boardState[startRow][nextCol].getColor().equals(this.getColor())))
        // ) {
        //     moves.add(new Integer[] {startRow, nextCol});
        // }
        // //now check columns to the right
        // nextCol = startCol + 1;
        
        // while (nextCol < boardState.length -1 && boardState[startRow][nextCol] == null) {
        //     moves.add(new Integer[] {startRow, nextCol});
        //     nextCol++;
        // }
    
        // if (nextCol <= boardState.length -1 && 
        //     (boardState[startRow][nextCol] == null || !(boardState[startRow][nextCol].getColor().equals(this.getColor())))
        // ) {
        //     moves.add(new Integer[] {startRow, nextCol});
        // }
       
        setValidMoves(moves);
    }

    private void addMoves(String dir, Piece[][] boardState, ArrayList<Integer[]> moves) {
        int currRow = this.getPosition()[0];
        int currCol = this.getPosition()[1];
        int nextRow = currRow;
        int nextCol = currCol;
        switch(dir) {
            case "up":
                nextRow = currRow -1;
                break;
            case "down":
                nextRow = currRow + 1;
                break;
            case "left":
                nextCol = currCol - 1;
                break;
            case "right":
                nextCol = currCol + 1;
                break;
            default:
                break;
        }

        boolean notAtEdge = checkNextPosition(dir, nextRow, nextCol);

        while (notAtEdge) {
            if (boardState[nextRow][nextCol] != null) {
                if (!boardState[nextRow][nextCol].getColor().equals(this.getColor())) {
                    moves.add(new Integer[] {nextRow, nextCol});
                }
                break;
            }
            moves.add(new Integer[] {nextRow, nextCol});
            nextRow = advanceRow(dir, nextRow);
            nextCol = advanceCol(dir, nextCol);
        }

    }

    private boolean checkNextPosition(String dir, int nextRow, int nextCol) {
        return (
            dir.equals("up") && nextRow > -1 ||
            dir.equals("down") && nextRow < 8 ||
            dir.equals("left") && nextCol > -1 ||
            dir.equals("right") && nextCol < 8
            );
    }

    private int advanceRow(String dir, int nextRow) {
        if (dir.equals("left") || dir.equals("right")) {
            return nextRow;
        }
        return dir.equals("up") ? nextRow - 1 : nextRow + 1;
    }

    private int advanceCol(String dir, int nextCol) {
        if (dir.equals("up") || dir.equals("down")) {
            return nextCol;
        }
        return dir.equals("left") ? nextCol - 1 : nextCol + 1;
    } 

    public String toString() {
        return "Rook";
    }
}
