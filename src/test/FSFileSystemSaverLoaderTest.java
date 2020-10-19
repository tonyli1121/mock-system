package test;

import static org.junit.Assert.*;
import org.junit.*;
import Commands.HistoryTracker;
import fileSystems.FileSystem;
import fileSystems.FileSystemLoader;
import fileSystems.Path;
import fileSystems.SavedFileSystem;

public class FSFileSystemSaverLoaderTest {
  private FileSystem fs;
  private HistoryTracker his;
  /*
   * 1. check if current directory was loaded
   * 2. check if file was loaded
   * 3. check if path stack was loaded
   * 4. check if directory was loaded
   * 5. check if history was loaded
   * 
   * SavedDirectory, SavedFile, SavedFileSystem, and FileSystemLoader are all tested here
   */
  
  @Before
  public void setup() {
    fs = new FileSystem();
    his = new HistoryTracker();
    
    fs.createDirWhole(new Path(), "folder");
    fs.echoToFile("file", "abc", false);
    fs.pushDirectory("folder");
    
    his.push("cmd1");
    his.push("cmd2");
    
    SavedFileSystem saveFs = new SavedFileSystem();
    saveFs.loadFromSystem(fs, his);
    saveFs.saveToLocal("a");
    
    fs = new FileSystem();
    his = new HistoryTracker();
    
    FileSystemLoader.loadToFileSystem("a", fs, his);
  }
  
  @Test
  public void checkCurrentDir() {
    assertEquals(fs.getCurrentDirectory().getName(), "folder");
  }
  
  @Test
  public void checkDirectory() {
    fs.previousDirectory();
    assertTrue(fs.isValidRelativePath(new Path("folder")));
  }
  
  @Test
  public void checkFile() {
    fs.previousDirectory();
    assertEquals(fs.getFileInfo("file"), "abc");
  }
  
  @Test
  public void checkPathStack() {
    assertEquals(fs.getStack().pop(), new Path());
  }
  
  @Test
  public void checkHistory() {
    assertEquals(his.output(), "1. cmd1\n2. cmd2");
  }
}
