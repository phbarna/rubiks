package gui;

import rubiks.Cube;
import rubiks.CubeStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Gui extends JPanel implements ActionListener {
    private CubePanel cubeCanvas;
    private Cube cube = new Cube();
    private final JTextArea algorithmText = new JTextArea();
    private final JButton buttonBuildCube = new JButton("Build From String");
    private final JTextArea buildTextArea = new JTextArea();

    private Gui() {
        try {
            cube = new Cube().asSolved();
            cubeCanvas = new CubePanel(cube);
            cubeCanvas.setBackground(Color.black);
            //  buildTextArea.setText(cube.getDisplayAnnotation());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Gui g = new Gui();
        g.displayGui();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().toLowerCase().contains("about")) {
            String text =
                    "Rubiks Cube written by Pete Barnard - 7th February 2020"+
                            "\n\n" +
                            "Help \n" +
                            "====\n" +
                            "1. The cube's default front/upright orientation is orange front, yellow top, green left - " +
                            " you can put it back to this state by clicking the Orientate Forward/Up button\n"+
                            "-- but can drag the cube across edge lines to move it to a different orientation.\n"+
                            "2. The algorithm text box allows you to put in the following turns fc bc lc rc uc dc -- these stand for front clockwise, back clockwise, left clockise, right clockwise, \n" +
                            "-- upper clockwise and down clockwise" +
                            "- all turns are clockwise as if you are looking at the cube face..."+
                            "each turn has a corresponding anticlockwise move so front anticlockwise would equate to fa\n "+
                            "-- you can also put a number in front of the move to indicate number of turns e.g. 3da is equivalent to dc" +
                            "the turns can be run consecuvitely for example fc fa would return the cube to it's original state.\n"+
                            "3. You can build your own cube from a string. To do this hold your cube in front/upright and " +
                            "put in the orange face from left to right, top to bottom order, then rotate the cube putting in the blue, \n"+
                            "-- red, green in exactly the same way.  Then with orange facing you tilt the cube down so that yellow face is facing "+
                            "you.  \n" +
                            "-- then tilt the cube up to the white face and enter the"+
                            " white face letters.  Click build and you should see your cube build.\n"+
                            "4. You can save the contents of your cube state in to the build cube text area at any time. ENJOY :-)\n";
            JOptionPane.showMessageDialog(cubeCanvas, text, "About", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getActionCommand().toLowerCase().contains("save")) {
            buildTextArea.setText(cube.getDisplayAnnotation());
        } else if (e.getActionCommand().toLowerCase().contains("orientate")) {
            this.cubeCanvas.setOrientationForwardUP();
        } else if (e.getActionCommand().toLowerCase().equals("build from string")) {
            try {
                if (buildTextArea.getText().isEmpty()) {
                    return;
                }
                String backupText = cube.getDisplayAnnotation(); // stops fron repainting a faulty cube
                CubeStatus status = cube.buildCubeFromString(this.buildTextArea.getText());
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
                //   buildTextArea.setText(cube.getDisplayAnnotation());
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
                //    buildTextArea.setText(cube.getDisplayAnnotation());
                cubeCanvas.repaint();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                String tempText = algorithmText.getText().replace("\n", "");
                if (this.algorithmText.getText().isEmpty())
                    return;
                if (cube.followAlgorithm(this.algorithmText.getText(), false) < 0) {
                    JOptionPane.showMessageDialog(cubeCanvas, "Error in your algorithm.\n " +
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
        JPanel leftPanel = new JPanel(new GridLayout(1, 1));
        JPanel rightPanel = new JPanel(new BorderLayout(15, 15));
        JPanel bottomRightPanel = new JPanel(new FlowLayout());

        JButton buttonSolve = new JButton("Solve");
        JButton buttonAbout = new JButton("About/Help");
        buttonAbout.addActionListener(this);

        JFrame app = new JFrame("Rubiks");
        app.getContentPane().setLayout(new BorderLayout());
        app.add(cubeCanvas, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();

        buildTextArea.setBackground(Color.white);

        app.setSize(800, 800);
        cubeCanvas.setSize(new Dimension(800, 600));
        int height = cubeCanvas.getHeight();
        int width = cubeCanvas.getWidth();
        controlPanel.getAccessibleContext();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        borderLayout.setVgap(20);
        controlPanel.setLayout(borderLayout);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 1, 1));
        JPanel algorithmPanel = new JPanel(new BorderLayout());

        JPanel buildCubePanel = new JPanel(new BorderLayout());
        buildCubePanel.setSize(300, 400);
        // controlPanel.add(buildCubePanel, BorderLayout.CENTER);
        rightPanel.add(buildCubePanel, BorderLayout.WEST);
        controlPanel.add(rightPanel, BorderLayout.EAST);
        controlPanel.setPreferredSize(new Dimension(800, 200));

        JButton buttonExecute = new JButton("-- Execute Algorithm --");
        buttonExecute.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        buttonExecute.addActionListener(this);

        JButton buttonSolvedCube = new JButton("Build Solved Cube");
        buttonSolve.setToolTipText("Solving Not implemented yet");
        buttonExecute.setToolTipText("Executes the algorith above i.e. fc 2lc rc etc ");

        buttonSolvedCube.addActionListener(this);
        buttonPanel.add(buttonSolvedCube);
        buttonPanel.add(buttonSolve);
        buttonPanel.add(buttonAbout);

        buttonBuildCube.addActionListener(this);
        buttonBuildCube.setToolTipText("Builds a new cube from above string - see help for more info on this");
        algorithmText.setColumns(20);
       algorithmText.setLineWrap(true);
        algorithmText.setRows(1);
        algorithmText.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

        buildTextArea.setRows(10);
        buildTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
        buildTextArea.setColumns(15);


        buildCubePanel.add(buildTextArea, BorderLayout.CENTER);
        buildCubePanel.add(buttonBuildCube, BorderLayout.SOUTH);
        buildCubePanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));
        JPanel panelAlgorithText = new JPanel(new FlowLayout());

        panelAlgorithText.add((algorithmText));

        algorithmPanel.add(buttonExecute, BorderLayout.CENTER);
        JLabel labelAlgorith = new JLabel("Algorithm Text:");
        panelAlgorithText.add(labelAlgorith);
        panelAlgorithText.add(algorithmText);
        algorithmPanel.add(panelAlgorithText, BorderLayout.NORTH);
        algorithmPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));
        rightPanel.add(algorithmPanel, BorderLayout.EAST);
        JButton buttonBuildRandom = new JButton("Random Cube");
        JButton buttonsaveState = new JButton("Save Cube State");
        buttonsaveState.addActionListener(this);
        JButton buttonOrientate = new JButton("Orientate forward/up");
        buttonOrientate.addActionListener(this);

        //   controlPanel.add(algorithmPanel, BorderLayout.SOUTH);
        controlPanel.add(algorithmPanel);
        // buttonPanel.add((buttonExecute));
        buttonBuildRandom.addActionListener(this);
        buttonPanel.add(buttonBuildRandom);
        buttonPanel.add(buttonsaveState);
        buttonPanel.add(buttonOrientate);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));

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