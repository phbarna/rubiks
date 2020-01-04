package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CubeTest {

    @Test
    public void cubeSetup() {
        Cube c = new Cube();
    }

    /**
     * reuse - thus avoids repeating similar code from getCubeOfColourTest
     * @param cube the cube
     * @param c the colour to test against
     */
    private void doColourTest(Cube cube, Colour c) {
        try {
            Square[] squares = cube.getCubesOfColour(c);
            if (squares.length != 9) {
                Assert.fail("Must be 9 squares here for "+c.toString());
            }
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void getCubesOfColourTest() {
        // goes through the enum list and checks that there are 9 squares matching each colour
        Cube cube = new Cube();
        for (Colour theColour: Colour.values()) {
            doColourTest(cube, theColour);
        }
    }

    @Test
    public void algorithmTestFail() {

        Cube c = new Cube();
        try {
            c.followAlgorithm("fc lc lcx"); // fc and lc are ok, but lcx should fail
            Assert.fail("should not get here");
        } catch (Exception ex) {
            Assert.assertEquals("algorith does not support lcx", ex.getMessage());
        }
    }

    @Test
    public void SixColoursTest() {
        Cube c = new Cube();
        Side greenSide = c.getGreenSide();
        Assert.assertEquals(Colour.GREEN, greenSide.getColour());
        Side whiteSide = c.getWhiteSide();
        Assert.assertEquals(Colour.WHITE, whiteSide.getColour());
        Side redSide = c.getRedSide();
        Assert.assertEquals(Colour.RED, redSide.getColour());
        Side orangeSide = c.getOrangeSide();
        Assert.assertEquals(Colour.ORANGE, orangeSide.getColour());
        Side blueSide = c.getBlueSide();
        Assert.assertEquals(Colour.BLUE, blueSide.getColour());
        Side yellowSide = c.getYellowSide();
        Assert.assertEquals(Colour.YELLOW, yellowSide.getColour());
    }


}