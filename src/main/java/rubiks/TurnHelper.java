package rubiks;

/**
 * Manages the cube turns. We had to have individual methods for right, left,
 * upper and front down and back as the interactions with the cube are too
 * different to make one generic turn method
 */
class TurnHelper {
  private static final CubeUtils CUBE_UTILS = new CubeUtils();

  /**
   * Convert everything to clockwise turns.
   * 
   * @param numberOfTurns the original number of turns.
   * @param isClockwise   boolean to check if everything is already clocksise
   * @return the adjusted number of clockwise rutns
   */
  private int adjustTurns(final int numberOfTurns, final boolean isClockwise) {
    int numberOfTurnsMod4 = numberOfTurns % 4; // just in case a value higher than 4 gets put in
    if (!isClockwise) { // convert to clockwise
      numberOfTurnsMod4 = 4 - numberOfTurnsMod4; // convert everything to clockwise
      numberOfTurnsMod4 = numberOfTurnsMod4 % 4; // need to modulus it again
    }
    return numberOfTurnsMod4;
  }

  void rightTurn(final Cube cube, final boolean clockwise, final int numberOfTurns) {
    int numberOfClockwiseTurns = adjustTurns(numberOfTurns, clockwise);

    Side[] otherSides = {
        cube.getRedSide(), cube.getWhiteSide(), cube.getOrangeSide(), cube.getYellowSide()
    };
    Side turningSide = cube.getBlueSide();
    turningSide.rotateSide(numberOfClockwiseTurns);
    for (int turn = 0; turn < numberOfClockwiseTurns; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] rowCol1 = CUBE_UTILS.makeRowColCopy(otherSides[0].getColumn(0));
      MiniFace[] rowCol2 = CUBE_UTILS.makeRowColCopy(otherSides[1].getColumn(2));
      MiniFace[] rowCol3 = CUBE_UTILS.makeRowColCopy(otherSides[2].getColumn(2));
      MiniFace[] rowCol4 = CUBE_UTILS.makeRowColCopy(otherSides[3].getColumn(2));
      rowCol1 = CUBE_UTILS.reverseRowCol(rowCol1);
      otherSides[1].setColumn(2, rowCol1);
      // putting white in to orange no reverse
      otherSides[2].setColumn(2, rowCol2);
      // putting orange in to yellow col2 to col2 no reverse
      otherSides[3].setColumn(2, rowCol3);
      // putting yellow in to red col2 - col 0 reverse
      rowCol4 = CUBE_UTILS.reverseRowCol(rowCol4);
      otherSides[0].setColumn(0, rowCol4);
    }
  }

  void leftTurn(final Cube cube, final boolean clockwise, final int numberOfTurns) {
    int numberOfClockwiseTurns = adjustTurns(numberOfTurns, clockwise);

    Side[] otherSides = {
        cube.getOrangeSide(), cube.getWhiteSide(), cube.getRedSide(), cube.getYellowSide()
    };
    Side turningSide = cube.getGreenSide();
    turningSide.rotateSide(numberOfClockwiseTurns);
    for (int turn = 0; turn < numberOfClockwiseTurns; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] orangeCol = CUBE_UTILS.makeRowColCopy(otherSides[0].getColumn(0));
      MiniFace[] whiteCol = CUBE_UTILS.makeRowColCopy(otherSides[1].getColumn(0));
      MiniFace[] redCol = CUBE_UTILS.makeRowColCopy(otherSides[2].getColumn(2));
      MiniFace[] yellowSide = CUBE_UTILS.makeRowColCopy(otherSides[3].getColumn(0));

      // orange to white
      otherSides[1].setColumn(0, orangeCol);

      // white to red
      whiteCol = CUBE_UTILS.reverseRowCol(whiteCol);
      otherSides[2].setColumn(2, whiteCol);

      // red to yellow
      redCol = CUBE_UTILS.reverseRowCol(redCol);
      otherSides[3].setColumn(0, redCol);

      // yellow to orange
      otherSides[0].setColumn(0, yellowSide);
    }
  }

  void frontTurn(final Cube cube, final boolean clockwise, final int numberOfTurns) {
    int numberOfClockwiseTurns = adjustTurns(numberOfTurns, clockwise);

    Side[] otherSides = {
        cube.getBlueSide(), cube.getWhiteSide(), cube.getGreenSide(), cube.getYellowSide()
    };
    Side turningSide = cube.getOrangeSide();
    turningSide.rotateSide(numberOfClockwiseTurns);
    for (int turn = 0; turn < numberOfClockwiseTurns; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] blueCol = CUBE_UTILS.makeRowColCopy(otherSides[0].getColumn(0));
      MiniFace[] whiteRow = CUBE_UTILS.makeRowColCopy(otherSides[1].getRow(0));
      MiniFace[] greenCol = CUBE_UTILS.makeRowColCopy(otherSides[2].getColumn(2));
      MiniFace[] yellowCol = CUBE_UTILS.makeRowColCopy(otherSides[3].getRow(2));

      // put blue into white
      blueCol = CUBE_UTILS.reverseRowCol(blueCol);
      otherSides[1].setRow(0, blueCol);

      // putting white in to green
      otherSides[2].setColumn(2, whiteRow);

      // green in to yellow
      greenCol = CUBE_UTILS.reverseRowCol(greenCol);
      otherSides[3].setRow(2, greenCol);

      otherSides[0].setColumn(0, yellowCol);
    }
  }

  void backTurn(final Cube cube, final boolean isClockwise, final int numberOfTurns) {
    int numberOfClockwiseTurns = adjustTurns(numberOfTurns, isClockwise);

    Side[] otherSides = {
        cube.getGreenSide(), cube.getWhiteSide(), cube.getBlueSide(), cube.getYellowSide()
    };
    Side turningSide = cube.getRedSide();
    turningSide.rotateSide(numberOfClockwiseTurns);
    for (int turn = 0; turn < numberOfClockwiseTurns; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] greenCol = CUBE_UTILS.makeRowColCopy(otherSides[0].getColumn(0));
      MiniFace[] whiteRow = CUBE_UTILS.makeRowColCopy(otherSides[1].getRow(2));
      MiniFace[] blueCol = CUBE_UTILS.makeRowColCopy(otherSides[2].getColumn(2));
      MiniFace[] yellowRow = CUBE_UTILS.makeRowColCopy(otherSides[3].getRow(0));

      // put green into white // no rev
      otherSides[1].setRow(2, greenCol);

      // putting white in to blue
      whiteRow = CUBE_UTILS.reverseRowCol(whiteRow);
      otherSides[2].setColumn(2, whiteRow);

      // blue in to yellow // no rev
      otherSides[3].setRow(0, blueCol);

      yellowRow = CUBE_UTILS.reverseRowCol(yellowRow);
      otherSides[0].setColumn(0, yellowRow);
    }
  }

  void upperTurn(final Cube cube, final boolean clockwise, final int numberOfTurns) {
    int numberOfClockwiseTurns = adjustTurns(numberOfTurns, clockwise);

    Side[] otherSides = {
        cube.getBlueSide(), cube.getOrangeSide(), cube.getGreenSide(), cube.getRedSide()
    };
    Side turningSide = cube.getYellowSide();
    turningSide.rotateSide(numberOfClockwiseTurns);
    for (int turn = 0; turn < numberOfClockwiseTurns; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] blueRow = CUBE_UTILS.makeRowColCopy(otherSides[0].getRow(0));
      MiniFace[] orangeRow = CUBE_UTILS.makeRowColCopy(otherSides[1].getRow(0));
      MiniFace[] greenRow = CUBE_UTILS.makeRowColCopy(otherSides[2].getRow(0));
      MiniFace[] redRow = CUBE_UTILS.makeRowColCopy(otherSides[3].getRow(0));

      otherSides[1].setRow(0, blueRow);

      // putting white in to orange no reverse
      otherSides[2].setRow(0, orangeRow);
      // putting orange in to yellow col2 to col2 no reverse
      otherSides[3].setRow(0, greenRow);
      // putting yellow in to red col2 - col 0 reverse

      otherSides[0].setRow(0, redRow);
    }
  }

  void downFaceTurn(final Cube cube, final boolean clockwise, final int numberOfTurns) {
    int numberOfClockwiseTurns = adjustTurns(numberOfTurns, clockwise);

    Side[] otherSides = {
        cube.getBlueSide(), cube.getRedSide(), cube.getGreenSide(), cube.getOrangeSide()
    };
    Side turningSide = cube.getWhiteSide();
    turningSide.rotateSide(numberOfClockwiseTurns);
    for (int turn = 0; turn < numberOfClockwiseTurns; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] blueRow = CUBE_UTILS.makeRowColCopy(otherSides[0].getRow(2));
      MiniFace[] redSide = CUBE_UTILS.makeRowColCopy(otherSides[1].getRow(2));
      MiniFace[] greenRow = CUBE_UTILS.makeRowColCopy(otherSides[2].getRow(2));
      MiniFace[] orangeSide = CUBE_UTILS.makeRowColCopy(otherSides[3].getRow(2));

      otherSides[1].setRow(2, blueRow);

      // putting white in to orange no reverse
      otherSides[2].setRow(2, redSide);
      // putting orange in to yellow col2 to col2 no reverse
      otherSides[3].setRow(2, greenRow);
      // putting yellow in to red col2 - col 0 reverse
      otherSides[0].setRow(2, orangeSide);
    }
  }

}
