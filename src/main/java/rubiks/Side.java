package rubiks;

/**
 * Represents a side of the cube.
 */
class Side {
  private final CubeUtils cubeUtils = new CubeUtils();
  /**
   * MiniFaceColour Keeps track of the physical colours looking from the sides - useful for working out orientation of each miniCbe.
   * Also makes life easier when actually outputting the 9 colours of any face.
   * Note that this should correspond with one of the colours on the square occupying the same place in
   * the array - else something has gone wrong and an exception should be thrown.
   * The annotation of these strings works front side topBottom order. For example
   * <p>
   * example
   * =======
   * wrb on a blue face
   * obviously a corner piece.  Means that the square on the blue face is currently showing white,
   * the red is on the red (right face) and the blue would is displaying on the top (yellow face).
   * Thus the square on this blue face would have to be at array position 2.
   */
  private final MiniFace[][] miniFaces = new MiniFace[3][3];
  private Colour sideColour; // this MUST not be changed once it is set.

  /**
   * Used for extracting the 3 blocks that are being rotated
   *
   * @param n - The column from this miniFace to be returned.
   * @return Returns array of miniFaces
   */
  MiniFace[] getColumn(int n) {

    MiniFace[] returnColumn = new MiniFace[3];
    for (int i = 0; i < 3; i++) {
      returnColumn[i] = this.miniFaces[i][n];
    }
    return returnColumn;
  }

  /**
   * Used for extracting the 3 blocks that are being rotated
   *
   * @param n The row to get
   * @return Returns a row
   */
  MiniFace[] getRow(int n) {
    return miniFaces[n];
  }

  void setRow(int n, MiniFace[] rowIn) {
    miniFaces[n] = rowIn;
  }

  void setColumn(int n, MiniFace[] columnIn) {
    for (int i = 0; i < 3; i++) {
      miniFaces[i][n] = columnIn[i];
    }
  }

  /**
   * Sets the face colours for a miniCube (there can be one, two or three colours depending whether center piece, edge piece
   * or corner piece.
   *
   * @param line A line represents the colours of the face
   */
  void setMiniColourFaces(String line) {
    line = line.replace(" ", "");
    String[] stringColours = line.split(""); // already validated as 9 hopefully :-)
    int index = 0;
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        int cubeType = index % 2;
        if (index == 4) { // center piece
          this.sideColour = Colour.ofName(stringColours[index]);
          miniFaces[r][c] = new CentreAxleMiniFace().withColours(stringColours[index]);
        } else if (cubeType == 0) { // corner piece
          miniFaces[r][c] = new CornerMiniFace()
              .withColours(stringColours[index] + "ww"); // put dummy whites in for now
        } else { // edge piece
          miniFaces[r][c] = new EdgeMiniFace()
              .withColours(stringColours[index] + "w"); // put in dummy white for now
        }
        index++;
      }
    }
  }

  /**
   * Check that all faceColours for this side are equal to this colour
   *
   * @return Returns true if the cube is in a solved state, else false.
   */
  boolean checkSolvedSide() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (!miniFaces[i][j].faceColour.equals(sideColour)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Rotate this side - note this will not affect any other sides - just this one
   *
   * @param numberOfTurns The number of times to rotate
   */
  void rotateSide(int numberOfTurns) {
    numberOfTurns = numberOfTurns % 4;

    for (int i = 0; i < numberOfTurns; i++) {

      MiniFace[] topRow = getRow(0);
      MiniFace[] topRowCopy = cubeUtils.makeRowColCopy(topRow);

      MiniFace[] bottomRow = getRow(2);
      MiniFace[] bottomRowCopy = cubeUtils.makeRowColCopy(bottomRow);

      MiniFace[] leftCol = getColumn(0);
      MiniFace[] leftColCopy = cubeUtils.makeRowColCopy(leftCol);

      MiniFace[] rightCol = getColumn(2);
      MiniFace[] rightColCopy = cubeUtils.makeRowColCopy(rightCol);

      // top row goes to right column
      setColumn(2, topRowCopy); // correct
      cubeUtils.rotateRowColFaces(getColumn(2), numberOfTurns);

      rightColCopy = cubeUtils.reverseRowCol(rightColCopy);

      // right column to bottom row --
      setRow(2, rightColCopy);
      cubeUtils.rotateRowColFaces(getRow(2), numberOfTurns);

      // bottom row to left column
      setColumn(0, bottomRowCopy);
      cubeUtils.rotateRowColFaces(getColumn(0), numberOfTurns);

      // left col to top row
      leftColCopy = cubeUtils.reverseRowCol(leftColCopy);
      setRow(0, leftColCopy);
      cubeUtils.rotateRowColFaces(getRow(0), numberOfTurns);
    }
  }

  /**
   * return string representation of the state of this side
   *
   * @return string representation of this side
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    //iterate through 3 by 3 array
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        sb.append(this.miniFaces[i][j].toString()).append(" ");
      }
    }
    return sb.toString().trim();
  }

  /**
   * Returns the outward facing colours - as 3 by 3 grid display. .  Nice to see how a side is doing,
   * especially for debugging.
   *
   * @return Returns the string representation of the colours of this side.
   */
  String getAllColoursForSide() {
    StringBuilder sb = new StringBuilder();
    //Iterate through 3 by 3 array
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        sb.append(this.miniFaces[i][j].toString(), 0, 1);

      }
    }
    return sb.toString() + "\n";
  }

  Colour getColour() {
    return sideColour;
  }

  Side withColour(Colour c) {
    this.sideColour = c;
    return this;
  }

  /**
   * returns a miniFace determined by coordinates from this face
   *
   * @param x the position in the horizontal axis as we look at this face
   * @param y the position in the vertical axis as we look at this face
   */
  MiniFace getMiniFace(int x, int y) {
    return this.miniFaces[x][y];
  }
}
