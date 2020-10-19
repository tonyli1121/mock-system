package test;

import static org.junit.Assert.*;
import org.junit.*;
import Commands.HistoryTracker;
import fileSystems.FileSystem;
import fileSystems.FileSystemLoader;
import fileSystems.Path;
import fileSystems.SystemNavigator;

public class FSSystemNavigatorTest {
  private FileSystem fs;
  private HistoryTracker history;
  /*
   * 1. Tree of root
   * 2. Tree from a directory
   * 3. Test is a child of parent
   * 4. Test when two path are the same
   * 5. Test when it's not a child of parent
   * 6. Test moving a file
   * 7. Test moving a directory
   */
  @Before
  public void setup() {
    fs = new FileSystem();
    history = new HistoryTracker();
    FileSystemLoader.loadToFileSystem("test", fs, history);
  }

  @Test
  public void testTree() {
    assertEquals(SystemNavigator.getTreeOf(fs),
        "/\n" + " a\n" + "  b\n" + "   c\n" + "   file2\n" + "  c\n" + "   d\n" + " file1");
  }

  @Test
  public void testTreeSubFolder() {
    assertEquals(SystemNavigator.getTreeOf(fs, fs.findWholeDirectory(new Path("a")), ""),
        "a\n" + "b\n" + "c\n" + "file2\n" + "c\n" + "d");
  }

  @Test
  public void testParentToChild() {
    assertTrue(SystemNavigator.isParentOf(new Path("."), new Path("a"), fs));
  }

  @Test
  public void testSamePath() {
    assertTrue(!SystemNavigator.isParentOf(new Path("a"), new Path("a"), fs));
  }

  @Test
  public void testNotParent() {
    assertTrue(!SystemNavigator.isParentOf(new Path("a"), new Path("."), fs));
  }

  @Test
  public void moveFile() {
    SystemNavigator.moveItemTo(fs, new Path("file1"), new Path("a/"), true);
    assertTrue(fs.findWholeDirectory(new Path("a")).hasFile("file1"));
  }

  @Test
  public void moveDirectory() {
    SystemNavigator.moveItemTo(fs, new Path("a/b"), new Path("."), false);
    assertTrue(fs.findWholeDirectory(new Path(".")).contains("b"));
  }
}
