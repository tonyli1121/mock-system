package errorLoggerSystem;

/**
 * This is the error case for: invalid url
 * @author Xingnian Jin
 *
 */
public class InvalidUrlError implements ErrorLogger{

  /**
   * prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Invalid URL entered");
    return true;
  }
}
