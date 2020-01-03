package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CubeTest {

    @Test
    public void algorithmTest() {
        Cube c = new Cube();
        try {
            c.followAlgorithem("fc lc lc");
        }
        catch (Exception ex) {
            Assert.fail(ex.getMessage());
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