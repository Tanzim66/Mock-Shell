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
import commands.Echo;
import driver.JShell;

public class EchoTest {

  Commands echo = new Echo();
  Commands concat = new Concatenate();
  JShell js = new JShell();

  @Test
  public void testExecuteCommand_MoreThanFourArguments() {
    System.out.println("Tests case user inputs more than 4 arguments:");
    String[] echoParams = {"echo", "\"text\"", ">", "one", "two"};
    assertEquals("Error", echo.executeCommand(js, echoParams));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_NoArguments() {
    System.out.println("Tests case user does not input an argument:");
    String[] echoParams = {"echo"};
    assertEquals("Error", echo.executeCommand(js, echoParams));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_ExpressionHasNoQuotes() {
    System.out.println("Tests case user does not have quotes in expression:");
    String[] echoParams = {"echo", "text"};
    assertEquals("Error", echo.executeCommand(js, echoParams));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_ExpressionIsNotSurroundedByQuotes() {
    System.out.println("Tests case user does not incase expression in quotes:");
    String[] echoParams = {"echo", "\"tex\"t"};
    assertEquals("Error", echo.executeCommand(js, echoParams));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_OnlyOneArgumentWithExpression() {
    System.out.println(
        "Tests case user inputs only one argument with an" + "expression:");
    String[] echoParams = {"echo", "\"This should be returned back\""};
    assertEquals("This should be returned back",
        echo.executeCommand(js, echoParams));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_CreateNewFile() {
    System.out.println("Tests case user creates a file:");
    String[] echoParams =
        {"echo", "\"This should not be returned back\"", ">", "name"};
    String[] catParams = {"cat", "name"};

    String echoResult = echo.executeCommand(js, echoParams);
    String concatResult = concat.executeCommand(js, catParams);

    assertEquals(
        "Redirection Successful: Created new file - "
            + "This should not be returned back",
        echoResult + " - " + concatResult);
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_OverwriteExistingFile() {
    System.out.println("Tests case user overwrites an existing file:");
    String[] echoParams1 =
        {"echo", "\"This should not be returned back\"", ">", "newFile"};
    String[] echoParams2 =
        {"echo", "\"This a different file\"", ">", "newFile"};
    String[] catParams = {"cat", "newFile"};

    echo.executeCommand(js, echoParams1);
    String echoResult = echo.executeCommand(js, echoParams2);
    String concatResult = concat.executeCommand(js, catParams);

    assertEquals("Redirection Successful: Existing file overwritten - "
        + "This a different file", echoResult + " - " + concatResult);
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_AppendExistingFile() {
    System.out.println("Tests case user appends to an existing file:");
    String[] echoParams1 =
        {"echo", "\"This should bot be returned back\"", ">", "newFile2"};
    String[] echoParams2 =
        {"echo", "\"This a different file\"", ">>", "newFile2"};
    String[] catParams = {"cat", "newFile2"};

    echo.executeCommand(js, echoParams1);
    String echoResult = echo.executeCommand(js, echoParams2);
    String concatResult = concat.executeCommand(js, catParams);

    assertEquals("Redirection Successful: "
        + "Existing file appended - This should bot be returned back\n"
        + "This a different file", echoResult + " - " + concatResult);
    System.out.println("\n");
  }

}
