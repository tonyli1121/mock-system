package errorLoggerSystem;

/**
 * This is the error case for: missing necessary argument
 * @author Xingnian Jin
 *
 */
public class MissingArgument implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Necessary argument missing");
    return true;
  }
}
