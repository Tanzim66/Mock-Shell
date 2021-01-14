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
import containers.DirectoryStack;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.UserInput;

/**
 * The PopDirectory class removes the top directory from the directory stack
 * then makes it the current working directory.
 */
public class PopDirectory extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully pops the directory, "Error" if it
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
    if (cmdAndParam.length != 1) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "0");
      return "Error";
    }

    DirectoryStack dirStack = js.getDirStack();
    CurrentDirectory currDir = js.getCurrDirObj();

    // print an error message and return "Error" if the directory stack is empty
    if (dirStack.getStack().empty()) {
      Output.printEmptyStackError();
      return "Error";
    }

    // pop the top directory from the directory stack
    return popDir(currDir, dirStack);
  }

  /**
   * This method removes the top directory from the directory stack then makes
   * it the current working directory.
   * 
   * @param currDir - current working directory
   * @param dirStack - the directory stack
   * @return "Success" after popping the directory
   */
  private String popDir(CurrentDirectory currDir, DirectoryStack dirStack) {
    CurrentDirectory newDirectory = dirStack.getStack().pop();
    currDir.setCurrDir(newDirectory.getCurrDir());
    currDir.setCurrPath(PathHandler.getFullPath(newDirectory.getCurrDir()));
    return "Success";
  }
}
