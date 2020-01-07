package rubiks;

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
     * Study the buildAsSolved method (which calls this to see how it builds up the sides)
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
                    break;
                }
                case "r": {
                    topFacePositions[0] = squareStrings[2].substring(2,3)
                            + squareStrings[2].substring(0,1)
                            + squareStrings[2].substring(1,2);

                    topFacePositions[1] = squareStrings[1].substring(1,2)
                            + squareStrings[1].substring(0,1);

                    bottomFacePositions[7] = squareStrings[7].substring(1,2)
                            + squareStrings[7].substring(0,1);
                    break;

                }
                case "g": {
                    topFacePositions[3] = squareStrings[1].substring(1,2)
                            + squareStrings[1].substring(0,1);

                    bottomFacePositions[3] = squareStrings[7].substring(1,2)
                            + squareStrings[7].substring(0,1);

                    bottomFacePositions[6] = squareStrings[6].substring(2,3)
                            + squareStrings[6].substring(0,1) +
                            squareStrings[6].substring(1,2);
                    break;
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

    /**
     * check that the 3 or 2 colours match but NOT in the right order
     * @param face1
     * @param face2
     * @return
     */
    public CubeStatus checkMatch(MiniFace face1, MiniFace face2) {

        // immediate fail - cannot match edges with corners etc
        if (face1.getColours().length != face2.getColours().length) {
            return CubeStatus.EDGE_AND_CORNER_MATCH_ERROR;
        }

        HashSet<Colour> colursHS = new HashSet<>(); // will ensure all the colours match
        Colour[] face1Colours = face1.getColours();
        Colour[] face2Colours = face2.getColours();
        int numMatches = 0;
        for (int i = 0; i< face1Colours.length; i++) {
            colursHS.add(face1Colours[i]);
            colursHS.add(face2Colours[i]);
            if (face1Colours[i].equals(face2Colours[i])) {
                numMatches++;
            }
        }
        if (numMatches == face1Colours.length) { // implies that all the colours from both cubes match which cannot happen
            if (face1Colours.length == 2)
                return CubeStatus.EDGE_MATCH_SAME_ERROR;
            else
                return CubeStatus.CORNER_MATCH_SAME_ERROR;
        }

        if ((colursHS.size() == face1.getColours().length) &&
                (colursHS.size() == face2.getColours().length)
        ) {
            return CubeStatus.OK;
        }
        else {
            if (face1Colours.length == 2)
                return CubeStatus.EDGE_MATCH_ERROR;
            else
                return CubeStatus.CORNER_MATCH_ERROR;
        }
    }

    public CubeStatus validateSide(String notation) {
        if (!Pattern.matches("[rgbyow]{3} [rgbyow]{2} [rgbyow]{3} " +
                        "[rgbyow]{2} [rgbyow] [rgbyow]{2} [rgbyow]{3} [rgbyow]{2} [rgbyow]{3}",
                notation)) {
            return CubeStatus.SIDE_VALIDATION_ERROR;
        }

        HashSet<String> uniqueHashSet = new HashSet<>();
        String[] uniqueStrings = notation.split(" ");
        for (String uniqwueStringWeHope: uniqueStrings) {
            uniqueHashSet.add(uniqwueStringWeHope);
        }
        if (uniqueHashSet.size() != 9) {
            return CubeStatus.SIDE_NOT_UNIQUE_ERROR;
        }
        return CubeStatus.OK;
    }

    /**
     * validates the whole cube by matching appropriate edges and corners.
     * If validation fails it returns some kind of message as a string.
     * If validation passes it returns "ok"
     * @param cube
     * @return
     */
    public CubeStatus validateCube(Cube cube) {
        CubeStatus errorStatus = CubeStatus.OK; // default condition

        // let's do top corners
        MiniFace miniFace1 = cube.getOrangeSide().getMiniFace(0,0); // top left corner of orange
        MiniFace miniFace2 = cube.getGreenSide().getMiniFace(0,2); // top left corner of orange
        MiniFace miniFaceTop = cube.getYellowSide().getMiniFace(2,0); // top left corner of orange

        //orange green
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        errorStatus = checkMatch(miniFace1, miniFaceTop);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        // orange blue
        miniFace1 = cube.getOrangeSide().getMiniFace(0,2);
        miniFace2 = cube.getBlueSide().getMiniFace(0,0);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniFaceTop = cube.getYellowSide().getMiniFace(2,2);
        errorStatus = checkMatch(miniFace1, miniFaceTop);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        // blue red
        miniFace1 = cube.getBlueSide().getMiniFace(0,2);
        miniFace2 = cube.getRedSide().getMiniFace(0,0);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniFaceTop = cube.getYellowSide().getMiniFace(0,2);
        errorStatus = checkMatch(miniFace1, miniFaceTop);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        // red green
        miniFace1 = cube.getRedSide().getMiniFace(0,2);
        miniFace2 = cube.getGreenSide().getMiniFace(0,0);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniFaceTop = cube.getYellowSide().getMiniFace(0,0);
        errorStatus = checkMatch(miniFace1, miniFaceTop);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        // let's do bottom corners
        miniFace1 = cube.getOrangeSide().getMiniFace(2,2); // top left corner of orange
        miniFace2 = cube.getBlueSide().getMiniFace(2,0); // top left corner of orange
        MiniFace miniFaceBottom = cube.getWhiteSide().getMiniFace(0,2); // top left corner of orange
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        errorStatus = checkMatch(miniFace1, miniFaceBottom);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getBlueSide().getMiniFace(2,2);
        miniFace2 = cube.getRedSide().getMiniFace(2,0);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniFaceBottom = cube.getWhiteSide().getMiniFace(2,2);
        errorStatus = checkMatch(miniFace1, miniFaceBottom);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getRedSide().getMiniFace(2,2);
        miniFace2 = cube.getGreenSide().getMiniFace(2,0);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniFaceBottom = cube.getWhiteSide().getMiniFace(2,0);
        errorStatus = checkMatch(miniFace1, miniFaceBottom);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getGreenSide().getMiniFace(2,2);
        miniFace2 = cube.getOrangeSide().getMiniFace(2,0);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniFaceBottom = cube.getWhiteSide().getMiniFace(0,0);
        errorStatus = checkMatch(miniFace1, miniFaceBottom);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        // let's do the 4 edge pieces top pieces
        miniFace1 = cube.getOrangeSide().getMiniFace(0,1);
        miniFace2 = cube.getYellowSide().getMiniFace(2,1);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getBlueSide().getMiniFace(0,1);
        miniFace2 = cube.getYellowSide().getMiniFace(1,2);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getRedSide().getMiniFace(0,1);
        miniFace2 = cube.getYellowSide().getMiniFace(0,1);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getGreenSide().getMiniFace(0,1);
        miniFace2 = cube.getYellowSide().getMiniFace(1,0);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        // bottom edges and we're done :-)
        miniFace1 = cube.getOrangeSide().getMiniFace(2,1);
        miniFace2 = cube.getWhiteSide().getMiniFace(0,1);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getBlueSide().getMiniFace(2,1);
        miniFace2 = cube.getWhiteSide().getMiniFace(1,2);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getRedSide().getMiniFace(2,1);
        miniFace2 = cube.getWhiteSide().getMiniFace(2,1);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        miniFace1 = cube.getGreenSide().getMiniFace(2,1);
        miniFace2 = cube.getWhiteSide().getMiniFace(1,0);
        errorStatus = checkMatch(miniFace1, miniFace2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        return errorStatus.OK;
    }

}
