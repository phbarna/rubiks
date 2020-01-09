package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class CornerMiniFaceTest {

    @Test
    public void colourPass() {
        try {
            CornerMiniFace cs = new CornerMiniFace().withColours("byr");
            Assert.assertEquals("byr", cs.toString());
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
            ex.printStackTrace();
        }
    }
}