package Commands;

import java.util.ArrayList;
import fileSystems.FileSystem;
import errorLoggerSystem.*;

/**
 * This class is able to perform command cd.
 * 
 * @author Tianxiao Li
 *
 */
public class CmdCd extends Command implements ExecutableCmd {

  /**
   * The successfully executed params.
   */
  private ArrayList<String> success;

  /**
   * This method takes a input strIn and returns an ArrayList containing each dir.
   * 
   * @param strIn input waits to be splited
   * @return an Arraylist with each element representing a directory
   */
  private ArrayList<String> toNoDash(String strIn) {
    ArrayList<String> result = new ArrayList<String>();
    for (String dir : strIn.split("/")) {
      result.add(dir);
    }
    return result;
  }

  /**
   * This method reads directory and navigate in the current folder.
   * 
   * @param dir directory to be navigated
   * @param fs file system
   * @return true if reached, false otherwise
   */
  private boolean readAndNavigateRelative(String dir, FileSystem fs) {
    if (dir.contentEquals(".")) {
      // stay in current directory
      return true;
    } else if (dir.contentEquals("..")) {
      // navigate to previous directory
      if (fs.previousDirectory()) {
        return true;
      } else {
        CurrentRootDirectory.printErrorMessage();
      }
    } else {
      // navigate to given directory
      strToPath(dir);
      if (fs.navigateRelative(myPath)) {
        return true;
      } else {
        System.out.print("[" + dir + "]");
        DirectoryNotFound.printErrorMessage();
      }
    }
    return false;
  }

  @Override
  public boolean execute(String param, FileSystem fs) {
    ArrayList<String> directories = new ArrayList<String>();
    success = new ArrayList<String>();
    
    if (param.startsWith("/")) { // absolute path
      if (fs.overrideWorkingDir(param)) {
        success.add("successfully navigated to an absolute path");
      }
    } 
    
    //relative path
    else {
      directories = toNoDash(param);
      // create backUp for situation when directory not exist but already partially run
      String backUp = fs.getCurrentPath();
      
      for (String dir : directories) {
        if (readAndNavigateRelative(dir, fs) == false) {
          // failure to navigate -> false, call backup
          success.clear();
          fs.overrideWorkingDir(backUp);
          break;
        }
        success.add(dir);
      }
    }
    return (!success.isEmpty());
  }

  /**
   * Prints out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  @Override
  public void displayDone(String param) {
    System.out.println("cd");
    System.out.println(param);

  }

}
