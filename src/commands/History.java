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

import java.util.ArrayList;
import containers.CurrentDirectory;
import containers.Directory;
import containers.UserInputList;
import driver.JShell;
import system.Output;
import system.Redirection;
import system.UserInput;


/**
 * The History class prints a given number of most recent commands. If a number
 * is not given, it prints all recent commands.
 */
public class History extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid. If the user input is
   * valid, then the function will print the given number of most recent
   * commands. If the user requests to redirect the output with the ">>" or ">"
   * symbols, the function redirects it to the file at the path that the user
   * desires.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Error" if something bad happens, "Redirection Successful" followed
   *         by the specific task of redirection (append, overwrite or create
   *         new file) if redirection was successful after redirection was
   *         requested, or the most recent commands that have been printed.
   */
  public String executeCommand(JShell js, String[] cmdAndParam) {

    String[] cmdAndParamWithoutArrow = cmdAndParam;

    Directory root = js.getRootDirectory();
    CurrentDirectory currDir = js.getCurrDirObj();
    UserInputList userInputList = js.getUserInputList();

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

    // print an error message if user input is invalid
    if (cmdAndParamWithoutArrow == null || cmdAndParamWithoutArrow.length > 2) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "0 or 1");
      return "Error";
    }
    // if a number is given and it is valid, get that number of most recent
    // commands. Print an error message and return "Error" if it is invalid.
    if (cmdAndParamWithoutArrow.length == 2) {
      if (UserInput.isNumeric(cmdAndParamWithoutArrow[1])) {
        int tempNum = Integer.parseInt(cmdAndParamWithoutArrow[1]);
        if (tempNum >= 0)
          return printHistoryWithConstraint(cmdAndParam, tempNum, userInputList,
              root, currDir, redirected, indexOfArrow);
        else {
          Output.printInvalidInteger();
          return "Error";
        }
      }
      Output.printInvalidParamTypeError();
      return "Error";
    }
    // if a number is not given, return all recent commands
    return printHistoryWithConstraint(cmdAndParam,
        userInputList.getRecentCommandsSize(), userInputList, root, currDir,
        redirected, indexOfArrow);
  }

  /**
   * This method obtains a given number of most recent commands.
   * 
   * @param cmdAndParamWithArrow - the user input
   * @param constraint - the number of most recent commands specified by user
   * @param userInput - the list of all recent commands
   * @param root - the root directory
   * @param currDir - current working directory
   * @param redirected - a boolean that indicates whether or not user has
   *        requested redirection.
   * @param indexOfArrow - the index of the arrow in cmdAndParamWithArrow if
   *        redirection is requested
   * @return result - the string of the recent commands
   */
  private String printHistoryWithConstraint(String[] cmdAndParamWithArrow,
      int constraint, UserInputList userInput, Directory root,
      CurrentDirectory currDir, boolean redirected, int indexOfArrow) {

    ArrayList<String> recentCommands = userInput.getRecentCommands();
    // a string for storing the output
    String result = "";
    // if the given number is greater than the number of total recent commands,
    // print as many recent commands as possible.
    int index = recentCommands.size() - constraint;
    if (index < 0) {
      index = 0;
    }
    // loop through the user input list to add the most recent commands to the
    // output
    for (int i = index; i < recentCommands.size(); i++) {
      if (i + 1 != recentCommands.size()) {
        result += (i + 1) + ". " + recentCommands.get(i) + "\n";
      } else
        result += (i + 1) + ". " + recentCommands.get(i);
    }
    // redirect the output to a file if redirection is requested, print the
    // output otherwise
    if (redirected) {
      String[] temp = {result, cmdAndParamWithArrow[indexOfArrow],
          cmdAndParamWithArrow[indexOfArrow + 1]};
      return Redirection.redirectToFile(root, temp, currDir);
    } else {
      System.out.println(result);
      return result;
    }

  }

}
