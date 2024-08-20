import java.util.ArrayList;


public class Player {
    ArrayList <Piece> pieces;
    String name;
    String color;

    public Player(String name, String color, ArrayList<Piece> pieces) {
        this.name = name;
        this.color = color;
        this.pieces = pieces;
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
