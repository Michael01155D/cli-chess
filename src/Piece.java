import java.util.ArrayList;
public abstract class Piece {
    protected String color;
    protected int[] position; //[y, x];
    protected int[] movement;
    protected ArrayList<Integer[]> validMoves;
    protected int value;

    public Piece(String color, int[] startPosition) {
        this.color = color;
        this.position = startPosition;
        this.validMoves = new ArrayList<>();
    }

    public int[] getPosition() {
        return this.position;
    }

    public void setPosition(int[]newPosition) {
        this.position = newPosition;
    }

    public int getValue() {
        return this.value;
    }

    public String getColor() {
        return this.color;
    }
    //valid moves in format: [rowIndex, colIndex];
    public ArrayList<Integer[]> getValidMoves() {
        return this.validMoves;
    }

    public void setValidMoves(ArrayList<Integer[]> newValidMoves) {
        this.validMoves = newValidMoves;
    }

    //convert input into array row/col and check validity in gameboard before calling this method
    public abstract void move(int row, int col); //calls setPosition
    
    public abstract void findValidMoves(Piece[][] boardState); //calls setValidMoves;

    public abstract String toString();


}
