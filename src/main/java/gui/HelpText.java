package gui;

/**
 * A container for holding the help text.
 */
class HelpText {
    final static String text =  "Rubiks Cube written by Pete Barnard - 7th February 2020" +
            "\n\n" +
            "Help \n" +
            "====\n" +
            "1. The cube's default front/upright orientation is orange front, yellow top, green left - " +
            " you can put it back to this state by clicking the Orientate Forward/Up button\n" +
            "-- but can drag to move it rotate it to a different orientation.\n" +
            "2. The algorithm text box allows you to put in the following turns fc bc lc rc uc dc -- these stand for front clockwise, back clockwise, left clockise, right clockwise, \n" +
            "-- upper clockwise and down clockwise" +
            "- all turns are clockwise as if you are looking at the cube face..." +
            "each turn has a corresponding anticlockwise move so front anticlockwise would equate to fa\n " +
            "-- you can also put a number in front of the move to indicate number of turns e.g. 3da is equivalent to dc" +
            "the turns can be run consecutively for example fc fa would return the cube to it's original state.\n" +
            "3. You can build your own cube from a string. To do this hold your cube in front/upright and " +
            "put in the orange face from left to right, top to bottom order, then rotate the cube putting in the blue, \n" +
            "-- red, green in exactly the same way.  Then with orange facing you tilt the cube down so that yellow face is facing " +
            "you.  \n" +
            "-- then tilt the cube up to the white face and enter the" +
            " white face letters.  Click build and you should see your cube build.\n" +
            "4. You can save the contents of your cube state in to the build cube text area at any time. ENJOY :-";
}
