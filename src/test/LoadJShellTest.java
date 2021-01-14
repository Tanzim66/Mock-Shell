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
import java.io.File;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.FileSystem;
import commands.ChangeDirectory;
import commands.Commands;
import commands.Echo;
import commands.LoadJShell;
import commands.MakeDirectory;
import commands.SaveJShell;
import driver.JShell;

public class LoadJShellTest {

  Commands save;
  Commands load;
  Commands mkdir;
  Commands echo;
  Commands cd;
  File file;
  JShell js1;
  JShell js2;
  FileSystem fs;

  @Before
  public void setUp() {
    js1 = new JShell();
    js2 = new JShell();
    save = new SaveJShell();
    load = new LoadJShell();
    mkdir = new MakeDirectory();
    echo = new Echo();
    cd = new ChangeDirectory();
    fs = FileSystem.createFileSystemInstance();
  }

  /*
   * Removes the instance of the file system.
   */
  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass().getDeclaredField("fs"));
    field.setAccessible(true);
    field.set(null, null);
  }

  /*
   * Should not run due to not enough arguments.
   */
  @Test
  public void testTooFewArguments() {
    String[] loadParams = {"load"};
    assertEquals("Error", load.executeCommand(js1, loadParams));
  }

  /*
   * Should not run due to too many arguments.
   */
  @Test
  public void testTooManyArguments() {
    String[] loadParams = {"load", "tooMany", "arguments"};
    assertEquals("Error", load.executeCommand(js1, loadParams));
  }

  /*
   * Should not run due to the loadJShell command not being the first command
   * inputted.
   */
  @Test
  public void testNotFirstCommand() {
    String[] loadParams = {"loadJShell", "anyFile"};
    js1.getUserInputList().getRecentCommands().add("Some command");
    js1.getUserInputList().getRecentCommands().add("loadJShell anyFile");
    assertEquals("Error", load.executeCommand(js1, loadParams));
  }

  /*
   * Should not run due to not being able to find the file for JShell.
   */
  @Test
  public void testInvalidFileName() {
    String[] loadParams = {"load", "nonExistentFile"};
    assertEquals("Error", load.executeCommand(js1, loadParams));
  }

  /*
   * Checks if the initial JShell has been successfully loaded by checking if
   * the file system, current directory, user input list (history), and the
   * directory stack (pushd, popd) is the same as the initial JShell.
   */
  @Test
  public void testJShellFilesAreSame() {
    String[] mkdirParams = {"mkdir", "folder1", "folder1/folder2", "folder3"};
    String[] echoParams = {"echo", "\"text\"", ">", "fileName"};
    String[] cdParams = {"cd", "folder1"};
    String[] saveParams = {"saveJShell", "saveFileName"};
    String[] loadParams = {"loadJShell", "saveFileName"};
    js1.getUserInputList().getRecentCommands().add("History from js1");
    mkdir.executeCommand(js1, mkdirParams);
    echo.executeCommand(js1, echoParams);
    cd.executeCommand(js1, cdParams);
    save.executeCommand(js1, saveParams);
    js2.getUserInputList().getRecentCommands().add("loadJShell saveFileName");
    load.executeCommand(js2, loadParams);
    assertEquals(js1.getCurrDirObj(), js2.getCurrDirObj());
    assertEquals(js1.getDirStack(), js2.getDirStack());
    assertEquals(js1.getUserInputList(), js2.getUserInputList());
    assertEquals(js1.getFileSystem(), js2.getFileSystem());
  }

}
