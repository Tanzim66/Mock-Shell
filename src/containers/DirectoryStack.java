// **********************************************************
// Assignment2:
// Student1: Brandon Lo
// UTORID user_name: lobrand3
// UT Student #: 1006302831
// Author: Brandon Lo
//
// Student2: Ka Fai Yuen
// UTORID user_name: yuenka8
// UT Student #: 1006336225
// Author: Calvin Ka Fai Yuen
//
// Student3: Jahin Promit
// UTORID user_name: promitja
// UT Student #: 1006398747
// Author: Jahin Promit
//
// Student4: Tanzim Ahmed
// UTORID user_name: ahmedmd3
// UT Student #: 1005948389
// Author: Tanzim Ahmed
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read tshe plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package containers;

import java.util.Stack;

/**
 * The DirectoryStack class contains all current directories saved in the stack.
 */
public class DirectoryStack implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  // dirStack: The directory stack that saves all current directories
  private Stack<CurrentDirectory> dirStack = new Stack<CurrentDirectory>();

  /**
   * This method returns the directory stack.
   * 
   * @param none
   * @return the directory stack
   */
  public Stack<CurrentDirectory> getStack() {
    return this.dirStack;
  }

  /**
   * This method compares an object to another DirectoryStack object outside of
   * the function and checks if they are equivalent.
   * 
   * @param o - the object
   * @return true if the two objects are equal, false if they are not equal or
   *         the object o does not exist or it is not an instance of
   *         DirectoryStack
   */
  public boolean equals(Object o) {
    // return false if the object o does not exist or it is not the instance of
    // DirectoryStack
    if (o == null || (!(o instanceof DirectoryStack))) {
      return false;
    }
    // check if the two DirectoryStack objects have the same stack and size
    DirectoryStack dirStack = (DirectoryStack) o;
    Stack<?> oldDirStack = (Stack<?>) getStack().clone();
    Stack<?> newDirStack = (Stack<?>) dirStack.getStack().clone();
    int maxStack = Math.max(oldDirStack.size(), newDirStack.size());
    for (int stackIndex = 0; stackIndex < maxStack; stackIndex++) {
      if (!oldDirStack.pop().equals(newDirStack.pop())) {
        return false;
      }
    }
    return true;
  }

}
