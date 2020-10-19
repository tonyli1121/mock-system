package Commands;

import java.util.ArrayList;
import fileSystems.SavedFileSystem;

/**
 * The History instance is responsible for tracking the commands entered and output the list of
 * commands on request.
 * 
 * @author Xuezhi Yan
 * @author Xingnian Jin
 */
public class HistoryTracker {

  /**
   * Store all the commands whether it's valid or not.
   */
  private ArrayList<String> cmdStack;
  /**
   * Store the number of all the commands whether it's valid or not.
   */
  private int currentNum;

  /**
   * The constructor method, it initializes the stack and it's starting number.
   */
  public HistoryTracker() {
    this.cmdStack = new ArrayList<String>();
    this.currentNum = 1;
  }

  /**
   * clear the history tracker
   */
  public void clear() {
    this.cmdStack.clear();
    this.currentNum = 1;
  }

  /**
   * check if the newly enter command is the first one or not
   * 
   * @return true if yes
   */
  public boolean isFirst() {
    return this.currentNum == 2;
  }

  /**
   * Push a newly entered command into the stack.
   * 
   * @param newCmd the command to add
   */
  public void push(String newCmd) {
    this.cmdStack.add(this.currentNum + ". " + newCmd);
    this.currentNum++;
  }

  /**
   * Push a new command but without the numeric header
   */
  public void pushWithoutHeader(String newCmd) {
    this.cmdStack.add(newCmd);
    this.currentNum++;
  }

  /**
   * Print out the whole list of command history.
   * 
   * @return the output string
   */
  public String output() {
    String result = String.join("\n", this.cmdStack);
    System.out.println(result);
    return result;
  }

  /**
   * Print out the "last" recent command entered by the user.
   * 
   * @param last the number of most recent commands to show
   * @return the output string
   */
  public String output(int last) {
    String result;
    if (last > this.cmdStack.size()) {
      return this.output();
    } else {
      result = String.join("\n",
          this.cmdStack.subList(this.cmdStack.size() - last, this.cmdStack.size()));
      System.out.println(result);
      return result;
    }
  }

  /**
   * save the history into the target savedFileSystem
   * 
   * @param saveFs the target savedFileSystem
   */
  public void saveInto(SavedFileSystem saveFs) {
    for (String entry : this.cmdStack) {
      saveFs.addCmd(entry);
    }
  }
  
  /**
   * Getter method for currentNum
   * 
   * @return currentNum
   */
  public int getCurrentNum() {
    return this.currentNum;
  }
  
  /**
   * Getter method for cmdStack
   * 
   * @return cmdStack
   */
  public ArrayList<String> getCmdStack() {
    return this.cmdStack;
  }
}
