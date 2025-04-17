package rubiks;

abstract sealed class MiniFace permits EdgeMiniFace, CornerMiniFace, CentreAxleMiniFace {

  /**
   * Represents the colour of the face i.e. center square colour.
   * The object is protected, so subclasses can have a direct relationship with
   * properties of the parent class.
   */
  protected Colour faceColour;

  /**
   * Used identically by all child classes.
   *
   * @return returns this face colour
   */
  Colour getFaceColour() {
    return faceColour;
  }

  public abstract Colour[] getColours();

  protected abstract void setColours(String s);

  public abstract void rotateColours(int numberOfTurns);

  @Override
  public abstract String toString();

}
