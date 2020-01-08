package rubiks;

/**
 * Enum used to catch a descriptive reason for why building the cube (or validating the cube)
 * has gone wrong.  This is more useful for debugging. When it's working  wwould not expect to see all of these errors -
 * only those caused by a user trying to build a cube incorrecty.
 */
public enum CubeStatus {

    OK(0, "OK"),
    SIDE_VALIDATION_RED_SIDE_ERROR(1, "Error validating RED side"),
    SIDE_VALIDATION_BLUE_SIDE_ERROR(2, "Error validating BLUE side"),
    SIDE_VALIDATION_ORANGE_SIDE_ERROR(3, "Error validating ORANGE side"),
    SIDE_VALIDATION_GREEN_SIDE_ERROR(4, "Error validating GREEN side"),
    EDGE_MATCH_SAME_ERROR(5, "Detected an edge piece with duplicate colours"),
    EDGE_MATCH_ERROR(6, "Detected an edge piece which does not correspond"),
    CORNER_MATCH_SAME_ERROR(7, "Detected a corner piece which has duplicate colours"),
    CORNER_MATCH_ERROR(8, "Detected a corner which does not correspond"),
    EDGE_AND_CORNER_MATCH_ERROR(9, "Error - trying to match a corner with an edge !");


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
