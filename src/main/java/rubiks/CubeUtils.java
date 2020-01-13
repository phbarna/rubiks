package rubiks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * used by the cube - just extracts some functionality from the Cube class to make the actual Cube class more readable
 */
public class CubeUtils {

    /**
     * creats a deep coopy of a side - so that we don't end up modifying the same reference
     * @param originalSide
     * @return
     * @throws Exception
     */
    public Side copySide(Side originalSide) throws Exception {

        Side copy = new Side().withColour(originalSide.getColour());
        String test = originalSide.toString();

        List<MiniFace> miniFaceList = new ArrayList<>();
        MiniFace[] row = new MiniFace[3];
        MiniFace[] col = new MiniFace[3];
        for (int r = 0; r < 3; r++) {

            for (int c = 0; c < 3; c++) {
                if (c == 1 && r == 1) { // center piece
                    MiniFace miniFace = new CentreAxleMiniFace().withColours(originalSide.getMiniFace(r, c).toString());
                    miniFaceList.add(miniFace);
                } else if (c == 1 || r == 1) {   // edge piece
                    MiniFace miniFace = new EdgeMiniFAce().withColours(originalSide.getMiniFace(r, c).toString());
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
     * @param cube
     * @return true or false
     */
    public boolean checkSolvedState(Cube cube) {
        // need to check any 5 sides
        if (cube.getOrangeSide().checkSolvedSide() &&
                cube.getGreenSide().checkSolvedSide() &&
                cube.getBlueSide().checkSolvedSide() &&
                cube.getRedSide().checkSolvedSide() &&
                cube.getYellowSide().checkSolvedSide()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check that the 3 or 2 colours match but NOT in the right order - a right order match is a fail !
     *
     * @param face1
     * @param face2
     * @return
     */
    public CubeStatus checkMiniFaceMatch(MiniFace face1, MiniFace face2) {

        // immediate fail - cannot match edges with corners etc
        if (face1.getColours().length != face2.getColours().length) {
            return CubeStatus.EDGE_AND_CORNER_MATCH_ERROR;
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
        if (numMatches == face1Colours.length) { // implies that all the colours from both cubes match which cannot happen
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
    public CubeStatus validateCorners(Cube cube) throws Exception {
        MiniFace miniFaceTop = null;
        MiniFace miniFaceBottom = null;
        Side[] sides = {cube.getOrangeSide(), cube.getBlueSide(), cube.getRedSide(), cube.getGreenSide()};
        for (int index = 0; index < sides.length; index++) {

            Side topSide = copySide(cube.getYellowSide());
            Side bottomSide = copySide(cube.getWhiteSide());
            topSide.rotateFace(index);
            bottomSide.rotateFace(4-index);

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

            // now do top corners
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
    public CubeStatus validateEdges(Cube cube) throws Exception {

        MiniFace miniFaceTop = null;
        MiniFace miniFaceBottom = null;
        Side[] sides = {cube.getOrangeSide(), cube.getBlueSide(), cube.getRedSide(), cube.getGreenSide()};
        for (int index = 0; index< sides.length; index++) {

            Side topSide = copySide(cube.getYellowSide());
            Side bottomSide = copySide(cube.getWhiteSide());
            topSide.rotateFace(index);
            bottomSide.rotateFace(4-index);
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
        return CubeStatus.OK;
    }

    /**
     * validates the whole cube by matching all edges and corners.
     * If validation fails it returns a CubeStatus enum with appropriate error code.
     * If validation passes it returns "ok"
     *
     * @param cube
     * @return cubeStatus
     */
    public CubeStatus validateCube(Cube cube) throws Exception {

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
