package Commands;

import fileSystems.FileSystem;
import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.ExtraArgument;
import errorLoggerSystem.MissingArgument;
import errorLoggerSystem.ParentDirectoryError;
import fileSystems.Directory;
import fileSystems.File;
import fileSystems.Path;
import fileSystems.SystemNavigator;

/**
 * This class is responsible for the execution of mv.
 * 
 * @author Xuezhi Yan
 * @author Pengpeng Cao
 */
public class CmdMv implements ExecutableCmd {

  /**
   * Move the first element of arguments into the second element of arguments.
   * 
   * @param arguments
   * @param fs the current FileSystem
   * @return true if the item is successfully moved, otherwise false.
   */
  private boolean doMove(ArrayList<String> arguments, FileSystem fs) {
    Path from = Path.makeFullPath(fs, arguments.get(0));
    Path to = Path.makeFullPath(fs, arguments.get(1));
    if (from.toString().equals(to.toString()))
      return true;
    if (SystemNavigator.isParentOf(from, to, fs)) {
      ParentDirectoryError.printErrorMessage();
      return false;
    }
    String name = from.previousPath();
    if (fs.isValidWholePath(from) && fs.isValidWholePath(to)) {
      from.nextPath(name);
      Directory found = fs.findWholeDirectory(from);
      if (found != null) {
        SystemNavigator.moveItemTo(fs, from, to, false);
        return true;
      } else {
        from.previousPath();
        File targetFile = fs.findWholeDirectory(from).getFile(name);
        if (targetFile == null) {
          DirectoryNotFound.printErrorMessage();
          return false;
        }
        from.nextPath(name);
        SystemNavigator.moveItemTo(fs, from, to, true);
        return true;
      }
    }
    DirectoryNotFound.printErrorMessage();
    return false;
  }

  /**
   * The execute method for CmdMv.
   * 
   * @param param the parameters for the command
   * @param fs the current fileSystem
   * @return true if successfully executed, false otherwise
   */
  @Override
  public boolean execute(String param, FileSystem fs) {
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    if (arguments.size() < 2) {
      MissingArgument.printErrorMessage();
    } else if (arguments.size() > 2) {
      ExtraArgument.printErrorMessage();
    } else {
      return this.doMove(arguments, fs);
    }
    return false;
  }

  /**
   * Prints out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  @Override
  public void displayDone(String param) {
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    System.out.println("mv");
    System.out.println(String.join(" ", arguments));
  }
}
