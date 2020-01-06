package rubiks;

import java.util.regex.Pattern;

/**
 * a miniCube ... parent of 2 faced and 3 aced. Has one face
 * and is synominous with a side
 */
public class MiniFace {

    protected Colour faceColour = null; // the single colour of the face

    protected MiniFace withColours(String colours) throws Exception {
        if (!Pattern.matches("[rgbyow]", colours)) {
            throw new Exception("Error trying to build center colour - with: " + colours);
        }
        faceColour = Colour.valueOf(colours);
        return this;
    }

    protected Colour determineFace(String s) throws Exception {

        s = s.toLowerCase();
        switch (s) {
            case ("w"): {
                return Colour.w;
            }
            case ("b"): {
                return Colour.b;
            }
            case ("g"): {
                return Colour.g;
            }
            case ("o"): {
                return Colour.o;
            }
            case ("y"): {
                return Colour.y;
            }
            case ("r"): {
                return Colour.r;
            }
        }
        throw new Exception("Error determining colour"); // should never get here
    }


    /**
     * returns a string representation of the colours.
     * @return
     */
    public String toString() {
        return faceColour.toString();
    }

}

