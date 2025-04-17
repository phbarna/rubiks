package rubiks;

/**
 * 6 letters representing the 6 colours of the cube.
 */
enum Colour {
  // The 6 enums representing the 6 colours.
  O("o"), B("b"), R("r"), G("g"), Y("y"), W("w");

  /**
   * The enum name.
   */
  private final String name;

  /**
   * Class consructor which takes in a colour name.
   * 
   * @param name A constructor for the colour enum.
   */
  Colour(final String name) {
    this.name = name;
  }

  /**
   * Lower case comes in, but we are checking the actual value of the enum.
   *
   * @param s A single, lower case character of the colour comes in. Convert to
   *          upper case and use inBuilt valueOf method
   * @return Returns the colour represented by the lowerCase letter that comes in,
   *         i.e. 'r' returns Colour.R
   */
  public static Colour ofName(final String s) {
    return Colour.valueOf(s.toUpperCase());
  }

  @Override
  public String toString() {
    return name;
  }

}