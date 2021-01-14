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
 * The Echo class either appends a string to the content of a file or overwrites
 * the content of the file with the string. If the file does not exist, it
 * creates a new file containing the string as its contents.
 */
public class Echo extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid. If the Parameters are
   * valid, it will, if there is no redirection symbol, print the second
   * argument. If there is a redirection symbol followed by a path, it will
   * create a new file if the file at the path does not exist, it will overwrite
   * the file at the path if the redirection symbol is ">" and the file at the
   * path exists or it will append the file at the path is the redirection
   * symbol is ">>" and the file at the path already exists.
   * 
   * precondition: cmdAndParam's first entry always is the command "echo", every
   * string in cmdAndParams have even number of quotes
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Error" if something bad happens, "Redirection Successful" followed
   *         by the specific task of redirection (append, overwrite or create
   *         new file) if redirection was successful after redirection was
   *         requested, or the message of the expression entered if there was no
   *         redirection
   */
  public String executeCommand(JShell js, String[] cmdAndParam) {

    // print an error message and return "Error" if user input is invalid
    if (cmdAndParam.length == 1 || cmdAndParam.length == 3
        || cmdAndParam.length >= 5) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "1 or 3");
      return "Error";
    }

    Directory root = js.getRootDirectory();
    CurrentDirectory currDir = js.getCurrDirObj();

    // return an error message and return "Error" if first argument is not
    // surrounded by exactly two quotes
    if (!UserInput.surroundedByTwoQuotes(cmdAndParam[1])
        || UserInput.numQuotes(cmdAndParam[1]) != 2) {
      Output.printDoesNotContainTwoQuotesError();
      return "Error";
    }

    // print an error message and return "Error" if second argument is invalid
    if (cmdAndParam.length == 4 && !cmdAndParam[2].equals(">")
        && !cmdAndParam[2].equals(">>")) {
      Output.printEchoArrowError();
      return "Error";
    }
    cmdAndParam[1] = cmdAndParam[1].replaceAll("\"", "");

    // print the first argument if a file name is not specified and redirection
    // is not requested
    if (cmdAndParam.length == 2) {
      Output.println(cmdAndParam[1]);
      return cmdAndParam[1];
    } else {
      String[] noCommand = new String[cmdAndParam.length - 1];
      System.arraycopy(cmdAndParam, 1, noCommand, 0, cmdAndParam.length - 1);
      return Redirection.redirectToFile(root, noCommand, currDir);
    }
  }


}
