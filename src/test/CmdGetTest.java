
package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Commands.CmdGet;
import fileSystems.FileSystem;

/**
 * This is the unit tests for get command
 * @author Tianxiao Li
 *
 */
public class CmdGetTest {
  FileSystem fs = new FileSystem();
  CmdGet myGet = new CmdGet();
  java.io.ByteArrayOutputStream out;
  
  String website;
  String actual;
  String actualErrorMessage;
  
  /**
   * set up default values before execution
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    website = "";
    actual = "";
    actualErrorMessage = "";
    out = new ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
  }

  /**
   * closes the output stream
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    out.close();
  }

  @Test
  public void runnableTest() {
    website = "https://www.w3.org/TR/PNG/iso_8859-1.txt";
    myGet.execute(website, fs);
    actualErrorMessage = out.toString();
    assertFalse(fs.getCurrentList().isEmpty());
    assertTrue("This should not print error message", actualErrorMessage.isEmpty());
  }
  
  @Test
  public void invalidUrlTestOne() {
    website = "https://www.w3.org/TR/PNG/iso_8859-1.txt/";
    myGet.execute(website, fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.getCurrentList().isEmpty());
    assertFalse("This should not print error message", actualErrorMessage.isEmpty());
  }
  
  @Test
  public void invalidUrlTestTwo() {
    website = "https://www.w3.org/TR/PNG/asdfasdf";
    myGet.execute(website, fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.getCurrentList().isEmpty());
    assertFalse("This should not print error message", actualErrorMessage.isEmpty());
  }
  
  @Test
  public void invalidUrlTestThree() {
    website = "abc";
    myGet.execute(website, fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.getCurrentList().isEmpty());
    assertFalse("This should not print error message", actualErrorMessage.isEmpty());
  }
  
  @Test
  public void invalidUrlTestFour() {
    website = "";
    myGet.execute(website, fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.getCurrentList().isEmpty());
    assertFalse("This should not print error message", actualErrorMessage.isEmpty());
  }
  
  @Test
  public void repeatSameFileExistTest() {
    website = "https://www.w3.org/TR/PNG/iso_8859-1.txt";
    myGet.execute(website, fs);
    myGet.execute(website, fs);
    actualErrorMessage = out.toString();
    assertFalse(fs.getCurrentList().isEmpty());
    assertFalse("This should not print error message", actualErrorMessage.isEmpty());
  }

}
