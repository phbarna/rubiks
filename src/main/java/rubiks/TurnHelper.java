package rubiks;

/**
 * Manages the cube turns. We had to have individual methods for right, left,
 * upper and front down and back
 * as the interactions with the cube are too different to make one generic turn
 * method
 */
class TurnHelper {
  private final CubeUtils cubeUtils = new CubeUtils();

  private int adjustTurns(int numberOfTimes, boolean clockwise) {
    numberOfTimes = numberOfTimes % 4; // just in case a value higher than 4 gets put in
    if (!clockwise) { // convert to clockwise
      numberOfTimes = 4 - numberOfTimes; // convert everything to clockwise
      numberOfTimes = numberOfTimes % 4; // need to modulus it again
    }
    return numberOfTimes;
  }

  void rightTurn(Cube cube, boolean clockwise, int numberOfTimes) {
    numberOfTimes = adjustTurns(numberOfTimes, clockwise);

    Side[] otherSides = { cube.getRedSide(), cube.getWhiteSide(), cube.getOrangeSide(), cube.getYellowSide() };
    Side turningSide = cube.getBlueSide();
    turningSide.rotateSide(numberOfTimes);
    for (int turn = 0; turn < numberOfTimes; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] rowCol1 = cubeUtils.makeRowColCopy(otherSides[0].getColumn(0));
      MiniFace[] rowCol2 = cubeUtils.makeRowColCopy(otherSides[1].getColumn(2));
      MiniFace[] rowCol3 = cubeUtils.makeRowColCopy(otherSides[2].getColumn(2));
      MiniFace[] rowCol4 = cubeUtils.makeRowColCopy(otherSides[3].getColumn(2));
      rowCol1 = cubeUtils.reverseRowCol(rowCol1);
      otherSides[1].setColumn(2, rowCol1);
      // putting white in to orange no reverse
      otherSides[2].setColumn(2, rowCol2);
      // putting orange in to yellow col2 to col2 no reverse
      otherSides[3].setColumn(2, rowCol3);
      // putting yellow in to red col2 - col 0 reverse
      rowCol4 = cubeUtils.reverseRowCol(rowCol4);
      otherSides[0].setColumn(0, rowCol4);
    }
  }

  void leftTurn(Cube cube, boolean clockwise, int numberOfTimes) {
    numberOfTimes = adjustTurns(numberOfTimes, clockwise);

    Side[] otherSides = { cube.getOrangeSide(), cube.getWhiteSide(), cube.getRedSide(), cube.getYellowSide() };
    Side turningSide = cube.getGreenSide();
    turningSide.rotateSide(numberOfTimes);
    for (int turn = 0; turn < numberOfTimes; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] orangeCol = cubeUtils.makeRowColCopy(otherSides[0].getColumn(0));
      MiniFace[] whiteCol = cubeUtils.makeRowColCopy(otherSides[1].getColumn(0));
      MiniFace[] redCol = cubeUtils.makeRowColCopy(otherSides[2].getColumn(2));
      MiniFace[] yellowSide = cubeUtils.makeRowColCopy(otherSides[3].getColumn(0));

      // orange to white
      otherSides[1].setColumn(0, orangeCol);

      // white to red
      whiteCol = cubeUtils.reverseRowCol(whiteCol);
      otherSides[2].setColumn(2, whiteCol);

      // red to yellow
      redCol = cubeUtils.reverseRowCol(redCol);
      otherSides[3].setColumn(0, redCol);

      // yellow to orange
      otherSides[0].setColumn(0, yellowSide);
    }
  }

  void frontTurn(Cube cube, boolean clockwise, int numberOfTimes) {
    numberOfTimes = adjustTurns(numberOfTimes, clockwise);

    Side[] otherSides = { cube.getBlueSide(), cube.getWhiteSide(), cube.getGreenSide(), cube.getYellowSide() };
    Side turningSide = cube.getOrangeSide();
    turningSide.rotateSide(numberOfTimes);
    for (int turn = 0; turn < numberOfTimes; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] blueCol = cubeUtils.makeRowColCopy(otherSides[0].getColumn(0));
      MiniFace[] whiteRow = cubeUtils.makeRowColCopy(otherSides[1].getRow(0));
      MiniFace[] greenCol = cubeUtils.makeRowColCopy(otherSides[2].getColumn(2));
      MiniFace[] yellowCol = cubeUtils.makeRowColCopy(otherSides[3].getRow(2));

      // put blue into white
      blueCol = cubeUtils.reverseRowCol(blueCol);
      otherSides[1].setRow(0, blueCol);

      // putting white in to green
      otherSides[2].setColumn(2, whiteRow);

      // green in to yellow
      greenCol = cubeUtils.reverseRowCol(greenCol);
      otherSides[3].setRow(2, greenCol);

      otherSides[0].setColumn(0, yellowCol);
    }
  }

  void backTurn(Cube cube, boolean clockwise, int numberOfTimes) {
    numberOfTimes = adjustTurns(numberOfTimes, clockwise);

    Side[] otherSides = { cube.getGreenSide(), cube.getWhiteSide(), cube.getBlueSide(), cube.getYellowSide() };
    Side turningSide = cube.getRedSide();
    turningSide.rotateSide(numberOfTimes);
    for (int turn = 0; turn < numberOfTimes; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] greenCol = cubeUtils.makeRowColCopy(otherSides[0].getColumn(0));
      MiniFace[] whiteRow = cubeUtils.makeRowColCopy(otherSides[1].getRow(2));
      MiniFace[] blueCol = cubeUtils.makeRowColCopy(otherSides[2].getColumn(2));
      MiniFace[] yellowRow = cubeUtils.makeRowColCopy(otherSides[3].getRow(0));

      // put green into white // no rev
      otherSides[1].setRow(2, greenCol);

      // putting white in to blue
      whiteRow = cubeUtils.reverseRowCol(whiteRow);
      otherSides[2].setColumn(2, whiteRow);

      // blue in to yellow // no rev
      otherSides[3].setRow(0, blueCol);

      yellowRow = cubeUtils.reverseRowCol(yellowRow);
      otherSides[0].setColumn(0, yellowRow);
    }
  }

  void upperTurn(Cube cube, boolean clockwise, int numberOfTimes) {
    numberOfTimes = adjustTurns(numberOfTimes, clockwise);

    Side[] otherSides = { cube.getBlueSide(), cube.getOrangeSide(), cube.getGreenSide(), cube.getRedSide() };
    Side turningSide = cube.getYellowSide();
    turningSide.rotateSide(numberOfTimes);
    for (int turn = 0; turn < numberOfTimes; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] blueRow = cubeUtils.makeRowColCopy(otherSides[0].getRow(0));
      MiniFace[] orangeRow = cubeUtils.makeRowColCopy(otherSides[1].getRow(0));
      MiniFace[] greenRow = cubeUtils.makeRowColCopy(otherSides[2].getRow(0));
      MiniFace[] redRow = cubeUtils.makeRowColCopy(otherSides[3].getRow(0));

      otherSides[1].setRow(0, blueRow);

      // putting white in to orange no reverse
      otherSides[2].setRow(0, orangeRow);
      // putting orange in to yellow col2 to col2 no reverse
      otherSides[3].setRow(0, greenRow);
      // putting yellow in to red col2 - col 0 reverse

      otherSides[0].setRow(0, redRow);
    }
  }

  void downFaceTurn(Cube cube, boolean clockwise, int numberOfTimes) {
    numberOfTimes = adjustTurns(numberOfTimes, clockwise);

    Side[] otherSides = { cube.getBlueSide(), cube.getRedSide(), cube.getGreenSide(), cube.getOrangeSide() };
    Side turningSide = cube.getWhiteSide();
    turningSide.rotateSide(numberOfTimes);
    for (int turn = 0; turn < numberOfTimes; turn++) {
      // make safe copies of all the original rows/cols to move
      MiniFace[] blueRow = cubeUtils.makeRowColCopy(otherSides[0].getRow(2));
      MiniFace[] redSide = cubeUtils.makeRowColCopy(otherSides[1].getRow(2));
      MiniFace[] greenRow = cubeUtils.makeRowColCopy(otherSides[2].getRow(2));
      MiniFace[] orangeSide = cubeUtils.makeRowColCopy(otherSides[3].getRow(2));

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
