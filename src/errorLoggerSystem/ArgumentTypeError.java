package errorLoggerSystem;

/**
 * This is the error case for: argument type error
 * @author Xingnian Jin
 *
 */
public class ArgumentTypeError implements ErrorLogger{

  /**
   * Prints out the designated error message
   */
  public static boolean printErrorMessage() {
    System.out.println("Error: Argument type mismatch");
    return true;
  }
}
