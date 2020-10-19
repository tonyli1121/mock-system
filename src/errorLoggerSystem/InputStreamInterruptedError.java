package errorLoggerSystem;

/**
 * This is the error case for: input stream interrupted error
 * @author Xingnian Jin
 *
 */
public class InputStreamInterruptedError implements ErrorLogger {

  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Input stream interrupted");
    return true;
  }

}
