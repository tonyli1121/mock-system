package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.ExtraArgument;
import errorLoggerSystem.MissingArgument;
import fileSystems.FileSystem;
import fileSystems.Path;

/**
 * This class executes command pushd.
 * 
 * @author Xingnian Jin
 * @author Xuezhi Yan
 *
 */
public class CmdPushd implements ExecutableCmd {

  /**
   * The private method used to handle global path cases.
   * 
   * @param path the global path
   * @param fs the working file system
   */
  
  private String errMsg = "";
  
  private boolean doGlobalPathCase(ArrayList<String> arguments, FileSystem fs) {
    Path temp = new Path();
    temp.readPath(arguments.get(0));
    if (fs.isValidWholePath(temp)) {
      fs.pushDirectory(arguments.get(0));
      return true;
    } else {
      DirectoryNotFound.printErrorMessage();
      errMsg = "directory not found error msg";
      return false;
    }
  }

  /**
   * The private method used to handle relative path cases.
   * 
   * @param path the relative path
   * @param fs the working file system
   */
  private boolean doRelativePathCase(ArrayList<String> arguments, FileSystem fs) {
    Path temp = new Path();
    temp.readPath(arguments.get(0));
    if (fs.isValidRelativePath(temp)) {
      fs.pushDirectory(fs.getCurrentPath() + arguments.get(0));
      return true;
    } else {
      DirectoryNotFound.printErrorMessage();
      errMsg = "directory not found error msg";
      return false;
    }
  }

  @Override
  /**
   * Returns true and push the current working directory into the stack and navigate to the given
   * directory/path Returns false and prints out error message if the following cases: - Empty
   * parameter, since command pushd requires one DIR - Multiple parameter, since command pushd only
   * accepts one DIR - The given DIR/PATH is not a valid relative DIR/PATH (under current DIR) - The
   * given PATH is not a valid global PATH (under root DIR)
   * 
   * @param param a DIR/PATH string provided by user input
   * @param fs the current FileSystem
   * @return a boolean value true or false indicating if the operation is executed successfully
   */
  public boolean execute(String param, FileSystem fs) {
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    if (arguments.size() == 0) {
      MissingArgument.printErrorMessage();
      errMsg = "missing argument error msg";
      return false;
    }
    if (arguments.size() > 1) {
      ExtraArgument.printErrorMessage();
      errMsg = "extra argument error msg";
      return false;
    }
    if (arguments.get(0).charAt(0) == '/') {
      return this.doGlobalPathCase(arguments, fs);
    } else {
      return this.doRelativePathCase(arguments, fs);
    }
  }

  /**
   * Getter for errMsg
   * 
   * @return errMsg
   */
  public String getErrMsg() {
    return this.errMsg;
  }
  
  @Override
  /**
   * Prints out the command followed by parameters for the command
   * 
   * @param param the whole parameter String after the command
   */
  public void displayDone(String param) {
    System.out.println("pushd");
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    System.out.println(arguments.get(0));
  }
}
