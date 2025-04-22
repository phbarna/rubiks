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
 * Note that this would have looked a bit neater with gson or jackson libraries.
 * But this would have involved an uber jar, making the final jar size about 8
 * time bigger ! So it seemed sensible to do my own json parsing for this small
 * use case.
 */
public class CubeSnapshotIO {
  private static final String FILENAME = "cubeSnapshot.json";

  /**
   * Private constuctor hides default one and avoids class being instantiated.
   */
  private CubeSnapshotIO() {

  }

  /**
   * Creates a default snapshot if the file isn't found or the snapshot is null.
   * 
   * @return a cube snapshot with default conditions.
   */
  private static CubeSnapshot createDefaultSnapshot() {
    // OY is Orange-front, yellow-top and is the default front/upwards orientation.
    return new CubeSnapshot("", "OY", Solved.SOLVED_ANNOTATION);
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
          // we replace the line feeds with spaces to make it valid json.
          snapshot.getDisplayAnnotation().replace("\n", " "));

      writer.write(json);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the snapshot of the cube from a json file, if it is null or no file etc
   * return a default cube state.git
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
      // We replace spaces with line feeds as this is what the gui deals with
      String displayAnnotation = json.split("\"displayAnnotation\":")[1].split("}")[0].replace("\"", "").trim()
          .replace(" ", "\n");

      snapshot = new CubeSnapshot(algorithm, orientation, displayAnnotation);
    } catch (FileNotFoundException e) {
      snapshot = createDefaultSnapshot();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (snapshot == null) {
      snapshot = createDefaultSnapshot();
    }
    return snapshot;
  }

}