package errorLoggerSystem;

/**
 * This is the error case for: file not found error
 * @author Xingnian Jin
 *
 */
public class FileNotFound implements ErrorLogger {
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Given file name not found");
    return true;
  }

}
