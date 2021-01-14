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
import system.Redirection;
import system.UserInput;

/**
 * The PrintWorkingDirectory class prints the whole path to the current working
 * directory.
 */
public class PrintWorkingDirectory extends Commands {

  /**
   * This method checks if user input is valid before obtaining the whole path
   * to the current working directory. If the user input is valid, then the
   * function will print path to the current directory. If the user requests to
   * redirect the output with the ">>" or ">" symbols, the function redirects it
   * to the file at the path that the user desires.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Error" if something bad happens, "Redirection Successful" followed
   *         by the specific task of redirection (append, overwrite or create
   *         new file) if redirection was successful after redirection was
   *         requested, or the path to the current directory that have been
   *         printed.
   */
  public String executeCommand(JShell js, String[] cmdAndParam) {

    String[] tempCmdAndParam = cmdAndParam;

    // a string for storing the path to the current working directory
    String result = "";

    Directory root = js.getRootDirectory();
    CurrentDirectory currDir = js.getCurrDirObj();

    //////////////////////////////// REDIRECTION////////////////////////////////
    int indexOfArrow = UserInput.getFirstIndexOfArrow(cmdAndParam);
    boolean redirected = false;
    if (indexOfArrow != -1) {
      redirected = true;
      if (cmdAndParam.length - 1 - indexOfArrow != 1) {
        Output.printRedirectionParameterSizeError();
        return "Error";
      }
      tempCmdAndParam =
          UserInput.getCmdAndParamWithoutArrow(cmdAndParam, indexOfArrow);
    }
    ////////////////////////////////////////////////////////////////////////////

    // print an error message and return "Error" if user input is invalid
    if (tempCmdAndParam == null || tempCmdAndParam.length != 1) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "0");
      return "Error";
    }
    // get the path to the current directory
    result = currDir.getCurrPath();

    // if redirection is requested, redirect result to a file, print the result
    // otherwise
    if (redirected) {
      String[] temp =
          {result, cmdAndParam[indexOfArrow], cmdAndParam[indexOfArrow + 1]};
      return Redirection.redirectToFile(root, temp, currDir);
    } else {
      Output.println(result);
      return result;
    }
  }
}
