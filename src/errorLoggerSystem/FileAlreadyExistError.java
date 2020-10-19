package errorLoggerSystem;

/**
 * This is the error case for: file already exist
 * @author Xingnian Jin
 *
 */
public class FileAlreadyExistError implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: File already exist in the current directory");
    return true;
  }

}
