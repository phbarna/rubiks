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
            String returnedNotation = s.toString();
            Assert.assertEquals(notation, returnedNotation);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Test
    public void copySideTest() {
        try {
            String sideColours = "rrrrrrrrr";
            Side side = new Side();

            side.setMiniColourFaces(sideColours);

            CubeUtils utils = new CubeUtils();
            Side copy = utils.copySide(side);
            MiniFace[] row = copy.getRow(0);

            // note this is a not equals to test as we want to test that the 2 objects are not the same reference - it is a copy
            Assert.assertNotEquals(side.getMiniFace(0,0), row[0]);

            // but we asset equals that the strings retuirned by these objects are equal
            Assert.assertEquals(side.getMiniFace(0,0).toString(), row[0].toString());

            row[1].setColours("br");
            side.setRow(0, row);

            MiniFace[] col = side.getColumn(1);

            Assert.assertEquals("br", col[0].toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }


    @Test
    public void buildSideAndTestRowCols() {
        try {
            String sideColours = "grwgywywy";
            Side side = new Side();

            side.setMiniColourFaces(sideColours);

            MiniFace[] row = side.getRow(0);
            row[1].setColours("br");
            side.setRow(0, row);

            MiniFace[] col = side.getColumn(1);

            Assert.assertEquals("br", col[0].toString());

            boolean x = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void TestWrongColourMatchException() {

        try {
            Side s = new Side().withColour(Colour.b);
            String notation = "ogy oy oby og o ob ogw ow obw"; // note we are attempting to put a yellow line in to a blue side
            s.setSquaresandColours(notation);
            Assert.fail("Should not get here");
        } catch (Exception ex) {
            Assert.assertEquals("The side you are trying to build does not correspond with the colour of this side for b and o", ex.getMessage());
        }
    }

    @Test
    public void TestSingleSideCurrentColours() {

        try {
            Side s = new Side().withColour(Colour.o);
            String notation = "ogy oy oby og o ob ogw ow obw"; // correctly formatted string with no duplicates
            s.setSquaresandColours(notation);
            String returnedColours = s.getAllColoursForSide();
            Assert.assertTrue(returnedColours.startsWith("ooo\no"));
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void TestSingleSideFail() {
        Side s = new Side().withColour(Colour.o);
        String notation = null;
        try {
            notation = "ogy oy oby oy o ob ogw ow obw"; // we have a subtle duplicate combination so it will fail
            s.setSquaresandColours(notation);
            Assert.fail("Should not get here");
        } catch (Exception ex) {
            Assert.assertEquals("Error trying to build side - with: " + notation, ex.getMessage());
        }
    }
}