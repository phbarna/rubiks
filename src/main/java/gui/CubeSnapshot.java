package gui;

/**
 * A pojo for representing the state of the cube for reading/writing to a file
 */
public class CubeSnapshot {
  private String algorithm;
  private String orientation;
  private String displayAnnotation;

  /**
   * Constructor which takes all parameters for this class.
   */
  public CubeSnapshot(String algorithm, String orientation, String displayAnnotation) {
    this.algorithm = algorithm;
    this.orientation = orientation;
    this.displayAnnotation = displayAnnotation;
  }

  // Getter for algorithm
  public String getAlgorithm() {
    return algorithm;
  }

  // Setter for algorithm
  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  // Getter for orientation
  public String getOrientation() {
    return orientation;
  }

  public void setOrientation(String orientation) {
    this.orientation = orientation;
  }

  public String getDisplayAnnotation() {
    return displayAnnotation;
  }

  public void setDisplayAnnotation(String displayAnnotation) {
    this.displayAnnotation = displayAnnotation;
  }

}