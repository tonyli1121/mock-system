package fileSystems;

import java.util.ArrayList;


/**
 * The Directory instance has the ability to store other directories and files, manage the stored
 * objects, and provide the way to search, contain and get the stored members.
 * 
 * @author Xuezhi Yan
 */
public class Directory {

  /**
   * Name of directory.
   */
  private String name;
  /**
   * List of directory names.
   */
  private ArrayList<Directory> subDirectories;
  /**
   * List of file names.
   */
  private ArrayList<File> subFiles;

  /**
   * The Directory class constructor, it initialize the instance with the input name, an empty list
   * of files and an empty list of directories.
   * 
   * @param nameIn the name of the directory
   */
  public Directory(String nameIn) {
    this.name = nameIn;
    this.subDirectories = new ArrayList<Directory>();
    this.subFiles = new ArrayList<File>();
  }

  /**
   * Return the text representation of the object
   */
  @Override
  public String toString() {
    return this.name;
  }

  /**
   * Check if the given directory name exists in the existing directories.
   * 
   * @param nameIn the name to check
   * @return true if it already exists, otherwise false
   */
  public boolean contains(String nameIn) {
    for (Directory dir : this.subDirectories) {
      if (dir.getName().equals(nameIn)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if the given file name exists in the existing files.
   * 
   * @param nameIn the name to check
   * @return true if it already exists, otherwise false
   */
  public boolean hasFile(String nameIn) {
    for (File file : this.subFiles) {
      if (file.getName().equals(nameIn)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Return the directory name of the instance, which has a slash at the end of it.
   * 
   * @return the in-path name of this directory
   */
  public String getName() {
    return this.name;
  }

  /**
   * Return the file with the name nameIn, returns null if the file is not found.
   * 
   * @param nameIn the name of the target file
   * @return the file with the nameIn, null if the file is not found
   */
  public File getFile(String nameIn) {
    for (File file : this.subFiles) {
      if (file.getName().equals(nameIn)) {
        return file;
      }
    }
    return null;
  }

  /**
   * Return a list of directory names for its sub-directories.
   * 
   * @return directory names for sub-directories
   */
  public ArrayList<String> getSubDirectories() {
    ArrayList<String> nameList = new ArrayList<String>();
    this.subDirectories.forEach((directory) -> nameList.add(directory.getName()));
    return nameList;
  }

  /**
   * Return a list of file names for its sub-files.
   * 
   * @return file names for sub-files
   */
  public ArrayList<String> getSubFiles() {
    ArrayList<String> nameList = new ArrayList<String>();
    this.subFiles.forEach((file) -> nameList.add(file.getName()));
    return nameList;
  }

  /**
   * Return a list of names for its sub-directories and sub-files.
   * 
   * @return names for all of its sub-elements
   */
  public ArrayList<String> getSubAll() {
    ArrayList<String> nameList = new ArrayList<String>();
    this.subDirectories.forEach((directory) -> nameList.add(directory.getName()));
    this.subFiles.forEach((file) -> nameList.add(file.getName()));
    return nameList;
  }

  /**
   * Add a new directory to its sub-directory.
   * 
   * @param dirIn the directory to add
   * @return false if the directory already exist, otherwise true
   */
  public boolean addDirectory(Directory dirIn) {
    if (this.contains(dirIn.getName()) || this.hasFile(dirIn.getName())) {
      return false;
    }
    this.subDirectories.add(dirIn);
    return true;
  }

  /**
   * Add a new file to its sub-files.
   * 
   * @param fileIn the file to add
   */
  public void addFile(File fileIn) {
    this.subFiles.add(fileIn);
  }

  /**
   * Search for a directory named target.
   * 
   * @param target the target name of the directory
   * @return the directory if found, otherwise null
   */
  public Directory search(String target) {
    for (Directory element : this.subDirectories) {
      if (element.getName().equals(target)) {
        return element;
      }
    }
    return null;
  }

  /**
   * Remove the directory name target if found.
   * 
   * @param target the target name of the directory to be removed
   * @return the directory if found, otherwise null
   */
  public Directory removeDirectory(String target) {
    Directory found = this.search(target);
    if (found == null) {
      return null;
    } else {
      this.subDirectories.remove(found);
      return found;
    }
  }

  /**
   * Remove the file name target if found.
   * 
   * @param file the target name of the file to be removed
   * @return the file if found, otherwise null
   */
  public File removeFile(String file) {
    File found = this.getFile(file);
    if (found == null) {
      return null;
    } else {
      this.subFiles.remove(found);
      return found;
    }
  }

  /**
   * A recursive method that displays the directories in the form of a tree.
   * 
   * @param depth the current recursion depth
   * @param prevResult the previous cumulative string
   * @param sep the separator used for spacing
   * @return the resulting string for the current branch
   */
  public String branch(int depth, String prevResult, String sep) {
    String newResult = prevResult;
    for (int i = 0; i < depth; i++) {
      newResult += sep;
    }
    newResult += this.getName();
    for (Directory dir : this.subDirectories) {
      newResult = newResult + "\n";
      newResult = dir.branch(depth + 1, newResult, sep);
    }
    for (File file : this.subFiles) {
      newResult += "\n";
      for (int i = 0; i < depth + 1; i++) {
        newResult += sep;
      }
      newResult += file.getName();
    }
    return newResult;
  }

  /**
   * A recursive method that saves a tree of files and directories into the saveTarget.
   * 
   * @param parentPath the previous path of this recursion
   * @param saveTarget the target SavedFileSystem
   */
  public void saveInto(Path parentPath, SavedFileSystem saveTarget) {
    for (Directory dir : this.subDirectories) {
      saveTarget.addDirectory(parentPath, dir);
      dir.saveInto(parentPath.combine(new Path(dir.getName())), saveTarget);
    }
    for (File file : this.subFiles) {
      saveTarget.addFile(parentPath, file);
    }
  }

  /**
   * A recursive method that copies the elements in this directory to the target.
   * 
   * @param parentPath the previous path of this recursion
   * @param target the copy target
   * @param fs the working fileSystem
   */
  public void copyInto(Path parentPath, Path target, FileSystem fs) {
    for (Directory dir : this.subDirectories) {
      fs.createDirWhole(target.combine(parentPath), dir.getName());
      dir.copyInto(parentPath.combine(new Path(dir.getName())), target, fs);
    }
    for (File file : this.subFiles) {
      file.copyTo(target.combine(parentPath), fs);
    }
  }
}
