package rubiks;

import common.Orientation;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * represents the cube as a whole.
 * Should have public methods for turns
 * NOTE that the cube is always set up as solve as default.
 * Randomising the turns will mess the cube up
 * Future instructions for passing a specific state of the cube will be done
 */
public class Cube {
  private final CubeUtils cubeUtils = new CubeUtils();
  // create our six sides
  private final Side whiteSide;
  private final Side yellowSide;
  private final Side blueSide;
  private final Side redSide;
  private final Side orangeSide;
  private final Side greenSide;

  private static final Random RANDOM = new Random();

  public Cube() {
    whiteSide = new Side().withColour(Colour.W);
    yellowSide = new Side().withColour(Colour.Y);
    blueSide = new Side().withColour(Colour.B);
    redSide = new Side().withColour(Colour.R);
    orangeSide = new Side().withColour(Colour.O);
    greenSide = new Side().withColour(Colour.G);
  }

  /**
   * ...or asShuffle
   * ... the cube object - as not much point in having an orphan cube with no
   * sides or faces etc.
   *
   * @return Returns a new cube in a solved state
   */
  public Cube asSolved() {

    String notation = """
        rrrrrrrrr
        ggggggggg
        yyyyyyyyy
        ooooooooo
        bbbbbbbbb
        wwwwwwwww
        """;

    buildCubeFromString(notation);
    return this;
  }

  Cube asShuffled() {
    asSolved(); // asSolved first - so that we know it is shuffled from a 'valid state'
    shuffle();
    return this;
  }

  /**
   * Could be useful for debug to see more detailed state of the cube
   *
   * @return a string
   */
  String getFullAnnotationString() {
    return String.format("""
        %s
        %s
        %s
        %s
        %s
        %s
        """, orangeSide, blueSide, redSide, greenSide, yellowSide, whiteSide);
  }

  Side getWhiteSide() {
    return whiteSide;
  }

  Side getBlueSide() {
    return blueSide;
  }

  Side getRedSide() {
    return redSide;
  }

  Side getOrangeSide() {
    return orangeSide;
  }

  Side getGreenSide() {
    return greenSide;
  }

  Side getYellowSide() {
    return yellowSide;
  }

  /**
   * A method to enhance ease of writing the gui. The gui sends any of the 24
   * orientations
   * as you look as the cube and this method just sends 3 strings so that the gui
   * can render without
   * thinking
   *
   * @param orientation A 2 letter indicator of cube orientation (forward/up i.e
   *                    OY is orange forward yellow up (
   *                    which is the default condition by the way)
   * @return returns a fake representation of the cube by doing dummy turns on
   *         temp sides so that the cube
   *         is correctly orientated as far as the gui sees it (saves the gui
   *         having to think before rendering).
   */
  public String getOrientationStrings(String orientation) {
    orientation = orientation.toUpperCase();
    Orientation guiOrientation = Orientation.valueOf(orientation);
    Side tempFrontSide;
    Side tempLeftSide;
    Side tempTopSide;

    switch (guiOrientation) {
      case OY:
        tempFrontSide = cubeUtils.copySide(this.getOrangeSide());
        tempLeftSide = cubeUtils.copySide(this.getGreenSide());
        tempTopSide = cubeUtils.copySide(this.getYellowSide());
        break;
      case GY:
        tempFrontSide = cubeUtils.copySide(this.getGreenSide());
        tempLeftSide = cubeUtils.copySide(this.getRedSide());
        tempTopSide = cubeUtils.copySide(this.getYellowSide());
        tempTopSide.rotateSide(3);
        break;
      case BY:
        tempFrontSide = cubeUtils.copySide(this.getBlueSide());
        tempLeftSide = cubeUtils.copySide(this.getOrangeSide());
        tempTopSide = cubeUtils.copySide(this.getYellowSide());
        tempTopSide.rotateSide(1);
        break;
      case RY:
        tempFrontSide = cubeUtils.copySide(this.getRedSide());
        tempLeftSide = cubeUtils.copySide(this.getBlueSide());
        tempTopSide = cubeUtils.copySide(this.getYellowSide());
        tempTopSide.rotateSide(2);
        break;
      case RG:
        tempFrontSide = cubeUtils.copySide(this.getRedSide());
        tempLeftSide = cubeUtils.copySide(this.getYellowSide());
        tempTopSide = cubeUtils.copySide(this.getGreenSide());
        tempTopSide.rotateSide(3);
        tempLeftSide.rotateSide(1);
        tempFrontSide.rotateSide(3);
        break;
      case WG:
        tempFrontSide = cubeUtils.copySide(this.getWhiteSide());
        tempLeftSide = cubeUtils.copySide(this.getRedSide());
        tempTopSide = cubeUtils.copySide(this.getGreenSide());
        tempFrontSide.rotateSide(1);
        tempTopSide.rotateSide(0);
        tempLeftSide.rotateSide(3);
        break;
      case OG:
        tempFrontSide = cubeUtils.copySide(this.getOrangeSide());
        tempLeftSide = cubeUtils.copySide(this.getWhiteSide());
        tempTopSide = cubeUtils.copySide(this.getGreenSide());
        tempLeftSide.rotateSide(1);
        tempTopSide.rotateSide(1);
        tempFrontSide.rotateSide(1);
        break;
      case YG:
        tempFrontSide = cubeUtils.copySide(this.getYellowSide());
        tempLeftSide = cubeUtils.copySide(this.getOrangeSide());
        tempTopSide = cubeUtils.copySide(this.getGreenSide());
        tempTopSide.rotateSide(2);
        tempFrontSide.rotateSide(1);
        tempLeftSide.rotateSide(1);
        break;
      case YR:
        tempFrontSide = cubeUtils.copySide(this.getYellowSide());
        tempLeftSide = cubeUtils.copySide(this.getGreenSide());
        tempTopSide = cubeUtils.copySide(this.getRedSide());
        tempLeftSide.rotateSide(1);
        tempFrontSide.rotateSide(0);
        tempTopSide.rotateSide(2);
        break;
      case BR:
        tempFrontSide = cubeUtils.copySide(this.getBlueSide());
        tempLeftSide = cubeUtils.copySide(this.getYellowSide());
        tempTopSide = cubeUtils.copySide(this.getRedSide());
        tempTopSide.rotateSide(3);
        tempFrontSide.rotateSide(3);
        break;
      case WR:
        tempFrontSide = cubeUtils.copySide(this.getWhiteSide());
        tempLeftSide = cubeUtils.copySide(this.getBlueSide());
        tempTopSide = cubeUtils.copySide(this.getRedSide());
        tempLeftSide.rotateSide(3);
        tempFrontSide.rotateSide(2);
        break;
      case GR:
        tempFrontSide = cubeUtils.copySide(this.getGreenSide());
        tempLeftSide = cubeUtils.copySide(this.getWhiteSide());
        tempTopSide = cubeUtils.copySide(this.getRedSide());
        tempTopSide.rotateSide(1);
        tempFrontSide.rotateSide(1);
        tempLeftSide.rotateSide(2);
        break;
      case YB:
        tempFrontSide = cubeUtils.copySide(this.getYellowSide());
        tempLeftSide = cubeUtils.copySide(this.getRedSide());
        tempTopSide = cubeUtils.copySide(this.getBlueSide());
        tempTopSide.rotateSide(2);
        tempFrontSide.rotateSide(3);
        tempLeftSide.rotateSide(1);
        break;
      case OB:
        tempFrontSide = cubeUtils.copySide(this.getOrangeSide());
        tempLeftSide = cubeUtils.copySide(this.getYellowSide());
        tempTopSide = cubeUtils.copySide(this.getBlueSide());
        tempTopSide.rotateSide(3);
        tempFrontSide.rotateSide(3);
        tempLeftSide.rotateSide(3);
        break;
      case WB:
        tempFrontSide = cubeUtils.copySide(this.getWhiteSide());
        tempLeftSide = cubeUtils.copySide(this.getOrangeSide());
        tempTopSide = cubeUtils.copySide(this.getBlueSide());
        tempTopSide.rotateSide(0);
        tempFrontSide.rotateSide(3);
        tempLeftSide.rotateSide(3);
        break;
      case RB:
        tempFrontSide = cubeUtils.copySide(this.getRedSide());
        tempLeftSide = cubeUtils.copySide(this.getWhiteSide());
        tempTopSide = cubeUtils.copySide(this.getBlueSide());
        tempTopSide.rotateSide(1);
        tempFrontSide.rotateSide(1);
        tempLeftSide.rotateSide(3);
        break;
      case YO:
        tempFrontSide = cubeUtils.copySide(this.getYellowSide());
        tempLeftSide = cubeUtils.copySide(this.getBlueSide());
        tempTopSide = cubeUtils.copySide(this.getOrangeSide());
        tempTopSide.rotateSide(2);
        tempFrontSide.rotateSide(2);
        tempLeftSide.rotateSide(1);
        break;
      case GO:
        tempFrontSide = cubeUtils.copySide(this.getGreenSide());
        tempLeftSide = cubeUtils.copySide(this.getYellowSide());
        tempTopSide = cubeUtils.copySide(this.getOrangeSide());
        tempTopSide.rotateSide(3);
        tempFrontSide.rotateSide(3);
        tempLeftSide.rotateSide(2);
        break;
      case WO:
        tempFrontSide = cubeUtils.copySide(this.getWhiteSide());
        tempLeftSide = cubeUtils.copySide(this.getGreenSide());
        tempTopSide = cubeUtils.copySide(this.getOrangeSide());
        tempTopSide.rotateSide(0);
        tempFrontSide.rotateSide(0);
        tempLeftSide.rotateSide(3);
        break;
      case BO:
        tempFrontSide = cubeUtils.copySide(this.getBlueSide());
        tempLeftSide = cubeUtils.copySide(this.getWhiteSide());
        tempTopSide = cubeUtils.copySide(this.getOrangeSide());
        tempTopSide.rotateSide(1);
        tempLeftSide.rotateSide(0);
        tempFrontSide.rotateSide(1);
        break;
      case BW:
        tempFrontSide = cubeUtils.copySide(this.getBlueSide());
        tempLeftSide = cubeUtils.copySide(this.getRedSide());
        tempTopSide = cubeUtils.copySide(this.getWhiteSide());
        tempTopSide.rotateSide(1);
        tempLeftSide.rotateSide(2);
        tempFrontSide.rotateSide(2);
        break;
      case OW:
        tempFrontSide = cubeUtils.copySide(this.getOrangeSide());
        tempLeftSide = cubeUtils.copySide(this.getBlueSide());
        tempTopSide = cubeUtils.copySide(this.getWhiteSide());
        tempTopSide.rotateSide(2);
        tempLeftSide.rotateSide(2);
        tempFrontSide.rotateSide(2);
        break;
      case GW:
        tempFrontSide = cubeUtils.copySide(this.getGreenSide());
        tempLeftSide = cubeUtils.copySide(this.getOrangeSide());
        tempTopSide = cubeUtils.copySide(this.getWhiteSide());
        tempFrontSide.rotateSide(2);
        tempLeftSide.rotateSide(2);
        tempTopSide.rotateSide(3);
        break;
      case RW:
        tempFrontSide = cubeUtils.copySide(this.getRedSide());
        tempLeftSide = cubeUtils.copySide(this.getGreenSide());
        tempTopSide = cubeUtils.copySide(this.getWhiteSide());
        tempLeftSide.rotateSide(2);
        tempFrontSide.rotateSide(2);
        break;
      default:
        tempFrontSide = this.getOrangeSide();
        tempLeftSide = this.getGreenSide();
        tempTopSide = this.getYellowSide();
        break;
    }

    return tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
        + tempTopSide.getAllColoursForSide();

  }

  // the 4 others are done differently to this face i.e arrays move from face to
  // face.
  private void rightClockwise(int numberOfTimes) {
    // we order our 4 sides red, yellow, orange, white in this case (i.e right, up,
    // down, bottom from blue perspective)
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.rightTurn(this, true, numberOfTimes);
  }

  private void rightAntiClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.rightTurn(this, false, numberOfTimes);
  }

  private void leftClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.leftTurn(this, true, numberOfTimes);
  }

  private void leftAntiClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.leftTurn(this, false, numberOfTimes);
  }

  private void frontClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.frontTurn(this, true, numberOfTimes);
  }

  private void frontAntiClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.frontTurn(this, false, numberOfTimes);
  }

  private void upperClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.upperTurn(this, true, numberOfTimes);
  }

  private void upperAntiClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.upperTurn(this, false, numberOfTimes);
  }

  private void downFaceClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.downFaceTurn(this, true, numberOfTimes);
  }

  private void downFaceAntiClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.downFaceTurn(this, false, numberOfTimes);
  }

  private void backClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.backTurn(this, true, numberOfTimes);
  }

  private void backAntiClockwise(int numberOfTimes) {
    TurnHelper turnHelper = new TurnHelper();
    turnHelper.backTurn(this, false, numberOfTimes);
  }

  private boolean colourDistributionCheck(String sixLines) {
    boolean returnValue = true;
    for (Colour colour : Colour.values()) {
      if (sixLines.chars().filter(ch -> ch == colour.toString().charAt(0)).count() != 9) {
        returnValue = false;
        break;
      }

    }
    return returnValue;
  }

  private boolean sideErrorUnknownCheck(String sixLines) {
    HashSet<String> uniqueCenterHS = new HashSet<>(); // ensures center squares are unique
    String[] lines = sixLines.split("\n");
    for (String s : lines) {
      if (s.length() != 9) {
        return false;
      }
      if (!Pattern.matches("[rgbyow]{9}", s)) {
        return false;
      }
      uniqueCenterHS.add(s.substring(4, 5)); // 4,5 is the center face
    }

    return (uniqueCenterHS.size() == 6);

  }

  private void addFaceColours(String[] lines) {
    for (String s : lines) {
      switch (s.substring(4, 5)) {
        case "o":
          orangeSide.setMiniColourFaces(s);
          break;
        case "b":
          blueSide.setMiniColourFaces(s);
          break;
        case "r":
          redSide.setMiniColourFaces(s);
          break;
        case "g":
          greenSide.setMiniColourFaces(s);
          break;
        case "y":
          yellowSide.setMiniColourFaces(s);
          break;
        case "w":
          whiteSide.setMiniColourFaces(s);
          break;
        default:
      }
    }
  }

  /**
   * takes in 6 lines which represent 6 sides - reading left-right, top-bottom (
   * This method turned out to be quite complicated
   *
   * @param sixLines the 6 lines of the cube
   * @return a cube status indicating any problems with cube build
   */
  public CubeStatus buildCubeFromString(String sixLines) {
    // get rid of whitespace
    // white space is allowed on constructing string but we must remove it here
    sixLines = sixLines.replace(" ", "").toLowerCase();
    // first let's do some initial validation on these string/s
    String[] lines = sixLines.split("\n");

    if (lines.length != 6) {
      return CubeStatus.SIDE_ERROR_UNKNOWN;
    }

    if (!colourDistributionCheck(sixLines)) {
      return CubeStatus.COLOUR_DISTRIBUTION_ERROR;
    }

    if (!sideErrorUnknownCheck(sixLines)) {
      return CubeStatus.SIDE_ERROR_UNKNOWN;
    }

    // loop again and this time add face colours
    addFaceColours(lines);

    // now we need to calculate the miniface other axis colours with the info we
    // have
    Side[] sides = { orangeSide, blueSide, redSide, greenSide }; // first just iterate the 4 x-axis sides
    for (int index = 0; index < 4; index++) {
      // do tops and bottoms first
      for (int i = 0; i < 9; i++) {
        switch (i) {
          case 0:
            MiniFace miniFaceTop = yellowSide.getMiniFace(0, 0);
            MiniFace miniFaceY = redSide.getMiniFace(0, 2);
            MiniFace miniFaceX = greenSide.getMiniFace(0, 0);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour());
            MiniFace miniFaceBottom = whiteSide.getMiniFace(0, 0);
            miniFaceY = orangeSide.getMiniFace(2, 0);
            miniFaceX = greenSide.getMiniFace(2, 2);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour());
            break;
          case 1:
            miniFaceY = redSide.getMiniFace(0, 1);
            miniFaceTop = yellowSide.getMiniFace(0, 1);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceY.getFaceColour());

            miniFaceY = orangeSide.getMiniFace(2, 1);
            miniFaceBottom = whiteSide.getMiniFace(0, 1);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceY.getFaceColour());
            break;
          case 2:
            miniFaceTop = yellowSide.getMiniFace(0, 2);
            miniFaceX = blueSide.getMiniFace(0, 2);
            miniFaceY = redSide.getMiniFace(0, 0);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour() + miniFaceY.getFaceColour());
            miniFaceBottom = whiteSide.getMiniFace(0, 2);
            miniFaceY = orangeSide.getMiniFace(2, 2);
            miniFaceX = blueSide.getMiniFace(2, 0);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour());
            break;
          case 3:
            miniFaceX = greenSide.getMiniFace(0, 1);
            miniFaceTop = yellowSide.getMiniFace(1, 0);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour().toString());
            miniFaceX = greenSide.getMiniFace(2, 1);
            miniFaceBottom = whiteSide.getMiniFace(1, 0);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour());
            break;
          // note - there is no case 4 - that's the centerpiece and does not change
          case 5:
            miniFaceTop = yellowSide.getMiniFace(1, 2);
            miniFaceX = blueSide.getMiniFace(0, 1);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour().toString());
            miniFaceX = blueSide.getMiniFace(2, 1);
            miniFaceBottom = whiteSide.getMiniFace(1, 2);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour());
            break;
          case 6:
            miniFaceX = greenSide.getMiniFace(0, 2);
            miniFaceY = orangeSide.getMiniFace(0, 0);
            miniFaceTop = yellowSide.getMiniFace(2, 0);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour() + miniFaceY.getFaceColour().toString());
            miniFaceBottom = whiteSide.getMiniFace(2, 0);
            miniFaceY = redSide.getMiniFace(2, 2);
            miniFaceX = greenSide.getMiniFace(2, 0);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour());
            break;

          case 7:
            miniFaceY = orangeSide.getMiniFace(0, 1);
            miniFaceTop = yellowSide.getMiniFace(2, 1);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceY.getFaceColour());

            miniFaceY = redSide.getMiniFace(2, 1);
            miniFaceBottom = whiteSide.getMiniFace(2, 1);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceY.getFaceColour());
            break;
          case 8:
            miniFaceX = blueSide.getMiniFace(0, 0);
            miniFaceY = orangeSide.getMiniFace(0, 2);
            miniFaceTop = yellowSide.getMiniFace(2, 2);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour().toString());
            miniFaceBottom = whiteSide.getMiniFace(2, 2);
            miniFaceX = blueSide.getMiniFace(2, 2);
            miniFaceY = redSide.getMiniFace(2, 0);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour());
            break;
          default:
        }
      }

      for (int i = 0; i < 9; i++) {
        // create copy of top and bottom sides which we are going to use to rotate
        Side topSide = cubeUtils.copySide(yellowSide);
        Side bottomSide = cubeUtils.copySide(whiteSide);

        int bottomRotation = (4 - index);

        switch (i) {
          case 0:
            MiniFace miniFace = sides[index].getMiniFace(0, 0);
            topSide.rotateSide((index));
            MiniFace miniFaceTop = topSide.getMiniFace(2, 0);

            MiniFace toTheLeft = sides[(index + 3) % 4].getMiniFace(0, 2);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + toTheLeft.getFaceColour()
                + miniFaceTop.getFaceColour().toString());
            break;

          case 1:
            topSide.rotateSide(index);
            miniFace = sides[index].getMiniFace(0, 1);
            miniFaceTop = topSide.getMiniFace(2, 1);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceTop.getFaceColour().toString());
            break;
          case 2:

            miniFace = sides[index].getMiniFace(0, 2);

            topSide.rotateSide((index));
            miniFaceTop = topSide.getMiniFace(2, 2);

            MiniFace toTheRight = sides[(index + 1) % 4].getMiniFace(0, 0);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + toTheRight.getFaceColour()
                + miniFaceTop.getFaceColour().toString());

            break;
          case 3:
            miniFace = sides[index].getMiniFace(1, 0);
            MiniFace miniFaceLeft = sides[(index + 3) % 4].getMiniFace(1, 2);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceLeft.getFaceColour() + toString());
            break;

          case 5:
            miniFace = sides[index].getMiniFace(1, 2);
            toTheRight = sides[(index + 1) % 4].getMiniFace(1, 0);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + toTheRight.getFaceColour());
            break;
          case 6:
            miniFace = sides[index].getMiniFace(2, 0);
            miniFaceLeft = sides[(index + 3) % 4].getMiniFace(2, 2);
            bottomSide.rotateSide(bottomRotation);
            MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 0);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceLeft.getFaceColour()
                + miniFaceBottom.getFaceColour().toString());

            break;
          case 7:
            miniFace = sides[index].getMiniFace(2, 1);
            bottomSide.rotateSide(bottomRotation);
            miniFaceBottom = bottomSide.getMiniFace(0, 1);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceBottom.getFaceColour().toString());
            break;
          case 8:
            miniFace = sides[index].getMiniFace(2, 2);
            MiniFace miniFaceRight = sides[(index + 1) % 4].getMiniFace(2, 0);
            bottomSide.rotateSide(bottomRotation);
            miniFaceBottom = bottomSide.getMiniFace(0, 2);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceRight.getFaceColour()
                + miniFaceBottom.getFaceColour().toString());
            break;
          default:

        }
      }
    }
    return cubeUtils.validateCube(this);
  }

  /**
   * gets a string that a gui could easily deal with to build a cube
   *
   * @return returns a display annotation which the gui can build and save cubes
   *         from
   *         - note this does not cater for orientation - it assumes orange front,
   *         yellow top position.
   */
  public String getDisplayAnnotation() {

    String s = getOrangeSide().getAllColoursForSide() +
        getBlueSide().getAllColoursForSide() +
        getRedSide().getAllColoursForSide() +
        getGreenSide().getAllColoursForSide() +
        getYellowSide().getAllColoursForSide() +
        getWhiteSide().getAllColoursForSide();
    return s.trim();
  }

  /**
   * @param algorithm    the string to put in i.e. fc bc 2la dc uc rc etc
   * @param stopOnSolved a boolean to determine whether the algorithm stops on a
   *                     solved state or just carries on
   * @return -1 (validation error), 0 (no solve), or any positive integer
   *         representing the number of actions for a solve
   */
  public int followAlgorithm(String algorithm, boolean stopOnSolved) {
    algorithm = algorithm.replace("\n", " ").replace("  ", " ");
    algorithm = algorithm.toLowerCase();
    String[] instructions = algorithm.split(" ");

    // validate instructions before we start - i.e. if one fails, don't do any turns
    for (String instruction : instructions) {
      if (!Pattern.matches("[0-3]?[rlufdb][ac]", instruction)) {
        return -1;
      }
    }
    int instructionNumber = 1; // keeps track of number of instructions so that we know how long a solve takes
    for (String instruction : instructions) {
      int numberOfTurns = 1; // default of one turn if not specified
      if (instruction.length() == 3) { // means there is a number of turns
        numberOfTurns = Integer.parseInt(instruction.substring(0, 1));
        instruction = instruction.substring(1, 3);
      }
      if (instruction.length() != 2) {
        return -1;
      }

      switch (instruction) {
        case "rc" -> rightClockwise(numberOfTurns);
        case "ra" -> rightAntiClockwise(numberOfTurns);
        case "fc" -> frontClockwise(numberOfTurns);
        case "fa" -> frontAntiClockwise(numberOfTurns);
        case "uc" -> upperClockwise(numberOfTurns);
        case "ua" -> upperAntiClockwise(numberOfTurns);
        case "lc" -> leftClockwise(numberOfTurns);
        case "la" -> leftAntiClockwise(numberOfTurns);
        case "dc" -> downFaceClockwise(numberOfTurns);
        case "da" -> downFaceAntiClockwise(numberOfTurns);
        case "bc" -> backClockwise(numberOfTurns);
        case "ba" -> backAntiClockwise(numberOfTurns);
        default -> {
          return -1;
        }
      }

      if (stopOnSolved && cubeUtils.checkSolvedState(this)) {
        return instructionNumber;
      }
      instructionNumber++;
    }
    return 0; // if it gets here it has not reached a solved condition so returns just returns
              // zero
  }

  /**
   * @param min The minimum value in the random range
   * @param max The maximum value in the random range
   * @return a random int
   */
  private int random(int min, int max) {
    int range = max - min + 1;
    return RANDOM.nextInt(range) + min;
  }

  /**
   * Used random numbers to randomise cube
   */
  public void shuffle() {
    // max and min are arbitrary values, but I think 20-40 turns is suitable for a
    // good cube shuffle :-)
    int numTwists = random(20, 40);
    for (int i = 0; i < numTwists; i++) {
      // select random from 1-6 (for one of our 6 moves)
      int theMove = random(1, 6);

      // pointless to turn more than 3 times - so no point in doing anticlockwise
      // moves
      int numberOfTimes = random(1, 3);
      switch (theMove) {
        case 1 -> upperClockwise(numberOfTimes);
        case 2 -> rightClockwise(numberOfTimes);
        case 3 -> leftClockwise(numberOfTimes);
        case 4 -> frontClockwise(numberOfTimes);
        case 5 -> downFaceClockwise(numberOfTimes);
        case 6 -> backClockwise(numberOfTimes);
        default -> throw new IllegalArgumentException("Unknown move: " + theMove);
      }
    }
  }
}
