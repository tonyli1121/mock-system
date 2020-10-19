package errorLoggerSystem;

/**
 * This is the error case for: URL not accessible
 * @author Xingnian Jin
 *
 */
public class UrlNotAccessableError implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Given URL not accessible");
    return true;
  }

}
