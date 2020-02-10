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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
                            "the turns can be run consecutively for example fc fa would return the cube to it's original state.\n"+
                            "3. You can build your own cube from a string. To do this hold your cube in front/upright and " +
                            "put in the orange face from left to right, top to bottom order, then rotate the cube putting in the blue, \n"+
                            "-- red, green in exactly the same way.  Then with orange facing you tilt the cube down so that yellow face is facing "+
                            "you.  \n" +
                            "-- then tilt the cube up to the white face and enter the"+
                            " white face letters.  Click build and you should see your cube build.\n"+
                            "4. You can save the contents of your cube state in to the build cube text area at any time. ENJOY :-)\n";
            JOptionPane.showMessageDialog(cubeCanvas, text, "About", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getActionCommand().toLowerCase().contains("copy")) {
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
                String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
                cubeCanvas.setStrings(orientationString);
                cubeCanvas.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
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
        JPanel rightPanel = new JPanel(new BorderLayout(15, 2));

        JButton buttonSolve = new JButton("Solve");
        JButton buttonAbout = new JButton("About/Help");
        buttonAbout.addActionListener(this);

        JFrame app = new JFrame("Rubiks");
        app.getContentPane().setLayout(new BorderLayout());
        app.add(cubeCanvas, BorderLayout.CENTER);

        buildTextArea.setBackground(Color.white);

        app.setSize(800, 800);
        cubeCanvas.setSize(new Dimension(800, 600));
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        borderLayout.setVgap(20);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 0, 0));

        JPanel algorithmPanel = new JPanel(new BorderLayout());

        JPanel buildCubePanel = new JPanel(new BorderLayout());
        buildCubePanel.setSize(300, 400);
        rightPanel.add(buildCubePanel, BorderLayout.WEST);

        JButton buttonExecute = new JButton("Execute Algorithm");

        buttonExecute.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        buttonExecute.addActionListener(this);

        JButton buttonSolvedCube = new JButton("Build Solved Cube");
        buttonSolve.setToolTipText("Solving Not implemented yet");
        buttonExecute.setToolTipText("Executes the algorith above i.e. fc 2lc rc etc ");
        JPanel panelButtonFiller = new JPanel();
        JButton buttonBuildRandom = new JButton("Build Random Cube");
        buttonSolvedCube.addActionListener(this);
        buttonPanel.add(buttonSolvedCube);
        buttonPanel.add(buttonBuildRandom);
        buttonPanel.add(buttonSolve);

        buttonBuildCube.addActionListener(this);
        buttonBuildCube.setToolTipText("Builds a new cube from above string - see help for more info on this");
        algorithmText.setColumns(20);
        algorithmText.setLineWrap(true);
        algorithmText.setWrapStyleWord(true);
        algorithmText.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        algorithmText.setRows(1);
        algorithmText.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

        buildTextArea.setRows(10);
        buildTextArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        buildTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        buildTextArea.setColumns(15);

        buildCubePanel.add(buildTextArea, BorderLayout.CENTER);
        buildCubePanel.add(buttonBuildCube, BorderLayout.SOUTH);

        buildCubePanel.setBorder(BorderFactory.createLineBorder(app.getBackground(), 1));
        JPanel panelAlgorithText = new JPanel(new FlowLayout());
        JScrollPane scroll = new JScrollPane();
        scroll.add(algorithmText);

        algorithmText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));

        panelAlgorithText.add((scroll));

        algorithmPanel.add(algorithmText, BorderLayout.CENTER);

        algorithmPanel.add(buttonExecute, BorderLayout.SOUTH);
        algorithmPanel.setBorder(BorderFactory.createLineBorder(app.getBackground(), 1));
        rightPanel.add(algorithmPanel, BorderLayout.EAST);

        JButton buttonsaveState = new JButton("Copy Cube State");
        buttonsaveState.setOpaque(true);
        buttonsaveState.setBackground(new Color(102, 0, 153));
        buttonsaveState.setOpaque(false);
        buttonsaveState.addActionListener(this);
        JButton buttonOrientate = new JButton("Orientate forward/up");
        buttonOrientate.addActionListener(this);

        buttonBuildRandom.addActionListener(this);

        buttonPanel.add(buttonsaveState);
        buttonPanel.add(buttonOrientate);
        buttonPanel.add(buttonAbout);
        buttonPanel.setBackground(app.getBackground());

        JPanel leftFill = new JPanel();
        JPanel rightFill = new JPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(800, 200));
        JPanel topFill = new JPanel();

        JPanel controlPanel = new JPanel(new GridLayout(1,3, 10, 0));

        controlPanel.add(buttonPanel);
        controlPanel.add(algorithmPanel);
        controlPanel.add(buildCubePanel);

        controlPanel.setBackground(app.getBackground());

        mainPanel.add(controlPanel, BorderLayout.CENTER);
        mainPanel.setBackground(app.getBackground());
        JPanel bottomFiller = new JPanel();
        mainPanel.add(rightFill, BorderLayout.EAST);
        mainPanel.add(leftFill, BorderLayout.WEST);
        mainPanel.add(topFill, BorderLayout.NORTH);
        mainPanel.add(bottomFiller, BorderLayout.SOUTH);
        leftFill.setBackground(app.getBackground());
        rightFill.setBackground(app.getBackground());
        bottomFiller.setPreferredSize(new Dimension(600, 20));
        app.add(mainPanel,BorderLayout.SOUTH);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);

        try {
            String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
            cubeCanvas.setStrings(orientationString);
            cubeCanvas.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Entry point for the program
     * @param args
     */
    public static void main(String[] args) {
        Gui g = new Gui();
        g.displayGui();
    }
}