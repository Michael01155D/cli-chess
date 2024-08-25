import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

//Current issue: if player is in check, need to be able to only select pieces that can get player out of check. 
// if no such pieces exist, game over. 
//CURRENT BUG SEE LINE ~400. Moves are remaining valid even if after making them, king is in check. need to debug removeIllegalmoves/simulate move methods. 
//Source of bug: illegal moves are being re-added.
//^ex: If rook can valid move to a7, a6, a5. When simulating a7, it isnt added. so valid moves become a6, a5. 
// ^then when simulating a6, valid moves becomes a7, a5. 
//I think the reason for this bug is that im iterating thru piece's valid moves list and calling a method on each move that also mutates validMove list

//idea: instead of using one method that calls simulate move on each valid move, make simulate move return a bool, only add to valid moves if true. 



//In charge of setting up players, printing board, managing turns
public class GameHandler {

    private Player white;
    private Player black;
    private GameBoard gameBoard;
    private Scanner inputScanner;
    private Player activePlayer;
    private boolean isGameOver;

    //maps to quickly convert between rows/cols as their labelled on board to and from array indicies. ex: "a6" (col, row) would be Board[2, 0] (row, col)
    final Map<String, Integer> BOARD_COL_TO_INDEX = Map.of( 
        "a", 0,
        "b", 1,
        "c", 2,
        "d", 3,
        "e", 4,
        "f", 5,
        "g", 6,
        "h", 7
    );

    final Map<Integer, String> INDEX_TO_BOARD_COL = Map.of(
        0, "a",
        1, "b",
        2, "c",
        3, "d",
        4, "e",
        5, "f",
        6, "g",
        7, "h"
    );

    final Map<String, Integer> BOARD_ROW_TO_INDEX = Map.of(
        "8", 0,
        "7", 1,
        "6", 2,
        "5", 3,
        "4", 4,
        "3", 5,
        "2", 6,
        "1", 7
    );

    final Map <Integer, String> INDEX_TO_BOARD_ROW = Map.of(
        0, "8",
        1, "7",
        2, "6",
        3, "5",
        4, "4",
        5, "3",
        6, "2",
        7, "1"
    );

    public GameHandler() {
        gameBoard = new GameBoard();
        inputScanner = new Scanner(System.in);
        initializePlayers();
        this.activePlayer = white;
        play(); 
    }
    
    //method to get/set names of players. playerNum either "One" or "Two". prevPlayerName only matters for two
    public String createPlayerName(String playerNum, String prevPlayerName) {
        String name = "";
        while (name.trim().length() > 20 || name.trim().length() < 1 || name.equals(prevPlayerName)) {
            System.out.println("Player " + playerNum +", please enter your name (between 1 to 20 characters)");
            name = inputScanner.nextLine();
            if (name.equals(prevPlayerName)) {
                System.out.println("Please choose a unique player name");
            }

        }
        return name;
    }

    public void initializePlayers() {
        //prev player name irrelevant for nameOne

        //TODO: UNCOMMENT THIS BLOCK WHEN DONE TESTING (commented out to expedite game init process)

        // String nameOne = createPlayerName("One", " ");
        // String nameTwo = createPlayerName("Two", nameOne);
        // Random rand = new Random();
        // int colorSelection = rand.nextInt(2);
        // String nameForWhite = colorSelection == 0 ? nameOne : nameTwo;
        // String nameForBlack = nameForWhite.equals(nameOne) ? nameTwo : nameOne;
        // white = new Player(nameForWhite, "white", gameBoard.getActivePieces("white"));
        // black = new Player(nameForBlack, "black", gameBoard.getActivePieces("black"));

        //TODO: delete these 2 lines when done testing
        white = new Player("w", "white", gameBoard.getActivePieces("white"));
        black = new Player("b", "black", gameBoard.getActivePieces("black"));
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public void setActivePlayer(Player player) {
        this.activePlayer = player;
    }

    public void swapTurn() {
        Player nextPlayer = getActivePlayer() == white ? black : white;
        setActivePlayer(nextPlayer);
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }
    //gameflow: display board, choose a piece, move piece, check gameOver, swap player. 
    public void play() {
        while (!isGameOver){
            GameBoard board = getGameBoard();
            board.displayBoard();
            //find all the legal moves for the players' pieces
            findPlayerValidMoves();
            boolean isInCheck = activePlayer.getKing().getIsInCheck();
            String currColor = activePlayer.getColor();
            Piece selectedPiece =  getPieceFromInput(currColor);
            int pieceValidMoves = selectedPiece.getValidMoves().size();
            int totalValidMoves = activePlayer.getTotalValidMoves();
          
            if (isInCheck && totalValidMoves == 0) {
                //todo: implement a proper game over method
                this.isGameOver = true;
                break;
            }

            while (pieceValidMoves == 0) {
                System.out.println("This piece has no valid moves, please select another piece.");
                selectedPiece = getPieceFromInput(currColor);
                pieceValidMoves = selectedPiece.getValidMoves().size();
            }
            
            board.seeValidMoves(selectedPiece);
        
            int[] move = getMoveFromInput(selectedPiece);
            //if valid move possible, prompt user for a move, keep prompting until move is valid
            while (!moveIsValid(move, selectedPiece.getValidMoves())){
                move = getMoveFromInput(selectedPiece);
            }

            //if opponent's piece is at new destination, remove it from board
            Piece capturedPiece = board.getBoard()[move[0]][move[1]];
            if (capturedPiece != null) {
                board.removeActivePiece(capturedPiece);
                if (activePlayer == white) {
                    black.removePiece(capturedPiece);
                } else {
                    white.removePiece(capturedPiece);
                }
            }
            //perform move, updating board state
            board.movePiece(selectedPiece, move);

        //if pawn reaches end row, promote it then update boardstate
        if (selectedPiece instanceof Pawn &&  (move[0]== 0 || move[0] == 7) ) {
            promotePawn(selectedPiece);
            board.setSpacesUnderAttack(currColor);
            //update the spaces unsafe for the other player's king after replacing the pawn with a new piece:
            if (activePlayer == white) {
                black.getKing().setUnsafeSpaces(board.getSpacesUnderAttack(currColor));
            } else {
                white.getKing().setUnsafeSpaces(board.getSpacesUnderAttack(currColor));
            }
        }
            
            //after everything else:
            swapTurn();
        }
        
        System.out.println("Game over! bye");
    }

    public String getColInput() {
        String col = "";
        while (!BOARD_COL_TO_INDEX.containsKey(col)) {
            System.out.println("Please enter the column (a to h) :");
            col = inputScanner.nextLine();
        }
        return col;
    }

    public String getRowInput() { 
        String row = "";
        while (!BOARD_ROW_TO_INDEX.containsKey(row)) {
            System.out.println("Please enter the row (1 to 8) :");
            row = inputScanner.nextLine();
        }
        return row;
    }

    //valid moves are moves that are inbounds and either empty or contain opponent piece.
    //legal moves are valid, and also result in own king not being in check.
    //after finding valid moves, iterate through them to determine which are also legal. 
    public void removeIllegalMoves(Piece piece) {
        ArrayList<Integer[]> legalMoves = new ArrayList<>();
        for (Integer[] validMove: piece.getValidMoves()) {
            //if performing the move leaves own king in check, simulateMove returns false
            if (simulateMove(validMove, piece)) {
                legalMoves.add(validMove);
            }
        }
        piece.setValidMoves(legalMoves);
    }

    public Piece getPieceFromInput(String currColor) {
        Piece selectedPiece = null;
        while (selectedPiece == null || !(selectedPiece.getColor().equals(currColor))) {
            if (selectedPiece != null) {
                System.out.println("That piece belongs to the opponent!");
            }
            System.out.println(activePlayer.getName() + " select a " + currColor + " piece to move");
            int colIndex = BOARD_COL_TO_INDEX.get(getColInput());
            int rowIndex = BOARD_ROW_TO_INDEX.get(getRowInput());
            selectedPiece = getGameBoard().getPiece(rowIndex, colIndex);
            if (selectedPiece == null) {
                System.out.println("There was no piece found at that position");
            }
        }

        return selectedPiece;
    }

    public int[] getMoveFromInput(Piece selectedPiece) {
        String currRow = INDEX_TO_BOARD_ROW.get(selectedPiece.getPosition()[0]);
        String currCol = INDEX_TO_BOARD_COL.get(selectedPiece.getPosition()[1]);
        System.out.println("Select a valid space on the board to move the " + selectedPiece + " at " + currCol + currRow);
        int colIndex = BOARD_COL_TO_INDEX.get(getColInput());
        int rowIndex = BOARD_ROW_TO_INDEX.get(getRowInput());
        return new int[] {rowIndex, colIndex};
    }

    public boolean moveIsValid(int[] move, ArrayList<Integer[]> validMoves) {
        for (Integer[] validMove: validMoves) {
            if (validMove[0] == move[0] && validMove[1] == move[1]) {
                return true;
            }
        }
        return false;
    }

    //simulate an ***OTHERWISE VALID*** move to see if doing it would put your king in check. if yes, remove it from the piece's validMoves arrayList
    public boolean simulateMove(Integer[] move, Piece piece) {
        //isLegalMove will be set false if the move being simulated would put player's own king in check. if false, remove the move from piece's validMoves list.
        boolean isLegalMove = true;
        GameBoard board = getGameBoard();
        Piece[][] boardState = board.getBoard();
        int currRow = piece.getPosition()[0];
        int currCol = piece.getPosition()[1];
        int nextRow = move[0];
        int nextCol = move[1];
        //to simulate the move: if its a capture, temporarily remove the captured piece from board and store in variable to put back later
        Piece pieceToCapture = null;
        if (boardState[nextRow][nextCol] != null) {
            pieceToCapture = boardState[nextRow][nextCol];
        }

        if (pieceToCapture != null) {
            board.removeActivePiece(pieceToCapture);
        }
        //update boardstate to simulate the move
        boardState[currRow][currCol] = null;
        boardState[nextRow][nextCol] = piece;
        piece.setPosition(new int[] {nextRow, nextCol});
        //update spaces under attack after simulated move is made
        board.setSpacesUnderAttack("white");
        board.setSpacesUnderAttack("black");
        
        //after simulating move, see if doing so would put own king in check, if so move is unsafe
        //if piece being moved is white, check the spaces under attack by black, and vice versa
        String colorToCheck = piece.getColor().equals("white") ? "black" : "white";
            int kingsRow = getActivePlayer().getKing().getPosition()[0];
            int kingsCol = getActivePlayer().getKing().getPosition()[1];
            for (Integer[] unsafe : board.getSpacesUnderAttack(colorToCheck)) {
                //if making the move would put own King in check, set isSafeMove to false before reverting all changes
                if (unsafe[0] == kingsRow && unsafe[1] == kingsCol) {
                    isLegalMove = false;
                    break;
                }
            }
            //regardless of if move is safe or not, undo the changes to gameBoard and spacesUnderAttack to allow rest of methods to work normally:
            if (pieceToCapture != null) {
                board.addActivePiece(pieceToCapture);
                boardState[nextRow][nextCol] = pieceToCapture;
            } else {
                boardState[nextRow][nextCol] = null;
            }
            boardState[currRow][currCol] = piece;
            piece.setPosition(new int[] {currRow, currCol});
            board.setSpacesUnderAttack("white");
            board.setSpacesUnderAttack("black");
            return isLegalMove;
    }

    public void promotePawn(Piece pawn) {
        GameBoard board = getGameBoard();
        getActivePlayer().removePiece(pawn);
        board.removeActivePiece(pawn);
        Piece newPiece = createPiece(getNewPieceTypeInput(), pawn);
        getActivePlayer().addPiece(newPiece);
        board.addActivePiece(newPiece);
        //set position on board of pawn to the new piece:
        board.getBoard()[pawn.getPosition()[0]][pawn.getPosition()[1]] = newPiece;
    }

    //helper method for promoting pawns
    public String getNewPieceTypeInput() {
        String newPieceType = "";
        String[] pieceTypes = {"knight", "rook", "bishop", "queen"};
        HashSet<String> validPieceNames = new HashSet<>();
        for (String type : pieceTypes) {
            validPieceNames.add(type);
        }

        while (!validPieceNames.contains(newPieceType.toLowerCase().trim())) {
            System.out.println( getActivePlayer().getName() + ", type the piece to promote your pawn to: ");
            System.out.println("Options: \"knight\", \"rook\", \"bishop\", \"queen\"");
            newPieceType = this.inputScanner.nextLine().toLowerCase().trim();
        }

        return newPieceType;
    }

    //used for pawn promotion
    public Piece createPiece(String pieceType, Piece pawn) {
        Piece newPiece;
        String color = pawn.getColor();
        int row = pawn.getPosition()[0];
        int col = pawn.getPosition()[1];
        switch (pieceType) {
            case "knight":
                newPiece = new Knight(color, new int[] {row, col});
                break;
            case "rook":
                newPiece = new Rook(color, new int[] {row, col});
                break;
            case "bishop":
                newPiece = new Bishop(color, new int[] {row, col});
                break;
            case "queen":
                newPiece = new Queen(color, new int[] {row, col});
                break;
            default:
                newPiece = new Queen(color, new int[] {row, col});
                break;
        }
        return newPiece;
    }
    
    //called at the start of each turn. pieces each set their valid moves, set players total valid moves
    public void findPlayerValidMoves() {
            int totalMoves = 0;
            for (Piece piece: activePlayer.getPieces()) {
                piece.findValidMoves(getGameBoard().getBoard());
                //once valid moves are found, remove the ones that would leave player's king in check:
                removeIllegalMoves(piece);
                totalMoves += piece.getValidMoves().size();
            }
            //
            activePlayer.setTotalValidMoves(totalMoves);
            System.out.println("Player " + activePlayer.getName() + " has " + totalMoves + " valid moves");
        }
        

    //for testing:
    public void printUnsafeSpaces(String color) {
        ArrayList<Integer[]> spaces = getGameBoard().getSpacesUnderAttack(color);
        String oppoColor = color.equals("white") ? "black" : "white";
        System.out.println(oppoColor + " King cannot move to: ");
        for (Integer[] space: spaces) {
            System.out.println("(" + INDEX_TO_BOARD_COL.get(space[1]) + INDEX_TO_BOARD_ROW.get(space[0]) +")");
        }
    }
    
}
