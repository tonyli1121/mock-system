package Commands;

import errorLoggerSystem.ExtraArgument;
import errorLoggerSystem.StackEmpty;
import fileSystems.FileSystem;

/**
 * This class executes command popd.
 * @author Xingnian Jin
 *
 */
public class CmdPopd implements ExecutableCmd {

  /**
   * Return true and pop the top entry from the directory stack and set the current directory to it.
   * Return false and print out the error message if the following:
   * - Extra parameter, since popd does not take in parameter
   * - Empty stack, if the stack reaches its bottom and there is no DIR to pop
   * 
   * @param param the parameter from user input followed by the command
   * @param fs the current FileSystem
   * @return a boolean value true or false indicating if the operation is executed successfully
   * @author Xingnian Jin
   */
  
  private String errMsg = "";
  @Override
  public boolean execute(String param, FileSystem fs) {
    if (param.length() != 0) {
      ExtraArgument.printErrorMessage();
      errMsg = "extra argument error msg";
      return false;
    }
    
    if (!fs.popDirectory()) {
      StackEmpty.printErrorMessage();
      errMsg = "stack empty error msg";
      return false;
    }
    
    return true;
  }

  /**
   * Getter for errMsg
   * 
   * @return errMsg
   */
  public String getErrMsg() {
    return this.errMsg;
  }
  
  /**
   * Print out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  @Override
  public void displayDone(String param) {
    System.out.println("popd");
    System.out.println(param);
    
  }
}
