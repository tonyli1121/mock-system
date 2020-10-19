package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import Commands.CmdEcho;
import fileSystems.FileSystem;

/**
 * CmdEchoTest tests functionalities of CmdEcho
 * @author Xingnian Jin
 *
 */
public class CmdEchoTest {

  FileSystem fs = new FileSystem();
  CmdEcho echo = new CmdEcho();
  
  @Before
  public void setUp() {
    
  }
  
  @Test
  public void justPrintTest() {
    echo.execute("\"testMsg\"", fs);
    assertEquals("testMsg", echo.getPrintedValue());
  }
  
  @Test
  public void echoToFileTest() {
    echo.execute("\"msg\" >file1", fs);
    assertTrue(fs.getCurrentDirectory().hasFile("file1"));
  }
  
  @Test
  public void missingArgErrTest() {
    echo.execute("", fs);
    assertEquals("missing argument error msg", echo.getPrintedValue());
  }
  
  @Test
  public void argumentFormatErrTest() {
    echo.execute("msg", fs);
    assertEquals("argument format error msg", echo.getPrintedValue());
  }
  
  @Test
  public void directoryNotFoundErrTest() {
    echo.execute("\"msg\" >a/file1", fs);
    assertEquals("directory not found error msg", echo.getPrintedValue());
  }
  
}
