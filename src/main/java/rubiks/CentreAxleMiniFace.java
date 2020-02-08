package rubiks;

/**
 * a miniFAce ... Has one face and is synonymous with a side
 */
class CentreAxleMiniFace extends MiniFace {

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

    /**
     *
     * @return Gets all colours as an array
     */
    public Colour[] getColours() {
        return new Colour[]{faceColour};
    }

    /**
     * returns a string representation of the colours.
     * @return Returns the colour of this face
     */
    public String toString() {

        return faceColour.toString();
    }

}

