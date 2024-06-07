package connect;

/**
 * The controller for the Connect Four game.
 * We use the swing library to create the GUI for the game.
 * The view and the model are separated from each other.
 * The controller is the middleman between the
 * view and the model.
 * The controller will play the game.
 */
public interface SwingConnectFourController {

  void playGame(ConnectFourModel model);

  void resetGame();

  Player getTurn();

}
