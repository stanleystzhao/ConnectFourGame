package connect;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
public class SwingConnectFourViewImpl extends JFrame implements SwingConnectFourView {
  private final JButton[][] buttons;
  private final JLabel turnLabel;
  private final JLabel messageLabel;
  private SwingConnectFourControllerImpl controller;
  private final BlockingQueue<Integer> inputQueue;

  /**
   * Constructor for the view.
   * @param title the title of the window
   */
  public SwingConnectFourViewImpl(String title) {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);

    // Initialize input queue
    inputQueue = new LinkedBlockingQueue<>();

    // Create components
    turnLabel = new JLabel("Current turn: RED");
    messageLabel = new JLabel("");
    JPanel boardPanel = new JPanel(new GridLayout(6, 7));
    final JPanel buttonPanel = new JPanel();
    buttons = new JButton[6][7];
    // Create board buttons
    // cells are to be filled with the player's color when clicked
    // from the bottom row to the top row
    for (int i = 5; i >= 0; i--) {
      for (int j = 0; j < 7; j++) {
        buttons[i][j] = new JButton();
        buttons[i][j].setBackground(Color.WHITE);
        final int column = j;
        buttons[i][j].addActionListener(e -> {
          // Notify controller of player's move
          inputQueue.offer(column);
        });
        boardPanel.add(buttons[i][j]);

      }
    }

    // Create restart and quit buttons
    JButton restartButton = new JButton("Restart Game");
    restartButton.addActionListener(e -> {
      // Reset the game
      controller.resetGame();
    });
    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener(e -> {
      // Close the window
      dispose();
    });
    buttonPanel.add(restartButton);
    buttonPanel.add(quitButton);

    // Add components to the frame
    add(turnLabel, BorderLayout.NORTH);
    add(boardPanel, BorderLayout.CENTER);
    add(messageLabel, BorderLayout.SOUTH);
    add(buttonPanel, BorderLayout.SOUTH);

    setVisible(true);
  }

  /**
   * Set the controller for the view.
   * @param controller the controller to set
   */
  @Override
  public void setController(SwingConnectFourControllerImpl controller) {
    this.controller = controller;
  }


  /**
   * Create the board for the game.
   */
  @Override
  public void createBoard() {
    // Initialize the board
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        buttons[i][j].setBackground(Color.WHITE);
      }
    }
    // Display initial turn
    turnLabel.setText("Current turn: RED");
    // Clear message
    messageLabel.setText("");
  }

  @Override
  public void updateBoard(Player[][] boardState) {
    // Update the board display based on the board state
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        if (boardState[i][j] == Player.RED) {
          buttons[i][j].setBackground(Color.RED);
        } else if (boardState[i][j] == Player.YELLOW) {
          buttons[i][j].setBackground(Color.YELLOW);
        } else {
          buttons[i][j].setBackground(Color.WHITE);
        }
      }
    }
    // Update turn label
    if (controller.getTurn() == Player.RED) {
      turnLabel.setText("Current turn: RED");
    } else {
      turnLabel.setText("Current turn: YELLOW");
    }
  }

  /**
   * Display a message to the user.
   * @param message the message to display
   */
  @Override
  public void displayMessage(String message) {
    // Display a message to the user
    messageLabel.setText(message);
  }


  /**
   * Ask the user if they want to play again.
   * @param winner the player who won the game, or null if it's a draw
   * @return true if the user wants to play again, false otherwise
   */
  @Override
  public boolean askToPlayAgain(Player winner) {
    // Ask the user if they want to play again
    String message;
    if (winner == null) {
      message = "It's a draw! Play again?";
    } else {
      message = winner + " wins! Play again?";
    }
    int choice =
        JOptionPane.showConfirmDialog(this, message, "Game Over", JOptionPane.YES_NO_OPTION);
    return choice == JOptionPane.YES_OPTION;
  }

  /**
   * Reset the board for a new game.
   */
  @Override
  public void resetBoard() {
    // Reset the board for a new game
    inputQueue.clear();
    createBoard();

  }

  /**
   * Wait for player input.
   * @return the column the player selected
   */
  @Override
  public int waitForPlayerInput() {
    try {
      // Block until player makes a move
      return inputQueue.take();
    } catch (InterruptedException e) {
      // Handle interruption
      Thread.currentThread().interrupt();
      return -1;
    }
  }
}
