package rubiks;

import java.util.HashSet;
import java.util.regex.*;

/**
 * corner squares have 3 faces
 */
public class CornerSquare extends Square {

    private Colour[] colours = new Colour[3];

    public Colour[] getColours() {
        return colours;
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
        if (!Pattern.matches("[rgbwoy] [rgbwoy] [rgbwoy]", coloursString)) {
            throw new Exception("Cannot parse "+coloursString);
        }
        String[] colourArray = coloursString.split(" ");

        // more validation - make sure no duplicates from r g b w o g
        HashSet<String> hm = new HashSet<>();
        for (String string: colourArray) {
            hm.add(string);
        }
        if (hm.size() != 3) {
            throw new Exception("Cannot parse "+coloursString);
        }
        for (String theColour:colourArray) {
            switch (theColour) {
                case("w"): {
                    colours[0] = Colour.WHITE;
                    break;
                }
                case("b"): {
                    colours[0] = Colour.BLUE;
                    break;
                }
                case("G"): {
                    colours[0] = Colour.GREEN;
                    break;
                }
                case("O"): {
                    colours[0] = Colour.ORANGE;
                    break;
                }
                case("Y"): {
                    colours[0] = Colour.YELLOW;
                    break;
                }
                case("R"): {
                    colours[0] = Colour.RED;
                    break;
                }
            }

        }

    }







}
