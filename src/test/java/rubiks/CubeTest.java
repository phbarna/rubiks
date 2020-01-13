package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CubeTest {

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
            CubeUtils utils = new CubeUtils();
            CubeStatus status =  utils.validateCube(cube);
            Assert.assertEquals(CubeStatus.OK, status);
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

            cube.followAlgorithmAttempt("rc");
            cube.followAlgorithmAttempt("3rc");
            // we have sent 4 right turn requests so would expect the returned string to be identical
            Assert.assertEquals(cubeText,cube.getFullAnnotationString());

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }


    @Test
    public void algorithmTest() {
        try {
            Cube c = new Cube().asSolved();
            c.followAlgorithmAttempt("rc");
            System.out.println(c.getFullAnnotationString() +"\n");
            c.followAlgorithmAttempt("rc");
            System.out.println(c.getFullAnnotationString() +"\n");
            boolean result = c.followAlgorithmAttempt("ra");


            System.out.println(c.getFullAnnotationString() +"\n");
            c.followAlgorithmAttempt("rc");

            System.out.println(c.getFullAnnotationString() +"\n");
            c.followAlgorithmAttempt("rc");

            System.out.println(c.getFullAnnotationString() +"\n");

            CubeUtils utils = new CubeUtils();
            CubeStatus status = utils.validateCube(c);
            Assert.assertEquals(CubeStatus.OK, status);
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

            // proof that the cube we have just created is can return a string that can return a valid cube
            Cube cube2 = new Cube();
            Assert.assertEquals(CubeStatus.OK, cube2.buildCubeFromString(c.getDisplayAnnotation()));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void buildRealWorldCubeWithCornerSwap() { // poke in a random cube which but I have subtly swapped 2 corners over
        String notation = "yywoworgy" + "\n" +
                "oogrowogr" + "\n" +
                "rbgbbyogg" + "\n" +
                "rbworyrrw" + "\n" +
                "owbbgybbb" + "\n" +
                "yrwgywywg" + "\n";
        try {
            Cube cube = new Cube();

            CubeUtils cubeUtils = new CubeUtils();
            CubeStatus status = cube.buildCubeFromString(notation);
            Assert.assertEquals(CubeStatus.CORNER_MATCH_ERROR, status);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
}
