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
import containers.CurrentDirectory;
import containers.Directory;
import containers.File;
import system.PathHandler;

public class PathHandlerTest {
  File file;
  Directory dir;
  Directory root;
  CurrentDirectory currDir;

  @Before
  public void setUp() {
    root = new Directory("/");
    currDir = new CurrentDirectory(root, "/");
  }

  @Test
  public void testGetFullPathOfRootDirectory() {
    assertEquals("/", PathHandler.getFullPath(root));
  }

  @Test
  public void testGetFullPathOfDirectoryInRootDirectory() {
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    assertEquals("/testDir", PathHandler.getFullPath(dir));
  }

  @Test
  public void testGetFullPathOfDirectoryInMultipleLevelDirectory() {
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir1");
    dir = dir.getChildDirectory("innerDir1");
    dir.addDirectoryInside("innerDir2");
    dir = dir.getChildDirectory("innerDir2");
    assertEquals("/testDir/innerDir1/innerDir2", PathHandler.getFullPath(dir));
  }

  @Test
  public void testGetFullPathOfFileInRootDirectory() {
    root.addFileInside("file");
    file = root.getChildFile("file");
    assertEquals("/file", PathHandler.getFullPath(file));
  }

  @Test
  public void testGetFullPathOfFileInMultipleLevelDirectory() {
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    file = dir.getChildFile("file");
    assertEquals("/testDir/innerDir/file", PathHandler.getFullPath(file));
  }

  @Test
  public void testGetItemNameOfEmptyPath() {
    String path = "";
    assertEquals("", PathHandler.getItemName(path));
  }

  @Test
  public void testGetItemNameOfRootPath() {
    String path = "/";
    assertEquals("/", PathHandler.getItemName(path));
  }

  @Test
  public void testGetItemNameOfAGivenPath() {
    String path = "/hello/itemName";
    assertEquals("itemName", PathHandler.getItemName(path));
  }

  @Test
  public void testCheckPathGivenEmptyPath() {
    String path = "";
    assertEquals("Directory", PathHandler.checkPath(root, currDir, path));
  }

  @Test
  public void testCheckPathGivenRootPath() {
    String path = "/";
    assertEquals("Directory", PathHandler.checkPath(root, currDir, path));
  }

  @Test
  public void testCheckPathGivenDirectoryPath() {
    String path = "/testDir/innerDir1/innerDir2";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir1");
    dir = dir.getChildDirectory("innerDir1");
    dir.addDirectoryInside("innerDir2");
    assertEquals("Directory", PathHandler.checkPath(root, currDir, path));
  }

  @Test
  public void testCheckPathGivenFilePath() {
    String path = "/testDir/innerDir/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    assertEquals("File", PathHandler.checkPath(root, currDir, path));
  }

  @Test
  public void testCheckPathGivenInvalidPath1() {
    String path = "/testDir/innerDir2";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir1");
    dir = dir.getChildDirectory("innerDir1");
    dir.addDirectoryInside("innerDir2");
    assertNull(PathHandler.checkPath(root, currDir, path));
  }

  @Test
  public void testCheckPathGivenInvalidPath2() {
    String path = "/testDir/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    assertNull(PathHandler.checkPath(root, currDir, path));
  }

  @Test
  public void testGetDirectoryBeforeGivenRootPath() {
    String path = "/";
    assertEquals(root, PathHandler.getDirectoryBefore(root, currDir, path));
  }

  @Test
  public void testGetDirectoryBeforeGivenDirectoryPath() {
    String path = "/testDir/innerDir1/innerDir2";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir1");
    Directory dirBefore = dir.getChildDirectory("innerDir1");
    dirBefore.addDirectoryInside("innerDir2");
    assertEquals(dirBefore,
        PathHandler.getDirectoryBefore(root, currDir, path));
  }

  @Test
  public void testGetDirectoryBeforeGivenRelativeDirectoryPath() {
    String path = "innerDir1/../innerDir1/innerDir2";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    currDir.setCurrDir(dir);
    currDir.setCurrPath(PathHandler.getFullPath(dir));
    dir.addDirectoryInside("innerDir1");
    Directory dirBefore = dir.getChildDirectory("innerDir1");
    dirBefore.addDirectoryInside("innerDir2");
    assertEquals(dirBefore,
        PathHandler.getDirectoryBefore(root, currDir, path));
  }

  @Test
  public void testGetDirectoryBeforeGivenFilePath() {
    String path = "/testDir/innerDir/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    Directory dirBefore = dir.getChildDirectory("innerDir");
    dirBefore.addFileInside("file");
    assertEquals(dirBefore,
        PathHandler.getDirectoryBefore(root, currDir, path));
  }

  @Test
  public void testGetDirectoryBeforeGivenRelativeFilePath() {
    String path = "innerDir/../innerDir/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    currDir.setCurrDir(dir);
    currDir.setCurrPath(PathHandler.getFullPath(dir));
    dir.addDirectoryInside("innerDir");
    Directory dirBefore = dir.getChildDirectory("innerDir");
    dirBefore.addFileInside("file");
    assertEquals(dirBefore,
        PathHandler.getDirectoryBefore(root, currDir, path));
  }

  @Test
  public void testGetDirectoryBeforeGivenInvalidPath1() {
    String path = "/testDir/innerDir/invalidDir/dirName";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    assertNull(PathHandler.getDirectoryBefore(root, currDir, path));
  }

  @Test
  public void testGetDirectoryBeforeGivenInvalidPath2() {
    String path = "/testDir/innerDir/invalidDir/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    assertNull(PathHandler.getDirectoryBefore(root, currDir, path));
  }

  @Test
  public void testGetDirectoryBeforeGivenInvalidPath3() {
    String path = "/testDir/innerDir/file/tryingtoaccessfile";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    assertNull(PathHandler.getDirectoryBefore(root, currDir, path));
  }

  @Test
  public void testGetDirectoryGivenRootPath() {
    String path = "/";
    assertEquals(root, PathHandler.getDirectoryFromPath(root, currDir, path));
  }

  @Test
  public void testGetDirectoryGivenDirectoryPath() {
    String path = "/testDir/innerDir1/innerDir2";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir1");
    dir = dir.getChildDirectory("innerDir1");
    dir.addDirectoryInside("innerDir2");
    dir = dir.getChildDirectory("innerDir2");
    assertEquals(dir, PathHandler.getDirectoryFromPath(root, currDir, path));
  }

  @Test
  public void testGetDirectoryGivenRelativeDirectoryPath() {
    String path = "innerDir1/innerDir2/../innerDir2";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    currDir.setCurrDir(dir);
    currDir.setCurrPath(PathHandler.getFullPath(dir));
    dir.addDirectoryInside("innerDir1");
    dir = dir.getChildDirectory("innerDir1");
    dir.addDirectoryInside("innerDir2");
    dir = dir.getChildDirectory("innerDir2");
    assertEquals(dir, PathHandler.getDirectoryFromPath(root, currDir, path));
  }

  @Test
  public void testGetDirectoryGivenInvalidDirectoryPath() {
    String path = "/testDir/innerDir2";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir1");
    dir = dir.getChildDirectory("innerDir1");
    dir.addDirectoryInside("innerDir2");
    assertNull(PathHandler.getDirectoryFromPath(root, currDir, path));
  }

  @Test
  public void testGetDirectoryGivenInvalidDirectoryPath2() {
    String path = "/testDir/innerDir1/innerDir2/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir1");
    dir = dir.getChildDirectory("innerDir1");
    dir.addDirectoryInside("innerDir2");
    dir = dir.getChildDirectory("innerDir2");
    dir.addFileInside("file");
    assertNull(PathHandler.getDirectoryFromPath(root, currDir, path));
  }

  @Test
  public void testGetFileFromGivenFilePath() {
    String path = "/testDir/innerDir/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    file = dir.getChildFile("file");
    assertEquals(file, PathHandler.getFileFromPath(root, currDir, path));
  }

  @Test
  public void testGetFileFromGivenRelativeFilePath() {
    String path = "innerDir/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    currDir.setCurrDir(dir);
    currDir.setCurrPath(PathHandler.getFullPath(dir));
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    file = dir.getChildFile("file");
    assertEquals(file, PathHandler.getFileFromPath(root, currDir, path));
  }

  @Test
  public void testGetFileGivenInvalidFilePath() {
    String path = "/testDir/file";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    assertNull(PathHandler.getFileFromPath(root, currDir, path));
  }

  @Test
  public void testGetFileGivenInvalidFilePath2() {
    String path = "/testDir/innerDir";
    root.addDirectoryInside("testDir");
    dir = root.getChildDirectory("testDir");
    dir.addDirectoryInside("innerDir");
    dir = dir.getChildDirectory("innerDir");
    dir.addFileInside("file");
    assertNull(PathHandler.getFileFromPath(root, currDir, path));
  }

  @Test
  public void testCheckValidItemNameGivenInvalidName() {
    String name = "hello.world";
    assertFalse(PathHandler.checkValidItemName(name));
  }

  @Test
  public void testCheckValidItemNameGivenInvalidName2() {
    String name = "helloworld!";
    assertFalse(PathHandler.checkValidItemName(name));
  }

  @Test
  public void testCheckValidItemNameGivenInvalidName3() {
    String name = "hello.world?";
    assertFalse(PathHandler.checkValidItemName(name));
  }

  @Test
  public void testCheckValidItemNameGivenValidName() {
    String name = "helloworld";
    assertTrue(PathHandler.checkValidItemName(name));
  }

  @Test
  public void testEditItemNameGivenInvalidName() {
    String name = "hello.world";
    String expected = "helloworld";
    assertEquals(expected, PathHandler.editItemName(name));
  }

  @Test
  public void testEditItemNameGivenInvalidName2() {
    String name = "helloworld?";
    String expected = "helloworld";
    assertEquals(expected, PathHandler.editItemName(name));
  }

  @Test
  public void testEditItemNameGivenInvalidName3() {
    String name = "hello.world?";
    String expected = "helloworld";
    assertEquals(expected, PathHandler.editItemName(name));
  }

  @Test
  public void testEditItemNameGivenValidName() {
    String name = "helloworld";
    String expected = "helloworld";
    assertEquals(expected, PathHandler.editItemName(name));
  }

  @Test
  public void testEditItemNameGivenValidName2() {
    String name = "goodbyeworld";
    String expected = "goodbyeworld";
    assertEquals(expected, PathHandler.editItemName(name));
  }
}
