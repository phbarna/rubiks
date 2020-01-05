package rubiks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    // set up miniCubes - there must be 26 - and a hashmap is used here to comfirm that there are 26
    HashSet<String> miniCubesHM;
    private MiniCube[] miniCubes = new MiniCube[26]; // predifined 26 minicubes

    /**
     *
     */
    public Cube() {
        miniCubesHM = new HashSet<>(); // used for self validating that all miniCubes are different
        try {
            // build corners
            miniCubesHM.add("goy");
            miniCubesHM.add("bry");
            miniCubesHM.add("boy");
            miniCubesHM.add("gry");
            miniCubesHM.add("gow");
            miniCubesHM.add("brw");
            miniCubesHM.add("grw");
            miniCubesHM.add("bow");

            // build sides
            miniCubesHM.add("bw");
            miniCubesHM.add("by");
            miniCubesHM.add("bo");
            miniCubesHM.add("br");
            miniCubesHM.add("gr");
            miniCubesHM.add("gw");
            miniCubesHM.add("go");
            miniCubesHM.add("gy");
            miniCubesHM.add("ow");
            miniCubesHM.add("oy");
            miniCubesHM.add("ry");
            miniCubesHM.add("rw");

            // build single side colours
            miniCubesHM.add("b");
            miniCubesHM.add("g");
            miniCubesHM.add("o");
            miniCubesHM.add("y");
            miniCubesHM.add("w");
            miniCubesHM.add("r");

            if (miniCubesHM.size() != 26) {
                throw new Exception("Error building cube - " + miniCubesHM.size() + " is wrong number. Need 26");
            }

            // size of hm should be 28, all unique

            int index = 0;
            for (String uniqueColour : miniCubesHM) {
                switch (uniqueColour.length()) {
                    case 1: {
                        miniCubes[index] = new MiniCube().withColours(uniqueColour);
                        break;
                    }
                    case 2: {
                        miniCubes[index] = new EdgeMiniCube().withColours(uniqueColour);
                        break;
                    }
                    case 3: {
                        miniCubes[index] = new CornerMiniCube().withColours(uniqueColour);
                        break;
                    }

                }
                index++;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        buildSolvedCube(); // builds cube in it's solved state

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

        if (numberOfTimes > 3) { // not a logical error but turning more than 3 times is silly so I'm throwing an exception in protest
            throw new Exception("Please don't try and turn more than 3 times in same direction. It's pointless");
        }

        // we should back up this face - as it is going to be messed around a bit from other faces - and we need to rotate it
        String[] backupOfThisFace = new String[9];
        for (int i = 0; i< 9; i++) {

        }


        for (int i = 0; i<4;i++) {
           TurnTransposeOrder whatToDo = TurnTransposeOrder.values()[i];
           // under dev
           String[] temp = new String[3];
           otherSides[i].receiveRoworColumn(temp, turningSide, 1,true, whatToDo);

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
    public void buildSolvedCube() {
        Side[] sidesToReturn = new Side[6];
        // note that the top and bottom sides can be calculated from the information we have here
        String notation = "ogy oy oby og o ob ogw ow obw\n" + // orange side (front)
                "boy by bry bo b br bow bw brw\n" + // right
                "rby ry rgy rb r rg rbw rw rgw\n" +  // back
                "gry gy goy gr g go grw gw gow\n"; // left
        String[] stringSideNotations = new String[9];
        try {
            buildSidesFromString(notation);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
     * gets an array of 9 representing all the colours of one side
     *
     * @param colour to determing which 9 cubes to get from the 26
     * @return
     */
    public MiniCube[] getCubesOfColour(Colour colour) throws Exception {

        List<MiniCube> returnMiniCubes = new ArrayList<MiniCube>();

        for (MiniCube miniCube : miniCubes) {
            Colour[] squareColours = miniCube.getColours();
            for (Colour c : squareColours) {
                if (c.equals(colour)) {
                    returnMiniCubes.add(miniCube);
                    // we have found a match add it to our returnColours
                }
            }
        }
        if (returnMiniCubes.size() != 9) {
            throw new Exception("Error trying to return " + returnMiniCubes.size() + " when there must be 9 for " + colour.toString());
        }
        return returnMiniCubes.toArray(new MiniCube[9]);
    }

    /**
     * returns a randomised cube
     */
    public Cube shuffle() throws Exception {
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
        return this;
    }
}
