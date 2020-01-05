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
     *
     * example
     * =======
     * wrb on a blue face
     * obviously a corner piece.  Means that the square on the blue face is currently showing white,
     * the red is on the red (right face) and the blue would is displaying on the top (yellow face).
     * Thus the square on this blue face would have to be at array position 2.
     */
    private String[][] squareOrientationByColour = new String[3][3];

    /**
     *
     * @param notation a string representation e.g "boy by bry bo b br bow bw brw
     * which reads left to right, top to bottom of each side. First colour is the forward facing
     * 2nd colour is nearest edge and 3rd colour is top or bottom
     *
     * @return this object
     */
    public Side setSquaresandColours(String notation) throws Exception {
        // validation the notation string
        if (!Pattern.matches("[rgbyow]{3} [rgbyow]{2} [rgbyow]{3} " +
                        "[rgbyow]{2} [rgbyow] [rgbyow]{2} [rgbyow]{3} [rgbyow]{2} [rgbyow]{3}",
                notation)) {
            throw new Exception("Error trying to build side - with: " +notation);
        }

        HashSet<String> uniqueHashSet = new HashSet<>();
        String[] uniqueStrings = notation.split(" ");
        for (String uniqwueStringWeHope: uniqueStrings) {
            uniqueHashSet.add(uniqwueStringWeHope);
        }
        if (uniqueHashSet.size() != 9) {
            throw new Exception("Error trying to build side - with: " +notation);
        }

        String[] blocks = notation.split(" ");
        // note that array position 4 HAS to correspond with the colour of this side - this is critical and is also checked elsewhere
        if (!this.sideColour.toString().equals(blocks[4]))
        {
            throw new Exception("The side you are trying to build does not correspond with the colour of this side"+
            " for " +this.sideColour.toString() + " and "+blocks[4]);
        }

        int rowPosition = 0;
        for (int i = 0;i< blocks.length; i++) {
            if (i == 3 || i == 6) {
                rowPosition++;
            }
            int columnPosition = i % 3;
            squareOrientationByColour[rowPosition][columnPosition] = blocks[i];
        }
        return this;
    }






    public Colour getColour() {
        return sideColour;
    }

    public Side withColour(Colour c) {
        this.sideColour = c;
        return this;
    }
}
