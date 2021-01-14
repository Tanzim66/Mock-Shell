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

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import commands.Commands;
import containers.Directory;
import commands.Copy;
import commands.Echo;
import commands.MakeDirectory;
import driver.JShell;
import system.FileSystem;

public class CopyTest {

  Commands mkdir;
  Commands echo;
  Commands copy;
  JShell js;
  FileSystem fs;

  @Before
  public void setUp() {
    js = new JShell();
    mkdir = new MakeDirectory();
    echo = new Echo();
    copy = new Copy();
    fs = FileSystem.createFileSystemInstance();
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass().getDeclaredField("fs"));
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  public void testLessThanTwoArguments() {
    String[] cmdWithNoParam = {"cp"};
    String[] cmdWithOneParam = {"cp", "oneParam"};
    assertEquals("Error", copy.executeCommand(js, cmdWithNoParam));
    assertEquals("Error", copy.executeCommand(js, cmdWithOneParam));
  }

  @Test
  public void testMoreThanTwoArguments() {
    String[] cmdAndParam = {"cp", "more", "than", "two", "arguments"};
    assertEquals("Error", copy.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testOldPathDoesNotExist() {
    String[] cmdAndParam = {"cp", "oldPath", "newPath"};
    assertEquals("Error", copy.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testCopyItemToItself() {
    String[] cmdAndParam = {"mkdir", "directory"};
    String[] cmdAndParam1 = {"cp", "directory", "directory"};
    mkdir.executeCommand(js, cmdAndParam);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam1));
  }

  @Test
  public void testCopyParentDirectoryToChildDirectory() {
    String[] cmdAndParam = {"mkdir", "parentDir", "parentDir/childDir"};
    String[] cmdAndParam1 = {"cp", "parentDir", "parentDir/childDir"};
    mkdir.executeCommand(js, cmdAndParam);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam1));
  }

  @Test
  public void testCopyDirectoryToNonExitingItem() {
    String[] cmdAndParam = {"mkdir", "directory"};
    String[] cmdAndParam1 = {"cp", "directory", "nonExisitingItem"};
    mkdir.executeCommand(js, cmdAndParam);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam1));
  }

  @Test
  public void testCopyDirectoryToFile() {
    String[] cmdAndParam = {"mkdir", "directory"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam2 = {"cp", "directory", "file"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testCopyDirectoryToInvalidPath() {
    String[] cmdAndParam = {"mkdir", "directory"};
    String[] cmdAndParam1 = {"cp", "directory", "/invalid/path"};
    mkdir.executeCommand(js, cmdAndParam);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam1));
  }

  @Test
  public void testCopyDirectoryWithChildToExistingDirectory() {
    Directory dir = new Directory("oldDir");
    dir.addDirectoryInside("childDir");
    dir.getChildDirectory("childDir").addDirectoryInside("grandchildDir");
    dir.addFileInside("childFile");
    dir.getChildFile("childFile").setFileContent("content");
    String[] cmdAndParam = {"mkdir", "oldDir", "oldDir/childDir",
        "oldDir/childDir/grandchildDir", "newDir"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "oldDir/childFile"};
    String[] cmdAndParam2 = {"cp", "oldDir", "newDir"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    copy.executeCommand(js, cmdAndParam2);
    assertEquals(dir, js.getCurrDirObj().getCurrDir().
    		getChildDirectory("newDir").getChildDirectory("oldDir"));
  }

  @Test
  public void testCopyDirectoryToExistingFile() {
    String[] cmdAndParam = {"mkdir", "directory"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam2 = {"cp", "directory", "file"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testCopyDirToExistingDirContainingDirWithSameName() {
    String[] cmdAndParam = {"mkdir", "oldDir", "newDir", "newDir/oldDir"};
    String[] cmdAndParam1 = {"cp", "oldDir", "newDir"};
    mkdir.executeCommand(js, cmdAndParam);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam1));
  }

  @Test
  public void testCopyDirToExistingDirContainingFileWithSameName() {
    String[] cmdAndParam = {"mkdir", "oldDir", "newDir"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "newDir/oldDir"};
    String[] cmdAndParam2 = {"cp", "oldDir", "newDir"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testCopyFileToExistingDirectory() {
    String[] cmdAndParam = {"mkdir", "directory"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam2 = {"cp", "file", "directory"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    copy.executeCommand(js, cmdAndParam2);
    assertEquals(js.getCurrDirObj().getCurrDir().getChildFile("file"),
        js.getCurrDirObj().getCurrDir().getChildDirectory("directory")
            .getChildFile("file"));
  }

  @Test
  public void testCopyFileToExistingFile() {
    String[] cmdAndParam = {"echo", "\"old content\"", ">", "oldFile"};
    String[] cmdAndParam1 = {"echo", "\"new content\"", ">", "newFile"};
    String[] cmdAndParam2 = {"cp", "oldFile", "newFile"};
    echo.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    copy.executeCommand(js, cmdAndParam2);
    assertEquals("old content", js.getCurrDirObj().getCurrDir()
        .getChildFile("newFile").getFileContent());
  }

  @Test
  public void testCopyFileToNonExistingDirectory() {
    String[] cmdAndParam = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam1 = {"cp", "file", "nonExistingDirectory/"};
    echo.executeCommand(js, cmdAndParam);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam1));
  }

  @Test
  public void testCopyFileToNonExistingFile() {
    String[] cmdAndParam = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam1 = {"cp", "file", "newFile"};
    echo.executeCommand(js, cmdAndParam);
    copy.executeCommand(js, cmdAndParam1);
    assertEquals("content", js.getCurrDirObj().getCurrDir()
        .getChildFile("newFile").getFileContent());
  }

  @Test
  public void testCopyFileToInvalidPath() {
    String[] cmdAndParam = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam1 = {"cp", "file", "invalid/path"};
    echo.executeCommand(js, cmdAndParam);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam1));
  }

  @Test
  public void testCopyFileToExistingDirContainingDirWithSameName() {
    String[] cmdAndParam = {"mkdir", "directory", "directory/file"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam2 = {"cp", "file", "directory"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testCopyFileToExistingDirContainingFileWithSameName() {
    String[] cmdAndParam = {"mkdir", "directory"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "directory/file"};
    String[] cmdAndParam2 = {"echo", "\"content1\"", ">", "file"};
    String[] cmdAndParam3 = {"cp", "file", "directory"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    echo.executeCommand(js, cmdAndParam2);
    assertEquals("Error", copy.executeCommand(js, cmdAndParam3));
  }

}

