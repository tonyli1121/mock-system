package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import Commands.CmdCd;
import Commands.CmdLs;
import Commands.CmdMkdir;
import Commands.HistoryTracker;
import fileSystems.FileSystem;

/**
 * HistoryTrackerTest tests functionalities of history tracker in file system
 * @author Xingnian Jin
 *
 */
public class HistoryTrackerTest {
  
  FileSystem fs = new FileSystem();
  CmdMkdir mkdir = new CmdMkdir();
  CmdLs ls = new CmdLs();
  CmdCd cd = new CmdCd();
  HistoryTracker myHis = new HistoryTracker();
  
  /**
   * Test clear()
   */
  @Test
  public void isClearTest() {
    myHis.push("testCommand");
    myHis.clear();
    assertEquals(1, myHis.getCurrentNum());
  }
  
  /** 
   * Test isFirst()
   */
  @Test
  public void isFirstTest() {
    myHis.push("firstCommand");
    assertTrue(myHis.isFirst());
  }
  
  /** 
   * Test isFirst()
   */
  @Test
  public void notFirstTest() {
    myHis.push("firstCommand");
    myHis.push("SecondCommand");
    assertFalse(myHis.isFirst());
  }
  
  /** 
   * Test push(String newCmd)
   */
  @Test
  public void pushTest() {
    myHis.push("firstCommand");
    assertEquals(2, myHis.getCurrentNum());
    assertEquals("1. firstCommand", myHis.getCmdStack().get(0));
  }
  
  /**
   * Test pushWithoutHeader(String newCmd)
   */
  @Test
  public void pushWithoutHeaderTest() {
    myHis.pushWithoutHeader("firstCommand");
    assertEquals(2, myHis.getCurrentNum());
    assertEquals("firstCommand", myHis.getCmdStack().get(0));
  }
  
  /**
   * Test output()
   */
  @Test
  public void outputTest() {
    myHis.push("firstCommand");
    myHis.push("secondCommand");
    assertEquals("1. firstCommand\n2. secondCommand", myHis.output());
  }
  
  /**
   * Test output(int last)
   */
  @Test
  public void outputSecondTest() {
    myHis.push("firstCommand");
    myHis.push("secondCommand");
    assertEquals("1. firstCommand\n2. secondCommand", myHis.output(2));
  }
  
}
