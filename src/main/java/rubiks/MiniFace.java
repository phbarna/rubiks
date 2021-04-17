package rubiks;

abstract class MiniFace {

  Colour faceColour = null; // the single colour of the face

  /**
   * used identically by all child classes.
   *
   * @return returns this face colour
   */
  Colour getFaceColour() {
    return faceColour;
  }

  public abstract Colour[] getColours();

  protected abstract void setColours(String s);

  public abstract void rotateColours(int numberOfTurns);

  public abstract String toString();

}
