package Commands;

import fileSystems.FileSystem;
/**
 * This is a interface that contains basic command methods
 * 
 * 
 * @author Xuezhi Yan
 * @author Tianxiao Li
 *
 */
public interface ExecutableCmd {
  /**
   * Execute the command.
   * 
   * @param params the parameters for this command
   * @param fs the target file system
   * @return true if successfully executed, otherwise false
   */
  public boolean execute(String param, FileSystem fs);
  
  /**
   * Display message if command runs successfully.
   * 
   * @param param the parameters for this command
   */
  public void displayDone(String param);
}
