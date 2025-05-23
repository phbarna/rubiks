package rubiks;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class CubeTest {

  private final static CubeUtils CUBE_UTILS = new CubeUtils();

  @Test
  public void solvedCubeTest() {
    try {
      Cube cube = new Cube().asSolved();

      boolean solved = CUBE_UTILS.checkSolvedState(cube);
      Assert.assertTrue(solved);
      String returnString = cube.getDisplayAnnotation();
      // if we can reconstruct the cube as defined by the string we've just
      // returned.... then that's good :-)
      Cube newCube = new Cube();
      newCube.buildCubeFromString(returnString);
      CubeStatus status = CUBE_UTILS.validateCube(newCube);
      assertEquals(CubeStatus.OK, status);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void rightTurnTest() {
    try {
      String notation = """
          yywoworgy
          rogrowogo
          orgbbybgg
          rbworyrrw
          owbbgybbb
          grwgywywy
          """;

      Cube cube = new Cube();
      cube.buildCubeFromString(notation);
      String cubeText = cube.getFullAnnotationString();

      cube.followAlgorithm("rc", false); // one clockwise turn
      CubeStatus status = CUBE_UTILS.validateCube(cube);
      assertEquals(CubeStatus.OK, status);
      cube.followAlgorithm("3rc", false); // 2 clockwise turns

      // we have sent 4 right turn requests so would expect the returned string to be
      // identical to the state of cube
      assertEquals(cubeText, cube.getFullAnnotationString());

    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void algorithmTestFail() {
    try {
      Cube cube = new Cube();
      int result = cube.followAlgorithm("rc 2ra lxx", false); // lcx not valid
      assertEquals(-1, result);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void buildCubeWithError() {
    String notation = """
        orryoboyy
        gogwbybgr
        ryworowrg
        owywgryow
        ggwrygbgy
        bbybwwobb
        """;

    Cube cube = new Cube();
    try {
      CubeStatus status = cube.buildCubeFromString(notation);
      assertEquals(CubeStatus.COLOUR_DISTRIBUTION_ERROR, status);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void validationTest() {
    String invalid = """
        oooooobooo
        bbbbbobbb
        yyyyyyyyy
        ggggggggg
        rrrrrrrrr
        wwwwwwwww
        """;

    try {
      Cube cube = new Cube().asSolved();
      CubeStatus status = cube.buildCubeFromString(invalid);
      Assert.assertNotEquals(CubeStatus.OK, status);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * This is just a speed test to see how long masses of random turns take - hence
   * the Ignore annotation
   * should be on for most of the time
   */
  @Test
  @Ignore("Skipping speedTest because it's too slow for regular test runs")
  public void speedTest() {
    try {
      Cube shuffledCube = new Cube().asShuffled();
      LocalDateTime now1 = LocalDateTime.now();
      String[] commands = { "lc", "rc", "uc", "bc", "fc", "dc,", "la", "ra", "ua", "ba", "fa", "da" };
      for (int i = 0; i < 2000; i++) {
        int rand = ThreadLocalRandom.current().nextInt(commands.length);
        shuffledCube.followAlgorithm(commands[rand], false);
        if (!CUBE_UTILS.validateCube(shuffledCube).equals(CubeStatus.OK)) {
          Assert.fail("validation fail !!");
          break;
        }
      }
      LocalDateTime now2 = LocalDateTime.now();
      long diff = ChronoUnit.SECONDS.between(now1, now2);
      System.out.println("took " + diff + " seconds");

    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void buildRealWorldCube() {
    // poke in a random cube (which I have in front of me) and test validation
    String notation = """
        yywoworgy
        rogrowogo
        orgbbybgg
        rbworyrrw
        owbbgybbb
        grwgywywy
        """;

    Cube cube = new Cube();

    try {
      CubeStatus status = cube.buildCubeFromString(notation);
      boolean solved = CUBE_UTILS.checkSolvedState(cube);
      assertEquals(CubeStatus.OK, status);

      // just check it isn't returning true here - as not a solved cube
      Assert.assertFalse(solved);

      // proof that the cube we have just created is can return a string that can
      // return a valid cube
      Cube cube2 = new Cube();
      assertEquals(CubeStatus.OK, cube2.buildCubeFromString(cube.getDisplayAnnotation()));

    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void buildRealWorldCubeWithCornerSwap() {
    // poke in a random cube which but have subtly swapped 2 corners over
    String notation = """
        yywoworgy
        oogrowogr
        rbgbbyogg
        rbworyrrw
        owbbgybbb
        yrwgywywg
        """;

    try {
      Cube cube = new Cube();
      CubeStatus status = cube.buildCubeFromString(notation);
      assertEquals(CubeStatus.CORNER_MATCH_ERROR, status);

    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }
}