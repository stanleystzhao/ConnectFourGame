package connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the ConnectFourModelImpl class.
 */
public class ConnectFourModelImplTest {

  private ConnectFourModelImpl model;

  @Before
  public void setUp() {
    model = new ConnectFourModelImpl(6, 7);
  }

  @Test
  public void testValidConstructor() {
    assertEquals(Player.RED, model.getTurn());
    assertFalse(model.isGameOver());
    assertNull(model.getWinner());
  }

  @Test
  public void testWinnerIsNullWhenGameBegins() {
    assertNull(model.getWinner());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorRowsTooSmall() {
    new ConnectFourModelImpl(3, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorColumnsTooSmall() {
    new ConnectFourModelImpl(6, 3);
  }

  @Test
  public void testValidMove() {
    model.makeMove(0);
    assertEquals(Player.YELLOW, model.getTurn());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigColumn() {
    model.makeMove(7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeColumn() {
    model.makeMove(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFullColumn() {
    for (int i = 0; i < 6; i++) {
      model.makeMove(0);
    }
    model.makeMove(0);
  }

  @Test
  public void testGetTurn() {
    assertEquals(Player.RED, model.getTurn());
    model.makeMove(0);
    assertEquals(Player.YELLOW, model.getTurn());
  }

  @Test
  public void testGetTurnIsNullWhenGameOver() {
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    assertTrue(model.isGameOver());
    assertNull(model.getTurn());
  }


  @Test
  public void testHorizontalWinRed() {
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    assertTrue(model.isGameOver());
    assertEquals(Player.RED, model.getWinner());
  }

  @Test
  public void testHorizontalWinYellow() {
    model.makeMove(0);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(1);
    model.makeMove(2);
    model.makeMove(2);
    model.makeMove(0);
    model.makeMove(3);
    model.makeMove(0);
    model.makeMove(3);
    assertTrue(model.isGameOver());
    assertEquals(Player.YELLOW, model.getWinner());
  }

  @Test
  public void testVerticalWinYellow() {
    model.makeMove(0);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(1);
    model.makeMove(2);
    model.makeMove(2);
    model.makeMove(3);
    assertTrue(model.isGameOver());
    assertEquals(Player.RED, model.getWinner());
  }

  @Test
  public void testVerticalWinRed() {
    model.makeMove(0);
    model.makeMove(6);
    model.makeMove(0);
    model.makeMove(5);
    model.makeMove(0);
    model.makeMove(4);
    model.makeMove(0);
    assertTrue(model.isGameOver());
    assertEquals(Player.RED, model.getWinner());
  }

  @Test
  public void testDiagonalWinBottomLeftToTopRightRed() {
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(1);
    model.makeMove(2);
    model.makeMove(2);
    model.makeMove(3);
    model.makeMove(2);
    model.makeMove(3);
    model.makeMove(3);
    model.makeMove(5);
    model.makeMove(3);
    assertTrue(model.isGameOver());
    assertEquals(Player.RED, model.getWinner());
  }

  @Test
  public void testDiagonalWinBottomLeftToTopRightYellow() {
    model.makeMove(6);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(1);
    model.makeMove(2);
    model.makeMove(2);
    model.makeMove(3);
    model.makeMove(2);
    model.makeMove(3);
    model.makeMove(3);
    model.makeMove(5);
    model.makeMove(3);
    assertTrue(model.isGameOver());
    assertEquals(Player.YELLOW, model.getWinner());
  }

  @Test
  public void testDiagonalWinTopLeftToBottomRightYellow() {
    model.makeMove(0);
    model.makeMove(0);
    model.makeMove(0);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(1);
    model.makeMove(6);
    model.makeMove(1);
    model.makeMove(2);
    model.makeMove(2);
    model.makeMove(6);
    model.makeMove(3);
    assertTrue(model.isGameOver());
    assertEquals(Player.YELLOW, model.getWinner());
  }

  @Test
  public void testDiagonalWinTopLeftToBottomRightRed() {
    model.makeMove(6);
    model.makeMove(0);
    model.makeMove(0);
    model.makeMove(0);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(1);
    model.makeMove(6);
    model.makeMove(1);
    model.makeMove(2);
    model.makeMove(2);
    model.makeMove(6);
    model.makeMove(3);
    assertTrue(model.isGameOver());
    assertEquals(Player.RED, model.getWinner());
  }

  @Test
  public void testDraw() {
    ConnectFourModelImpl fourByfour = new ConnectFourModelImpl(4, 4);
    fourByfour.makeMove(0);
    fourByfour.makeMove(0);
    fourByfour.makeMove(0);
    fourByfour.makeMove(0);
    fourByfour.makeMove(1);
    fourByfour.makeMove(1);
    fourByfour.makeMove(1);
    fourByfour.makeMove(1);
    fourByfour.makeMove(3);
    fourByfour.makeMove(2);
    fourByfour.makeMove(2);
    fourByfour.makeMove(2);
    fourByfour.makeMove(2);
    fourByfour.makeMove(3);
    fourByfour.makeMove(3);
    fourByfour.makeMove(3);
    assertTrue(fourByfour.isGameOver());
    assertNull(fourByfour.getWinner());
  }

  @Test
  public void testGameReset() {
    model.makeMove(0);
    model.makeMove(1);
    model.resetBoard();
    assertNull(model.getBoardState()[0][0]);
    assertNull(model.getBoardState()[0][1]);
    assertEquals(Player.RED, model.getTurn());
    assertFalse(model.isGameOver());
    assertNull(model.getWinner());
  }

  @Test
  public void testGetBoardState() {
    Player[][] board = model.getBoardState();
    for (Player[] players : board) {
      for (int j = 0; j < board[0].length; j++) {
        assertNull(players[j]);
      }
    }
    model.makeMove(0);
    board = model.getBoardState();
    assertEquals(Player.RED, board[0][0]);
  }

  @Test
  public void testGetWinner() {
    assertNull(model.getWinner());
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    assertEquals(Player.RED, model.getWinner());
  }

  @Test
  public void testIsGameOver() {
    assertFalse(model.isGameOver());
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    assertTrue(model.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGameOverCantMove() {
    assertFalse(model.isGameOver());
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    model.makeMove(1);
    model.makeMove(0);
    assertTrue(model.isGameOver());
    model.makeMove(0);
  }

  @Test
  public void testInitializeBoard() {
    model.makeMove(0);
    model.makeMove(1);
    model.initializeBoard();
    assertNull(model.getBoardState()[0][0]);
    assertNull(model.getBoardState()[0][1]);
    assertEquals(Player.RED, model.getTurn());
    assertFalse(model.isGameOver());
    assertNull(model.getWinner());
  }

  @Test
  public void testMakeMove() {
    model.makeMove(0);
    assertEquals(Player.YELLOW, model.getTurn());
    assertEquals(Player.RED, model.getBoardState()[0][0]);
  }

  @Test
  public void testToString() {
    ConnectFourModelImpl fourByfour = new ConnectFourModelImpl(4, 4);
    assertEquals("_ _ _ _\n_ _ _ _\n_ _ _ _\n_ _ _ _\n",
        fourByfour.toString());
    fourByfour.makeMove(0);
    assertEquals("R _ _ _\n_ _ _ _\n_ _ _ _\n_ _ _ _\n",
        fourByfour.toString());
  }
}