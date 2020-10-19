package fileSystems;

import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.FileNotFound;

/**
 * SystemNavigator is reponsible for tree navigation and directory relations.
 * 
 * @author Xuezhi Yan
 */
public class SystemNavigator {
  /**
   * Return the tree format of directories in given FileSystem.
   * 
   * @param fs the current FileSystem
   * @return a tree of directories
   */
  public static String getTreeOf(FileSystem fs) {
    Directory root = fs.getRootDirectory();
    String result = root.branch(0, "/", " ");
    return result;
  }

  /**
   * Return the tree format of directories in given FileSystem.
   * 
   * @param fs the FileSystem to be given a tree format
   * @param dir the directory to be separated by separator
   * @param sep the separator used for spacing
   * @return a tree of directories
   */
  public static String getTreeOf(FileSystem fs, Directory dir, String sep) {
    String result = dir.branch(0, "", sep);
    return result;
  }

  /**
   * Move the item in the path from to the path towards.
   * 
   * @param fs the current FileSystem
   * @param from the path contains the items to be moved
   * @param towards the path that the items moved to
   * @param isFile true if the items to be moved is file, false otherwise
   * @return true if able to move, otherwise false
   */
  public static boolean moveItemTo(FileSystem fs, Path from, Path towards, boolean isFile) {
    String target = from.previousPath();
    Directory fromDir = fs.findWholeDirectory(from);
    Directory toDir = fs.findWholeDirectory(towards);
    if (fromDir == null || toDir == null) {
      DirectoryNotFound.printErrorMessage();
      return false;
    }
    if (isFile) {
      if (fromDir.getFile(target) == null) {
        FileNotFound.printErrorMessage();
        return false;
      }
      toDir.addFile(fromDir.removeFile(target));
    } else {
      if (fromDir.search(target) == null) {
        DirectoryNotFound.printErrorMessage();
        return false;
      }
      toDir.addDirectory(fromDir.removeDirectory(target));
    }
    return true;
  }

  /**
   * Check if path parent contains path child.
   * 
   * @param parent the path to be checked if it contains another path
   * @param child the path to be checked if it is contained in another path
   * @param fs the current FileSystem
   * @return true if parent contains child, false otherwise
   */
  public static boolean isParentOf(Path parent, Path child, FileSystem fs) {
    child = child.clone();
    child.previousPath();
    while (!child.isEmpty()) {
      if (fs.findWholeDirectory(parent) == fs.findWholeDirectory(child)) {
        return true;
      }
      child.previousPath();
    }
    if (fs.findWholeDirectory(parent) == fs.findWholeDirectory(child)) {
      return true;
    }
    return false;
  }
}
