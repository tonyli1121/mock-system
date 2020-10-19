package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import Commands.*;
import errorLoggerSystem.*;
import fileSystems.FileSystem;

/**
 * This instance is responsible for distinguishing command and perform the appropriate one.
 * 
 * @author Tianxiao Li
 *
 */
public class Execute {

  /**
   * Map each command to its behavior.
   */
  private static Hashtable<String, String> commandToBehaviour;

  /**
   * Initialize a HashTable for all commands.
   */
  private static void initializeHashTable() {
    commandToBehaviour = new Hashtable<String, String>();
    for (String com : CommandsConstants.commands) {
      commandToBehaviour.put(com,
          "Cmd" + com.substring(0, 1).toUpperCase() + com.substring(1).toLowerCase());
    }

  }

  /**
   * This method performs the command history, and displays a message when success.
   * 
   * @param args arguments for history to read, contains numbers only
   * @param myFileSystem a FileSystem to be working on
   */
  private static void performCmdHistory(String args, FileSystem myFileSystem) {
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(args.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    if (arguments.size() == 0) {
      Input.history.output();
      System.out.println("history");
    } else if (arguments.size() == 1) {
      if (arguments.get(0).chars().allMatch(Character::isDigit)) {
        Input.history.output(Integer.parseInt(arguments.get(0)));
        System.out.println("history");
        System.out.println(arguments.get(0));
      } else {
        ArgumentTypeError.printErrorMessage();
      }
    } else {
      ExtraArgument.printErrorMessage();
    }
  }

  /**
   * This method performs the command save, and displays message when succeed
   * 
   * @param args arguments
   * @param myFileSystem the file system
   */
  private static void performCmdSave(String args, FileSystem myFileSystem) {
    if (CmdSave.execute(args, myFileSystem, Input.history)) {
      CmdSave.displayDone(args);
    }
  }

  /**
   * This method performs the command load, and displays message when succeed
   * 
   * @param args arguments
   * @param myFileSystem the file system
   */
  private static void performCmdLoad(String args, FileSystem myFileSystem) {
    if (Input.history.isFirst()) {
      if (CmdLoad.execute(args, myFileSystem, Input.history)) {
        CmdLoad.displayDone(args);
      }
    } else {
      FirstCommandError.printErrorMessage();
    }
  }

  /**
   * This method distinguish and perform the command.
   * 
   * @param myInput this is the input from the shell, contains command and arguments
   * @param myFileSystem this is the FileSystem to be modified during the operations
   */
  public static void performCommand(Input myInput, FileSystem myFileSystem)
      throws ClassNotFoundException {
    String command = myInput.getCommand();
    String arguments = myInput.getArguments();
    if (command.contentEquals("history")) {
      performCmdHistory(arguments, myFileSystem);
      return;
    } else if (command.contentEquals("save")) {
      performCmdSave(arguments, myFileSystem);
      return;
    } else if (command.contentEquals("load")) {
      performCmdLoad(arguments, myFileSystem);
      return;
    }
    initializeHashTable();
    String className = (String) commandToBehaviour.get(command);
    Class c = Class.forName("Commands." + className);
    try {
      ExecutableCmd myCmd = (ExecutableCmd) c.newInstance();
      if (myCmd.execute(arguments, myFileSystem))
        myCmd.displayDone(arguments);
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
