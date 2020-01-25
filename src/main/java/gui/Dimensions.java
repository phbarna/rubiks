package gui;

/**
 * builds the dimensions for all 27 visible squares in our  virtual cube
 */
public class Dimensions {

    public static int cubeTopLeftx = 150;
    public static int cubeTopLefty = 50;
    public static int shift = 150;
    public static int size = 300;
    private static int miniShift = 0;
    private static int miniSize = 0;

    // create arrays of the 9 squares on each visible side i.e. left, front, top - all with 4 polygon points
    public static int[][][] xPointsLeftSide = new int[3][3][4];
    public static int[][][] yPointsLeftSide = new int[3][3][4];

    public static int[][][] xPointsTopSide = new int[3][3][4];
    public static int[][][] yPointsTopSide = new int[3][3][4];

    public static int[][][] xPointsFrontSide = new int[3][3][4];
    public static int[][][] yPointsFrontSide = new int[3][3][4];

    static {
        miniShift = shift/3;
        miniSize = size/3;
    }

    // static block for left
    static {





        for (int c = 0; c < 3; c++) {

            for (int r = 0; r < 3; r++) {




                xPointsLeftSide[c][r][0] = cubeTopLeftx;
                xPointsLeftSide[c][r][1] = cubeTopLeftx;
                xPointsLeftSide[c][r][2] = cubeTopLeftx+(miniSize-miniShift);
                xPointsLeftSide[c][r][3] = cubeTopLeftx+miniSize-miniShift;

                yPointsLeftSide[c][r][0] = cubeTopLefty;
                yPointsLeftSide[c][r][1] = cubeTopLefty+miniSize;
                yPointsLeftSide[c][r][2] = cubeTopLefty+miniSize+miniShift;
                yPointsLeftSide[c][r][3] = cubeTopLefty +miniShift;

            }


//        int[] xPoints = { cubeTwoPoints[0].x,
//                cubeTwoPoints[0].x,
//
//                cubeTwoPoints[0].x-(size/3)+miniShift,
//                cubeTwoPoints[0].x-(size/3)+miniShift};
//
//        int[] yPoints = { cubeTwoPoints[0].y,
//                cubeTwoPoints[0].y+(size/3),
//
//                cubeTwoPoints[0].y +miniShift,
//                cubeTwoPoints[0].y -(size/3) + miniShift};


        }
    }


}
