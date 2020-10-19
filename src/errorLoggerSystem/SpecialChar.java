package errorLoggerSystem;

/**
 * This is the error case for: special character appeared
 * @author Xingnian Jin
 *
 */
public class SpecialChar implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Special characters involved in Filename or Directory");
    return true;
  }
}
