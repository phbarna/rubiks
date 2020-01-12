package rubiks;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * Represents a side of the cube.
 */
public class Side {

    private Colour sideColour; // this MUST not be changed once it is set.

    /**
     * miniFaceColour Keeps track of the physica colours looking from the sides - useful for working out orientation of each minicube.
     * Also makes life easier when actually outputting the 9 colours of any face.
     * Note that this should correspond with one of the colours on the square occupying the same place in
     * the array - else something has gone wrong and an exception should be thrown.
     * The annotation of these strings works front side topBottom order. For example
     * <p>
     * example
     * =======
     * wrb on a blue face
     * obviously a corner piece.  Means that the square on the blue face is currently showing white,
     * the red is on the red (right face) and the blue would is displaying on the top (yellow face).
     * Thus the square on this blue face would have to be at array position 2.
     */
    private MiniFace[][] miniFaces = new MiniFace[3][3];

    /**
     * used for extracting the 3 blocks that are being rotated
     *
     * @param n
     * @return
     * @throws Exception
     */
    public MiniFace[] getColumn(int n) throws Exception {
        if (n > 2 || n < 0) {
            throw new Exception("n must be bertween 0 and 2 for extracting column");
        }
        MiniFace[] returnColumn = new MiniFace[3];
        for (int i = 0; i < 3; i++) {
            returnColumn[i] = this.miniFaces[i][n];
        }
        return returnColumn;
    }

    /**
     * used for extracting the 3 blocks that are being rotated
     *
     * @param n
     * @return
     * @throws Exception
     */
    public MiniFace[] getRow(int n) throws Exception {
        if (n > 2 || n < 0) {
            throw new Exception("n must be bertween 0 and 2 for extracting row");
        }
        MiniFace[] returnRow = new MiniFace[3];
        for (int i = 0; i < 3; i++) {
            returnRow[i] = this.miniFaces[n][i];
        }
        return returnRow;
    }

    public void setRow(int n, MiniFace[] rowIn) throws Exception {
        if (n > 2 || n < 0) {
            throw new Exception("n must be bertween 0 and 2 for extracting row");
        }
        if (rowIn.length != 3) {
            throw new Exception("row length must be 3 for setting a row in a side.");
        }

        for (int i = 0; i < 3; i++) {
            miniFaces[n][i] = rowIn[i];
        }
    }

    public void setColumn(int n, MiniFace[] columnIn) throws Exception {
        if (n > 2 || n < 0) {
            throw new Exception("n must be bertween 0 and 2 for extracting column");
        }
        if (columnIn.length != 3) {
            throw new Exception("column length must be 3 for setting a column in a side.");
        }

        for (int i = 0; i < 3; i++) {
            miniFaces[i][n] = columnIn[i];
        }
    }

    public void setMiniColourFaces(String line) {
        line = line.replace(" ","");
        String[] stringColours = line.split(""); // already validated as 9 hopefully :-)
        this.sideColour = Colour.valueOf(stringColours[4]);
        int index = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int cubeType = index % 2;
                if (index == 4) { // center piece
                    this.sideColour = Colour.valueOf(stringColours[index]);
                    miniFaces[r][c] = new CentreAxleMiniFace().withColours(stringColours[index]);
                } else if (cubeType == 0) { // corner piece
                    miniFaces[r][c] = new CornerMiniFace().withColours(stringColours[index] + "ww"); // put dummy whites in for now
                } else { // edge piece
                    miniFaces[r][c] = new EdgeMiniFAce().withColours(stringColours[index] + "w"); // put in dummy white for now
                }

                // miniFaces[r][c].setFaceColourFromString(stringColours[index]);
                index++;
            }
        }
    }

    /**
     * check that all faceColours for this side are equal to this colour
     *
     * @return
     */
    public boolean checkSolvedSide() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!miniFaces[i][j].faceColour.equals(sideColour)) {
                    return false;
                }
            }
        }
        return true;
    }

    public MiniFace[] makeRowColCopy(MiniFace[] rowCol) {
        MiniFace[] copy = new MiniFace[3];
        for (int i = 0; i < 3; i++) {
            copy[i] = rowCol[i];
        }
        return copy;
    }

    public void rotateFace(int numberOfTurns) throws Exception {
        numberOfTurns = numberOfTurns % 4;

        MiniFace[] topRow = getRow(0);
        MiniFace[] topRowCopy = makeRowColCopy(topRow);

        MiniFace[] bottomRow = getRow(2);
        MiniFace[] bottomRowCopy = makeRowColCopy(bottomRow);

        MiniFace[] leftCol = getColumn(0);
        MiniFace[] leftColCopy = makeRowColCopy(leftCol);

        MiniFace[] rightCol = getColumn(2);
        MiniFace[] rightColCopy = makeRowColCopy(rightCol);

        for (int i = 0; i < numberOfTurns; i++) {
            // top row goes to right column
            setColumn(2, topRow);
            // right column to bottom row
            setRow(2, rightColCopy);
            // bottom row to left column
            setColumn(0, bottomRowCopy);
            // left col to top row
            setRow(0, leftColCopy);
        }
    }

    // deprecated
    public boolean validateSide(String notation) {
        try {
            // validation the notation string
            if (!Pattern.matches("[rgbyow]{3} [rgbyow]{2} [rgbyow]{3} " +
                            "[rgbyow]{2} [rgbyow] [rgbyow]{2} [rgbyow]{3} [rgbyow]{2} [rgbyow]{3}",
                    notation)) {
                return false;
            }
            String[] blocks = notation.split(" ");
            if (!this.sideColour.toString().equals(blocks[4])) {
                return false;
            }

            HashSet<String> uniqueHashSet = new HashSet<>();
            String[] uniqueStrings = notation.split(" ");
            for (String uniqwueStringWeHope : uniqueStrings) {
                uniqueHashSet.add(uniqwueStringWeHope);
            }
            if (uniqueHashSet.size() != 9) {
                return false;
            }

            // note that array position 4 HAS to correspond with the colour of this side - this is critical and is also checked elsewhere
            if (!this.sideColour.toString().equals(blocks[4])) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * @param notation a string representation e.g "boy by bry bo b br bow bw brw
     *                 which reads left to right, top to bottom of each side. First colour is the forward facing
     *                 2nd colour is nearest edge and 3rd colour is top or bottom
     * @return this object
     */
    public Side setSquaresandColours(String notation) throws Exception {

        HashSet<String> uniqueHashSet = new HashSet<>();
        String[] uniqueStrings = notation.split(" ");
        for (String uniqwueStringWeHope : uniqueStrings) {
            uniqueHashSet.add(uniqwueStringWeHope);
        }
        if (uniqueHashSet.size() != 9) {
            throw new Exception("Error trying to build side - with: " + notation);
        }

        String[] blocks = notation.split(" ");
        // note that array position 4 HAS to correspond with the colour of this side - this is critical and is also checked elsewhere
        if (!this.sideColour.toString().equals(blocks[4])) {
            throw new Exception("The side you are trying to build does not correspond with the colour of this side" +
                    " for " + this.sideColour.toString() + " and " + blocks[4]);
        }

        int rowPosition = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (i == 3 || i == 6) {
                rowPosition++;
            }
            int columnPosition = i % 3;

            if (blocks[i].length() == 1) {
                this.miniFaces[rowPosition][columnPosition] = new CentreAxleMiniFace().withColours(blocks[i]);
            } else if (blocks[i].length() == 2) {
                this.miniFaces[rowPosition][columnPosition] = new EdgeMiniFAce().withColours(blocks[i]);
            } else if (blocks[i].length() == 3) {
                this.miniFaces[rowPosition][columnPosition] = new CornerMiniFace().withColours(blocks[i]);
            } // no further validation required - previous validation ensures there is 1 2 or 3

        }
        return this;
    }

    /**
     * return string representation of the state of this side
     *
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        /**
         * iterate through 3 by 3 array
         */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(this.miniFaces[i][j].toString() + " ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * returns the outward facing colours - as 3 by 3 grid display. .  Nice to see how a side is doing,
     * especially for debugging.
     *
     * @return
     */
    public String getAllColoursForSide() {
        StringBuilder sb = new StringBuilder();
        /**
         * iterate through 3 by 3 array
         */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(this.miniFaces[i][j].toString().substring(0, 1));
                if (j == 2 || j == 5) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    public Colour getColour() {
        return sideColour;
    }

    public Side withColour(Colour c) {
        this.sideColour = c;
        return this;
    }

    /**
     * returns a miniFace determined by cordinates from this face
     *
     * @param x the position in the horizontal axis as we look at this face
     * @param y the position in the vertical axis as we look at this face
     */
    public MiniFace getMiniFace(int x, int y) {
        return this.miniFaces[x][y];
    }
}
