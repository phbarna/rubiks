package rubiks;

import common.Orientation;
import common.Solved;

import java.util.HashSet;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Represents the cube as a whole.
 * Should have public methods for turns
 * NOTE that the cube is always set up as solve as default.
 * Randomising the turns will mess the cube up
 */
public final class Cube {

  /**
   * Create our six sides
   */
  private final Side whiteSide;
  private final Side yellowSide;
  private final Side blueSide;
  private final Side redSide;
  private final Side orangeSide;
  private final Side greenSide;
  private static final CubeUtils CUBE_UTILS = new CubeUtils();
  private static final Random RANDOM = new Random();
  private static final TurnHelper TURN_HELPER = new TurnHelper();

  public Cube() {
    whiteSide = new Side().withColour(Colour.W);
    yellowSide = new Side().withColour(Colour.Y);
    blueSide = new Side().withColour(Colour.B);
    redSide = new Side().withColour(Colour.R);
    orangeSide = new Side().withColour(Colour.O);
    greenSide = new Side().withColour(Colour.G);
  }

  /**
   * The cube object - as not much point in having an orphan cube with no
   * sides or faces etc.
   *
   * @return Returns a new cube in a solved state
   */
  public Cube asSolved() {

    buildCubeFromString(Solved.SOLVED_ANNOTATION);
    return this;
  }

  Cube asShuffled() {
    // asSolved first - so that we know it is shuffled from a 'valid state'.
    asSolved();
    // then startt the shuffle.
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
   * @param orientation A 2 letter indicator of cube orientation
   *                    (forward/up i.e
   *                    OY is orange forward yellow up (
   *                    which is the default condition by the way).
   * @return returns a fake representation of the cube by doing dummy turns on
   *         temp sides so that the cube
   *         is correctly orientated as far as the gui sees it (saves the gui
   *         having to think before rendering).
   */
  public String getOrientationStrings(final String orientation) {
    String orientationUppercase = orientation.toUpperCase();
    Orientation guiOrientation = Orientation.valueOf(orientationUppercase);

    return switch (guiOrientation) {
      case OY -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getOrangeSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getGreenSide());
        Side tempTopSide = CUBE_UTILS.copySide(getYellowSide());
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case GY -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getGreenSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getRedSide());
        Side tempTopSide = CUBE_UTILS.copySide(getYellowSide());
        tempTopSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case BY -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getBlueSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getOrangeSide());
        Side tempTopSide = CUBE_UTILS.copySide(getYellowSide());
        tempTopSide.rotateSide(1);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case RY -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getRedSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getBlueSide());
        Side tempTopSide = CUBE_UTILS.copySide(getYellowSide());
        tempTopSide.rotateSide(2);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case RG -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getRedSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getYellowSide());
        Side tempTopSide = CUBE_UTILS.copySide(getGreenSide());
        tempTopSide.rotateSide(3);
        tempLeftSide.rotateSide(1);
        tempFrontSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case WG -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getWhiteSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getRedSide());
        Side tempTopSide = CUBE_UTILS.copySide(getGreenSide());
        tempFrontSide.rotateSide(1);
        tempTopSide.rotateSide(0);
        tempLeftSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case OG -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getOrangeSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getWhiteSide());
        Side tempTopSide = CUBE_UTILS.copySide(getGreenSide());
        tempLeftSide.rotateSide(1);
        tempTopSide.rotateSide(1);
        tempFrontSide.rotateSide(1);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case YG -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getYellowSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getOrangeSide());
        Side tempTopSide = CUBE_UTILS.copySide(getGreenSide());
        tempTopSide.rotateSide(2);
        tempFrontSide.rotateSide(1);
        tempLeftSide.rotateSide(1);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case YR -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getYellowSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getGreenSide());
        Side tempTopSide = CUBE_UTILS.copySide(getRedSide());
        tempLeftSide.rotateSide(1);
        tempFrontSide.rotateSide(0);
        tempTopSide.rotateSide(2);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case BR -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getBlueSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getYellowSide());
        Side tempTopSide = CUBE_UTILS.copySide(getRedSide());
        tempTopSide.rotateSide(3);
        tempFrontSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case WR -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getWhiteSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getBlueSide());
        Side tempTopSide = CUBE_UTILS.copySide(getRedSide());
        tempLeftSide.rotateSide(3);
        tempFrontSide.rotateSide(2);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case GR -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getGreenSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getWhiteSide());
        Side tempTopSide = CUBE_UTILS.copySide(getRedSide());
        tempTopSide.rotateSide(1);
        tempFrontSide.rotateSide(1);
        tempLeftSide.rotateSide(2);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case YB -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getYellowSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getRedSide());
        Side tempTopSide = CUBE_UTILS.copySide(getBlueSide());
        tempTopSide.rotateSide(2);
        tempFrontSide.rotateSide(3);
        tempLeftSide.rotateSide(1);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case OB -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getOrangeSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getYellowSide());
        Side tempTopSide = CUBE_UTILS.copySide(getBlueSide());
        tempTopSide.rotateSide(3);
        tempFrontSide.rotateSide(3);
        tempLeftSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case WB -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getWhiteSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getOrangeSide());
        Side tempTopSide = CUBE_UTILS.copySide(getBlueSide());
        tempTopSide.rotateSide(0);
        tempFrontSide.rotateSide(3);
        tempLeftSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case RB -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getRedSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getWhiteSide());
        Side tempTopSide = CUBE_UTILS.copySide(getBlueSide());
        tempTopSide.rotateSide(1);
        tempFrontSide.rotateSide(1);
        tempLeftSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case YO -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getYellowSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getBlueSide());
        Side tempTopSide = CUBE_UTILS.copySide(getOrangeSide());
        tempTopSide.rotateSide(2);
        tempFrontSide.rotateSide(2);
        tempLeftSide.rotateSide(1);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case GO -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getGreenSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getYellowSide());
        Side tempTopSide = CUBE_UTILS.copySide(getOrangeSide());
        tempTopSide.rotateSide(3);
        tempFrontSide.rotateSide(3);
        tempLeftSide.rotateSide(2);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case WO -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getWhiteSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getGreenSide());
        Side tempTopSide = CUBE_UTILS.copySide(getOrangeSide());
        tempTopSide.rotateSide(0);
        tempFrontSide.rotateSide(0);
        tempLeftSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case BO -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getBlueSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getWhiteSide());
        Side tempTopSide = CUBE_UTILS.copySide(getOrangeSide());
        tempTopSide.rotateSide(1);
        tempLeftSide.rotateSide(0);
        tempFrontSide.rotateSide(1);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case BW -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getBlueSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getRedSide());
        Side tempTopSide = CUBE_UTILS.copySide(getWhiteSide());
        tempTopSide.rotateSide(1);
        tempLeftSide.rotateSide(2);
        tempFrontSide.rotateSide(2);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case OW -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getOrangeSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getBlueSide());
        Side tempTopSide = CUBE_UTILS.copySide(getWhiteSide());
        tempTopSide.rotateSide(2);
        tempLeftSide.rotateSide(2);
        tempFrontSide.rotateSide(2);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case GW -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getGreenSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getOrangeSide());
        Side tempTopSide = CUBE_UTILS.copySide(getWhiteSide());
        tempFrontSide.rotateSide(2);
        tempLeftSide.rotateSide(2);
        tempTopSide.rotateSide(3);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      case RW -> {
        Side tempFrontSide = CUBE_UTILS.copySide(getRedSide());
        Side tempLeftSide = CUBE_UTILS.copySide(getGreenSide());
        Side tempTopSide = CUBE_UTILS.copySide(getWhiteSide());
        tempLeftSide.rotateSide(2);
        tempFrontSide.rotateSide(2);
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
      default -> {
        Side tempFrontSide = getOrangeSide();
        Side tempLeftSide = getGreenSide();
        Side tempTopSide = getYellowSide();
        yield tempFrontSide.getAllColoursForSide() + tempLeftSide.getAllColoursForSide()
            + tempTopSide.getAllColoursForSide();
      }
    };
  }

  // the 4 others are done differently to this face i.e arrays move from face to
  // face.
  private void rightClockwise(final int numberOfTimes) {
    // we order our 4 sides red, yellow, orange, white in this case (i.e right, up,
    // down, bottom from blue perspective)
    TURN_HELPER.rightTurn(this, true, numberOfTimes);
  }

  private void rightAntiClockwise(final int numberOfTimes) {
    TURN_HELPER.rightTurn(this, false, numberOfTimes);
  }

  private void leftClockwise(final int numberOfTimes) {
    TURN_HELPER.leftTurn(this, true, numberOfTimes);
  }

  private void leftAntiClockwise(final int numberOfTimes) {
    TURN_HELPER.leftTurn(this, false, numberOfTimes);
  }

  private void frontClockwise(final int numberOfTimes) {
    TURN_HELPER.frontTurn(this, true, numberOfTimes);
  }

  private void frontAntiClockwise(final int numberOfTimes) {
    TURN_HELPER.frontTurn(this, false, numberOfTimes);
  }

  private void upperClockwise(final int numberOfTimes) {
    TURN_HELPER.upperTurn(this, true, numberOfTimes);
  }

  private void upperAntiClockwise(final int numberOfTimes) {
    TURN_HELPER.upperTurn(this, false, numberOfTimes);
  }

  private void downFaceClockwise(final int numberOfTimes) {
    TURN_HELPER.downFaceTurn(this, true, numberOfTimes);
  }

  private void downFaceAntiClockwise(final int numberOfTimes) {
    TURN_HELPER.downFaceTurn(this, false, numberOfTimes);
  }

  private void backClockwise(final int numberOfTimes) {
    TURN_HELPER.backTurn(this, true, numberOfTimes);
  }

  private void backAntiClockwise(final int numberOfTimes) {
    TURN_HELPER.backTurn(this, false, numberOfTimes);
  }

  private boolean colourDistributionCheck(final String sixLines) {
    boolean returnValue = true;
    for (Colour colour : Colour.values()) {
      if (sixLines.chars().filter(ch -> ch == colour.toString().charAt(0)).count() != 9) {
        returnValue = false;
        break;
      }

    }
    return returnValue;
  }

  private boolean sideErrorUnknownCheck(final String sixLines) {
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

  private void addFaceColours(final String[] lines) {
    for (String s : lines) {
      switch (s.substring(4, 5)) {
        case "o" -> orangeSide.setMiniColourFaces(s);
        case "b" -> blueSide.setMiniColourFaces(s);
        case "r" -> redSide.setMiniColourFaces(s);
        case "g" -> greenSide.setMiniColourFaces(s);
        case "y" -> yellowSide.setMiniColourFaces(s);
        case "w" -> whiteSide.setMiniColourFaces(s);
        default -> throw new IllegalArgumentException("Unknown colour: " + s);
      }
    }
  }

  /**
   * takes in 6 lines which represent 6 sides - reading left-right, top-bottom (
   * This method turned out to be quite complicated.
   *
   * @param sixLines the 6 lines of the cube
   * @return a cube status indicating any problems with cube build
   */
  public CubeStatus buildCubeFromString(final String sixLines) {
    // get rid of whitespace
    // white space is allowed on constructing string but we must remove it here
    String formattedSixLines = sixLines.replace(" ", "").toLowerCase();
    // first let's do some initial validation on these string/s
    String[] lines = formattedSixLines.split("\n");

    if (lines.length != 6) {
      return CubeStatus.SIDE_ERROR_UNKNOWN;
    }

    if (!colourDistributionCheck(formattedSixLines)) {
      return CubeStatus.COLOUR_DISTRIBUTION_ERROR;
    }

    if (!sideErrorUnknownCheck(formattedSixLines)) {
      return CubeStatus.SIDE_ERROR_UNKNOWN;
    }

    // loop again and this time add face colours
    addFaceColours(lines);

    // now we need to calculate the miniface other axis colours with the info we
    // have
    Side[] sides = {
        orangeSide, blueSide, redSide, greenSide
    }; // first just iterate the 4 x-axis sides
    for (int index = 0; index < 4; index++) {
      // do tops and bottoms first
      for (int i = 0; i < 9; i++) {
        // there is no 4 as this is the center piece.
        if (i == 4) {
          continue;
        }
        switch (i) {
          case 0 -> {
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
          }
          case 1 -> {
            MiniFace miniFaceY = redSide.getMiniFace(0, 1);
            MiniFace miniFaceTop = yellowSide.getMiniFace(0, 1);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceY.getFaceColour());

            miniFaceY = orangeSide.getMiniFace(2, 1);
            MiniFace miniFaceBottom = whiteSide.getMiniFace(0, 1);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceY.getFaceColour());
          }
          case 2 -> {
            MiniFace miniFaceTop = yellowSide.getMiniFace(0, 2);
            MiniFace miniFaceX = blueSide.getMiniFace(0, 2);
            MiniFace miniFaceY = redSide.getMiniFace(0, 0);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour() + miniFaceY.getFaceColour());
            MiniFace miniFaceBottom = whiteSide.getMiniFace(0, 2);
            miniFaceY = orangeSide.getMiniFace(2, 2);
            miniFaceX = blueSide.getMiniFace(2, 0);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour());
          }
          case 3 -> {
            MiniFace miniFaceX = greenSide.getMiniFace(0, 1);
            MiniFace miniFaceTop = yellowSide.getMiniFace(1, 0);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour().toString());
            miniFaceX = greenSide.getMiniFace(2, 1);
            MiniFace miniFaceBottom = whiteSide.getMiniFace(1, 0);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour());
          }
          case 5 -> {
            MiniFace miniFaceTop = yellowSide.getMiniFace(1, 2);
            MiniFace miniFaceX = blueSide.getMiniFace(0, 1);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour().toString());
            miniFaceX = blueSide.getMiniFace(2, 1);
            MiniFace miniFaceBottom = whiteSide.getMiniFace(1, 2);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour());
          }
          case 6 -> {
            MiniFace miniFaceX = greenSide.getMiniFace(0, 2);
            MiniFace miniFaceY = orangeSide.getMiniFace(0, 0);
            MiniFace miniFaceTop = yellowSide.getMiniFace(2, 0);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour() + miniFaceY.getFaceColour().toString());
            MiniFace miniFaceBottom = whiteSide.getMiniFace(2, 0);
            miniFaceY = redSide.getMiniFace(2, 2);
            miniFaceX = greenSide.getMiniFace(2, 0);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour());
          }
          case 7 -> {
            MiniFace miniFaceY = orangeSide.getMiniFace(0, 1);
            MiniFace miniFaceTop = yellowSide.getMiniFace(2, 1);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceY.getFaceColour());

            miniFaceY = redSide.getMiniFace(2, 1);
            MiniFace miniFaceBottom = whiteSide.getMiniFace(2, 1);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceY.getFaceColour());
          }
          case 8 -> {
            MiniFace miniFaceX = blueSide.getMiniFace(0, 0);
            MiniFace miniFaceY = orangeSide.getMiniFace(0, 2);
            MiniFace miniFaceTop = yellowSide.getMiniFace(2, 2);
            miniFaceTop.setColours(miniFaceTop.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour().toString());
            MiniFace miniFaceBottom = whiteSide.getMiniFace(2, 2);
            miniFaceX = blueSide.getMiniFace(2, 2);
            miniFaceY = redSide.getMiniFace(2, 0);
            miniFaceBottom.setColours(miniFaceBottom.getFaceColour().toString()
                + miniFaceX.getFaceColour()
                + miniFaceY.getFaceColour());
          }
          default -> throw new IllegalArgumentException("Unexpected value: " + i);
        }

      }

      for (int i = 0; i < 9; i++) {
        // there is no 4 as this is the center piece.
        if (i == 4) {
          continue;
        }
        // create copy of top and bottom sides which we are going to use to rotate
        Side topSide = CUBE_UTILS.copySide(yellowSide);
        Side bottomSide = CUBE_UTILS.copySide(whiteSide);

        int bottomRotation = (4 - index);

        switch (i) {
          case 0 -> {
            MiniFace miniFace = sides[index].getMiniFace(0, 0);
            topSide.rotateSide(index);
            MiniFace miniFaceTop = topSide.getMiniFace(2, 0);

            MiniFace toTheLeft = sides[(index + 3) % 4].getMiniFace(0, 2);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + toTheLeft.getFaceColour()
                + miniFaceTop.getFaceColour().toString());
          }
          case 1 -> {
            topSide.rotateSide(index);
            MiniFace miniFace = sides[index].getMiniFace(0, 1);
            MiniFace miniFaceTop = topSide.getMiniFace(2, 1);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceTop.getFaceColour().toString());
          }
          case 2 -> {
            MiniFace miniFace = sides[index].getMiniFace(0, 2);
            topSide.rotateSide(index);
            MiniFace miniFaceTop = topSide.getMiniFace(2, 2);

            MiniFace toTheRight = sides[(index + 1) % 4].getMiniFace(0, 0);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + toTheRight.getFaceColour()
                + miniFaceTop.getFaceColour().toString());
          }
          case 3 -> {
            MiniFace miniFace = sides[index].getMiniFace(1, 0);
            MiniFace miniFaceLeft = sides[(index + 3) % 4].getMiniFace(1, 2);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceLeft.getFaceColour() + toString());
          }
          case 5 -> {
            MiniFace miniFace = sides[index].getMiniFace(1, 2);
            MiniFace toTheRight = sides[(index + 1) % 4].getMiniFace(1, 0);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + toTheRight.getFaceColour());
          }
          case 6 -> {
            MiniFace miniFace = sides[index].getMiniFace(2, 0);
            MiniFace miniFaceLeft = sides[(index + 3) % 4].getMiniFace(2, 2);
            bottomSide.rotateSide(bottomRotation);
            MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 0);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceLeft.getFaceColour()
                + miniFaceBottom.getFaceColour().toString());
          }
          case 7 -> {
            MiniFace miniFace = sides[index].getMiniFace(2, 1);
            bottomSide.rotateSide(bottomRotation);
            MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 1);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceBottom.getFaceColour().toString());
          }
          case 8 -> {
            MiniFace miniFace = sides[index].getMiniFace(2, 2);
            MiniFace miniFaceRight = sides[(index + 1) % 4].getMiniFace(2, 0);
            bottomSide.rotateSide(bottomRotation);
            MiniFace miniFaceBottom = bottomSide.getMiniFace(0, 2);
            miniFace.setColours(miniFace.getFaceColour().toString()
                + miniFaceRight.getFaceColour()
                + miniFaceBottom.getFaceColour().toString());
          }
          default -> throw new IllegalArgumentException("Unexpected value: " + i);
        }
      }
    }
    return CUBE_UTILS.validateCube(this);
  }

  /**
   * Gets a string that a gui could easily deal with to build a cube.
   *
   * @return Returns a display annotation which the gui can build and save cubes
   *         from - note this method not cater for orientation; it assumes orange
   *         front, yellow top, green left etc position.
   */
  public String getDisplayAnnotation() {

    String s = getOrangeSide().getAllColoursForSide()
        + getBlueSide().getAllColoursForSide()
        + getRedSide().getAllColoursForSide()
        + getGreenSide().getAllColoursForSide()
        + getYellowSide().getAllColoursForSide()
        + getWhiteSide().getAllColoursForSide();
    return s.trim();
  }

  /**
   * @param algorithm    the string to put in i.e. fc bc 2la dc uc rc etc
   * @param stopOnSolved a boolean to determine whether the algorithm stops
   *                     on a
   *                     solved state or just carries on
   * @return -1 (validation error), 0 (no solve), or any positive integer
   *         representing the number of actions for a solve
   */
  public int followAlgorithm(final String algorithm, final boolean stopOnSolved) {
    // tidy up algorithm i.e. remove extra whitespace
    String formattedAlgorithm = algorithm.replace("\n", " ").replace("  ", " ").toLowerCase().trim();
    String[] instructions = formattedAlgorithm.split(" ");

    // validate instructions before we start - i.e. if one fails, don't do any turns
    for (String instruction : instructions) {
      if (!Pattern.matches("[0-3]?[rlufdb][ac]", instruction)) {
        return -1;
      }
    }
    int instructionNumber = 1; // keeps track of number of instructions so that we know how long a solve takes
    for (String instruction : instructions) {
      String twoLetterInstruction;
      int numberOfTurns = 1; // default of one turn if not specified
      if (instruction.length() == 3) { // means there is a number of turns (between 1 and 3)
        numberOfTurns = Integer.parseInt(instruction.substring(0, 1));
        twoLetterInstruction = instruction.substring(1, 3);
      } else {
        twoLetterInstruction = instruction;
      }
      if (twoLetterInstruction.length() != 2) {
        throw new IllegalArgumentException("Instruction not valid");
      }
      switch (twoLetterInstruction) {
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

      if (stopOnSolved && CUBE_UTILS.checkSolvedState(this)) {
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
  private int random(final int min, final int max) {
    int range = max - min + 1;
    return RANDOM.nextInt(range) + min;
  }

  /**
   * Used random numbers to randomise cube.
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
