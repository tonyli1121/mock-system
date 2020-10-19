package fileSystems;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * SavedDirectory is a save format for the SavedFileSystem.
 * 
 * @author Xuezhi Yan
 */
public class SavedDirectory {
  /**
   * the name of the target directory
   */
  private String name;
  /**
   * the path of the saved directory
   */
  private String path;

  /**
   * initialized its name and path
   * 
   * @param path saved path
   * @param name dir name
   */
  public SavedDirectory(String path, String name) {
    this.name = name;
    this.path = path;
  }

  /**
   * Save the directory in a JSON format into the target SavedSystem
   * 
   * @param savedSystem the target System
   */
  @SuppressWarnings("unchecked")
  public void saveToJSON(JSONArray savedSystem) {
    JSONObject dirInfo = new JSONObject();

    dirInfo.put("type", "directory");
    dirInfo.put("path", this.path);
    dirInfo.put("name", this.name);

    savedSystem.add(dirInfo);
  }
}
