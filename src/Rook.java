import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 5;
    }

    public void move(){

    }

    public ArrayList<Integer> findValidMoves(int[] position) {
        ArrayList<Integer> validMoves = new ArrayList<>();
        return validMoves;
    }

    public String toString() {
        return "R";
    }
}
