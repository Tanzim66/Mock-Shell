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

public class CurrentDirectoryTest {
  CurrentDirectory currDir1;
  CurrentDirectory currDir2;
  Directory dir1;
  Directory dir2;

  @Before
  public void setUp() throws Exception {
    dir1 = new Directory("/");
    dir2 = new Directory("/");
    currDir1 = new CurrentDirectory(dir1, "/");
    currDir2 = new CurrentDirectory(dir2, "/");
  }

  @Test
  public void testEquals() {
    dir1.setDirectoryName("Same");
    dir2.setDirectoryName("Same");
    currDir1.setCurrDir(dir1);
    currDir2.setCurrDir(dir2);
    currDir1.setCurrPath("/");
    currDir2.setCurrPath("/");
    assertEquals(currDir1, currDir2);

  }

  @Test
  public void testNotEqual() {
    dir1.setDirectoryName("Different");
    dir2.setDirectoryName("Very Different");
    currDir1.setCurrDir(dir1);
    currDir2.setCurrDir(dir2);
    currDir1.setCurrPath("/");
    currDir2.setCurrPath("/");
    assertNotEquals(currDir1, currDir2);

  }

}
