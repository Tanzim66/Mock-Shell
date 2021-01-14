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
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Commands;
import commands.Echo;
import commands.History;
import driver.JShell;
import system.FileSystem;

public class HistoryTest {
  JShell js;
  Commands history;
  Commands echo;
  ArrayList<String> recentCommands;
  FileSystem fs;



  @Before
  public void setUp() throws Exception {
    js = new JShell();
    history = new History();
    echo = new Echo();
    fs = FileSystem.createFileSystemInstance();
    recentCommands = js.getUserInputList().getRecentCommands();
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass().getDeclaredField("fs"));
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  public void testTooManyArgumentsNoRedirection() {
    String[] historyParams = {"history", "3", "4"};
    assertEquals("Error", history.executeCommand(js, historyParams));

  }

  @Test
  public void testTooManyArgumentsRedirection() {
    String[] historyParams = {"history", "3", ">", "too", "many"};
    String[] historyParams2 = {"history", "still", "too", "many"};
    assertEquals("Error", history.executeCommand(js, historyParams));
    assertEquals("Error", history.executeCommand(js, historyParams2));

  }

  @Test
  public void testRedirectionNonInteger() {
    String[] historyParams = {"history", "a", ">", "file"};
    assertEquals("Error", history.executeCommand(js, historyParams));
  }

  @Test
  public void testInvalidFileNameRedirection() {
    String[] historyParams = {"history", ">", "IN%VA@LID"};
    assertEquals("Redirection Error",
        history.executeCommand(js, historyParams));
  }

  @Test
  public void testNoArguments() {
    String[] historyParams = {"history"};
    recentCommands.add("Message1");
    recentCommands.add("Message2");
    recentCommands.add("Message3");
    recentCommands.add("history");

    String actual = history.executeCommand(js, historyParams);
    String expected =
        "1. Message1\n" + "2. Message2\n" + "3. Message3\n" + "4. history";
    assertEquals(expected, actual);
  }

  @Test
  public void testNumberArgument() {
    String[] historyParams = {"history", "2"};
    recentCommands.add("Message4");
    recentCommands.add("Message5");
    recentCommands.add("Message6");
    recentCommands.add("history 2");

    String actual = history.executeCommand(js, historyParams);
    String expected = "3. Message6\n" + "4. history 2";
    assertEquals(expected, actual);
  }

  @Test
  public void testOverwriteRedirectionNoNumber() {
    String[] echoParams = {"echo", "\"text\"", ">", "file1"};
    String[] historyParams = {"history", ">", "file1"};
    recentCommands.add("echo \"text\" > file1");
    recentCommands.add("history > file1");
    echo.executeCommand(js, echoParams);
    history.executeCommand(js, historyParams);

    String expected = "1. echo \"text\" > file1\n" + "2. history > file1";
    assertEquals(expected,
        js.getCurrDirObj().getCurrDir().getChildFile("file1").getFileContent());


  }

  @Test
  public void testOverwriteRedirectionNumber() {
    String[] echoParams = {"echo", "\"text\"", ">", "file2"};
    String[] historyParams = {"history", "2", ">", "file2"};
    recentCommands.add("echo \"text\" > file2");
    recentCommands.add("\\o/");
    recentCommands.add("abbass pls bless us with marks");
    recentCommands.add("history 2 > file2");
    echo.executeCommand(js, echoParams);
    history.executeCommand(js, historyParams);

    String expected =
        "3. abbass pls bless us with marks\n" + "4. history 2 > file2";
    assertEquals(expected,
        js.getCurrDirObj().getCurrDir().getChildFile("file2").getFileContent());

  }

  @Test
  public void testAppendRedirectionNoNumber() {
    String[] echoParams = {"echo", "\"ThisWillShow\"", ">>", "file3"};
    String[] historyParams = {"history", ">>", "file3"};
    recentCommands.add("echo \"ThisWillShow\" >> file3");
    recentCommands.add("history >> file3");
    echo.executeCommand(js, echoParams);
    history.executeCommand(js, historyParams);

    String expected = "ThisWillShow\n" + "1. echo \"ThisWillShow\" >> file3\n"
        + "2. history >> file3";
    assertEquals(expected,
        js.getCurrDirObj().getCurrDir().getChildFile("file3").getFileContent());

  }

  @Test
  public void testAppendRedirectionNumber() {
    String[] echoParams = {"echo", "\"text\"", ">>", "file4"};
    String[] historyParams = {"history", "2", ">>", "file4"};
    recentCommands.add("echo \"text\" >> file4");
    recentCommands.add("\\o/");
    recentCommands.add("abbass pls bless us with marks");
    recentCommands.add("history 2 >> file4");
    echo.executeCommand(js, echoParams);
    history.executeCommand(js, historyParams);

    String expected = "text\n" + "3. abbass pls bless us with marks\n"
        + "4. history 2 >> file4";
    assertEquals(expected,
        js.getCurrDirObj().getCurrDir().getChildFile("file4").getFileContent());
  }



}
