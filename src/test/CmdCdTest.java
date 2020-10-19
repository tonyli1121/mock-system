/**
 * 
 */
package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Commands.CmdCd;
import Commands.CmdMkdir;
import fileSystems.FileSystem;

/**
 * This is the unit test for cd
 * 
 * @author Tianxiao Li
 *
 */
public class CmdCdTest {
  FileSystem fs = new FileSystem();
  CmdMkdir mk = new CmdMkdir();
  CmdCd myCd = new CmdCd();
  String actual;
  java.io.ByteArrayOutputStream out;
  String actualErrorMessage;

  /**
   * Creates a tree-like filesystem and allows to capture output
   * 
   * @throws java.lang.Exception
   */
  @Before
  public void setUpBeforeClass() throws Exception {
    mk.execute("a b", fs);
    mk.execute("/a/c/ /a/d/ /b/e/ /b/f/ /a/c/g/ /a/c/g/i/ /b/f/h/", fs);
    out = new java.io.ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
  }

  /**
   * closes the outputStream
   * 
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    out.close();
  }

  @Test
  public void currentLocationTest() {
    myCd.execute(".", fs);
    actual = fs.getCurrentPath();
    actualErrorMessage = out.toString();
    assertEquals("/", actual);
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void previoudLocationTest() {
    myCd.execute("..", fs);
    actual = fs.getCurrentPath();
    actualErrorMessage = out.toString();
    assertTrue(actual.contentEquals("/"));
    assertTrue("This should print an error", !actualErrorMessage.isEmpty());
  }

  @Test
  public void TobByFullPathTest() {
    myCd.execute("/b/", fs);
    actual = fs.getCurrentPath();
    actualErrorMessage = out.toString();
    assertEquals("/b/", actual);
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void TohFrombByRelativePathTest() {
    myCd.execute("/b/", fs);
    myCd.execute("f/h", fs);
    actual = fs.getCurrentPath();
    actualErrorMessage = out.toString();
    assertEquals("/b/f/h/", actual);
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void ToRootFromhUsingDotsTest() {
    myCd.execute("/b/f/h/", fs);
    myCd.execute("../../..", fs);
    actual = fs.getCurrentPath();
    actualErrorMessage = out.toString();
    assertEquals("/", actual);
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void ToeThencTest() {
    myCd.execute("/b/e/", fs);
    myCd.execute("../../a/c/", fs);
    actual = fs.getCurrentPath();
    actualErrorMessage = out.toString();
    assertEquals("/a/c/", actual);
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void ToNonExistTest() {
    myCd.execute("z", fs);
    actual = fs.getCurrentPath();
    actualErrorMessage = out.toString();
    assertEquals("/", actual);
    assertTrue("This should print an error", !actualErrorMessage.isEmpty());
  }

  @Test
  public void ToiFromcTest() {
    myCd.execute("/a/c/", fs);
    myCd.execute("g/i", fs);
    actual = fs.getCurrentPath();
    actualErrorMessage = out.toString();
    assertEquals("/a/c/g/i/", actual);
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }
}
