package rubiks;

/**
 * represents the cube as a whole.
 * Should have public methods for turns
 * NOTE that the cube is always set up as solve as default.
 * Randomising the turns will mess the cube up
 * Future instructions for passing a specific state of the cube will be done
 */
class Cube {

    // create our six sides
    private final Side whiteSide = new Side().withColour(Colour.w);
    private final Side yellowSide = new Side().withColour(Colour.y);
    private final Side blueSide = new Side().withColour(Colour.b);
    private final Side redSide = new Side().withColour(Colour.r);
    private final Side orangeSide = new Side().withColour(Colour.o);
    private final Side greenSide = new Side().withColour(Colour.g);

    /**
     * recommended to run either this method
     * ...or asShuffle
     * ... or asDefined when constructing the cube object - as not much point in having an orphan cube with no sides or faces etc.
     * @return
     * @throws Exception
     */
    public Cube asSolved() throws Exception {
        buildAsSolved();
        return this;
    }

    public Cube asShuffled() throws Exception {
        buildAsSolved();
        this.shuffle();
        return this;
    }

    public Cube asDefined(String notation) throws Exception {
        buildSidesFromString(notation);
        return this;
    }

    /**
     * only require the 4 edge axis sides.  This is enough information to construct the whole cube from
     * @return
     */
    public String toString() {
        return orangeSide.toString() + "\n"
                + blueSide.toString() + "\n"
                + redSide.toString() + "\n"
                + greenSide.toString();
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
     *  It is assumed the 4 other sides will be ordered correctly with respect to the turning side.
     *  We move right, up, down, bottom with respect as if the turning side was facing towards us
     *  to the face which is being moved.
     * @param turningSide the colour of the side which did the turn
     * @param otherSides the 4 other sides which will be affected by the turn
     * @param clockwise turn be clockwise or anticlockwise
     * @param numberOfTimes - between 1 and 3.
     */

    //method under development
    private void genericTurn(Side turningSide, Side[] otherSides, boolean clockwise, int numberOfTimes) throws Exception {
        if (numberOfTimes<0) {
            throw new Exception("Error - we don't do negative numbers for number of turns");
        }
        numberOfTimes = numberOfTimes % 4; // silly to put more than 3 turns in so modular it just in case

        // right clockwise
        // let's do the 4 sides first. i.e 0,3,6 from red go to white side 0 3 6 of white side
        //gow  gw bow  go to gow gw bow - no change

        for (Side side:otherSides) {

        }

        // we should back up this face - as it is going to be messed around a bit from other faces - and we need to rotate it
        String[] backupOfThisFace = new String[9];
        for (int i = 0; i< 9; i++) {

        }

        for (int i = 0; i<4;i++) {
           TurnTransposeOrder whatToDo = TurnTransposeOrder.values()[i];
           // under dev
           String[] temp = new String[3];
           //otherSides[i].receiveRoworColumn(temp, turningSide, numberOfTimes,clockwise, whatToDo);

           // now we modify the turning Side
        }

    }

    // all turn modifies it's own face and 4 others.
    // the 4 others are done differently to this face i.e arrays move from face to face.
    public void rightClockwise(int numberOfTimes) throws Exception {
        // we order our 4 sides red, yellow, orange, white in this case (i.e right, up, down, bottom from blue perspective)
        Side[] otherSides = {redSide, yellowSide, orangeSide, whiteSide};
        genericTurn(blueSide, otherSides, true, numberOfTimes);

    }

    public void rightAntiClockwise(int numberOfTimes)   throws Exception  {
        Side[] otherSides = {redSide, yellowSide, orangeSide, whiteSide};
        genericTurn(blueSide, otherSides, false, numberOfTimes);
    }

    public void leftClockwise(int numberOfTimes)  throws Exception {
        Side[] otherSides = {orangeSide, yellowSide, redSide, whiteSide};
        genericTurn(greenSide, otherSides, true, numberOfTimes);
    }

    public void leftAntiClockwise(int numberOfTimes)  throws Exception {
        Side[] otherSides = {orangeSide, yellowSide, redSide, whiteSide};
        genericTurn(greenSide, otherSides, false, numberOfTimes);
    }

    public void frontClockwise(int numberOfTimes)  throws Exception {
        Side[] otherSides = {blueSide, yellowSide, greenSide, whiteSide};
        genericTurn(orangeSide, otherSides, true, numberOfTimes);
    }

    public void frontAntiClockwise(int numberOfTimes)  throws Exception {
        Side[] otherSides = {blueSide, yellowSide, greenSide, whiteSide};
        genericTurn(orangeSide, otherSides, false, numberOfTimes);
    }

    public void upperClockwise(int numberOfTimes)   throws Exception {
        Side[] otherSides = {blueSide, redSide, greenSide, orangeSide};
        genericTurn(yellowSide, otherSides, true, numberOfTimes);
    }

    public void upperAntiClockwise(int numberOfTimes)   throws Exception {
        Side[] otherSides = {blueSide, redSide, greenSide, orangeSide};
        genericTurn(yellowSide, otherSides, false, numberOfTimes);
    }

    /**
     * /**
     * * Build all 6 sides (just as strings using a fixed instructions as follows.
     * * As we look at the cube
     * * orange is front
     * * blue is right
     * * red is back
     * * green is left
     * * yellow is top
     * * white is bottom
     * *
     * <p>
     * * Our notation works line by line for each where the correspondsing and unique square represents the 1 2 or 3 letters
     * * the first letter of each square represents the outward facing colour for that position. The C is the center square and is fixed colour
     * * flt ft frt fl C fr flb fb frb
     * * flt ft frt fl C fr flb fb frb
     * * flt ft frt fl C fr flb fb frb
     * * flt ft frt fl C fr flb fb frb
     *
     * @param longNotation
     */
    public void buildSidesFromString(String longNotation) throws Exception {
        CubeUtils cubeUtils = new CubeUtils();

        // the longNotation only has sides - we calculate top and bottom sides from this info
        String[] sideStrings = cubeUtils.addTopAndBottoms(longNotation);
        // more valididation that our cube is in a good state.  Can't have too much validation when building cube

        for (String sideString : sideStrings) {
            // extract colour from this string at positionn 14 - this is the center point on the side and never changes
            String correctColour = sideString.substring(14, 15);

            switch (correctColour) {
                case "o": {
                    orangeSide.setSquaresandColours(sideString);
                    break;
                }
                case "r": {
                    redSide.setSquaresandColours(sideString);
                    break;
                }
                case "w": {
                    whiteSide.setSquaresandColours(sideString);
                    break;
                }
                case "b": {
                    blueSide.setSquaresandColours(sideString);
                    break;
                }
                case "g": {
                    greenSide.setSquaresandColours(sideString);
                    break;
                }
                case "y": {
                    yellowSide.setSquaresandColours(sideString);
                    break;
                }
            }
        }
    }

    /**
     * This is actually a good example of how to build the sides using the instructions above (from buildSidesFromString() method) :-)
     */
    public void buildAsSolved() throws Exception {
        Side[] sidesToReturn = new Side[6];
        // note that the top and bottom sides can be calculated from the information we have here
        String notation = "ogy oy oby og o ob ogw ow obw\n" + // orange side (front)
                "boy by bry bo b br bow bw brw\n" + // right
                "rby ry rgy rb r rg rbw rw rgw\n" +  // back
                "gry gy goy gr g go grw gw gow\n"; // left
        buildSidesFromString(notation);
    }


    /**
     * follows a predifined set of instructions
     *
     * @param algorithm a space seperated list of instructions - each must have 2 letters, i.e. fc (front clockwise)
     */
    public void followAlgorithm(String algorithm) throws Exception {
        String[] instructions = algorithm.split(" ");
        for (String instruction : instructions) {

            if (instruction.length() != 2) {
                throw new Exception("algorith does not support " + instruction);
            }

            switch (instruction) {
                case "rc": {
                    rightClockwise(1);
                    break;
                }
                case "ra": {
                    rightAntiClockwise(1);
                    break;
                }
                case "fc": {
                    frontClockwise(1);
                    break;
                }
                case "fa": {
                    frontAntiClockwise(1);
                    break;
                }
                case "uc": {
                    upperClockwise(1);
                    break;
                }
                case "ua": {
                    upperAntiClockwise(1);
                    break;
                }
                case "lc": {
                    leftClockwise(1);
                    break;
                }
                case "la": {
                    leftAntiClockwise(1);
                    break;
                }
                default: {
                    throw new Exception("algorith does not support " + instruction);
                }
            }
        }
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
