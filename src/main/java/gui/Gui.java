package gui;


import rubiks.Cube;
import rubiks.CubeStatus;

import javax.swing.*;
import java.awt.*;

public class Gui extends Canvas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
        try {
            Cube cube = new Cube().asShuffled();
            CubeStatus status = cube.buildCubeFromString("wbowobrgr\n" +
                    "wyorbogby\n" +
                    "rygrygoyb\n" +
                    "wwgrggroy\n" +
                    "yrbwrgbbg\n" +
                    "bowywwyoo\n");
            if (status.equals(CubeStatus.OK)) {
                System.out.println("ok");

            }

            int attempts = cube.followAlgorithm("lc rc", true);

        } catch (Exception ex) {

        }
        Canvas canvas = new Gui();
        canvas.setSize(800, 800);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {


        g.setColor(Color.blue);
        g.drawRect(100, 100, 200, 200);
        g.drawLine(0, 0, 400, 400);
        g.setColor(Color.red);

        g.drawLine(400, 400, 0, 800);

        g.fillPolygon(new int[]{35, 55, 80, 100}, new int[]{300, 300, 350, 350}, 4); //Draws

    }
}
