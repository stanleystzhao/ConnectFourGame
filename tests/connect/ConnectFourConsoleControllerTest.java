package connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.junit.Test;

/**
 * Test class for ConnectFourConsoleController.
 */
public class ConnectFourConsoleControllerTest {

  @Test
  public void testModelInvalid() {
    try {
      ConnectFourView view = new ConnectFourView(new StringBuilder());
      ConnectFourConsoleController controller = new ConnectFourConsoleController(
          new InputStreamReader(System.in), view);
      controller.playGame(null);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Model cannot be null", e.getMessage());
    } catch (IOException e) {
      fail("Unexpected IOException");
    }
  }

  @Test
  public void testValidMovesRedWins() {
    ConnectFourView view = new ConnectFourView(new StringBuilder());
    ConnectFourConsoleController controller = new ConnectFourConsoleController(
        new StringReader("1\n2\n1\n2\n1\n2\n1\nn\n"), view);
    ConnectFourModelImpl model = new ConnectFourModelImpl(6, 7);
    try {
      controller.playGame(model);
      assertTrue(model.isGameOver());
      assertEquals(Player.RED, model.getWinner());
    } catch (IllegalArgumentException | IOException e) {
      fail("Unexpected exception");
    }
  }

  @Test
  public void testValidMovesYellowWins() {
    ConnectFourView view = new ConnectFourView(new StringBuilder());
    ConnectFourConsoleController controller = new ConnectFourConsoleController(
        new StringReader("1\n2\n1\n2\n1\n2\n3\n2\nn\n"), view);
    ConnectFourModelImpl model = new ConnectFourModelImpl(6, 7);
    try {
      controller.playGame(model);
      assertTrue(model.isGameOver());
      assertEquals(Player.YELLOW, model.getWinner());
    } catch (IllegalArgumentException | IOException e) {
      fail("Unexpected exception");
    }
  }

  @Test
  public void testValidMovesTie() {
    ConnectFourView view = new ConnectFourView(new StringBuilder());
    ConnectFourConsoleController controller = new ConnectFourConsoleController(
        new StringReader("1\n2\n3\n4\n1\n2\n3\n4\n4\n3\n2\n1\n1\n2\n3\n4\nn\n"), view);
    ConnectFourModelImpl model = new ConnectFourModelImpl(4, 4);
    try {
      controller.playGame(model);
      assertTrue(model.isGameOver());
      assertNull(model.getWinner());
    } catch (IllegalArgumentException | IOException e) {
      fail("Unexpected exception");
    }
  }

  @Test
  public void testAllowsReplay() {
    ConnectFourView view = new ConnectFourView(new StringBuilder());
    ConnectFourConsoleController controller = new ConnectFourConsoleController(
        new StringReader("1\n2\n3\n4\n1\n2\n3\n4\n4\n3\n2\n1\n1\n2\n3\n4\nyes\n0\n"), view);
    ConnectFourModelImpl model = new ConnectFourModelImpl(4, 4);
    try {
      controller.playGame(model);
      assertFalse(model.isGameOver());
      assertNull(model.getWinner());
    } catch (IllegalArgumentException | IOException e) {
      fail("Unexpected exception");
    }
  }

  @Test
  public void testAllowsQuitMidGame() {
    ConnectFourView view = new ConnectFourView(new StringBuilder());
    ConnectFourConsoleController controller = new ConnectFourConsoleController(
        new StringReader("1\n2\n3\n4\n1\n2\n3\n0\n"), view);
    ConnectFourModelImpl model = new ConnectFourModelImpl(4, 4);
    try {
      controller.playGame(model);
      assertNull(model.getWinner());
    } catch (IllegalArgumentException | IOException e) {
      fail("Unexpected exception");
    }
  }

  @Test
  public void testAllowsQuitWhenGameOver() {
    ConnectFourView view = new ConnectFourView(new StringBuilder());
    ConnectFourConsoleController controller = new ConnectFourConsoleController(
        new StringReader("1\n2\n3\n4\n1\n2\n3\n4\n4\n3\n2\n1\n1\n2\n3\n4\nno\n"), view);
    ConnectFourModelImpl model = new ConnectFourModelImpl(4, 4);
    try {
      controller.playGame(model);
      assertTrue(model.isGameOver());
      assertNull(model.getWinner());
    } catch (IllegalArgumentException | IOException e) {
      fail("Unexpected exception");
    }
  }

}