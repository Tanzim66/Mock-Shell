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
import commands.Manual;
import containers.CurrentDirectory;
import containers.Directory;
import containers.File;
import driver.JShell;
import system.PathHandler;

public class ManualTest {
  File file;
  Commands man = new Manual();
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
    String[] cmdAndParam = {"man"};
    assertEquals("Error", man.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testMoreThanOneArgument() {
    String[] cmdAndParam = {"man", "echo", "mkdir"};
    assertEquals("Error", man.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testOneCommand() {
    String[] cmdAndParam = {"man", "echo"};
    assertEquals(
        "echo STRING [> OUTFILE]\n"
            + "If OUTFILE is not provided, print STRING on the shell. "
            + "Otherwise, put STRING into file OUTFILE.\n\n"
            + "echo STRING >> OUTFILE\n"
            + "Like the previous command, but appends instead of overwrites.",
        man.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testOneInvalidCommand() {
    String[] cmdAndParam = {"man", "something"};
    assertEquals("Error", man.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testRedirectInvalidCommandWithOverride() {
    String[] cmdAndParam = {"man", "something", ">", "someFile"};
    assertEquals("Redirection Error", man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectInvalidFileWithOverride() {
    String[] cmdAndParam = {"man", "echo", ">", "new.File"};
    assertEquals("Redirection Error", man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectInvalidCommandWithAppend() {
    String[] cmdAndParam = {"man", "something", ">>", "someFile"};
    assertEquals("Redirection Error", man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectInvalidFileWithAppend() {
    String[] cmdAndParam = {"man", "echo", ">>", "new.File"};
    assertEquals("Redirection Error", man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectInvalidArrows() {
    String[] cmdAndParam = {"man", "echo", ">>>", "newFile"};
    assertEquals("Error", man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectNewFileWithOverride() {
    String[] cmdAndParam = {"man", "echo", ">", "newFile"};
    assertEquals("Redirection Successful: Created new file",
        man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNotNull(file);
  }

  @Test
  public void testRedirectNewFileWithAppend() {
    String[] cmdAndParam = {"man", "echo", ">>", "newFile2"};
    assertEquals("Redirection Successful: Created new file",
        man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNotNull(file);
  }

  @Test
  public void testRedirectOverrideExistingFile() {
    String[] cmdAndParam = {"man", "echo", ">", "overrideFile"};
    assertEquals("Redirection Successful: Created new file",
        man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNotNull(file);
    String[] cmdAndParam2 = {"man", "mkdir", ">", "overrideFile"};
    assertEquals("Redirection Successful: Existing file overwritten",
        man.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[3]);
    assertNotNull(file);
  }

  @Test
  public void testRedirectAppendExistingFile() {
    String[] cmdAndParam = {"man", "echo", ">", "appendFile"};
    assertEquals("Redirection Successful: Created new file",
        man.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNotNull(file);
    String[] cmdAndParam2 = {"man", "mkdir", ">>", "appendFile"};
    assertEquals("Redirection Successful: Existing file appended",
        man.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[3]);
    assertNotNull(file);
  }
}
