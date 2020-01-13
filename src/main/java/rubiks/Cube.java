package rubiks;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * represents the cube as a whole.
 * Should have public methods for turns
 * NOTE that the cube is always set up as solve as default.
 * Randomising the turns will mess the cube up
 * Future instructions for passing a specific state of the cube will be done
 */
class Cube {
    private CubeUtils cubeUtils = new CubeUtils();
    // create our six sides
    private Side whiteSide = null;
    private Side yellowSide = null;
    private Side blueSide = null;
    private Side redSide = null;
    private Side orangeSide = null;
    private Side greenSide = null;

    public Cube() {
        whiteSide = new Side().withColour(Colour.w);
        yellowSide = new Side().withColour(Colour.y);
        blueSide = new Side().withColour(Colour.b);
        redSide = new Side().withColour(Colour.r);
        orangeSide = new Side().withColour(Colour.o);
        greenSide = new Side().withColour(Colour.g);
    }

    /**
     * recommended to run either this method
     * ...or asShuffle
     * ... or asDefined when constructing the cube object - as not much point in having an orphan cube with no sides or faces etc.
     *
     * @return
     * @throws Exception
     */
    public Cube asSolved() throws Exception {

        String notation = "rrrrrrrrr\n" +
                "ggggggggg\n" +
                "yyyyyyyyy\n" +
                "ooooooooo\n" +
                "bbbbbbbbb\n" +
                "wwwwwwwww";
        CubeStatus status = buildCubeFromString(notation);
        // really would not expect this to fail so throw exception if it does
        if (!status.equals(CubeStatus.OK)) {
            throw new Exception(status.getDescription());
        }
        return this;
    }

    public Cube asShuffled() throws Exception {
        asSolved(); //
        shuffle();
        return this;
    }


    /**
     * only require the 4 edge axis sides.  This is enough information to construct the whole cube from
     *
     * @return
     */
    public String getFullAnnotationString() {
        return orangeSide.toString() + "\n"
                + blueSide.toString() + "\n"
                + redSide.toString() + "\n"
                + greenSide.toString() + "\n" +
                //todo remove yellow string from here
                yellowSide.toString() + "\n"
                + whiteSide.toString();
    }

    public Side getWhiteSide() {
        return whiteSide;
    }

    public Side getBlueSide() {
        return blueSide;
    }

    public Side getRedSide() {
        return redSide;
    }

    public Side getOrangeSide() {
        return orangeSide;
    }

    public Side getGreenSide() {
        return greenSide;
    }

    public Side getYellowSide() {
        return yellowSide;
    }

    /**
     * It is assumed the 4 other sides will be ordered correctly with respect to the turning side.
     * We move right, up, down, bottom with respect as if the turning side was facing towards us
     * to the face which is being moved.
     *
     * @param turningSide   the colour of the side which did the turn
     * @param otherSides    the 4 other sides which will be affected by the turn
     * @param clockwise     turn be clockwise or anticlockwise
     * @param numberOfTimes - between 1 and 3.
     */

    //method under development
    public void genericTurn(Side turningSide, Side[] otherSides, boolean clockwise, int numberOfTimes) throws Exception {
        if (numberOfTimes < 0) { // if less than 0 (which is silly then convert to positive and reverse clockwise condition
            numberOfTimes = numberOfTimes * -1;
            clockwise = !clockwise;
        }
        numberOfTimes = numberOfTimes % 4; // just in case a value higher than 4 gets put in
        if (!clockwise) { // convert to clockwise
            numberOfTimes = 4 - numberOfTimes;
            numberOfTimes = numberOfTimes % 4; // need to modulus it again
        }

        // let's do the 4 sides first. i.e 0,3,6 from red go to white side 0 3 6 of white side
        // the 4 other rows/columns being affected by this procedure
        MiniFace[] first = otherSides[0].getColumn(0);
        MiniFace[] second = otherSides[1].getColumn(0);
        MiniFace[] third = otherSides[2].getColumn(0);
        MiniFace[] fourth = otherSides[3].getColumn(0);

        // orientate this face
    }


    // all turn modifies it's own face and 4 others.
    // the 4 others are done differently to this face i.e arrays move from face to face.
    public void rightClockwise(int numberOfTimes) throws Exception {
        // we order our 4 sides red, yellow, orange, white in this case (i.e right, up, down, bottom from blue perspective)
        Side[] otherSides = {redSide, yellowSide, orangeSide, whiteSide};
        genericTurn(blueSide, otherSides, true, numberOfTimes);

    }

    public void rightAntiClockwise(int numberOfTimes) throws Exception {
        Side[] otherSides = {redSide, yellowSide, orangeSide, whiteSide};
        genericTurn(blueSide, otherSides, false, numberOfTimes);
    }

    public void leftClockwise(int numberOfTimes) throws Exception {
        Side[] otherSides = {orangeSide, yellowSide, redSide, whiteSide};
        genericTurn(greenSide, otherSides, true, numberOfTimes);
    }

    public void leftAntiClockwise(int numberOfTimes) throws Exception {
        Side[] otherSides = {orangeSide, yellowSide, redSide, whiteSide};
        genericTurn(greenSide, otherSides, false, numberOfTimes);
    }

    public void frontClockwise(int numberOfTimes) throws Exception {
        Side[] otherSides = {blueSide, yellowSide, greenSide, whiteSide};
        genericTurn(orangeSide, otherSides, true, numberOfTimes);
    }

    public void frontAntiClockwise(int numberOfTimes) throws Exception {
        Side[] otherSides = {blueSide, yellowSide, greenSide, whiteSide};
        genericTurn(orangeSide, otherSides, false, numberOfTimes);
    }

    public void upperClockwise(int numberOfTimes) throws Exception {
        Side[] otherSides = {blueSide, redSide, greenSide, orangeSide};
        genericTurn(yellowSide, otherSides, true, numberOfTimes);
    }

    public void upperAntiClockwise(int numberOfTimes) throws Exception {
        Side[] otherSides = {blueSide, redSide, greenSide, orangeSide};
        genericTurn(yellowSide, otherSides, false, numberOfTimes);
    }

    /**
     * takes in 6 lines which represent 6 sides - reading left-right, top-bottom (
     * This method turned out to be quite compicated
     *
     * @param sixLines
     * @return
     */
    public CubeStatus buildCubeFromString(String sixLines) throws Exception {
        // get rid of whitespace
        // white space is allowed on constructing string but we must remove it here
        sixLines = sixLines.replace(" ", "");
        // first let's do some initial validation on these string/s
        String lines[] = sixLines.split("\n");
        if (lines.length != 6) {
            return CubeStatus.SIDE_ERROR_UNKNOWN;
        }

        // check that there are 9 occurances of each colour in total
        for (Colour colour: Colour.values()) {
            long count = sixLines.chars().filter(ch -> ch == colour.toString().charAt(0)).count();
            if (count != 9) {
                return CubeStatus.COLOUR_DISTRIBUTION_ERROR;
            }
        }

        HashSet<String> uniqueCenterHS = new HashSet<>(); // ensures center squares are unique

        for (String s : lines) {
            if (s.length() != 9) {
                return CubeStatus.SIDE_ERROR_UNKNOWN;
            }
            if (!Pattern.matches("[rgbyow]{9}", s)) {
                return CubeStatus.SIDE_ERROR_UNKNOWN;
            }
            uniqueCenterHS.add(s.substring(4, 5)); // 4,5 is the center clour
        }
        if (uniqueCenterHS.size() != 6) {
            return CubeStatus.SIDE_ERROR_UNKNOWN;
        }

        // loop again and this time add face colours
        for (String s : lines) {
            switch (s.substring(4, 5)) {
                case "o": {
                    orangeSide.setMiniColourFaces(s);
                    break;
                }
                case "b": {
                    blueSide.setMiniColourFaces(s);
                    break;
                }
                case "r": {
                    redSide.setMiniColourFaces(s);
                    break;
                }
                case "g": {
                    greenSide.setMiniColourFaces(s);
                    break;
                }
                case "y": {
                    yellowSide.setMiniColourFaces(s);
                    break;
                }
                case "w": {
                    whiteSide.setMiniColourFaces(s);
                    break;
                }
            }
        }

        // now we need to calculate the miniface other axis colours with the info we have
        Side[] sides = {orangeSide, blueSide, redSide, greenSide}; // first just iterate the 4 x axis sides
        for (int index = 0; index < 4; index++) {

            StringBuilder condition = new StringBuilder();

            // do tops and bottoms first
            for (int i = 0; i < 9; i++) {

                switch (i) {
                    case 0: {
                        MiniFace miniFaceTop = yellowSide.getMiniFace(0, 0);
                        MiniFace miniFaceY = redSide.getMiniFace(0, 2);
                        MiniFace miniFaceX = greenSide.getMiniFace(0, 0);
                        miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                                + miniFaceX.getFaceColour()
                                + miniFaceY.getFaceColour());

                        MiniFace miniFaceBottom = whiteSide.getMiniFace(0, 0);
                        miniFaceY = orangeSide.getMiniFace(2, 0);
                        miniFaceX = greenSide.getMiniFace(2, 2);
                        miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                                + miniFaceX.getFaceColour()
                                + miniFaceY.getFaceColour());

                        break;
                    }
                    case 1: {
                        MiniFace miniFaceY = redSide.getMiniFace(0, 1);
                        MiniFace miniFaceTop = yellowSide.getMiniFace(0, 1);
                        miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                                + miniFaceY.getFaceColour());

                        miniFaceY = orangeSide.getMiniFace(2, 1);
                        MiniFace miniFaceBottom = whiteSide.getMiniFace(0, 1);
                        miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                                + miniFaceY.getFaceColour());

                        break;
                    }
                    case 2: {
                        MiniFace miniFaceTop = yellowSide.getMiniFace(0, 2);
                        MiniFace miniFaceX = blueSide.getMiniFace(0, 2);
                        MiniFace miniFaceY = redSide.getMiniFace(0, 0);
                        miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                                + miniFaceX.getFaceColour() + miniFaceY.getFaceColour());

                        MiniFace miniFaceBottom = whiteSide.getMiniFace(0, 2);
                        miniFaceY = orangeSide.getMiniFace(2, 2);
                        miniFaceX = blueSide.getMiniFace(2, 0);
                        miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                                + miniFaceX.getFaceColour()
                                + miniFaceY.getFaceColour());

                        break;
                    }
                    case 3: {
                        MiniFace miniFaceX = greenSide.getMiniFace(0, 1);
                        MiniFace miniFaceTop = yellowSide.getMiniFace(1, 0);
                        miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                                + miniFaceX.getFaceColour().toString());

                        miniFaceX = greenSide.getMiniFace(2, 1);
                        MiniFace miniFaceBottom = whiteSide.getMiniFace(1, 0);
                        miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                                + miniFaceX.getFaceColour());

                        break;
                    } // note - there is no case 4 - that's the centerpiece and does not change
                    case 5: {
                        MiniFace miniFaceTop = yellowSide.getMiniFace(1, 2);
                        MiniFace miniFaceX = blueSide.getMiniFace(0, 1);
                        miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                                + miniFaceX.getFaceColour().toString());

                        miniFaceX = blueSide.getMiniFace(2, 1);
                        MiniFace miniFaceBottom = whiteSide.getMiniFace(1, 2);
                        miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                                + miniFaceX.getFaceColour());

                        break;
                    }
                    case 6: {
                        MiniFace miniFaceX = greenSide.getMiniFace(0, 2);
                        MiniFace miniFaceY = orangeSide.getMiniFace(0, 0);
                        MiniFace miniFaceTop = yellowSide.getMiniFace(2, 0);
                        miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                                + miniFaceX.getFaceColour() + miniFaceY.getFaceColour().toString());

                        MiniFace miniFaceBottom = whiteSide.getMiniFace(2, 0);
                        miniFaceY = redSide.getMiniFace(2, 2);
                        miniFaceX = greenSide.getMiniFace(2, 0);
                        miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                                + miniFaceX.getFaceColour()
                                + miniFaceY.getFaceColour());

                        break;
                    }

                    case 7: {
                        MiniFace miniFaceY = orangeSide.getMiniFace(0, 1);
                        MiniFace miniFaceTop = yellowSide.getMiniFace(2, 1);
                        miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                                + miniFaceY.getFaceColour());

                        miniFaceY = redSide.getMiniFace(2, 1);
                        MiniFace miniFaceBottom = whiteSide.getMiniFace(2, 1);
                        miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                                + miniFaceY.getFaceColour());
                        break;
                    }

                    case 8: {
                        MiniFace miniFaceX = blueSide.getMiniFace(0, 0);
                        MiniFace miniFaceY = orangeSide.getMiniFace(0, 2);
                        MiniFace miniFaceTop = yellowSide.getMiniFace(2, 2);
                        miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                                + miniFaceX.getFaceColour()
                                + miniFaceY.getFaceColour().toString());

                        MiniFace miniFaceBottom = whiteSide.getMiniFace(2, 2);
                        miniFaceX = blueSide.getMiniFace(2, 2);
                        miniFaceY = redSide.getMiniFace(2, 0);
                        miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                                + miniFaceX.getFaceColour()
                                + miniFaceY.getFaceColour());

                        break;
                    }

                }
            }

            for (int i = 0; i < 9; i++) {
                // create copy of top and bottom sides which we are going to use to rotate
                Side topSide = cubeUtils.copySide(yellowSide);
                Side bottomSide = cubeUtils.copySide(whiteSide);

                int rotation = index;
                int bottomrotation = (4-index);

                switch (i) {
                    case 0: {
                        MiniFace miniFace = sides[index].getMiniFace(0, 0);
                        topSide.rotateFace((rotation));
                        MiniFace miniFaceTop = topSide.getMiniFace(2, 0);

                        MiniFace toTheLeft = sides[(index+3) % 4].getMiniFace(0, 2);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + toTheLeft.getFaceColour()
                                + miniFaceTop.getFaceColour().toString());
                        break;

                    }
                    case 1: {
                        topSide.rotateFace((rotation));
                        MiniFace miniFace = sides[index].getMiniFace(0, 1);
                        MiniFace miniFaceTop = topSide.getMiniFace(2, 1);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + miniFaceTop.getFaceColour().toString());

                        break;
                    }
                    case 2: {

                        MiniFace miniFace = sides[index].getMiniFace(0, 2);

                        topSide.rotateFace((rotation));
                        MiniFace miniFaceTop = topSide.getMiniFace(2, 2);

                        MiniFace toTheRight = sides[(index+1) % 4].getMiniFace(0, 0);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + toTheRight.getFaceColour()
                                + miniFaceTop.getFaceColour().toString());

                        break;
                    }
                    case 3: {
                        MiniFace miniFace = sides[index].getMiniFace(1, 0);
                        MiniFace miniFaceLeft = sides[(index+3) % 4].getMiniFace(1, 2);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + miniFaceLeft.getFaceColour() + toString());
                        break;
                    }

                    case 5: {
                        MiniFace miniFace = sides[index].getMiniFace(1, 2);

                        MiniFace toTheRight = sides[(index+1) % 4].getMiniFace(1, 0);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + toTheRight.getFaceColour());

                        break;
                    }
                    case 6: {

                        MiniFace miniFace = sides[index].getMiniFace(2, 0);
                        MiniFace miniFaceLeft = sides[(index + 3) % 4].getMiniFace(2, 2);
                        bottomSide.rotateFace(bottomrotation);
                        MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 0);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + miniFaceLeft.getFaceColour()
                                + miniFaceBottom.getFaceColour().toString());

                        break;
                    }
                    case 7: {
                        MiniFace miniFace = sides[index].getMiniFace(2, 1);
                        bottomSide.rotateFace(bottomrotation);
                        MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 1);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + miniFaceBottom.getFaceColour().toString());

                        break;
                    }
                    case 8: {
                        MiniFace miniFace = sides[index].getMiniFace(2, 2);
                        MiniFace miniFaceRight = sides[(index + 1) % 4].getMiniFace(2, 0);
                        bottomSide.rotateFace(bottomrotation);
                        MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 2);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + miniFaceRight.getFaceColour()
                                + miniFaceBottom.getFaceColour().toString());
                        break;
                    }

                }

            }
        }

        return cubeUtils.validateCube(this);
    }





    /**
     * gets a string that a gui could easily deal with to build a cube
     *
     * @return
     */
    public String getDisplayAnnotation() {
        StringBuilder returnSB = new StringBuilder();
        returnSB.append(getOrangeSide().getAllColoursForSide());
        returnSB.append(getBlueSide().getAllColoursForSide());
        returnSB.append(getYellowSide().getAllColoursForSide());
        returnSB.append(getGreenSide().getAllColoursForSide());
        returnSB.append(getRedSide().getAllColoursForSide());
        returnSB.append(getWhiteSide().getAllColoursForSide());
        return returnSB.toString().trim();
    }

    /**
     * gets a string that displays the contents of all the whole cube
     *
     * @return
     */
    public void getDisplaySidesForDebug() {
        StringBuilder returnSB = new StringBuilder();
        returnSB.append("Orange Side\n==========\n");
        returnSB.append(getOrangeSide().getAllColoursForSide());
        returnSB.append("\nBlue Side\n==========\n");
        returnSB.append(getBlueSide().getAllColoursForSide());
        returnSB.append("\nYellow Side\n==========\n");
        returnSB.append(getYellowSide().getAllColoursForSide());
        returnSB.append("\nGreen Side\n==========\n");
        returnSB.append(getGreenSide().getAllColoursForSide());
        returnSB.append("\nRed Side\n==========\n");
        returnSB.append(getRedSide().getAllColoursForSide());
        returnSB.append("\nWhite Side\n==========\n");
        //todo turn whiteside printing back on
        returnSB.append(getWhiteSide().getAllColoursForSide());
        System.out.println(returnSB.toString());
    }

    /**
     * follows a predifined set of instructions
     * returns false if it fails
     *
     * @param algorithm a space seperated list of instructions - each must have 2 letters, i.e. fc (front clockwise)
     */
    public boolean followAlgorithmAttempt(String algorithm) throws Exception {

        String[] instructions = algorithm.split(" ");

        // validate instructions before we start - i.e. if one fails, don't do any turns
        for (String instruction: instructions) {
            if (!Pattern.matches("([0-9]?rc)|" +
                    "([0-9]?ra)|" +
                    "([0-9]?lc)|" +
                    "([0-9]?la)|" +
                    "([0-9]?ua)|" +
                    "([0-9]?uc)|" +
                    "([0-9]?fc)|" +
                    "([0-9]?fa)|", instruction)) {
                return false;
            }
        }

        for (String instruction : instructions) {
            int numberOfTurns = 1; // default of one turn if not specified
            if (instruction.length() == 3) { // means there is a number of turns
                numberOfTurns = Integer.parseInt(instruction.substring(0,1));
                instruction = instruction.substring(1,3);
            }
            if (instruction.length() != 2 && instruction.length() != 3) {
                return false;
            }

            switch (instruction) {
                case "rc": {
                    rightClockwise(numberOfTurns);
                    break;
                }
                case "ra": {
                    rightAntiClockwise(numberOfTurns);
                    break;
                }
                case "fc": {
                    frontClockwise(numberOfTurns);
                    break;
                }
                case "fa": {
                    frontAntiClockwise(numberOfTurns);
                    break;
                }
                case "uc": {
                    upperClockwise(numberOfTurns);
                    break;
                }
                case "ua": {
                    upperAntiClockwise(numberOfTurns);
                    break;
                }
                case "lc": {
                    leftClockwise(numberOfTurns);
                    break;
                }
                case "la": {
                    leftAntiClockwise(numberOfTurns);
                    break;
                }
                default: {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * returns a randomised cube
     */
    public void shuffle() throws Exception {
        // define the possible number of moves
        int max = 25;
        int min = 15;
        int range = max - min + 1;
        int numTwists = (int) (Math.random() * range) + min;
        // generate random numbers within 1 to 10
        for (int i = 0; i < numTwists; i++) {
            // select random from 1-4 (for our 4 twist moves)
            int rand = (int) (Math.random() * 4) + 1;

            // pointless to turn more than 3 times - so no point in doing anticlockwise moves here
            int numbnerofTimes = (int) (Math.random() * 3) + 1;
            switch (rand) {
                case 1: {
                    upperClockwise(numbnerofTimes);
                    break;
                }
                case 2: {
                    rightClockwise(numbnerofTimes);
                    break;
                }
                case 3: {
                    leftClockwise(numbnerofTimes);
                    break;
                }
                case 4: {
                    frontClockwise(numbnerofTimes);
                    break;
                }
            }

        }
    }
}
