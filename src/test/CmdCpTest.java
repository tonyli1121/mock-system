package test;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Commands.CmdCp;
import Commands.HistoryTracker;
import fileSystems.FileSystem;
import fileSystems.FileSystemLoader;
import fileSystems.Path;

public class CmdCpTest {
  CmdCp cp = new CmdCp();
  FileSystem fs;
  String actual;
  java.io.ByteArrayOutputStream out;
  String actualErrorMessage;

  @Before
  public void setup() {
    fs = new FileSystem();
    FileSystemLoader.loadToFileSystem("test", fs, new HistoryTracker());
    out = new java.io.ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
  }

  @After
  public void tearDown() throws IOException {
    out.close();
  }

  @Test
  public void copyAtoB() {
    cp.execute("a/b a/c/d", fs);
    actualErrorMessage = out.toString();
    assertTrue(fs.isValidWholePath(new Path("a/c/d/c")));
    assertEquals(fs.getFileInfoRelative(new Path("a/c/d"), "file2"), "line1 of file2");
    assertTrue("This should not print error", actualErrorMessage.isEmpty());
  }

  @Test
  public void onePathNonExist() {
    cp.execute("c/b a/c/d", fs);
    actualErrorMessage = out.toString();
    assertEquals("Error: Given directory does not exist\r\n", actualErrorMessage);
  }

  @Test
  public void parentRelationship() {
    cp.execute("a a/b", fs);
    actualErrorMessage = out.toString();
    assertEquals("Error: Cannot move/copy a parent directory into its child\r\n",
        actualErrorMessage);
  }

  @Test
  public void outputSuccess() {
    cp.displayDone("a    b");
    actualErrorMessage = out.toString();
    assertEquals(actualErrorMessage, "cp\r\na b\r\n");
  }

}
