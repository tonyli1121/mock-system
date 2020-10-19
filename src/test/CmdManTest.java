/**
 * 
 */
package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Commands.CmdMan;
import fileSystems.FileSystem;

/**
 * @author litianx3
 *
 */
public class CmdManTest {

  FileSystem fs = new FileSystem();
  CmdMan myMan = new CmdMan();
  java.io.ByteArrayOutputStream out;

  String command;
  String actual;
  String actualErrorMessage;

  /**
   * set up, create string instance and outputCapture instance
   * 
   * @throws java.lang.Exception
   */
  @Before
  public void setUpBeforeClass() throws Exception {
    command = "";
    actual = "";
    actualErrorMessage = "";
    out = new ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
  }

  /**
   * closes ByteArrayOutputStream out
   * 
   * @throws java.lang.Exception
   */
  @After
  public void tearDownAfterClass() throws Exception {
    out.close();
  }

  @Test
  public void commandCattest() {
    command = "cat";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandCDtest() {
    command = "cd";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandEchotest() {
    command = "echo";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandGettest() {
    command = "get";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandSavetest() {
    command = "save";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandLstest() {
    command = "ls";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandMantest() {
    command = "man";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandMkdirtest() {
    command = "mkdir";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandPopdtest() {
    command = "popd";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandPushdtest() {
    command = "pushd";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandPwdtest() {
    command = "pwd";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandHistorytest() {
    command = "history";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandExittest() {
    command = "exit";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandMvtest() {
    command = "mv";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandCptest() {
    command = "cp";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandFindtest() {
    command = "find";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandTreetest() {
    command = "tree";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandLoadtest() {
    command = "load";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertFalse("This should not print error", actual.toLowerCase().startsWith("error"));
  }

  @Test
  public void commandErrortest1() {
    command = "Load";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertTrue(actual, actual.toLowerCase().contains("error"));
  }

  @Test
  public void commandErrortest2() {
    command = "no";
    myMan.execute(command, fs);
    actual = out.toString();
    assertFalse("This should print something, either error message or instruction.",
        actual.isEmpty());
    assertTrue("This should print an error", actual.toLowerCase().startsWith("error"));
  }
}
