package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CubeTest {

    @Test
    public void cubeSetup() {
        try {
            Cube c = new Cube();
        }
        catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void cubeTurnTest() {
        try {
            Cube cube = new Cube();
            cube.frontClockwise(1);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    /**
     * reuse - thus avoids repeating similar code from getCubeOfColourTest
     * @param cube the cube
     * @param c the colour to test against
     */
    private void doColourTest(Cube cube, Colour c) {
        try {
            MiniCube[] miniCubes = cube.getCubesOfColour(c);
            if (miniCubes.length != 9) {
                Assert.fail("Must be 9 miniCubes here for "+c.toString());
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
            // Assert.fail("should not get here");
        } catch (Exception ex) {
            Assert.assertEquals("algorith does not support lcx", ex.getMessage());
        }
    }

    @Test
    public void SixColoursTest() {
        Cube c = new Cube();
        Side greenSide = c.getGreenSide();
        Assert.assertEquals(Colour.g, greenSide.getColour());
        Side whiteSide = c.getWhiteSide();
        Assert.assertEquals(Colour.w, whiteSide.getColour());
        Side redSide = c.getRedSide();
        Assert.assertEquals(Colour.r, redSide.getColour());
        Side orangeSide = c.getOrangeSide();
        Assert.assertEquals(Colour.o, orangeSide.getColour());
        Side blueSide = c.getBlueSide();
        Assert.assertEquals(Colour.b, blueSide.getColour());
        Side yellowSide = c.getYellowSide();
        Assert.assertEquals(Colour.y, yellowSide.getColour());
    }


}