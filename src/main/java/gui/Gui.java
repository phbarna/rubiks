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



    }

}
