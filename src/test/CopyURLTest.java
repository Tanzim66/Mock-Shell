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
import commands.Commands;
import commands.CopyURL;
import containers.CurrentDirectory;
import containers.Directory;
import containers.File;
import driver.JShell;
import system.PathHandler;

public class CopyURLTest {

  File file;
  Commands curl = new CopyURL();
  JShell js;
  Directory root;
  CurrentDirectory currDir;

  @Before
  public void setUp() {
    js = new JShell();
    root = js.getRootDirectory();
    currDir = js.getCurrDirObj();
  }

  @Test
  public void testNoArguments() {
    String[] cmdAndParam = {"curl"};
    assertEquals("Error", curl.executeCommand(js, cmdAndParam));
  }

  @Test
  public void testMoreThanOneArgument() {
    String[] cmdAndParam =
        {"curl", "http://www.brainjar.com/java/host/test.html",
            "https://www.webfx.com/blog/images/assets/cdn.sixrevisions.com/"
                + "0435-01_html5_download_attribute_demo/samp/htmldoc.html"};
    assertEquals("Error", curl.executeCommand(js, cmdAndParam));
    String fileName =
        PathHandler.editItemName(PathHandler.getItemName(cmdAndParam[1]));
    file = PathHandler.getFileFromPath(root, currDir, fileName);
    assertNull(file);
    fileName =
        PathHandler.editItemName(PathHandler.getItemName(cmdAndParam[2]));
    file = PathHandler.getFileFromPath(root, currDir, fileName);
    assertNull(file);
  }

  @Test
  public void testCopyInvalidFile() {
    String[] cmdAndParam = {"curl", "something/doesnotexist.txt"};
    assertEquals("Error", curl.executeCommand(js, cmdAndParam));
    file = PathHandler.getFileFromPath(root, currDir, cmdAndParam[1]);
    assertNull(file);
  }

  @Test
  public void testCopyValidFile() {
    String[] cmdAndParam =
        {"curl", "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt"};
    assertEquals("Success", curl.executeCommand(js, cmdAndParam));
    String fileName =
        PathHandler.editItemName(PathHandler.getItemName(cmdAndParam[1]));
    file = PathHandler.getFileFromPath(root, currDir, fileName);
    assertNotNull(file);
  }
}
