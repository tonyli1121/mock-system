package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.ArgumentFormatError;
import errorLoggerSystem.ArgumentTypeError;
import errorLoggerSystem.MissingArgument;
import fileSystems.Directory;
import fileSystems.FileSystem;
import fileSystems.Path;

/**
 * This is a class that executes 'find' command
 * @author Xingnian Jin
 *
 */
public class CmdFind implements ExecutableCmd{
  
  private ArrayList<String> success;
  private String printedValue = "";
  
  /**
   * Finds the directory with target name, -type 'd'
   * 
   * @param p given path
   * @param fs given fileSystem
   * @param name target directory name
   */
  private void findDirectory(Path p, FileSystem fs, String name) {
    Directory dir = null;
    Directory targetDir = null;
    if (fs.isValidWholePath(p)) {
      dir = fs.findWholeDirectory(p);
    } else if (fs.isValidRelativePath(p)) {
      dir = fs.findRelativeDirectory(p);
    }
    if (dir != null) {
      targetDir = dir.search(name);
    }
    if (targetDir != null) {
      String result = "Found directory with the name: [" + name + "] in path: " + p;
      System.out.println(result);
      printedValue += result;
      success.add(name);
    }
  }
  
  /**
   * Finds the file with target name, -type 'f'
   * 
   * @param p given path
   * @param fs given fileSystem
   * @param name target name
   */
  private void findFile(Path p, FileSystem fs, String name) {
    Directory dir = null;
    if (fs.isValidWholePath(p)) {
      dir = fs.findWholeDirectory(p);
    } else if (fs.isValidRelativePath(p)) {
      dir = fs.findRelativeDirectory(p);
    }
    if (dir != null) {
      ArrayList<String> fileList = dir.getSubFiles();
      for (String e:fileList) {
        if (e.equals(name)) {
          String result = "Found file with the name: [" + name + "] in path: " + p;
          System.out.println(result);
          printedValue += result;
          success.add(name);
        }
      }
    }
  }

  /**
   * Situation with only one possible path to find
   * 
   * @param arguments sliced input argument
   * @param fs current fileSystem
   */
  private void findWithOnePath(ArrayList<String> arguments, FileSystem fs) {
    Path p = new Path();
    p.readPath(arguments.get(0));
    String name = arguments.get(4).substring(1, arguments.get(4).length() - 1);
    if (!arguments.get(1).equals("-type") || !(arguments.get(2).equals("f") || 
        arguments.get(2).equals("d")) || !arguments.get(3).equals("-name") || 
        arguments.get(4).charAt(0) != '"' || 
        arguments.get(4).charAt(arguments.get(4).length() - 1) != '"') {
      ArgumentFormatError.printErrorMessage();
      printedValue += "argument format error msg";
      return;
    }
    if (fs.isValidRelativePath(p) || fs.isValidWholePath(p)) {
      if (arguments.get(2).equals("f")) {
        // find file
        findFile(p, fs, name);
      } else {
        // find directory
        findDirectory(p, fs, name);
      }
    } else {
      ArgumentTypeError.printErrorMessage();
      printedValue += "argument type error msg";
    }
  }
  
  /**
   * Helper function to get the position of the occurrence of "-type"
   * 
   * @param arguments sliced input arguments
   * @param fs current fileSystem
   * @param p_list a list of Paths
   * @return the position of "-type", -1 if no occurrence
   */
  private int getPosition(ArrayList<String> arguments, FileSystem fs, ArrayList<Path> p_list) {
    int pos = -1;
    for (int i = 0; i < arguments.size(); i++) {
      if (!arguments.get(i).equals("-type")) {
        Path p = new Path();
        p.readPath(arguments.get(i));
        if (fs.isValidRelativePath(p) || fs.isValidWholePath(p)) {
          p_list.add(p);
        } else {
          ArgumentTypeError.printErrorMessage();
          printedValue += "argument type error msg";
        }
      } else {
        pos = i;
        break;
      }
    }
    return pos;
  }
  /**
   * Situation with two or more possible path to find
   * 
   * @param arguments sliced input argument
   * @param fs current fileSystem
   */
  private void findWithMultiPath(ArrayList<String> arguments, FileSystem fs) {
    ArrayList<Path> p_list = new ArrayList<Path>();
    int pos = getPosition(arguments, fs, p_list);
    if (pos != -1 && (arguments.get(pos + 1).equals("f") || arguments.get(pos + 1).equals("d")) && 
        arguments.get(pos + 2).equals("-name") && arguments.get(pos + 3).charAt(0) == '"' && 
        arguments.get(pos + 3).charAt(arguments.get(pos + 3).length() - 1) == '"') {
      //execute here format correct
      String name = arguments.get(pos + 3).substring(1, arguments.get(pos + 3).length() - 1);
      if (arguments.get(pos + 1).equals("f")) {
        // find file
        for (Path p:p_list) {
          findFile(p, fs, name);
        }
      } else {
        // find directory
        for (Path p:p_list) {
          findDirectory(p, fs, name);
        }
      }
    } else {
      ArgumentFormatError.printErrorMessage();
      printedValue += "argument format error msg";
    }
  }
  
  /**
   * Getter for printedValue
   * 
   * @return printedValue
   */
  public String getPrintedValue() {
    return this.printedValue;
  }
  
  @Override
  /**
   * The execute method for find
   * 
   * @param param input parameter
   * @param fs current fileSystem
   * @return true if executed false otherwise
   */
  public boolean execute(String param, FileSystem fs) {
    success = new ArrayList<String>();
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    if (arguments.size() < 5) {
      MissingArgument.printErrorMessage();
      printedValue += "missing argument error msg";
    } else if (arguments.size() == 5) {
      // One path situation
      findWithOnePath(arguments, fs);
    } else {
      // multiple path situation
      findWithMultiPath(arguments, fs);
    }
    return !success.isEmpty();
  }

  /**
   * Prints out the command followed by parameters for the command.
   */
  @Override
  public void displayDone(String param) {
    System.out.println("find");
    if (!success.isEmpty()) {
      System.out.println(success.get(0));
    }
  }
}
