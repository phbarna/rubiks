package rubiks;

/**
 * a miniFAce ... Has one face and is synominous with a side
 */
public class CentreAxleMiniFace extends MiniFace {

    protected void setColours(String colours) {
        this.faceColour = Colour.valueOf(colours.substring(0,1));
    }

    protected CentreAxleMiniFace withColours(String colours)  {
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

