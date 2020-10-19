package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.ExtraArgument;
import errorLoggerSystem.MissingArgument;
import fileSystems.FileSystem;
import fileSystems.SavedFileSystem;

/**
 * This class is responsible for the execution of save.
 * 
 * @author Xuezhi Yan
 *
 */
public class CmdSave {
  
  /**
   * The execute method for CmdSave.
   * 
   * @param param the parameters for the command
   * @param fs the current working FileSystem
   * @param history the current HistoryTracker
   * @return true if successfully executed, false otherwise
   */
  public static boolean execute(String param, FileSystem fs, HistoryTracker history) {
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    if(arguments.size() == 0) {
      MissingArgument.printErrorMessage();
    } else if (arguments.size() > 1) {
      ExtraArgument.printErrorMessage();
    } else {
      SavedFileSystem saveFs = new SavedFileSystem();
      saveFs.loadFromSystem(fs, history);
      if(saveFs.saveToLocal(arguments.get(0))) {
        return true;
      } else {
        DirectoryNotFound.printErrorMessage();
      }
    }
    return false;
  }
  
  /**
   * Prints out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  public static void displayDone(String param) {
    System.out.println("save");
    System.out.println(param);
    return;
  }
}
