package errorLoggerSystem;

/**
 * This is the error case for: directory not found
 * @author Xingnian Jin
 *
 */
public class DirectoryNotFound implements ErrorLogger{

  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Given directory does not exist");
    return true;
  }
}
