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
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import commands.Commands;
import commands.SaveJShell;
import driver.JShell;

public class SaveJShellTest {

  Commands save;
  File file;
  JShell js;


  @Before
  public void setUp() {
    js = new JShell();
    save = new SaveJShell();
  }

  /*
   * Should not run due to not enough arguments.
   */
  @Test
  public void testTooFewArguments() {
    String[] saveParams = {"save"};
    assertEquals("Error", save.executeCommand(js, saveParams));
  }

  /*
   * Should not run due to too many arguments.
   */
  @Test
  public void testTooManyArguments() {
    String[] saveParams = {"save", "too", "many", "arguments"};
    assertEquals("Error", save.executeCommand(js, saveParams));

  }

  /*
   * Should not run due to the file name having an invalid character "."
   */
  @Test
  public void testInvalidFileName() {
    String[] saveParams = {"save", "invalidFile.Name"};
    assertEquals("Error", save.executeCommand(js, saveParams));

  }

  /*
   * Checks if the file has been saved on your computer with the correct name
   */
  @Test
  public void testValidFileNameAndLocation() {
    String[] saveParams = {"save", "testFile"};
    save.executeCommand(js, saveParams);
    file = new File("testFile");
    assertEquals(true, file.isFile());

  }


}
