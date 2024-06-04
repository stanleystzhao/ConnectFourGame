
package connect;


/**
 * A model representing the game state of Connect Four.
 * The model is responsible for maintaining the state of the game,
 * and for enforcing the rules of the game.
 */
public class ConnectFourModelImpl implements ConnectFourModel {
  private Player[][] board;
  private int rows;
  private int columns;
  private Player turn;
  private Player winner;
  private boolean gameOver;

  /**
   * Constructs a new ConnectFourModel that takes in the number of rows and columns
   * for the game board.
   *
   * @param rows the number of rows in the game board
   * @param columns the number of columns in the game board
   * @throws IllegalArgumentException if the number of rows or columns is less than 4
   */
  public ConnectFourModelImpl(int rows, int columns) throws IllegalArgumentException {
    if (rows < 4 || columns < 4) {
      throw new IllegalArgumentException("Invalid rows or columns");
    }
    this.turn = Player.RED;
    this.rows = rows;
    this.columns = columns;
    this.winner = null;
    this.gameOver = false;
    this.initializeBoard();
  }

  @Override
  public void initializeBoard() {
    int i;
    int j;
    this.board = new Player[rows][columns];
    for (i = 0; i < rows; i++) {
      for (j = 0; j < columns; j++) {
        this.board[i][j] = null;
      }
    }
  }

  @Override
  public void makeMove(int column) throws IllegalArgumentException {
    // check if the game is over
    if (this.gameOver) {
      throw new IllegalArgumentException("Game over");
    }
    // check if the column is valid
    if (column < 0 || column >= columns) {
      throw new IllegalArgumentException("Invalid column");
    }
    // check if the column is full
    int i;
    // find the first empty row in the column
    for (i = 0; i < rows; i++) { // start from the bottom
      if (this.board[i][column] == null) {
        this.board[i][column] = this.turn;
        break;
      }
    }
    if (i == rows) {
      throw new IllegalArgumentException("Column full");
    }
    // check if the game is over
    if (this.checkWin()) {
      this.gameOver = true;
      this.winner = this.turn;
    } else if (this.checkDraw()) {
      this.gameOver = true;
    } else {
      this.turn = this.turn == Player.RED ? Player.YELLOW : Player.RED;
    }
  }

  private boolean checkDraw() {
    int i;
    int j;
    for (i = 0; i < rows; i++) {
      for (j = 0; j < columns; j++) {
        if (this.board[i][j] == null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Four helper methods that check if the current player has won the game.
   * @return true if the current player has won the game, false otherwise
   */
  private boolean checkWin() {
    return checkHorizontalWin() || checkVerticalWin() || checkDiagonalWin();
  }

  private boolean checkHorizontalWin() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j <= columns - 4; j++) {
        if (board[i][j] != null
            && board[i][j] == board[i][j + 1]
            && board[i][j] == board[i][j + 2]
            && board[i][j] == board[i][j + 3]) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkVerticalWin() {
    for (int i = 0; i <= rows - 4; i++) {
      for (int j = 0; j < columns; j++) {
        if (board[i][j] != null && board[i][j] == board[i + 1][j]
            && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkDiagonalWin() {
    for (int i = 0; i <= rows - 4; i++) {
      for (int j = 0; j <= columns - 4; j++) {
        if (board[i][j] != null && board[i][j] == board[i + 1][j + 1]
            && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
          return true;
        }
        if (board[i][j + 3] != null && board[i][j + 3] == board[i + 1][j + 2]
            && board[i][j + 3] == board[i + 2][j + 1] && board[i][j + 3] == board[i + 3][j]) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Player getTurn() {
    if (this.gameOver) {
      return null;
    }
    return this.turn;
  }

  @Override
  public boolean isGameOver() {
    if (this.checkDraw()) {
      this.gameOver = true;
    }
    return this.gameOver;
  }

  @Override
  public Player getWinner() {
    if (this.isGameOver()) {
      return this.winner;
    } else {
      return null;
    }
  }

  @Override
  public void resetBoard() {
    int i;
    int j;
    for (i = 0; i < rows; i++) {
      for (j = 0; j < columns; j++) {
        this.board[i][j] = null;
      }
    }
    this.turn = Player.RED;
    this.winner = null;
    this.gameOver = false;
  }

  @Override
  public Player[][] getBoardState() {
    Player[][] copy = new Player[rows][columns];
    for (int i = 0; i < rows; i++) {
      System.arraycopy(board[i], 0, copy[i], 0, columns);
    }
    return copy;
  }

  /**
   * A toString method that returns the current state of the game board.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int i;
    int j;
    for (i = 0; i < rows; i++) {
      for (j = 0; j < columns; j++) {
        if (this.board[i][j] == null) {
          sb.append("_");
        } else {
          sb.append(this.board[i][j].getDisplayName());
        }
        if (j < columns - 1) {
          sb.append(" ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

}