package gui;

import common.Orientation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import rubiks.Cube;
import rubiks.CubeStatus;
import rubiks.CubeUtils;

class Gui implements ActionListener, WindowListener {
      private static final int WIDTH = 800;
      private static final int HEIGHT = 400;
      private final JTextArea algorithmText = new JTextArea();
      private final JButton buttonBuildCube = new JButton("Build From String");
      private final JTextArea buildTextArea = new JTextArea();
      private CubePanel cubeCanvas;
      private Cube cube = new Cube();

      private Gui() {
            try {
                  cube = new Cube().asSolved();
                  cubeCanvas = new CubePanel(cube, WIDTH, HEIGHT);
            } catch (Exception ex) {
                  ex.printStackTrace();
            }
      }

      /**
       * Entry point for the program - starts the gui
       *
       * @param args - No runtime args used here
       */
      public static void main(String[] args) {
            Gui g = new Gui();
            g.displayGui();
      }

      public void windowIconified(WindowEvent e) {
            // method not used
      }

      public void windowDeiconified(WindowEvent e) {
            // method not used
      }

      public void windowActivated(WindowEvent e) {
            // method not used
      }

      public void windowDeactivated(WindowEvent e) {
            // method not used
      }

      /***
       * Attempts to read file if it exists and is ok. Thus remembers cube state from
       * last time the cube was used.
       * 
       * @param e - the window event
       */
      public void windowOpened(WindowEvent e) {
            try {
                  File myObj = new File("cube");
                  StringBuilder sb = new StringBuilder();
                  try (Scanner myReader = new Scanner(myObj)) {

                        while (myReader.hasNextLine()) {
                              sb.append(myReader.nextLine()).append("\n");
                        }
                  }
                  String readText = sb.toString();
                  String[] splitStrings = readText.split(",");

                  if (splitStrings.length != 3) {
                        throw new IOException("Error reading file");
                  }

                  CubeStatus status = cube.buildCubeFromString(splitStrings[2]);
                  cube.getOrientationStrings(splitStrings[1]);
                  cubeCanvas.setGuiOrientation(Orientation.valueOf(splitStrings[1]));
                  algorithmText.setText(splitStrings[0]);

                  if (!status.equals(CubeStatus.OK)) {
                        throw new IOException("Error reading file: " + status.getDescription());
                  }
                  String text = cube.getOrientationStrings(cubeCanvas.getOrientation());
                  cubeCanvas.setStrings(text);
                  this.buildTextArea.setText(cube.getDisplayAnnotation());
                  cubeCanvas.repaint();
            } catch (FileNotFoundException ex) {
                  // file not found? fine :-)
            } catch (IOException ex) {
                  ex.printStackTrace();
            }
      }

      public void windowClosed(WindowEvent e) {
            // method not used
      }

      /**
       * Saves cube state to a text file. If text is already
       * saved in the buildText area then gives the user the option to either save
       * current
       * state or it will just save what was already saved in the buildText area.
       *
       * @param e - the windowEvent
       */
      public void windowClosing(WindowEvent e) {
            try {

                  String currentText = cube.getDisplayAnnotation();
                  String savedText = buildTextArea.getText();
                  String textToSave = currentText;
                  if (!savedText.isEmpty() && !currentText.equals(savedText)) {
                        CubeStatus status = cube.buildCubeFromString(savedText);
                        // first stage validation - don't proceed if cannot build a valid cube
                        if (status.equals(CubeStatus.OK)) {
                              CubeUtils cubeUtils = new CubeUtils();
                              // 2nd stage validation - test if text area can build valid cube
                              if (cubeUtils.validateCube(cube).equals(CubeStatus.OK)) {
                                    int input = JOptionPane.showConfirmDialog(null,
                                                "Would you like to save cube's current state ?" +
                                                            "\n .. ('No' will save what's in the text area)",
                                                "Save cube state",
                                                JOptionPane.YES_NO_OPTION);
                                    if (input == JOptionPane.NO_OPTION) {
                                          textToSave = buildTextArea.getText();
                                    }
                              }
                        }
                  }
                  try (FileWriter myWriter = new FileWriter("cube")) {
                        String orientation = cubeCanvas.getOrientation();
                        myWriter.write(algorithmText.getText() + "," + orientation + "," + textToSave);
                  }
            } catch (IOException ex) {
                  ex.printStackTrace();
            }
      }

      private int saveTextQuestion() {
            String currentText = cube.getDisplayAnnotation();
            if (!buildTextArea.getText().isEmpty() && !currentText.equals(buildTextArea.getText())) {
                  Cube dummyCube = new Cube();
                  CubeStatus status = dummyCube.buildCubeFromString(buildTextArea.getText());
                  if (status.equals(CubeStatus.OK)) {
                        return JOptionPane.showConfirmDialog(null,
                                    "Would you like to save cube's current state before proceeding ?" +
                                                "\n .. ('No' will keep what's in the text area)",
                                    "Save cube state",
                                    JOptionPane.YES_NO_OPTION);
                  }
            }
            return JOptionPane.NO_OPTION;
      }

      private void buildFromString() {
            if (buildTextArea.getText().isEmpty()) {
                  return;
            }
            String backupText = cube.getDisplayAnnotation(); // stops from repainting a faulty cube
            CubeStatus status = cube.buildCubeFromString(this.buildTextArea.getText());
            if (!status.equals(CubeStatus.OK)) {
                  cube.buildCubeFromString(backupText); // put cube back to how it was
                  JOptionPane.showMessageDialog(cubeCanvas, status.getDescription(), "Build Error",
                              JOptionPane.ERROR_MESSAGE);
            } else {
                  String text = cube.getOrientationStrings(cubeCanvas.getOrientation());
                  cubeCanvas.setStrings(text);
                  cubeCanvas.repaint();
            }
      }

      private void buildRandomCube() {
            int answer = saveTextQuestion();
            if (answer == JOptionPane.YES_OPTION) {
                  buildTextArea.setText(cube.getDisplayAnnotation());
            }
            cube.shuffle();
            String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
            cubeCanvas.setStrings(orientationString);
            cubeCanvas.repaint();
      }

      private void buildSolvedCube() {
            int answer = saveTextQuestion();
            if (answer == JOptionPane.YES_OPTION) {
                  buildTextArea.setText(cube.getDisplayAnnotation());
            }
            cube.buildCubeFromString("""
                        ooooooooo
                        wwwwwwwww
                        bbbbbbbbb
                        rrrrrrrrr
                        ggggggggg
                        yyyyyyyyy
                        """);
            String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
            cubeCanvas.setStrings(orientationString);
            cubeCanvas.repaint();
      }

      private void unknownCondition() {
            if (this.algorithmText.getText().isEmpty()) {
                  return;
            }
            if (cube.followAlgorithm(this.algorithmText.getText(), false) < 0) {
                  JOptionPane.showMessageDialog(cubeCanvas, "Error in your algorithm.\n " +
                              "Use notation:\n lc rc fc dc uc bc la ra fa da ua ba", "Algorithm Error",
                              JOptionPane.ERROR_MESSAGE);
            } else {
                  String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
                  cubeCanvas.setStrings(orientationString);
                  cubeCanvas.repaint();
            }
      }

      /**
       * Method to check which button was pressed and act accordingly on this.
       *
       * @param e - the ActionEvent
       */
      public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().toLowerCase().contains("about")) {
                  JOptionPane.showMessageDialog(cubeCanvas, HelpText.TEXT, "About", JOptionPane.PLAIN_MESSAGE);
            } else if (e.getActionCommand().toLowerCase().contains("copy")) {
                  buildTextArea.setText(cube.getDisplayAnnotation());
            } else if (e.getActionCommand().toLowerCase().contains("orientate")) {
                  this.cubeCanvas.setOrientationForwardUP();
            } else if (e.getActionCommand().equalsIgnoreCase("build from string")) {
                  buildFromString();
            } else if (e.getActionCommand().toLowerCase().contains("random")) {
                  buildRandomCube();
            } else if (e.getActionCommand().toLowerCase().contains("solved")) {
                  buildSolvedCube();
            } else if (e.getActionCommand().toLowerCase().contains("solve")) {
                  JOptionPane.showMessageDialog(cubeCanvas, "Sorry - solving not implemented yet !", ":-(",
                              JOptionPane.PLAIN_MESSAGE);
            } else {
                  unknownCondition();
            }
      }

      private void displayGui() {
            JPanel rightPanel = new JPanel(new BorderLayout(15, 2));

            JButton buttonSolve = new JButton("Solve");
            JButton buttonAbout = new JButton("About/Help");
            buttonAbout.addActionListener(this);
            buttonSolve.addActionListener(this);

            JFrame app = new JFrame("Rubiks");
            app.getContentPane().setLayout(new BorderLayout());
            app.add(cubeCanvas, BorderLayout.CENTER);

            buildTextArea.setBackground(Color.white);
            app.setSize(WIDTH, HEIGHT);
            cubeCanvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            BorderLayout borderLayout = new BorderLayout();
            borderLayout.setHgap(20);
            borderLayout.setVgap(20);

            JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 0, 0));

            JPanel algorithmPanel = new JPanel(new BorderLayout());

            JPanel buildCubePanel = new JPanel(new BorderLayout());

            rightPanel.add(buildCubePanel, BorderLayout.WEST);

            JButton buttonExecute = new JButton("Execute Algorithm");

            buttonExecute.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
            buttonExecute.addActionListener(this);

            JButton buttonSolvedCube = new JButton("Build Solved Cube");
            buttonSolve.setToolTipText("Solving Not implemented yet");
            buttonExecute.setToolTipText("Executes the algorithm above i.e. fc 2lc rc etc ");
            JButton buttonBuildRandom = new JButton("Build Random Cube");
            buttonSolvedCube.addActionListener(this);
            buttonPanel.add(buttonSolvedCube);
            buttonPanel.add(buttonBuildRandom);
            buttonPanel.add(buttonSolve);

            buttonBuildCube.addActionListener(this);
            buttonBuildCube
                        .setToolTipText("Builds a new cube from above string - see help for more info on this");
            algorithmText.setColumns(20);
            algorithmText.setToolTipText("e.g. rc lc dc uc fc bc - see help for more details");
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
            JPanel panelAlgorithmText = new JPanel(new FlowLayout());
            JScrollPane scroll = new JScrollPane();
            scroll.add(algorithmText);

            algorithmText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));

            panelAlgorithmText.add((scroll));

            algorithmPanel.add(algorithmText, BorderLayout.CENTER);

            algorithmPanel.add(buttonExecute, BorderLayout.SOUTH);
            algorithmPanel.setBorder(BorderFactory.createLineBorder(app.getBackground(), 1));
            rightPanel.add(algorithmPanel, BorderLayout.EAST);

            JButton buttonSaveState = new JButton("Copy Cube State");
            buttonSaveState.addActionListener(this);
            JButton buttonOrientate = new JButton("Orientate forward/up");
            buttonOrientate.addActionListener(this);

            buttonBuildRandom.addActionListener(this);

            buttonPanel.add(buttonSaveState);
            buttonPanel.add(buttonOrientate);
            buttonPanel.add(buttonAbout);
            buttonPanel.setBackground(app.getBackground());

            JPanel leftFill = new JPanel();
            JPanel rightFill = new JPanel();
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setPreferredSize(new Dimension(800, 200));
            JPanel topFill = new JPanel();

            JPanel controlPanel = new JPanel(new GridLayout(1, 3, 10, 0));

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
            app.addWindowListener(this);
            app.add(mainPanel, BorderLayout.SOUTH);
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
            app.setResizable(false);
            app.pack();

            try {
                  String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
                  cubeCanvas.setStrings(orientationString);

            } catch (Exception ex) {
                  ex.printStackTrace();
            }
      }
}