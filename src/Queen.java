import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 9;
    }

    public void move(){

    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        setValidMoves(moves);
    }

    public String toString() {
        return "Q";
    }

}
