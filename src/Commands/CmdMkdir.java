package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.MissingArgument;
import errorLoggerSystem.SpecialChar;
import fileSystems.FileSystem;
import fileSystems.Path;

/**
 * This class is responsible for the execution of mkdir.
 * 
 * @author Xuezhi Yan
 *
 */
public class CmdMkdir implements ExecutableCmd {

  /**
   * The successfully executed params.
   */
  private ArrayList<String> success;

  /**
   * The private method used to handle global path cases.
   * 
   * @param newDir the global path
   * @param fs the working file system
   */
  private void doGlobalPathCase(String newDir, FileSystem fs) {
    Path temp = new Path();
    temp.readPath(newDir);
    String name = temp.previousPath();
    if (fs.isValidWholePath(temp)) {
      if (fs.createDirWhole(temp, name)) {
        success.add(newDir);
      }
    } else {
      System.out.print("[" + newDir + "]");
      DirectoryNotFound.printErrorMessage();
    }
  }

  /**
   * The private method used to handle relative path cases.
   * 
   * @param newDir the relative path
   * @param fs the working file system
   */
  private void doRelativePathCase(String newDir, FileSystem fs) {
    Path temp = new Path();
    temp.readPath(newDir);
    String name = temp.previousPath();
    if (fs.isValidRelativePath(temp)) {
      if (fs.createDirRelative(temp, name)) {
        success.add(newDir);
      }
    } else {
      System.out.print("[" + newDir + "]");
      DirectoryNotFound.printErrorMessage();
    }
  }

  /**
   * The execute method for CmdMkdir.
   * 
   * @param param the parameters for the command
   * @param fs the current fileSystem
   * @return true if successfully executed, false otherwise
   */
  @Override
  public boolean execute(String param, FileSystem fs) {
    success = new ArrayList<String>();
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    if (arguments.size() == 0) {
      MissingArgument.printErrorMessage();
      return false;
    }
    for (String newDir : arguments) {
      if (!ValidityCheck.validPath(newDir)) {
        System.out.print("[" + newDir + "]");
        SpecialChar.printErrorMessage();
      } else if (newDir.contains("/")) {
        if (newDir.charAt(0) == '/') {
          this.doGlobalPathCase(newDir, fs);
        } else {
          this.doRelativePathCase(newDir, fs);
        }
      } else {
        if (fs.createDirWithin(newDir)) {
          success.add(newDir);
        }
      }
    }
    return !this.success.isEmpty();
  }

  /**
   * Prints out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  @Override
  public void displayDone(String param) {
    System.out.println("mkdir");
    System.out.println(String.join(" ", this.success));
  }
}
