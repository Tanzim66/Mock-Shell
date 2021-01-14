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
import commands.Echo;
import commands.MakeDirectory;
import commands.PrintWorkingDirectory;
import driver.JShell;

public class PrintWorkingDirectoryTest {

  Commands pwd;
  Commands mkdir;
  Commands cd;
  Commands echo;
  JShell js;

  @Before
  public void setUp() {
    js = new JShell();
    pwd = new PrintWorkingDirectory();
    mkdir = new MakeDirectory();
    cd = new ChangeDirectory();
    echo = new Echo();
  }

  /*
   * Should not run due to too many arguments (Without Redirection).
   */
  @Test
  public void testTooManyArgumentsNoRedirection() {
    String[] pwdParams = {"pwd", "invalid"};
    assertEquals("Error", pwd.executeCommand(js, pwdParams));
  }

  /*
   * Should not run due to too many arguments (With redirection).
   */
  @Test
  public void testTooManyArgumentsRedirection() {
    String[] pwdParams = {"pwd", ">", "invalid", "param"};
    assertEquals("Error", pwd.executeCommand(js, pwdParams));

  }

  /*
   * Should give the current directory.
   */
  @Test
  public void testNoArguments() {
    String[] pwdParams = {"pwd"};
    String[] mkdirParams = {"mkdir", "folder1"};
    String[] cdParams = {"cd", "folder1"};
    mkdir.executeCommand(js, mkdirParams);
    cd.executeCommand(js, cdParams);
    assertEquals("/folder1", pwd.executeCommand(js, pwdParams));

  }


  /*
   * Should not work due to the file name having illegal characters.
   */
  @Test
  public void testInvalidFileNameRedirection() {
    String[] pwdParams = {"pwd", ">", "IN%VA@LID"};
    assertEquals("Redirection Error", pwd.executeCommand(js, pwdParams));

  }

  /*
   * Overwrites a file's contents with the name of the current directory.
   */
  @Test
  public void testOverwriteRedirection() {
    String[] mkdirParams = {"mkdir", "folder2"};
    String[] cdParams = {"cd", "folder2"};
    String[] echoParams = {"echo", "\"text\"", ">", "file1"};
    String[] pwdParams = {"pwd", ">", "file1"};
    mkdir.executeCommand(js, mkdirParams);
    cd.executeCommand(js, cdParams);
    echo.executeCommand(js, echoParams);
    pwd.executeCommand(js, pwdParams);
    assertEquals("/folder2",
        js.getCurrDirObj().getCurrDir().getChildFile("file1").getFileContent());

  }

  /*
   * Appends a file's contents with the name of the current directory.
   */
  @Test
  public void testAppendRedirection() {
    String[] mkdirParams = {"mkdir", "folder3"};
    String[] cdParams = {"cd", "folder3"};
    String[] echoParams = {"echo", "\"moreText\"", ">", "file2"};
    String[] pwdParams = {"pwd", ">>", "file2"};
    mkdir.executeCommand(js, mkdirParams);
    cd.executeCommand(js, cdParams);
    echo.executeCommand(js, echoParams);
    pwd.executeCommand(js, pwdParams);
    assertEquals("moreText\n/folder3",
        js.getCurrDirObj().getCurrDir().getChildFile("file2").getFileContent());

  }


}
