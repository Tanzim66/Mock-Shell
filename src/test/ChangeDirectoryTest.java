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
import commands.ChangeDirectory;
import commands.Commands;
import commands.MakeDirectory;
import driver.JShell;

public class ChangeDirectoryTest {
  Commands mkdir;
  Commands cd;
  JShell js;

  @Before
  public void setUp() {
    js = new JShell();
    cd = new ChangeDirectory();
    mkdir = new MakeDirectory();
  }

  /*
   * Should not run due to not enough arguments.
   */
  @Test
  public void testTooFewArguments() {
    String[] cdParam = {"cd"};
    assertEquals("Error", cd.executeCommand(js, cdParam));
  }

  /*
   * Should not run due to too many arguments.
   */
  @Test
  public void testTooManyArguments() {
    String[] cdParam = {"cd", "invalid", "argument", "size"};
    assertEquals("Error", cd.executeCommand(js, cdParam));

  }

  /*
   * Should not run due to going to an invalid directory.
   */
  @Test
  public void testInvalidDirectoryName() {
    String[] cdParam = {"cd", "invalidDirectory"};
    assertEquals("Error", cd.executeCommand(js, cdParam));

  }

  /*
   * Goes into folder "folder1" using relative path.
   */
  @Test
  public void testRelativePathChange() {
    String[] mkDirParam = {"mkdir", "folder1"};
    String[] cdParam = {"cd", "folder1"};
    mkdir.executeCommand(js, mkDirParam);
    cd.executeCommand(js, cdParam);
    assertEquals("folder1", js.getCurrDirObj().getCurrDir().getDirectoryName());


  }

  /*
   * Goes into folder3 using absolute path.
   */
  @Test
  public void testFullPathChange() {
    String[] mkDirParam = {"mkdir", "folder2", "folder2/folder3"};
    String[] cmdAndParam = {"cd", "/folder2/folder3"};
    mkdir.executeCommand(js, mkDirParam);
    cd.executeCommand(js, cmdAndParam);
    assertEquals("folder3", js.getCurrDirObj().getCurrDir().getDirectoryName());

  }

  /*
   * Goes back to folder4 using path with periods.
   */
  @Test
  public void testPeriodMoving() {
    String[] mkDirParam = {"mkdir", "folder4", "folder4/folder5"};
    String[] cdParam = {"cd", "/folder4/folder5/./.."};
    mkdir.executeCommand(js, mkDirParam);
    cd.executeCommand(js, cdParam);
    assertEquals("folder4", js.getCurrDirObj().getCurrDir().getDirectoryName());
  }

}
