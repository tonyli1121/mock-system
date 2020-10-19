package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.ArgumentTypeError;
import errorLoggerSystem.ExtraArgument;
import errorLoggerSystem.FileNotFound;
import errorLoggerSystem.InputStreamInterruptedError;
import errorLoggerSystem.MissingArgument;
import fileSystems.FileSystem;
import fileSystems.FileSystemLoader;

/**
 * This class is responsible for the execution of load.
 * 
 * @author Xuezhi Yan
 *
 */
public class CmdLoad {
  
  /**
   * The execute method for CmdLoad.
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
    if (arguments.size() == 0) {
      MissingArgument.printErrorMessage();
    } else if (arguments.size() > 1) {
      ExtraArgument.printErrorMessage();
    } else {
      int result = FileSystemLoader.loadToFileSystem(arguments.get(0), fs, history);
      switch (result) {
        case 0:
          return true;
        case 1:
          FileNotFound.printErrorMessage();
          break;
        case 2:
          InputStreamInterruptedError.printErrorMessage();
          break;
        case 3:
          ArgumentTypeError.printErrorMessage();
          break;
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
    System.out.println("load");
    System.out.println(param);
    return;
  }
}
