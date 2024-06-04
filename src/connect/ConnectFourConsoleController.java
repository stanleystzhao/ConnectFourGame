package connect;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Controller component in the MVC architecture of a Connect Four game. This class defines the core
 * functionalities required to play a game of Connect Four on the console.
 */
public class ConnectFourConsoleController implements ConnectFourController {
  private final Readable input;
  private final ConnectFourView view;
  private ConnectFourModel model;

  /**
   * Constructor for the ConnectFourConsoleController class.
   *
   * @param input the input source for user moves
   * @param view the view component for displaying the game state and messages
   */
  public ConnectFourConsoleController(Readable input, ConnectFourView view) {
    this.input = input;
    this.view = view;
    this.model = new ConnectFourModelImpl(6, 7);
  }

  /**
   * Execute a single game of Connect Four given a Connect Four Model. When the game
   * is over, the playGame method ends.
   *
   * @param m a non-null Connect Four Model
   * @throws IllegalArgumentException if the model is null
   */
  @Override
  public void playGame(ConnectFourModel m) throws IllegalArgumentException, IOException {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    model = m;

    Scanner scanner = new Scanner(input);
    boolean playAgain = true;

    do {
      view.displayGameState(model.toString());
      view.displayPlayerTurn(model.getTurn().toString());

      int column = -1;
      boolean validMove = false;
      while (!validMove) {
        try {
          view.askForMove();
          column = scanner.nextInt();
          if (column == 0) {
            view.displayGameQuit(model.toString());
            playAgain = false;
            break;
          }
          model.makeMove(column - 1);
          validMove = true;
        } catch (InputMismatchException e) {
          view.displayInvalidInput();
          scanner.next();
        } catch (IllegalArgumentException e) {
          view.displayInvalidNumber(e.getMessage());
        }

      }

      if (model.isGameOver()) {
        view.displayGameOver(model.getWinner() == null ? null : model.getWinner().toString());
        view.askPlayAgain();
        String playAgainInput = scanner.next().toLowerCase();
        if ("yes".equals(playAgainInput)) {
          playAgain = true;
          model.resetBoard();
        } else {
          playAgain = false;
        }
      }
    } while (playAgain);
  }

}
