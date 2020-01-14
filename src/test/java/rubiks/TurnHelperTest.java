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
            cube.followAlgorithm("rc", false); // one clockwise turn
            CubeStatus status = utils.validateCube(cube);
            Assert.assertEquals(CubeStatus.OK, status);
            cube.followAlgorithm("rc rc rc", false); // 3 more clockwise turns

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

            cube.followAlgorithm("lc", false); // one left clockwise turn
            Assert.assertEquals(CubeStatus.OK, cubeUtils.validateCube(cube));
            Assert.assertNotEquals(cube.getFullAnnotationString(), cubeText);

            cube.followAlgorithm("la", false); // now go anti-clockwise putting it back to how it was

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
            cube.followAlgorithm("fc", false); // one  turn
            Assert.assertEquals(CubeStatus.OK, cubeUtils.validateCube(cube));
            Assert.assertNotEquals(cube.getFullAnnotationString(), cubeText);
            cube.followAlgorithm("3fc", false); // 3 more turns putting it back to original state
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
            cube.followAlgorithm("uc", false); // one turn
            Assert.assertEquals(CubeStatus.OK, cubeUtils.validateCube(cube));
            Assert.assertNotEquals(cube.getFullAnnotationString(), cubeText);
            cube.followAlgorithm("3uc", false); // 3 more turns putting it back to original state
            Assert.assertEquals(cube.getFullAnnotationString(), cubeText);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void backTurnTest() {
        try {

            Cube cube = new Cube();
            cube.buildCubeFromString(notation);
            String cubeText = cube.getFullAnnotationString();
            cube.followAlgorithm("bc", false); // one turn
            Assert.assertEquals(CubeStatus.OK, cubeUtils.validateCube(cube));
            Assert.assertNotEquals(cube.getFullAnnotationString(), cubeText);
            cube.followAlgorithm("3bc", false); // 3 more turns putting it back to original state
            Assert.assertEquals(cube.getFullAnnotationString(), cubeText);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void downFaceTurnTest() {
        try {

            Cube cube = new Cube();
            cube.buildCubeFromString(notation);
            String cubeText = cube.getFullAnnotationString();
            cube.followAlgorithm("dc", false); // one turn
            Assert.assertEquals(CubeStatus.OK, cubeUtils.validateCube(cube));
            Assert.assertNotEquals(cube.getFullAnnotationString(), cubeText);
            cube.followAlgorithm("da", false); // 3 more turns putting it back to original state
            Assert.assertEquals(cube.getFullAnnotationString(), cubeText);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
}