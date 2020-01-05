package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CornerMiniCubeTest {

    @Test
    public void colourRegexFail() {
        CornerMiniCube cs = new CornerMiniCube();
        try {
            cs.setColours("micky mouse");
        } catch (Exception ex) {
            Assert.assertEquals("Cannot parse micky mouse", ex.getMessage());
        }
    }

    @Test
    public void colourPass() {
        CornerMiniCube cs = new CornerMiniCube();
        try {
            cs.setColours("rwb");
            Colour[] colours  = cs.getColours();
            Assert.assertTrue(colours[0].equals(Colour.r));
            Assert.assertTrue(colours[1].equals(Colour.w));
            Assert.assertTrue(colours[2].equals(Colour.b));
            Assert.assertEquals(3, colours.length);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }


}