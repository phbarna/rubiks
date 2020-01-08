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
    public CornerMiniFace withColours(String colours)   throws Exception {
        faceColour = Colour.valueOf(colours.substring(0, 1));
        xAxisColour = Colour.valueOf(colours.substring(1, 2));
        yAxisColour = Colour.valueOf(colours.substring(2, 3));

        HashSet<Colour> hs = new HashSet<Colour>();
        hs.add(faceColour);
        hs.add(xAxisColour);
        hs.add(yAxisColour);
        if (hs.size() != 3)
        {
            throw new Exception("Corner face must have 3 distinct colours: - " + faceColour + " " + xAxisColour + " "+yAxisColour + " - not valid");
        }
        return this;
    }

    @Override
    public Colour[] getColours() {
        Colour[] colours = {faceColour, xAxisColour, yAxisColour};
        return colours;
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
