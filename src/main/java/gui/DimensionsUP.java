package gui;

/**
 * builds the dimensions for all 27 visible squares in our  virtual cube - UP mode
 */
class DimensionsUP extends Dimensions {

    DimensionsUP() {
        cubeTopLeftx = 150;
        shift = 150;
        cubeTopLefty = 50;
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
