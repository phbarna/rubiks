package rubiks;

/**
 * A center axis ... Has one face and is synonymous with a side.
 * Note that faceColour is inheritted from a protected variable.
 */
final class CentreAxleMiniFace extends MiniFace {

  CentreAxleMiniFace withColours(final String colours) {
    faceColour = Colour.ofName(colours);
    return this;
  }

  @Override
  public void rotateColours(final int numberOfTurns) {
    // no op - there is only one colour on this face so nothing to do
  }

  /**
   * @return Gets all colours as an array
   */
  @Override
  public Colour[] getColours() {
    return new Colour[] {
        faceColour
    };
  }

  @Override
  protected void setColours(final String colours) {
    this.faceColour = Colour.ofName(colours.substring(0, 1));
  }

  /**
   * returns a string representation of the colours.
   *
   * @return Returns the colour of this face
   */
  @Override
  public String toString() {
    return faceColour.toString();
  }
}
