package rubiks;

abstract class MiniFace {

    Colour faceColour = null; // the single colour of the face

    protected abstract void setColours(String s);

    /**
     * used identically by all child classes.
     * @return returns this face colour
     */
    Colour getFaceColour() {
        return faceColour;
    }

    public abstract Colour[] getColours();

    public abstract void rotateColours(int numberOfTurns);

    public abstract String toString();

}
