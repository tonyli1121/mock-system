package test;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import Commands.CmdCat;
import Commands.CmdEcho;
import fileSystems.FileSystem;

/**
 * CmdCatTest tests functionalities for CmdCat
 * @author Xingnian Jin
 *
 */
public class CmdCatTest {
  
  CmdEcho echo = new CmdEcho();
  FileSystem fs = new FileSystem();
  CmdCat myCat = new CmdCat();
  
  /**
   * This is a setup for the following tests
   */
  @Before
  public void setUp() {
    echo.execute("\"msg\" >file1", fs);
    echo.execute("\"msg\" >file2", fs);
  }
  
  /**
   * Tests normal print a file
   */
  @Test
  public void printFileTest() {
    myCat.execute("file1", fs);
    assertEquals("msg\n\n", myCat.getPrintedValue());
  }
  
  /**
   * Test print multiple files
   */
  @Test
  public void printMultipleFileTest() {
    myCat.execute("file1 file2", fs);
    assertEquals("msg\n\nmsg\n\n", myCat.getPrintedValue());
  }
  
  /**
   * Test missing argument error
   */
  @Test
  public void missingArgErrTest() {
    myCat.execute("", fs);
    assertEquals("missing argument error msg", myCat.getPrintedValue());
  }
  
  /**
   * Test file not found error
   */
  @Test
  public void fileNotFoundErrTest() {
    myCat.execute("file3", fs);
    assertEquals("file not found error msg", myCat.getPrintedValue());
  }
  
  /**
   * Test argument type error
   */
  @Test
  public void argumentTypeErrTest() {
    myCat.execute("aPath/", fs);
    assertEquals("argument type error msg", myCat.getPrintedValue());
  }
  
  /**
   * Test directory not found error
   */
  @Test
  public void directoryNotFoundErrTest() {
    myCat.execute("/a/file1", fs);
    assertEquals("directory not found error msg", myCat.getPrintedValue());
  }
}
