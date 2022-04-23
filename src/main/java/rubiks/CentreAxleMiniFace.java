package rubiks;

/**
 * A center axis ... Has one face and is synonymous with a side
 */
final class CentreAxleMiniFace extends MiniFace {

  CentreAxleMiniFace withColours(String colours) {
    faceColour = Colour.ofName(colours);
    return this;
  }

  public void rotateColours(int numberOfTurns) {
    // no op - there is only one colour on this face so nothing to do
  }

  /**
   * @return Gets all colours as an array
   */
  public Colour[] getColours() {
    return new Colour[] {faceColour};
  }

  protected void setColours(String colours) {
    this.faceColour = Colour.ofName(colours.substring(0, 1));
  }

  /**
   * returns a string representation of the colours.
   *
   * @return Returns the colour of this face
   */
  public String toString() {
    return faceColour.toString();
  }
}

