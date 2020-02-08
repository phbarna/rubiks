package rubiks;

import org.junit.Assert;
import org.junit.Test;

public class SideTest {


    @Test
    public void copySideTest() {
        try {
            String sideColours = "rrrrrrrrr";
            Side side = new Side();

            side.setMiniColourFaces(sideColours);

            CubeUtils utils = new CubeUtils();
            Side copy = utils.copySide(side);
            MiniFace[] row = copy.getRow(0);

            // note this is a not equals to test as we want to test that the 2 objects are NOT the same reference - it is a copy
            Assert.assertNotEquals(side.getMiniFace(0,0), row[0]);

            // but we asset equals that the strings returned by these objects are equal
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
    public void notOppositeSidesCheck() {
        CornerMiniFace miniFace1 = new CornerMiniFace() .withColours("boy");
        CornerMiniFace miniFace2 = new CornerMiniFace() .withColours("yob");

        CubeUtils utils = new CubeUtils();
        boolean pass = utils.validateNotOppositeSides(miniFace1, miniFace2);
        Assert.assertTrue(pass);

        miniFace1 = new CornerMiniFace() .withColours("bgy"); // blue and green are opposites
        miniFace2 = new CornerMiniFace() .withColours("yob");
        pass = utils.validateNotOppositeSides(miniFace1, miniFace2);
        Assert.assertFalse(pass);
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

            // col 1 position 0 intersects row 0 pos 1 -
            Assert.assertEquals("br", col[0].toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
}