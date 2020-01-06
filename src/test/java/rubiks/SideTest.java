package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class SideTest {


    @Test
    public void TestSingleSide() {
        try {
            Side s = new Side().withColour(Colour.o);
            String notation = "ogy oy oby og o ob ogw ow obw"; // corectly formatted string with no duplicates
            s.setSquaresandColours(notation);
            String returnedNotation  = s.toString();
            Assert.assertEquals(notation, returnedNotation);

        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void TestSingleSideCurrentColours() {

        try {
            Side s = new Side().withColour(Colour.o);
            String notation = "ogy oy oby og o ob ogw ow obw"; // corectly formatted string with no duplicates
            s.setSquaresandColours(notation);
            String returnedColours  = s.getAllColours();
            System.out.println(returnedColours);
            Assert.assertTrue(returnedColours.startsWith("ooo\no"));

        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test public void TestSingleSideFail() {
        Side s = new Side().withColour(Colour.o);
        String notation = null;
        try {
            notation = "ogy oy oby oy o ob ogw ow obw"; // we have a duplicate combination so it will fail
            s.setSquaresandColours(notation);
            Assert.fail("Should not get here");
        } catch (Exception ex) {
            Assert.assertEquals("Error trying to build side - with: " +notation, ex.getMessage());
        }
    }
}