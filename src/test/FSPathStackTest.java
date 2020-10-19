package test;

import static org.junit.Assert.*;
import org.junit.*;
import fileSystems.Path;
import fileSystems.PathStack;

public class FSPathStackTest {
  /*
   * 1. Pop from empty stack
   * 2. Add them pop from empty stack
   * 3. Check if the stack is empty
   * the saveInto method is tested with the SaverLoader test
   */
  private PathStack testStack;
  
  @Before
  public void setup() {
    testStack = new PathStack();
  }

  @Test
  public void popEmptyStack() {
    assertEquals(testStack.pop(), null);
  }
  
  @Test
  public void addThenPopStack() {
    testStack.push(new Path("abc"));
    assertEquals(testStack.pop(), "abc/");
  }
  
  @Test
  public void isEmptyStack() {
    assertTrue(testStack.isEmpty());
  }
}
