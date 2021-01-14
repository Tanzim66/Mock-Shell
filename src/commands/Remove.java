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
 * The Remove class removes a directory along with all its children.
 */
public class Remove extends Commands {

  /**
   * This method checks if user input is valid then proceeds to remove the
   * directory. It prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully removes the directory, "Error" if it
   *         fails
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
    // get the directory that is going to be removed
    Directory rmDir =
        PathHandler.getDirectoryFromPath(root, currDir, cmdAndParam[1]);

    String currPath = PathHandler.getFullPath(currDir.getCurrDir());
    String rmPath = PathHandler.getFullPath(rmDir);

    // print an error message and return "Error" if the directory does not exist
    if (rmDir == null) {
      Output.printPathDoesNotExistError(cmdAndParam[1]);
      return "Error";
    }
    // print an error message and return "Error" if user is trying to remove
    // current working directory
    if (currPath.indexOf(rmPath) == 0) {
      Output.printRemoveCurrentDirectoryError(cmdAndParam[1]);
      return "Error";
    }
    // remove the directory then return "Success"
    rmDir.getParentDirectory().getInnerDirectories().remove(rmDir);
    return "Success";
  }
}
