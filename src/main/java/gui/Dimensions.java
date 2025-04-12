package gui;

/**
 * builds the dimensions for all 27 visible squares in our virtual cube - UP
 * mode
 */
class Dimensions {

  private final int[][][] yPointsFrontSide = new int[3][3][4];
  private final int[][][] xPointsLeftSide = new int[3][3][4];
  private final int[][][] yPointsLeftSide = new int[3][3][4];
  private final int[][][] xPointsTopSide = new int[3][3][4];
  private final int[][][] yPointsTopSide = new int[3][3][4];
  private final int[][][] xPointsFrontSide = new int[3][3][4];

  Dimensions() {
    int cubeTopLeftX;
    int cubeTopLeftY;
    int shift;
    int miniShift;
    int miniSize;
    int size = 200;
    cubeTopLeftX = 280;
    shift = 100;
    cubeTopLeftY = 37;
    miniShift = shift / 3;
    miniSize = size / 3;
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        // left side coordinates
        int leftSideFarVertical = cubeTopLeftX + (miniSize * c) - (miniShift * c);
        xPointsLeftSide[c][r][0] = leftSideFarVertical;
        xPointsLeftSide[c][r][1] = leftSideFarVertical;
        int leftSideNearVertical = cubeTopLeftX + (miniSize - miniShift) + (miniShift * c);
        xPointsLeftSide[c][r][2] = leftSideNearVertical;
        xPointsLeftSide[c][r][3] = leftSideNearVertical;
        yPointsLeftSide[c][r][0] = cubeTopLeftY + (miniSize * c) - (c * miniSize) + (c * miniShift) + (miniSize * r);
        yPointsLeftSide[c][r][1] = cubeTopLeftY + miniSize + (c * miniShift) + (miniSize * r);
        yPointsLeftSide[c][r][2] = cubeTopLeftY + miniSize + miniShift + (c * miniShift) + (miniSize * r);
        yPointsLeftSide[c][r][3] = cubeTopLeftY + miniShift + (c * miniShift) + (miniSize * r);

        // top side coordinates
        xPointsTopSide[c][r][0] = cubeTopLeftX + (miniSize * c) + (miniShift * r);
        xPointsTopSide[c][r][1] = cubeTopLeftX + (miniSize * c) + miniSize + (miniShift * r);
        xPointsTopSide[c][r][2] = cubeTopLeftX + (miniSize * c) + (miniSize) + miniShift + (miniShift * r);
        xPointsTopSide[c][r][3] = cubeTopLeftX + (miniSize * c) + miniShift + (miniShift * r);
        yPointsTopSide[c][r][0] = cubeTopLeftY + (miniShift * r);
        yPointsTopSide[c][r][1] = cubeTopLeftY + (miniShift * r);
        yPointsTopSide[c][r][2] = cubeTopLeftY + miniShift + (miniShift * r);
        yPointsTopSide[c][r][3] = cubeTopLeftY + miniShift + (miniShift * r);

        // front side coordinates
        xPointsFrontSide[c][r][0] = cubeTopLeftX + shift + (miniSize * c);
        xPointsFrontSide[c][r][1] = cubeTopLeftX + shift + miniSize + (miniSize * c);
        xPointsFrontSide[c][r][2] = cubeTopLeftX + shift + miniSize + (miniSize * c);
        xPointsFrontSide[c][r][3] = cubeTopLeftX + shift + (miniSize * c);
        yPointsFrontSide[c][r][0] = cubeTopLeftY + shift + (miniSize * r);
        yPointsFrontSide[c][r][1] = cubeTopLeftY + shift + (miniSize * r);
        yPointsFrontSide[c][r][2] = cubeTopLeftY + shift + miniSize + (miniSize * r);
        yPointsFrontSide[c][r][3] = cubeTopLeftY + shift + miniSize + (miniSize * r);
      }
    }
  }

  int[][][] getYPointsLeftSide() {
    return yPointsLeftSide;
  }

  int[][][] getXPointsLeftSide() {
    return xPointsLeftSide;
  }

  // create arrays of the 9 squares on each visible side i.e. left, front, top -
  // all with 4 polygon points
  int[][][] getXPointsTopSide() {
    return xPointsTopSide;
  }

  int[][][] getYPointsTopSide() {
    return yPointsTopSide;
  }

  int[][][] getXPointsFrontSide() {
    return xPointsFrontSide;
  }

  int[][][] getYPointsFrontSide() {
    return yPointsFrontSide;
  }
}
