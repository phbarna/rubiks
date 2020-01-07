package rubiks;

/**
 * Enum used to catch a descriptive reason for why building the cube (or validating the cube)
 * has gone wrong.  This is more useful for debugging whilst developping. When it's working I
 * would not expect to see all of these errors - only those caused by a user trying to build a cube incorrecty.
 */
public enum CubeStatus {

    OK(0, "OK"),
    SIDE_VALIDATION_ERROR(7, "Error validating a side"),
    SIDE_NOT_UNIQUE_ERROR(5, "One or more sides have dupicate mini cubes"),
    EDGE_MATCH_SAMNE_ERROR(1, "Two or more edge faces have the same colours"),
    EDGE_MATCH_ERROR(3, "Two or more edge faces not match"),
    CORNER_MATCH_SAME_ERROR(2, "Two or more corner faces have the same colours"),
    CORNER_MATCH_ERROR(4, "Two or more corner faces do not match"),
    EDGE_AND_CORNER_MATCH_ERROR(6, "Trying to match a corner with an edge");

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
