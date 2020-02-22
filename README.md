# rubiks
This program simulates a 3x3 rubiks cube.

1. The default position of the cube is: orange is face is forward, white face is down, yellow face is up. This is how the cube engine sees it. 

2. You can drag into any orientation to see any of the sides from any angle.

3. You can put in an algorithm consisting any of the following turns.
  rc lc uc dc fc bc (standing for right clockwise, left clockwise, upper clockwise, front clockwise, back clocksiwe)
  - each of these has a corresponding anticlocksise move (e.g. ra - right anticlockwise)
  - any of these turns can be preceeded by a number e.g. 3rc would equate to 3 right clockwise turns (which = ra)
  - and put in consecutive turns in to the text area e.g. "rc lc 2ua" 
  
4. You can build your own cube. Each face is read left to right, top to bottom.
  
5. You can copy the contents of your current cube into the the build cube area (which gives an example of how to build your cube).
 
Solving is not implemented yet - but will be available in a future version.
