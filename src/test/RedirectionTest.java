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
import org.junit.Test;
import commands.Commands;
import commands.Concatenate;
import commands.MakeDirectory;
import driver.JShell;
import system.Redirection;

public class RedirectionTest {

  Commands catInstance = new Concatenate();
  Commands mkdirInstance = new MakeDirectory();
  JShell js = new JShell();

  @Test
  public void testRedirectToFile_CreateANewFile() {
    System.out.println("Tests redirection creating new file:");

    String[] params = {"This is the content of newFile", ">", "newFile"};
    String redirectResult = Redirection.redirectToFile(js.getRootDirectory(),
        params, js.getCurrDirObj());
    String[] cmdAndParam = {"cat", "newFile"};
    String concatResult = catInstance.executeCommand(js, cmdAndParam);

    assertEquals(
        "Redirection Successful: Created new file - "
            + "This is the content of newFile",
        redirectResult + " - " + concatResult);
    System.out.println("\n");
  }

  @Test
  public void testRedirectToFile_OverwriteAFile() {
    System.out.println("Tests redirection overwriting a file:");

    String[] params = {"This is the content of newFile", ">", "newFile2"};
    Redirection.redirectToFile(js.getRootDirectory(), params,
        js.getCurrDirObj());
    params[0] = "This is overwritten";
    String redirectResult = Redirection.redirectToFile(js.getRootDirectory(),
        params, js.getCurrDirObj());
    String[] cmdAndParam = {"cat", "newFile2"};
    String concatResult = catInstance.executeCommand(js, cmdAndParam);

    assertEquals("Redirection Successful: Existing file overwritten - "
        + "This is overwritten", redirectResult + " - " + concatResult);
    System.out.println("\n");
  }


  @Test
  public void testRedirectToFile_AppendAFile() {
    System.out.println("Tests redirection appending a file:");

    String[] params = {"This is the content of newFile", ">", "newFile3"};
    Redirection.redirectToFile(js.getRootDirectory(), params,
        js.getCurrDirObj());
    params[0] = "This is appended";
    params[1] = ">>";
    String redirectResult = Redirection.redirectToFile(js.getRootDirectory(),
        params, js.getCurrDirObj());
    String[] cmdAndParam = {"cat", "newFile3"};
    String concatResult = catInstance.executeCommand(js, cmdAndParam);

    assertEquals(
        "Redirection Successful: Existing file appended - "
            + "This is the content of newFile\nThis is appended",
        redirectResult + " - " + concatResult);

    System.out.println("\n");
  }


  @Test
  public void testRedirectToFile_GivenFullPath() {

    System.out.println("Tests redirection using full path:");

    String[] cmdAndParam = {"mkdir", "/a", "/a/b", "/a/b/c"};
    mkdirInstance.executeCommand(js, cmdAndParam);

    String[] params =
        {"This is the content of newFile", ">", "/a/b/c/newFile3"};
    String redirectResult = Redirection.redirectToFile(js.getRootDirectory(),
        params, js.getCurrDirObj());

    String[] cmdAndParam2 = {"cat", "/a/b/c/newFile3"};
    String concatResult = catInstance.executeCommand(js, cmdAndParam2);

    assertEquals(
        "Redirection Successful: Created new file - "
            + "This is the content of newFile",
        redirectResult + " - " + concatResult);

    System.out.println("\n");
  }

  @Test
  public void testRedirectToFile_PathIsNotValid() {

    System.out.println("Tests redirection using full path:");

    String[] params =
        {"This is the content of newFile", ">", "/a/b/r/t/c/newFile3"};
    String redirectResult = Redirection.redirectToFile(js.getRootDirectory(),
        params, js.getCurrDirObj());

    assertEquals("Redirection Error", redirectResult);

    System.out.println("\n");
  }

}
