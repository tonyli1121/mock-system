package errorLoggerSystem;

/**
 * This is the error case for: stack empty error
 * @author Xingnian Jin
 *
 */
public class StackEmpty implements ErrorLogger{
  
  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Directory stack empty");
    return true;
  }

}
