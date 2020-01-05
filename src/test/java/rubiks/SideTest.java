package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class SideTest {


    @Test
    public void Test() {

        Cube cube = new Cube();
        Side s = new Side();
        try {
            Square[] squares = cube.getCubesOfColour(Colour.b); // ensures there should be 9

            s.setSquaresandColours(squares, new Colour[9]);

        } catch (Exception ex) {
            Assert.fail(ex.getMessage());

        }

    }


}