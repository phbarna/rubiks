package rubiks;

class EdgeMiniFace extends MiniFace {

    private Colour otherAxisColour = null; // we don't know if it is an x-axis or y-axis but we know it it on the other axis to the face

    EdgeMiniFace withColours(String colours)  {
        faceColour = Colour.ofName(colours.substring(0,1));
        otherAxisColour = Colour.ofName(colours.substring(1,2));
        return this;
    }

    public void setColours(String colours) {
        this.faceColour = Colour.ofName(colours.substring(0,1));
        this.otherAxisColour = Colour.ofName(colours.substring(1,2));
    }

    public void rotateColours(int numberOfTurns) {
        // noOp - the face colour never changes for a turn.
    }

    public Colour[] getColours() {
        return new Colour[]{faceColour, otherAxisColour};
    }

    /**
     * returns a string representation of the colours.
     * @return - Length of 2 i.e. it's face colour and it's corresponding x or y axis colour
     */
    @Override
    public String toString() {
        return faceColour.toString()+otherAxisColour.toString();
    }

}
