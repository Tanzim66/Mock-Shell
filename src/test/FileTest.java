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
import containers.Directory;
import containers.File;

public class FileTest {

  File file;
  Directory dir;

  @Before
  public void setUp() {
    dir = new Directory("/");
    file = new File("file", dir);
  }

  @Test
  public void testGetFileName() {
    assertEquals("file", file.getFileName());
  }

  @Test
  public void testGetFileNameForNewFile() {
    file = new File("testFile", dir);
    assertEquals("testFile", file.getFileName());
  }

  @Test
  public void testSetFileName() {
    file.setFileName("helloWorld");
    assertEquals("helloWorld", file.getFileName());
  }

  @Test
  public void testGetFileContentOfEmptyFile() {
    assertEquals("", file.getFileContent());
  }

  @Test
  public void testSetAndGetFileContent() {
    file.setFileContent("testing");
    assertEquals("testing", file.getFileContent());
  }

  @Test
  public void testFileEqual() {
    File f1 = new File("sameFileName", dir);
    File f2 = new File("sameFileName", dir);
    f1.setFileContent("Same text!");
    f2.setFileContent("Same text!");
    assertEquals(f1, f2);

  }

  @Test
  public void testFileNotEqual() {
    File f1 = new File("sameFileName", dir);
    File f2 = new File("diffName", dir);
    f1.setFileContent("Same text!");
    f2.setFileContent("Same text!");
    assertNotEquals(f1, f2);
  }
}
