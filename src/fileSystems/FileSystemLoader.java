package fileSystems;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Commands.HistoryTracker;

/**
 * The FileSystemLoader is a static class that loads data into a file system.
 * 
 * @author Xuezhi Yan
 */
public class FileSystemLoader {

  /**
   * Load the file at fileToRead into the current fileSystem and history, including the path stack,
   * history, directories, files and current path.
   * 
   * error code: 0: successful 1: file not found 2: input stream interrupted 3: file type mismatch
   * 
   * @param fileToRead
   * @param fs
   * @return the error code
   */
  public static int loadToFileSystem(String fileToRead, FileSystem fs, HistoryTracker history) {
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader(fileToRead)) {
      Object obj = jsonParser.parse(reader);

      JSONArray systemData = (JSONArray) obj;
      for (Object saveData : systemData) {
        addNewObject((JSONObject) saveData, fs, history);
      }
      return 0;

    } catch (FileNotFoundException e) {
      return 1;
    } catch (IOException e) {
      return 2;
    } catch (org.json.simple.parser.ParseException e) {
      return 3;
    }
  }

  /**
   * Pass the JSON object to each corresponding loaders.
   * 
   * @param obj the current reading JSON object
   * @param fs the current file system
   * @param history the current history object
   * @return false if load failed, otherwise true
   */
  private static boolean addNewObject(JSONObject obj, FileSystem fs, HistoryTracker history) {
    if (obj.get("type").equals("file")) {
      if (!addFile(obj, fs)) {
        return false;
      }
    }
    if (obj.get("type").equals("directory")) {
      if (!addDirectory(obj, fs)) {
        return false;
      }
    }
    if (obj.get("type").equals("current")) {
      if (!loadCurrentPath(obj, fs)) {
        return false;
      }
    }
    if (obj.get("type").equals("stack")) {
      if (!loadPathStack(obj, fs)) {
        return false;
      }
    }
    if (obj.get("type").equals("history")) {
      if (!loadHistory(obj, history)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Load a file into the system.
   * 
   * @param obj the reading object
   * @param fs the current file system
   * @return true if successful
   */
  private static boolean addFile(JSONObject obj, FileSystem fs) {
    Path path = new Path();
    path.readPath((String) obj.get("path"));
    return fs.echoToFileWhole(path, (String) obj.get("name"), (String) obj.get("data"), false);
  }

  /**
   * Load a directory to the system.
   * 
   * @param obj the reading object
   * @param fs the current file system
   * @return true if successful
   */
  private static boolean addDirectory(JSONObject obj, FileSystem fs) {
    Path path = new Path();
    path.readPath((String) obj.get("path"));
    return fs.createDirWhole(path, (String) obj.get("name"));
  }

  /**
   * Load a path stack to the system.
   * 
   * @param obj the reading object
   * @param fs the current file system
   * @return true if successful
   */
  private static boolean loadPathStack(JSONObject obj, FileSystem fs) {
    JSONArray pathList = (JSONArray) obj.get("data");
    for (Object entry : pathList) {
      fs.pushDirectory((String) entry);
    }
    return true;
  }

  /**
   * Load the cmd histories to the history tracker.
   * 
   * @param obj the reading object
   * @param history the current history tracker
   * @return true if successful
   */
  private static boolean loadHistory(JSONObject obj, HistoryTracker history) {
    JSONArray cmdList = (JSONArray) obj.get("data");
    history.clear();
    for (Object entry : cmdList) {
      history.pushWithoutHeader((String) entry);
    }
    return true;
  }

  /**
   * Load the current path into the file system.
   * 
   * @param obj the reading object
   * @param fs the current file system
   * @return true if successful
   */
  private static boolean loadCurrentPath(JSONObject obj, FileSystem fs) {
    return fs.overrideWorkingDir((String) obj.get("data"));
  }
}
