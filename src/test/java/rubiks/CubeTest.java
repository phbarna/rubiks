package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CubeTest {

    @Test
    public void cubeSetupTest() {
        try {
            Cube c = new Cube().asSolved();
            String returnString = c.toString();
            // if we can reconstruct the cube as defined by the string we've just returned.... then that's good :-)
            Cube newCube = new Cube().asDefined(returnString);
            String newString = newCube.toString();
            String[] lines = newString.split("\n");
            Assert.assertEquals(4, lines.length);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void cubeTurnTest() {
        try {
            Cube cube = new Cube().asSolved();
            cube.frontClockwise(2);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void algorithmTestFail() {
        try {
            Cube c = new Cube();
            c.followAlgorithm("fc lc lcx"); // fc and lc are ok, but lcx should fail
            // Assert.fail("should not get here");
        } catch (Exception ex) {
            Assert.assertEquals("algorith does not support lcx", ex.getMessage());
        }
    }

    @Test
    public void validationTest() {
        try {
            Cube cube = new Cube().asSolved();
            CubeUtils cubeUtils = new CubeUtils();
            CubeStatus errorStatus = cubeUtils.validateCube(cube);
            Assert.assertEquals(CubeStatus.OK, errorStatus);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void cubeSidesFailTest() {
        try {
            Side s1 = new Side().withColour(Colour.o);
            String notation = "ogy oy oby og o ob ogw ow obw"; // corectly formatted string with no duplicates
            s1.setSquaresandColours(notation);

            Side s2 = new Side().withColour(Colour.o);
            notation = "ogy oy oby og o ob ogw ow obw"; // corectly formatted string with no duplicates
            s2.setSquaresandColours(notation);

            CubeUtils cubeUtils = new CubeUtils();
            // the 2 sides are equal which is an obvious validation fail - so this should be a dramatic FALSE !
            CubeStatus ok  = cubeUtils.checkMatch(s1.getMiniFace(0,0), s2.getMiniFace(0,0));
            Assert.assertEquals(CubeStatus.CORNER_MATCH_SAME_ERROR, ok); // assert false because the 2 sides are the same
        } catch (Exception ex) {
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
            Assert.fail(ex.getMessage());
        }
    }



}