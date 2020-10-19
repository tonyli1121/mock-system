package driver;

import errorLoggerSystem.*;

/**
 * This class is an error check class for Jshell, it checks if the command input is a valid command,
 * and possible error checks for exit and pwd
 * 
 * @author Tianxiao Li
 *
 */
public class Validity {

  /**
   * This method checks if the input is one of the proper commands.
   * 
   * @param com a string input waits to be checked
   * @return true if it is one of the commands, false otherwise
   */
  private static boolean isValidCommand(String com) {
    for (String c: CommandsConstants.commands) {
    	if (c.contentEquals(com))
    		return true;
    }
    return false;
  }

  /**
   * This method checks for command exit, for case: 
   * - too many argument
   * 
   * @param arg argument to be checked
   * @return true if passed, false otherwise
   */
  private static boolean commandExitTestPassed(String arg) {
    // ERROR CASE: too many argument
    if (!(arg.isEmpty())) {
      ExtraArgument.printErrorMessage();
      return false;
    }

    return true; // passed
  }


  /**
   * This method checks for command pwd, for case: 
   * - too many argument
   * 
   * @param arg argument to be checked
   * @return true if passed, false otherwise
   */
  private static boolean commandPwdTestPassed(String arg) {
    // Error Case: too many arguments
    if (!arg.isEmpty()) {
      ExtraArgument.printErrorMessage();
      return false;
    }
    return true;
  }

  /**
   * This method checks commands by name by calling corresponding error checks, 
   * it only checks for exit and pwd, other commands are checked when executed
   * 
   * @param command command to be checked
   * @param arg arguments for the command to read (to be checked)
   * @return true if pass test, false otherwise
   */
  private static boolean checkCommandsByNames(String command, String arg) {
    switch (command) {
      case ("exit"):
        return commandExitTestPassed(arg);
      case ("pwd"):
        return commandPwdTestPassed(arg);
      
      default:
        return true; // others done in command class
    }
  }

  /**
   * This method checks for errors, input with arguments are checked only if there is one argument
   * (no whitespace).
   * 
   * @param myInput this is a type Input parameter, waiting to be checked
   * @return true if pass, false otherwise
   */
  protected static boolean checkValidityforShell(Input myInput) {

    String command = myInput.getCommand();
    String arg = myInput.getArguments();

    // empty input
    if (command.isEmpty()) {
      // next input
      return true;
    }

    // valid command, now check specifically
    else if (isValidCommand(command)) {
      return checkCommandsByNames(command, arg);
    }

    // invalid command
    InvalidCommand.printErrorMessage();
    return false;
  }
}
