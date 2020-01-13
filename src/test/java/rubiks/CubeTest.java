package rubiks;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class CubeTest {

    @Test
    public void solvedCubeTest() {
        try {
            Cube c = new Cube().asSolved();
            c.getDisplaySidesForDebug();
            CubeUtils cubeUtils = new CubeUtils();
            boolean solved = cubeUtils.checkSolvedState(c);
            Assert.assertTrue(solved);
            String returnString = c.toString();
            // if we can reconstruct the cube as defined by the string we've just returned.... then that's good :-)
            Cube newCube = new Cube().asDefined(returnString);
            CubeStatus status  = cubeUtils.validateCube(newCube);
            Assert.assertEquals(CubeStatus.OK, status);
            String newString = newCube.toString();
            String[] lines = newString.split("\n");
            Assert.assertEquals(4, lines.length);
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
    public void algorithmTestFail() {
        try {
            Cube c = new Cube();
            boolean result = c.followAlgorithmAttempt("fc lc lcx"); //lcx not valid
            Assert.assertFalse(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void buildFailedCubeTest() {
        // note that the top and bottom sides can be calculated from the information we have here
        String notation = "obw oy oby og o ob ogw ow obw\n" + // orange side (front)
                "boy by bry bo b br bow bw brw\n" + // right
                "rby ry rgy rb r rg rbw rw rgw\n" +  // back
                "gry gy goy gr g go grw gw gow\n"; // left
        try {
            Cube cube = new Cube().asDefined(notation);
            Assert.fail("Should not get here");
        } catch (Exception ex) {
            Assert.assertEquals(CubeStatus.PIECES_NOT_UNIQUE_ERROR.getDescription(), ex.getMessage());
        }
    }

    @Test
    public void buildRealWorldCube() { // poke in a random cube which I haved in front of me and test validation
        // note that the top and bottom sides can be calculated from the information we have here
        String notation = "rogrowogo" + "\n" +
                "orgbbybgg" + "\n" +
                "rbworyrrw" + "\n"+
                "owbbgybbb" + "\n"+
                "grwgywywy" + "\n"+
                "yywoworgy";
        Cube c = new Cube();
        try {
            CubeStatus status = c.buildSidesFromString2(notation);


            System.out.println(c.toString());
            Assert.assertEquals(CubeStatus.OK, status);
            //  CubeUtils utils = new CubeUtils();
            //  CubeStatus status = utils.validateCube(c);
            //   System.out.println(status);
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
            CubeStatus status = cubeUtils.validateCube(cube);
            Assert.assertEquals(CubeStatus.CORNER_MATCH_ERROR, status);

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test

    public void buildFromString() {
        String notation = "rrrrrrrrr\n" +
                "ggggggggg\n" +
                "yyyyyyyyy\n" +
                "ooooooooo\n" +
                "bbbbbbbbb\n" +
                "wwwwwwwww";
        Cube c = new Cube();
        try {
            CubeStatus status = c.buildSidesFromString2(notation);


            System.out.println(c.toString());
            Assert.assertEquals(CubeStatus.OK, status);
          //  CubeUtils utils = new CubeUtils();
          //  CubeStatus status = utils.validateCube(c);
         //   System.out.println(status);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void cubeSidesCornerSideSameFailTest() {
        try {
            Side s1 = new Side().withColour(Colour.o);
            String notation = "ogy oy oby og o ob ogw ow obw"; // corectly formatted string with no duplicates
            s1.setSquaresandColours(notation);

            Side s2 = new Side().withColour(Colour.o);
            notation = "ogy oy oby og o ob ogw ow obw"; // corectly formatted string with no duplicates
            s2.setSquaresandColours(notation);

            CubeUtils cubeUtils = new CubeUtils();
            // the 2 sides are equal which is an obvious validation fail - so this should be a dramatic FALSE !
            CubeStatus status  = cubeUtils.checkMatch(s1.getMiniFace(0,0), s2.getMiniFace(0,0));
            Assert.assertEquals(CubeStatus.CORNER_MATCH_SAME_ERROR, status); // assert false because the 2 sides are the same
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }



    @Test
    public void cubeSidesEdgeCornerFail() { // see what goes wron when we try and match an edge with a corner
        try {
            Side s1 = new Side().withColour(Colour.o);
            String notation = "ogy oy oby og o ob ogw ow obw"; // corectly formatted string with no duplicates
            s1.setSquaresandColours(notation);

            Side s2 = new Side().withColour(Colour.b);
            notation = "boy by bry bo b br bow bw brw"; // correct solved blue side
            s2.setSquaresandColours(notation);

            CubeUtils cubeUtils = new CubeUtils();
            // we are trying to do something silly - i.e. matching an edge with a corner which should definitey fail validation
            CubeStatus ok = cubeUtils.checkMatch(s1.getMiniFace(0,1), s2.getMiniFace(0,0));
            Assert.assertEquals(CubeStatus.EDGE_AND_CORNER_MATCH_ERROR, ok); // assert false because the 2 sides are the same

            // now reverse them to check corner with edge
            ok = cubeUtils.checkMatch(s2.getMiniFace(0,0), s1.getMiniFace(0,1));
            Assert.assertEquals(CubeStatus.EDGE_AND_CORNER_MATCH_ERROR, ok); // assert false because the 2 sides are the same
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void cubeSidesValidTest() {
        try {
            Side orangeSide = new Side().withColour(Colour.o);
            String notation = "ogy oy oby og o ob ogw ow obw"; // correct solved orange side
            orangeSide.setSquaresandColours(notation);

            Side blueSide = new Side().withColour(Colour.b);
            notation = "boy by bry bo b br bow bw brw"; // correct solved blue side
            blueSide.setSquaresandColours(notation);
            CubeUtils cubeUtils = new CubeUtils();
            // do corner checks
            CubeStatus ok = cubeUtils.checkMatch(orangeSide.getMiniFace(0,2), blueSide.getMiniFace(0,0));
            Assert.assertEquals(CubeStatus.OK, ok); //

            // do the same for an edge piece
            ok = cubeUtils.checkMatch(orangeSide.getMiniFace(1,2), blueSide.getMiniFace(1, 0));
            Assert.assertEquals(CubeStatus.OK, ok);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

}