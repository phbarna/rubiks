package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class TurnHelperTest {

    private CubeUtils cubeUtils = new CubeUtils();

    String notation = "rrrrrrrrr\n" +
            "ggggggggg\n" +
            "yyyyyyyyy\n" +
            "ooooooooo\n" +
            "bbbbbbbbb\n" +
            "wwwwwwwww";

    @Test
    public void rightTurnTest() {
        try {

            Cube cube = new Cube();
            cube.buildCubeFromString(notation);
            String cubeText = cube.getFullAnnotationString();

            CubeUtils utils = new CubeUtils();
            cube.followAlgorithmAttempt("rc"); // one clockwise turn
            CubeStatus status = utils.validateCube(cube);
            Assert.assertEquals(CubeStatus.OK, status);
            cube.followAlgorithmAttempt("rc rc rc"); // 3 more clockwise turns

            // we have sent 4 right turn requests so would expect the returned string to be identical to the state of cube
            Assert.assertEquals(cubeText, cube.getFullAnnotationString());

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void leftTurnTest() {
        try {

            Cube cube = new Cube();
            cube.buildCubeFromString(notation);
            String cubeText = cube.getFullAnnotationString();

            cube.followAlgorithmAttempt("lc"); // one left clockwise turn
            Assert.assertEquals(CubeStatus.OK, cubeUtils.validateCube(cube));
            Assert.assertNotEquals(cube.getFullAnnotationString(), cubeText);

            cube.followAlgorithmAttempt("la"); // now go anti-clockwise putting it back to how it was

            Assert.assertEquals(cube.getFullAnnotationString(), cubeText);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void frontTurnTest() {
        try {

            Cube cube = new Cube();
            cube.buildCubeFromString(notation);
            String cubeText = cube.getFullAnnotationString();
            cube.followAlgorithmAttempt("fc"); // one  turn
            Assert.assertEquals(CubeStatus.OK, cubeUtils.validateCube(cube));
            Assert.assertNotEquals(cube.getFullAnnotationString(), cubeText);
            cube.followAlgorithmAttempt("3fc"); // 3 more turns putting it back to original state
            Assert.assertEquals(cube.getFullAnnotationString(), cubeText);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void upperTurnTest() {
        try {

            Cube cube = new Cube();
            cube.buildCubeFromString(notation);
            String cubeText = cube.getFullAnnotationString();
            cube.followAlgorithmAttempt("uc"); // one turn
            Assert.assertEquals(CubeStatus.OK, cubeUtils.validateCube(cube));
            Assert.assertNotEquals(cube.getFullAnnotationString(), cubeText);
            cube.followAlgorithmAttempt("3uc"); // 3 more turns putting it back to original state
            Assert.assertEquals(cube.getFullAnnotationString(), cubeText);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

}