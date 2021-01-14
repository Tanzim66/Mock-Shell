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
import driver.JShell;

public class ConcatenateTest {
  Commands concatInstance = new Concatenate();
  Commands echoInstance = new Echo();
  Commands mkdirInstance = new MakeDirectory();
  JShell js = new JShell();


  @Test
  public void testExecuteCommand_NoArguments() {
    System.out.println("Tests case user inputs no arguments:");
    // no redirection
    String[] catParams = {"cat"};
    assertEquals("Error", concatInstance.executeCommand(js, catParams));

    // with redirection
    String[] catParams2 = {"cat", ">", "file"};
    assertEquals("Error", concatInstance.executeCommand(js, catParams2));
    System.out.println("\n");

  }

  @Test
  public void testExecuteCommand_OneFileThatDoesNotExist() {
    System.out.println("Tests case user inputs one path to a file,"
        + "but the file does not exist:");
    // no redirection
    String[] catParams1 = {"cat", "path"};
    assertEquals("Error", concatInstance.executeCommand(js, catParams1));

    // with redirection
    String[] catParams2 = {"cat", "path2", ">", "file"};
    assertEquals("Redirection Error",
        concatInstance.executeCommand(js, catParams2));
    System.out.println("\n");
  }


  @Test
  public void testExecuteCommand_OneFileThatDoesExist() {
    System.out
        .println("Tests case user inputs one" + " path to a file that exists:");
    // make a new file
    String[] echoParams = {"echo", "\"This file exists1\"", ">", "newfile1"};
    echoInstance.executeCommand(js, echoParams);

    // concat the file
    String[] catParams = {"cat", "newfile1"};
    String result = concatInstance.executeCommand(js, catParams);

    assertEquals("This file exists1", result);
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_MultipleFilesThatExist() {
    System.out.println("Tests case user inputs multiple paths to files, all"
        + "of which exist:");
    // make three new files
    String[] echoParams = {"echo", "\"This file exists2\"", ">", "newfile2"};
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists3\"";
    echoParams[3] = "newfile3";
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists4\"";
    echoParams[3] = "newfile4";
    echoInstance.executeCommand(js, echoParams);

    String[] catParams = {"cat", "newfile2", "newfile3", "newfile4"};
    String result = concatInstance.executeCommand(js, catParams);

    assertEquals(
        "This file exists2\n\n\n\nThis file exists3\n\n\n\nThis file exists4",
        result);
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_MultipleFilesExistButOneDoesNotExist() {
    System.out.println("Tests case user inputs multiple paths to files, one "
        + "of them does not exist:");
    String[] echoParams = {"echo", "\"This file exists5\"", ">", "newfile5"};
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists6\"";
    echoParams[3] = "newfile6";
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists7\"";
    echoParams[3] = "newfile7";
    echoInstance.executeCommand(js, echoParams);

    // newfile8 does not exist
    String[] catParams =
        {"cat", "newfile5", "newfile6", "newfile8", "newfile7"};
    String result = concatInstance.executeCommand(js, catParams);

    assertEquals("This file exists5\n\n\n\nThis file exists6", result);
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_MultipleFilesExistWithFullPath() {
    System.out.println("Tests case user inputs multiple full paths to files, "
        + "all of them exist:");
    // Make directories
    String[] mkdirParams = {"mkdir", "/a", "/a/b", "/a/b/c", "/a/b/c/d"};
    mkdirInstance.executeCommand(js, mkdirParams);

    // Make 3 folders inside the directories
    String[] echoParams = {"echo", "\"This file exists8\"", ">", "a/newfile8"};
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists9\"";
    echoParams[3] = "/a/b/newfile9";
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists10\"";
    echoParams[3] = "/a/b/c/d/newfile10";
    echoInstance.executeCommand(js, echoParams);

    // concat the files using their full paths
    String[] catParams =
        {"cat", "/a/newfile8", "/a/b/newfile9", "/a/b/c/d/newfile10"};
    String result = concatInstance.executeCommand(js, catParams);

    assertEquals(
        "This file exists8\n\n\n\nThis file exists9\n\n\n\nThis file exists10",
        result);
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_MultipleParametersAfterRedirection() {
    System.out.println("Tests case user inputs more than one argument after "
        + "redirection symbol:");
    String[] catParams = {"cat", "param", ">", "one", "two"};
    assertEquals("Error", concatInstance.executeCommand(js, catParams));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_NoArgumentsAfterRedirection() {
    System.out.println("Tests case user inputs more than one argument after "
        + "redirection symbol:");

    String[] echoParams = {"echo", "\"This is a new file\"", ">", "tempFile"};
    echoInstance.executeCommand(js, echoParams);

    String[] catParams = {"cat", "tempFile", ">"};

    assertEquals("Error", concatInstance.executeCommand(js, catParams));
    System.out.println("\n");
  }

  @Test
  public void testExecuteCommand_RedirectMultipleFilesWithAllValidPaths() {
    System.out.println("Tests case user inputs multiple paths to files, all"
        + "of which exist, and redirects output:");
    String[] echoParams = {"echo", "\"This file exists11\"", ">", "newfile11"};
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists12\"";
    echoParams[3] = "newfile12";
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists13\"";
    echoParams[3] = "newfile13";
    echoInstance.executeCommand(js, echoParams);

    String[] catParams =
        {"cat", "newfile11", "newfile12", "newfile13", ">", "superFile"};
    String result = concatInstance.executeCommand(js, catParams);

    String[] catParams2 = {"cat", "superFile"};
    String superFile = concatInstance.executeCommand(js, catParams2);

    assertEquals(
        "Redirection Successful: Created new file - This file exists11\n\n\n\n"
            + "This file exists12\n\n\n\nThis file exists13",
        result + " - " + superFile);
    System.out.println("\n");
  }


  @Test
  public void testExecuteCommand_RedirectMultipleFilesWithOneInvalidPath() {
    System.out.println("Tests case user inputs multiple paths to files, all"
        + "of which exist except 1, and redirects output:");
    String[] echoParams = {"echo", "\"This file exists14\"", ">", "newfile14"};
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists15\"";
    echoParams[3] = "newfile15";
    echoInstance.executeCommand(js, echoParams);
    echoParams[1] = "\"This file exists16\"";
    echoParams[3] = "newfile16";
    echoInstance.executeCommand(js, echoParams);

    // newfile8 does not exist
    String[] catParams = {"cat", "newfile14", "newfile15", "newfile17",
        "newfile16", ">", "superFile2"};
    String result = concatInstance.executeCommand(js, catParams);

    String[] catParams2 = {"cat", "superFile2"};
    String superFile = concatInstance.executeCommand(js, catParams2);

    assertEquals(
        "Redirection Successful: Created new file - "
            + "This file exists14\n\n\n\nThis file exists15",
        result + " - " + superFile);
    System.out.println("\n");
  }


}
