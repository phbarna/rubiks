package gui;

import rubiks.Cube;
import rubiks.CubeStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JPanel implements ActionListener {
    private CubePanel CubeCanvas = new CubePanel();
    private Cube cube = new Cube();
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().toLowerCase().equals("build")) {
            try {

                CubeStatus status = cube.buildCubeFromString(this.textArea.getText());
                int i = 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            CubeCanvas.repaint();

        }
        if (e.getActionCommand().toLowerCase().contains("random")) {
            try {
                cube.shuffle();
                String text = cube.getDisplayAnnotation();
                CubeCanvas.setStrings(text);
                CubeCanvas.repaint();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (e.getActionCommand().toLowerCase().contains("solved")) {
            try {
                cube = new Cube().asSolved();
                String text = cube.getDisplayAnnotation();
                CubeCanvas.setStrings(text);
                CubeCanvas.repaint();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
                try {

                    cube.followAlgorithm(this.algorithmText.getText(), false);
                    CubeCanvas.setStrings(cube.getDisplayAnnotation());
                    CubeCanvas.repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    CubeCanvas.repaint();
                }
            }
    }
    private Gui() {
        try {
            cube = new Cube().asSolved();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private JTextField algorithmText = new JTextField();
    private JButton buttonBuildCube = new JButton("Build");
    private Color color = Color.RED;
    private int xmax = 800;
    private int xmin = 500;
    private boolean forward = false;
    private JTextArea textArea = new JTextArea();


    private void displayGui() {
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



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel testPanel = new JPanel(new FlowLayout());



        JPanel testPane3 = new JPanel(new FlowLayout());


        JPanel testPane2 = new JPanel(new FlowLayout());

        controlPanel.add(testPane2);


        controlPanel.setPreferredSize(new Dimension(800, 200));

        JButton buttonExecute = new JButton("Execute Algorithm");
        buttonExecute.addActionListener(this);

        JButton buttonSolvedCube = new JButton("Build Solved Cube");
        buttonSolvedCube.addActionListener(this);
        buttonPanel.add(buttonSolvedCube);



        buttonBuildCube.addActionListener(this);
        algorithmText.setColumns(20);


        textArea.setRows(6);
        textArea.setColumns(9);
        testPane2.add(textArea);

        JButton buttonBuildRandom = new JButton("Random Cube");

        buttonPanel.add(algorithmText);
        buttonPanel.add((buttonExecute));
        buttonBuildRandom.addActionListener(this);
        buttonPanel.add(buttonBuildRandom);
        testPane2.add(buttonBuildCube);

        controlPanel.add((buttonPanel), 1, 0);
        app.add(controlPanel, BorderLayout.SOUTH);

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        try {
            CubeStatus status = cube.buildCubeFromString("");
            String text = cube.getDisplayAnnotation();

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