package test;

import org.junit.*;
import Commands.CmdLs;
import Commands.CmdMkdir;
import fileSystems.FileSystem;
import static org.junit.Assert.*;

/**
 * TestCmdLs tests functionalities of 'ls' command in file system
 * @author Xingnian Jin
 *
 */
public class CmdLsTest{

  FileSystem fs = new FileSystem();
  CmdLs myLs = new CmdLs();
  CmdMkdir mkdir = new CmdMkdir();
  
  /**
   * Set up function for the whole unit test
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception{
    // Set up making directories
    mkdir.execute("a", fs);
    mkdir.execute("b", fs);
    mkdir.execute("a/a1", fs);
    mkdir.execute("a/a2", fs);
    mkdir.execute("b/b1", fs);
    mkdir.execute("b/b2", fs);
  }
  
  /**
   * Test ls with no parameter
   */
  @Test
  public void noParamTest() {
    myLs.execute("", fs);
    assertEquals("a/\nb/", myLs.getPrintedValue());
  }
  
  /**
   * Test ls with recursive -R only
   */
  @Test
  public void recursiveOnlyTest() {
    myLs.execute("-R", fs);
    assertEquals("\n/\na/\na1/\na2/\nb/\nb1/\nb2/", myLs.getPrintedValue());
  }
  
  /**
   * Test ls with global path input
   */
  @Test
  public void globalPathTest() {
    myLs.execute("/a/", fs);
    assertEquals("/a/:\na1/\na2/\n", myLs.getPrintedValue());
  }
  
  /**
   * Test ls with local path input
   */
  @Test
  public void localPathTest() {
    myLs.execute("a/", fs);
    assertEquals("a/:a1/\na2/\n", myLs.getPrintedValue());
  }
  
  /**
   * Test ls with recursive -R and path input
   */
  @Test
  public void recursiveWithPathTest() {
    myLs.execute("-R /a/", fs);
    assertEquals("\na/\na1/\na2/", myLs.getPrintedValue());
  }
  
  @Test
  public void multiplePathTest() {
    myLs.execute("/a/ /b/", fs);
    assertEquals("/a/:\na1/\na2/\n/b/:\nb1/\nb2/\n", myLs.getPrintedValue());
  }
  
  /**
   * Test ls with directory error occurs
   */
  @Test
  public void directoryErrorTest() {
    myLs.execute("c/", fs);
    assertEquals("directory not found error msg", myLs.getPrintedValue());
  }
  
  /**
   * Test ls with file not found error occurs
   */
  @Test
  public void fileErrorTest() {
    myLs.execute("randommsg", fs);
    assertEquals("file not found error msg", myLs.getPrintedValue());
  }
  
  /**
   * Test ls with argument format error occurs
   */
  @Test
  public void argumentFormatErrorTest() {
    myLs.execute("-R c/", fs);
    assertEquals("argument format error msg", myLs.getPrintedValue());
  }
}
