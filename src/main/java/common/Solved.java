package common;

/**
 * A class for storing the solved annotation text which is used by the gui
 * package and also the rubiks package.
 */
public class Solved {

  /**
   * Private constuctor hides default one and avoids class being instantiated.
   */
  private Solved() {

  }

  // Represents the cube in a solved state.
  public static final String SOLVED_ANNOTATION = """
      ooooooooo
      wwwwwwwww
      bbbbbbbbb
      rrrrrrrrr
      ggggggggg
      yyyyyyyyy
      """;

}
