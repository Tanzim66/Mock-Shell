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
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import containers.Directory;
import containers.File;

public class DirectoryTest {

  Directory dir;

  @Before
  public void setUp() {
    dir = new Directory("/");
  }

  @Test
  public void testDirectoryIsEmpty() {
    assertTrue(dir.isEmpty());
  }

  @Test
  public void testGetDirectoryName() {
    assertEquals("/", dir.getDirectoryName());
  }

  @Test
  public void testGetDirectoryNameForNewDirectory() {
    dir = new Directory("newDir");
    assertEquals("newDir", dir.getDirectoryName());
  }

  @Test
  public void testSetDirectoryName() {
    dir.setDirectoryName("helloWorld");
    assertEquals("helloWorld", dir.getDirectoryName());
  }

  @Test
  public void testAddDirectoryInside() {
    dir.addDirectoryInside("newDir");
    assertFalse(dir.isEmpty());
  }

  @Test
  public void testAddFileInside() {
    dir.addFileInside("newFile");
    assertFalse(dir.isEmpty());
  }

  @Test
  public void testGetInnerDirectories() {
    dir.addDirectoryInside("newDir1");
    dir.addDirectoryInside("newDir2");
    dir.addDirectoryInside("newDir3");
    ArrayList<Directory> innerDirectories = new ArrayList<>();
    innerDirectories.add(new Directory("newDir1"));
    innerDirectories.add(new Directory("newDir2"));
    innerDirectories.add(new Directory("newDir3"));
    assertEquals(innerDirectories, dir.getInnerDirectories());
  }

  @Test
  public void testGetInnerFiles() {
    dir.addFileInside("newFile1");
    dir.addFileInside("newFile2");
    dir.addFileInside("newFile3");
    Directory dir2 = new Directory("/");
    ArrayList<File> innerFiles = new ArrayList<>();
    innerFiles.add(new File("newFile1", dir2));
    innerFiles.add(new File("newFile2", dir2));
    innerFiles.add(new File("newFile3", dir2));
    assertEquals(innerFiles, dir.getInnerFiles());
  }

  @Test
  public void testGetChildDirectory() {
    dir.addDirectoryInside("newDir");
    Directory newDir = dir.getInnerDirectories().get(0);
    assertEquals(newDir, dir.getChildDirectory("newDir"));
  }

  @Test
  public void testGetChildFile() {
    dir.addFileInside("newFile");
    File newFile = dir.getInnerFiles().get(0);
    assertEquals(newFile, dir.getChildFile("newFile"));
  }

  @Test
  public void testGetParentDirectory() {
    dir.addDirectoryInside("newDir");
    assertEquals(dir, dir.getChildDirectory("newDir").getParentDirectory());
  }

  @Test
  public void testDirectoryEqual() {
    Directory d1 = new Directory("/");
    Directory d2 = new Directory("/");
    d1.addDirectoryInside("CoolDir");
    d2.addDirectoryInside("CoolDir");
    d1.addFileInside("CoolFile");
    d2.addFileInside("CoolFile");
    assertEquals(d1, d2);
  }

  @Test
  public void testDirectoryNotEqual() {
    Directory d1 = new Directory("/");
    Directory d2 = new Directory("/");
    d1.addDirectoryInside("CoolDir");
    d2.addDirectoryInside("NotCoolDir");
    d1.addFileInside("CoolFile");
    d2.addFileInside("CoolFile");
    assertNotEquals(d1, d2);

  }
}
