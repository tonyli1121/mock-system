package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Commands.CmdCd;
import Commands.CmdGet;
import Commands.CmdMkdir;
import Commands.CmdMv;
import fileSystems.FileSystem;

public class CmdMvTest {
  
  CmdMv myMv = new CmdMv();
  CmdMkdir myMk = new CmdMkdir();
  CmdGet myGet = new CmdGet();
  CmdCd myCd = new CmdCd();
  FileSystem fs = new FileSystem();
  java.io.ByteArrayOutputStream out;
  String originalMessage;
  String movedMsg;
  String errorMsg;
  String originalLocation;
  String targetLocation;
  
  private boolean existInLocation(String name, String address, FileSystem fs) {
    myCd.execute(address, fs);
    ArrayList<String> temp = fs.getCurrentList();
    myCd.execute("/", fs);
    return temp.contains(name);
  }
  
  private String fileInfoAtGivenLocation(String address, FileSystem fs) {
    String res;
    myCd.execute(address, fs);
    res = fs.getFileInfo("iso_8859-1.txt");
    myCd.execute("/", fs);
    return res;
  }
  
  @Before
  public void setUp() throws Exception {
    originalLocation = "";
    targetLocation = "";
    myMk.execute("a/", fs);
    myMk.execute("b/", fs);
    myGet.execute("https://www.w3.org/TR/PNG/iso_8859-1.txt", fs);
    originalMessage = fs.getFileInfo("iso_8859-1.txt");
    movedMsg = "";
    errorMsg = "";
    out = new ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
  }

  @After
  public void tearDown() throws Exception {
    out.close();
  }

  @Test
  public void MoveToAtest() {
    originalLocation = "/iso_8859-1.txt";
    targetLocation = "/a/";
    myMv.execute(originalLocation + " " + targetLocation, fs);
    errorMsg = out.toString();
    movedMsg = fileInfoAtGivenLocation(targetLocation, fs);
    assertTrue("file shouldnt be at original location", !existInLocation("iso_8859-1.txt","/", fs));
    assertTrue("file should be at given location", existInLocation("iso_8859-1.txt", targetLocation, fs));
    assertTrue("no error", errorMsg.isEmpty());
    assertTrue(movedMsg.contentEquals(originalMessage));
  }
  
  @Test
  public void MoveToBThenToAtest() {
    originalLocation = "/iso_8859-1.txt";
    targetLocation = "/b/";
    myMv.execute(originalLocation + " " + targetLocation, fs);
    errorMsg = out.toString();
    movedMsg = fileInfoAtGivenLocation(targetLocation, fs);
    assertTrue("file shouldnt be at original location", !existInLocation("iso_8859-1.txt","/", fs));
    assertTrue("file should be at given location", existInLocation("iso_8859-1.txt", targetLocation, fs));
    assertTrue("no error", errorMsg.isEmpty());
    assertTrue(movedMsg.contentEquals(originalMessage));
    
    originalLocation = "/b/iso_8859-1.txt";
    targetLocation = "/a/";
    myMv.execute(originalLocation + " " + targetLocation, fs);
    errorMsg = out.toString();
    movedMsg = fileInfoAtGivenLocation(targetLocation, fs);
    assertTrue("file shouldnt be at original location", !existInLocation("iso_8859-1.txt", "/", fs));
    assertTrue("file should be at given location", existInLocation("iso_8859-1.txt", targetLocation, fs));
    assertTrue("no error", errorMsg.isEmpty());
    assertTrue(movedMsg.contentEquals(originalMessage));
  }
   
  @Test
  public void MoveToNonExisttest() {
    originalLocation = "/iso_8859-1.txt";
    targetLocation = "/f/";
    myMv.execute(originalLocation + " " + targetLocation, fs);
    errorMsg = out.toString();
    
    assertTrue("no error", !errorMsg.isEmpty());
  }
  
  @Test
  public void MoveFoldertest() {
    originalLocation = "/iso_8859-1.txt";
    targetLocation = "/a/";
    myMv.execute(originalLocation + " " + targetLocation, fs);
    
    originalLocation = "/a/";
    targetLocation = "/b/";
    myMv.execute(originalLocation + " " + targetLocation, fs);
    errorMsg = out.toString();
    movedMsg = fileInfoAtGivenLocation("/b/a/", fs);
    assertTrue(existInLocation("a", "/b/", fs));
    assertTrue("no error", errorMsg.isEmpty());
    assertTrue(movedMsg.contentEquals(originalMessage));
  }
  
}
