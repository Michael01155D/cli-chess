import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
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
        if (getActivePlayer() == white) {
            setActivePlayer(black); 
        }
         else { 
            setActivePlayer(white);
        }
    }

    //gameflow: display board, choose a piece, move piece, check gameOver, swap player. 
    public void play() {
        while (!isGameOver){
            this.gameBoard.displayBoard();
            //check if player's king is in check, if yes, must select king
            boolean isInCheck = activePlayer.getKing().getIsInCheck();
            String currColor = activePlayer.getColor();
            Piece selectedPiece = isInCheck ? activePlayer.getKing() : getPieceFromInput(currColor);
            //once Piece is selected, find its valid moves (if any)
            selectedPiece.findValidMoves(this.gameBoard.getBoard());
            int numValidMoves = selectedPiece.getValidMoves().size();
            //if king in check and it cant move, game over
            if (isInCheck && numValidMoves == 0) {
                //todo: implement a proper game over method
                this.isGameOver = true;
                break;
            }

            while (numValidMoves == 0) {
                System.out.println("This piece has no valid moves, please select another piece.");
                selectedPiece = getPieceFromInput(currColor);
                numValidMoves = selectedPiece.getValidMoves().size();
            }
        
            int[] move = getMoveFromInput(selectedPiece);
            //if valid move possible, prompt user for a move, keep prompting until move is valid
            while (!moveIsValid(move, selectedPiece.getValidMoves())){
                move = getMoveFromInput(selectedPiece);
            }

            //perform move, updating board state
            gameBoard.movePiece(selectedPiece, move);
            
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

    public Piece getPieceFromInput(String currColor) {
        Piece selectedPiece = null;
        while (selectedPiece == null || !(selectedPiece.getColor().equals(currColor))) {
            if (selectedPiece != null) {
                System.out.println("That piece belongs to the opponent!");
            }
            System.out.println(activePlayer.getName() + " select a " + currColor + " piece to move");
            int colIndex = BOARD_COL_TO_INDEX.get(getColInput());
            int rowIndex = BOARD_ROW_TO_INDEX.get(getRowInput());
            selectedPiece = this.gameBoard.getPiece(rowIndex, colIndex);
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



    //for testing:
    public void printUnsafeSpaces(String color) {
        ArrayList<Integer[]> spaces = gameBoard.getSpacesUnderAttack(color);
        String oppoColor = color.equals("white") ? "black" : "white";
        System.out.println(oppoColor + " King cannot move to: ");
        for (Integer[] space: spaces) {
            System.out.println("(" + INDEX_TO_BOARD_COL.get(space[1]) + INDEX_TO_BOARD_ROW.get(space[0]) +")");
        }
    }
    
}
