package gui;

import rubiks.Cube;
import rubiks.CubeStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JPanel implements ActionListener {
    CubePanel CubeCanvas = new CubePanel();
    Cube cube = new Cube();
    public void actionPerformed(ActionEvent e) {
        try {
            cube.followAlgorithm(this.algorithmText.getText(), true);
            CubeCanvas.setStrings(cube.getDisplayAnnotation());
            CubeCanvas.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
            CubeCanvas.repaint();
        }
    }
    public Gui() {
        try {
            cube = new Cube().asSolved();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private JTextField algorithmText = new JTextField();
    private Color color = Color.RED;
    private int xmax = 800;
    private int xmin = 500;
    private boolean forward = false;


    public void displayGui() {
        Dimensions d = new Dimensions();

        JFrame app = new JFrame("Rubiks");
        app.getContentPane().setLayout(new BorderLayout());
        app.add(CubeCanvas, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();

        app.setSize(800, 800);
        CubeCanvas.setSize(new Dimension(800, 600));
        int height = CubeCanvas.getHeight();
        int width = CubeCanvas.getWidth();
        controlPanel.getAccessibleContext();

        controlPanel.setLayout(new GridLayout(2, 2));

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
        buttonExecute.addActionListener(this);


        JButton buttonBuildCube = new JButton("Build");
        algorithmText.setColumns(20);

        JTextArea textArea = new JTextArea();
        textArea.setRows(6);
        textArea.setColumns(9);
        testPane2.add(textArea);
        buttonPanel.add(algorithmText);
        buttonPanel.add((buttonExecute));
        testPane2.add(buttonBuildCube);

        controlPanel.add((buttonPanel), 1, 0);
        app.add(controlPanel, BorderLayout.SOUTH);

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        try {
            CubeStatus status = cube.buildCubeFromString("");
            String text = cube.getDisplayAnnotation();


            System.out.println(text);


            CubeCanvas.setStrings(text);
            CubeCanvas.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    public static void main(String[] args) {
        Gui g = new Gui();
        g.displayGui();
    }

    private void setColour(Color c) {
        color = c;
        if (!forward) {
            xmax = xmax - 1;
            xmin--;
        } else {
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


    }
}