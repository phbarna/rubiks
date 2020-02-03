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
public class Cube {
    private final CubeUtils cubeUtils = new CubeUtils();
    // create our six sides
    private Side whiteSide;
    private Side yellowSide;
    private Side blueSide;
    private Side redSide;
    private Side orangeSide;
    private Side greenSide;

    public Cube() {
        whiteSide = new Side().withColour(Colour.w);
        yellowSide = new Side().withColour(Colour.y);
        blueSide = new Side().withColour(Colour.b);
        redSide = new Side().withColour(Colour.r);
        orangeSide = new Side().withColour(Colour.o);
        greenSide = new Side().withColour(Colour.g);
    }

    /**
     * ...or asShuffle
     * ...  the cube object - as not much point in having an orphan cube with no sides or faces etc.
     *
     * @return
     * @throws Exception throws exception
     */
    public Cube asSolved() throws Exception {

        String notation = "rrrrrrrrr\n" +
                "ggggggggg\n" +
                "yyyyyyyyy\n" +
                "ooooooooo\n" +
                "bbbbbbbbb\n" +
                "wwwwwwwww";
        CubeStatus status = buildCubeFromString(notation);
        // really would not expect this to fail ... but if it does
        if (!status.equals(CubeStatus.OK)) {
            throw new Exception(status.getDescription());
        }
        return this;
    }

    Cube asShuffled() throws Exception {
        asSolved(); // so that we know it is shuffled from a 'valid state'
        shuffle();
        return this;
    }

    /**
     * could be useful for debug to see more detailed state of the cube
     * @return a string
     */
    String getFullAnnotationString() {
        return orangeSide.toString() + "\n"
                + blueSide.toString() + "\n"
                + redSide.toString() + "\n"
                + greenSide.toString() + "\n"
                + yellowSide.toString() + "\n"
                + whiteSide.toString();
    }

    Side getWhiteSide() {
        return whiteSide;
    }

    Side getBlueSide() {
        return blueSide;
    }

    Side getRedSide() {
        return redSide;
    }

    Side getOrangeSide() {
        return orangeSide;
    }

    Side getGreenSide() {
        return greenSide;
    }

    Side getYellowSide() {
        return yellowSide;
    }

    /**
     * a method to enhance ease of writing the gui.  The gui sends any of the 24 orientations
     * as you look as the cube and this method just sends 3 strings so that the gui can render without
     * thinking
     * @param orientation
     * @return
     * @throws Exception
     */
    public String getOrientationStrings(String orientation) throws Exception {
        orientation= orientation.toUpperCase();
        Orientation guiOrientation = Orientation.valueOf(orientation);
        Side tempFrontSide;
        Side tempLeftSide;
        Side tempTopSide;

        switch (guiOrientation) {
            case OY: {
                tempFrontSide = cubeUtils.copySide(this.getOrangeSide());
                tempLeftSide = cubeUtils.copySide(this.getGreenSide());
                tempTopSide = cubeUtils.copySide(this.getYellowSide());
                break;
            }
            case GY: {
                tempFrontSide = cubeUtils.copySide(this.getGreenSide());
                tempLeftSide = cubeUtils.copySide(this.getRedSide());
                tempTopSide = cubeUtils.copySide(this.getYellowSide());
                tempTopSide.rotateSide(3);
                break;
            }

            default: {
                tempFrontSide = this.getOrangeSide();
                tempLeftSide = this.getGreenSide();
                tempTopSide = this.getYellowSide();
                break;
            }
        }

        return tempFrontSide.toString() + "\n" + tempLeftSide.toString() + "\n" + tempTopSide.toString();



        // big case statement coming up
    }

    // the 4 others are done differently to this face i.e arrays move from face to face.
    private void rightClockwise(int numberOfTimes) throws Exception {
        // we order our 4 sides red, yellow, orange, white in this case (i.e right, up, down, bottom from blue perspective)
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.rightTurn(this, true, numberOfTimes);
    }

    private void rightAntiClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.rightTurn(this, false, numberOfTimes);
    }

    private void leftClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.leftTurn(this, true, numberOfTimes);
    }

    private void leftAntiClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.leftTurn(this, false, numberOfTimes);
    }

    private void frontClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.frontTurn(this, true, numberOfTimes);
    }

    private void frontAntiClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.frontTurn(this, false, numberOfTimes);
    }

    private void upperClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.upperTurn(this, true, numberOfTimes);
    }

    private void upperAntiClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.upperTurn(this, false, numberOfTimes);
    }

    private void downFaceClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.downFaceTurn(this, true, numberOfTimes);
    }

    private void downFaceAntiClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.downFaceTurn(this, false, numberOfTimes);
    }

    private void backClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.backTurnTurn(this, true, numberOfTimes);
    }

    private void backAntiClockwise(int numberOfTimes) throws Exception {
        TurnHelper turnHelper = new TurnHelper();
        turnHelper.backTurnTurn(this, false, numberOfTimes);
    }

    /**
     * takes in 6 lines which represent 6 sides - reading left-right, top-bottom (
     * This method turned out to be quite complicated
     *
     * @param sixLines the 6 lines of the cube
     * @return a cube status indicating any problems with cube build
     */
    public CubeStatus buildCubeFromString(String sixLines) throws Exception {
        // get rid of whitespace
        // white space is allowed on constructing string but we must remove it here
        sixLines = sixLines.replace(" ", "").toLowerCase();
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
            uniqueCenterHS.add(s.substring(4, 5)); // 4,5 is the center face
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
        Side[] sides = {orangeSide, blueSide, redSide, greenSide}; // first just iterate the 4 x-axis sides
        for (int index = 0; index < 4; index++) {

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


                int bottomRotation = (4-index);

                switch (i) {
                    case 0: {
                        MiniFace miniFace = sides[index].getMiniFace(0, 0);
                        topSide.rotateSide((index));
                        MiniFace miniFaceTop = topSide.getMiniFace(2, 0);

                        MiniFace toTheLeft = sides[(index+3) % 4].getMiniFace(0, 2);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + toTheLeft.getFaceColour()
                                + miniFaceTop.getFaceColour().toString());
                        break;

                    }
                    case 1: {
                        topSide.rotateSide(index);
                        MiniFace miniFace = sides[index].getMiniFace(0, 1);
                        MiniFace miniFaceTop = topSide.getMiniFace(2, 1);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + miniFaceTop.getFaceColour().toString());

                        break;
                    }
                    case 2: {

                        MiniFace miniFace = sides[index].getMiniFace(0, 2);

                        topSide.rotateSide((index));
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
                        bottomSide.rotateSide(bottomRotation);
                        MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 0);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + miniFaceLeft.getFaceColour()
                                + miniFaceBottom.getFaceColour().toString());

                        break;
                    }
                    case 7: {
                        MiniFace miniFace = sides[index].getMiniFace(2, 1);
                        bottomSide.rotateSide(bottomRotation);
                        MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 1);
                        miniFace.setColours(miniFace.getFaceColour().toString()
                                + miniFaceBottom.getFaceColour().toString());

                        break;
                    }
                    case 8: {
                        MiniFace miniFace = sides[index].getMiniFace(2, 2);
                        MiniFace miniFaceRight = sides[(index + 1) % 4].getMiniFace(2, 0);
                        bottomSide.rotateSide(bottomRotation);
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
        returnSB.append(getOrangeSide().getAllColoursForSide(false));
        returnSB.append(getBlueSide().getAllColoursForSide(false));
        returnSB.append(getYellowSide().getAllColoursForSide(false));
        returnSB.append(getGreenSide().getAllColoursForSide(false));
        returnSB.append(getRedSide().getAllColoursForSide(false));
        returnSB.append(getWhiteSide().getAllColoursForSide(false));
        return returnSB.toString().trim();
    }

    /**
     * gets a string that displays the contents of all the whole cube
     *
     * @return
     */
    void getDisplaySidesForDebug() {
        StringBuilder returnSB = new StringBuilder();
        returnSB.append("Orange Side\n==========\n");
        returnSB.append(getOrangeSide().getAllColoursForSide(true));
        returnSB.append("\nBlue Side\n==========\n");
        returnSB.append(getBlueSide().getAllColoursForSide(true));
        returnSB.append("\nYellow Side\n==========\n");
        returnSB.append(getYellowSide().getAllColoursForSide(true));
        returnSB.append("\nGreen Side\n==========\n");
        returnSB.append(getGreenSide().getAllColoursForSide(true));
        returnSB.append("\nRed Side\n==========\n");
        returnSB.append(getRedSide().getAllColoursForSide(true));
        returnSB.append("\nWhite Side\n==========\n");
        returnSB.append(getWhiteSide().getAllColoursForSide(true));
        System.out.println(returnSB.toString());
    }

    /**
     *
     * @param algorithm
     * @param stopOnSolved
     * @return -1 (validationb error), 0 (no solve), or any positive integer representing the number of actions for a solve
     * @throws Exception
     */
    public int followAlgorithm(String algorithm, boolean stopOnSolved) throws Exception {

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
                    "([0-9]?dc)|" +
                    "([0-9]?da)|" +
                    "([0-9]?bc)|" +
                    "([0-9]?ba)|" +
                    "([0-9]?fa)|", instruction)) {
                return -1;
            }
        }
        int instructionNumber = 1; // keeps track of number of instructions so that we know how long a solve takes
        for (String instruction : instructions) {
            int numberOfTurns = 1; // default of one turn if not specified
            if (instruction.length() == 3) { // means there is a number of turns
                numberOfTurns = Integer.parseInt(instruction.substring(0,1));
                instruction = instruction.substring(1,3);
            }
            if (instruction.length() != 2 && instruction.length() != 3) {
                return -1;
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
                case "dc": {
                    downFaceClockwise(numberOfTurns);
                    break;
                }
                case "da": {
                    downFaceAntiClockwise(numberOfTurns);
                    break;
                }
                case "bc": {
                    backClockwise(numberOfTurns);
                    break;
                }
                case "ba": {
                    backAntiClockwise(numberOfTurns);
                    break;
                }
                default: {
                    return -1;
                }
            }
            if (stopOnSolved && cubeUtils.checkSolvedState(this)) {
                return instructionNumber;
            }
            instructionNumber++;
        }
        return 0; // if it gets here it has not found a solve so returns just returns zero
    }

    /**
     * returns a randomised cube
     */
    public void shuffle() throws Exception {
        // max and min are arbitary values but I think 20-35 turns is suitable for a good cube shuffle :-)
        int max = 40;
        int min = 20;
        int range = max - min + 1;
        int numTwists = (int) (Math.random() * range) + min;
        // generate random numbers within 1 to 10
        for (int i = 0; i < numTwists; i++) {
            // select random from 1-4 (for our 4 twist moves)
            int rand = (int) (Math.random() * 6) + 1;

            // pointless to turn more than 3 times - so no point in doing anticlockwise moves
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
                case 5: {
                    downFaceClockwise(numbnerofTimes);
                    break;
                }
                case 6: {
                    backClockwise(numbnerofTimes);
                    break;
                }
            }
        }
    }
}
