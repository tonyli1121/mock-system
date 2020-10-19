/**
 * 
 */
package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import driver.Input;

/**
 * This is unit test for Input class in driver package, checks setter and getter
 * at the same time
 * @author Tianxiao Li
 *
 */
public class InputTest {

  Input myIn = new Input();
  String expectedCmd;
  String expectedArg;
  /**
   * set up default values
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    expectedCmd = "";
    expectedArg = "";
  }


  @Test
  public void whiteSpacetest1() {
    myIn.setFullInput("cd             /a/");
    expectedCmd = "cd";
    expectedArg = "/a/";
    assertTrue("command wrong.", myIn.getCommand().contentEquals(expectedCmd));
    assertTrue("Argument wrong.", myIn.getArguments().contentEquals(expectedArg));
  }

  @Test
  public void whiteSpacetest2() {
    myIn.setFullInput("mkdir     1 2");
    expectedCmd = "mkdir";
    expectedArg = "1 2";
    assertTrue("command wrong.", myIn.getCommand().contentEquals(expectedCmd));
    assertTrue("Argument wrong.", myIn.getArguments().contentEquals(expectedArg));
  }
  
  @Test
  public void whiteSpacetest3() {
    myIn.setFullInput("        cd             /a/");
    expectedCmd = "cd";
    expectedArg = "/a/";
    assertTrue("command wrong.", myIn.getCommand().contentEquals(expectedCmd));
    assertTrue("Argument wrong.", myIn.getArguments().contentEquals(expectedArg));
  }
  
  @Test
  public void whiteSpacetest4() {
    myIn.setFullInput("cd /a/");
    expectedCmd = "cd";
    expectedArg = "/a/";
    assertTrue("command wrong.", myIn.getCommand().contentEquals(expectedCmd));
    assertTrue("Argument wrong.", myIn.getArguments().contentEquals(expectedArg));
  }
  
  @Test
  public void whiteSpacetest5() {
    myIn.setFullInput("cd  ");
    expectedCmd = "cd";
    expectedArg = "";
    assertTrue("command wrong.", myIn.getCommand().contentEquals(expectedCmd));
    assertTrue("Argument wrong.", myIn.getArguments().contentEquals(expectedArg));
  }
}
