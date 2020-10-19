package errorLoggerSystem;

/**
 * This is the error case for: parent directory error
 * @author Xingnian Jin
 *
 */
public class ParentDirectoryError  implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Cannot move/copy a parent directory into its child");
    return true;
  }


}
