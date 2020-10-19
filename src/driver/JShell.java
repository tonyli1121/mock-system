// **********************************************************
// Assignment2:
// Student1: Tianxiao Li
// UTORID user_name: litianx3
// UT Student #: 1004964005
// Author: Tianxiao Li
//
// Student2: Pengpeng Cao
// UTORID user_name: caopeng3
// UT Student #: 1005285549
// Author: Pengpeng Cao
//
// Student3: Xingnian Jin
// UTORID user_name: jinxingn
// UT Student #: 1004803787
// Author: Xingnian Jin
//
// Student4: Xuezhi Yan
// UTORID user_name: yanxuezh
// UT Student #: 1004785994
// Author: Xuezhi Yan
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;
import fileSystems.FileSystem;

/**
 * Display a prompt that allows user to type in commands and will perform the commands.
 * 
 * @author Tianxiao Li
 * @author Xuezhi Yan
 */
public class JShell {

  /**
   * The main execution method for JShell
   * 
   * Thank you for using JShell!
   * 
   * @param args
   */
  public static void main(String[] args) {
    // Create a new input object
    String input;
    Scanner in;
    FileSystem myFileSystem = new FileSystem();
    System.out.print(myFileSystem.getCurrentPath());
    System.out.print('#');
    in = new Scanner(System.in);
    input = in.nextLine();
    Input myInput = new Input();
    myInput.setFullInput(input);

    while (true) {
      if (Validity.checkValidityforShell(myInput)) {
        if (myInput.getCommand().contentEquals("exit"))
          break;
        else {
          try {
            Execute.performCommand(myInput, myFileSystem);
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
        }
      }
      System.out.print(myFileSystem.getCurrentPath());
      System.out.print('#');
      in = new Scanner(System.in);
      input = in.nextLine();
      myInput.setFullInput(input);
    }

    in.close(); // close scanner
  }

}
