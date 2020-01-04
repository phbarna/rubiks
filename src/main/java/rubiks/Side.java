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
     */
    private Colour[] miniCubeFaceColour = new Colour[9];

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
        this.miniCubeFaceColour = colours;
    }






    public Colour getColour() {
        return sideColour;
    }

    public Side withColour(Colour c) {
        this.sideColour = c;
        return this;
    }
}
