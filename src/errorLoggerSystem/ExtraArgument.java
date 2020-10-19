package errorLoggerSystem;

/**
 * This is the error case for: extra argument error
 * @author Xingnian Jin
 *
 */
public class ExtraArgument implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Unnecessary argument added");
    return true;
  }

}
