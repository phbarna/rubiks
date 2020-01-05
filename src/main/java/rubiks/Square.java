package rubiks;

import java.util.regex.Pattern;

/**
 * a square ... parent of 2faced and 3 aced. Has one face
 * and is synominous with a side
 */
public class Square {
    protected Colour[] colours;

    public Square() {
        colours = new Colour[1];
    }

    protected Colour determineColour(String s) throws Exception {

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

    public Square withColours(String colourString) throws Exception {
        setColours(colourString);
        return this;
    }

    /**
     * returns the colours as an arrayt of colours
     *
     * @return
     */
    public Colour[] getColours() {
        return colours;
    }

    protected void setColours(String coloursString) throws Exception {
        coloursString = coloursString.toLowerCase();
        if (!Pattern.matches("[rgbwoy]", coloursString)) {
            throw new Exception("Cannot parse " + coloursString);
        }
        colours[0] = determineColour(coloursString);
    }

    /**
     * returns a string representation of the colours.
     * under dev
     * @return
     */
    public String toString() {
        return " "; // under dev
    }

}

