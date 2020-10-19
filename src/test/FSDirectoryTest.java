package test;

import static org.junit.Assert.*;
import org.junit.*;
import fileSystems.Directory;
import fileSystems.File;

public class FSDirectoryTest {
  /*
   * 1. Create new directory 
   * 2. Add a file to the directory 
   * 3. Add a directory to the directory 
   * 4. Test if a file exists 
   * 5. Test if a directory exists 
   * 6. Test getting a file 
   * 7. Test getting a directory 
   * 8. Test getting a none-existing file 
   * 9. Test getting a none-existing directory 
   * 10. Test get all sub elements
   * 11. Test removing a directory 
   * 12. Test removing a file
   */

  private Directory testDir;

  @Before
  public void setUp() {
    testDir = new Directory("testDir");
  }

  @Test
  public void newDirectory() {
    assertEquals(testDir.getName(), "testDir");
  }

  @Test
  public void addFile() {
    testDir.addFile(new File("file"));
    assertEquals(testDir.hasFile("file"), true);
  }

  @Test
  public void addDir() {
    testDir.addDirectory(new Directory("dir"));
    assertEquals(testDir.contains("dir"), true);
  }

  @Test
  public void hasFile() {
    testDir.addFile(new File("file"));
    assertEquals(testDir.hasFile("file2"), false);
  }

  @Test
  public void hasDir() {
    testDir.addDirectory(new Directory("dir"));
    assertEquals(testDir.contains("dir2"), false);
  }

  @Test
  public void getFile() {
    File newFile = new File("file");
    testDir.addFile(newFile);
    assertEquals(testDir.getFile("file"), newFile);
  }

  @Test
  public void getDir() {
    Directory newDir = new Directory("dir");
    testDir.addDirectory(newDir);
    assertEquals(testDir.search("dir"), newDir);
  }

  @Test
  public void getNonFile() {
    File newFile = new File("file");
    testDir.addFile(newFile);
    assertEquals(testDir.getFile("file2"), null);
  }

  @Test
  public void getNonDir() {
    Directory newDir = new Directory("dir");
    testDir.addDirectory(newDir);
    assertEquals(testDir.search("dir2"), null);
  }

  @Test
  public void getSubAll() {
    File newFile = new File("file");
    Directory newDir = new Directory("dir");
    testDir.addFile(newFile);
    testDir.addDirectory(newDir);
    assertTrue(testDir.getSubAll().contains("file") && testDir.getSubAll().contains("dir"));
  }
  
  @Test
  public void removeFile() {
    testDir.addFile(new File("file"));
    testDir.removeFile("file");
    assertEquals(testDir.hasFile("file"), false);
  }

  @Test
  public void removeDir() {
    testDir.addDirectory(new Directory("dir"));
    testDir.removeDirectory("dir");
    assertEquals(testDir.contains("dir"), false);
  }
}
