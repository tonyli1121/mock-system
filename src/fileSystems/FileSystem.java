package fileSystems;

import java.util.ArrayList;
import errorLoggerSystem.DirectoryAlreadyExist;
import errorLoggerSystem.SpecialChar;

/**
 * The FileSystem instance is responsible for managing the relationship between the directories and
 * files. It also provides the ability to navigate between directories.
 * 
 * @author Xuezhi Yan
 */
public class FileSystem {

  /**
   * Path that currently open.
   */
  private Path currentPath;
  /**
   * Directory that currently open.
   */
  private Directory currentDirectory;
  /**
   * The first directory of currentPath.
   */
  private Directory rootDirectory;
  /**
   * A path of stack that stores paths.
   */
  private PathStack stack;

  /**
   * The FileSystem constructor takes no parameters, it initializes itself with a new starting path,
   * a new root directory and initializes the currentDirectory to its root directory.
   */
  public FileSystem() {
    this.currentPath = new Path();
    this.rootDirectory = new Directory(Lib.rootDir);
    this.currentDirectory = this.rootDirectory;
    this.stack = new PathStack();
  }

  /**
   * Return the current path and indicates that it's a file system
   */
  @Override
  public String toString() {
    return "FileSystem at " + this.getCurrentPath();
  }

  /**
   * Return the current navigating path
   * 
   * @return current path in string representation
   */
  public String getCurrentPath() {
    return Lib.seperator + currentPath.toString();
  }

  /**
   * Return the files and folder contained in the current directory.
   * 
   * @return a list of files and folders in its string representation
   */
  public ArrayList<String> getCurrentList() {
    return this.currentDirectory.getSubAll();
  }

  /**
   * Return the files and folder contained in the path.
   * 
   * @param path the path to check
   * @return a list of files and folders in its string representation
   */
  public ArrayList<String> getRelativeList(Path path) {
    Path temp = this.currentPath.clone();
    this.navigateRelative(path);
    ArrayList<String> result = this.getCurrentList();
    this.overrideWorkingDir(temp.toString());
    return result;
  }

  /**
   * Return the files and folder contained in the path.
   * 
   * @param path the path to check
   * @return a list of files and folders in its string representation
   */
  public ArrayList<String> getGlobalList(Path path) {
    Path temp = this.currentPath.clone();
    this.overrideWorkingDir(path.toString());
    ArrayList<String> result = this.getCurrentList();
    this.overrideWorkingDir(temp.toString());
    return result;
  }

  /**
   * Check if the relative path is a valid path.
   * 
   * @param path the path to check
   * @return true if it's valid, otherwise false
   */
  public boolean isValidRelativePath(Path path) {
    Path temp = this.currentPath.clone();
    if (!this.navigateRelative(path)) {
      this.overrideWorkingDir(temp.toString());
      return false;
    } else {
      this.overrideWorkingDir(temp.toString());
      return true;
    }
  }

  /**
   * Check if the global path is a valid path.
   * 
   * @param path the path to check
   * @return true if it's valid, otherwise false
   */
  public boolean isValidWholePath(Path path) {
    Path temp = this.currentPath.clone();
    if (!this.overrideWorkingDir(path.toString())) {
      this.overrideWorkingDir(temp.toString());
      return false;
    } else {
      this.overrideWorkingDir(temp.toString());
      return true;
    }
  }

  /**
   * File the directory at the relative path.
   * 
   * @param path the path to look at
   * @return the directory if found, null if not found
   */
  public Directory findRelativeDirectory(Path path) {
    Path temp = this.currentPath.clone();
    if (!this.navigateRelative(path)) {
      this.overrideWorkingDir(temp.toString());
      return null;
    } else {
      Directory result = this.currentDirectory;
      this.overrideWorkingDir(temp.toString());
      return result;
    }
  }

  /**
   * File the directory at the global path.
   * 
   * @param path the path to look at
   * @return the directory if found, null if not found
   */
  public Directory findWholeDirectory(Path path) {
    Path temp = this.currentPath.clone();
    if (!this.overrideWorkingDir(path.toString())) {
      this.overrideWorkingDir(temp.toString());
      return null;
    } else {
      Directory result = this.currentDirectory;
      this.overrideWorkingDir(temp.toString());
      return result;
    }
  }

  /**
   * Navigate to a folder in the current directory.
   * 
   * @param dest the target folder name
   * @return true if the directory is found and navigated, otherwise false
   */
  public boolean navigateWithin(String dest) {
    if (dest.equals("..")) {
      return this.previousDirectory();
    }
    if (dest.equals(".")) {
      return true;
    }
    Directory found = this.currentDirectory.search(dest);
    if (found != null) {
      this.currentDirectory = found;
      this.currentPath.nextPath(dest);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Navigate to a relative path not in the current directory.
   * 
   * @param path the relative path to navigate to
   * @return true if successful navigate, false if not
   */
  public boolean navigateRelative(Path path) {
    ArrayList<String> cpath = path.getPath();
    boolean flag = true;
    for (String each : cpath) {
      flag = this.navigateWithin(each);
      if (!flag) {
        return false;
      }
    }
    return true;
  }

  /**
   * Replace the current working path with the newDir.
   * 
   * @param newDir the new working path
   * @return true if successfully navigated, otherwise false
   */
  public boolean overrideWorkingDir(String newDir) {
    Path temp = new Path();
    temp.readPath(newDir);
    ArrayList<String> tempPath = temp.getPath();
    this.currentPath.readPath("");
    this.currentDirectory = this.rootDirectory;
    boolean flag = true;
    for (String each : tempPath) {
      flag = this.navigateWithin(each);
      if (!flag) {
        return false;
      }
    }
    return true;
  }

  /**
   * Create a new directory within the working directory.
   * 
   * @param dest the name of the new directory
   * @return true if successfully created, otherwise false
   */
  public boolean createDirWithin(String dest) {
    if (!checkName(dest)) {
      System.out.print("[" + dest + "]");
      SpecialChar.printErrorMessage();
      return false;
    }
    if (this.currentDirectory.addDirectory(new Directory(dest))) {
      return true;
    }
    System.out.print("[" + dest + "]");
    DirectoryAlreadyExist.printErrorMessage();
    return false;
  }

  /**
   * Create a new directory in the relative path.
   * 
   * @param path the relative path
   * @param dest the name of new directory
   * @return true if successfully added, false if path is invalid or directory already exists.
   */
  public boolean createDirRelative(Path path, String dest) {
    if (!checkName(dest)) {
      System.out.print("[" + dest + "]");
      SpecialChar.printErrorMessage();
      return false;
    }
    Directory found = this.findRelativeDirectory(path);
    if (found.addDirectory(new Directory(dest))) {
      return true;
    }
    System.out.print("[" + dest + "]");
    DirectoryAlreadyExist.printErrorMessage();
    return false;
  }

  /**
   * Create a new directory in the global path.
   * 
   * @param path the global path
   * @param dest the name of new directory
   * @return true if successfully added, false if path is invalid or directory already exists.
   */
  public boolean createDirWhole(Path path, String dest) {
    if (!checkName(dest)) {
      System.out.print("[" + dest + "]");
      SpecialChar.printErrorMessage();
      return false;
    }
    Directory found = this.findWholeDirectory(path);
    if (found.addDirectory(new Directory(dest))) {
      return true;
    }
    System.out.print("[" + dest + "]");
    DirectoryAlreadyExist.printErrorMessage();
    return false;
  }

  /**
   * If append is true, append dataIn to the data stored in the file nameIn within the current
   * working directory, create a new file and add the data if such file doesn't exist. Otherwise,
   * rewrite the data instead.
   * 
   * @param nameIn the name of the file
   * @param dataIn the data to append/rewrite
   * @param append true if append, false if overwrite
   * @return true if successfully executed.
   */
  public boolean echoToFile(String nameIn, String dataIn, boolean append) {
    if (!checkName(nameIn)) {
      SpecialChar.printErrorMessage();
      return false;
    }
    if (this.currentDirectory.contains(nameIn)) {
      DirectoryAlreadyExist.printErrorMessage();
      return false;
    }
    File found = this.currentDirectory.getFile(nameIn);
    if (found != null) {
      if (append) {
        found.appendData(dataIn);
      } else {
        found.overrideData(dataIn);
      }
    } else {
      this.currentDirectory.addFile(new File(nameIn, dataIn));
    }
    return true;
  }

  /**
   * Return the data stored in the file under the current working directory.
   * 
   * @param nameIn the name of the target file
   * @return the data if found, null if the file is not found
   */
  public String getFileInfo(String nameIn) {
    File found = this.currentDirectory.getFile(nameIn);
    if (found != null) {
      return found.getData();
    }
    return null;
  }

  /**
   * If append is true, append dataIn to the data stored in the file nameIn in the global path,
   * create a new file and add the data if such file doesn't exist. otherwise, rewrite the data
   * instead.
   * 
   * @param path the global path
   * @param nameIn the name of the file
   * @param dataIn the data to append/rewrite
   * @param append true if append, false if overwrite
   * @return true if successfully executed.
   */
  public boolean echoToFileWhole(Path path, String nameIn, String dataIn, boolean append) {
    if (!checkName(nameIn)) {
      SpecialChar.printErrorMessage();
      return false;
    }
    Directory dir = this.findWholeDirectory(path);
    if (dir.contains(nameIn)) {
      DirectoryAlreadyExist.printErrorMessage();
      return false;
    }
    File found = dir.getFile(nameIn);
    if (found != null) {
      if (append) {
        found.appendData(dataIn);
      } else {
        found.overrideData(dataIn);
      }
    } else {
      dir.addFile(new File(nameIn, dataIn));
    }
    return true;
  }

  /**
   * Return the data stored in the file under the global path.
   * 
   * @param path the target global path
   * @param nameIn the name of the target file
   * @return the data if found, null if the file is not found
   */
  public String getFileInfoWhole(Path path, String nameIn) {
    Directory dir = this.findWholeDirectory(path);
    File found = dir.getFile(nameIn);
    if (found != null) {
      return found.getData();
    }
    return null;
  }

  /**
   * If append is true, append dataIn to the data stored in the file nameIn in the relative path,
   * create a new file and add the data if such file doesn't exist. otherwise, rewrite the data
   * instead.
   * 
   * @param path the relative path
   * @param nameIn the name of the file
   * @param dataIn the data to append/rewrite
   * @param append true if append, false if overwrite
   * @return true if successfully executed.
   */
  public boolean echoToFileRelative(Path path, String nameIn, String dataIn, boolean append) {
    if (!checkName(nameIn)) {
      SpecialChar.printErrorMessage();
      return false;
    }
    Directory dir = this.findRelativeDirectory(path);
    if (dir.contains(nameIn)) {
      DirectoryAlreadyExist.printErrorMessage();
      return false;
    }
    File found = dir.getFile(nameIn);
    if (found != null) {
      if (append) {
        found.appendData(dataIn);
      } else {
        found.overrideData(dataIn);
      }
    } else {
      dir.addFile(new File(nameIn, dataIn));
    }
    return true;
  }

  /**
   * Return the data stored in the file under the relative path.
   * 
   * @param path the target relative path
   * @param nameIn the name of the target file
   * @return the data if found, null if the file is not found
   */
  public String getFileInfoRelative(Path path, String nameIn) {
    Directory dir = this.findRelativeDirectory(path);
    File found = dir.getFile(nameIn);
    if (found != null) {
      return found.getData();
    }
    return null;
  }

  /**
   * Push the current path into the stack, then navigate into the new path.
   * 
   * @param nameIn name of the new path
   * @return true if executed
   */
  public boolean pushDirectory(String nameIn) {
    this.stack.push(this.currentPath.clone());
    this.overrideWorkingDir(nameIn);
    return true;
  }

  /**
   * Pop the last path from the stack, navigate into it.
   * 
   * @return true if executed, false if the stack is empty.
   */
  public boolean popDirectory() {
    Path current = this.stack.pop();
    if (current == null) {
      return false;
    }
    this.overrideWorkingDir(current.toString());
    return true;
  }

  /**
   * Navigate to the parent directory.
   * 
   * @return true if executed, false if already at root directory.
   */
  public boolean previousDirectory() {
    if (this.currentDirectory == this.rootDirectory) {
      return false;
    }
    this.currentPath.previousPath();
    this.overrideWorkingDir(this.currentPath.toString());
    return true;
  }

  /**
   * Add file at the current directory
   * 
   * @param file The file to be added to the current directory
   */
  public boolean addFileAtCurrentDirectory(File fileIn) {
    if (this.currentDirectory.hasFile(fileIn.getName()))
      return false;
    this.currentDirectory.addFile(fileIn);
    return true;
  }

  public Directory getRootDirectory() {
    return this.rootDirectory;
  }

  /**
   * Return the current directory for the file system
   * 
   * @return current directory
   */
  public Directory getCurrentDirectory() {
    return this.currentDirectory;
  }

  /**
   * Get the current path stack
   * 
   * @return the current path stack
   */
  public PathStack getStack() {
    return this.stack;
  }

  /**
   * The static method that make sure no . is included in file or directory name
   * 
   * @param strIn the string to check
   * @return true if no . appeared, otherwise false
   */
  private static boolean checkName(String strIn) {
    if (strIn == null) {
      return false;
    }
    return !strIn.contains(".");
  }
}
