package rubiks;

public class EdgeMiniFAce extends MiniFace {

    protected Colour otherAxisColour = null; // we don't know if it is an x-axis or y-axis but we know it it on the other axis to the face

    protected EdgeMiniFAce withColours(String colours) throws Exception {

        faceColour = Colour.valueOf(colours.substring(0,1));
        otherAxisColour = Colour.valueOf(colours.substring(1,2));

        if (faceColour.equals(otherAxisColour)) {
            throw new Exception("Error with EdgeMiniFAce - colours are the same");
        }
        return this;
    }

    /**
     * this is just a case of swapping the facecolour with the otheraxis
     * @param numberOfTurns
     */
    public void rotateColours(int numberOfTurns) {

    }

    public Colour[] getColours() {
        Colour[] colours = {faceColour, otherAxisColour};
        return colours;
    }

    /**
     * returns a string representation of the colours.
     * @return
     */
    @Override
    public String toString() {
       return faceColour.toString()+otherAxisColour.toString();
    }

}
