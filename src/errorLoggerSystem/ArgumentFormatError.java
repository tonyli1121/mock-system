package errorLoggerSystem;

/**
 * This is the error case for: argument format error
 * @author Xingnian Jin
 *
 */
public class ArgumentFormatError implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Argument format mismatch");
    return true;
  }

}
