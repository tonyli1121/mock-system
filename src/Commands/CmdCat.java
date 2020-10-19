package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.ArgumentTypeError;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.FileNotFound;
import errorLoggerSystem.MissingArgument;
import fileSystems.Path;
import fileSystems.FileSystem;

/**
 * This class is responsible for the execution of cat.
 * 
 * @author Xingnian Jin
 * @author Xuezhi Yan
 */
public class CmdCat implements ExecutableCmd {

  /**
   * The successfully executed params.
   */
  private ArrayList<String> success;
  
  private String printedValue = "";

  /**
   * The private method used to handle global path cases.
   * 
   * @param path the global path
   * @param fs the working file system
   */
  private void doGlobalPathCase(String path, FileSystem fs) {
    Path temp = new Path();
    temp.readPath(path);
    String name = temp.previousPath();
    if (fs.isValidWholePath(temp)) {
      if (fs.findWholeDirectory(temp).hasFile(name)) {
        System.out.println(fs.getFileInfoWhole(temp, name));
        System.out.println("\n\n");
        printedValue += fs.getFileInfoWhole(temp, name) + "\n\n";
        success.add(path);
      } else {
        System.out.print("[" + path + "]");
        FileNotFound.printErrorMessage();
        printedValue = "file not found error msg";
      }
    } else {
      System.out.print("[" + path + "]");
      DirectoryNotFound.printErrorMessage();
      printedValue = "directory not found error msg";
    }
  }

  /**
   * The private method used to handle relative path cases.
   * 
   * @param path the relative path
   * @param fs the working file system
   */
  private void doRelativePathCase(String path, FileSystem fs) {
    Path temp = new Path();
    temp.readPath(path);
    String name = temp.previousPath();
    if (fs.isValidRelativePath(temp)) {
      if (fs.findRelativeDirectory(temp).hasFile(name)) {
        System.out.println(fs.getFileInfoRelative(temp, name));
        System.out.println("\n\n");
        printedValue += fs.getFileInfoRelative(temp, name) + "\n\n";
        success.add(path);
      } else {
        System.out.print("[" + path + "]");
        FileNotFound.printErrorMessage();
        printedValue = "file not found error msg";
      }
    } else {
      System.out.print("[" + path + "]");
      DirectoryNotFound.printErrorMessage();
      printedValue = "directory not found error msg";
    }
  }

  /**
   * Returns true and print out the given file content if the params are valid. Return false and
   * gives error message if the following cases: - Empty parameter, since cat needs at least 1
   * FILENAME to run - Given FILENAME has special character - Given FILENAME does not exist in the
   * current directory
   * 
   * @param param The whole parameter String after the command
   * @param fs The FileSystem that the user is currently in
   * @return a boolean value true or false indicating if the operation is executed successfully
   */
  @Override
  public boolean execute(String param, FileSystem fs) {
    success = new ArrayList<String>();
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));

    if (arguments.size() == 0) {
      MissingArgument.printErrorMessage();
      printedValue = "missing argument error msg";
      return false;
    }
    for (String path : arguments) {
      if (path.charAt(path.length() - 1) == '/') {
        System.out.print("[" + path + "]");
        ArgumentTypeError.printErrorMessage();
        printedValue = "argument type error msg";
      } else if (path.charAt(0) == '/') {
        this.doGlobalPathCase(path, fs);
      } else {
        this.doRelativePathCase(path, fs);
      }
    }
    if (success.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Getter method for printedValue
   * 
   * @return printedValue
   */
  public String getPrintedValue() {
    return this.printedValue;
  }

  /**
   * Prints out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  @Override
  public void displayDone(String param) {
    System.out.println("cat");
    System.out.println(String.join(" ", success));
  }

}
