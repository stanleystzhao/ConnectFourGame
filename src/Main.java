import connect.ConnectFourModelImpl;
import connect.SwingConnectFourControllerImpl;
import connect.SwingConnectFourViewImpl;


/**
 * The main class to run the Connect 4 game.
 * We use the swing library to create the GUI for the game.
 * The view and the model are separated from each other.
 * The controller is the middleman between the view and the model.
 * The window will display the game board and the current player.
 * The main portion of the window will be a 6row x 7column grid of buttons.
 * The buttons are initially empty (white)
 * and will be filled with the player's color (yellow or red) when clicked.
 * The window will also display the current player's turn at the top.
 * The window will also display a message when the game is over.
 * The window will have a "Restart Game" button that will reset the game.
 * The window will have a "Quit" button that will close the window.
 */
public class Main {
  /**
   * The main method to run the Connect 4 game.
   * @param args the command-line arguments that are not used
   */
  public static void main(String[] args) {

    ConnectFourModelImpl model = new ConnectFourModelImpl(6, 7);
    SwingConnectFourViewImpl view = new SwingConnectFourViewImpl("Connect 4");
    SwingConnectFourControllerImpl controller = new SwingConnectFourControllerImpl(view, model);

    view.setController(controller);
    view.createBoard();

    controller.playGame(model);

  }
}