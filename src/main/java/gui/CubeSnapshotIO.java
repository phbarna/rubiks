package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import common.Solved;

/**
 * Used for reading/writing the state of the cube to a json file.
 * Note that this would have looked a bit neater with gson or jackson libraries
 * but I felt they would have bloated the final jar file so it seemed more
 * appropriate to write my own.
 */
public class CubeSnapshotIO {
  private static final String FILENAME = "cubeSnapshot.json";

  /**
   * Private constuctor hides default one and avoids class being instantiated.
   */
  private CubeSnapshotIO() {

  }

  /**
   * Creates a default snapshot if the file isn't found or the snapshot is null
   * for some reason.
   * 
   * @return a cube snapshot with default conditions.
   */
  private static CubeSnapshot createDefault() {
    return new CubeSnapshot("", "oy", Solved.SOLVED_ANNOTATION);
  }

  /**
   * Writes the snapshot of the cube to a file as json.
   * 
   * @param snapshot a snapshot of the cube
   */
  public static void writeToFile(CubeSnapshot snapshot) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
      String json = String.format("""
          {
            "algorithm": "%s",
            "orientation": "%s",
            "displayAnnotation": "%s"
          }
          """,
          snapshot.getAlgorithm(),
          snapshot.getOrientation(),
          snapshot.getDisplayAnnotation());

      writer.write(json);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the snapshot of the cube from a json file, if it is null return a
   * default cube.
   * 
   * @return A snapshot of the cube state.
   */
  public static CubeSnapshot readFromFile() {
    CubeSnapshot snapshot = null;
    try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
      StringBuilder jsonBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        jsonBuilder.append(line).append("\n");
      }
      String json = jsonBuilder.toString();
      // Simple parsing: assuming the JSON format is valid and well-formed
      String algorithm = json.split("\"algorithm\":")[1].split(",")[0].replace("\"", "").trim();
      String orientation = json.split("\"orientation\":")[1].split(",")[0].replace("\"", "").trim();
      String displayAnnotation = json.split("\"displayAnnotation\":")[1].split("}")[0].replace("\"", "").trim();

      snapshot = new CubeSnapshot(algorithm, orientation, displayAnnotation);
    } catch (FileNotFoundException e) {
      snapshot = createDefault();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (snapshot == null) {
      snapshot = createDefault();
    }
    return snapshot;
  }

}