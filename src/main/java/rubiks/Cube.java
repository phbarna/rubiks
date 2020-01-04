package rubiks;

import java.util.HashSet;

/**
 * represents the cuybe as a whole.
 * Should have public methods for turns
 * NOTE that the cube is always set up solved.  Randomising the turns will mess the cube up
 */
class Cube {
    // construct 28 minicube squares
    Square redSquare = new Square();
    // create our six sides
    private Side whiteSide = new Side().withColour(Colour.WHITE);
    private Side yellowSide = new Side().withColour(Colour.YELLOW);
    private Side blueSide = new Side().withColour(Colour.BLUE);
    private Side redSide = new Side().withColour(Colour.RED);
    private Side orangeSide = new Side().withColour(Colour.ORANGE);
    private Side greenSide = new Side().withColour(Colour.GREEN);

    private HashSet<String> squaresHM = new HashSet<>(); // used for self validating that all squares are different

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

    public void order(String s)
    {


    }

    public Cube() {
        // set up squares - there should be 28
        order("r b g");
        try {
            // build corners
            squaresHM.add("g o y");
            squaresHM.add("g o w");
            squaresHM.add("b r w");
            squaresHM.add("g r w");
            squaresHM.add("b o w");
            squaresHM.add("g r y");
            squaresHM.add("o r y");
            squaresHM.add("b r y");

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
                throw new Exception("Error building cube - " + squaresHM.size() + " is not enough sqares. Need 26");
            }

            // size of hm should be 28, all unique
            Square[] squares = new Square[28]; // predifined 28 squares
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

            System.out.println("size is "+squaresHM.size());


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
     * returns a randomised cube
     */
    public Cube shuffle() {
        // define the possible number of moves
        int max = 20;
        int min = 7;
        int range = 20 - 7 + 1;
        int numTwists = (int) (Math.random() * range) + min;
        // generate random numbers within 1 to 10
        for (int i = 0; i < numTwists; i++) {
            // select random from 1-8 (for our 8 twist moves)
            int rand = (int) (Math.random() * 8) + 1;

            // pointless to turn more than 3 times
            int numbnerofTimes = (int) (Math.random() * 3) + 1;
            switch (rand) {
                case 1: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        rightClockwise();
                    break;
                }
                case 2: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        rightAntiClockwise();
                    break;
                }
                case 3: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        leftClockwise();
                    break;
                }

                case 4: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        leftAntiClockwise();
                    break;
                }
                case 5: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        upperClockwise();
                    break;
                }
                case 6: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        upperAntiClockwise();
                    break;
                }
                case 7: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        frontClockwise();
                    break;
                }
                case 8: {
                    for (int t = 0; t < numbnerofTimes; t++)
                        frontAntiClockwise();
                    break;
                }


            }

        }
        return this;
    }
}
