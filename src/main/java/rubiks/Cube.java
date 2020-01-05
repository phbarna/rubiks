package rubiks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

/**
 * represents the cube as a whole.
 * Should have public methods for turns
 * NOTE that the cube is always set up as solve as default.
 * Randomising the turns will mess the cube up
 * Future instructions for passing a specific state of the cube will be done
 */
class Cube {

    Square redSquare = new Square();
    // create our six sides
    private final Side whiteSide = new Side().withColour(Colour.WHITE);
    private final Side yellowSide = new Side().withColour(Colour.YELLOW);
    private final Side blueSide = new Side().withColour(Colour.BLUE);
    private final Side redSide = new Side().withColour(Colour.RED);
    private final Side orangeSide = new Side().withColour(Colour.ORANGE);
    private final Side greenSide = new Side().withColour(Colour.GREEN);
    private Square[] squares = new Square[26]; // predifined 26 minicubes

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

    public void rightClockwise() {
        System.out.println("rightClockwise");
    }

    public void rightAntiClockwise() {
        System.out.println("rightAnticlockwise");
    }

    public void leftClockwise() {
        System.out.println("leftClockwise");
    }

    public void leftAntiClockwise() {
        System.out.println("leftAntiClockwise");
    }

    public void frontClockwise() {
        System.out.println("frontClockwise");
    }

    public void frontAntiClockwise() {
        System.out.println("frontAntiClockwise");
    }

    public void upperClockwise() {
        System.out.println("upperClockwise");
    }

    public void upperAntiClockwise() {
        System.out.println("upperAntiClockwise");
    }


    /**
     *
     */
    public Cube() {
        // set up squares - there will be 26
        HashSet<String> squaresHM = new HashSet<>(); // used for self validating that all squares are different
        try {
            // build corners
            squaresHM.add("g o y");
            squaresHM.add("b r y");
            squaresHM.add("b o y");
            squaresHM.add("g r y");
            squaresHM.add("g o w");
            squaresHM.add("b r w");
            squaresHM.add("g r w");
            squaresHM.add("b o w");

            // build sides
            squaresHM.add("b w");
            squaresHM.add("b y");
            squaresHM.add("b o");
            squaresHM.add("b r");
            squaresHM.add("g r");
            squaresHM.add("g w");
            squaresHM.add("g o");
            squaresHM.add("g y");
            squaresHM.add("o w");
            squaresHM.add("o y");
            squaresHM.add("r y");
            squaresHM.add("r w");

            // build single side colours
            squaresHM.add("b");
            squaresHM.add("g");
            squaresHM.add("o");
            squaresHM.add("y");
            squaresHM.add("w");
            squaresHM.add("r");

            if (squaresHM.size() != 26) {
                throw new Exception("Error building cube - " + squaresHM.size() + " is wrong number. Need 26");
            }

            // size of hm should be 28, all unique

            int index = 0;
            for (String uniqueColour: squaresHM) {
                switch (uniqueColour.length()) {
                    case 1: {
                        squares[index] = new Square().withColours(uniqueColour);
                        break;
                    }
                    case 3: {
                        squares[index] = new EdgeSquare().withColours(uniqueColour);
                        break;
                    }
                    case 5: {
                        squares[index] = new CornerSquare().withColours(uniqueColour);
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

    /**
     *    /**
     *      * Build all 6 sides (just as strings using a fixed instructions as follows.
     *      * As we look at the cube
     *      * orange is front
     *      * blue is right
     *      * red is back
     *      * green is left
     *      * yellow is top
     *      * white is bottom
     *      *
     *
     *      * Our notation works line by line for each where the correspondsing and unique square represents the 1 2 or 3 letters
     *      * the first letter of each square represents the outward facing colour for that position. The C is the center square and is fixed colour
     *      * flt ft frt fl C fr flb fb frb
     *      * flt ft frt fl C fr flb fb frb
     *      * flt ft frt fl C fr flb fb frb
     *      * flt ft frt fl C fr flb fb frb
     * @param longNotation
     */
    public void buildSidesFromStrings(String longNotation) {
        CubeUtils cubeUtils = new CubeUtils();
        try {
            // the longNotation only has sides - we calculate top and bottom sides from this info
            String[] sideStrings = cubeUtils.addTopAndBottoms(longNotation);
            for (String sideString: sideStrings) {
                // extract colour from this string at positionn 14 - this is the center point on the side and never changes
                String correctColour = sideString.substring(14,15);
                try {
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This is actually a good example of how to build the sides using the instructions above (from buildSidesFromString() method) :-)
     */
    public void buildSolvedCube()  {
        Side[] sidesToReturn = new Side[6];
        // note that the top and bottom sides can be calculated from the information we have here
        String notation = "ogy oy oby og o ob ogw ow obw\n" + // orange side (front)
                "boy by bry bo b br bow bw brw\n"  + // right
                "rby ry rgy rb r rg rbw rw rgw\n"  +  // back
                "gry gy goy gr g go grw gw gow\n"; // left
        String[] stringSideNotations = new String[9];
        try {
            buildSidesFromStrings(notation);

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
                throw new Exception("algorith does not support " +instruction);
            }

            switch (instruction) {
                case "rc": {
                    rightClockwise();
                    break;
                }
                case "ra": {
                    rightAntiClockwise();
                    break;
                }
                case "fc": {
                    frontClockwise();
                    break;
                }
                case "fa": {
                    frontAntiClockwise();
                    break;
                }
                case "uc": {
                    upperClockwise();
                    break;
                }
                case "ua": {
                    upperAntiClockwise();
                    break;
                }
                case "lc": {
                    leftClockwise();
                    break;
                }
                case "la": {
                    leftAntiClockwise();
                    break;
                }
                default: {
                    throw new Exception("algorith does not support " +instruction);
                }

            }
        }
    }

    /**
     * gets an array of 9 representing all the colours of one side
     * @param colour to determing which 9 cubes to get from the 26
     * @return
     */
    public Square[] getCubesOfColour(Colour colour) throws  Exception{

        List<Square> returnSquares = new ArrayList<Square>();

        for (Square square: squares) {
            Colour[] squareColours = square.getColours();
            for (Colour c: squareColours) {
                if (c.equals(colour)) {
                    returnSquares.add(square);
                    // we have found a match add it to our returnColours
                }
            }
        }
        if (returnSquares.size() != 9) {
            throw new Exception("Error trying to return "+returnSquares.size() + " when there must be 9 for "+colour.toString());
        }
        return returnSquares.toArray(new Square[9]);
    }

    /**
     * returns a randomised cube
     */
    public Cube shuffle() {
        // define the possible number of moves
        int max = 25;
        int min = 15;
        int range = max - min + 1;
        int numTwists = (int) (Math.random() * range) + min;
        // generate random numbers within 1 to 10
        for (int i = 0; i < numTwists; i++) {
            // select random from 1-4 (for our 4 twist moves)
            int rand = (int) (Math.random() * 4) + 1;

            // pointless to turn more than 3 times - and note that no point in doing anticlockwise moves
            // as the number of turns can be up to 3
            int numbnerofTimes = (int) (Math.random() * 3) + 1;
            switch (rand) {
                case 1: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        upperClockwise();
                    break;
                }
                case 2: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        rightClockwise();
                    break;
                }
                case 3: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        leftClockwise();
                    break;
                }
                case 4: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        frontClockwise();
                    break;
                }


            }

        }
        return this;
    }
}
