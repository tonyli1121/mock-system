package test;

import errorLoggerSystem.ArgumentFormatError;
import errorLoggerSystem.ArgumentTypeError;
import errorLoggerSystem.CurrentRootDirectory;
import errorLoggerSystem.DirectoryAlreadyExist;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.ExtraArgument;
import errorLoggerSystem.FileNotFound;
import errorLoggerSystem.InvalidCommand;
import errorLoggerSystem.MissingArgument;
import errorLoggerSystem.SpecialChar;
import errorLoggerSystem.StackEmpty;

/**
 * Test all cases in errorLoggerSystem.
 * 
 * @author Xingnian Jin
 * 
 */
public class TestErrorLogger extends TestFile {
  
  public static void casePrinter() {
    System.out.println("[CASE]");
  }

  
  private static void testErrorLoggerSystem() {
    action("Print argument format error message");
    ArgumentFormatError.printErrorMessage();
    separator();
    
    action("Print argument type error message");
    ArgumentTypeError.printErrorMessage();
    separator();
    
    action("Print current at root directory error message");
    CurrentRootDirectory.printErrorMessage();
    separator();
    
    action("Print directory already exist error message");
    DirectoryAlreadyExist.printErrorMessage();
    separator();
    
    action("Print directory not found error message");
    DirectoryNotFound.printErrorMessage();
    separator();
    
    action("Print extra argument error message");
    ExtraArgument.printErrorMessage();
    separator();
    
    action("Print file not found error message");
    FileNotFound.printErrorMessage();
    separator();
    
    action("Print invalid command error meesage");
    InvalidCommand.printErrorMessage();
    separator();
    
    action("Print missing necessary argument error mesasage");
    MissingArgument.printErrorMessage();
    separator();
    
    action("Print special character appeared error message");
    SpecialChar.printErrorMessage();
    separator();
    
    action("Print stack empty error message");
    StackEmpty.printErrorMessage();
    separator();
  }
  public static void main(String[] args) {
    
    log("TEST ERRORLOGGER SYSTEM BEGINS...");
    testErrorLoggerSystem();
    log("TEST ERRORLOGGER SYSTEM FINISHED...");
  }
}
