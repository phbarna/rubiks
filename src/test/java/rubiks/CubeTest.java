package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CubeTest {

    @Test
    public void solvedCubeTest() {
        try {
            Cube c = new Cube().asSolved();

            CubeUtils cubeUtils = new CubeUtils();
            boolean solved = cubeUtils.checkSolvedState(c);
            Assert.assertTrue(solved);
            String returnString = c.getDisplayAnnotation();
            // if we can reconstruct the cube as defined by the string we've just returned.... then that's good :-)
            Cube newCube = new Cube();
            newCube.buildCubeFromString(returnString);
            CubeStatus status = cubeUtils.validateCube(newCube);
            Assert.assertEquals(CubeStatus.OK, status);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());

        }
    }

    @Test
    public void cubeTurnTest() {
        try {
            Cube cube = new Cube().asSolved();
            cube.rightClockwise(1);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void algorithmTest() {
        try {
            Cube c = new Cube();
            boolean result = c.followAlgorithmAttempt("2lc ra rc la fc 2uc fa");
            Assert.assertTrue(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void algorithmTestFail() {
        try {
            Cube c = new Cube();
            boolean result = c.followAlgorithmAttempt("rc 2ra lxx"); //lcx not valid
            Assert.assertFalse(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }


    @Test
    public void builCubeWithError() {
        String notation = "orryoboyy" + "\n" +
                "gogwbybgr" + "\n" +
                "ryworowrg" + "\n" +
                "owywgryow" + "\n" +
                "ggwrygbgy" + "\n" +
                "bbybwwobb";
        Cube c = new Cube();
        try {
            CubeStatus status = c.buildCubeFromString(notation);
            Assert.assertEquals(CubeStatus.COLOUR_DISTRIBUTION_ERROR, status);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void buildRealWorldCube() { // poke in a random cube which I haved in front of me and test validation
        String notation = "yywoworgy" + "\n" +
                "rogrowogo" + "\n" +
                "orgbbybgg" + "\n" +
                "rbworyrrw" + "\n" +
                "owbbgybbb" + "\n" +
                "grwgywywy" + "\n";

        Cube c = new Cube();

        try {
            CubeStatus status = c.buildCubeFromString(notation);
            CubeUtils utils = new CubeUtils();
            boolean solved = utils.checkSolvedState(c);
            Assert.assertEquals(CubeStatus.OK, status);
            Assert.assertFalse(solved); // just check it isn't returning true here.

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void buildRealWorldCubeWithCornerSwap() { // poke in a random cube which but I have subtly swapped 2 corners over
        String notation = "rogrowbbb" +
                "orgbbyogo" +
                "rbworybgg" +
                "owbbgyrrw" +
                "grwgywywy" +
                "roygwyyow";

        try {
            Cube cube = new Cube();

            CubeUtils cubeUtils = new CubeUtils();
            CubeStatus status = cube.buildCubeFromString(notation);
            Assert.assertEquals(CubeStatus.SIDE_ERROR_UNKNOWN, status);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
}
