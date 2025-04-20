package rubiks;

/**
 * Represents a side of the cube.
 */
class Side {
  /**
   * A utils object for getting various parts of a cube.
   */
  private static final CubeUtils CUBE_UTILS = new CubeUtils();
  /**
   * MiniFaceColour Keeps track of the physical colours looking from the sides -
   * useful for working out orientation of each miniCbe.
   * Also makes life easier when actually outputting the 9 colours of any face.
   * Note that this should correspond with one of the colours on the square
   * occupying the same place in
   * the array - else something has gone wrong and an exception should be thrown.
   * The annotation of these strings works front side topBottom order. For example
   * <p>
   * example
   * =======
   * wrb on a blue face
   * obviously a corner piece. Means that the square on the blue face is currently
   * showing white,
   * the red is on the red (right face) and the blue would is displaying on the
   * top (yellow face).
   * Thus the square on this blue face would have to be at array position 2.
   */
  private final MiniFace[][] miniFaces = new MiniFace[3][3];

  /**
   * This MUST not be changed once it is set.
   */
  private Colour sideColour = null;

  /**
   * Returns a miniFace determined by coordinates from this face.
   *
   * @param x the position in the horizontal axis as we look at this face
   * @param y the position in the vertical axis as we look at this face
   * @return A single square from the miniface.
   */
  MiniFace getMiniFace(final int x, final int y) {
    return miniFaces[x][y];
  }

  /**
   * Used for extracting the 3 blocks that are being rotated.
   *
   * @param n - The column from this miniFace to be returned.
   * @return Returns array of miniFaces
   */
  MiniFace[] getColumn(final int n) {

    MiniFace[] returnColumn = new MiniFace[3];
    for (int i = 0; i < 3; i++) {
      returnColumn[i] = this.miniFaces[i][n];
    }
    return returnColumn;
  }

  /**
   * Used for extracting the 3 blocks that are being rotated.
   *
   * @param n The row to get
   * @return Returns a row
   */
  MiniFace[] getRow(final int n) {
    return miniFaces[n];
  }

  void setRow(final int n, final MiniFace[] rowIn) {
    miniFaces[n] = rowIn;
  }

  void setColumn(final int n, final MiniFace[] columnIn) {
    for (int i = 0; i < 3; i++) {
      miniFaces[i][n] = columnIn[i];
    }
  }

  /**
   * Sets the face colours for a miniCube (there can be one, two or three colours
   * depending whether center piece, edge piece
   * or corner piece.
   *
   * @param line A line represents the colours of the face
   */
  void setMiniColourFaces(final String line) {
    String trimmedLine = line.replace(" ", "");
    // already validated as 9 hopefully :-)
    String[] stringColours = trimmedLine.split("");
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
   * Check that all faceColours for this side are equal to this colour.
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
   * Rotate this side - note this will not affect any other sides - just this one.
   *
   * @param numberOfTurns The number of times to rotate
   */
  void rotateSide(final int numberOfTurns) {
    int mod4Turns = numberOfTurns % 4;

    for (int i = 0; i < mod4Turns; i++) {

      MiniFace[] topRow = getRow(0);
      MiniFace[] topRowCopy = CUBE_UTILS.makeRowColCopy(topRow);

      MiniFace[] bottomRow = getRow(2);
      MiniFace[] bottomRowCopy = CUBE_UTILS.makeRowColCopy(bottomRow);

      MiniFace[] leftCol = getColumn(0);
      MiniFace[] leftColCopy = CUBE_UTILS.makeRowColCopy(leftCol);

      MiniFace[] rightCol = getColumn(2);
      MiniFace[] rightColCopy = CUBE_UTILS.makeRowColCopy(rightCol);

      // top row goes to right column
      setColumn(2, topRowCopy); // correct
      CUBE_UTILS.rotateRowColFaces(getColumn(2), mod4Turns);

      rightColCopy = CUBE_UTILS.reverseRowCol(rightColCopy);

      // right column to bottom row --
      setRow(2, rightColCopy);
      CUBE_UTILS.rotateRowColFaces(getRow(2), mod4Turns);

      // bottom row to left column
      setColumn(0, bottomRowCopy);
      CUBE_UTILS.rotateRowColFaces(getColumn(0), mod4Turns);

      // left col to top row
      leftColCopy = CUBE_UTILS.reverseRowCol(leftColCopy);
      setRow(0, leftColCopy);
      CUBE_UTILS.rotateRowColFaces(getRow(0), mod4Turns);
    }
  }

  /**
   * Return string representation of the state of this side.
   *
   * @return string representation of this side
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    // iterate through 3 by 3 array
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        sb.append(this.miniFaces[i][j].toString()).append(" ");
      }
    }
    return sb.toString().trim();
  }

  /**
   * Returns the outward facing colours - as 3 by 3 grid display. . Nice to see
   * how a side is doing,
   * especially for debugging.
   *
   * @return Returns the string representation of the colours of this side.
   */
  String getAllColoursForSide() {
    StringBuilder sb = new StringBuilder();
    // Iterate through 3 by 3 array
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

  Side withColour(final Colour c) {
    this.sideColour = c;
    return this;
  }

}
