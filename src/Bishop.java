import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 3;
    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        //same logic as rook, while next space is valid and not occupied, add to moves, if occupied add to moves if opposite color piece
        String[] dirs = {"upLeft", "upRight", "downLeft", "downRight"};
        for (String dir: dirs) {
            addMoves(dir, boardState, moves);
        }
        setValidMoves(moves);
    }

    //helper function to find valid moves given the bishop's direction
    public void addMoves(String dir, Piece[][] boardState, ArrayList<Integer[]> moves) {
        int currRow = this.getPosition()[0];
        int currCol = this.getPosition()[1];
        boolean incrementNextRow = dir.equals("downLeft") || dir.equals("downRight");
        boolean incrementNextCol = dir.equals("upRight") || dir.equals("downRight");
        int nextRow = incrementNextRow ? currRow + 1 : currRow - 1;
        int nextCol = incrementNextCol ? currCol + 1 : currCol - 1;
        
        boolean notAtEdge = checkNextPosition(dir, nextRow, nextCol);
        
        while (notAtEdge) {
            //if the next board position in current direction isnt empty, stop iterating. add position if piece is opposite color.
            if (boardState[nextRow][nextCol] != null) {
                if (!boardState[nextRow][nextCol].getColor().equals(this.getColor())) {
                    moves.add(new Integer[] {nextRow, nextCol});
                }
                break;
            }
            //if next board position empty, add it to moves and update nextRow/nextCol and check if reached edge of board. 
            moves.add(new Integer[] {nextRow, nextCol});
            nextRow = incrementNextRow ? nextRow + 1 : nextRow - 1;
            nextCol = incrementNextCol ? nextCol + 1 : nextCol - 1;
            notAtEdge = checkNextPosition(dir, nextRow, nextCol);  
        }
    }

    //helper function called by addMoves to update the while loop's condition value after each movement
    private boolean checkNextPosition(String dir, int nextRow, int nextCol) {
        boolean notAtEdge;
        switch (dir) {
            case "upLeft":
                notAtEdge = nextRow > -1 && nextCol > -1;
                break;
            case "upRight":
                notAtEdge = nextRow > -1 && nextCol < 8;
                break;
            case "downLeft":
                notAtEdge = nextRow < 8 && nextCol > -1;
                break;
            case "downRight":
                notAtEdge = nextRow < 8 && nextCol < 8;
                break;
            default:
                notAtEdge = false;
                break;
        }
        return notAtEdge;
    }

    public String toString() {
        return "Bishop";
    }

}
