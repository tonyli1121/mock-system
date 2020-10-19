package errorLoggerSystem;

/**
 * This is the error case for: current in root directory
 * @author Xingnian Jin
 *
 */
public class CurrentRootDirectory implements ErrorLogger{

  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Currently in root directory, can not proceed backward");
    return true;
  }
}
