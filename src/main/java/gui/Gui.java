package gui;

import common.Orientation;
import common.Solved;

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
import javax.swing.WindowConstants;

final class Gui implements ActionListener, WindowListener {

  private static final int WIDTH = 800;
  private static final int HEIGHT = 400;
  private final JTextArea algorithmText = new JTextArea();
  private final JButton buttonBuildCube = new JButton("Build From String");
  private final JTextArea buildTextArea = new JTextArea();
  private CubePanel cubeCanvas;
  private Cube cube = new Cube();

  private Gui() {
    cube = new Cube().asSolved();
    cubeCanvas = new CubePanel(cube, WIDTH, HEIGHT);
  }

  /**
   * Entry point for the program - starts the gui
   *
   * @param args - No runtime args used here
   */
  public static void main(final String[] args) {
    Gui g = new Gui();
    g.displayGui();
  }

  @Override
  public void windowIconified(final WindowEvent e) {
    // method not used
  }

  @Override
  public void windowDeiconified(final WindowEvent e) {
    // method not used
  }

  @Override
  public void windowActivated(final WindowEvent e) {
    // method not used
  }

  @Override
  public void windowDeactivated(final WindowEvent e) {
    // method not used
  }

  /**
   * Attempts to read file if it exists and is ok. Thus remembers cube state from
   * last time the cube was used.
   * 
   * @param e - The window event
   */
  @Override
  public void windowOpened(final WindowEvent e) {

    CubeSnapshot snapshot = CubeSnapshotIO.readFromFile();
    CubeStatus status = cube.buildCubeFromString(snapshot.getDisplayAnnotation());

    if (!status.equals(CubeStatus.OK)) {
      throw new IllegalStateException("Cube status is not OK: " + status);
    }

    cubeCanvas.setGuiOrientation(Orientation.valueOf(snapshot.getOrientation()));
    algorithmText.setText(snapshot.getAlgorithm());

    this.buildTextArea.setText(cube.getDisplayAnnotation());
    cubeCanvas.repaint();
  }

  @Override
  public void windowClosed(final WindowEvent e) {
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
  @Override
  public void windowClosing(final WindowEvent e) {

    String currentText = cube.getDisplayAnnotation();
    String savedText = buildTextArea.getText();
    String displayAnnotationText = currentText;
    if (!savedText.isEmpty() && !currentText.equals(savedText)) {
      CubeStatus status = cube.buildCubeFromString(savedText);
      // first stage validation - don't proceed if cannot build a valid cube
      if (status.equals(CubeStatus.OK)) {
        CubeUtils cubeUtils = new CubeUtils();
        // 2nd stage validation - test if text area can build valid cube
        if (cubeUtils.validateCube(cube).equals(CubeStatus.OK)) {
          int input = JOptionPane.showConfirmDialog(null,
              """
                  Would you like to save cube's current state ?
                  .. ('No' will save what's in the text area)
                  """,
              "Save cube state",
              JOptionPane.YES_NO_OPTION);
          if (input == JOptionPane.NO_OPTION) {
            displayAnnotationText = buildTextArea.getText();
          }
        }
      }
    }
    CubeSnapshot snapshot = new CubeSnapshot(algorithmText.getText(), cubeCanvas.getOrientation(),
        displayAnnotationText);
    CubeSnapshotIO.writeToFile(snapshot);
  }

  private int saveTextQuestion() {
    String currentText = cube.getDisplayAnnotation();
    if (!buildTextArea.getText().isEmpty() && !currentText.equals(buildTextArea.getText())) {
      Cube dummyCube = new Cube();
      CubeStatus status = dummyCube.buildCubeFromString(buildTextArea.getText());
      if (status.equals(CubeStatus.OK)) {
        return JOptionPane.showConfirmDialog(null,
            """
                Would you like to save cube's current state before proceeding ?
                .. ('No' will keep what's in the text area)
                """,
            "Save cube state",
            JOptionPane.YES_NO_OPTION);
      }
    }
    return JOptionPane.NO_OPTION;
  }

  /**
   * Builds the cube from the text in the buildTextArea field
   */
  private void buildFromString() {
    if (buildTextArea.getText().isEmpty()) {
      return;
    }
    String backupText = cube.getDisplayAnnotation(); // stops from repainting a faulty cube
    CubeStatus status = cube.buildCubeFromString(buildTextArea.getText());
    if (!status.equals(CubeStatus.OK)) {
      cube.buildCubeFromString(backupText); // put cube back to how it was
      JOptionPane.showMessageDialog(cubeCanvas, status, "Build Error",
          JOptionPane.ERROR_MESSAGE);
    } else {
      String text = cube.getOrientationStrings(cubeCanvas.getOrientation());
      cubeCanvas.setStrings(text);
      cubeCanvas.repaint();
    }
  }

  /**
   * Builds a random cube.
   */
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

  /**
   * Builds a cube in the solved condition.
   */
  private void buildSolvedCube() {
    int answer = saveTextQuestion();
    if (answer == JOptionPane.YES_OPTION) {
      buildTextArea.setText(cube.getDisplayAnnotation());
    }
    cube.buildCubeFromString(Solved.SOLVED_ANNOTATION);
    String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
    cubeCanvas.setStrings(orientationString);
    cubeCanvas.repaint();
  }

  private void unknownCondition() {
    if (this.algorithmText.getText().isEmpty()) {
      return;
    }
    if (cube.followAlgorithm(algorithmText.getText(), false) < 0) {
      JOptionPane.showMessageDialog(cubeCanvas, """
          Error in your algorithm.
          Use notation:
          lc rc fc dc uc bc la ra fa da ua ba""",
          "Algorithm Error",
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
  @Override
  public void actionPerformed(final ActionEvent e) {
    String actionCommand = e.getActionCommand().toLowerCase();
    String commandKey;
    final String about = "about";
    final String copy = "copy";
    final String orientate = "orientate";
    final String buildFromString = "buildFromString";
    final String random = "random";
    final String solved = "solved";
    final String solve = "solve";
    // Step 1: Normalize the command
    if (actionCommand.contains(about)) {
      commandKey = about;
    } else if (actionCommand.contains(copy)) {
      commandKey = copy;
    } else if (actionCommand.contains("orientate")) {
      commandKey = orientate;
    } else if (actionCommand.equals("build from string")) {
      commandKey = buildFromString;
    } else if (actionCommand.contains("random")) {
      commandKey = random;
    } else if (actionCommand.contains("solved")) {
      commandKey = solved;
    } else if (actionCommand.contains("solve")) {
      commandKey = solve;
    } else {
      commandKey = "unknown";
    }

    // Step 2: Handle the command with a switch
    switch (commandKey) {
      case about -> JOptionPane.showMessageDialog(
          cubeCanvas, HelpText.TEXT, "About", JOptionPane.PLAIN_MESSAGE);
      case copy -> buildTextArea.setText(cube.getDisplayAnnotation());
      case orientate -> cubeCanvas.setOrientationForwardUP();
      case buildFromString -> buildFromString();
      case random -> buildRandomCube();
      case solved -> buildSolvedCube();
      case solve -> JOptionPane.showMessageDialog(
          cubeCanvas, "Sorry - solving not implemented yet !", ":-(",
          JOptionPane.PLAIN_MESSAGE);
      default -> unknownCondition();
    }
  }

  /**
   * Displays and lays out all the swing components for the gui.
   */
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
    app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    app.setVisible(true);
    app.setResizable(false);
    app.pack();

    String orientationString = cube.getOrientationStrings(cubeCanvas.getOrientation());
    cubeCanvas.setStrings(orientationString);
  }
}
