package gui;

import java.awt.*;
import javax.swing.*;
import rubiks.*;

public class Gui extends JPanel {

    private Color color = Color.RED;
    private int xmax =800;
    private int xmin = 500;
    private boolean forward = false;
    private void setColour(Color c) {
        color = c;
        if (!forward) {
            xmax = xmax - 1;
            xmin--;
        }
        else {
            xmax++;

            xmin++;
        }
        if (xmax == 100) {
            forward = true;
        }
        if (xmax == 800)
            forward = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.setColor(Color.white);
//        g.fillOval(10, 10, 200, 200);
//
//        // draw Eyes
//        g.setColor(Color.BLACK);
//        g.fillOval(55, 65, 30, 30);
//        g.fillOval(135, 65, 30, 30);
//
//        // draw Mouth
//        g.fillOval(50, 110, 120, 60);
//
//        // adding smile
//        g.setColor(Color.white);
//        g.fillRect(50, 110, 120, 30);
//        g.fillOval(50, 120, 120, 40);
//
//        g.setColor(color);
//        Point point = new Point(100, 200);
//
//        int[]x = new int[4];
//        int[]y = new int[4];
//        int n;  // count of points

// Make a triangle
//        g.setColor(color);
//        x[0]=xmin; x[1]=xmax; x[2]=xmax; x[3]=xmin;
//        y[0]=100; y[1]=100; y[2]=200; y[3]=200;
//        n = 4;
//        Polygon myTri = new Polygon(x, y, n);  // a triangle
//        g.fillPolygon(myTri);

        g.drawLine(Coordinates.frontSideTopLeftP.x, Coordinates.frontSideTopLeftP.y,
                Coordinates.frontSideTopRightP.x, Coordinates.frontSideTopRightP.y);

        g.drawLine(Coordinates.frontSideTopRightP.x, Coordinates.frontSideTopRightP.y,
                Coordinates.frontSideBottomRighP.x, Coordinates.frontSideBottomRighP.y);

        g.drawLine(Coordinates.frontSideBottomRighP.x, Coordinates.frontSideBottomRighP.y,
                Coordinates.frontSideBottomLeftP.x, Coordinates.frontSideBottomLeftP.y);

        g.drawLine(Coordinates.frontSideBottomLeftP.x, Coordinates.frontSideBottomLeftP.y,
                Coordinates.frontSideTopLeftP.x, Coordinates.frontSideTopLeftP.y);



    }
    public static void main(String[] args) {

        Gui CubeCanvas = new Gui();
        JFrame app = new JFrame("Rubiks");
        app.add(CubeCanvas, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();

        app.setSize(800, 800);
        CubeCanvas.setSize(new Dimension(800, 600));
        int height = CubeCanvas.getHeight();
        int width = CubeCanvas.getWidth();
        controlPanel.getAccessibleContext();

        controlPanel.setLayout(new GridLayout(1,1));

        controlPanel.setBackground(Color.YELLOW);
        controlPanel.setPreferredSize(new Dimension(800, 200));

        app.add(controlPanel);

        app.setLocationRelativeTo(null);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        try {
            Cube cube = new Cube().asShuffled();
            CubeStatus status = cube.buildCubeFromString("");
            String text = cube.getDisplayAnnotation();


            System.out.println(text);
            Thread.sleep(1000);
            CubeCanvas.setColour(Color.black);
            CubeCanvas.repaint();
for (int i = 0; i< 100000; i++) {
    Thread.sleep(5);
    CubeCanvas.setColour(Color.blue);
    CubeCanvas.repaint();
}

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}