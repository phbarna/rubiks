package rubiks;

import java.util.HashSet;
import java.util.regex.Pattern;

public class EdgeMiniFace extends MiniFace {

    protected Colour otherAxisColour = null; // we don't know if it is an x-axis or y-axis but we know it it on the other axis to the face

    @Override
    protected EdgeMiniFace withColours(String colours) throws Exception {
        if (!Pattern.matches("[rgbyow]{2}",
                colours)) {
            throw new Exception("Error trying to build side - with: " + colours);
        }

        faceColour = Colour.valueOf(colours.substring(0,1));
        otherAxisColour = Colour.valueOf(colours.substring(1,2));

        return this;
    }



    /**
     * returns a string representation of the colours.
     * @return
     */
    @Override
    public String toString() {
       return faceColour.toString()+otherAxisColour.toString();
    }

}
