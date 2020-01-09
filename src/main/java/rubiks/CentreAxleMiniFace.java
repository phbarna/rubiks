package rubiks;

/**
 * a miniFAce ... Has one face and is synominous with a side
 */
public class CentreAxleMiniFace extends MiniFace {

    /**
     * tempBuffer - used as we switch the orientation of colours -
     * not actually used in parent class as there is only one colour (faceColour) - but it's children use it
     */
    protected Colour tempBuffer = null;

    protected CentreAxleMiniFace withColours(String colours) throws Exception {
        faceColour = Colour.valueOf(colours);
        return this;
    }

    public void rotateColours(int numberOfTurns) {
        // no op - there is only one colour on this face so nothing to do
    }

    public Colour[] getColours() {
        Colour[] colours = {faceColour};
        return colours;
    }

    /**
     * returns a string representation of the colours.
     * @return
     */
    public String toString() {
        return faceColour.toString();
    }

}

