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
import commands.Echo;
import commands.MakeDirectory;
import commands.Tree;
import system.FileSystem;
import driver.JShell;

public class TreeTest {

  Commands mkdir;
  Commands echo;
  Commands tree;
  JShell js;
  FileSystem fs;

  @Before
  public void setUp() {
    js = new JShell();
    mkdir = new MakeDirectory();
    echo = new Echo();
    tree = new Tree();
    fs = FileSystem.createFileSystemInstance();
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass().getDeclaredField("fs"));
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  public void testTooManyArguments() {
    String[] cmdAndParam = {"tree", "argument"};
    assertEquals("Error", tree.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testTreeWithOnlyRoot() {
    String[] cmdAndParam = {"tree"};
    assertEquals("/\n", tree.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testTreeWithOneDirectoryAndOneFile() {
    String[] cmdAndParam = {"mkdir", "dir"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam2 = {"tree"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    assertEquals("/\n\tdir\n\tfile\n", tree.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testTreeWithMultipleChild() {
    String[] cmdAndParam = {"mkdir", "dir", "dir/dir1", "dir2"};
    String[] cmdAndParam1 = {"echo", "\"content\"", ">", "dir/file"};
    String[] cmdAndParam2 = {"tree"};
    mkdir.executeCommand(js, cmdAndParam);
    echo.executeCommand(js, cmdAndParam1);
    assertEquals("/\n\tdir\n\t\tdir1\n\t\tfile\n\tdir2\n",
        tree.executeCommand(js, cmdAndParam2));
  }

  @Test
  public void testRedirectionWithTooManyArguments() {
    String[] cmdAndParam = {"tree", ">", "random argument", "file"};
    assertEquals("Error", tree.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testRedirectionWithInvalidArrowArgument() {
    String[] cmdAndParam = {"tree", ">>>", "file"};
    assertEquals("Error", tree.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testRedirectToFileWithInvalidName() {
    String[] cmdAndParam = {"tree", ">", "IN%VA@LID"};
    assertEquals("Redirection Error", tree.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testRedirectTreeToNewFile() {
    String[] cmdAndParam = {"tree", ">", "file"};
    tree.executeCommand(js, cmdAndParam);
    assertEquals("/\n",
        js.getCurrDirObj().getCurrDir().getChildFile("file").getFileContent());
  }

  @Test
  public void testRedirectTreeToExistingFileByOverwrite() {
    String[] cmdAndParam = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam1 = {"tree", ">", "file"};
    echo.executeCommand(js, cmdAndParam);
    tree.executeCommand(js, cmdAndParam1);
    assertEquals("/\n\tfile\n",
        js.getCurrDirObj().getCurrDir().getChildFile("file").getFileContent());
  }

  @Test
  public void testRedirectTreeToExistingFileByAppend() {
    String[] cmdAndParam = {"echo", "\"content\"", ">", "file"};
    String[] cmdAndParam1 = {"tree", ">>", "file"};
    echo.executeCommand(js, cmdAndParam);
    tree.executeCommand(js, cmdAndParam1);
    assertEquals("content\n/\n\tfile\n",
        js.getCurrDirObj().getCurrDir().getChildFile("file").getFileContent());
  }

  @Test
  public void testRedirectTreeToNewFileThenAppendNewTreeToIt() {
    String[] cmdAndParam = {"tree", ">>", "file"};
    tree.executeCommand(js, cmdAndParam);
    tree.executeCommand(js, cmdAndParam);
    assertEquals("/\n\n/\n\tfile\n",
        js.getCurrDirObj().getCurrDir().getChildFile("file").getFileContent());
  }
}
