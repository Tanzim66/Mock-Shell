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

package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import containers.UserInputList;

public class UserInputListTest {

  UserInputList userList1;
  UserInputList userList2;


  @Before
  public void setUp() throws Exception {
    userList1 = new UserInputList();
    userList2 = new UserInputList();
  }

  @Test
  public void testEquals() {
    userList1.getRecentCommands().add("This");
    userList1.getRecentCommands().add("is");
    userList1.getRecentCommands().add("same");
    userList1.getRecentCommands().add("list");

    userList2.getRecentCommands().add("This");
    userList2.getRecentCommands().add("is");
    userList2.getRecentCommands().add("same");
    userList2.getRecentCommands().add("list");

    assertEquals(userList1, userList2);
  }

  @Test
  public void testNotEqual() {
    userList1.getRecentCommands().add("This");
    userList1.getRecentCommands().add("isn't");
    userList1.getRecentCommands().add("the");
    userList1.getRecentCommands().add("same");

    userList2.getRecentCommands().add("This");
    userList2.getRecentCommands().add("is");
    userList2.getRecentCommands().add("not");
    userList2.getRecentCommands().add("same");

    assertNotEquals(userList1, userList2);

  }

}
