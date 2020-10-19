package errorLoggerSystem;

/**
 * This is the error case for: first command error.
 * @author Xingnian Jin
 *
 */
public class FirstCommandError implements ErrorLogger {

  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: This command should be executed before any other commands");
    return true;
  }

}
