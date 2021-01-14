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
import containers.CurrentDirectory;
import containers.Directory;
import driver.JShell;
import system.PathHandler;

public class MakeDirectoryTest {
  Directory dir;
  Commands mkdir = new MakeDirectory();
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
  public void testOneArgument() {
    String[] cmdAndParam = {"mkdir"};
    assertEquals("Error", mkdir.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testMakeOneDirectoryWithInvalidName() {
    String[] cmdAndParam = {"mkdir", "/dir.directory"};
    assertEquals("Error", mkdir.executeCommand(js, cmdAndParam));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNull(dir);
  }

  @Test
  public void testMakeOneDirectoryWithInvalidPath() {
    String[] cmdAndParam = {"mkdir", "/invalidPath/testDir"};
    assertEquals("Error", mkdir.executeCommand(js, cmdAndParam));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNull(dir);
  }

  @Test
  public void testMakeOneDirectoryAndTestDuplicateFails() {
    String[] cmdAndParam = {"mkdir", "/oneDir"};
    assertEquals("Success", mkdir.executeCommand(js, cmdAndParam));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNotNull(dir);
    assertEquals("Error", mkdir.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testMakeTwoDirectoriesInRoot() {
    String[] cmdAndParam = {"mkdir", "/twoDir1", "/twoDir2"};
    assertEquals("Success", mkdir.executeCommand(js, cmdAndParam));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNotNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[2]);
    assertNotNull(dir);
  }

  @Test
  public void testMakeOneDirectoryInsideOfAnother() {
    String[] cmdAndParam = {"mkdir", "/outerDir", "/outerDir/innerDir"};
    assertEquals("Success", mkdir.executeCommand(js, cmdAndParam));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNotNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[2]);
    assertNotNull(dir);
  }

  @Test
  public void testMakeFiveDirectoriesAndThirdFails() {
    String[] cmdAndParam =
        {"mkdir", "/dir1", "/dir2", "/in.valid", "dir4", "dir5"};
    assertEquals("Error", mkdir.executeCommand(js, cmdAndParam));
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);
    assertNotNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[2]);
    assertNotNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[3]);
    assertNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[4]);
    assertNull(dir);
    dir = PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[5]);
    assertNull(dir);
  }
}
