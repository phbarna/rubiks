package rubiks;

/**
 * Enum used to catch a descriptive reason for why building the cube (or validating the cube)
 * has gone wrong.  This is more useful for debugging. When it's working  wwould not expect to see all of these errors -
 * only those caused by a user trying to build a cube incorrecty.
 */
public enum CubeStatus {

    OK(0, "OK"),
    EDGE_MATCH_SAME_ERROR(1, "Detected an edge piece with duplicate colours"),
    EDGE_MATCH_ERROR(2, "Detected an edge piece which does not correspond with neighbour"),
    CORNER_MATCH_SAME_ERROR(3, "Detected a corner piece which has duplicate colours"),
    CORNER_MATCH_ERROR(4, "Detected a corner which does not correspond with neighbour/s"),
    EDGE_AND_CORNER_MATCH_ERROR(5, "Error - trying to match a corner with an edge !"),
    PIECES_NOT_UNIQUE_ERROR(17, "Error - detected a piece with duplicate sides"),
    SIDE_ERROR_UNKNOWN(7, "Error - One or more sides has validation error/s"),
    COLOUR_DISTRIBUTION_ERROR(8, "Colour distribution is not equal to 9 per colour."),
    CUBE_NOT_BUILT_ERROR(9, "The cube does not appear to be built correctly.");

    private final int code;
    private final String description;

    private CubeStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
