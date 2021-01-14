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
import driver.JShell;
import system.FileSystem;

public class FileSystemTest {
  FileSystem fs;
  JShell js1;
  JShell js2;

  @Before
  public void setUp() throws Exception {
    js1 = new JShell();
    js2 = new JShell();
    fs = FileSystem.createFileSystemInstance();
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass().getDeclaredField("fs"));
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  public void testSimpletonDesign() {
    assertEquals(js1.getFileSystem(), js2.getFileSystem());
  }

}
