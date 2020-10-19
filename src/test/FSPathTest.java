package test;

import static org.junit.Assert.*;
import org.junit.*;
import fileSystems.FileSystem;
import fileSystems.Path;

public class FSPathTest {
  private Path testPath;
  private FileSystem fs;
  
  /*
   * 1.create new path
   * 2.create new path with initial
   * 3.clone a path
   * 4.transform to string
   * 5.empty path to string
   * 6.test chop
   * 7.chop empty path
   * 8.test read a path 
   * 9.read empty path
   * 10.read path with multiple slashes
   * 11.test previous path
   * 12.test previous empty path
   * 13.test add path
   * 14.test combine two paths
   * 15.test make a full path from a full path
   * 16.make a full path from a relative path
   */
  
  @Before
  public void setup() {
    fs = new FileSystem();
    fs.createDirWithin("dir1");
    fs.navigateWithin("dir1");
  }

  @Test
  public void newPath() {
    testPath = new Path();
    assertTrue(testPath.getPath().isEmpty());
  }

  @Test
  public void newPathWithInitial() {
    testPath = new Path("dir1/dir2");
    assertEquals(testPath.previousPath(), "dir2");
  }

  @Test
  public void clonePath() {
    testPath = new Path("dir1/dir2");
    Path testPath2 = testPath.clone();
    assertEquals(testPath2.previousPath(), "dir2");
  }

  @Test
  public void pathToString() {
    testPath = new Path("dir1/dir2");
    assertEquals(testPath, "dir1/dir2/");
  }
  
  @Test
  public void emptyPathToString() {
    testPath = new Path();
    assertEquals(testPath, "");
  }

  @Test
  public void chopPath() {
    testPath = new Path("dir1/dir2");
    assertEquals(testPath.chop(), "dir1");
  }
  
  @Test
  public void chopEmptyPath() {
    testPath = new Path();
    assertEquals(testPath.chop(), null);
  }

  @Test
  public void readPath() {
    testPath = new Path();
    testPath.readPath("dir1/dir2");
    assertEquals(testPath, "dir1/dir2/");
  }
  
  @Test
  public void readEmptyPath() {
    testPath = new Path();
    testPath.readPath("");
    assertEquals(testPath, "");
  }
  
  @Test
  public void readManySlashPath() {
    testPath = new Path();
    testPath.readPath("///dir1/////dir2/////");
    assertEquals(testPath, "dir1/dir2/");
  }

  @Test
  public void previousPath() {
    testPath = new Path("dir1/dir2");
    assertEquals(testPath.previousPath(), "dir2");
  }
  
  @Test
  public void previousEmptyPath() {
    testPath = new Path();
    assertEquals(testPath.previousPath(), null);
  }

  @Test
  public void addPath() {
    testPath = new Path("dir1/dir2");
    testPath.nextPath("dir3");
    assertEquals(testPath, "dir1/dir2/dir3/");
  }

  @Test
  public void combinePath() {
    assertEquals(new Path("dir1").combine(new Path("dir2")), "dir1/dir2/");
  }

  @Test
  public void makeFullPathFromFull() {
    assertEquals(Path.makeFullPath(fs, "/dir2"), "dir2/");
  }
  
  @Test
  public void makeFullPathFromRelative() {
    assertEquals(Path.makeFullPath(fs, "dir2"), "dir1/dir2/");
  }
}
