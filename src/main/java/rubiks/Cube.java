package rubiks;

/**
 * represents the cuybe as a whole.
 * Should have public methods for turns
 * NOTE that the cube is always set up solved.  Randomising the turns will mess the cube up
 */
public class Cube {
    // construct 28 minicube squares
    Square redSquare = new Square();
    // create our six sides
    private Side whiteSide = new Side().withColour(Colour.WHITE);
    private Side yellowSide = new Side().withColour(Colour.YELLOW);
    private Side blueSide = new Side().withColour(Colour.BLUE);
    private Side redSide = new Side().withColour(Colour.RED);
    private Side orangeSide = new Side().withColour(Colour.ORANGE);
    private Side greenSide = new Side().withColour(Colour.GREEN);

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
     * follows a predifined set of instructions
     *
     * @param algorithm a space seperated list of instructions - each must have 2 letters, i.e. fc (front clockwise)
     */
    public void followAlgorithem(String algorithm) throws Exception {
        String[] instructions = algorithm.split(" ");
        for (String instruction : instructions) {

            if (instruction.length() != 2) {
                throw new Exception("instruction "+instruction + " is malformed");
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
