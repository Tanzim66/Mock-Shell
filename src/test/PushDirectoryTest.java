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
import commands.Commands;
import commands.MakeDirectory;
import commands.PushDirectory;
import driver.JShell;

public class PushDirectoryTest {

  Commands mkdir;
  Commands pushDir;
  JShell js;

  @Before
  public void setUp() {
    js = new JShell();
    mkdir = new MakeDirectory();
    pushDir = new PushDirectory();
  }

  /*
   * Should not run due to not enough arguments.
   */
  @Test
  public void testTooFewArguments() {
    String[] pushParams = {"pushd"};
    assertEquals("Error", pushDir.executeCommand(js, pushParams));
  }

  /*
   * Should not run due to too many arguments.
   */
  @Test
  public void testTooManyArguments() {
    String[] pushParams = {"pushd", "with", "too", "many", "arguments"};
    assertEquals("Error", pushDir.executeCommand(js, pushParams));
  }

  /*
   * The stack should be empty as the user tried to push an invalid directory
   */
  @Test
  public void testPushInvalidDir() {
    String[] pushParams = {"pushd", "invalidDir"};
    pushDir.executeCommand(js, pushParams);
    assertEquals(true, js.getDirStack().getStack().isEmpty());
  }

  /*
   * Pushes into multiple directories, checks if the current directory changed
   * and the proper directory got added to the top of the stack.
   */
  @Test
  public void testPushValidDir() {
    String[] mkdirParams = {"mkdir", "folder1", "folder2", "folder2/folder3"};
    String[] pushdParams1 = {"pushd", "folder1"};
    String[] pushdParams2 = {"pushd", "/folder2/folder3"};
    mkdir.executeCommand(js, mkdirParams);
    pushDir.executeCommand(js, pushdParams1);
    assertEquals("/folder1", js.getCurrDirObj().getCurrPath());
    pushDir.executeCommand(js, pushdParams2);
    assertEquals("/folder2/folder3", js.getCurrDirObj().getCurrPath());
    assertEquals("/folder1", js.getDirStack().getStack().peek().getCurrPath());
  }

}
