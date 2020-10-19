/**
 * 
 */
package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Commands.CmdCd;
import Commands.CmdMkdir;
import Commands.CmdPwd;
import fileSystems.FileSystem;

/**
 * Unit test for CmdPwd, does not contains situation for extra argument,
 * as the checks are done in driver
 * 
 * @author Tianxiao Li
 *
 */
public class CmdPwdTest {

  FileSystem fs = new FileSystem();
  CmdPwd myPwd = new CmdPwd();
  CmdMkdir myMkdir = new CmdMkdir();
  CmdCd myCd = new CmdCd();
  String outmsg;
  java.io.ByteArrayOutputStream out;
  
  /**
   * set up before execution
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    myMkdir.execute("/a", fs);
    out = new ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
  }

  /**
   * closes the output stream
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    out.close();
  }

  @Test
  public void test1() {
    myPwd.execute("", fs);
    outmsg = out.toString();
    assertTrue(outmsg, outmsg.trim().contentEquals("/"));
  }
  
  @Test
  public void test2() {
    myCd.execute("a", fs);
    myPwd.execute("", fs);
    outmsg = out.toString();
    assertTrue(outmsg, outmsg.trim().contentEquals("/a/"));
  }
  
}
