package gui;

class Dimensions {
    int size = 300;
    int cubeTopLeftx;
    int cubeTopLefty;
    int shift;
    int miniShift;
    int miniSize;
    int[][][] yPointsFrontSide = new int[3][3][4];
    int[][][] xPointsLeftSide = new int[3][3][4];
    int[][][] yPointsLeftSide = new int[3][3][4];
    int[][][] xPointsTopSide = new int[3][3][4];
    int[][][] yPointsTopSide = new int[3][3][4];
    int[][][] xPointsFrontSide = new int[3][3][4];
    int xLine1;
    int xLine2;
    int xLine3;
    int yLine1;
    int yLine2;
    int yLine3;

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

}