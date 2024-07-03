import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 1;
    }

    public void move(){
    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        int row = getPosition()[0];
        if (row == 0 || row == 7) { return;}
        int col = getPosition()[1];
        boolean onFarLeft = col == 0;
        boolean onFarRight = col == 7;
        //if white, move upwards, ie descending row index
        int nextRow = this.getColor().equals("white") ? row - 1 : row + 1;
        System.out.println("*** FINDING VALID MOVES FOR " + this.getColor() + " Pawn at position: " + "(" + row + ", " +col +")");
        if (boardState[nextRow][col] == null) {
            moves.add(new Integer[] {nextRow, col});
        }
        if ( !onFarRight && boardState[nextRow][col + 1] != null) {
            moves.add(new Integer[] {nextRow, col + 1});
        }
        if ( !onFarLeft && boardState[nextRow][col - 1] != null) {
            moves.add(new Integer[] {nextRow, col -1});
        }
        setValidMoves(moves);
    }

    public String toString() {
        return "P";
    }

}
