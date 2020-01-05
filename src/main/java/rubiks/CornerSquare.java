package rubiks;

import java.util.HashSet;
import java.util.regex.*;

/**
 * corner squares have 3 faces
 */
public class CornerSquare extends Square {

    public Colour[] getColours() {
        return colours;
    }

    public CornerSquare() {
        colours = new Colour[3];
    }

    public CornerSquare withColours(String colours) throws Exception {
        setColours(colours);
        return this;
    }

    /**
     * pass the colours as a string for simplicity i.e. rgb  (red, green, blue)
     * @param coloursString - a space delimited list of colours r g b w o g where there has to be 3 letters
     */
    @Override
    public void setColours(String coloursString) throws Exception {
        coloursString = coloursString.toLowerCase();
        if (!Pattern.matches("[rgbwoy][rgbwoy][rgbwoy]", coloursString)) {
            throw new Exception("Cannot parse "+coloursString);
        }
        String[] colourArray = coloursString.split("");

        // more validation - make sure no duplicates from r g b w o g
        HashSet<String> hm = new HashSet<>();
        for (String string: colourArray) {
            hm.add(string);
        }
        if (hm.size() != 3) {
            throw new Exception("Cannot parse "+coloursString);
        }
        for (int i = 0; i< 3; i++) {
            colours[i] = determineColour(colourArray[i]);
        }
    }

}
