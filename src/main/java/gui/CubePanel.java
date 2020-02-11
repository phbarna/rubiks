package gui;

import common.Orientation;
import rubiks.Cube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class CubePanel extends JPanel implements MouseMotionListener, MouseListener {
    private static final int D_W = 800;
    private static final int D_H = 600;
    private char[] frontSide = new char[9];
    private char[] leftSide = new char[9];
    private char[] topSide = new char[9];
    private int previousX;
    private int previousY;
    private final Dimensions dimensions;
    private final Cube cube;

    private Orientation guiOrientation = Orientation.OY; // default;

    CubePanel(Cube cube) {
        this.cube = cube;
        dimensions = new Dimensions(); // default condition = facing up.
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        previousX = e.getX();
        previousY = e.getY();
    }

    String getOrientation() {
        return guiOrientation.toString();
    }

    void setOrientationForwardUP() {
        guiOrientation = Orientation.OY;
        String orientation = cube.getOrientationStrings(this.guiOrientation.toString());
        this.setStrings(orientation);
        this.repaint();
    }

    private void dragRight() {

        switch (guiOrientation) {
            case OY: {
                guiOrientation = Orientation.GY;
                break;
            }
            case GY: {
                guiOrientation = Orientation.RY;
                break;
            }
            case RY: {
                guiOrientation = Orientation.BY;
                break;
            }
            case BY: {
                guiOrientation = Orientation.OY;
                break;
            }
            case RG: {
                guiOrientation = Orientation.YG;
                break;
            }
            case WG: {
                guiOrientation = Orientation.RG;
                break;
            }
            case OG: {
                guiOrientation = Orientation.WG;
                break;
            }
            case YG: {
                guiOrientation = Orientation.OG;
                break;
            }
            case YR: {
                guiOrientation = Orientation.GR;
                break;
            }
            case BR: {
                guiOrientation = Orientation.YR;
                break;
            }
            case WR: {
                guiOrientation = Orientation.BR;
                break;
            }
            case GR: {
                guiOrientation = Orientation.WR;
                break;
            }
            case YB: {
                guiOrientation = Orientation.RB;
                break;
            }
            case OB: {
                guiOrientation = Orientation.YB;
                break;
            }
            case WB: {
                guiOrientation = Orientation.OB;
                break;
            }
            case RB: {
                guiOrientation = Orientation.WB;
                break;
            }
            case YO: {
                guiOrientation = Orientation.BO;
                break;
            }
            case GO: {
                guiOrientation = Orientation.YO;
                break;
            }
            case WO: {
                guiOrientation = Orientation.GO;
                break;
            }
            case BO: {
                guiOrientation = Orientation.WO;
                break;
            }
            case BW: {
                guiOrientation = Orientation.RW;
                break;
            }
            case OW: {
                guiOrientation = Orientation.BW;
                break;
            }
            case GW: {
                guiOrientation = Orientation.OW;
                break;
            }
            case RW: {
                guiOrientation = Orientation.GW;
                break;
            }

            default: {

                break;
            }
        }

        String orientation = cube.getOrientationStrings(this.guiOrientation.toString());
        this.setStrings(orientation);
        this.repaint();
    }

    private void dragLeft() {

        switch (guiOrientation) {
            case OY: {
                guiOrientation = Orientation.BY;
                break;
            }
            case GY: {
                guiOrientation = Orientation.OY;
                break;
            }
            case BY: {
                guiOrientation = Orientation.RY;
                break;
            }
            case RY: {
                guiOrientation = Orientation.GY;
                break;
            }
            case RG: {
                guiOrientation = Orientation.WG;
                break;
            }
            case WG: {
                guiOrientation = Orientation.OG;
                break;
            }
            case OG: {
                guiOrientation = Orientation.YG;
                break;
            }
            case YG: {
                guiOrientation = Orientation.RG;
                break;
            }
            case YR: {
                guiOrientation = Orientation.BR;
                break;
            }
            case BR: {
                guiOrientation = Orientation.WR;
                break;
            }
            case WR: {
                guiOrientation = Orientation.GR;
                break;
            }
            case GR: {
                guiOrientation = Orientation.YR;
                break;
            }
            case YB: {
                guiOrientation = Orientation.OB;
                break;
            }
            case OB: {
                guiOrientation = Orientation.WB;
                break;
            }
            case WB: {
                guiOrientation = Orientation.RB;
                break;
            }
            case RB: {
                guiOrientation = Orientation.YB;
                break;
            }
            case YO: {
                guiOrientation = Orientation.GO;
                break;
            }
            case GO: {
                guiOrientation = Orientation.WO;
                break;
            }
            case WO: {
                guiOrientation = Orientation.BO;
                break;
            }
            case BO: {
                guiOrientation = Orientation.YO;
                break;
            }
            case BW: {
                guiOrientation = Orientation.OW;
                break;
            }
            case OW: {
                guiOrientation = Orientation.GW;
                break;
            }
            case GW: {
                guiOrientation = Orientation.RW;
                break;
            }
            case RW: {
                guiOrientation = Orientation.BW;
                break;
            }

            default: {

                break;
            }
        }

        String test = cube.getOrientationStrings(this.guiOrientation.toString());
        this.setStrings(test);
        this.repaint();
    }

    private void dragDown() {

        switch (guiOrientation) {
            case OY: {
                guiOrientation = Orientation.YR;
                break;
            }
            case GY: {
                guiOrientation = Orientation.YB;
                break;
            }
            case BY: {
                guiOrientation = Orientation.YG;
                break;
            }
            case RY: {
                guiOrientation = Orientation.YO;
                break;
            }
            case RG: {
                guiOrientation = Orientation.GO;
                break;
            }
            case WG: {
                guiOrientation = Orientation.GY;
                break;
            }
            case OG: {
                guiOrientation = Orientation.GR;
                break;
            }
            case YG: {
                guiOrientation = Orientation.GW;
                break;
            }
            case YR: {
                guiOrientation = Orientation.RW;
                break;
            }
            case BR: {
                guiOrientation = Orientation.RG;
                break;
            }
            case WR: {
                guiOrientation = Orientation.RY;
                break;
            }
            case GR: {
                guiOrientation = Orientation.RB;
                break;
            }
            case YB: {
                guiOrientation = Orientation.BW;
                break;
            }
            case OB: {
                guiOrientation = Orientation.BR;
                break;
            }
            case WB: {
                guiOrientation = Orientation.BY;
                break;
            }
            case RB: {
                guiOrientation = Orientation.BO;
                break;
            }
            case YO: {
                guiOrientation = Orientation.OW;
                break;
            }
            case GO: {
                guiOrientation = Orientation.OB;
                break;
            }
            case WO: {
                guiOrientation = Orientation.OY;
                break;
            }
            case BO: {
                guiOrientation = Orientation.OG;
                break;
            }
            case BW: {
                guiOrientation = Orientation.WG;
                break;
            }
            case OW: {
                guiOrientation = Orientation.WR;
                break;
            }
            case GW: {
                guiOrientation = Orientation.WB;
                break;
            }
            case RW: {
                guiOrientation = Orientation.WO;
                break;
            }

            default: {

                break;
            }
        }

        String test = cube.getOrientationStrings(this.guiOrientation.toString());
        this.setStrings(test);
        this.repaint();
    }

    private void dragUp() {

        switch (guiOrientation) {
            case OY: {
                guiOrientation = Orientation.WO;
                break;
            }
            case GY: {
                guiOrientation = Orientation.WG;
                break;
            }
            case BY: {
                guiOrientation = Orientation.WB;
                break;
            }
            case RY: {
                guiOrientation = Orientation.WR;
                break;
            }
            case RG: {
                guiOrientation = Orientation.BR;
                break;
            }
            case WG: {
                guiOrientation = Orientation.BW;
                break;
            }
            case OG: {
                guiOrientation = Orientation.BO;
                break;
            }
            case YG: {
                guiOrientation = Orientation.BY;
                break;
            }
            case YR: {
                guiOrientation = Orientation.OY;
                break;
            }
            case BR: {
                guiOrientation = Orientation.OB;
                break;
            }
            case WR: {
                guiOrientation = Orientation.OW;
                break;
            }
            case GR: {
                guiOrientation = Orientation.OG;
                break;
            }
            case YB: {
                guiOrientation = Orientation.GY;
                break;
            }
            case OB: {
                guiOrientation = Orientation.GO;
                break;
            }
            case WB: {
                guiOrientation = Orientation.GW;
                break;
            }
            case RB: {
                guiOrientation = Orientation.GR;
                break;
            }
            case YO: {
                guiOrientation = Orientation.RY;
                break;
            }
            case GO: {
                guiOrientation = Orientation.RG;
                break;
            }
            case WO: {
                guiOrientation = Orientation.RW;
                break;
            }
            case BO: {
                guiOrientation = Orientation.RB;
                break;
            }
            case BW: {
                guiOrientation = Orientation.YB;
                break;
            }
            case OW: {
                guiOrientation = Orientation.YO;
                break;
            }
            case GW: {
                guiOrientation = Orientation.YG;
                break;
            }
            case RW: {
                guiOrientation = Orientation.YR;
                break;
            }

            default: {

                break;
            }
        }

        String test = cube.getOrientationStrings(this.guiOrientation.toString());
        this.setStrings(test);
        this.repaint();
    }

    /**
     * Mouse drag diagonal downwards
     */
    private void dragDiagDown() {
        switch (guiOrientation) {
            case OY: {
                guiOrientation = Orientation.YG;
                break;
            }
            case GY: {
                guiOrientation = Orientation.GO;
                break;
            }
            case BY: {
                guiOrientation = Orientation.BR;
                break;
            }
            case RY: {
                guiOrientation = Orientation.RG;
                break;
            }
            case RG: {
                guiOrientation = Orientation.RW;
                break;
            }
            case WG: {
                guiOrientation = Orientation.WO;
                break;
            }
            case OG: {
                guiOrientation = Orientation.OY;
                break;
            }
            case YG: {
                guiOrientation = Orientation.YR;
                break;
            }
            case YR: {
                guiOrientation = Orientation.YB;
                break;
            }
            case BR: {
                guiOrientation = Orientation.BW;
                break;
            }
            case WR: {
                guiOrientation = Orientation.WG;
                break;
            }
            case GR: {
                guiOrientation = Orientation.GY;
                break;
            }
            case YB: {
                guiOrientation = Orientation.YO;
                break;
            }
            case OB: {
                guiOrientation = Orientation.OW;
                break;
            }
            case WB: {
                guiOrientation = Orientation.WR;
                break;
            }
            case RB: {
                guiOrientation = Orientation.RY;
                break;
            }
            case YO: {
                guiOrientation = Orientation.YG;
                break;
            }
            case GO: {
                guiOrientation = Orientation.GW;
                break;
            }
            case WO: {
                guiOrientation = Orientation.WB;
                break;
            }
            case BO: {
                guiOrientation = Orientation.BY;
                break;
            }
            case BW: {
                guiOrientation = Orientation.BO;
                break;
            }
            case OW: {
                guiOrientation = Orientation.OG;
                break;
            }
            case GW: {
                guiOrientation = Orientation.GR;
                break;
            }
            case RW: {
                guiOrientation = Orientation.RB;
                break;
            }

            default: {

                break;
            }
        }

        String test = cube.getOrientationStrings(this.guiOrientation.toString());
        this.setStrings(test);
        this.repaint();
    }

    private void dragDiagUp() {

        switch (guiOrientation) {
            case OY: {
                guiOrientation = Orientation.OG;
                break;
            }
            case GY: {
                guiOrientation = Orientation.GR;
                break;
            }
            case BY: {
                guiOrientation = Orientation.BO;
                break;
            }
            case RY: {
                guiOrientation = Orientation.RB;
                break;
            }
            case RG: {
                guiOrientation = Orientation.RY;
                break;
            }
            case WG: {
                guiOrientation = Orientation.WR;
                break;
            }
            case OG: {
                guiOrientation = Orientation.OW;
                break;
            }
            case YG: {
                guiOrientation = Orientation.YO;
                break;
            }
            case YR: {
                guiOrientation = Orientation.YG;
                break;
            }
            case BR: {
                guiOrientation = Orientation.OB;
                break;
            }
            case WR: {
                guiOrientation = Orientation.BY;
                break;
            }
            case GR: {
                guiOrientation = Orientation.GW;
                break;
            }
            case YB: {
                guiOrientation = Orientation.YR;
                break;
            }
            case OB: {
                guiOrientation = Orientation.OY;
                break;
            }
            case WB: {
                guiOrientation = Orientation.WO;
                break;
            }
            case RB: {
                guiOrientation = Orientation.RW;
                break;
            }
            case YO: {
                guiOrientation = Orientation.YB;
                break;
            }
            case GO: {
                guiOrientation = Orientation.GY;
                break;
            }
            case WO: {
                guiOrientation = Orientation.WG;
                break;
            }
            case BO: {
                guiOrientation = Orientation.BW;
                break;
            }
            case BW: {
                guiOrientation = Orientation.BR;
                break;
            }
            case OW: {
                guiOrientation = Orientation.OB;
                break;
            }
            case GW: {
                guiOrientation = Orientation.GO;
                break;
            }
            case RW: {
                guiOrientation = Orientation.RG;
                break;
            }

            default: {

                break;
            }
        }

        String test = cube.getOrientationStrings(this.guiOrientation.toString());
        this.setStrings(test);
        this.repaint();
    }

    public void mouseDragged(MouseEvent e) {

        // use 3 imaginary x lines and 3 imaginary y lines as drag reference points

        int xLine1 = dimensions.getxLine1();
        int xLine2 = dimensions.getxLine2();
        int xLine3 = dimensions.getxLine3();
        int yLine1 = dimensions.getyLine1();
        int yLine2 = dimensions.getyLine2();
        int yLine3 = dimensions.getyLine3();

        int xLineDiagonal = (xLine1 + xLine2)/2;

        // see if we have dragged along one of these points

        Point p = e.getPoint();
        double getX = p.getX();

        double getY = p.getY();

        if (xLineDiagonal < getX && xLineDiagonal > previousX) {
            dragDiagUp();
        }
        if (xLineDiagonal > getX && xLineDiagonal < previousX) {
            dragDiagDown();
        }

        else if (xLine1 < getX && xLine1 > previousX) {
            dragRight();
        } else if (xLine1 > getX && xLine1 < previousX) {
            dragLeft();
        } else if (xLine2 < getX && xLine2 > previousX) {
            dragRight();
        } else if (xLine2 > getX && xLine2 < previousX) {
            dragLeft();
        } else if (xLine3 < getX && xLine3 > previousX) {
            dragRight();
        } else if (xLine3 > getX && xLine3 < previousX) {
            dragLeft();
        }

        else if (yLine1 < getY && yLine1 > previousY) {
            dragDown();

        } else if (yLine1 > getY && yLine1 < previousY) {
            dragUp();
        }
        else if (yLine2 < getY && yLine2 > previousY) {
            dragDown();

        } else if (yLine2 > getY && yLine2 < previousY) {
            dragUp();
        }

        else if (yLine3 < getY && yLine3 > previousY) {
            dragDown();

        } else if (yLine3 > getY && yLine3 < previousY) {
            dragUp();
        }

        previousX = (int) getX;
        previousY = (int) getY;

    }


    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {

    }

    private Color getColour(char c) {
        switch (c) {
            case 'b': {
                return Color.BLUE;
            }
            case 'o': {
                return Color.getHSBColor(20, 240, 120).brighter(); // looks more like orange :-)
            }
            case 'w': {
                return Color.WHITE;
            }
            case 'g': {
                return Color.GREEN;
            }
            case 'y': {
                return Color.YELLOW;
            }
            case 'r': {
                return Color.RED;
            }
        }
        return null; // not expected to get here - could throw an exception ?
    }

    protected void paintComponent(Graphics g) {
        paint(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(D_W, D_H);
    }

    void setStrings(String allSides) {
        String[] threeStrings = allSides.split("\n");
        frontSide = threeStrings[0].toCharArray();
        leftSide = threeStrings[1].toCharArray();
        topSide = threeStrings[2].toCharArray();

    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int[] x;
        int[] y;

        // do front
        int index = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                g.setColor(getColour(frontSide[index]));
                x = dimensions.getxPointsFrontSide()[c][r];
                y = dimensions.getyPointsFrontSide()[c][r];
                g.fillPolygon(x, y, 4);
                index++;
            }
        }

        // do left
        index = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                x = dimensions.getxPointsLeftSide()[c][r];
                y = dimensions.getyPointsLeftSide()[c][r];

                g.setColor(getColour(leftSide[index]));
                g.fillPolygon(x, y, 4);
                index++;
            }
        }

        // do top
        index = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                g.setColor(getColour(topSide[index]));

                x = dimensions.getxPointsTopSide()[c][r];
                y = dimensions.getyPointsTopSide()[c][r];

                g.fillPolygon(x, y, 4);
                index++;
            }
        }
        g.setColor(Color.black);
        for (int i = 0; i < 3; i++) {
            g.drawLine(dimensions.getxPointsLeftSide()[0][i][0],
                    dimensions.getyPointsLeftSide()[0][i][0],
                    dimensions.getxPointsLeftSide()[2][i][3],
                    dimensions.getyPointsLeftSide()[2][i][3]);
        }
        for (int i = 0; i < 3; i++) {
            g.drawLine(
                    dimensions.getxPointsLeftSide()[i][0][3],
                    dimensions.getyPointsLeftSide()[i][0][3],
                    dimensions.getxPointsLeftSide()[i][2][2],
                    dimensions.getyPointsLeftSide()[i][2][2]);
        }

        for (int i = 0; i < 3; i++) {
            g.drawLine(dimensions.getxPointsTopSide()[0][i][3],
                    dimensions.getyPointsTopSide()[0][i][3],
                    dimensions.getxPointsTopSide()[2][i][2],
                    dimensions.getyPointsTopSide()[2][i][2]);
        }

        for (int i = 0; i < 3; i++) {
            g.drawLine(dimensions.getxPointsTopSide()[i][0][1],
                    dimensions.getyPointsTopSide()[i][0][1],
                    dimensions.getxPointsTopSide()[i][2][2],
                    dimensions.getyPointsTopSide()[i][2][2]);
        }

        for (int i = 0; i < 3; i++) {
            g.drawLine(dimensions.getxPointsFrontSide()[0][i][3],
                    dimensions.getyPointsFrontSide()[0][i][3],
                    dimensions.getxPointsFrontSide()[2][i][2],
                    dimensions.getyPointsFrontSide()[2][i][2]);
        }

        for (int i = 0; i < 3; i++) {
            g.drawLine(dimensions.getxPointsFrontSide()[i][0][1],
                    dimensions.getyPointsFrontSide()[i][0][1],
                    dimensions.getxPointsFrontSide()[i][2][2],
                    dimensions.getyPointsFrontSide()[i][2][2]);
        }
    }
}