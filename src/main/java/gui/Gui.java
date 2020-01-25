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


    }
    public static void main(String[] args) {

        Gui CubeCanvas = new Gui();
        JFrame app = new JFrame("Rubiks");
        app.getContentPane().setLayout(new BorderLayout());
        app.add(CubeCanvas, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();

        app.setSize(800, 800);
        CubeCanvas.setSize(new Dimension(800, 600));
        int height = CubeCanvas.getHeight();
        int width = CubeCanvas.getWidth();
        controlPanel.getAccessibleContext();

        controlPanel.setLayout(new GridLayout(2,2));

        controlPanel.setBackground(Color.YELLOW);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel testPanel = new JPanel(new FlowLayout());
        testPanel.setBackground(Color.pink);


        JPanel testPane3 = new JPanel(new FlowLayout());
        testPane3.setBackground(Color.orange);

        JPanel testPane2 = new JPanel(new FlowLayout());
        testPane2.setBackground(Color.yellow);
        controlPanel.add(testPane2);
        controlPanel.add(testPanel);
        controlPanel.add(testPane3);
        controlPanel.setPreferredSize(new Dimension(800, 200));
        buttonPanel.setBackground(Color.blue);
        JButton buttonExecute = new JButton("Execute Algorithm");
        JTextField algorithmText = new JTextField();
        JButton buttonBuildCube = new JButton("Build");
        algorithmText.setColumns(20);

        JTextArea textArea = new JTextArea();
        textArea.setRows(6);
        textArea.setColumns(9);
        testPane2.add(textArea);
        buttonPanel.add(algorithmText);
        buttonPanel.add((buttonExecute));
        testPane2.add(buttonBuildCube);

        controlPanel.add((buttonPanel),1,0);
        app.add(controlPanel, BorderLayout.SOUTH);

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        try {
            Cube cube = new Cube().asShuffled();
            CubeStatus status = cube.buildCubeFromString("");
            String text = cube.getDisplayAnnotation();


            System.out.println(text);
            Thread.sleep(1000);
            CubeCanvas.setColour(Color.black);
         //   app.pack();
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