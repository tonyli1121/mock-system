package fileSystems;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import Commands.HistoryTracker;
/**
 * SavedFileSystem is responsible of taking a file system and save its status into a local file.
 * 
 * @author Xuezhi Yan
 */
public class SavedFileSystem {
  /**
   * The saved directories¡£
   */
  private ArrayList<SavedDirectory> directories;
  /**
   * The saved files.
   */
  private ArrayList<SavedFile> files;
  /**
   * The saved history.
   */
  private ArrayList<String> histories;
  /**
   * The saved path stack.
   */
  private ArrayList<Path> pathStack;
  /**
   * The saved working directory.
   */
  private String currentDir;

  /**
   * The constructor that initializes every thing.
   */
  public SavedFileSystem() {
    this.directories = new ArrayList<SavedDirectory>();
    this.files = new ArrayList<SavedFile>();
    this.histories = new ArrayList<String>();
    this.pathStack = new ArrayList<Path>();
  }

  /**
   * Load everything from a fileSystem and a history.
   * 
   * @param fs the target file system to save
   * @param history the history tracker to save
   * @return true if successfully executed
   */
  public boolean loadFromSystem(FileSystem fs, HistoryTracker history) {
    fs.getRootDirectory().saveInto(new Path(), this);
    fs.getStack().saveInto(this);
    this.currentDir = fs.getCurrentPath();
    history.saveInto(this);
    return true;
  }

  /**
   * Add a command to histories.
   * @param entry the command to be added
   */
  public void addCmd(String entry) {
    this.histories.add(entry);
  }

  /**
   * Add a path to pathStack.
   * @param path the path to be added
   */
  public void addPath(Path path) {
    this.pathStack.add(path);
  }

  /**
   * Add a file with its path and data into files.
   * @param path the path of the file to be added
   * @param file the file to be added
   */
  public void addFile(Path path, File file) {
    this.files.add(new SavedFile(path.toString(), file.getName(), file.getData()));
  }

  /**
   * Add a directory and its path to directories.
   * @param path the path of the directory to be added
   * @param directory the directory to be added
   */
  public void addDirectory(Path path, Directory directory) {
    this.directories.add(new SavedDirectory(path.toString(), directory.getName()));
  }

  /**
   * Return saved histories in JSON format.
   * 
   * @return saved histories
   */
  @SuppressWarnings("unchecked")
  private JSONObject getHistoryObject() {
    JSONArray savedHistory = new JSONArray();
    for(String entry:this.histories) {
      savedHistory.add(entry);
    }
    JSONObject historyInfo = new JSONObject();

    historyInfo.put("type", "history");
    historyInfo.put("data", savedHistory);
    return historyInfo;
  }

  /**
   * Return saved path stack in JSON format.
   * @return saved path stack
   */
  @SuppressWarnings("unchecked")
  private JSONObject getStackObject() {
    JSONArray savedStack = new JSONArray();
    for(Path entry:this.pathStack) {
      savedStack.add(entry.toString());
    }
    JSONObject stackInfo = new JSONObject();

    stackInfo.put("type", "stack");
    stackInfo.put("data", savedStack);
    return stackInfo;
  }
  
  /**
   * Return the current directory in JSON format.
   * @return current directory
   */
  @SuppressWarnings("unchecked")
  private JSONObject getCurrentObject() {
    JSONObject currentInfo = new JSONObject();

    currentInfo.put("type", "current");
    currentInfo.put("data", this.currentDir);
    return currentInfo;
  }

  /**
   * Return saved FileSystem in JSON format.
   * @return saved FileSystem
   */
  @SuppressWarnings("unchecked")
  private JSONArray getJSOnArray() {
    JSONArray savedSystem = new JSONArray();
    for (SavedDirectory dir : this.directories) {
      dir.saveToJSON(savedSystem);
    }
    for (SavedFile file : this.files) {
      file.saveToJSON(savedSystem);
    }
    savedSystem.add(this.getStackObject());
    savedSystem.add(this.getHistoryObject());
    savedSystem.add(this.getCurrentObject());
    return savedSystem;
  }

  /**
   * Save inputPath to local in JSON format.
   * 
   * @param inputPath the path to be saved
   * @return true if successfully saved, otherwise false
   */
  public boolean saveToLocal(String inputPath) {
    JSONArray savedData = this.getJSOnArray();
    try (FileWriter file = new FileWriter(inputPath)) {
      file.write(savedData.toJSONString());
      file.flush();
      return true;

    } catch (IOException e) {
      return false;
    }
  }
}
