import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 1;
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
        
        if (boardState[nextRow][col] == null) {
            moves.add(new Integer[] {nextRow, col});
        }
        //if piece exists in upper diagonal and is of opposite color
        if ( !onFarRight && (boardState[nextRow][col + 1] != null && !(boardState[nextRow][col + 1].getColor().equals(this.color)))) {
            moves.add(new Integer[] {nextRow, col + 1});
        }
        if ( !onFarLeft &&  (boardState[nextRow][col - 1] != null && !(boardState[nextRow][col - 1].getColor().equals(this.color)))) {
            moves.add(new Integer[] {nextRow, col -1});
        }
        //booleans to check if pawn can move up two spaces
        boolean hasNotMoved = (this.color.equals("white") && row == 6) || (this.color.equals("black") && row == 1);
        boolean forwardEmpty = boardState[nextRow][col] == null;
        int forwardTwoSpaces = this.color.equals("white") ? nextRow - 1 : nextRow + 1;
        
        if (hasNotMoved && boardState[forwardTwoSpaces][col] == null && forwardEmpty) {
            moves.add(new Integer[] {forwardTwoSpaces, col});
        }
         
        setValidMoves(moves);
    }

    public ArrayList<Integer[]> getDiagonals() {
        ArrayList<Integer[]> diagonals = new ArrayList<>();
        int[] position = this.getPosition();
        //if white, it can attack up (decrement row) 
        if (this.getColor().equals("white")) {
            if (position[0] > 0 && position[1] < 7) {
                diagonals.add(new Integer[] {position[0] - 1, position[1] + 1});
            }
            if (position[0] > 0 && position[1] > 0) {
                diagonals.add(new Integer[] {position[0] - 1, position[1] - 1});
            }
        } else {
            //if black, it can attack down (increment row)
            if (position[0] < 7 && position[1] < 7) {
                diagonals.add(new Integer[] {position[0] + 1, position[1] + 1});
            }
            if (position[0] < 7 && position[1] > 0) {
                diagonals.add(new Integer[] {position[0] + 1, position[1] - 1});
            }
        }
        return diagonals;
    }
    public String toString() {
        return "Pawn";
    }

}
