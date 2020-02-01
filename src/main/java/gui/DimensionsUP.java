package gui;

/**
 * builds the dimensions for all 27 visible squares in our  virtual cube
 */
class DimensionsUP {

    private int cubeTopLeftx = 150;
    private int cubeTopLefty = 50;
    private int shift = 150;
    private int size = 300;
    private int miniShift;
    private int miniSize;

    int[][][] yPointsFrontSide = new int[3][3][4];
    int[][][] xPointsLeftSide = new int[3][3][4];

    int[][][] yPointsLeftSide = new int[3][3][4];
    int[][][] xPointsTopSide = new int[3][3][4];
    int[][][] yPointsTopSide = new int[3][3][4];
    int[][][] xPointsFrontSide = new int[3][3][4];


    public int[][][] getyPointsLeftSide() {
        return yPointsLeftSide;
    }

    public int[][][] getxPointsLeftSide() {
        return xPointsLeftSide;
    }

    public int getCubeTopLeftx() {
        return cubeTopLeftx;
    }

    // create arrays of the 9 squares on each visible side i.e. left, front, top - all with 4 polygon points
    public int[][][] getxPointsTopSide() {
        return xPointsTopSide;
    }

    public int[][][] getyPointsTopSide() {
        return yPointsTopSide;
    }

    public int[][][] getxPointsFrontSide() {
        return xPointsFrontSide;
    }

    public int[][][] getyPointsFrontSide() {
        return yPointsFrontSide;
    }

    public DimensionsUP() {
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

            }
        }


    }


}
