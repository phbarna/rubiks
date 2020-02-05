package gui;

import rubiks.Cube;
import rubiks.CubeStatus;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JPanel implements ActionListener {
    private CubePanel cubeCanvas;
    private Cube cube = new Cube();
    private JTextField algorithmText = new JTextField();
    private JButton buttonBuildCube = new JButton("Build");
    private Color color = Color.RED;
    private int xmax = 800;
    private int xmin = 500;
    private boolean forward = false;
    private JTextArea textArea = new JTextArea();

    private Gui() {
        try {
            cube = new Cube().asSolved();
            cubeCanvas = new CubePanel(cube);
            textArea.setText(cube.getDisplayAnnotation());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Gui g = new Gui();
        g.displayGui();
    }

    public void actionPerformed(ActionEvent e) {


        if (e.getActionCommand().toLowerCase().contains("save")) {
           textArea.setText(cube.getDisplayAnnotation());
        }
        else if (e.getActionCommand().toLowerCase().contains("orientate")) {
            this.cubeCanvas.setGuiOrientation("OY");
        }
        else if (e.getActionCommand().toLowerCase().equals("build")) {
            try {
                String backupText = cube.getDisplayAnnotation(); // stops fron repainting a faulty cube
                CubeStatus status = cube.buildCubeFromString(this.textArea.getText());
                if (!status.equals(CubeStatus.OK)) {
                    cube.buildCubeFromString(backupText); // put cube back to how it was
                    JOptionPane.showMessageDialog(cubeCanvas, status.getDescription(), "Build Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String text = cube.getOrientationStrings(cubeCanvas.getOrientation());
                    cubeCanvas.setStrings(text);
                    cubeCanvas.repaint();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getActionCommand().toLowerCase().contains("random")) {
            try {
                cube.shuffle();
                String text = cube.getDisplayAnnotation();

                String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
                cubeCanvas.setStrings(orientationString);
                textArea.setText(cube.getDisplayAnnotation());
                cubeCanvas.repaint();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getActionCommand().toLowerCase().contains("solved")) {
            try {
                CubeStatus status = cube.buildCubeFromString("ooooooooo" + "\n" +
                        "wwwwwwwww" + "\n" +
                        "bbbbbbbbb" + "\n" +
                        "rrrrrrrrr" + "\n" +
                        "ggggggggg" + "\n" +
                        "yyyyyyyyy" + "\n");
                String text = cube.getDisplayAnnotation();
                String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
                cubeCanvas.setStrings(orientationString);
                textArea.setText(cube.getDisplayAnnotation());
                cubeCanvas.repaint();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                if (this.algorithmText.getText().isEmpty())
                    return;
                if (cube.followAlgorithm(this.algorithmText.getText(), false) < 0) {
                    JOptionPane.showMessageDialog(cubeCanvas, "Error in your algorithm.\n "+
                            "Use notation:\n lc rc fc dc uc bc la ra fa da ua ba", "Algorithm Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
                    cubeCanvas.setStrings(orientationString);
                    cubeCanvas.repaint();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void displayGui() {

        JFrame app = new JFrame("Rubiks");
        app.getContentPane().setLayout(new BorderLayout());
        app.add(cubeCanvas, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();

        app.setSize(800, 800);
        cubeCanvas.setSize(new Dimension(800, 600));
        int height = cubeCanvas.getHeight();
        int width = cubeCanvas.getWidth();
        controlPanel.getAccessibleContext();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        borderLayout.setVgap(20);
        controlPanel.setLayout(borderLayout);

        JPanel buttonPanel = new JPanel(new GridLayout(2,2, 4,4));
        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel buildCubePanel = new JPanel(new BorderLayout());
        buildCubePanel.setSize(100, 400);

        controlPanel.add(buildCubePanel, BorderLayout.CENTER);

        controlPanel.setPreferredSize(new Dimension(800, 200));

        JButton buttonExecute = new JButton("Execute Algorithm");
        buttonExecute.addActionListener(this);

        JButton buttonSolvedCube = new JButton("Build Solved Cube");
        buttonSolvedCube.addActionListener(this);
        buttonPanel.add(buttonSolvedCube);

        buttonBuildCube.addActionListener(this);

        algorithmText.setColumns(20);

        textArea.setRows(10);
        Font  f2  = new Font(Font.MONOSPACED,  Font.BOLD, 14);
        textArea.setFont(f2);
        textArea.setColumns(9);

        buildCubePanel.add(textArea, BorderLayout.CENTER);
        buildCubePanel.add(buttonBuildCube, BorderLayout.SOUTH);

        algorithmPanel.add(algorithmText);
        algorithmPanel.add(buttonExecute);

        JButton buttonBuildRandom = new JButton("Random Cube");
        JButton buttonsaveState= new JButton("Save Cube State");
        buttonsaveState.addActionListener(this);
        JButton buttonOrientate= new JButton("Orientate forward/up");
        buttonOrientate.addActionListener(this);

        controlPanel.add(algorithmPanel, BorderLayout.SOUTH);
       // buttonPanel.add((buttonExecute));
        buttonBuildRandom.addActionListener(this);
        buttonPanel.add(buttonBuildRandom);
        buttonPanel.add(buttonsaveState);
        buttonPanel.add(buttonOrientate);

controlPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

        controlPanel.add(buttonPanel, BorderLayout.WEST);
        app.add(controlPanel, BorderLayout.SOUTH);

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        try {
            CubeStatus status = cube.buildCubeFromString("");
            String text = cube.getDisplayAnnotation();
            String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
            cubeCanvas.setStrings(orientationString);
            cubeCanvas.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}