import java.util.Scanner;

/**
 * TicTacToe.java
 * Author: August McCoy, Group 4
 * Code Description: This class allows two users to play tic tac toe in the terminal by typing in the row and column
 * they want to put their respective piece.
 */
public class TicTacToe {
    /** Constant for board size*/
    private static final int BOARD_SIZE = 3;

    /** enum on game status to determine if game has finished*/
    private enum Status {WIN, DRAW, CONTINUE};

    /** Use to update Status enumeration*/
    private Status status;

    /** Double char array to keep track of x's and o's*/
    private char[][] board;

    /** Used to keep track if first player (X) or Second Player (O)*/
    private boolean firstPlayer;

    /** boolean to see if game is over*/
    private boolean gameOver;

    /** Scanner for user input*/
    private Scanner scanner;

    public TicTacToe() {
        gameOver = false;
        firstPlayer = true;
        board = new char[BOARD_SIZE][BOARD_SIZE];
        scanner = new Scanner(System.in);

        // initialize board with empty char
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = ' ';
            }
        }
        // Set enum status to continue for game logic to flow
        status = Status.CONTINUE;

        // Start game in constructor
        play();
    }

    /**
     * Main creates an instance of this class which starts the game
     * @param args, the code in main
     */
    static void main(String[] args) {
        new TicTacToe();
    }

    /**
     * This method runs the game logic
     */
    public void play() {
        while(!gameOver) {
            // Print board and which player is playing now
            printBoard();
            char currentSymbol = firstPlayer ? 'X' : 'O';
            System.out.println("Player " + currentSymbol + "'s turn.");

            int row = -1;
            int col = -1;
            boolean valid = false;

            // Get player move and see if it is valid
            while (!valid) {
                System.out.print("Player " + currentSymbol + ": Enter row (0, 1, or 2):");
                row = scanner.nextInt();
                System.out.print("Player " + currentSymbol + ": Enter column (0, 1, or 2):");
                col = scanner.nextInt();

                if (validMove(row, col)) {
                    valid = true;
                } else {
                    System.out.println("Invalid move, please try again with only 0, 1, or 2.");
                }
            }

            // Update board and get gameStatus
            board[row][col] = currentSymbol;
            status = gameStatus();

            // Check if game ended, if not next players turn
            if (status != Status.CONTINUE) {
                printBoard();
                printStatus(firstPlayer ? 1 : 2);
            } else {
                firstPlayer = !firstPlayer;
            }
        }
    }

    /**
     * This method is used to print the end of game message dependent on if the game was a draw
     * or if the game was a WIN.
     * @param player int of the player who won, 1 if X, 2 if O
     */
    private void printStatus(int player) {
        if (status == Status.DRAW) {
            System.out.println("Game is a draw");
            gameOver = true;
        } else if (status == Status.WIN) {
            char symbol = (player == 1) ? 'X' : 'O';
            System.out.println("Player " + symbol + " wins.");
            gameOver = true;
        }

    }

    /**
     * This method checks the Status of the game, if the game has ended due to draw or win or if the game should
     * continue
     * @return Status.WIN, if a player won, Status.DRAW, if neither player won and their are no more spots to place their
     * piece, and Status.CONTINUE, if neither player has won yet and their are still spots to place pieces
     */
    private Status gameStatus() {
        // Row and Column check
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return Status.WIN;
            }
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return Status.WIN;
            }
        }

        // Diagonal check
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return Status.WIN;
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return Status.WIN;
        }

        // Check for empty spaces
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == ' ') {
                    return Status.CONTINUE;
                }
            }
        }

        // All spaces filled no win
        return Status.DRAW;
    }

    /**
     * This method is called just to print the board
     */
    public void printBoard() {
        System.out.println(" _______________________ ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.println("|       |       |       |");
            System.out.print("|");
            for (int j = 0; j < BOARD_SIZE; j++) {
                printSymbol(j, board[i][j]);
            }
            System.out.println();
            System.out.println("|_______|_______|_______|");
        }

    }

    /**
     * This is a helper function to printBoard(), it helps print the tic
     * tac toe board
     *
     * @param column the int column where the char is printed, this isn't used in method more of a mental track with
     *               printing
     * @param value the char that is being printed
     */
    private void printSymbol(int column, char value) {
        System.out.print("   " + value + "   |");
    }

    /**
     * Basic boolean check to see if the player is making an in bounds and
     * un occupied pick to place their tic or tac.
     *
     * @param row int row where the user wants to place their char
     * @param column int column where the user wants to place their char
     * @return true if the char was successfully placed, false otherwise
     */
    private boolean validMove(int row, int column) {
        if (row > BOARD_SIZE || column > BOARD_SIZE || row < 0 || column < 0) return false;
        if (board[row][column] != ' ') return false;
        return true;
    }
}

