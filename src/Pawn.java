import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color, int[][] startPosition) {
        super(color, startPosition);
        this.value = 1;
    }

    public void move(){
        
    }
    
    public ArrayList<Integer> findValidMoves(int[][] position) {
        ArrayList<Integer> validMoves = new ArrayList<>();
        return validMoves;
    }

}
