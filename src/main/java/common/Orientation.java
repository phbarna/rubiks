package common;

/**
 * Contains all 24 possible orientations - makes life easier for the GUI to
 * render, so ghe gui package and rubiks package both need to see this.
 */
public enum Orientation {
  OY, BY, RY, GY,
  WO, BO, YO, GO,
  GW, RW, BW, OW,
  OG, YG, RG, WG,
  WB, RB, YB, OB,
  YR, BR, WR, GR
}
