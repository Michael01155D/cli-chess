import java.util.ArrayList;

public class King extends Piece {


    private boolean isInCheck;
    
    //Arr List of spaces the other color can capture, ie: king cant move to a space in this list
    private ArrayList<Integer[]> unSafeSpaces;
    
    public King(String color, int[] startPosition) {
        super(color, startPosition);
        this.value = 999;
        isInCheck = false;
        this.unSafeSpaces = new ArrayList<>();
    }

    public void setUnsafeSpaces(ArrayList<Integer[]> unsafeSpaces) {
        this.unSafeSpaces = unsafeSpaces;
    }
    
    public ArrayList<Integer[]> getUnsafeSpaces() {
        return this.unSafeSpaces;
    }

    //if current position is n unsafeSpaces arrList, set to true
    public void setIsInCheck() {
        int currRow = getPosition()[0];
        int currCol = getPosition()[1];
        for (Integer[] unsafeSpace : getUnsafeSpaces()) {
            if (unsafeSpace[0] == currRow && unsafeSpace[1] == currCol) {
                this.isInCheck = true;
                return;
            }
        }
        this.isInCheck = false;
    }
    
    public boolean getIsInCheck() {
        return this.isInCheck;
    }

    //never used, added as requirement of abstract parent
    public void findValidMoves(Piece[][] board){}

    public void findValidMoves(Piece[][] boardState, GameBoard board) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        int currRow = this.getPosition()[0];
        int currCol = this.getPosition()[1];
        //check all 8 positions stored as pairs of 2 elements
        int[] possibleMoves = new int[] {
            currRow, currCol + 1, 
            currRow, currCol - 1, 
            currRow + 1, currCol,
            currRow + 1, currCol + 1,
            currRow + 1, currCol -1,
            currRow -1, currCol,
            currRow -1, currCol + 1,
            currRow -1, currCol -1
         };
         for (int i = 0; i < possibleMoves.length; i+=2) {
            addMove(boardState, possibleMoves[i], possibleMoves[i + 1], moves, currRow, currCol, board);
         }
        setValidMoves(moves);
    }

    public void addMove(Piece[][] boardState, int nextRow, int nextCol, ArrayList<Integer[]> moves, int currRow, int currCol, GameBoard board) {
        if (nextRow > 7 || nextCol > 7 || nextRow < 0 || nextCol < 0) {
            return;
        }

        //BUG: here, if boardState[nextRow][nextCol] is an opposite player piece, its only safe if that position is not protected by another piece
        if (boardState[nextRow][nextCol] == null ||
            (boardState[nextRow][nextCol] != null && !boardState[nextRow][nextCol].getColor().equals(this.getColor()))
            ) {
                
                //use a boolean flag when going thru array of unsafe spaces, add move if flag is true at end. 
                boolean isSafeMove = true;
                /*if opponent piece at [nextRow][nextCol], need to see if taking that piece would put king in check
                    first, store the piece in temp var and take it w the king
                    update boardstate and update unsafespaces
                    if king current position after moving is unsafe, the move is not valid
                    if not valid, revert updates to boardstate and unsafespaces (store prev. vals in temp vars)
                    put king back to his spot
                */
                if (boardState[nextRow][nextCol] != null) {
                    //temporarily change boardstate to simulate king taking the piece
                    Piece pieceToCapture = boardState[nextRow][nextCol];
                    boardState[currRow][currCol] = null;
                    boardState[nextRow][nextCol] = this;
                    board.removeActivePiece(pieceToCapture);
                    board.setSpacesUnderAttack("white");
                    board.setSpacesUnderAttack("black");
                    //after simulating king taking piece, see if doing so would put king in check, if so move is unsafe
                    for (Integer[] unsafe : board.getSpacesUnderAttack(pieceToCapture.getColor())) {
                        //capturing the piece would be King in check, set isSafeMove to false before reverting all changes
                        if (unsafe[0] == nextRow && unsafe[1] == nextCol) {
                            isSafeMove = false;
                            break;
                        }
                    }
                    //regardless of if move is safe or not, undo the changes to gameBoard state to allow rest of methods to work normally:
                    board.addActivePiece(pieceToCapture);
                    boardState[nextRow][nextCol] = pieceToCapture;
                    boardState[currRow][currCol] = this;
                    board.setSpacesUnderAttack("white");
                    board.setSpacesUnderAttack("black");
                }

            for (Integer[] unsafeSpace : getUnsafeSpaces()) {
                //if the possible move is in list of unsafe spaces, dont add it
                if ((unsafeSpace[0] == nextRow && unsafeSpace[1] == nextCol) ) {
                    isSafeMove = false;
                    break;
                }
            }
            if (isSafeMove) {
                moves.add(new Integer[] {nextRow, nextCol});
            }
        } 
    }

    //for debugging.
    public void printUnsafeSpaces() {
        for (Integer[] space: this.unSafeSpaces) {
            System.out.println("cant move to: " + space[0] + ", " + space[1]);
        }
    }

    public String toString() {
        return "King";
    }

}
