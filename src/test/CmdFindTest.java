package test;

import org.junit.*;
import Commands.CmdEcho;
import Commands.CmdFind;
import Commands.CmdMkdir;
import fileSystems.FileSystem;
import static org.junit.Assert.*;

/**
 * CmdFindTest tests functionalities of 'find' command in file system
 * @author Xingnian Jin
 *
 */
public class CmdFindTest {
  
  FileSystem fs = new FileSystem();
  CmdFind myFind = new CmdFind();
  CmdMkdir mkdir = new CmdMkdir();
  CmdEcho echo = new CmdEcho();

  /**
   * Set up function for the whole unit test
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception{
    mkdir.execute("a", fs);
    mkdir.execute("b", fs);
    mkdir.execute("a/a1", fs);
    mkdir.execute("a/target", fs);
    mkdir.execute("b/b1", fs);
    mkdir.execute("b/target", fs);
    echo.execute("\"msg\" >/a/targetFile", fs);
    echo.execute("\"msg\" >/b/targetFile", fs);
  }
  
  /**
   * Test find directory with single path
   */
  @Test
  public void singlePathFindDirTest() {
    myFind.execute("/a -type d -name \"target\"", fs);
    assertEquals("Found directory with the name: [target] in path: a/", myFind.getPrintedValue());
  }
  
  /**
   * Test find file with single path
   */
  @Test
  public void singlePathFindFileTest() {
    myFind.execute("/a -type f -name \"targetFile\"", fs);
    assertEquals("Found file with the name: [targetFile] in path: a/", myFind.getPrintedValue());
  }
  
  /**
   * Test find directory with multiple paths
   */
  @Test
  public void multiPathFindDirTest() {
    myFind.execute("/a /b -type d -name \"target\"", fs);
    assertEquals("Found directory with the name: [target] in path: a/Found directory with the "
        + "name: [target] in path: b/", myFind.getPrintedValue());
  }
  
  /**
   * Test find file with multiple paths
   */
  @Test
  public void multiPathFindFileTest() {
    myFind.execute("/a /b -type f -name \"targetFile\"", fs);
    assertEquals("Found file with the name: [targetFile] in path: a/Found file with the name: "
        + "[targetFile] in path: b/", myFind.getPrintedValue());
  }
  
  /**
   * Test missing argument error case
   */
  @Test
  public void missingArgumentErrTest() {
    myFind.execute("", fs);
    assertEquals("missing argument error msg", myFind.getPrintedValue());
  }
  
  /**
   * Test argument type error case
   */
  @Test
  public void argumentTypeErrTest() {
    myFind.execute("/a -typo d -name \"target\"", fs);
    assertEquals("argument format error msg", myFind.getPrintedValue());
  }
  
}
