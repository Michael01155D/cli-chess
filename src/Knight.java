import java.util.ArrayList;

public class Knight extends Piece {

    public Knight (String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 3;
    }
    public void move(){
        
    }

    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        setValidMoves(moves);
    }

    public String toString() {
        return "k";
    }

}
