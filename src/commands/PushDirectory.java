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
import containers.DirectoryStack;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.UserInput;

/**
 * The PushDirectory class allows user to save directories in the directory
 * stack and changes current working directory to another directory.
 */
public class PushDirectory extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully pushes the directory, "Error" if it
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
    DirectoryStack dirStack = js.getDirStack();
    // run the command
    return pushDir(root, cmdAndParam[1], currDir, dirStack);
  }

  /**
   * This method pushes the current directory onto the directory stack then
   * changes current working directory to another directory specified by user.
   * 
   * @param root - the root directory
   * @param path - the path to the directory which method will set as the
   *        current directory
   * @param currDir - current working directory
   * @param dirStack - the directory stack
   * @return "Success" if it successfully pushes the directory, "Error" if it
   *         fails
   */
  private String pushDir(Directory root, String path, CurrentDirectory currDir,
      DirectoryStack dirStack) {
    String pathType = PathHandler.checkPath(root, currDir, path);
    // print an error message and return "Error" if path does not exist
    if (pathType == null) {
      Output.printPathDoesNotExistError(path);
      return "Error";
    }
    // print an error message and return "Error" if the path specifies a file
    else if (pathType.equals("File")) {
      Output.printExpectedDirGotFileError();
      return "Error";
    } else {
      // create a temporary variable for storing the location of the current
      // working directory which will be pushed onto the directory stack
      CurrentDirectory tempDir =
          new CurrentDirectory(currDir.getCurrDir(), currDir.getCurrPath());

      // push the temporary variable onto the directory stack
      dirStack.getStack().push(tempDir);

      // get the directory from path and set it as the current directory
      Directory newDirectory =
          PathHandler.getDirectoryFromPath(root, currDir, path);
      currDir.setCurrDir(newDirectory);
      currDir.setCurrPath(PathHandler.getFullPath(newDirectory));
      return "Success";
    }
  }
}
