package rubiks;

// 6 letters representing the 6 colours of the cube
enum Colour {
  O("o"), B("b"), R("r"), G("g"), Y("y"), W("w");
  private final String name;

  Colour(String name) {
    this.name = name;
  }

  /**
   * Lower case comes in, but we are checking the actual value of the enum
   *
   * @param s
   * @return
   */
  public static Colour ofName(String s) {
    return Colour.valueOf(s.toUpperCase());
  }

  @Override
  public String toString() {
    return name;
  }

}