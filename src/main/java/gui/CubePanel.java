package gui;

import common.Orientation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import rubiks.Cube;
import java.awt.Cursor;

class CubePanel extends JPanel implements MouseListener {
  private final int dw;
  private final int dh;
  private final transient Dimensions dimensions;
  private final transient Cube cube;
  private char[] frontSide = new char[9];
  private char[] leftSide = new char[9];
  private char[] topSide = new char[9];
  private int previousX;
  private int previousY;
  private boolean dragTip = false; // flag to stop drag dialog coming up more than once

  private Orientation guiOrientation = Orientation.OY;

  CubePanel(final Cube cube, final int width, final int height) {
    this.dw = width;
    this.dh = height;
    this.cube = cube;
    dimensions = new Dimensions(); // default condition = facing up.
    addMouseListener(this);
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    int pixelTolerance = 10;
    int currentX = e.getX();
    int currentY = e.getY();
    boolean xDragRight = false;
    boolean yDragDown = false;
    boolean xDragLeft = false;
    boolean yDragUp = false;

    if (currentX > previousX + pixelTolerance) {
      xDragRight = true;
    } else if (currentX < previousX - pixelTolerance) {
      xDragLeft = true;
    }
    if (currentY > previousY + pixelTolerance) {
      yDragDown = true;
    } else if (currentY < previousY - pixelTolerance) {
      yDragUp = true;
    }

    if (xDragRight && yDragUp) {
      dragDiagUp();
    } else if (xDragRight) {
      dragRight();
    } else if (xDragLeft && yDragDown) {
      dragDiagDown();
    } else if (xDragLeft) {
      dragLeft();
    } else if (yDragUp) {
      dragUp();
    } else if (yDragDown) {
      dragDown();
    } else {
      if (!dragTip) { // stops dialog coming up more than once which may irritate user
        dragTip = true;
        JOptionPane pane = new JOptionPane("Tip - drag mouse to rotate cube",
            JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(this, "Title");
        dialog.setModal(false);
        dialog.setVisible(true);

        pane.setVisible(true);

        new Timer(2500, e1 -> dialog.setVisible(false)).start();
      }
    }
    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    // implemented method not used
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    // implemented method not used
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    previousX = e.getX();
    previousY = e.getY();
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    // method not used
  }

  String getOrientation() {
    return guiOrientation.toString();
  }

  void setOrientationForwardUP() {
    guiOrientation = Orientation.OY;
    String orientation = cube.getOrientationStrings(guiOrientation.toString());
    this.setStrings(orientation);
    this.repaint();
  }

  void setGuiOrientation(final Orientation orientationIn) {
    guiOrientation = orientationIn;
    String orientation = cube.getOrientationStrings(guiOrientation.toString());
    this.setStrings(orientation);
    this.repaint();
  }

  private void dragRight() {

    switch (guiOrientation) {
      case OY:
        guiOrientation = Orientation.GY;
        break;
      case GY:
        guiOrientation = Orientation.RY;
        break;
      case RY:
        guiOrientation = Orientation.BY;
        break;
      case BY:
        guiOrientation = Orientation.OY;
        break;
      case RG:
        guiOrientation = Orientation.YG;
        break;
      case WG:
        guiOrientation = Orientation.RG;
        break;
      case OG:
        guiOrientation = Orientation.WG;
        break;
      case YG:
        guiOrientation = Orientation.OG;
        break;
      case YR:
        guiOrientation = Orientation.GR;
        break;
      case BR:
        guiOrientation = Orientation.YR;
        break;
      case WR:
        guiOrientation = Orientation.BR;
        break;
      case GR:
        guiOrientation = Orientation.WR;
        break;
      case YB:
        guiOrientation = Orientation.RB;
        break;
      case OB:
        guiOrientation = Orientation.YB;
        break;
      case WB:
        guiOrientation = Orientation.OB;
        break;
      case RB:
        guiOrientation = Orientation.WB;
        break;
      case YO:
        guiOrientation = Orientation.BO;
        break;
      case GO:
        guiOrientation = Orientation.YO;
        break;
      case WO:
        guiOrientation = Orientation.GO;
        break;
      case BO:
        guiOrientation = Orientation.WO;
        break;
      case BW:
        guiOrientation = Orientation.RW;
        break;
      case OW:
        guiOrientation = Orientation.BW;
        break;
      case GW:
        guiOrientation = Orientation.OW;
        break;
      case RW:
        guiOrientation = Orientation.GW;
        break;
      default:
        break;
    }

    String orientation = cube.getOrientationStrings(this.guiOrientation.toString());
    this.setStrings(orientation);
    this.repaint();
  }

  private void dragLeft() {

    switch (guiOrientation) {
      case OY:
        guiOrientation = Orientation.BY;
        break;
      case GY:
        guiOrientation = Orientation.OY;
        break;
      case BY:
        guiOrientation = Orientation.RY;
        break;
      case RY:
        guiOrientation = Orientation.GY;
        break;
      case RG:
        guiOrientation = Orientation.WG;
        break;
      case WG:
        guiOrientation = Orientation.OG;
        break;
      case OG:
        guiOrientation = Orientation.YG;
        break;
      case YG:
        guiOrientation = Orientation.RG;
        break;
      case YR:
        guiOrientation = Orientation.BR;
        break;
      case BR:
        guiOrientation = Orientation.WR;
        break;
      case WR:
        guiOrientation = Orientation.GR;
        break;
      case GR:
        guiOrientation = Orientation.YR;
        break;
      case YB:
        guiOrientation = Orientation.OB;
        break;
      case OB:
        guiOrientation = Orientation.WB;
        break;
      case WB:
        guiOrientation = Orientation.RB;
        break;
      case RB:
        guiOrientation = Orientation.YB;
        break;
      case YO:
        guiOrientation = Orientation.GO;
        break;
      case GO:
        guiOrientation = Orientation.WO;
        break;
      case WO:
        guiOrientation = Orientation.BO;
        break;
      case BO:
        guiOrientation = Orientation.YO;
        break;
      case BW:
        guiOrientation = Orientation.OW;
        break;
      case OW:
        guiOrientation = Orientation.GW;
        break;
      case GW:
        guiOrientation = Orientation.RW;
        break;
      case RW:
        guiOrientation = Orientation.BW;
        break;
      default:
        break;
    }

    String test = cube.getOrientationStrings(this.guiOrientation.toString());
    this.setStrings(test);
    this.repaint();
  }

  private void dragDown() {

    switch (guiOrientation) {
      case OY:
        guiOrientation = Orientation.YR;
        break;
      case GY:
        guiOrientation = Orientation.YB;
        break;
      case BY:
        guiOrientation = Orientation.YG;
        break;
      case RY:
        guiOrientation = Orientation.YO;
        break;
      case RG:
        guiOrientation = Orientation.GO;
        break;
      case WG:
        guiOrientation = Orientation.GY;
        break;
      case OG:
        guiOrientation = Orientation.GR;
        break;
      case YG:
        guiOrientation = Orientation.GW;
        break;
      case YR:
        guiOrientation = Orientation.RW;
        break;
      case BR:
        guiOrientation = Orientation.RG;
        break;
      case WR:
        guiOrientation = Orientation.RY;
        break;
      case GR:
        guiOrientation = Orientation.RB;
        break;
      case YB:
        guiOrientation = Orientation.BW;
        break;
      case OB:
        guiOrientation = Orientation.BR;
        break;
      case WB:
        guiOrientation = Orientation.BY;
        break;
      case RB:
        guiOrientation = Orientation.BO;
        break;
      case YO:
        guiOrientation = Orientation.OW;
        break;
      case GO:
        guiOrientation = Orientation.OB;
        break;
      case WO:
        guiOrientation = Orientation.OY;
        break;
      case BO:
        guiOrientation = Orientation.OG;
        break;
      case BW:
        guiOrientation = Orientation.WG;
        break;
      case OW:
        guiOrientation = Orientation.WR;
        break;
      case GW:
        guiOrientation = Orientation.WB;
        break;
      case RW:
        guiOrientation = Orientation.WO;
        break;
      default:
        break;
    }

    String test = cube.getOrientationStrings(this.guiOrientation.toString());
    this.setStrings(test);
    this.repaint();
  }

  private void dragUp() {

    switch (guiOrientation) {
      case OY:
        guiOrientation = Orientation.WO;
        break;
      case GY:
        guiOrientation = Orientation.WG;
        break;
      case BY:
        guiOrientation = Orientation.WB;
        break;
      case RY:
        guiOrientation = Orientation.WR;
        break;
      case RG:
        guiOrientation = Orientation.BR;
        break;
      case WG:
        guiOrientation = Orientation.BW;
        break;
      case OG:
        guiOrientation = Orientation.BO;
        break;
      case YG:
        guiOrientation = Orientation.BY;
        break;
      case YR:
        guiOrientation = Orientation.OY;
        break;
      case BR:
        guiOrientation = Orientation.OB;
        break;
      case WR:
        guiOrientation = Orientation.OW;
        break;
      case GR:
        guiOrientation = Orientation.OG;
        break;
      case YB:
        guiOrientation = Orientation.GY;
        break;
      case OB:
        guiOrientation = Orientation.GO;
        break;
      case WB:
        guiOrientation = Orientation.GW;
        break;
      case RB:
        guiOrientation = Orientation.GR;
        break;
      case YO:
        guiOrientation = Orientation.RY;
        break;
      case GO:
        guiOrientation = Orientation.RG;
        break;
      case WO:
        guiOrientation = Orientation.RW;
        break;
      case BO:
        guiOrientation = Orientation.RB;
        break;
      case BW:
        guiOrientation = Orientation.YB;
        break;
      case OW:
        guiOrientation = Orientation.YO;
        break;
      case GW:
        guiOrientation = Orientation.YG;
        break;
      case RW:
        guiOrientation = Orientation.YR;
        break;
      default:
        break;
    }

    String test = cube.getOrientationStrings(this.guiOrientation.toString());
    this.setStrings(test);
    this.repaint();
  }

  /**
   * Mouse drag diagonal downwards.
   */
  private void dragDiagDown() {
    switch (guiOrientation) {
      case OY:
        guiOrientation = Orientation.OB;
        break;
      case GY:
        guiOrientation = Orientation.GO;
        break;
      case BY:
        guiOrientation = Orientation.BR;
        break;
      case RY:
        guiOrientation = Orientation.RG;
        break;
      case RG:
        guiOrientation = Orientation.RW;
        break;
      case WG:
        guiOrientation = Orientation.WO;
        break;
      case OG:
        guiOrientation = Orientation.OY;
        break;
      case YG:
        guiOrientation = Orientation.YR;
        break;
      case YR:
        guiOrientation = Orientation.YB;
        break;
      case BR:
        guiOrientation = Orientation.BW;
        break;
      case WR:
        guiOrientation = Orientation.WG;
        break;
      case GR:
        guiOrientation = Orientation.GY;
        break;
      case YB:
        guiOrientation = Orientation.YO;
        break;
      case OB:
        guiOrientation = Orientation.OW;
        break;
      case WB:
        guiOrientation = Orientation.WR;
        break;
      case RB:
        guiOrientation = Orientation.RY;
        break;
      case YO:
        guiOrientation = Orientation.YG;
        break;
      case GO:
        guiOrientation = Orientation.GW;
        break;
      case WO:
        guiOrientation = Orientation.WB;
        break;
      case BO:
        guiOrientation = Orientation.BY;
        break;
      case BW:
        guiOrientation = Orientation.BO;
        break;
      case OW:
        guiOrientation = Orientation.OG;
        break;
      case GW:
        guiOrientation = Orientation.GR;
        break;
      case RW:
        guiOrientation = Orientation.RB;
        break;
      default:
        break;
    }

    String test = cube.getOrientationStrings(this.guiOrientation.toString());
    this.setStrings(test);
    this.repaint();
  }

  private void dragDiagUp() {
    switch (guiOrientation) {
      case OY:
        guiOrientation = Orientation.OG;
        break;
      case GY:
        guiOrientation = Orientation.GR;
        break;
      case BY:
        guiOrientation = Orientation.BO;
        break;
      case RY:
        guiOrientation = Orientation.RB;
        break;
      case RG:
        guiOrientation = Orientation.RY;
        break;
      case WG:
        guiOrientation = Orientation.WR;
        break;
      case OG:
        guiOrientation = Orientation.OW;
        break;
      case YG:
        guiOrientation = Orientation.YO;
        break;
      case YR:
        guiOrientation = Orientation.YG;
        break;
      case BR:
        guiOrientation = Orientation.BY;
        break;
      case WR:
        guiOrientation = Orientation.WB;
        break;
      case GR:
        guiOrientation = Orientation.GW;
        break;
      case YB:
        guiOrientation = Orientation.YR;
        break;
      case OB:
        guiOrientation = Orientation.OY;
        break;
      case WB:
        guiOrientation = Orientation.WO;
        break;
      case RB:
        guiOrientation = Orientation.RW;
        break;
      case YO:
        guiOrientation = Orientation.YB;
        break;
      case GO:
        guiOrientation = Orientation.GY;
        break;
      case WO:
        guiOrientation = Orientation.WG;
        break;
      case BO:
        guiOrientation = Orientation.BW;
        break;
      case BW:
        guiOrientation = Orientation.BR;
        break;
      case OW:
        guiOrientation = Orientation.OB;
        break;
      case GW:
        guiOrientation = Orientation.GO;
        break;
      case RW:
        guiOrientation = Orientation.RG;
        break;
      default:
        break;
    }

    String test = cube.getOrientationStrings(this.guiOrientation.toString());
    this.setStrings(test);
    this.repaint();
  }

  private Color getColour(final char c) {
    switch (c) {
      case 'b':
        return Color.BLUE;
      case 'o':
        return Color.getHSBColor(20, 240, 120).brighter(); // looks more like orange :-)
      case 'w':
        return Color.WHITE;
      case 'g':
        return Color.GREEN;
      case 'y':
        return Color.YELLOW;
      case 'r':
        return Color.RED;
      default:
    }
    return null; // not expected to get here - could throw an exception ?
  }

  @Override
  protected void paintComponent(final Graphics g) {
    paint(g);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(dw, dh);
  }

  void setStrings(final String allSides) {
    String[] threeStrings = allSides.split("\n");
    frontSide = threeStrings[0].toCharArray();
    leftSide = threeStrings[1].toCharArray();
    topSide = threeStrings[2].toCharArray();
  }

  @Override
  public void paint(final Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    int[] x;
    int[] y;

    // do front
    int index = 0;
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        g.setColor(getColour(frontSide[index]));
        x = dimensions.getXPointsFrontSide()[c][r];
        y = dimensions.getYPointsFrontSide()[c][r];
        g.fillPolygon(x, y, 4);
        index++;
      }
    }

    // do left
    index = 0;
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        x = dimensions.getXPointsLeftSide()[c][r];
        y = dimensions.getYPointsLeftSide()[c][r];

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

        x = dimensions.getXPointsTopSide()[c][r];
        y = dimensions.getYPointsTopSide()[c][r];

        g.fillPolygon(x, y, 4);
        index++;
      }
    }
    g.setColor(Color.black);
    for (int i = 0; i < 3; i++) {
      g.drawLine(dimensions.getXPointsLeftSide()[0][i][0],
          dimensions.getYPointsLeftSide()[0][i][0],
          dimensions.getXPointsLeftSide()[2][i][3],
          dimensions.getYPointsLeftSide()[2][i][3]);
    }
    for (int i = 0; i < 3; i++) {
      g.drawLine(
          dimensions.getXPointsLeftSide()[i][0][3],
          dimensions.getYPointsLeftSide()[i][0][3],
          dimensions.getXPointsLeftSide()[i][2][2],
          dimensions.getYPointsLeftSide()[i][2][2]);
    }

    for (int i = 0; i < 3; i++) {
      g.drawLine(dimensions.getXPointsTopSide()[0][i][3],
          dimensions.getYPointsTopSide()[0][i][3],
          dimensions.getXPointsTopSide()[2][i][2],
          dimensions.getYPointsTopSide()[2][i][2]);
    }

    for (int i = 0; i < 3; i++) {
      g.drawLine(dimensions.getXPointsTopSide()[i][0][1],
          dimensions.getYPointsTopSide()[i][0][1],
          dimensions.getXPointsTopSide()[i][2][2],
          dimensions.getYPointsTopSide()[i][2][2]);
    }

    for (int i = 0; i < 3; i++) {
      g.drawLine(dimensions.getXPointsFrontSide()[0][i][3],
          dimensions.getYPointsFrontSide()[0][i][3],
          dimensions.getXPointsFrontSide()[2][i][2],
          dimensions.getYPointsFrontSide()[2][i][2]);
    }

    for (int i = 0; i < 3; i++) {
      g.drawLine(dimensions.getXPointsFrontSide()[i][0][1],
          dimensions.getYPointsFrontSide()[i][0][1],
          dimensions.getXPointsFrontSide()[i][2][2],
          dimensions.getYPointsFrontSide()[i][2][2]);
    }
  }
}
