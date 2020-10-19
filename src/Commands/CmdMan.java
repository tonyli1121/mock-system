package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.MissingArgument;
import errorLoggerSystem.ExtraArgument;
import fileSystems.FileSystem;

/**
 * This class executes command 'man'
 * 
 * @author Pengpeng Cao
 *
 */
public class CmdMan implements ExecutableCmd {

  /**
   * Return true and print out the documentation of command if the parameter is valid. Return false
   * and gives error message if the following messages:- Empty parameter, since man needs at least
   * one COMMAND to run - Multiple parameters, since man prints out one documentation at one time -
   * Given COMMAND does not exist in the manual.
   * 
   * @param param The whole parameter String after the command
   * @param fs The FileSystem that the user is currently in
   * @return a boolean value true or false indicating if the operation is executed successfully
   * @author Pengpeng Cao
   */
  @Override
  public boolean execute(String param, FileSystem fs) {
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));

    if (arguments.size() == 0) {
      MissingArgument.printErrorMessage();
      return false;
    }
    if (arguments.size() > 1) {
      ExtraArgument.printErrorMessage();
      return false;
    } else {
      return Manual.performMan(arguments.get(0));
    }
  }

  /**
   * Prints out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  @Override
  public void displayDone(String param) {
    System.out.println("man");
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    if (!arguments.isEmpty()) {
      System.out.println(String.join(" ", arguments));
    }
  }

}
