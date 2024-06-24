import java.util.ArrayList;
public abstract class Piece {
    protected String color;
    protected int[][] position;
    protected int[] movement;
    protected ArrayList<Integer> validMoves;
    protected int value;

    public Piece(String color, int[][] startPosition) {
        this.color = color;
        this.position = startPosition;
        this.validMoves = new ArrayList<>();
    }

    public int[][] getPosition() {
        return this.position;
    }

    public void setPosition(int[][]newPosition) {
        this.position = newPosition;
    }

    public int getValue() {
        return this.value;
    }

    public ArrayList<Integer> getValidMoves() {
        return this.validMoves;
    }

    public void setValidMoves(ArrayList<Integer> newValidMoves) {
        this.validMoves = newValidMoves;
    }

    public abstract void move(); //calls setPosition
    
    public abstract ArrayList<Integer> findValidMoves(int[][] position); //uses curr position, return is used to setValidMoves;



}
