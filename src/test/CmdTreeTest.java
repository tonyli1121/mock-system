package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Commands.CmdMkdir;
import Commands.CmdTree;
import fileSystems.FileSystem;

public class CmdTreeTest {

  FileSystem fs = new FileSystem();
  CmdTree myTree = new CmdTree();
  CmdMkdir mk = new CmdMkdir();
  java.io.ByteArrayOutputStream out;

  String argument;
  String output;

  /**
   * build a file system before execution
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    argument = "";
    output = "";
    out = new ByteArrayOutputStream();
    mk.execute("a b", fs);
    mk.execute("/a/c/ /a/d/ /b/e/ /b/f/ /a/c/g/ /a/c/g/i/ /b/f/h/", fs);
    System.setOut(new java.io.PrintStream(out));
  }

  /**
   * closes the output stream
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    out.close();
  }

  @Test
  public void treeTest1() {
    argument = "aslkdfjsaldfkj";
    myTree.execute(argument, fs);
    output = out.toString();
    assertTrue("this should print an error", output.toLowerCase().startsWith("error"));
    assertFalse("this should not print a tree", output.contains("/"));
  }

  @Test
  public void treeTest2() {
    argument = "";
    myTree.execute(argument, fs);
    output = out.toString();
    assertFalse("this should not print an error", output.toLowerCase().startsWith("error"));
    assertTrue("this should print a tree", output.contains("/"));

  }

}
