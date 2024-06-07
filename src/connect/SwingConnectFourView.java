package connect;

/**
 * The view for the Connect Four game.
 * We use swing library to create the GUI for the game.
 * The view and the model are separated from each other.
 * The controller is the middleman between the view and the model.
 * the window will display the game board and the current player.
 * The main portion of the window will be a 6row x 7column grid of buttons.
 * The buttons are initially empty (white)
 * and will be filled with the player's color (yellow or red) when clicked.
 * The window will also display the current player's turn at the top.
 * The window will also display a message when the game is over.
 * The window will have a "Restart Game" button that will reset the game.
 * The window will have a "Quit" button that will close the window.
 * These two buttons are at the bottom of the window.
 */
public interface SwingConnectFourView {

  void createBoard();

  void updateBoard(Player[][] boardState);

  void setController(SwingConnectFourControllerImpl controller);

  void resetBoard();

  void displayMessage(String message);

  int waitForPlayerInput();

  boolean askToPlayAgain(Player winner);
}
