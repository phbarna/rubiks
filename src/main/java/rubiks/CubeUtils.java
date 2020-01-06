package rubiks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * extracts some functionality from the Cube class to make the actual Cube class more readable
 */
public class CubeUtils {

    /**
     *
     * works in this order front right back left - that's all we need as top and bottom can then be worked out in code
     *
     * Study the buildSolvedCube method (which calls this to see how it builds up the sides)
     * @param notation
     */
    public String[] addTopAndBottoms(String notation) throws Exception {
        String[] returnList = new String[6];
        // we build our top and bottom rows... means user has to put in less information
        // and we can calculate this from the other rows.
        String[] topFacePositions = new String[9];
        topFacePositions[4] = "y"; // this is set in stone
        String[] bottomFacePositions = new String[9];
        bottomFacePositions[4] = "w"; // this is set in stone

        StringBuilder topSideNotation_SB = new StringBuilder();
        StringBuilder bottomSideNotation_SB = new StringBuilder();
        String[] lines = notation.split("\n");

        // let's do some unique validation but dumping everything into a hashset and counting the results
        HashSet<String> hs = new HashSet<>();
        String[] stringsToTestUniqueness = notation.split("[ \n]");
        for (String uniqueStringWeHope: stringsToTestUniqueness) {
            hs.add(uniqueStringWeHope);
        }
        if (hs.size() != 36) {
            throw new Exception("Error during building sides - should be 36 not "+ hs.size());
        }
        for (int i = 0; i< lines.length;i++) {

            returnList[i] = lines[i];
            String[] squareStrings = lines[i].split(" ");
            if (squareStrings[4].equals("w") || squareStrings[4].equals("y") ) {
                throw new Exception("Error building cube. You do not put top and bottom of cube in - this is done for you.");
            }
            // the center square position is at 14 in the string (this is set in stone)
            /**
             * apologies to anybody reading the switch code below - it was just a case of transposing
             * information from the sides to get the top and bottom rows - this was done by actually having a
             * cube beside me and just working it all out by examining it whilst writing the code.
             * But once it's done - it's done.  Don't change this code !
             */
            switch (squareStrings[4]) { // position 4 is critical - it is the center of each side and NEVER changes
                case "o": { // o is front
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
                    topFacePositions[5] = squareStrings[1].substring(1,2)
                            + squareStrings[1].substring(0,1);
                    topFacePositions[2] = squareStrings[2].substring(2,3)
                            + squareStrings[2].substring(0,1)
                            + squareStrings[2].substring(1,2);

                    // now fill in bottom
                    bottomFacePositions[5] = squareStrings[7].substring(1,2)
                            + squareStrings[7].substring(0,1);

                    bottomFacePositions[8] = squareStrings[8].substring(2,3)
                         + squareStrings[8].substring(0,1)
                            + squareStrings[8].substring(1,2);
                }
                case "r": {
                    topFacePositions[0] = squareStrings[2].substring(2,3)
                            + squareStrings[2].substring(0,1)
                            + squareStrings[2].substring(1,2);

                    topFacePositions[1] = squareStrings[1].substring(1,2)
                            + squareStrings[1].substring(0,1);

                    bottomFacePositions[7] = squareStrings[7].substring(1,2)
                            + squareStrings[7].substring(0,1);

                }
                case "g": {
                    topFacePositions[3] = squareStrings[1].substring(1,2)
                            + squareStrings[1].substring(0,1);

                    bottomFacePositions[3] = squareStrings[7].substring(1,2)
                            + squareStrings[7].substring(0,1);

                    bottomFacePositions[6] = squareStrings[6].substring(1,2)
                            + squareStrings[6].substring(0,1) +
                            squareStrings[6].substring(2,3);
                }
            }
        }
        for (String s: topFacePositions) {
            topSideNotation_SB.append(s + " ");
        }

        for (String s: bottomFacePositions) {
            bottomSideNotation_SB.append(s + " ");
        }
        // we now have our top and bottom notations as well as all the sides
        String topSideNotation = topSideNotation_SB.toString().trim();
        String bottomSideNotation = bottomSideNotation_SB.toString().trim();

        // we now have our 6 lines of side notations.

        returnList[4] = topSideNotation;
        returnList[5] = bottomSideNotation;

        return returnList;
    }



}
