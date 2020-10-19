package fileSystems;

import java.util.ArrayList;

/**
 * The PathStack it a stack of Path that follows LIFO
 * 
 * @author Xuezhi Yan
 */
public class PathStack {
  
  /**
   * A list of stacks.
   */
  private ArrayList<Path> pathStack;

  /**
   * The constructor takes no input parameters, it initializes an empty stack.
   */
  public PathStack() {
    this.pathStack = new ArrayList<Path>();
  }

  /**
   * Check if the stack is empty.
   * 
   * @return true if empty, otherwise false
   */
  public boolean isEmpty() {
    return this.pathStack.isEmpty();
  }

  /**
   * Push a new path to the end of the stack.
   * 
   * @param path the path to add
   */
  public void push(Path path) {
    this.pathStack.add(path);
  }

  /**
   * Pop the last element from the stack.
   * 
   * @return the element from the stack
   */
  public Path pop() {
    if (this.isEmpty()) {
      return null;
    }
    return this.pathStack.remove(this.pathStack.size() - 1);
  }
  
  /**
   * Saves the stack into the target SavedFileSystem.
   * 
   * @param saveFs the target to save
   */
  public void saveInto(SavedFileSystem saveFs) {
    for(Path path:this.pathStack) {
      saveFs.addPath(path);
    }
  }
}
