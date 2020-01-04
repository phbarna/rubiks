package rubiks;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * extracts some functionality from the Cube class to make the Cube class more readable
 */
public class CubeUtils {
    /**
     * Build all 6 sides using a fixed instructions as follows.
     * As we look at the cube
     * orange is front
     * blue is right
     * red is back
     * green is left
     * yellow is top
     * white is bottom
     *
     *
     * Our notation works line by line for each where the correspondsing and unique square represents the 1 2 or 3 letters
     * the first letter of each square represents the outward facing colour for that position. The C is the center square and is fixed colour
     * flt ft frt fl C fr flb fb frb
     * flt ft frt fl C fr flb fb frb
     * flt ft frt fl C fr flb fb frb
     * flt ft frt fl C fr flb fb frb
     *
     * works in this order front right back left - that's all we need as top and bottom can then be worked out in code
     *
     * Study the buildSolvedCube method (which calls this to see how it builds up the sides)
     * @param notation
     */
    public Side[] buildSides(String notation) throws Exception {
        Side[] sidesToReturn = new Side[6];
        // we build our top and bottom rows... means user has to put in less information
        // and we can calculate this from the other rows.
        String[] topFacePositions = new String[9];
        topFacePositions[4] = "y"; // this is set in stone
        String[] bottomFacePositions = new String[9];
        bottomFacePositions[4] = "w"; // this is set in stone

        StringBuilder topRowNotation = new StringBuilder();
        StringBuilder bottomRowNotation = new StringBuilder();
        String[] lines = notation.split("\n");

        for (String line: lines) {
            // first do some validation - this is by no means concrete as
            // it does not cater for duplicates - the cube objecgt should hand this issue
            if (!Pattern.matches("[rgbyow]{3} [rgbyow]{2} [rgbyow]{3} " +
                            "[rgbyow]{2} [rgbyow] [rgbyow]{2} [rgbyow]{3} [rgbyow]{2} [rgbyow]{3}",
                    line)) {
                throw new Exception("Error trying to build side - with: " +line);
            }

            String[] squareStrings = line.split(" ");
            // the center square position is at 14 in the string (this is set in stone)


            switch (squareStrings[4]) { // position 4 is critical - it is the center of each side
                case "o": { // o is front
                    // extract position 6 for top with the information we have
                    topFacePositions[6] = squareStrings[0].substring(2,3)
                            + squareStrings[0].substring(1,2)
                            + squareStrings[0].substring(0,1);
                    topFacePositions[7] = squareStrings[1].substring(1,2)
                            + squareStrings[1].substring(0,1);
                    topFacePositions[8] = squareStrings[2].substring(2,3)
                            + squareStrings[2].substring(1,2)
                            + squareStrings[2].substring(0,1);

                    // now do the same to extract what we can of the bottom layer
                    bottomFacePositions[0] = squareStrings[6].substring(2,3)
                            + squareStrings[6].substring(1,2)
                            + squareStrings[6].substring(0,1);
                    bottomFacePositions[1] = squareStrings[7].substring(1,2)
                            + squareStrings[7].substring(0,1);
                    bottomFacePositions[2] = squareStrings[8].substring(2,3)
                            + squareStrings[2].substring(1,2)
                            + squareStrings[2].substring(0,1);


                    break;
                }
                case "b": {
                    topFacePositions[8] = squareStrings[0].substring(2,3)
                            + squareStrings[0].substring(1,2)
                            + squareStrings[0].substring(0,1);

                }

            }
            System.out.println("top face");
            for (String s: topFacePositions) {
                System.out.println(s);
            }
            System.out.println("\nbottom face");
            for (String s: bottomFacePositions) {
                System.out.println(s);
            }




        }
        // calculate top and bottom faces

        return sidesToReturn;
    }

    /**
     * This is actually a good example of how to build the sides using the instructions above (from buildSides() method) :-)
     */
    public Side[] buildSolvedCube() {
        Side[] sidesToReturn = new Side[6];
        // note that the top and bottom sides can be calculated from the information we have here
        String notation = "ogy oy oby og o ob ogw ow obw\n" + // orange side (front)
                "boy by bry bo b br bow bw bry\n"  + // right
                "rby ry rgy rb r rg rbw rw rgw\n"  +  // back
                "gry gy goy gr g go grw gw gow\n"; // left

        try {
            sidesToReturn = buildSides(notation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sidesToReturn;
    }


}
