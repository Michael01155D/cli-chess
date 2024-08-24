import java.util.ArrayList;


public class Player {
    private ArrayList <Piece> pieces;
    private String name;
    private String color;
    private int totalValidMoves;

    public Player(String name, String color, ArrayList<Piece> pieces) {
        this.name = name;
        this.color = color;
        this.pieces = pieces;
        this.totalValidMoves = 20;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Piece> getPieces() {
        return this.pieces;
    }

    public String getColor() {
        return this.color;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public void removePiece(Piece piecetoRemove) {
        for (Piece piece : this.getPieces()) {
            if (piece == piecetoRemove) {
                this.getPieces().remove(piecetoRemove);
                return;
            }
        }
    }

    public void addPiece(Piece pieceToAdd) {
        ArrayList<Piece> pieces = this.getPieces();
        pieces.add(pieceToAdd);
        this.setPieces(pieces);
    }

    public int getTotalValidMoves() {
        return this.totalValidMoves;
    }

    public void setTotalValidMoves(int numMoves) {
        this.totalValidMoves = numMoves;
    }

    public King getKing() {
        for (Piece piece: getPieces()) {
            if (piece instanceof King) {
                return (King) piece;
            }
        }
        //this shouldnt be reached, if it is then king isnt an active piece somehow
        return null;
    }

}
