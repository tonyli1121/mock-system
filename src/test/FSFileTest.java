package test;

import static org.junit.Assert.*;
import org.junit.*;
import fileSystems.File;

public class FSFileTest {
  /*
   * 1. Create new file
   * 2. Create new file with data
   * 3. Append new data
   */
  private File testFile;

  @Test
  public void newFile() {
    testFile = new File("testFile");
    assertEquals(testFile.getData(), "");
  }

  @Test
  public void newFileWData() {
    testFile = new File("testFile", "line1");
    assertEquals(testFile.getData(), "line1");
  }

  @Test
  public void newAppendData() {
    testFile = new File("testFile", "line1");
    testFile.appendData("still line1");
    assertEquals(testFile.getData(), "line1\nstill line1");
  }
}
