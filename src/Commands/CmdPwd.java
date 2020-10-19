package Commands;

import fileSystems.FileSystem;

/**
 * This class is responsible for the execution of pwd.
 * 
 * @author Tianxiao Li
 */
public class CmdPwd implements ExecutableCmd {

  /**
   * This method performs the pwd command.
   * 
   * @param fs the current fileSystem
   */
  private void performPwd(FileSystem fs) {
    System.out.println(fs.getCurrentPath());
  }
  
  /**
   * Execute method for CmdPwd.
   * 
   * @param fs the current fileSystem
   */
  @Override
  public boolean execute(String param, FileSystem fs) {
    performPwd(fs);
    return true;
  }

  /**
   * Prints out the command followed by parameters for the command.
   */
  @Override
  public void displayDone(String param) {
    System.out.println("pwd");
    return;
  }

}
