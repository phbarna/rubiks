package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CornerSquareTest {

    @Test
    public void colourRegexFail() {
        CornerSquare cs = new CornerSquare();
        try {
            cs.setColours("micky mouse");
        } catch (Exception ex) {
            Assert.assertEquals("Cannot parse micky mouse", ex.getMessage());
        }
    }

    @Test
    public void colourPass() {
        CornerSquare cs = new CornerSquare();
        try {
            cs.setColours("r w b");
            Colour[] colours  = cs.getColours();
            Assert.assertTrue(colours[0].equals(Colour.RED));
            Assert.assertTrue(colours[1].equals(Colour.WHITE));
            Assert.assertTrue(colours[2].equals(Colour.BLUE));
            Assert.assertEquals(3, colours.length);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }


}