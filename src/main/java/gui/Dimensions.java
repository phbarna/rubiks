package gui;

/**
 * builds the dimensions for all 27 visible squares in our  virtual cube
 */
class Dimensions {

    static int cubeTopLeftx = 150;
    static int cubeTopLefty = 50;
    static int shift = 150;
    static int size = 300;
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

    // static block for left
    static {


        for (int r = 0; r < 3; r++) {

            for (int c = 0; c < 3; c++) {

                xPointsLeftSide[c][r][0] = cubeTopLeftx + (miniSize*c);
                xPointsLeftSide[c][r][1] = cubeTopLeftx + (miniSize*c);
                xPointsLeftSide[c][r][2] = cubeTopLeftx+(miniSize-miniShift) ;
                xPointsLeftSide[c][r][3] = cubeTopLeftx+(miniSize-miniShift) ;



                yPointsLeftSide[c][r][0] = cubeTopLefty+(miniSize*c);
                yPointsLeftSide[c][r][1] = cubeTopLefty+miniSize +(miniShift*r);
                yPointsLeftSide[c][r][2] = cubeTopLefty+miniSize+miniShift + (miniSize*r) ;
                yPointsLeftSide[c][r][3] = cubeTopLefty +miniShift+ (miniSize*r);
                int x = 0;


                System.out.println("x for " + r + " "+c);
                System.out.println(xPointsLeftSide[c][r][0] + " "+
                        xPointsLeftSide[c][r][1] +" " +
                        + xPointsLeftSide[c][r][2] + " "+
                        + xPointsLeftSide[c][r][3]);

                System.out.println("y for " + r + " "+c);
                System.out.println(yPointsLeftSide[c][r][0] + " "+
                        yPointsLeftSide[c][r][1] +" " +
                        + yPointsLeftSide[c][r][2] + " "+
                        + yPointsLeftSide[c][r][3]);

                System.out.println();
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
