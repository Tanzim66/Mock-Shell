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
import containers.CurrentDirectory;
import containers.Directory;
import containers.DirectoryStack;

public class DirectoryStackTest {

  DirectoryStack dirStack1;
  DirectoryStack dirStack2;
  Directory dir1;
  Directory dir2;
  CurrentDirectory currDir1;
  CurrentDirectory currDir2;



  @Before
  public void setUp() throws Exception {
    dirStack1 = new DirectoryStack();
    dirStack2 = new DirectoryStack();
  }

  @Test
  public void testEquals() {
    dir1 = new Directory("/");
    dir2 = new Directory("/");
    currDir1 = new CurrentDirectory(dir1, "/");
    currDir2 = new CurrentDirectory(dir2, "/");
    dirStack1.getStack().push(currDir1);
    dirStack2.getStack().push(currDir2);
    assertEquals(dirStack1, dirStack2);
  }

  @Test
  public void testNotEqual() {
    dir1 = new Directory("/");
    dir2 = new Directory("/diff");
    currDir1 = new CurrentDirectory(dir1, "/");
    currDir2 = new CurrentDirectory(dir2, "/diff");
    dirStack1.getStack().push(currDir1);
    dirStack2.getStack().push(currDir2);
    assertNotEquals(dirStack1, dirStack2);


  }

}
