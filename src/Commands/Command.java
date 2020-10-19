package Commands;

import java.util.ArrayList;
import fileSystems.FileSystem;
import fileSystems.Path;

/**
 * This is class that contains some methods that are helpful for error check and generating path,
 * array objects.
 * 
 * @author Tianxiao Li
 */
public class Command {

  /**
   * The path that currently working on.
   */
  protected static Path myPath;

  /**
   * This method converts a raw input into a path object.
   * 
   * @param strIn This is a raw input waits to be converted
   */
  protected static void strToPath(String strIn) {
    myPath = new Path();
    myPath.readPath(strIn);
  }

  /**
   * This method returns a boolean value representing if the input path is a full path.
   * 
   * @param strIn a string version of path waiting to be checked
   * @return True if strIn is an absolute path, False otherwise
   */
  protected static boolean isAbsPath(String strIn) {
    return (strIn.charAt(0) == '/');
  }

  /**
   * This method returns a list representation of input.
   * 
   * @param strIn input waits to be converted
   * @return ArrayList<String>, each elements represent a directory
   */
  protected static ArrayList<String> ToList(String strIn) {
    ArrayList<String> directories = new ArrayList<String>();
    for (String dir : strIn.split(" ")) {
      if (!dir.contentEquals(""))
        directories.add(dir.trim());
    }
    return directories;
  }

  /**
   * This method returns an ArrayList<string> for the input listIn such that every elements in the
   * ArrayList doesn't contain any special char, prints an specialChar error when meets any invalid
   * directory.
   * 
   * @param listIn a list of directories, unsure if any of the elements contains special char
   * @return an ArrayList that no element contains special char
   */
  protected static ArrayList<String> ToListContainValidOnly(ArrayList<String> listIn) {
    ArrayList<String> result = new ArrayList<String>();
    for (String dir : listIn) {
      if (ValidityCheck.noSpecialChar(dir))
        result.add(dir);
      else
        System.out.println(dir + "contains invalid characters");
    }
    return result;
  }

  /**
   * This method returns an ArrayList contains only directories that does not exist in FileSystem,
   * prints DirectoryAlreadyExist error when meets a directory that already exist in FileSystem.
   * 
   * @param listIn list of directories to be checked, must call ToListContainValidOnly first
   * @param fs target file system
   * @return ArrayList<string> contains only directories that does not exist
   */
  protected static ArrayList<String> ToListContainsDNE(ArrayList<String> listIn, FileSystem fs) {
    Path temp = new Path();
    ArrayList<String> result = new ArrayList<String>();
    for (String dir : listIn) {
      temp = new Path();
      temp.readPath(dir);
      if (isAbsPath(dir)) {
        if (fs.isValidWholePath(temp)) {
          errorLoggerSystem.DirectoryAlreadyExist.printErrorMessage();
        } else {
          result.add(dir);
        }
      } else {
        if (fs.isValidRelativePath(temp)) {
          errorLoggerSystem.DirectoryAlreadyExist.printErrorMessage();
        } else {
          result.add(dir);
        }
      }
    }
    return result;
  }

  /**
   * This method returns an ArrayList contains only directories that exist in FileSystem, prints
   * DirectoryNotFound error if meets a directory that doesn't exist in FileSystem.
   * 
   * @param listIn list of directories to be checked, must call ToListContainValidOnly first
   * @param fs target file system
   * @return ArrayList<string> contains only directories that exist
   */
  protected static ArrayList<String> ToListContainsExist(ArrayList<String> listIn, FileSystem fs) {
    Path temp = new Path();
    ArrayList<String> result = new ArrayList<String>();
    for (String dir : listIn) {
      temp = new Path();
      temp.readPath(dir);
      if (isAbsPath(dir)) {
        if (!fs.isValidWholePath(temp)) {
          errorLoggerSystem.DirectoryNotFound.printErrorMessage();
        } else {
          result.add(dir);
        }
      } else {
        if (!fs.isValidRelativePath(temp)) {
          errorLoggerSystem.DirectoryNotFound.printErrorMessage();
        } else {
          result.add(dir);
        }
      }
    }
    return result;
  }
}
