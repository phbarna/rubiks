package rubiks;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CubeTest {

  private final CubeUtils cubeUtils = new CubeUtils();

  @Test
  public void solvedCubeTest() {
    try {
      Cube cube = new Cube().asSolved();

      CubeUtils cubeUtils = new CubeUtils();
      boolean solved = cubeUtils.checkSolvedState(cube);
      Assert.assertTrue(solved);
      String returnString = cube.getDisplayAnnotation();
      // if we can reconstruct the cube as defined by the string we've just returned.... then that's good :-)
      Cube newCube = new Cube();
      newCube.buildCubeFromString(returnString);
      CubeStatus status = cubeUtils.validateCube(newCube);
      assertEquals(CubeStatus.OK, status);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void rightTurnTest() {
    try {
      String notation = "yywoworgy" + "\n" +
          "rogrowogo" + "\n" +
          "orgbbybgg" + "\n" +
          "rbworyrrw" + "\n" +
          "owbbgybbb" + "\n" +
          "grwgywywy" + "\n";
      Cube cube = new Cube();
      cube.buildCubeFromString(notation);
      String cubeText = cube.getFullAnnotationString();

      CubeUtils utils = new CubeUtils();
      cube.followAlgorithm("rc", false); // one clockwise turn
      CubeStatus status = utils.validateCube(cube);
      assertEquals(CubeStatus.OK, status);
      cube.followAlgorithm("3rc", false); // 2 clockwise turns

      // we have sent 4 right turn requests so would expect the returned string to be identical to the state of cube
      assertEquals(cubeText, cube.getFullAnnotationString());

    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void algorithmTestFail() {
    try {
      Cube c = new Cube();
      int result = c.followAlgorithm("rc 2ra lxx", false); //lcx not valid
      assertEquals(-1, result);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void buildCubeWithError() {
    String notation = "orryoboyy" + "\n" +
        "gogwbybgr" + "\n" +
        "ryworowrg" + "\n" +
        "owywgryow" + "\n" +
        "ggwrygbgy" + "\n" +
        "bbybwwobb";
    Cube c = new Cube();
    try {
      CubeStatus status = c.buildCubeFromString(notation);
      assertEquals(CubeStatus.COLOUR_DISTRIBUTION_ERROR, status);
    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void validationTest() {
    String invalid = "ooooobooo\n" +
        "bbbbbobbb\n" +
        "yyyyyyyyy\n" +
        "ggggggggg\n" +
        "rrrrrrrrr\n" +
        "wwwwwwwww";

    try {
      Cube c = new Cube().asSolved();
      CubeStatus status = c.buildCubeFromString(invalid);
      Assert.assertNotEquals(CubeStatus.OK, status);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * This is just a speed test to see how long masses of random turns take - hence the Ignore annotation
   * should be on for most of the time
   */
  @Test
  @Ignore
  public void speedTest() {
    try {
      Cube shuffledCube = new Cube().asShuffled();
      LocalDateTime now1 = LocalDateTime.now();
      String[] commands = {"lc", "rc", "uc", "bc", "fc", "dc,", "la", "ra", "ua", "ba", "fa", "da"};
      for (int i = 0; i < 30000; i++) {
        int rand = (int) (Math.random() * commands.length);
        shuffledCube.followAlgorithm(commands[rand], false);
        if (!cubeUtils.validateCube(shuffledCube).equals(CubeStatus.OK)) {
          Assert.fail("validation fail !!");
          break;
        }
        if (cubeUtils.checkSolvedState(shuffledCube)) {
          Assert.fail(
              "Technically this isn't a fail, but with 43,252,003,274,489,856,00043 combinations - " +
                  "unlikely to solve on random turns !");
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
  public void buildRealWorldCube() { // poke in a random cube which I have in front of me and test validation
    String notation = "yywoworgy" + "\n" +
        "rogrowogo" + "\n" +
        "orgbbybgg" + "\n" +
        "rbworyrrw" + "\n" +
        "owbbgybbb" + "\n" +
        "grwgywywy" + "\n";

    Cube cube = new Cube();

    try {
      CubeStatus status = cube.buildCubeFromString(notation);
      CubeUtils utils = new CubeUtils();
      boolean solved = utils.checkSolvedState(cube);
      assertEquals(CubeStatus.OK, status);
      Assert.assertFalse(solved); // just check it isn't returning true here - as not a solved cube

      // proof that the cube we have just created is can return a string that can return a valid cube
      Cube cube2 = new Cube();
      assertEquals(CubeStatus.OK, cube2.buildCubeFromString(cube.getDisplayAnnotation()));

    } catch (Exception ex) {
      ex.printStackTrace();
      Assert.fail(ex.getMessage());
    }
  }

  @Test
  public void buildRealWorldCubeWithCornerSwap() { // poke in a random cube which but  have subtly swapped 2 corners over
    String notation = "yywoworgy" + "\n" +
        "oogrowogr" + "\n" +
        "rbgbbyogg" + "\n" +
        "rbworyrrw" + "\n" +
        "owbbgybbb" + "\n" +
        "yrwgywywg" + "\n";
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