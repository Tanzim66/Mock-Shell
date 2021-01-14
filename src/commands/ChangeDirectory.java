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
package commands;

import containers.CurrentDirectory;
import containers.Directory;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.UserInput;

/**
 * This ChangeDirectory class changes the current working directory to another
 * directory.
 */
public class ChangeDirectory extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully changed current directory to newDir,
   *         "Error" if it fails
   */
  public String executeCommand(JShell js, String[] cmdAndParam) {

    //////////////////////////////// REDIRECTION////////////////////////////////
    int indexOfArrow = UserInput.getFirstIndexOfArrow(cmdAndParam);
    if (indexOfArrow != -1) {
      if (cmdAndParam.length - 1 - indexOfArrow != 1) {
        Output.printRedirectionParameterSizeError();
        return "Error";
      }
      cmdAndParam =
          UserInput.getCmdAndParamWithoutArrow(cmdAndParam, indexOfArrow);
    }
    ////////////////////////////////////////////////////////////////////////////

    // print an error message and return "Error" if user input is invalid

    if (cmdAndParam.length != 2) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "1");
      return "Error";
    }
    Directory root = js.getRootDirectory();
    CurrentDirectory currDir = js.getCurrDirObj();
    // execute the command
    return changeDir(root, cmdAndParam[1], currDir);
  }

  /**
   * This method changes the current directory currDir to the directory newDir.
   * 
   * @param root - the root directory
   * @param newDir - the directory which method will change current directory to
   * @param currDir - current working directory
   * @return "Success" if it successfully changed current directory to newDir,
   *         "Error" if it fails
   */
  private String changeDir(Directory root, String newDir,
      CurrentDirectory currDir) {

    Directory newDirectory =
        PathHandler.getDirectoryFromPath(root, currDir, newDir);
    if (newDirectory != null) {
      currDir.setCurrDir(newDirectory);
      currDir.setCurrPath(PathHandler.getFullPath(newDirectory));
      return "Success";
    }
    // print an error message and return "Error" if newDir does not exist
    else {
      Output.printPathDoesNotExistError(newDir);
      return "Error";
    }
  }

}
