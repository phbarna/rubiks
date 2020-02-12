package rubiks;

/**
 * Enum used to catch a descriptive reason for why building the cube (or validating the cube)
 * has gone wrong.  This is more useful for debugging. When it's working would not expect to see all of these errors -
 * only those caused by a user trying to build a cube incorrectly.
 */
public enum CubeStatus {

    OK(0, "OK"),
    EDGE_MATCH_SAME_ERROR(1, "Detected an edge piece with duplicate colours"),
    EDGE_MATCH_ERROR(2, "Detected an edge piece which does not correspond with neighbour"),
    CORNER_MATCH_SAME_ERROR(3, "Detected a corner piece which has duplicate colours"),
    CORNER_MATCH_ERROR(4, "Detected a corner which does not correspond with neighbour/s"),
    EDGE_AND_CORNER_MATCH_ERROR(5, "Error - trying to match a corner with an edge !"),
    SIDE_ERROR_UNKNOWN(6, "Error - One or more sides has validation error/s"),
    COLOUR_DISTRIBUTION_ERROR(7, "Colour distribution is not equal to 9 per colour."),
    CUBE_NOT_BUILT_ERROR(8, "The cube does not appear to be built correctly."),
    OPPOSITE_SIDES_ERROR(9, "Cannot have opposite sides touching.");
    private final int code;
    private final String description;

    CubeStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
