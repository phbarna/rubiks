package rubiks;

/**
 * Edge mini face have 2 faces
 * Note that faceColour is inheritted from a protected variable.
 */
final class EdgeMiniFace extends MiniFace {
  // we don't know if it is an x-axis or y-axis but we know it it on the other
  // axis to the face
  private Colour otherAxisColour = null;

  EdgeMiniFace withColours(final String colours) {
    faceColour = Colour.ofName(colours.substring(0, 1));
    otherAxisColour = Colour.ofName(colours.substring(1, 2));
    return this;
  }

  @Override
  public void setColours(final String colours) {
    this.faceColour = Colour.ofName(colours.substring(0, 1));
    this.otherAxisColour = Colour.ofName(colours.substring(1, 2));
  }

  @Override
  public void rotateColours(final int numberOfTurns) {
    // noOp - the face colour never changes for a turn.
  }

  @Override
  public Colour[] getColours() {
    return new Colour[] {
        faceColour, otherAxisColour
    };
  }

  /**
   * Returns a string representation of the colours.
   * 
   * @return - Length of 2 i.e. it's face colour and it's corresponding x or y
   *         axis colour
   */
  @Override
  public String toString() {
    return faceColour.toString() + otherAxisColour.toString();
  }
}
