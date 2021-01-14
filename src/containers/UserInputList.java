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
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package containers;

import java.util.ArrayList;

/**
 * The UserInputList class stores all recent commands in a string array list.
 */
public class UserInputList implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  // recentCommands: The string array list that stores all recent commands
  private ArrayList<String> recentCommands = new ArrayList<String>();

  /**
   * This method sets recentCmds as the the recentCommands.
   * 
   * @param recentCmds - the array list of recent commands which method will set
   *        as the recentCommands
   * @return none
   */
  public void setRecentCommands(ArrayList<String> recentCmds) {
    this.recentCommands = recentCmds;
  }

  /**
   * This method returns the array list of all recent commands.
   * 
   * @param none
   * @return the string array list of all recent commands
   */
  public ArrayList<String> getRecentCommands() {
    return this.recentCommands;
  }

  /**
   * This method returns the size of the array list of all recent commands.
   * 
   * @param none
   * @return the size of the array list of all recent commands
   */
  public int getRecentCommandsSize() {
    return this.recentCommands.size();
  }

  /**
   * This method compares an object to another File object outside of the
   * function and checks if they are equivalent.
   * 
   * @param o - the object
   * @return true if the two objects are equal, false if they are not equal or
   *         the object o does not exist or it is not an instance of
   *         UserInputList
   */
  public boolean equals(Object o) {
    // return false if the object o does not exist or it is not the instance of
    // UserInputList
    if (o == null || (!(o instanceof UserInputList))) {
      return false;
    }
    // check if the two UserInputList objects have the same size and recent
    // commands
    UserInputList userInputList = (UserInputList) o;
    int maxCommandSize = Math.max(getRecentCommands().size(),
        userInputList.getRecentCommands().size() - 1);
    for (int commandIndex = 0; commandIndex < maxCommandSize; commandIndex++) {
      if (!userInputList.getRecentCommands().get(commandIndex)
          .equals(getRecentCommands().get(commandIndex))) {
        return false;
      }
    }
    return true;
  }


}
