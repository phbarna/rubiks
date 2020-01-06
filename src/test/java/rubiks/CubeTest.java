package rubiks;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class CubeTest {

    @Test
    public void cubeSetup() {
        try {
            Cube c = new Cube();
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void cubeTurnTest() {
        try {
            Cube cube = new Cube();
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
    public void SixColoursTest() {
        try {
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
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }


}