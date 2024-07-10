import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 5;
    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        setValidMoves(moves);
    }

    public String toString() {
        return "Rook";
    }
}
