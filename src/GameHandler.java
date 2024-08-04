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

    final Map <Integer, Integer> INDEX_TO_BOARD_ROW = Map.of(
        0, 8,
        1, 7,
        2, 6,
        3, 5,
        4, 4,
        5, 3,
        6, 2,
        7, 1
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
            name = inputScanner.next();
            if (name.equals(prevPlayerName)) {
                System.out.println("Please choose a unique player name");
            }

        }
        return name;
    }

    public void initializePlayers() {
        //prev player name irrelevant for nameOne
        String nameOne = createPlayerName("One", " ");
        String nameTwo = createPlayerName("Two", nameOne);
        Random rand = new Random();
        int colorSelection = rand.nextInt(2);
        String nameForWhite = colorSelection == 0 ? nameOne : nameTwo;
        String nameForBlack = nameForWhite.equals(nameOne) ? nameTwo : nameOne;
        white = new Player(nameForWhite, "white", gameBoard.getActivePieces("white"));
        black = new Player(nameForBlack, "black", gameBoard.getActivePieces("black"));
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
        this.gameBoard.displayBoard();
        //to do first: check if player's king is in check, if yes, must select king else:
        Piece selectedPiece;
        if (activePlayer.getKing().getIsInCheck()) {
            selectedPiece = activePlayer.getKing();
        } else {
            System.out.println(activePlayer.getName() + " select a " + activePlayer.getColor() + " piece to move");
            String col = getColInput();
            String row = getRowInput();
            while(gameBoard.getPiece(row, col))
            System.out.println(activePlayer.getName() + " select a " + activePlayer.getColor() + " piece to move");
            String col = getColInput();
            String row = getRowInput();

        }
        //to do here: get the valid moves of the selected piece (or King if in check)
        //if valid moves empty, prompt select another piece, or end game if King in check
        //if valid moves not empty, prompt user for a move, keep prompting until move is valid
        //then perform move, updating board state and keeping track of which spaces king cant move to
        
        
        
        //after everything else:
        swapTurn();
    }

    public String getColInput() {
        String col = "";
        while (!BOARD_COL_TO_INDEX.containsKey(col)) {
            System.out.println("Please enter the column (a to h) :");
            col = inputScanner.nextLine();
        }
        return col;
    }

    public String  getRowInput() { 
        String row = "-1"; //-1 to allow parseInt on first check

        return row;
    }

    public Piece getPieceFromInput(String rowString, String colString) {
        int rowIndex = BOARD_ROW_TO_INDEX.get(rowString);
        int colIndex = BOARD_COL_TO_INDEX.get(colString);
        return this.gameBoard.getPiece(rowIndex, colIndex);
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
