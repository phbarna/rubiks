package rubiks;

public abstract class MiniFace {

    protected Colour faceColour = null; // the single colour of the face

    /**
     * tempBuffer - used as we switch the orientation of colours -
     * not actually used in parent class as there is only one colour (faceColour) - but it's children use it
     */
    protected Colour tempBuffer = null;

    protected MiniFace withFaceColourFromString(String s) {
        faceColour = Colour.valueOf(s);
        return this;
    }

    protected abstract void setColours(String s);


    protected abstract MiniFace withColours(String colours);
    /**
     * used identically by all child classes.
     * @return returns this face colour
     */
    public Colour getFaceColour() {
        return faceColour;
    }

    public abstract Colour[] getColours();

    public abstract void rotateColours(int numberOfTurns);

    public abstract String toString();

}
