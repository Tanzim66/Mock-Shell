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
import commands.Remove;
import containers.CurrentDirectory;
import containers.Directory;
import driver.JShell;
import system.PathHandler;

public class RemoveTest {

  Directory dir;
  Commands cd = new ChangeDirectory();
  Commands mkdir = new MakeDirectory();
  Commands rm = new Remove();
  JShell js;
  Directory root;
  CurrentDirectory currDir;

  @Before
  public void setUp() {
    js = new JShell();
    root = js.getRootDirectory();
    currDir = js.getCurrDirObj();
  }

  @Test
  public void testNoArguments() {
    String[] cmdAndParam = {"rm"};
    assertEquals("Error", rm.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testRemoveMoreThanOneArgument() {
    String[] cmdAndParam = {"mkdir", "/firsttest1", "/firsttest2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"rm", "/firsttest1", "/firsttest2"};
    assertEquals("Error", rm.executeCommand(js, cmdAndParam2));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam2[1]);
    assertNotNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam2[2]);
    assertNotNull(dir);
  }

  @Test
  public void testRemoveRoot() {
    String[] cmdAndParam = {"rm", "/"};
    assertEquals("Error", rm.executeCommand(js, cmdAndParam));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNotNull(dir);
  }

  @Test
  public void testRemoveInvalidDirectory() {
    String[] cmdAndParam = {"rm", "/doesntexist"};
    assertEquals("Error", rm.executeCommand(js, cmdAndParam));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNull(dir);
  }

  @Test
  public void testRemoveRootSubDirectory() {
    String[] cmdAndParam = {"mkdir", "/subDir"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"rm", "/subDir"};
    assertEquals("Success", rm.executeCommand(js, cmdAndParam2));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNull(dir);
  }

  @Test
  public void testRemoveRootParentOfSubDirectory() {
    String[] cmdAndParam = {"mkdir", "/parentDir", "/parentDir/innerDir"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"rm", "/parentDir"};
    assertEquals("Success", rm.executeCommand(js, cmdAndParam2));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[2]);
    assertNull(dir);
  }

  @Test
  public void testRemoveParentDirectoryOfCurrentDirectory() {
    String[] cmdAndParam = {"mkdir", "/toberemoved", "/toberemoved/current"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"cd", "/toberemoved/current"};
    cd.executeCommand(js, cmdAndParam2);
    String[] cmdAndParam3 = {"rm", "/toberemoved"};
    assertEquals("Error", rm.executeCommand(js, cmdAndParam3));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNotNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[2]);
    assertNotNull(dir);
  }
}
