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
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Commands;
import commands.List;
import commands.MakeDirectory;
import containers.CurrentDirectory;
import containers.Directory;
import containers.File;
import driver.JShell;
import system.FileSystem;
import system.PathHandler;

public class ListTest {
  File file;
  Commands mkdir = new MakeDirectory();
  Commands ls = new List();
  JShell js;
  Directory root;
  CurrentDirectory currDir;
  FileSystem fs;

  @Before
  public void setUp() {
    js = new JShell();
    root = js.getRootDirectory();
    currDir = js.getCurrDirObj();
    fs = FileSystem.createFileSystemInstance();
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass().getDeclaredField("fs"));
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  public void testNoArguments() {
    String[] cmdAndParam = {"ls"};
    assertEquals("", ls.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testNoArgumentsWithMultipleDirectories() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2", "/testDir3"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls"};
    assertEquals("testDir1\ntestDir2\ntestDir3\n",
        ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testListRoot() {
    String[] cmdAndParam = {"ls", "/"};
    assertEquals("/:\n\n", ls.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testListRootWithOneDirectory() {
    String[] cmdAndParam = {"mkdir", "/testDir"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "/"};
    assertEquals("/:\ntestDir\n\n", ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testListRootWithMultipleDirectory() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2", "/testDir3"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "/"};
    assertEquals("/:\ntestDir1\ntestDir2\ntestDir3\n\n",
        ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testListFiveDirectories() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2", "/testDir3",
        "/testDir4", "/testDir5"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 =
        {"ls", "/testDir1", "/testDir2", "/testDir3", "/testDir4", "/testDir5"};
    assertEquals("/testDir1:\n\n/testDir2:\n\n/testDir3:\n\n/testDir4:\n\n"
        + "/testDir5:\n\n", ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testListFiveDirectoriesWithSubDirectories() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2", "/testDir3",
        "/testDir4", "/testDir5", "/testDir1/subDir1", "/testDir2/subDir2",
        "/testDir3/subDir3", "/testDir4/subDir4", "/testDir5/subDir5"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 =
        {"ls", "/testDir1", "/testDir2", "/testDir3", "/testDir4", "/testDir5"};
    assertEquals("/testDir1:\nsubDir1\n\n/testDir2:\nsubDir2\n\n"
        + "/testDir3:\nsubDir3\n\n/testDir4:\nsubDir4\n\n"
        + "/testDir5:\nsubDir5\n\n", ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testListFiveDirectoriesFailsOnFirst() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2", "/testDir3",
        "/testDir4", "/testDir5"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "/unknownDir", "/testDir2", "/testDir3",
        "/testDir4", "/testDir5"};
    assertEquals("Error", ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testListFiveDirectoriesFailsAtThird() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2", "/testDir3",
        "/testDir4", "/testDir5"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "/testDir1", "/testDir2", "/unknownDir",
        "/testDir4", "/testDir5"};
    assertEquals("/testDir1:\n\n/testDir2:\n\n",
        ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testRecursivelyListNoArguments() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-R"};
    assertEquals(
        "/:\ntestDir1\ntestDir2\n\n/testDir1:\ninnerDir1\n\n/testDir1/"
            + "innerDir1:\n\n/testDir2:\ninnerDir2\n\n/testDir2/innerDir2:\n\n",
        ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testRecursivelyListWithWrongArgument() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-r"};
    assertEquals("Error", ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testRecursivelyListFiveDirectoriesFailsOnFirst() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2", "/testDir3",
        "/testDir4", "/testDir5"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-R", "/failDir", "/testDir2", "/testDir3",
        "/testDir4", "/testDir5"};
    assertEquals("Error", ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testRecursivelyListFiveArgumentsFailOnThird() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2", "/testDir3",
        "/testDir4", "/testDir5"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-R", "/testDir1", "/testDir2",
        "/unknownDir", "/testDir4", "/testDir5"};
    assertEquals("/testDir1:\n\n/testDir2:\n\n",
        ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testRecursivelyListMultipleArguments() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-R", "/testDir1", "/testDir2"};
    assertEquals(
        "/testDir1:\ninnerDir1\n\n/testDir1/"
            + "innerDir1:\n\n/testDir2:\ninnerDir2\n\n/testDir2/innerDir2:\n\n",
        ls.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testRedirectInvalidListWithOverride() {
    String[] cmdAndParam = {"ls", "/unknown", ">", "file"};
    assertEquals("Redirection Error", ls.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectInvalidFileWithOverride() {
    String[] cmdAndParam = {"ls", "/", ">", "file.new"};
    assertEquals("Redirection Error", ls.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectInvalidListWithAppend() {
    String[] cmdAndParam = {"ls", "/unknown", ">>", "file"};
    assertEquals("Redirection Error", ls.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectInvalidFileWithAppend() {
    String[] cmdAndParam = {"ls", "/", ">>", "file.new"};
    assertEquals("Redirection Error", ls.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectInvalidArrows() {
    String[] cmdAndParam = {"ls", "/", ">>>", "file"};
    assertEquals("/:\n\n", ls.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[3]);
    assertNull(file);
  }

  @Test
  public void testRedirectNewFileWithOverride() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "/", ">", "file"};
    assertEquals("Redirection Successful: Created new file",
        ls.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[3]);
    assertNotNull(file);
  }

  @Test
  public void testRedirectNewFileWithAppend() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "/", ">>", "file"};
    assertEquals("Redirection Successful: Created new file",
        ls.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[3]);
    assertNotNull(file);
  }

  @Test
  public void testRedirectOverrideExistingFile() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "/", ">", "file"};
    assertEquals("Redirection Successful: Created new file",
        ls.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[3]);
    assertNotNull(file);
    String[] cmdAndParam3 = {"ls", "/testDir1", ">", "file"};
    assertEquals("Redirection Successful: Existing file overwritten",
        ls.executeCommand(js, cmdAndParam3));
  }

  @Test
  public void testRedirectAppendExistingFile() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "/", ">>", "file"};
    assertEquals("Redirection Successful: Created new file",
        ls.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[3]);
    assertNotNull(file);
    String[] cmdAndParam3 = {"ls", "/testDir1", ">>", "file"};
    assertEquals("Redirection Successful: Existing file appended",
        ls.executeCommand(js, cmdAndParam3));
  }

  @Test
  public void testRecursiveRedirectNewFileWithOverride() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-R", "/", ">", "file"};
    assertEquals("Redirection Successful: Created new file",
        ls.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[4]);
    assertNotNull(file);
  }

  @Test
  public void testRecursiveRedirectNewFileWithAppend() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-R", "/", ">>", "file"};
    assertEquals("Redirection Successful: Created new file",
        ls.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[4]);
    assertNotNull(file);
  }

  @Test
  public void testRecursiveRedirectOverrideExistingFile() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-R", "/", ">", "file"};
    assertEquals("Redirection Successful: Created new file",
        ls.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[4]);
    assertNotNull(file);
    String[] cmdAndParam3 = {"ls", "-R", "/testDir1", ">", "file"};
    assertEquals("Redirection Successful: Existing file overwritten",
        ls.executeCommand(js, cmdAndParam3));
  }

  @Test
  public void testRecursiveRedirectAppendExistingFile() {
    String[] cmdAndParam = {"mkdir", "/testDir1", "/testDir2",
        "/testDir1/innerDir1", "/testDir2/innerDir2"};
    mkdir.executeCommand(js, cmdAndParam);
    String[] cmdAndParam2 = {"ls", "-R", "/", ">>", "file"};
    assertEquals("Redirection Successful: Created new file",
        ls.executeCommand(js, cmdAndParam2));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam2[4]);
    assertNotNull(file);
    String[] cmdAndParam3 = {"ls", "-R", "/testDir1", ">>", "file"};
    assertEquals("Redirection Successful: Existing file appended",
        ls.executeCommand(js, cmdAndParam3));
  }
}
