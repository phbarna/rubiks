package rubiks;

import java.util.regex.Pattern;

/**
 * a square ... parent of 2faced and 3 aced. Has one face
 * and is synominous with a side
 */
public class Square {

    public Square() {

    }

    public Square withColours(String colourString) throws Exception {
        setColours(colourString);
        return this;
    }

    public void setColours(String coloursString) throws Exception {
        coloursString = coloursString.toLowerCase();
        if (!Pattern.matches("[rgbwoy]", coloursString)) {
            throw new Exception("Cannot parse " + coloursString);
        }
    }


    }

