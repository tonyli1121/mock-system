package errorLoggerSystem;

/**
 * This is the error case for: directory already exist
 * @author Xingnian Jin
 *
 */
public class DirectoryAlreadyExist implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Given directory already exists in the path");
    return true;
  }

}
