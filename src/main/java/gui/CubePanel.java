package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CubePanel extends JPanel implements MouseMotionListener, MouseListener {
    private static final int D_W = 800;
    private static final int D_H = 600;
    private boolean upsideDown = false;
    char[] frontSide = new char[9];
    char[] leftSide = new char[9];
    char[] topSide = new char[9];
    int previousX;
    int previousY;
    private String allSides = "";

    private Orientation orientation = Orientation.ORANGE_FRONT; // default;
    public CubePanel() {
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mousePressed(MouseEvent e) {
        previousX = e.getX();
        previousY = e.getY();

    }

    public void mouseReleased(MouseEvent e) {

    }

    private void dragLeft() {

        switch (orientation) {

            case ORANGE_FRONT: {// want to go to green front now
                orientation = Orientation.GREEN_FRONT;
                break;
            }
            case GREEN_FRONT: {// want to go to green front now
                orientation = Orientation.RED_FRONT;
                break;
            }
            case BLUE_FRONT: {// want to go to green front now
                orientation = Orientation.ORANGE_FRONT;
                break;
            }
            case RED_FRONT: {// want to go to green front now
                orientation = Orientation.BLUE_FRONT;
                break;
            }
            default: {
                orientation = Orientation.ORANGE_FRONT;
            }
        }
        this.setStrings(allSides);
        this.repaint();

    }

    private void dragRight() {
        switch (orientation) {

            case ORANGE_FRONT: {// want to go to green front now
                orientation = Orientation.BLUE_FRONT;
                break;
            }
            case GREEN_FRONT: {// want to go to green front now
                orientation = Orientation.ORANGE_FRONT;
                break;
            }
            case BLUE_FRONT: {// want to go to green front now
                orientation = Orientation.RED_FRONT;
                break;
            }
            case RED_FRONT: {// want to go to green front now
                orientation = Orientation.GREEN_FRONT;
                break;
            }
            default: {
                orientation = Orientation.ORANGE_FRONT;
            }
        }
        this.setStrings(allSides);
        this.repaint();
    }

    private void dragUp() {

        if (!orientation.equals(Orientation.WHITE_FRONT) && !orientation.equals(Orientation.YELLOW_FRONT)) {
            orientation = Orientation.YELLOW_FRONT;
        } else if (orientation.equals(Orientation.WHITE_FRONT)) {
            orientation = Orientation.ORANGE_FRONT;
        }
        this.setStrings(allSides);
        this.repaint();
    }

    private void dragDown() {
        if (!orientation.equals(Orientation.WHITE_FRONT) && !orientation.equals(Orientation.YELLOW_FRONT)) {
            orientation = Orientation.WHITE_FRONT;
        } else if (orientation.equals(Orientation.YELLOW_FRONT)) {
            orientation = Orientation.ORANGE_FRONT;
        }
        this.setStrings(allSides);
        this.repaint();
    }

    public void mouseDragged(MouseEvent e) {

        // use 3 imaginary x lines and 3 imaginary y lines as drag reference points
        DimensionsUP dimensions = new DimensionsUP();
        int xLine1 = dimensions.getxPointsLeftSide()[0][0][0];
        int xLine2 = dimensions.getxPointsFrontSide()[0][0][0];
        int xLine3 = dimensions.getxPointsFrontSide()[2][0][1];

        int yLine1 = dimensions.getyPointsFrontSide()[2][2][2];
        int yLine2 = dimensions.getyPointsFrontSide()[0][0][0];
        int yLine3 = dimensions.getyPointsTopSide()[2][0][0];

        // see if we have dragged along one of these points

        Point p = e.getPoint();
        double getX = p.getX();

        double getY = p.getY();


        if (xLine1 < getX && xLine1 > previousX) {
            dragLeft();
        } else if (xLine1 > getX && xLine1 < previousX) {
            dragRight();
        } else if (xLine2 < getX && xLine2 > previousX) {
            dragLeft();
        } else if (xLine2 > getX && xLine2 < previousX) {
            dragRight();
        } else if (xLine3 < getX && xLine3 > previousX) {
            dragLeft();
        } else if (xLine3 > getX && xLine3 < previousX) {
            dragRight();
        }

        if (yLine1 < getY && yLine1 > previousY) {
            dragUp();

        } else if (yLine1 > getY && yLine1 < previousY) {
            dragDown();
        }
        if (yLine2 < getY && yLine2 > previousY) {
            dragUp();

        } else if (yLine2 > getY && yLine2 < previousY) {
            dragDown();
        }

        if (yLine3 < getY && yLine3 > previousY) {
            dragUp();

        } else if (yLine3 > getY && yLine3 < previousY) {
            dragDown();
        }

        previousX = (int) getX;
        previousY = (int) getY;

    }


    public void mouseExited(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }


    public Color getColour(char c) {
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
        // super.paintComponent(g);
        paint(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(D_W, D_H);
    }

    public void setStrings(String allSides) {
        this.allSides = allSides;
        String[] sixSides = allSides.split("\n");


        for (String side : sixSides) {
            switch (orientation) {
                case ORANGE_FRONT: {
                    if (side.substring(4, 5).equals("o")) {
                        frontSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("y")) {
                        topSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("g")) {
                        leftSide = side.toCharArray();
                    }
                    break;
                }
                case RED_FRONT: {
                    if (side.substring(4, 5).equals("r")) {
                        frontSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("y")) {
                        topSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("b")) {
                        leftSide = side.toCharArray();
                    }
                    break;
                }
                case GREEN_FRONT: {
                    if (side.substring(4, 5).equals("g")) {
                        frontSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("y")) {
                        topSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("r")) {
                        leftSide = side.toCharArray();
                    }
                    break;
                }
                case WHITE_FRONT: {
                    if (side.substring(4, 5).equals("w")) {
                        frontSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("o")) {
                        topSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("g")) {
                        leftSide = side.toCharArray();
                    }
                    break;
                }
                case BLUE_FRONT: {
                    if (side.substring(4, 5).equals("b")) {
                        frontSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("y")) {
                        topSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("o")) {
                        leftSide = side.toCharArray();
                    }
                    break;
                }
                case YELLOW_FRONT: {
                    if (side.substring(4, 5).equals("y")) {
                        frontSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("r")) {
                        topSide = side.toCharArray();
                    } else if (side.substring(4, 5).equals("g")) {
                        leftSide = side.toCharArray();
                    }
                    break;
                }

            }
        }
    }

    public void paint(Graphics g) {
        DimensionsUP dimensions = new DimensionsUP();
        int[] x = null;
        int[] y = null;
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
                if (orientation.equals(Orientation.WHITE_FRONT)) {
                    x = dimensions.getxPointsLeftSide()[r][2 - c];
                    y = dimensions.getyPointsLeftSide()[r][2 - c];

                } else if (orientation.equals(Orientation.YELLOW_FRONT)) {
                    x = dimensions.getxPointsLeftSide()[2 - r][c];
                    y = dimensions.getyPointsLeftSide()[2 - r][c];

                } else {
                    x = dimensions.getxPointsLeftSide()[c][r];
                    y = dimensions.getyPointsLeftSide()[c][r];
                }
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

                if (orientation.equals(Orientation.ORANGE_FRONT)) {
                    x = dimensions.getxPointsTopSide()[c][r];
                    y = dimensions.getyPointsTopSide()[c][r];
                } else if (orientation.equals(Orientation.BLUE_FRONT)) {
                    x = dimensions.getxPointsTopSide()[2 - r][c];
                    y = dimensions.getyPointsTopSide()[2 - r][c];
                } else if (orientation.equals(Orientation.RED_FRONT)) {
                    x = dimensions.getxPointsTopSide()[2 - c][2 - r];
                    y = dimensions.getyPointsTopSide()[2 - c][2 - r];
                } else if (orientation.equals(Orientation.GREEN_FRONT)) {
                    x = dimensions.getxPointsTopSide()[r][2 - c];
                    y = dimensions.getyPointsTopSide()[r][2 - c];
                } else if (orientation.equals(Orientation.YELLOW_FRONT)) {
                    x = dimensions.getxPointsTopSide()[2 - c][2 - r];
                    y = dimensions.getyPointsTopSide()[2 - c][2 - r];
                } else {
                    x = dimensions.getxPointsTopSide()[c][r];
                    y = dimensions.getyPointsTopSide()[c][r];
                }
                g.fillPolygon(x, y, 4);
                index++;
            }
        }

        // draw joining lines to make cube look more like a rubiks cube
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