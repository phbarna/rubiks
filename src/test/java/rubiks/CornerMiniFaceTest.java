package rubiks;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class CornerMiniFaceTest {

    @Test
    public void colourRegexFail() {

        try {
            CornerMiniFace cs = new CornerMiniFace().withColours("micky mouse");
            Assert.fail("Should not get here");
        } catch (Exception ex) {
            Assert.assertEquals("Error trying to build corner with: micky mouse", ex.getMessage());
        }
    }

    @Test
    public void colourLengthFai() {

        try {
            CornerMiniFace cs = new CornerMiniFace().withColours("by"); // corners have 3 colours so this will fail
            Assert.fail("Should not get here");
        } catch (Exception ex) {
            Assert.assertEquals("Error trying to build corner with: by", ex.getMessage());
        }
    }

    @Test
    public void colourPass() {
        try {
            CornerMiniFace cs = new CornerMiniFace().withColours("byr");
            Assert.assertEquals("byr", cs.toString());
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }


}