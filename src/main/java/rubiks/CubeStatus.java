package rubiks;

/**
 * Enum used to catch a descriptive reason for why building the cube (or
 * validating the cube)
 * has gone wrong. This is more useful for debugging. When it's working would
 * not expect to see all of these errors -
 * only those caused by a user trying to build a cube incorrectly.
 */
public enum CubeStatus {

  OK("OK"),
  EDGE_MATCH_SAME_ERROR("Detected an edge piece with duplicate colours"),
  EDGE_MATCH_ERROR("Detected an edge piece which does not correspond with neighbour"),
  CORNER_MATCH_SAME_ERROR("Detected a corner piece which has duplicate colours"),
  CORNER_MATCH_ERROR("Detected a corner which does not correspond with neighbour/s"),
  EDGE_AND_CORNER_MATCH_ERROR("Error - trying to match a corner with an edge !"),
  SIDE_ERROR_UNKNOWN("Error - One or more sides has validation error/s"),
  COLOUR_DISTRIBUTION_ERROR("Colour distribution is not equal to 9 per colour."),
  CUBE_NOT_BUILT_ERROR("The cube does not appear to be built correctly."),
  OPPOSITE_SIDES_ERROR("Error - cannot have opposite sides touching.");

  private final String description;

  CubeStatus(String description) {

    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
