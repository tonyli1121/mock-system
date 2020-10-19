package test;

import static org.junit.Assert.assertTrue;
import org.junit.*;
import errorLoggerSystem.ArgumentFormatError;
import errorLoggerSystem.ArgumentTypeError;
import errorLoggerSystem.CurrentRootDirectory;
import errorLoggerSystem.DirectoryAlreadyExist;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.ExtraArgument;
import errorLoggerSystem.FileNotFound;
import errorLoggerSystem.FirstCommandError;
import errorLoggerSystem.InputStreamInterruptedError;
import errorLoggerSystem.InvalidCommand;
import errorLoggerSystem.MissingArgument;
import errorLoggerSystem.ParentDirectoryError;
import errorLoggerSystem.SpecialChar;
import errorLoggerSystem.StackEmpty;

/**
 * ErrorLoggerTest tests all functionalities of errorLogger system
 * @author Xingnian Jin
 *
 */
public class ErrorLoggerTest {
  
  /**
   * Test argument format error
   */
  @Test
  public void argumentFormatErrTest() {
    assertTrue(ArgumentFormatError.printErrorMessage());
  }
  
  /**
   * Test argument type error
   */
  @Test
  public void argumentTypeErrTest() {
    assertTrue(ArgumentTypeError.printErrorMessage());
  }
  
  /**
   * Test current root directory error
   */
  @Test
  public void currentRootDirErrTest() {
    assertTrue(CurrentRootDirectory.printErrorMessage());
  }
  
  /**
   * Test directory already exist error
   */
  @Test
  public void dirAlreadyExistErrTest() {
    assertTrue(DirectoryAlreadyExist.printErrorMessage());
  }
  
  /**
   * Test directory not found error
   */
  @Test
  public void dirNotFoundErrTest() {
    assertTrue(DirectoryNotFound.printErrorMessage());
  }
  
  /**
   * Test extra argument error
   */
  @Test
  public void extraArgErrTest() {
    assertTrue(ExtraArgument.printErrorMessage());
  }
  
  /**
   * Test file not found error
   */
  @Test
  public void fileNotFoundErrTest() {
    assertTrue(FileNotFound.printErrorMessage());
  }
  
  /**
   * Test first command error
   */
  @Test
  public void firstCmdErrTest() {
    assertTrue(FirstCommandError.printErrorMessage());
  }
  
  /**
   * Test input stream interrupted error
   */
  @Test
  public void inputStreamErrTest() {
    assertTrue(InputStreamInterruptedError.printErrorMessage());
  }
  
  /**
   * Test invalid command error
   */
  @Test
  public void invalidCmdErrTest() {
    assertTrue(InvalidCommand.printErrorMessage());
  }
  
  /**
   * Test missing argument error
   */
  @Test
  public void missingArgErrTest() {
    assertTrue(MissingArgument.printErrorMessage());
  }
  
  /**
   * Test parent directory error
   */
  @Test
  public void parentDirErrTest() {
    assertTrue(ParentDirectoryError.printErrorMessage());
  }
  
  /**
   * Test special char error
   */
  @Test
  public void specialCharErrTest() {
    assertTrue(SpecialChar.printErrorMessage());
  }
  
  /**
   * Test stack empty error
   */
  @Test
  public void stackEmptyErrTest() {
    assertTrue(StackEmpty.printErrorMessage());
  }
}
