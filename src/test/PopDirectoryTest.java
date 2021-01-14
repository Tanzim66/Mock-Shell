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
import commands.PopDirectory;
import commands.PushDirectory;
import driver.JShell;

public class PopDirectoryTest {

  Commands mkdir;
  Commands pushDir;
  Commands popDir;
  JShell js;
  JShell js2;

  @Before
  public void setUp() {
    js = new JShell();
    js2 = new JShell();
    mkdir = new MakeDirectory();
    pushDir = new PushDirectory();
    popDir = new PopDirectory();
  }

  /*
   * Should not run due to too many arguments.
   */
  @Test
  public void testTooManyArguments() {
    String[] popParams = {"popd", "many", "arguments"};
    assertEquals("Error", popDir.executeCommand(js, popParams));
  }

  /*
   * Checks if the user successfully goes to the directory at the top of the
   * stack.
   */
  @Test
  public void testNonEmptyStack() {
    String[] mkdirParams = {"mkdir", "folder1", "folder1/folder2", "folder3"};
    String[] pushParams = {"pushd", "folder1/folder2"};
    String[] pushParams2 = {"pushd", "/folder3"};
    String[] popParams = {"popd"};
    mkdir.executeCommand(js, mkdirParams);
    pushDir.executeCommand(js, pushParams);
    pushDir.executeCommand(js, pushParams2);
    popDir.executeCommand(js, popParams);
    assertEquals("/folder1/folder2", js.getCurrDirObj().getCurrPath());

  }

  /*
   * Should not change the directory at the end due to the stack being empty.
   */
  @Test
  public void testEmptyStack() {
    String[] mkdirParams = {"mkdir", "folder4", "folder5"};
    String[] pushParams = {"pushd", "folder4"};
    String[] pushParams2 = {"pushd", "/folder5"};
    String[] popParams = {"popd"};
    mkdir.executeCommand(js, mkdirParams);
    pushDir.executeCommand(js, pushParams);
    pushDir.executeCommand(js, pushParams2);
    popDir.executeCommand(js, popParams);
    popDir.executeCommand(js, popParams);
    assertEquals("Error", popDir.executeCommand(js, popParams));
  }

}
