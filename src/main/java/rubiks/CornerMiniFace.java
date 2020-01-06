package rubiks;

import java.util.HashSet;
import java.util.regex.*;

/**
 * corner squares have 3 faces
 */
public class CornerMiniFace extends MiniFace {

    protected Colour xAxisColour = null; // any other face left or right relative to the main face
    protected Colour yAxisColour = null; // any other face up or down relative the the main face.

    @Override
    public CornerMiniFace withColours(String colours) throws Exception  {
        if (!Pattern.matches("[rgbyow]{3}", colours)) {
            throw new Exception("Error trying to build corner with: " + colours);
        }
        faceColour = Colour.valueOf(colours.substring(0,1));
        xAxisColour = Colour.valueOf(colours.substring(1,2));
        yAxisColour = Colour.valueOf(colours.substring(2,3));

        return this;
    }

    /**
     * returns a string representation of the colours.
     * @return
     */
    @Override
    public String toString() {
        return faceColour.toString()+xAxisColour.toString()+yAxisColour;
    }

}
