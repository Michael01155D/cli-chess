import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(String color, int[] startPosition) {
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
        return "B";
    }

}
