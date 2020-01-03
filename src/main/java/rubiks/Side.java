package rubiks;

/**
 * Represents a side of the cube.
 */
public class Side {

    private Colour colour;

    public Colour getColour() {
        return colour;
    }

    public void firstMethod() {
        System.out.println("gets here");
    }

    public Side withColour(Colour c) {
        this.colour = c;
        return this;
    }
}
