package test;

/**
 * The parent class of all test files.
 * 
 * @author Xuezhi Yan
 * @author Tianxiao Li
 */
public class TestFile {
  /**
   * Print message when test begins.
   * 
   * @param strIn the message indicates the test begin
   */
  protected static void log(String strIn) {
    System.out.println("--------------------------");
    System.out.println("[TEST]" + strIn);
  }

  /**
   * Print current list of resultant layer.
   * 
   * @param strIn the name of new layer
   */
  protected static void result(String strIn) {
    System.out.println("[OUTPUT]\n" + strIn + "\n");
  }

  /**
   * Print message when the next test is about to begin.
   * 
   * @param strIn the description of next test
   */
  protected static void action(String strIn) {
    System.out.println("[ACTION]" + strIn);
  }

  /**
   * Assert if the answer is correct.
   * 
   * @param result the actual result
   * @param answer the correct answer
   */
  protected static void check(Object result, Object answer) {
    assert (result.equals(answer)) : "Test Failed";
  }
  
  /**
   * Provide a separator for each test cases
   * 
   */
  protected static void separator() {
    System.out.println("\n......................................................");
  }

  /**
   * Assert if the answer is correct.
   * 
   * @param result true if passed, false if not
   */
  protected static void check(boolean result) {
    assert result : "Test Failed";
  }
  
  /**
   * Prints out the correct output
   * @param strIn The correct output
   */
  public static void correctOutput(String strIn) {
    System.out.println("[CORRECT OUTPUT]\n" + strIn + 
        "\n......................................................");
  }
}
