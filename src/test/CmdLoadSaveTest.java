/**
 * 
 */
package test;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Commands.CmdEcho;
import Commands.CmdLoad;
import Commands.CmdMkdir;
import Commands.CmdSave;
import Commands.HistoryTracker;
import fileSystems.FileSystem;
import fileSystems.Path;

/**
 * This is the unit test for mkdir
 * 
 * @author Xuezhi Yan
 *
 */
public class CmdLoadSaveTest {
  FileSystem fs;
  HistoryTracker his;
  CmdMkdir mk = new CmdMkdir();
  CmdEcho echo = new CmdEcho();
  String actual;
  java.io.ByteArrayOutputStream out;
  String actualErrorMessage;

  FileSystem resultfs;
  HistoryTracker resulthis;

  /**
   * Creates a tree-like filesystem and allows to capture output
   * 
   * @throws java.lang.Exception
   */
  @Before
  public void setup() {
    out = new java.io.ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
    fs = new FileSystem();
    his = new HistoryTracker();
    his.push("a");
    mk.execute("a a/b a/b/c a/d", fs);
    fs.createDirWhole(new Path(), "folder");
    fs.echoToFile("file", "abc", false);
    fs.pushDirectory("folder");

    his.push("cmd1");
    his.push("cmd2");

    resultfs = new FileSystem();
    resulthis = new HistoryTracker();
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
  public void saveToInvalidPath() {
    CmdSave.execute("aaa/a/a/a/a/a/a/a", fs, his);
    actualErrorMessage = out.toString();
    assertEquals("Error: Given directory does not exist\r\n", actualErrorMessage);
  }

  @Test
  public void loadFromInvalidPath() {
    CmdLoad.execute("aaa/a/a/a/a/a/a/a", fs, his);
    actualErrorMessage = out.toString();
    assertEquals("Error: Given file name not found\r\n", actualErrorMessage);
  }
  
  @Test
  public void saveOutputSuccess() {
    CmdSave.displayDone("a");
    actualErrorMessage = out.toString();
    assertEquals(actualErrorMessage, "save\r\na\r\n");
  }
  
  @Test
  public void loadOutputSuccess() {
    CmdLoad.displayDone("a");
    actualErrorMessage = out.toString();
    assertEquals(actualErrorMessage, "load\r\na\r\n");
  }
  
  @Test
  public void saveThenLoad() {
    CmdSave.execute("a", fs, his);
    CmdLoad.execute("a", resultfs, resulthis);
    actualErrorMessage = out.toString();
    //The result file system and history were tested in the FileSystemLoaderSaverTest
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }
}
