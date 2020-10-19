package Commands;

/**
 * This class is used to determine if a string contains special char.
 * 
 * @author Tianxiao Li
 */
public class ValidityCheck {
  
  /**
   * Contain all the special characters.
   */
  private final static String[] specialChar = {"/", ".", ",", "!", "@", "#", "$", "%", "^", "&",
      "*", "(", ")", "{", "}", "~", "|", "<", ">", "?", "\"", "\'", "\\"};

  /**
   * This method returns a string version of parameter such that all the left dashes '/' are
   * omitted.
   * 
   * @param dir This is a raw directory input.
   * @return dir but with all left dashes'/' removed.
   */
  private static String omitLeftDash(String dir) {
    String[] temp = dir.split("/");
    return (String.join("", temp));
  }

  /**
   * Returns whether or not a path contains only valid characters.
   * 
   * @param path the path to check
   * @return true if doesn't contain special char, false otherwise
   */
  protected static boolean validPath(String path) {

    // check for special chars
    for (String specialChars : specialChar) {
      if (specialChars != "/" && specialChars != ".") {
        if (path.contains(specialChars)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Check if the input contains special char.
   * 
   * @param dir input to be checked
   * @return true if doesn't contain special char, false otherwise
   */
  protected static boolean noSpecialChar(String dir) {

    // check for special chars
    for (String specialChars : specialChar) {
      if (omitLeftDash(dir).contains(specialChars)) {
        return false;
      }
    }
    return true;
  }
}
