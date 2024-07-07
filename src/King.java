import java.util.ArrayList;

public class King extends Piece {

    public King(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 999;
    }

    public void move(int row, int col){
        
    }   
    
    public void findValidMoves(Piece[][] boardState) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        setValidMoves(moves);
    }

    public String toString() {
        return "King";
    }

}
