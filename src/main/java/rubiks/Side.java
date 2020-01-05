package rubiks;

/**
 * Represents a side of the cube.
 */
public class Side {

    private Colour sideColour; // this MUST not be changed once it is set.

    private Square[] squares = new Square[9]; // represents the minicubes

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
    private String[] miniCubeFaceOrientationByColour = new String[9];

    /**
     * sets the position of the squares on this side.
     * Validation will be in place to ensure there is only one parent Square (centre colour)
     * 4 edge pieces 4 corner pieces.
     * Note that other sides will be sharing the same square.
     * Also note that position 4 in the array is fixed to a single parent Square.
     * Also note that it is critical that the squares array come in in the correct order -
     * it is the job of the Cube logic to order them though.
     */
    public void setSquaresandColours(Square[] squares, Colour[] colours) throws Exception {

        if (squares.length != 9) {
            throw new Exception("Error - cannbot build side with " + squares.length + " squares");
        }
        this.squares = squares;

    }

    /**
     *
     * @param notation a string representation e.g "boy by bry bo b br bow bw brw
     * which reads left to right, top to bottom of each side. First colour is the forward facing
     * 2nd colour is nearest edge and 3rd colour is top or bottom
     *
     * @return this object
     */
    public Side setSquaresandColours(String notation) throws Exception {

        String[] blocks = notation.split(" ");
        // note that array position 4 HAS to correspond with the colour of this side - this is critical and is also checked elsewhere
        if (!this.sideColour.toString().equals(blocks[4]))
        {
            throw new Exception("The side you are trying to build does not correspond with the colour of this side"+
            " for " +this.sideColour.toString() + " and "+blocks[4]);
        }

        for (int i = 0;i< blocks.length; i++) {

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
