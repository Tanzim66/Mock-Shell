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
 * The MakeDirectory class creates directories inside a directory.
 */
public class MakeDirectory extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid. Then, it makes as many
   * directories as user has inputed until it hits an invalid argument.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully creates the directories, "Error" if it
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
    if (cmdAndParam.length == 1) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "1 or more");
      return "Error";
    }

    Directory root = js.getRootDirectory();
    CurrentDirectory currDir = js.getCurrDirObj();

    // traverse all arguments to make each directory individually
    for (int i = 1; i < cmdAndParam.length; i++) {
      // stop if one fails
      if (!createDirectory(root, cmdAndParam[i], currDir)) {
        return "Error";
      }
    }
    return "Success";
  }

  /**
   * This method creates a directory in a given path.
   * 
   * @param root - the root directory
   * @param path - the path to the directory
   * @param currDir - current working directory
   * @return true if directory has successfully been created, false otherwise
   */
  private boolean createDirectory(Directory root, String path,
      CurrentDirectory currDir) {

    // get the directory name from the path
    String dirName = PathHandler.getItemName(path);

    // return false if the directory name is not valid
    if (!PathHandler.checkValidItemName(dirName)) {
      return false;
    }

    // obtain the parent directory which the new directory is to be created in
    Directory dir = PathHandler.getDirectoryBefore(root, currDir, path);

    // if the directory object does not exist or if a directory with the
    // same name already exists inside the parent directory, then print an
    // error message and return false
    if (dir == null || dir.getChildDirectory(dirName) != null) {
      Output.printCreateDirectoryError(dirName);
      return false;
    }

    // also print an error message and return false if a file with the same name
    // already exists in the parent directory
    if (dir.getChildFile(dirName) != null) {
      Output.printFileAlreadyExistsError(dirName);
      return false;
    }

    // create the new directory and return true
    dir.addDirectoryInside(dirName);
    Output.printDirectoryCreated(dirName);
    return true;
  }
}
