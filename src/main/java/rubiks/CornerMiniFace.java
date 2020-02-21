package rubiks;

/**
 * Corner squares have 3 faces
 */
class CornerMiniFace extends MiniFace {

    private Colour xAxisColour = null; // any other face left or right relative to the main face
    private Colour yAxisColour = null; // any other face up or down relative the the main face.

    CornerMiniFace withColours(String colours)  {
        faceColour = Colour.valueOf(colours.substring(0, 1));
        xAxisColour = Colour.valueOf(colours.substring(1, 2));
        yAxisColour = Colour.valueOf(colours.substring(2, 3));

        return this;
    }

    public void setColours(String colours) {
        this.faceColour = Colour.valueOf(colours.substring(0,1));
        this.xAxisColour = Colour.valueOf(colours.substring(1,2));
        this.yAxisColour = Colour.valueOf(colours.substring(2,3));
    }

    /**
     * rotate the colours to their corresponding positions
     * @param numberOfTurns - The numb er of times to rotate this face
     */
    public void rotateColours(int numberOfTurns) {

        for (int i = 0; i< numberOfTurns;i++) {
            Colour tempBuffer = xAxisColour;
            xAxisColour = yAxisColour;
            yAxisColour = tempBuffer;
        }
    }

    public Colour[] getColours() {
        return new Colour[]{faceColour, xAxisColour, yAxisColour};
    }

    /**
     * Returns a string representation of the colours.
     * @return Returns the string representation of all 3 sides e.g. "boy" which is blue face , orange x-axis yellow y-axis
     */
    @Override
    public String toString() {
        return faceColour.toString()+xAxisColour.toString()+yAxisColour;
    }

}
