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
import commands.Move;
import driver.JShell;

public class MoveTest {

  Commands mkdir;
  Commands echo;
  Commands move;
  Commands cd;
  JShell js;

  @Before
  public void setUp() {
    js = new JShell();
    mkdir = new MakeDirectory();
    echo = new Echo();
    move = new Move();
    cd = new ChangeDirectory();
  }

  /*
   * Should not run due to not enough arguments.
   */
  @Test
  public void testTooFewArguments() {
    String[] moveParam1 = {"mv"};
    String[] moveParam2 = {"mv", "notenough"};
    assertEquals("Error", move.executeCommand(js, moveParam1));
    assertEquals("Error", move.executeCommand(js, moveParam2));
  }

  /*
   * Should not run due to too many arguments.
   */
  @Test
  public void testTooManyArguments() {
    String[] moveParam = {"mv", "too", "many", "arguments"};
    assertEquals("Error", move.executeCommand(js, moveParam));

  }

  /*
   * Should not run due to the initial path not existing.
   */
  @Test
  public void testOldPathDoesNotExist() {
    String[] moveParam = {"mv", "/folder1", "/folder2"};
    assertEquals("Error", move.executeCommand(js, moveParam));
  }

  /*
   * Should not run due to trying to move a directory to an invalid path.
   */
  @Test
  public void testDirToInvalidPath() {
    String[] mkdirParam = {"mkdir", "folder1"};
    String[] moveParam = {"mv", "/folder1", "/folder3/folder5/"};
    mkdir.executeCommand(js, mkdirParam);
    assertEquals("Error", move.executeCommand(js, moveParam));
  }

  /*
   * Should not run due to being unable to move a directory into a non-existing
   * file.
   */
  @Test
  public void testDirToNewFile() {
    String[] mkdirParam = {"mkdir", "folder2"};
    String[] moveParam = {"mv", "/folder1/", "newFile"};
    mkdir.executeCommand(js, mkdirParam);
    assertEquals("Error", move.executeCommand(js, moveParam));
  }

  /*
   * Should not run due to being unable to move a directory to an existing file.
   */
  @Test
  public void testDirToExistingFile() {
    String[] mkdirParam = {"mkdir", "folder3"};
    String[] echoParam = {"echo", "\"Content\"", ">", "file1"};
    String[] moveParam = {"mv", "/folder3/", "file1"};
    mkdir.executeCommand(js, mkdirParam);
    echo.executeCommand(js, echoParam);
    assertEquals("Error", move.executeCommand(js, moveParam));
  }

  /*
   * Should rename the initial directory to the new directory name.
   */
  @Test
  public void testDirToNewDir() {
    String[] mkdirParam = {"mkdir", "folder4"};
    String[] moveParam = {"move", "/folder4/", "newDirName/"};
    mkdir.executeCommand(js, mkdirParam);
    move.executeCommand(js, moveParam);
    assertEquals("newDirName", js.getCurrDirObj().getCurrDir()
        .getChildDirectory("newDirName").getDirectoryName());
  }

  /*
   * Should not run due to moving a directory into it's own child.
   */
  @Test
  public void testDirToExistingChildDir() {
    String[] mkdirParam = {"mkdir", "folder5", "folder5/folder6"};
    String[] moveParam = {"mv", "/folder5/", "/folder5/folder6"};
    mkdir.executeCommand(js, mkdirParam);
    assertEquals("Error", move.executeCommand(js, moveParam));
  }

  /*
   * Should move a directory and its contents to another directory specified,
   * which is not one of it's children, and removing the initial directory from
   * it's initial path.
   */
  @Test
  public void testDirToExistingNonChildDir() {
    String[] mkdirParam = {"mkdir", "folder6", "folder7"};
    String[] cdParam = {"cd", "/folder7"};
    String[] moveParam = {"mv", "/folder6/", "/folder7/"};
    mkdir.executeCommand(js, mkdirParam);
    move.executeCommand(js, moveParam);
    cd.executeCommand(js, cdParam);
    assertEquals("folder6", js.getCurrDirObj().getCurrDir()
        .getChildDirectory("folder6").getDirectoryName());
  }

  /*
   * Should not run due to being unable to move a file to an invalid path.
   */
  @Test
  public void testFileToInvalidPath() {
    String[] echoParam = {"echo", "\"text\"", ">", "file2"};
    String[] moveParam = {"mv", "file2", "/invalid/path"};
    echo.executeCommand(js, echoParam);
    assertEquals("Error", move.executeCommand(js, moveParam));

  }

  /*
   * Should rename the initial file to the new file's name.
   */
  @Test
  public void testFileToNewFile() {
    String[] echoParam = {"echo", "\"moreText\"", ">", "file3"};
    String[] moveParam = {"mv", "file3", "newFileName"};
    echo.executeCommand(js, echoParam);
    move.executeCommand(js, moveParam);
    assertEquals("newFileName", js.getCurrDirObj().getCurrDir()
        .getChildFile("newFileName").getFileName());
    assertEquals("moreText", js.getCurrDirObj().getCurrDir()
        .getChildFile("newFileName").getFileContent());
  }

  /*
   * Should overwrite the new file's contents with the old file's contents,
   * while leaving the new file's name the same, and removing the initial file.
   */
  @Test
  public void testFileToExistingFile() {
    String[] echoParam1 = {"echo", "\"text4\"", ">", "file4"};
    String[] echoParam2 = {"echo", "\"text5\"", ">", "file5"};
    String[] moveParam = {"mv", "file4", "file5"};
    echo.executeCommand(js, echoParam1);
    echo.executeCommand(js, echoParam2);
    move.executeCommand(js, moveParam);
    assertEquals(null, js.getCurrDirObj().getCurrDir().getChildFile("file4"));
    assertEquals("file5",
        js.getCurrDirObj().getCurrDir().getChildFile("file5").getFileName());
    assertEquals("text4",
        js.getCurrDirObj().getCurrDir().getChildFile("file5").getFileContent());
  }

  /*
   * Should not run as you cannot move a file to a directory that does not
   * exist.
   */
  @Test
  public void testFileToNewDir() {
    String[] echoParam = {"echo", "\"dummyText\"", ">", "file6"};
    String[] moveParam = {"mv", "file6", "invalidDir/"};
    echo.executeCommand(js, echoParam);
    assertEquals("Error", move.executeCommand(js, moveParam));
  }

  /*
   * Should move the file into the directory specified, and remove the file from
   * it's initial file location.
   */
  @Test
  public void testFileToExistingDir() {
    String[] echoParam = {"echo", "\"evenMoreText\"", ">", "file7"};
    String[] mkdirParam = {"mkdir", "folder8"};
    String[] moveParam = {"mv", "file7", "folder8/"};
    String[] cdParam = {"cd", "folder8"};
    echo.executeCommand(js, echoParam);
    mkdir.executeCommand(js, mkdirParam);
    move.executeCommand(js, moveParam);
    assertEquals(null, js.getCurrDirObj().getCurrDir().getChildFile("file7"));
    cd.executeCommand(js, cdParam);
    assertEquals("file7",
        js.getCurrDirObj().getCurrDir().getChildFile("file7").getFileName());
    assertEquals("evenMoreText",
        js.getCurrDirObj().getCurrDir().getChildFile("file7").getFileContent());
  }



}
