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
import commands.MakeDirectory;
import commands.Search;
import driver.JShell;

public class SearchTest {

  Commands searchInstance = new Search();
  Commands mkdirInstance = new MakeDirectory();
  Commands echoInstance = new Echo();
  Commands catInstance = new Concatenate();
  JShell js = new JShell();

  @Test
  public void testExecuteCommand_InvalidArgumentSize() {

    System.out.println("Tests for invalid arguments:");

    // Without redirection
    String[] searchParams1 = {"search", "path"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams1));
    String[] searchParams2 = {"search", "path", "-type"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams2));
    String[] searchParams3 = {"search", "path", "-type", "d"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams3));
    String[] searchParams4 = {"search", "path", "-type", "d", "-name"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams4));
    String[] searchParams5 =
        {"search", "path", "-type", "d", "-name", "test", "test"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams5));

    // With Redirection
    String[] searchParams6 = {"search", "path", ">", "file"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams6));
    String[] searchParams7 = {"search", "path", "-type", ">", "file"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams7));
    String[] searchParams8 = {"search", "path", "-type", "d", ">", "file"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams8));
    String[] searchParams9 =
        {"search", "path", "-type", "d", "-name", ">", "file"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams9));
    String[] searchParams10 =
        {"search", "path", "-type", "d", "-name", "test", "test", ">", "file"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams10));

    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_TypeParameterMissing() {
    // -type is not part of the params
    System.out.println("Tests for missing type param:");

    // without redirection
    String[] searchParams =
        {"search", "path", "not-type", "d", "-name", "expression"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams));

    // with redirection
    String[] searchParams1 =
        {"search", "path", "not-type", "d", "-name", "expression", ">", "file"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams1));

    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_NameParameterMissing() {
    // -name is not part of the params
    System.out.println("Tests for missing name param:");

    // without redirection
    String[] searchParams =
        {"search", "path", "-type", "d", "not-name", "expression"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams));

    // with redirection
    String[] searchParams1 =
        {"search", "path", "-type", "d", "not-name", "expression", ">", "file"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams1));

    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_InvalidItemType() {
    // p is not a valid item type
    System.out.println("Tests for invalid item type:");
    String[] searchParams =
        {"search", "path", "-type", "p", "-name", "expression"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_ExpressionNotInQuotes() {
    System.out.println("Tests for expression not in quotes:");
    String[] searchParams =
        {"search", "path", "-type", "d", "-name", "expression"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams));
    String[] searchParams2 =
        {"search", "path", "-type", "d", "-name", "\"express\"ion"};
    assertEquals("Error", searchInstance.executeCommand(js, searchParams2));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_SearchForDirectory() {
    System.out.println("Tests for searching directories:");
    String[] mkdirParams =
        {"mkdir", "b", "b/Destination", "/a", "/a/b", "/a/b/Destination",
            "/a/b/c", "/a/b/d", "/a/b/c/Destination", "/a/b/d/Destination"};
    mkdirInstance.executeCommand(js, mkdirParams);

    // test with full path
    String[] searchParams1 =
        {"search", "/a", "-type", "d", "-name", "\"Destination\""};
    assertEquals("/a/b/Destination\n/a/b/c/Destination\n/a/b/d/Destination\n",
        searchInstance.executeCommand(js, searchParams1));
    // test with relative path
    String[] searchParams2 =
        {"search", "a", "-type", "d", "-name", "\"Destination\""};
    assertEquals("/a/b/Destination\n/a/b/c/Destination\n/a/b/d/Destination\n",
        searchInstance.executeCommand(js, searchParams2));
    // test with multiple paths
    String[] searchParams3 =
        {"search", "a", "b", "-type", "d", "-name", "\"Destination\""};
    assertEquals(
        "/a/b/Destination\n/a/b/c/Destination\n/a/b/d/Destination\n/b/Destination\n",
        searchInstance.executeCommand(js, searchParams3));
    // item cannot be found
    String[] searchParams4 =
        {"search", "a", "b", "-type", "d", "-name", "\"Dest\""};
    assertEquals("", searchInstance.executeCommand(js, searchParams4));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_SearchForFile() {
    System.out.println("Tests for searching files:");
    String[] mkdirParams =
        {"mkdir", "b2", "/a2", "/a2/b2", "/a2/b2/c2", "/a2/b2/d2"};
    mkdirInstance.executeCommand(js, mkdirParams);
    String[] echoParams1 = {"echo", "\"file\"", ">", "/a2/b2/Destination"};
    echoInstance.executeCommand(js, echoParams1);
    String[] echoParams2 = {"echo", "\"file\"", ">", "/a2/b2/c2/Destination"};
    echoInstance.executeCommand(js, echoParams2);
    String[] echoParams3 = {"echo", "\"file\"", ">", "/a2/b2/d2/Destination"};
    echoInstance.executeCommand(js, echoParams3);
    String[] echoParams4 = {"echo", "\"file\"", ">", "/b2/Destination"};
    echoInstance.executeCommand(js, echoParams4);

    // test with full path
    String[] searchParams1 =
        {"search", "/a2", "-type", "f", "-name", "\"Destination\""};
    assertEquals(
        "/a2/b2/Destination\n/a2/b2/c2/Destination\n"
            + "/a2/b2/d2/Destination\n",
        searchInstance.executeCommand(js, searchParams1));
    // test with relative path
    String[] searchParams2 =
        {"search", "a2", "-type", "f", "-name", "\"Destination\""};
    assertEquals(
        "/a2/b2/Destination\n/a2/b2/c2/Destination\n"
            + "/a2/b2/d2/Destination\n",
        searchInstance.executeCommand(js, searchParams2));
    // test with multiple paths
    String[] searchParams3 =
        {"search", "a2", "b2", "-type", "f", "-name", "\"Destination\""};
    assertEquals(
        "/a2/b2/Destination\n/a2/b2/c2/Destination\n/a2/b2/d2/Destination\n"
            + "/b2/Destination\n",
        searchInstance.executeCommand(js, searchParams3));
    // item cannot be found
    String[] searchParams4 =
        {"search", "a2", "b2", "-type", "f", "-name", "\"Dest\""};
    assertEquals("", searchInstance.executeCommand(js, searchParams4));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_RedirectToNewFile() {
    System.out.println("Tests for redirection of search to create a new file:");
    String[] mkdirParams = {"mkdir", "/a3", "/a3/b3", "/a3/b3/c3"};
    mkdirInstance.executeCommand(js, mkdirParams);

    String[] searchParams =
        {"search", "/", "-type", "d", "-name", "\"c3\"", ">", "/file"};
    String searchResult = searchInstance.executeCommand(js, searchParams);
    String[] catParams = {"cat", "/file"};
    String catResult = catInstance.executeCommand(js, catParams);

    assertEquals("Redirection Successful: Created new file - /a3/b3/c3\n",
        searchResult + " - " + catResult);

  }

  @Test
  public void testExecuteCommand_RedirectOverWriteExistingFile() {
    System.out.println("Tests for redirection of search to overwrite a file:");
    String[] mkdirParams = {"mkdir", "/a4", "/a4/b4", "/a4/b4/c4"};
    mkdirInstance.executeCommand(js, mkdirParams);

    String[] echoParams = {"echo", "\"Temp file\"", ">", "/file2"};
    echoInstance.executeCommand(js, echoParams);

    String[] searchParams =
        {"search", "/", "-type", "d", "-name", "\"c4\"", ">", "/file2"};
    String searchResult = searchInstance.executeCommand(js, searchParams);
    String[] catParams = {"cat", "/file2"};
    String catResult = catInstance.executeCommand(js, catParams);

    assertEquals(
        "Redirection Successful: Existing file overwritten - " + "/a4/b4/c4\n",
        searchResult + " - " + catResult);

  }

  @Test
  public void testExecuteCommand_RedirectAppendExistingFile() {
    System.out.println("Tests for redirection of search to append a file:");
    String[] mkdirParams = {"mkdir", "/a5", "/a5/b5", "/a5/b5/c5"};
    mkdirInstance.executeCommand(js, mkdirParams);

    String[] echoParams = {"echo", "\"Temp file\"", ">", "/file3"};
    echoInstance.executeCommand(js, echoParams);

    String[] searchParams =
        {"search", "/", "-type", "d", "-name", "\"c5\"", ">>", "/file3"};
    String searchResult = searchInstance.executeCommand(js, searchParams);
    String[] catParams = {"cat", "/file3"};
    String catResult = catInstance.executeCommand(js, catParams);

    assertEquals("Redirection Successful: Existing file appended - "
        + "Temp file\n/a5/b5/c5\n", searchResult + " - " + catResult);

  }

}
