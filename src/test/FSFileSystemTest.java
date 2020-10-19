package test;

import static org.junit.Assert.*;
import org.junit.*;
import Commands.HistoryTracker;
import fileSystems.File;
import fileSystems.FileSystem;
import fileSystems.FileSystemLoader;
import fileSystems.Path;

public class FSFileSystemTest {
  private FileSystem fs;
  private HistoryTracker history;
  
  /*
    *1. get current list
    *2. get relative list
    *3. get global list
    *4. relative path which is valid
    *5. relative path which isn't valid
    *6. global path which is valid
    *7. global path which isn't valid
    *8. find a relative directory
    *9. find a whole directory
    *10. find a non-existing directory
    *11. navigate within
    *12. navigate to relative path
    *13. navigate to global path
    *14. navigate to non-existing path
    *15. create a new directory
    *16. create a new relative directory
    *17. create a new global directory
    *18. echo a new file
    *19. echo a new relative file
    *20. echo a new global file
    *21. get the info of a file
    *22. get the info of a relative file
    *23. get the info of a non-existing file
    *24. push then pop a directory from the stack
    *25. go to the previous path
    *26. go to the previous path at root
    *27. add a new file to the directory
   */

  @Before
  public void setup() {
    fs = new FileSystem();
    history = new HistoryTracker();
    FileSystemLoader.loadToFileSystem("test", fs, history);
  }

  @Test
  public void getCurrentList() {
    assertTrue(fs.getCurrentList().contains("a") && fs.getCurrentList().contains("file1"));
  }

  @Test
  public void getRelativeList() {
    assertTrue(fs.getRelativeList(new Path("a")).contains("c")
        && fs.getRelativeList(new Path("a")).contains("b"));
  }

  @Test
  public void getGlobalList() {
    assertTrue(fs.getGlobalList(new Path("a")).contains("c")
        && fs.getGlobalList(new Path("a")).contains("b"));
  }

  @Test
  public void getValidRelavitePath() {
    assertTrue(fs.isValidRelativePath(new Path("a/b")));
  }

  @Test
  public void getInvalidRelavitePath() {
    assertTrue(!fs.isValidRelativePath(new Path("c/b")));
  }

  @Test
  public void getValidGlobalPath() {
    assertTrue(fs.isValidWholePath(new Path("a/b")));
  }

  @Test
  public void getInvalidGlobalPath() {
    assertTrue(!fs.isValidWholePath(new Path("c/b")));
  }

  @Test
  public void findRelativeDir() {
    assertEquals(fs.findRelativeDirectory(new Path("a/b")).getName(), "b");
  }

  @Test
  public void findGlobalDir() {
    assertEquals(fs.findWholeDirectory(new Path("a/b")).getName(), "b");
  }

  @Test
  public void findNonExistDir() {
    assertEquals(fs.findWholeDirectory(new Path("a/f")), null);
  }

  @Test
  public void navigateWithin() {
    fs.navigateWithin("a");
    assertEquals(fs.getCurrentDirectory().getName(), "a");
  }

  @Test
  public void navigateRelative() {
    fs.navigateRelative(new Path("a/b"));
    assertEquals(fs.getCurrentDirectory().getName(), "b");
  }

  @Test
  public void navigateGlobal() {
    fs.overrideWorkingDir("a/c");
    assertEquals(fs.getCurrentDirectory().getName(), "c");
  }

  @Test
  public void nagivateNonExisting() {
    assertTrue(!fs.overrideWorkingDir("d/c"));
  }

  @Test
  public void createNewDirectory() {
    fs.createDirWithin("dir");
    assertTrue(fs.getCurrentDirectory().contains("dir"));
  }

  @Test
  public void createRelativeDirectory() {
    fs.createDirRelative(new Path("a"), "dir");
    assertTrue(fs.findRelativeDirectory(new Path("a")).contains("dir"));
  }

  @Test
  public void createGlobalDirectory() {
    fs.createDirWhole(new Path("a/b"), "dir");
    assertTrue(fs.findRelativeDirectory(new Path("a/b")).contains("dir"));
  }

  @Test
  public void echoFile() {
    assertTrue(fs.echoToFile("file", "abc", false));
  }

  @Test
  public void echoToRelativeFile() {
    assertTrue(fs.echoToFileRelative(new Path("a"), "file", "abc", false));
  }

  @Test
  public void echoToGlobalFile() {
    assertTrue(fs.echoToFileWhole(new Path("a/b"), "file", "abc", false));
  }

  @Test
  public void getFileInfo() {
    assertEquals(fs.getFileInfo("file1"), "line1 of file1");
  }

  @Test
  public void getRelativeFileInfo() {
    assertEquals(fs.getFileInfoRelative(new Path("."), "file1"), "line1 of file1");
  }

  @Test
  public void getGlobalFileInfo() {
    assertEquals(fs.getFileInfoWhole(new Path("a/b"), "file2"), "line1 of file2");
  }

  @Test
  public void getNonExistFileInfo() {
    assertEquals(fs.getFileInfo("file"), null);
  }

  @Test
  public void addThenPopFromStack() {
    fs.pushDirectory("a");
    fs.popDirectory();
    assertEquals(fs.getCurrentDirectory().getName(), "");
  }

  @Test
  public void goToPreviousPath() {
    fs.navigateWithin("a");
    fs.navigateWithin("b");
    fs.previousDirectory();
    assertEquals(fs.getCurrentDirectory().getName(), "a");
  }

  @Test
  public void addNewFile() {
    fs.addFileAtCurrentDirectory(new File("file"));
    assertTrue(fs.getCurrentDirectory().hasFile("file"));
  }
}
