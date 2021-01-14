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
import containers.File;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.Redirection;
import system.UserInput;

/**
 * The Concatenate class displays the contents of files.
 */
public class Concatenate extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid. If the user input is
   * valid, then the function will print the contents of all the paths that the
   * user has inputed until all paths are done checking or if one path does not
   * exist. If the user requests to redirect the output with the ">>" or ">"
   * symbols, the function redirects it to the file at the path that the user
   * desires.
   * 
   * precondition: cmdAndParam's first entry always is the command "cat", every
   * string in cmdAndParams have even number of quotes
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Error" if something bad happens, "Redirection Successful" followed
   *         by the specific task of redirection (append, overwrite or create
   *         new file) if redirection was successful after redirection was
   *         requested, or the contents of all the files that have been printed
   *         until an error or until all the files have been printed.
   */
  public String executeCommand(JShell js, String[] cmdAndParam) {

    String[] cmdAndParamWithoutArrow = cmdAndParam;
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
      cmdAndParamWithoutArrow =
          UserInput.getCmdAndParamWithoutArrow(cmdAndParam, indexOfArrow);
    }
    ////////////////////////////////////////////////////////////////////////////

    // print an error message and return "Error" if user input is invalid
    if (cmdAndParamWithoutArrow == null
        || cmdAndParamWithoutArrow.length == 1) {
      Output.printInvalidArgumentSizeError(cmdAndParamWithoutArrow,
          "at least 1");
      return "Error";
    }
    // Make a new array for the same array as cmdAndParamWithoutArrow
    // but it doesn't include the command portion
    String[] paramWithoutArrow = new String[cmdAndParamWithoutArrow.length - 1];
    System.arraycopy(cmdAndParamWithoutArrow, 1, paramWithoutArrow, 0,
        cmdAndParamWithoutArrow.length - 1);

    // Run the command
    return concatAllFiles(cmdAndParam, paramWithoutArrow, root, currDir,
        redirected, indexOfArrow);
  }

  /**
   * This method reads all the files requested by user and obtains their
   * contents.
   * 
   * @param cmdAndParamWithArrow - the user input
   * @param paramWithoutArrow -
   * @param root - the root directory
   * @param currDir - current working directory
   * @param redirected - a boolean that indicates whether or not user has
   *        requested redirection.
   * @param indexOfArrow - the index of the arrow in cmdAndParamWithArrow if
   *        redirection is requested
   * @return a string of the file contents it has obtained
   */
  private String concatAllFiles(String[] cmdAndParamWithArrow,
      String[] paramWithoutArrow, Directory root, CurrentDirectory currDir,
      boolean redirected, int indexOfArrow) {

    int failed_index = -1;
    String result = "";

    // traverse all arguments to read each file individually
    for (int path = 0; path < paramWithoutArrow.length; path++) {
      String temp = concatFile(root, currDir, paramWithoutArrow[path]);
      // stop if one fails
      if (temp == null) {
        failed_index = path;
        break;
      }
      // separate the contents of files by three line breaks
      if (path != 0) {
        result += "\n\n\n\n" + temp;
        temp = "\n\n\n" + temp;
      } else {
        result += temp;
      }
      // if redirection is not requested, print the file contents
      if (!redirected)
        Output.println(temp);
    }
    // if the command has been redirected and the failed index is not the first
    // path, then we can redirect the output
    if (redirected && failed_index != 0) {
      String[] temp = {result, cmdAndParamWithArrow[indexOfArrow],
          cmdAndParamWithArrow[indexOfArrow + 1]};
      return Redirection.redirectToFile(root, temp, currDir);
    } else if (redirected && failed_index == 0) {
      result = "Redirection Error";
    } else if (!redirected && failed_index == 0) {
      result = "Error";
    }

    return result;
  }

  /**
   * This method reads one single file and gets its contents. It prints an error
   * message if file does not exist.
   * 
   * @param root - the root directory
   * @param currDir - current working directory
   * @param path - the path to the file
   * @return the file contents as a string or null if the file does not exist
   */
  private String concatFile(Directory root, CurrentDirectory currDir,
      String path) {

    // a string for storing the file contents
    String result = "";

    File resultFile = PathHandler.getFileFromPath(root, currDir, path);
    // if file does not exist, print an error message and return false
    // Add the failed index as the path, so that if the failed index is at 1
    // that means concat should not give any output back other than an error
    if (resultFile == null) {
      Output.printFileNotFoundError(path);
      return null;
    }
    // return the contents of file
    result += resultFile.getFileContent();
    return result;
  }
}
