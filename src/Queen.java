import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 9;
    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        String[] dirs = {"up", "down", "left", "right", "upLeft", "upRight", "downLeft", "downRight"};
        for (String dir: dirs) {
            addMoves(dir, boardState, moves);
        }
        setValidMoves(moves);
    }

    public void addMoves(String dir, Piece[][] boardState, ArrayList<Integer[]> moves) {
        int currRow = this.getPosition()[0];
        int currCol = this.getPosition()[1];
        //determine which direction rows/cols should update: 
        boolean incrementNextRow = dir.equals("downLeft") || dir.equals("downRight") || dir.equals("down");
        boolean incrementNextCol = dir.equals("upRight") || dir.equals("downRight") || dir.equals("right");
        int nextRow = incrementNextRow ? currRow + 1 : currRow - 1;
        int nextCol = incrementNextCol ? currCol + 1 : currCol - 1;
        //if not moving diagonal, dont update one of the dimensions
        nextRow = dir.equals("left") || dir.equals("right") ? currRow : nextRow;
        nextCol = dir.equals("up") || dir.equals("down") ? currCol : nextCol;

        boolean inBound = nextRow > -1 && nextRow < 8 && nextCol > -1 && nextCol < 8;
        
        while (inBound) {
            //if the next board position in current direction isnt empty, stop iterating. add position if piece is opposite color.
            if (boardState[nextRow][nextCol] != null) {
                if (!boardState[nextRow][nextCol].getColor().equals(this.getColor())) {
                    moves.add(new Integer[] {nextRow, nextCol});
                }
                break;
            }
            //if next board position empty, add it to moves and update nextRow/nextCol and check if reached edge of board. 
            moves.add(new Integer[] {nextRow, nextCol});
            //only change row/col if direction requires it
            if (!dir.equals("left") && !dir.equals("right")) {
                nextRow = incrementNextRow ? nextRow + 1 : nextRow - 1;
            }

            if (!dir.equals("up") && !dir.equals("down")){
                nextCol = incrementNextCol ? nextCol + 1 : nextCol - 1;
            }

            inBound = nextRow > -1 && nextRow < 8 && nextCol > -1 && nextCol < 8;
        }
    }

    public String toString() {
        return "Queen";
    }

}
