package test;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import Commands.CmdCd;
import Commands.CmdMkdir;
import Commands.CmdPopd;
import Commands.CmdPushd;
import fileSystems.FileSystem;

/**
 * Tests functionalities for CmdPushd and CmdPopd together
 * @author Xingnian Jin
 *
 */
public class CmdPushdCmdPopdTest {

  CmdPushd pushd = new CmdPushd();
  CmdPopd popd = new CmdPopd();
  CmdMkdir mkdir = new CmdMkdir();
  CmdCd cd = new CmdCd();
  FileSystem fs = new FileSystem();
  
  @Before
  public void setUp() {
    mkdir.execute("a", fs);
    mkdir.execute("b", fs);
    mkdir.execute("a/a1", fs);
  }
  
  @Test
  public void pushDirTest() {
    pushd.execute("a", fs);
    assertEquals("a", fs.getCurrentDirectory().toString());
  }
  
  @Test
  public void popDirTest() {
    pushd.execute("a", fs);
    popd.execute("", fs);
    assertEquals("", fs.getCurrentDirectory().toString());
  }
  
  @Test
  public void pushPathTest() {
    pushd.execute("a/a1", fs);
    assertEquals("a1", fs.getCurrentDirectory().toString());
  }
  
  @Test
  public void popMultipleTest() {
    pushd.execute("a", fs);
    pushd.execute("a1", fs);
    popd.execute("", fs);
    popd.execute("", fs);
    assertEquals("", fs.getCurrentDirectory().toString());
  }
  
  @Test
  public void popdExtraArgErrTest() {
    popd.execute("extraArg", fs);
    assertEquals("extra argument error msg", popd.getErrMsg());
  }
  
  @Test
  public void popdStackEmptyErrTest() {
    popd.execute("", fs);
    assertEquals("stack empty error msg", popd.getErrMsg());
  }
  
  @Test
  public void pushdMissingArgErrTest() {
    pushd.execute("", fs);
    assertEquals("missing argument error msg", pushd.getErrMsg());
  }
  
  @Test
  public void pushdExtraArgErrTest() {
    pushd.execute("arg1 arg2", fs);
    assertEquals("extra argument error msg", pushd.getErrMsg());
  }
  
  @Test
  public void pushdDirNotFoundErrTest() {
    pushd.execute("c", fs);
    assertEquals("directory not found error msg", pushd.getErrMsg());
  }
}
