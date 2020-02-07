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
    private final JTextArea textArea = new JTextArea();

    private Gui() {
        try {
            cube = new Cube().asSolved();
            cubeCanvas = new CubePanel(cube);
            cubeCanvas.setBackground(Color.black);
            //  textArea.setText(cube.getDisplayAnnotation());
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
        } else if (e.getActionCommand().toLowerCase().contains("orientate")) {
            this.cubeCanvas.setOrientationForwardUP();
        } else if (e.getActionCommand().toLowerCase().equals("build from string")) {
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
                //   textArea.setText(cube.getDisplayAnnotation());
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
                //    textArea.setText(cube.getDisplayAnnotation());
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

        JButton buttonHelp = new JButton("Help");
        JButton buttonSolve = new JButton("Solve");
        JButton buttonAbout = new JButton("About");

        JFrame app = new JFrame("Rubiks");
        app.getContentPane().setLayout(new BorderLayout());
        app.add(cubeCanvas, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel();

textArea.setBackground(Color.white);
        app.setSize(800, 800);
        cubeCanvas.setSize(new Dimension(800, 600));
        int height = cubeCanvas.getHeight();
        int width = cubeCanvas.getWidth();
        controlPanel.getAccessibleContext();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(20);
        borderLayout.setVgap(20);
        controlPanel.setLayout(borderLayout);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 1, 1));
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
        buttonPanel.add(buttonHelp);
        buttonPanel.add(buttonSolve);
        buttonPanel.add(buttonAbout);

        buttonBuildCube.addActionListener(this);
        buttonBuildCube.setToolTipText("Builds a new cube from above string - see help for more info on this");
        algorithmText.setColumns(20);
       algorithmText.setLineWrap(true);
        algorithmText.setRows(1);
        algorithmText.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

        textArea.setRows(10);
        textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
        textArea.setColumns(15);


        buildCubePanel.add(textArea, BorderLayout.CENTER);
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