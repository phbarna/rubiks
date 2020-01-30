package gui;

/**
 * builds the dimensions for all 27 visible squares in our  virtual cube
 */
class Dimensions {

    private static int cubeTopLeftx = 150;
    private static int cubeTopLefty = 50;
    private static int shift = 150;
    private static int size = 300;
    private static int miniShift;
    private static int miniSize;

    // create arrays of the 9 squares on each visible side i.e. left, front, top - all with 4 polygon points
    static int[][][] xPointsLeftSide = new int[3][3][4];
    static int[][][] yPointsLeftSide = new int[3][3][4];

    static int[][][] xPointsTopSide = new int[3][3][4];
    static int[][][] yPointsTopSide = new int[3][3][4];

    static int[][][] xPointsFrontSide = new int[3][3][4];
    static int[][][] yPointsFrontSide = new int[3][3][4];

    static {
        miniShift = shift/3;
        miniSize = size/3;
    }

    // static block
    static {

        for (int r = 0; r < 3; r++) {

            for (int c = 0; c < 3; c++) {

                // left side coordinates
                xPointsLeftSide[c][r][0] = cubeTopLeftx + (miniSize*c)-(miniShift*c);
                xPointsLeftSide[c][r][1] = cubeTopLeftx + (miniSize*c)-(miniShift*c);
                xPointsLeftSide[c][r][2] = cubeTopLeftx+(miniSize-miniShift) +(miniShift*c);
                xPointsLeftSide[c][r][3] = cubeTopLeftx+(miniSize-miniShift)  +(miniShift*c);

                yPointsLeftSide[c][r][0] = cubeTopLefty+(miniSize*c)-(c*miniSize)+(c*miniShift) + (miniSize*r);
                yPointsLeftSide[c][r][1] = cubeTopLefty+miniSize +(c*miniShift)+ (miniSize*r);
                yPointsLeftSide[c][r][2] = cubeTopLefty+miniSize+miniShift +(c*miniShift)+ (miniSize*r);
                yPointsLeftSide[c][r][3] = cubeTopLefty +miniShift +(c*miniShift)+ (miniSize*r);

                // top side coordinates
                xPointsTopSide[c][r][0] = cubeTopLeftx + (miniSize*c) + (miniShift*r);
                xPointsTopSide[c][r][1] = cubeTopLeftx + (miniSize*c) +miniSize + (miniShift*r);
                xPointsTopSide[c][r][2] = cubeTopLeftx + (miniSize*c) + (miniSize) +miniShift + (miniShift*r);
                xPointsTopSide[c][r][3] = cubeTopLeftx + (miniSize*c) +miniShift + (miniShift*r);

                yPointsTopSide[c][r][0] = cubeTopLefty + (miniShift*r);
                yPointsTopSide[c][r][1] = cubeTopLefty+ (miniShift*r);
                yPointsTopSide[c][r][2] = cubeTopLefty+miniShift+ (miniShift*r);
                yPointsTopSide[c][r][3] = cubeTopLefty +miniShift+ (miniShift*r);


               // g.drawRect(x + shift, y + shift, size, size);
                xPointsFrontSide[c][r][0] = cubeTopLeftx+shift + (miniSize*c);
                xPointsFrontSide[c][r][1] = cubeTopLeftx+shift +miniSize+ (miniSize*c);
                xPointsFrontSide[c][r][2] =cubeTopLeftx+shift +miniSize+ (miniSize*c);
                xPointsFrontSide[c][r][3] = cubeTopLeftx+shift+ (miniSize*c);

                yPointsFrontSide[c][r][0] =cubeTopLefty+shift + (miniSize*r);
                yPointsFrontSide[c][r][1] =cubeTopLefty+shift + (miniSize*r);
                yPointsFrontSide[c][r][2] =cubeTopLefty+shift + miniSize + (miniSize*r);
                yPointsFrontSide[c][r][3] =cubeTopLefty+shift + miniSize+ (miniSize*r);

            }

        }
    }


}
