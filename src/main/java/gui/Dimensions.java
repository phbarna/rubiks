package gui;

/**
 * builds the dimensions for all 27 visible squares in our  virtual cube - UP mode
 */
class Dimensions {

    private final int[][][] yPointsFrontSide = new int[3][3][4];
    private final int[][][] xPointsLeftSide = new int[3][3][4];
    private final int[][][] yPointsLeftSide = new int[3][3][4];
    private final int[][][] xPointsTopSide = new int[3][3][4];
    private final int[][][] yPointsTopSide = new int[3][3][4];
    private final int[][][] xPointsFrontSide = new int[3][3][4];
    private int xLine1;
    private int xLine2;
    private int xLine3;
    private int yLine1;
    private int yLine2;
    private int yLine3;

    int getxLine1() {
        return xLine1;
    }

    int getxLine2() {
        return xLine2;
    }

    int getxLine3() {
        return xLine3;
    }

    int getyLine1() {
        return yLine1;
    }

    int getyLine2() {
        return yLine2;
    }

    int getyLine3() {
        return yLine3;
    }

    int[][][] getyPointsLeftSide() {
        return yPointsLeftSide;
    }

    int[][][] getxPointsLeftSide() {
        return xPointsLeftSide;
    }

    // create arrays of the 9 squares on each visible side i.e. left, front, top - all with 4 polygon points
    int[][][] getxPointsTopSide() {
        return xPointsTopSide;
    }

    int[][][] getyPointsTopSide() {
        return yPointsTopSide;
    }

    int[][][] getxPointsFrontSide() {
        return xPointsFrontSide;
    }

    int[][][] getyPointsFrontSide() {
        return yPointsFrontSide;
    }


    Dimensions() {
        int cubeTopLeftx;
        int cubeTopLefty;
        int shift;
        int miniShift;
        int miniSize;
        int size = 200;
        cubeTopLeftx = 280;
        shift = 100;
        cubeTopLefty = 37;
        miniShift = shift / 3;
        miniSize = size / 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // left side coordinates
                xPointsLeftSide[c][r][0] = cubeTopLeftx + (miniSize * c) - (miniShift * c);
                xPointsLeftSide[c][r][1] = cubeTopLeftx + (miniSize * c) - (miniShift * c);
                xPointsLeftSide[c][r][2] = cubeTopLeftx + (miniSize - miniShift) + (miniShift * c);
                xPointsLeftSide[c][r][3] = cubeTopLeftx + (miniSize - miniShift) + (miniShift * c);

                yPointsLeftSide[c][r][0] = cubeTopLefty + (miniSize * c) - (c * miniSize) + (c * miniShift) + (miniSize * r);
                yPointsLeftSide[c][r][1] = cubeTopLefty + miniSize + (c * miniShift) + (miniSize * r);
                yPointsLeftSide[c][r][2] = cubeTopLefty + miniSize + miniShift + (c * miniShift) + (miniSize * r);
                yPointsLeftSide[c][r][3] = cubeTopLefty + miniShift + (c * miniShift) + (miniSize * r);

                // top side coordinates
                xPointsTopSide[c][r][0] = cubeTopLeftx + (miniSize * c) + (miniShift * r);
                xPointsTopSide[c][r][1] = cubeTopLeftx + (miniSize * c) + miniSize + (miniShift * r);
                xPointsTopSide[c][r][2] = cubeTopLeftx + (miniSize * c) + (miniSize) + miniShift + (miniShift * r);
                xPointsTopSide[c][r][3] = cubeTopLeftx + (miniSize * c) + miniShift + (miniShift * r);

                yPointsTopSide[c][r][0] = cubeTopLefty + (miniShift * r);
                yPointsTopSide[c][r][1] = cubeTopLefty + (miniShift * r);
                yPointsTopSide[c][r][2] = cubeTopLefty + miniShift + (miniShift * r);
                yPointsTopSide[c][r][3] = cubeTopLefty + miniShift + (miniShift * r);

                // g.drawRect(x + shift, y + shift, size, size);
                xPointsFrontSide[c][r][0] = cubeTopLeftx + shift + (miniSize * c);
                xPointsFrontSide[c][r][1] = cubeTopLeftx + shift + miniSize + (miniSize * c);
                xPointsFrontSide[c][r][2] = cubeTopLeftx + shift + miniSize + (miniSize * c);
                xPointsFrontSide[c][r][3] = cubeTopLeftx + shift + (miniSize * c);

                yPointsFrontSide[c][r][0] = cubeTopLefty + shift + (miniSize * r);
                yPointsFrontSide[c][r][1] = cubeTopLefty + shift + (miniSize * r);
                yPointsFrontSide[c][r][2] = cubeTopLefty + shift + miniSize + (miniSize * r);
                yPointsFrontSide[c][r][3] = cubeTopLefty + shift + miniSize + (miniSize * r);
                // set lines for dragging over
                xLine1 = this.getxPointsLeftSide()[0][0][0];
                xLine2 = this.getxPointsFrontSide()[0][0][0];
                xLine3 = this.getxPointsFrontSide()[2][0][1];
                yLine1 = this.getyPointsFrontSide()[2][2][2];
                yLine2 = this.getyPointsFrontSide()[0][0][0];
                yLine3 = this.getyPointsTopSide()[2][0][0];
            }
        }
    }

}
