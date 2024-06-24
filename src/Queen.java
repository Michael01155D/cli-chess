import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 9;
    }

    public void move(){

    }

    public ArrayList<Integer> findValidMoves(int[] position) {
        ArrayList<Integer> validMoves = new ArrayList<>();
        return validMoves;
    }

    public String toString() {
        return "Q";
    }

}
