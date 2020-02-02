package rubiks;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CubeTest {

    private CubeUtils cubeUtils = new CubeUtils();

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
            Assert.assertEquals(CubeStatus.OK, status);
            cube.followAlgorithm("3rc", false); // 2 clockwise turns

            // we have sent 4 right turn requests so would expect the returned string to be identical to the state of cube
            Assert.assertEquals(cubeText,cube.getFullAnnotationString());

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
            Assert.assertEquals(-1, result);
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


//ooooobooo
//bbbbbobbb
//yyyyyyyyy
//ggggggggg
//rrrrrrrrr
//wwwwwwwww
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
     *  should be on for most of the time
     */
    @Test
    @Ignore
    public void speedTest() {
         try {
             Cube shuffledCube = new Cube().asShuffled();
             LocalDateTime now1 = LocalDateTime.now();
             String[] commands = { "lc", "rc", "uc", "bc", "fc", "dc,", "la", "ra", "ua", "ba", "fa", "da"};
             for (int i =0; i< 100000; i++) {
                 int rand = (int) (Math.random() * commands.length);
                 shuffledCube.followAlgorithm(commands[rand], false);
             }
             LocalDateTime now2 = LocalDateTime.now();
             long diff = ChronoUnit.SECONDS.between(now1, now2);
             System.out.println("took "+diff + " seconds");

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