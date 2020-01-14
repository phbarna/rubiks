package rubiks;

import java.util.HashSet;

/**
 * corner squares have 3 faces
 */
class CornerMiniFace extends MiniFace {

    protected Colour xAxisColour = null; // any other face left or right relative to the main face
    protected Colour yAxisColour = null; // any other face up or down relative the the main face.

    public CornerMiniFace withColours(String colours)  {
        faceColour = Colour.valueOf(colours.substring(0, 1));
        xAxisColour = Colour.valueOf(colours.substring(1, 2));
        yAxisColour = Colour.valueOf(colours.substring(2, 3));

        HashSet<Colour> hs = new HashSet<Colour>();
        hs.add(faceColour);
        hs.add(xAxisColour);
        hs.add(yAxisColour);

        return this;
    }

    public void setColours(String colours) {
        this.faceColour = Colour.valueOf(colours.substring(0,1));
        this.xAxisColour = Colour.valueOf(colours.substring(1,2));
        this.yAxisColour = Colour.valueOf(colours.substring(2,3));
    }

    /**
     * rotate the colours to their corresponding positions
     * @param numberOfTurns
     */
    public void rotateColours(int numberOfTurns) {

        for (int i = 0; i< numberOfTurns;i++) {
            tempBuffer = xAxisColour;
            xAxisColour = yAxisColour;
            yAxisColour = tempBuffer;
        }

    }

    public Colour[] getColours() {
        Colour[] colours = {faceColour, xAxisColour, yAxisColour};
        return colours;
    }

    /**
     * returns a string representation of the colours.
     * @return
     */
    @Override
    public String toString() {
        return faceColour.toString()+xAxisColour.toString()+yAxisColour;
    }

}
