package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import errorLoggerSystem.ArgumentFormatError;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.FileNotFound;
import fileSystems.Directory;
import fileSystems.FileSystem;
import fileSystems.Path;
import fileSystems.SystemNavigator;

/**
 * The class responsible for the execution of command ls.
 * 
 * @author Xuezhi Yan
 * @author Xingnian Jin
 *
 */
public class CmdLs implements ExecutableCmd {

  /**
   * The successfully executed params.
   */
  private ArrayList<String> success;
  private String printedValue = "";

  /**
   * The private method for handling the directories.
   * 
   * @param path the path to look at
   * @param fs the current working file system
   */
  private void doDirectoryCase(String path, FileSystem fs) {
    Path temp = new Path();
    temp.readPath(path);
    if (path.charAt(0) == '/') {
      if (fs.isValidWholePath(temp)) {
        System.out.println(path + ":");
        System.out.println(String.join("\n", fs.getGlobalList(temp)));
        System.out.println("");
        printedValue += path + ":" + "\n" + String.join("\n", fs.getGlobalList(temp)) + "\n";
        success.add(path);
      } else {
        System.out.print("[" + path + "]");
        DirectoryNotFound.printErrorMessage();
        printedValue = "directory not found error msg";
      }
    } else {
      if (fs.isValidRelativePath(temp)) {
        System.out.println(path + ":");
        System.out.println(String.join("\n", fs.getRelativeList(temp)));
        System.out.println("");
        success.add(path);
        printedValue += path + ":" + String.join("\n", fs.getRelativeList(temp)) + "\n";
      } else {
        System.out.print("[" + path + "]");
        DirectoryNotFound.printErrorMessage();
        printedValue = "directory not found error msg";
      }
    }
  }

  /**
   * The private for handling local file cases.
   * 
   * @param name the name of file
   * @param path the path
   * @param fs the current working file system
   */
  private void doLocalFileCase(String name, String path, FileSystem fs) {
    if (fs.getCurrentList().contains(name)) {
      System.out.println(name);
      printedValue += name;
      success.add(name);
    } else {
      System.out.print("[" + path + "]");
      FileNotFound.printErrorMessage();
      printedValue = "file not found error msg";
    }
  }

  /**
   * The private method used to handle global path cases.
   * 
   * @param temp the global path
   * @param name the file name
   * @param path the string representation of path
   * @param fs the working file system
   */
  private void doGlobalPathCase(Path temp, String name, String path, FileSystem fs) {
    if (fs.isValidWholePath(temp)) {
      if (fs.findWholeDirectory(temp).contains(name)) {
        System.out.println(temp.toString() + name + "\n");
        success.add(path);
        printedValue += temp.toString() + name + "\n";
      } else {
        System.out.print("[" + path + "]");
        FileNotFound.printErrorMessage();
        printedValue = "file not found error msg";
      }
    } else {
      System.out.print("[" + path + "]");
      DirectoryNotFound.printErrorMessage();
      printedValue = "directory not found error msg";
    }
  }

  /**
   * The private method used to handle relative path cases.
   * 
   * @param temp the relative path
   * @param name the file name
   * @param path the string representation of path
   * @param fs the working file system
   */
  private void doRelativePathCase(Path temp, String name, String path, FileSystem fs) {
    if (fs.isValidRelativePath(temp)) {
      if (fs.findRelativeDirectory(temp).contains(name)) {
        System.out.println(temp.toString() + name + "\n");
        success.add(path);
        printedValue += temp.toString() + name + "\n";
      } else {
        System.out.print("[" + path + "]");
        FileNotFound.printErrorMessage();
        printedValue = "file not found error msg";
      }
    } else {
      System.out.print("[" + path + "]");
      DirectoryNotFound.printErrorMessage();
      printedValue = "directory not found error msg";
    }
  }
  
  /**
   * Operate printing directories without -R situation
   * @param arguments the separated argument of String
   * @param fs current fileSystem
   */
  private void operatingPath(ArrayList<String> arguments, FileSystem fs) {
    for (String path : arguments) {
      if (path.charAt(path.length() - 1) == '/') {
        this.doDirectoryCase(path, fs);
      } else {
        Path temp = new Path();
        temp.readPath(path);
        String name = temp.previousPath();
        name = name.substring(0, name.length() - 1);
        if (temp.isEmpty()) {
          this.doLocalFileCase(name, path, fs);
        } else if (path.charAt(0) == '/') {
          this.doGlobalPathCase(temp, name, path, fs);
        } else {
          this.doRelativePathCase(temp, name, path, fs);
        }
      }
    }
  }
  
  /**
   * Recursively prints out all sub-directories for the current directory
   * 
   * @param fs current fileSystem
   */
  private void recursiveCurrentDir(FileSystem fs) {
    Directory dir = fs.getCurrentDirectory();
    String result = SystemNavigator.getTreeOf(fs, dir, "");
    System.out.println(result);
    printedValue = result;
    success.add("");
  }
  
  /**
   * Recursively prints out all sub-directories for the specific given path
   * 
   * @param fs current fileSystem
   * @param arguments 
   */
  private void recursiveGivenPath(FileSystem fs, ArrayList<String> arguments) {
    ArrayList<String> temp = new ArrayList<String>();
    for (String str:arguments) {
      if (!str.equals("-R")) {
        temp.add(str);
      }
    }
    
    for (String str:temp) {
      Path p = new Path();
      p.readPath(str);
      Directory dir = null;
      if (fs.isValidWholePath(p)) {
        dir = fs.findWholeDirectory(p);
      } else if (fs.isValidRelativePath(p)) {
        dir = fs.findRelativeDirectory(p);
      } else {
        ArgumentFormatError.printErrorMessage();
        printedValue = "argument format error msg";
        return;
      }
      String result = SystemNavigator.getTreeOf(fs, dir, "");
      System.out.println(result);
      printedValue += result;
      success.add(str);
    }
  }
  
  /**
   * This is a getter for printedValue
   * 
   * @return printedValue variable
   */
  public String getPrintedValue() {
    return this.printedValue;
  }

  /**
   * The execute method for CmdLs.
   * 
   * @param param the parameters for the command
   * @param fs the current fileSystem
   * @return true if successfully executed, false otherwise
   */
  @Override
  public boolean execute(String param, FileSystem fs) {
    success = new ArrayList<String>();
    ArrayList<String> arguments = new ArrayList<String>();
    arguments.addAll(Arrays.asList(param.split(" ")));
    arguments.removeAll(Arrays.asList("", null));
    if (arguments.size() == 0) {
      System.out.println(String.join("\n", fs.getCurrentList()));
      printedValue = String.join("\n", fs.getCurrentList());
      return true;
    } 
    else if (arguments.size() == 1) {
      if (arguments.get(0).equals("-R")) {
        recursiveCurrentDir(fs);
      }
      else {
        operatingPath(arguments, fs);
      }
    }
    else {
      if (arguments.get(0).equals("-R")) {
        recursiveGivenPath(fs, arguments);
      }
      else {
        operatingPath(arguments, fs);
      }
      
    }
    return !success.isEmpty();
  }

  /**
   * Prints out the command followed by parameters for the command.
   */
  @Override
  public void displayDone(String param) {
    System.out.println("ls");
    if (!success.isEmpty()) {
      System.out.println(String.join(" ", success));
    }
  }

}
