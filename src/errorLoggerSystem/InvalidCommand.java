package errorLoggerSystem;

/**
 * This is the error case for: invalid command entered
 * @author Xingnian Jin
 *
 */
public class InvalidCommand implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Invalid command entered");
    return true;
  }

}
