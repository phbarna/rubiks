package rubiks;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

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
    public void colourRegexPass() {
        CornerSquare cs = new CornerSquare();
        try {
            cs.setColours("r w b");
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }


}