package connect;


/**
 * This is the controller for the Connect Four game.
 * We use swing library to create the GUI for the game.
 * The view and the model are separated from each other.
 * The controller is the middleman between the view and the model.
 * The controller is responsible for updating the view when the model changes.
 * The controller is responsible for updating the model when the user makes a move.
 * The controller is responsible for executing the game.
 */
public class SwingConnectFourControllerImpl implements SwingConnectFourController {
  private final SwingConnectFourViewImpl view;
  private final ConnectFourModelImpl model;

  /**
   * Constructor for the SwingConnectFourControllerImpl.
   * @param view The view for the game.
   * @param model The model for the game.
   */
  public SwingConnectFourControllerImpl(SwingConnectFourViewImpl view, ConnectFourModelImpl model) {
    this.view = view;
    this.model = model;
  }

  /**
   * This method plays the game.
   * @param model The model for the game.
   */
  @Override
  public void playGame(ConnectFourModel model) {

    while (!model.isGameOver()) {
      // Wait for player input
      int column = view.waitForPlayerInput();
      // Make a move
      try {
        model.makeMove(column);
      } catch (IllegalArgumentException e) {
        // Handle invalid moves
        view.displayMessage(e.getMessage());
        continue;
      }

      view.updateBoard(model.getBoardState());

      // Check for game over
      if (model.isGameOver()) {
        Player winner = model.getWinner();
        // Ask if player wants to play again
        boolean playAgain = view.askToPlayAgain(winner);
        // Reset the board if player wants to play again
        if (playAgain) {
          model.resetBoard();
          view.resetBoard();
        }
      }
    }
  }

  /**
   * This method resets the game.
   */
  @Override
  public void resetGame() {
    model.resetBoard();
    view.resetBoard();
  }

  /**
   * his method returns the player whose turn it is.
   * @return The player whose turn it is.
   */
  @Override
  public Player getTurn() {
    return model.getTurn();
  }
}
