package rubiks;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * used by the cube - just extracts some functionality from the Cube class to make the actual Cube class more readable
 */
class CubeUtils {

    /**
     * creats a deep coopy of a side - so that we don't end up modifying the same reference
     * @param originalSide the side which is passed in.
     * @return a copy of the side
     */
    Side copySide(Side originalSide)  {

        Side copy = new Side().withColour(originalSide.getColour());

        List<MiniFace> miniFaceList = new ArrayList<>();
        for (int r = 0; r < 3; r++) {

            for (int c = 0; c < 3; c++) {
                if (c == 1 && r == 1) { // center piece
                    MiniFace miniFace = new CentreAxleMiniFace().withColours(originalSide.getMiniFace(r, c).toString());
                    miniFaceList.add(miniFace);
                } else if (c == 1 || r == 1) {   // edge piece
                    MiniFace miniFace = new EdgeMiniFace().withColours(originalSide.getMiniFace(r, c).toString());
                    miniFaceList.add(miniFace);

                } else { // corner piece
                    MiniFace miniFace = new CornerMiniFace().withColours(originalSide.getMiniFace(r, c).toString());
                    miniFaceList.add(miniFace);
                }
            }
        }

        MiniFace[] topRow = {miniFaceList.get(0), miniFaceList.get(1), miniFaceList.get(2)};

        MiniFace[] middleRow = {miniFaceList.get(3), miniFaceList.get(4), miniFaceList.get(5)};

        MiniFace[] bottomRow = {miniFaceList.get(6), miniFaceList.get(7), miniFaceList.get(8)};

        copy.setRow(0, topRow);
        copy.setRow(1, middleRow);
        copy.setRow(2, bottomRow);

        return copy;
    }

    /**
     * checks solved state of the whole cube
     *
     * @param cube - a reference to our cube, which we are about to check whether it is in a solved state
     * @return true or false
     */
    boolean checkSolvedState(Cube cube) {
        // need to check any 5 sides
        if (!cube.getOrangeSide().checkSolvedSide() ||
                !cube.getGreenSide().checkSolvedSide()) {
            return false;
        } else if (!cube.getBlueSide().checkSolvedSide() ||
                !cube.getRedSide().checkSolvedSide()) {
            return false;
        } else if (cube.getRedSide().checkSolvedSide()) {
            return true;
        }
        return true;
    }

    /**
     * completes the validation process by making sure the minicubes do not have 2 colours
     * which are opposite sides of the cube so should never match
     * @param miniFace1
     * @param miniFace2
     * @return
     */
    boolean validateNotOppositeSides(MiniFace miniFace1, MiniFace miniFace2) {

        // check all colours don't have a corresponding opposite side match
        for (Colour colour: miniFace1.getColours()) {

            if (colour.equals(Colour.g)) {
                boolean match = Arrays.stream(miniFace2.getColours()).anyMatch(c -> c.equals(Colour.b));
                if (match)
                    return false;
            }
            if (colour.equals(Colour.b)) {
                boolean match = Arrays.stream(miniFace2.getColours()).anyMatch(c -> c.equals(Colour.g));
                if (match)
                    return false;
            }
            if (colour.equals(Colour.w)) {
                boolean match = Arrays.stream(miniFace2.getColours()).anyMatch(c -> c.equals(Colour.y));
                if (match)
                    return false;
            }
            if (colour.equals(Colour.r)) {
                boolean match = Arrays.stream(miniFace2.getColours()).anyMatch(c -> c.equals(Colour.o));
                if (match)
                    return false;
            }
            if (colour.equals(Colour.o)) {
                boolean match = Arrays.stream(miniFace2.getColours()).anyMatch(c -> c.equals(Colour.r));
                if (match)
                    return false;
            }
            if (colour.equals(Colour.g)) {
                boolean match = Arrays.stream(miniFace2.getColours()).anyMatch(c -> c.equals(Colour.b));
                if (match)
                    return false;
            }
        }

        return true;
    }

    /**
     * check that the 3 or 2 colours match but NOT in the right order - a right order match is a fail !
     * Also check that opposite sidea are not touching
     *
     * @param face1
     * @param face2
     * @return
     */
    private CubeStatus checkMiniFaceMatch(MiniFace face1, MiniFace face2) {

        // immediate fail - cannot match edges with corners
        if (face1.getColours().length != face2.getColours().length) {
            return CubeStatus.EDGE_AND_CORNER_MATCH_ERROR;
        }

        boolean opposite4SidesOK = validateNotOppositeSides(face1, face2);

        if (!opposite4SidesOK) {
            return CubeStatus.OPPOSITE_SIDES_ERROR;
        }

        HashSet<Colour> colursHS = new HashSet<>(); // will ensure all the colours match
        Colour[] face1Colours = face1.getColours();
        Colour[] face2Colours = face2.getColours();
        int numMatches = 0;
        for (int i = 0; i < face1Colours.length; i++) {
            colursHS.add(face1Colours[i]);
            colursHS.add(face2Colours[i]);
            if (face1Colours[i].equals(face2Colours[i])) {
                numMatches++;
            }
        }
        if (numMatches == face1Colours.length) { // implies that all the colours from both minicunbes match which cannot happen
            if (face1Colours.length == 2)
                return CubeStatus.EDGE_MATCH_SAME_ERROR;
            else
                return CubeStatus.CORNER_MATCH_SAME_ERROR;
        }

        if ((colursHS.size() == face1.getColours().length) &&
                (colursHS.size() == face2.getColours().length)
        ) {
            return CubeStatus.OK;
        } else {
            if (face1Colours.length == 2)
                return CubeStatus.EDGE_MATCH_ERROR;
            else
                return CubeStatus.CORNER_MATCH_ERROR;
        }
    }

    /**
     * validate that the all 8 corners of the cube match and don't have duplicate colours
     *
     * @param cube
     * @return
     */
    private CubeStatus validateCorners(Cube cube)  {
        MiniFace miniFaceTop;
        MiniFace miniFaceBottom;
        Side[] sides = {cube.getOrangeSide(), cube.getBlueSide(), cube.getRedSide(), cube.getGreenSide()};
        for (int index = 0; index < sides.length; index++) {

            Side topSide = copySide(cube.getYellowSide());
            Side bottomSide = copySide(cube.getWhiteSide());
            topSide.rotateSide(index);
            bottomSide.rotateSide(4-index);

            int nextSideIndex = (index + 1) % 4; // modulus ensures we can loop around - and compare corners 4 with side 0 at the end of loop
            MiniFace miniFace1 = sides[index].getMiniFace(0, 2);
            MiniFace miniFace2 = sides[nextSideIndex].getMiniFace(0, 0);
            CubeStatus errorStatus = checkMiniFaceMatch(miniFace1, miniFace2);
            if (!errorStatus.equals(CubeStatus.OK)) {
                return errorStatus;
            }

            miniFaceTop = topSide.getMiniFace(2, 2);

            errorStatus = checkMiniFaceMatch(miniFace1, miniFaceTop);
            if (!errorStatus.equals(CubeStatus.OK)) {
                return errorStatus;
            }

            // now do bottom corners
            miniFace1 = sides[index].getMiniFace(2, 2);
            miniFace2 = sides[nextSideIndex].getMiniFace(2, 0);
            errorStatus = checkMiniFaceMatch(miniFace1, miniFace2);
            if (!errorStatus.equals(CubeStatus.OK)) {
                return errorStatus;
            }
            miniFaceBottom = bottomSide.getMiniFace(0, 2);

            errorStatus = checkMiniFaceMatch(miniFace1, miniFaceBottom);
            if (!errorStatus.equals(CubeStatus.OK)) {
                return errorStatus;
            }
        }
        return CubeStatus.OK;
    }

    /**
     * checks all edges (top ones and and bottomones) match or don't have duplicate colours
     */
    private CubeStatus validateEdges(Cube cube)  {

        MiniFace miniFaceTop;
        MiniFace miniFaceBottom;
        Side[] sides = {cube.getOrangeSide(), cube.getBlueSide(), cube.getRedSide(), cube.getGreenSide()};
        for (int index = 0; index< sides.length; index++) {

            Side topSide = copySide(cube.getYellowSide());
            Side bottomSide = copySide(cube.getWhiteSide());
            topSide.rotateSide(index);
            bottomSide.rotateSide(4-index);
            // validate top edges first
            MiniFace miniFace = sides[index].getMiniFace(0, 1);
            miniFaceTop = topSide.getMiniFace(2, 1);

            CubeStatus errorStatus = checkMiniFaceMatch(miniFace, miniFaceTop);
            if (!errorStatus.equals(CubeStatus.OK)) {
                return errorStatus;
            }
            // now validate bottom edges
            miniFaceBottom = bottomSide.getMiniFace(0,1);
            miniFace = sides[index].getMiniFace(2, 1);

            errorStatus = checkMiniFaceMatch(miniFace, miniFaceBottom);

            if (!errorStatus.equals(CubeStatus.OK)) {
                return errorStatus;
            }
        }
        //validate middle edges
        MiniFace miniface1 = cube.getOrangeSide().getMiniFace(1,2);
        MiniFace miniface2 = cube.getBlueSide().getMiniFace(1,0);
        CubeStatus errorStatus = checkMiniFaceMatch(miniface1, miniface2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniface1 = cube.getBlueSide().getMiniFace(1,2);
        miniface2 = cube.getRedSide().getMiniFace(1,0);
        errorStatus = checkMiniFaceMatch(miniface1, miniface2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniface1 = cube.getRedSide().getMiniFace(1,2);
        miniface2 = cube.getGreenSide().getMiniFace(1,0);
        errorStatus = checkMiniFaceMatch(miniface1, miniface2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }
        miniface1 = cube.getGreenSide().getMiniFace(1,2);
        miniface2 = cube.getOrangeSide().getMiniFace(1,0);
        errorStatus = checkMiniFaceMatch(miniface1, miniface2);
        if (!errorStatus.equals(CubeStatus.OK)) {
            return errorStatus;
        }

        // valudate

        return CubeStatus.OK;
    }

    /**
     * reverses the 3 column (or row array) - need this for rotating the face
     * @param rowCol the row or column to reverse
     * @return reverse of miniface.
     */
    MiniFace[] reverseRowCol(MiniFace[] rowCol) {
        MiniFace[] returnMiniFace = new MiniFace[3];

        returnMiniFace[0] = new CornerMiniFace().withColours(rowCol[2].toString());
        returnMiniFace[2] = new CornerMiniFace().withColours(rowCol[0].toString());
        returnMiniFace[1] = new EdgeMiniFace().withColours(rowCol[1].toString());

        return returnMiniFace;

    }

    void rotateRowColFaces(MiniFace[] miniFaces, int numTurns) {
        for (int i = 0; i< 3; i++) {
            miniFaces[i].rotateColours(numTurns);
        }
    }

    MiniFace[] makeRowColCopy(MiniFace[] rowCol) {
        MiniFace[] copy = new MiniFace[3];
        for (int i = 0; i < 3; i++) {

            if (i == 1) { // it's an edge
                copy[i] = new EdgeMiniFace().withColours(rowCol[i].toString());
            } else { // it's a corner
                copy[i] = new CornerMiniFace().withColours(rowCol[i].toString());
            }
        }
        return copy;
    }

    /**
     * validates the whole cube by matching all edges and corners.
     * If validation fails it returns a CubeStatus enum with appropriate error code.
     * If validation passes it returns "ok"
     *
     * @param cube the cube to validate
     * @return cubeStatus
     */
    CubeStatus validateCube(Cube cube) {

        if (null == cube.getYellowSide().getRow(0)) {
              return CubeStatus.CUBE_NOT_BUILT_ERROR;
        }

        CubeStatus cubeStatus = validateCorners(cube);
        if (!cubeStatus.equals(CubeStatus.OK)) {
            return cubeStatus;
        }

        cubeStatus = validateEdges(cube);
        if (!cubeStatus.equals(CubeStatus.OK)) {
            return cubeStatus;
        }

        return CubeStatus.OK;
    }
}
