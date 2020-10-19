package Commands;

import fileSystems.FileSystem;

/**
 * This class is responsible for the execution of save.
 * 
 * @author Tianxiao Li
 *
 */
public class CmdTree implements ExecutableCmd {

  /**
   * The execute method for CmdTree.
   * 
   * @param param the parameters for the command
   * @param fs the current fileSystem
   * @return true if successfully executed, false otherwise
   */
  @Override
  public boolean execute(String param, FileSystem fs) {
    // TODO Auto-generated method stub
    if (!param.isEmpty()) {
      errorLoggerSystem.ExtraArgument.printErrorMessage();
      return false;
    }

    String myTree = fileSystems.SystemNavigator.getTreeOf(fs);
    System.out.println(myTree);
    return true;
  }

  /**
   * Prints out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  @Override
  public void displayDone(String param) {
    // TODO Auto-generated method stub
    System.out.println("tree");
    return;
  }

}
