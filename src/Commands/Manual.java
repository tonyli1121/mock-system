package Commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The Manual provides method to execute CmdMan.
 * 
 * @author Pengpeng Cao
 *
 */
public class Manual {

  /**
   * Contain all the valid commands.
   */
  private static String[] cmds =
      {"cat", "exit", "cd", "echo", "ls", "man", "mkdir", "popd", "pushd", "pwd", "history", "man",
          "mv", "cp", "get", "save", "load", "find", "tree"};

  /**
   * Print the documentation of exit.
   */
  public static void printExit() {
    System.out.println("Quit the Program.");
  }

  /**
   * Print the documentation of mkdir.
   */
  public static void printMkdir() {
    System.out.println("mkdir DIR");
    System.out.println("Create a directory named DIR, each of which may"
        + " be relative to the current directory or may be a full " + "path.");
  }

  /**
   * Print the documentation of cd.
   */
  public static void printCd() {
    System.out.println("cd DIR");
    System.out.println("Change directory to DIR, which may be relative"
        + " to the current directory or may be a full path.");
  }

  /**
   * Print the documentation of ls.
   */
  public static void printLs() {
    System.out.println("ls [-R][PATH ...]");
    System.out.println("If -R is present, recursively list all subdirectories.");
    System.out.println("'name/' would indicate a path, and name would indicate a file.");
    System.out.println("If no paths are given, print the contents of "
        + "the current directory, with a new line following each of" + " the content.");
    System.out.println("Otherwise, for each path p, the order listed:");
    System.out.println("- If p specifies a file, print p.");
    System.out.println("- If p specifies a directory, print p, a colon, "
        + "then the contents of that directory, then an extra new " + "line");
    System.out.println("- If p does not exist, print 'Error: Given file" + " name not found'.");
  }

  /**
   * Print the documentation of pwd.
   */
  public static void printPwd() {
    System.out.println("Print the current working directory which " + "includes the whole path.");
  }

  /**
   * Print the documentation of pushd.
   */
  public static void printPushd() {
    System.out.println("pushd DIR");
    System.out.println("Saves the current working directory by pushing "
        + "onto directory stack and then changes the new current " + "working directory to DIR.");
  }

  /**
   * Print the documentation of popd.
   */
  public static void printPopd() {
    System.out.println("Remove the top entry from the directory stack, " + "and cd into it");
  }

  /**
   * Print the documentation of history.
   */
  public static void printHistory() {
    System.out.println("history [number]");
    System.out.println("Print out recent commands, one command per " + "line.\n"
        + "If no number is given, print five commands. "
        + "Otherwise, print the number given of commands.");
  }

  /**
   * Print the documentation of cat.
   */
  public static void printCat() {
    System.out.println("cat FILE1 [FILE2 ...]");
    System.out.println("Display the contents of FILE1 and other files, "
        + "such as FILE2, concatenated in the shell.");
  }

  /**
   * Print the documentation of echo.
   */
  public static void printEcho() {
    System.out.println("echo STRING [> OUTFILE]");
    System.out.println("If OUTFILE is not provided, print STRING on the"
        + " shell. Otherwise, put STRING into file OUTFILE. ");
    System.out
        .println("STRING is a string of characters surrounded by" + " double quotation marks.");
    System.out.println("This creates a new file if OUTFILE does not "
        + "exists and erases the old contents if OUTFILE already " + "exists.\n");
    System.out.println("echo STRING >> OUTFILE");
    System.out.println("If OUTFILE is not provided, print STRING on the"
        + " shell. Otherwise, put STRING into file OUTFILE.");
    System.out
        .println("STRING is a string of characters surrounded by" + " double quotation marks.");
    System.out.println("This creates a new file if OUTFILE does not "
        + "exists and appends STRING after the old contents if " + "OUTFILE already exists.");
  }
  
  /**
   * Print the documentation of man.
   */
  public static void printMan() {
    System.out.println("man CMD");
    System.out.println("Print documentation for CMD.");
  }
  
  /**
   * Print the documentation of mv.
   */
  public static void printMv() {
    System.out.println("mv OLDPATH NEWPATH");
    System.out.println("Move item OLDPATH to NEWPATH.");
  }
  
  /**
   * Print the documentation of cp.
   */
  public static void printCp() {
    System.out.println("cp OLDPATH NEWPATH");
    System.out.println("Copy item OLDPATH to NEWPATH.");
  }
  
  /**
   * Print the documentation of get.
   */
  public static void printGet() {
    System.out.println("get URL");
    System.out.println("Retrieve the file at that URL and add it to the current working "
        + "directory. URL is a web address.");
  }
  
  /**
   * Print the documentation of save.
   */
  public static void printSave() {
    System.out.println("save FILENAME");
    System.out.println("Save the contents of FILENAME on the computer.");
  }
  
  /**
   * Print the documentation of load.
   */
  public static void printLoad() {
    System.out.println("load FILENAME");
    System.out.println("Load the contents of FILENAME and reinitialize everything that was "
        + "saved previously into the FILENAME.");
  }
  
  /**
   * Print the documentation of find.
   */
  public static void printFind() {
    System.out.println("find path ... -type [f|d] -name expression");
    System.out.println("Search path and find all directories that excatly named name expression "
        + "indicated by provided type. Type is not a mandatory input.");
  }
  
  /**
   * Print the documentation of tree.
   */
  public static void printTree() {
    System.out.println("tree");
    System.out.println("Display the entire file system as a tree from the root directory.");
  }

  /**
   * Check if parameter is a valid command.
   * 
   * @param cmd the string to check
   * @return true if it's valid, otherwise false
   */
  private static boolean hasCmd(String cmd) {
    for (String each : cmds) {
      if (each.equals(cmd)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Receive a command and invoke the corresponding method to print the manual of that command.
   * 
   * @param commandIn the input command
   * @return true if the command exists, otherwise false
   */
  public static boolean performMan(String commandIn) {
    String temp = "print";
    if (!hasCmd(commandIn)) {
      errorLoggerSystem.ArgumentTypeError.printErrorMessage();
      return false;
    }
    temp = temp + commandIn.substring(0, 1).toUpperCase();
    temp = temp + commandIn.substring(1);
    try {
      Method method = Manual.class.getMethod(temp);
      method.invoke(new Manual());
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return true;
  }
  
}
