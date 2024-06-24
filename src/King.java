import java.util.ArrayList;

public class King extends Piece {

    public King(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 999;
    }

    public void move(){
        
    }   
    
    public ArrayList<Integer> findValidMoves(int[] position) {
        ArrayList<Integer> validMoves = new ArrayList<>();
        return validMoves;
    }

    public String toString() {
        return "K";
    }

}
