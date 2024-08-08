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

        boolean inBound = nextRow < 8 && nextRow > -1 && nextCol < 8 && nextCol > -1;
        while (inBound) {
            if (boardState[nextRow][nextCol] != null) {
                if (!boardState[nextRow][nextCol].getColor().equals(this.getColor())) {
                    moves.add(new Integer[] {nextRow, nextCol});
                }
                break;
            }

            moves.add(new Integer[] {nextRow, nextCol});
            nextRow = advanceRow(dir, nextRow);
            nextCol = advanceCol(dir, nextCol);
            inBound = nextRow < 8 && nextRow > -1 && nextCol < 8 && nextCol > -1;
        }

    }
        //helpers functions to determine how rook should move based on direction
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
