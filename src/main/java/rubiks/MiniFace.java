package rubiks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * a miniCube ... parent of 2 faced and 3 aced. Has one face
 * and is synominous with a side
 */
public class MiniFace {

    protected Colour faceColour = null; // the single colour of the face

    /**
     * tempBuffer - used as we switch the orientation of colours -
     * not actually used in parent class as there is only one colour (faceColour) - but it's children use it
     */
    protected Colour tempBuffer = null;

    protected MiniFace withColours(String colours) throws Exception {
        faceColour = Colour.valueOf(colours);
        return this;
    }

    public Colour[] getColours() {
        Colour[] colours = {faceColour};
        return colours;
    }

    /**
     * returns a string representation of the colours.
     * @return
     */
    public String toString() {
        return faceColour.toString();
    }

}

