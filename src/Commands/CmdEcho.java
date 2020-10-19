package Commands;

import java.util.ArrayList;
import errorLoggerSystem.ArgumentFormatError;
import errorLoggerSystem.DirectoryNotFound;
import errorLoggerSystem.ExtraArgument;
import errorLoggerSystem.MissingArgument;
import fileSystems.FileSystem;
import fileSystems.Path;

/**
 * This class executes command 'echo'
 * @author Xingnian Jin
 *
 */
public class CmdEcho implements ExecutableCmd{
  
  private String printedValue = "";
  
  /**
   * This is a helper function.
   * Returns an ArrayList of String that removes the empty String elements in the given input.
   * 
   * @param strList this is a input String list from the user input variation
   * @return an ArrayList of String containing strList with empty String elements removed
   */
  private ArrayList<String> rmExtraEmptyString(String[] strList) {
    ArrayList<String> result = new ArrayList<String>();
    for (String element:strList) {
      if (element.length() != 0) {
        result.add(element);
      }
    }
    return result;
  }
  
  /**
   * This is a helper function.
   * Returns a String that has its space characters removed from the given input String.
   * 
   * @param input a String from user input
   * @return the given input String with space characters removed
   */
  private String rmExtraSpace(String input) {
    String result = "";
    for(int i = 0; i < input.length(); i++) {
      if (input.charAt(i) != ' ') {
        result = result + input.charAt(i);
      }
    }
    return result;
  }
  
  /**
   * This is a helper function.
   * Returns true if the given input param is a path and vice versa.
   * 
   * @param param a String from user input
   * @return true if the given input is a path and vice versa
   */
  private boolean isPath(String param) {
    if (param.split("/").length == 1) {
      return false;
    }
    return true;
  }
  
  /**
   * This is a helper method used to generate result string
   * 
   * @param x the input string from echo
   * @return a fixed new line string result
   */
  private String fixNewLine(String x) {
    int startingPos = 0;
    int endingPos = x.length();
    String result = "";
    for (int i = 0; i < x.length() - 1; i++) {
      if (x.charAt(i) == '\\' && x.charAt(i+1) == 'n') {
        endingPos = i;
        result = result + x.substring(startingPos, endingPos) + "\n";
        startingPos = i+2;
      }
    }
    result = result + x.substring(startingPos);
    return result;
  }

  /**
   * This is a helper method to judge errors for echo
   * 
   * @param param input parameter
   * @param paramList sliced input parameter
   * @param paramArr paramList with extra white space removed
   * @return false if error occurred, true if not
   */
  private boolean judgeErrors(String param, String[] paramList, ArrayList<String> paramArr) {
    // Missing necessary argument case
    if (param.length() == 0) {
      MissingArgument.printErrorMessage();
      printedValue = "missing argument error msg";
      return false;
    }
    // No "" between input case
    if (paramList.length == 1) {
      ArgumentFormatError.printErrorMessage();
      printedValue = "argument format error msg";
      return false;
    }
    // Extra argument case
    if (paramArr.size() > 2) {
      ExtraArgument.printErrorMessage();
      printedValue = "extra argument error msg";
      return false;
    }
    return true;
  }
  
  /**
   * Case that append string to the existing file
   * 
   * @param paramArr sliced input parameter with extra white space removed
   * @param pathExcludeFileName path exclude file name
   * @param fs current file system
   * @param temp temporary string list
   * @param result resulting string
   * @return true if successfully executed, false otherwise
   */
  private boolean appendCase(ArrayList<String> paramArr, String pathExcludeFileName, FileSystem fs,
      String[] temp, String result) {
    // full path case
    if (isPath(paramArr.get(1))) {
      Path myPath = new Path();
      myPath.readPath(pathExcludeFileName.substring(2));
      // not valid path case
      if (!(fs.isValidWholePath(myPath) || fs.isValidRelativePath(myPath))) {
        DirectoryNotFound.printErrorMessage();
        printedValue = "directory not found error msg";
        return false;
      }
      fs.echoToFileWhole(myPath, temp[temp.length - 1], result, true);
      return true;
    }
    // only FILENAME case
    else {
      fs.echoToFile(paramArr.get(1).substring(2), result, true);
      return true;
    }
  }
  
  /**
   * Case that overwrites string to the existing file
   * 
   * @param paramArr sliced input parameter with extra white space removed
   * @param pathExcludeFileName path exclude file name
   * @param fs current file system
   * @param temp temporary string list
   * @param result resulting string
   * @return true if successfully executed, false otherwise
   */
  private boolean overwriteCase(ArrayList<String> paramArr, String pathExcludeFileName, 
      FileSystem fs, String[] temp, String result) {
    // full path case
    if (isPath(paramArr.get(1))) {
      Path myPath = new Path();
      myPath.readPath(pathExcludeFileName.substring(1));
      // Not valid path case
      if (!(fs.isValidWholePath(myPath) || fs.isValidRelativePath(myPath))) {
        DirectoryNotFound.printErrorMessage();
        printedValue = "directory not found error msg";
        return false;
      }
      fs.echoToFileWhole(myPath, temp[temp.length - 1], result, false);
      return true;
    }
    // only FILENAME case
    else {
      fs.echoToFile(paramArr.get(1).substring(1), result, false);
      return true;
    }
  }
  
  @Override
  /**
   * Returns true if the echo command is successfully executed whether its:
   * - printing the String given if it is the only parameter
   * - Create a OUTFILE and append the String in it or overwrite the content with the String
   * Returns false and prints out error message if the following:
   * - Empty parameter, since echo command takes at least one parameter
   * - More than 2 parameters, since echo command takes at most two parameters
   * - Not a valid path
   * 
   * @param param the whole parameter from user input
   * @param fs the current FileSystem
   * @return a boolean value true or false indicating if the operation is executed successfully
   */
  public boolean execute(String param, FileSystem fs) {
    String[] paramList = param.split("\"");
    ArrayList<String> tempParamArr = rmExtraEmptyString(paramList);
    ArrayList<String> paramArr = new ArrayList<String>();
    for(String element:tempParamArr) {
      paramArr.add(rmExtraSpace(element));
    }
    if (judgeErrors(param, paramList, paramArr) == false) {
      return false;
    }
    String result = fixNewLine(paramList[1]);
    if (paramArr.size() == 1) {
      System.out.println(result);
      printedValue = result;
      return true;
    }
    String[] temp = paramArr.get(1).split("/");
    String pathExcludeFileName = "";
    for (int i = 0; i <temp.length - 1; i++) {
      pathExcludeFileName = pathExcludeFileName + temp[i] + "/";
    }
    if(paramArr.get(1).substring(0,2).equals(">>")) {  
      return appendCase(paramArr, pathExcludeFileName, fs, temp, result);
    } else if (paramArr.get(1).charAt(0) == '>'){
      return overwriteCase(paramArr, pathExcludeFileName, fs, temp, result);
    } else {
      ArgumentFormatError.printErrorMessage();
      return false;
    }
  }
  
  /**
   * Getter method for printedValue
   * 
   * @return printedValue
   */
  public String getPrintedValue() {
    return this.printedValue;
  }
  
  @Override
  /**
   * Prints out the command followed by parameters for the command.
   * 
   * @param param the whole parameter String after the command
   */
  public void displayDone(String param) {
    System.out.println("echo");
    System.out.println(param);
    
  }

}
