import java.util.ArrayList;

public class Knight extends Piece {

    public Knight (String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 3;
    }
    public void move(){
        
    }

    public ArrayList<Integer> findValidMoves(int[] position) {
        ArrayList<Integer> validMoves = new ArrayList<>();
        return validMoves;
    }

    public String toString() {
        return "k";
    }

}
