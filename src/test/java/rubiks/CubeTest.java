package rubiks;

import org.junit.Assert;
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
    public void algorithmTest() {
        try {
            Cube c = new Cube().asSolved();

            boolean result = c.followAlgorithm("ra", false);

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
            boolean result = c.followAlgorithm("rc 2ra lxx", false); //lcx not valid
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
    public void tryAndSolve() {
         try {
            // for (int i = 0; i< 100000; i++)
             {
                 String algorithm = "lc 2rc bc la rc 2uc 2fc lc bc dc 2uc bc la 2rc 2bc la 2dc uc la 2bc 2uc 2rc bc 2dc"+
                         "lc 2rc bc la rc 2uc 2fc lc bc dc 2uc bc la 2rc 2bc la 2dc uc la 2bc 2uc 2rc bc 2d";

                 Cube c = new Cube().asShuffled();

                 String notation = "rrr yoo gwy" + "\n" +
                         "yro bbg byg" + "\n" +
                         "wwo orw owb" + "\n" +
                         "yry rgb rgr" + "\n" +
                         "ggb yyb ggb" + "\n" +
                         "woo ywo wbw";

                 CubeStatus status = c.buildCubeFromString(notation);
                 Assert.assertEquals(CubeStatus.OK, status);

                 c.followAlgorithm(notation, true);

                 boolean solved = cubeUtils.checkSolvedState(c);
                 if (solved) {
                     System.out.println("solved");

                 }

               //  c.getDisplaySidesForDebug();
             }
         //    c.getDisplaySidesForDebug();

         } catch (Exception ex) {
             ex.printStackTrace();
             Assert.fail(ex.getMessage());
         }
    }

    @Test
    //@Ignore // this is just an interest test to see how long masses of shuffles take
    public void loopShuffleTest() {
         try {
             Cube shuffledCube = new Cube().asShuffled();
             LocalDateTime now1 = LocalDateTime.now();
             for (int i =0; i< 1; i++) {
                 shuffledCube.shuffle();

                 boolean isSolved = cubeUtils.checkSolvedState(shuffledCube);
                 if (isSolved) {
                     System.out.println("SOLVED");
                     LocalDateTime now2 = LocalDateTime.now();
                     long diff = ChronoUnit.SECONDS.between(now1, now2);
                     System.out.println("took "+diff + " seconds");
                     break;
                 }

                 CubeStatus status = cubeUtils.validateCube(shuffledCube);
                 if (!status.equals(CubeStatus.OK)) {
                     System.err.println(status.getDescription());
                     break;
                 }
             }
             LocalDateTime now2 = LocalDateTime.now();
             long diff = ChronoUnit.SECONDS.between(now1, now2);
             System.out.println("took "+diff + " seconds");
             shuffledCube.getDisplaySidesForDebug();
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