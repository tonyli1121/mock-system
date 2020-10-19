package fileSystems;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * SavedFile is a save format for the SavedFileSystem.
 * 
 * @author Xuezhi Yan
 */
public class SavedFile {
  /**
   * the path that contains the file
   */
  private String path;
  /**
   * the name of the file
   */
  private String name;
  /**
   * the data stored in the file
   */
  private String data;

  /**
   * initialized its name, data and path
   * 
   * @param path save path
   * @param name file name
   * @param data file data
   */
  public SavedFile(String path, String name, String data) {
    this.name = name;
    this.data = data;
    this.path = path;
  }

  /**
   * Save the directory in a JSON format into the target SavedSystem
   * 
   * @param savedSystem the target System
   */
  @SuppressWarnings("unchecked")
  public void saveToJSON(JSONArray savedSystem) {
    JSONObject fileInfo = new JSONObject();

    fileInfo.put("type", "file");
    fileInfo.put("path", this.path);
    fileInfo.put("name", this.name);
    fileInfo.put("data", this.data);
    
    savedSystem.add(fileInfo);
  }

}
