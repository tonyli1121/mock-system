/**
 * 
 */
package test;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Commands.CmdMkdir;
import fileSystems.FileSystem;
import fileSystems.Path;

/**
 * This is the unit test for mkdir
 * 
 * @author Xuezhi Yan
 *
 */
public class CmdMkdirTest {
  FileSystem fs = new FileSystem();
  CmdMkdir mk = new CmdMkdir();
  String actual;
  java.io.ByteArrayOutputStream out;
  String actualErrorMessage;

  /**
   * Creates a tree-like filesystem and allows to capture output
   * 
   * @throws java.lang.Exception
   */
  @Before
  public void setup() {
    out = new java.io.ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
  }

  /**
   * closes the outputStream
   * 
   * @throws IOException
   * 
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws IOException {
    out.close();
  }

  @Test
  public void createDir() {
    mk.execute("a", fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.isValidWholePath(new Path("a")));
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void createDirRelative() {
    mk.execute("a", fs);
    mk.execute("a/b", fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.isValidWholePath(new Path("a/b")));
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void createDirGlobal() {
    mk.execute("a", fs);
    mk.execute("/a/b", fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.isValidWholePath(new Path("a/b")));
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void createManyDir() {
    mk.execute("a     a/b    c a/b/c", fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.isValidWholePath(new Path("a")));
    assertTrue(fs.isValidWholePath(new Path("a/b")));
    assertTrue(fs.isValidWholePath(new Path("c")));
    assertTrue(fs.isValidWholePath(new Path("a/b/c")));
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void createRepetitiveDir() {
    mk.execute("a     a", fs);
    actualErrorMessage = out.toString();
    assertEquals(actualErrorMessage, "[a]Error: Given directory already exists in the path\r\n");
  }

  @Test
  public void createFalsePath() {
    mk.execute("b/c", fs);
    actualErrorMessage = out.toString();
    assertEquals(actualErrorMessage, "[b/c]Error: Given directory does not exist\r\n");
  }

  @Test
  public void outputSuccess() {
    mk.execute("a      b c", fs);
    mk.displayDone("a    b  c");
    actualErrorMessage = out.toString();
    assertEquals(actualErrorMessage, "mkdir\r\na b c\r\n");
  }
}
