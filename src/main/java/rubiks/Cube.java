package rubiks;

import java.util.HashMap;
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
        CubeStatus status = buildAsSolved();
        if (!status.equals(CubeStatus.OK)) {
            throw new Exception(status.getDescription());
        }
        return this;
    }

    public Cube asShuffled() throws Exception {
        CubeStatus status = buildAsSolved();
        if (!status.equals(CubeStatus.OK)) {
            throw new Exception(status.getDescription());
        }
        this.shuffle();
        return this;
    }

    public Cube asDefined(String notation) throws Exception {
        CubeStatus status = buildSidesFromString(notation);
        if (!status.equals(CubeStatus.OK))
        {
            throw new Exception(status.getDescription());
        }
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
                + greenSide.toString()+ "\n";
        //todo remove yellow string from here
                //+ yellowSide.toString();
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

    public Side getOrangeSide() { return orangeSide; }

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
    private void genericTurn(Side turningSide, Side[] otherSides, boolean clockwise, int numberOfTimes)  throws Exception {
        if (numberOfTimes<0) { // if less than 0 (which is silly then convert to positive and reverse clockwise condition
            numberOfTimes= numberOfTimes* -1;
            clockwise = !clockwise;
        }
        numberOfTimes = numberOfTimes % 4; // just in case a value higher than 4 gets put in
        if (!clockwise) { // convert to clockwise
            numberOfTimes = 4 - numberOfTimes;
            numberOfTimes = numberOfTimes % 4; // need to modulus it again
        }





        // right clockwise
        // let's do the 4 sides first. i.e 0,3,6 from red go to white side 0 3 6 of white side
        //gow  gw bow  go to gow gw bow - no change

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
     * takes in 5 lines which represent 5 sides - reading left-right, top-bottom (
     * @param fiveLines
     * @return
     */
    public CubeStatus buildSidesFromString2(String fiveLines) throws Exception {
        //todo under developme
        String lines[] = fiveLines.split("\n");
        if (lines.length != 5) {
            return CubeStatus.SIDE_ERROR_UNKNOWN;
        }

        HashSet<String> uniqueCenterHS = new HashSet<>(); // ensures center squares are correct


        for (String s: lines) {
            // white space is allowed on constructing string but we must remove it here
            s = s.replace(" ","");
            if (s.length() != 9) {
                return CubeStatus.SIDE_ERROR_UNKNOWN;
            }
            if (!Pattern.matches("[rgbyow]{4}[rgbyo]{1}[rgbyow]{4}", s)) {
                return CubeStatus.SIDE_ERROR_UNKNOWN;
            }
            uniqueCenterHS.add(s.substring(4,5));
        }
        if (uniqueCenterHS.size() != 5) {
            return CubeStatus.SIDE_ERROR_UNKNOWN;
        }

        // loop again and this time add
        for (String s: lines) {
            switch (s.substring(4,5)) {
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
            }
        }

        // now we need to calculate the miniface other axis colours with the info we have
        Side[] sides = { orangeSide, blueSide, redSide, greenSide}; // first just iterate the 4 x axis sides
        for (int index = 0; index< 4; index++) {

            // going to have to do this the hard way as the conditions for which face I'm on are all different compared to neighbours and tops
            MiniFace topLeft = sides[index].getMiniFace(0,0);
            MiniFace topMiddle = sides[index].getMiniFace(0,1);
            MiniFace topRight = sides[index].getMiniFace(0,2);
            MiniFace middleLeft = sides[index].getMiniFace(1,0);
            MiniFace middleRight = sides[index].getMiniFace(1,2);
            MiniFace bottomLeft = sides[index].getMiniFace(2,0);
            MiniFace bottomMiddle = sides[index].getMiniFace(2,1);
            MiniFace bottomRight = sides[index].getMiniFace(2,2);

            topLeft.setColours(topLeft.getFaceColour()
                    + sides[(index+3)%4].getMiniFace(0,2).getFaceColour().toString()
                    + yellowSide.getMiniFace(2,1));

            topMiddle.setColours(topMiddle.getFaceColour() + yellowSide.getMiniFace(2,1).getFaceColour().toString());

            topRight.setColours(topRight.getFaceColour()
                    + sides[(index+1)%4].getMiniFace(0,0).getFaceColour().toString()
                    + yellowSide.getMiniFace(2,2));




            middleLeft.setColours(middleLeft.getFaceColour() +
                    sides[(index+3)%4].getMiniFace(1,2).getFaceColour().toString());

            middleRight.setColours(topLeft.getFaceColour()
                    + sides[(index+1)%4].getMiniFace(0,2).getFaceColour().toString());

            bottomLeft.setColours(topMiddle.getFaceColour() + sides[(index+3)%4].getMiniFace(2,2).getFaceColour().toString() + "w");

            bottomRight.setColours(bottomRight.getFaceColour()
                    + sides[(index+1)%4].getMiniFace(0,2).getFaceColour().toString()+"w");


        }


        // now fill in top

        System.out.println(this.toString());


        // last stage - populate the whiteside sides and we're done !
        CubeUtils cubeUtils = new CubeUtils();
     //  CubeStatus status = cubeUtils.validateCube(this);
        return CubeStatus.OK;
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
    public CubeStatus buildSidesFromString(String longNotation) throws Exception {
        CubeUtils cubeUtils = new CubeUtils();

        // the longNotation only has sides - we calculate top and bottom sides from this info
        CubeStatus status = cubeUtils.validateSides(longNotation);

        if (!status.equals(CubeStatus.OK)) {
            return status;
        }

        if (!cubeUtils.validateUniquePieces(longNotation)) {
            return CubeStatus.PIECES_NOT_UNIQUE_ERROR;
        }
        status = cubeUtils.validateSides(longNotation);
        if (!cubeUtils.validateSides(longNotation).equals(CubeStatus.OK)) {
            return status;
        }

        String[] sideStrings = cubeUtils.addTopAndBottoms(longNotation);
        // more valididation that our cube is in a good state.  Can't have too much validation when building cube

        for (String sideString : sideStrings) {
            // extract colour from this string at positionn 14 - this is the center point on the side and never changes
            String correctColour = sideString.substring(14, 15);

            switch (correctColour) {
                case "o": {
                    if (!orangeSide.validateSide(sideString))
                        return CubeStatus.SIDE_ERROR_UNKNOWN;
                    orangeSide.setSquaresandColours(sideString);
                    break;
                }
                case "r": {
                    if (!redSide.validateSide(sideString))
                        return CubeStatus.SIDE_ERROR_UNKNOWN;
                    redSide.setSquaresandColours(sideString);
                    break;
                }
                case "w": {
                    whiteSide.setSquaresandColours(sideString);
                    break;
                }
                case "b": {
                    if (!blueSide.validateSide(sideString))
                        return CubeStatus.SIDE_ERROR_UNKNOWN;
                    blueSide.setSquaresandColours(sideString);
                    break;
                }
                case "g": {
                    if (!greenSide.validateSide(sideString))
                        return CubeStatus.SIDE_ERROR_UNKNOWN;
                    greenSide.setSquaresandColours(sideString);
                    break;
                }
                case "y": {
                    yellowSide.setSquaresandColours(sideString);
                    break;
                }
            }
        }
        return CubeStatus.OK;
    }

    /**
     * This is actually a good example of how to build the sides using the instructions above (from buildSidesFromString() method) :-)
     */
    public CubeStatus buildAsSolved() throws Exception {
        // note that the top and bottom sides can be calculated from the information we have here
        String notation = "ogy oy oby og o ob ogw ow obw\n" + // orange side (front)
                "boy by bry bo b br bow bw brw\n" + // right
                "rby ry rgy rb r rg rbw rw rgw\n" +  // back
                "gry gy goy gr g go grw gw gow\n"; // left
        CubeStatus status = buildSidesFromString(notation);
        return status;
    }


    /**
     * gets a string that a gui could easily deal with to build a cube
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
        return returnSB.toString();
    }

    /**
     * gets a string that displays the contents of all the whole cube
     * @return
     */
    public String getDisplaySidesForDebug() {
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
        return returnSB.toString();
    }

    /**
     * follows a predifined set of instructions
     * returns false if it fails
     *
     * @param algorithm a space seperated list of instructions - each must have 2 letters, i.e. fc (front clockwise)
     */
    public boolean followAlgorithmAttempt(String algorithm) throws Exception {
        String[] instructions = algorithm.split(" ");
        for (String instruction : instructions) {

            if (instruction.length() != 2) {
                return false;
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
