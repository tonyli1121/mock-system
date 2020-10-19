package driver;

import Commands.*;

/**
 * This instance type is responsible for taking input and seperate them into command and arguments,
 * and pushes input to history tracker stack.
 * 
 * @author Tianxiao Li
 *
 */
public class Input {
  
  /**
   * The complete input from the command shell.
   */
  private String input;
  /**
   * The command that is to be executed.
   */
  private String command;
  /**
   * The arguments following the command.
   */
  private String arguments;
  /**
   * The history tracker that is used to track command history.
   */
  protected static HistoryTracker history;

  /**
   * The constructor method for the class, initialize the instance.
   */
  public Input() {
    input = ""; // case matters
    history = new HistoryTracker();
    command = "";
    arguments = "";
  }

  /** 
   * Set variables, separate input into two parts, push the input to history stack.
   * 
   * @param strIn Input from shell
   */
  public void setFullInput(String strIn) {
    input = strIn;
    history.push(strIn);
    command = "";
    arguments = "";
    seperateInput();
  }
  /**
   * This method separates the input into two parts, one is command and the other is called
   * secondPart (DIR).
   */
  private void seperateInput() {
    boolean workingOnCommand = true;

    for (int i = 0; i < this.input.length(); i++) {
      if (workingOnCommand && this.input.charAt(i) == ' ') {
        // working on command but met a space
        if (!this.command.isEmpty()) {
          // if something already added into command, but met a space, start on arguments
          workingOnCommand = false;
          continue;
        }
      }
      if (workingOnCommand) {
        if (input.charAt(i) == ' ') 
          continue;
        this.command = this.command + this.input.charAt(i);
      } else {
        this.arguments = this.arguments + input.charAt(i);
      }
    }
    // make sure not empty, then trim command and arguments to discard whitespaces
    if (this.command != "")
      this.command = this.command.trim();
    if (this.arguments != "")
      this.arguments = this.arguments.trim();
  }

  /**
   * This method returns the command part of input.
   * 
   * @return a string that represents the command part of input
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * This method returns the arguments part of input.
   * 
   * @return a string that represents the arguments part of input
   */
  public String getArguments() {
    return this.arguments;
  }

}
