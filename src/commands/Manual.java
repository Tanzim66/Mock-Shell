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

import java.util.HashMap;
import containers.CurrentDirectory;
import containers.Directory;
import driver.JShell;
import system.Output;
import system.Redirection;
import system.UserInput;

/**
 * The Manual class prints the documentations of the command specified by user.
 */
public class Manual extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid. If the user input is
   * valid, then the function will print the documentations for the command
   * specified by user. If the user requests to redirect the output with the
   * ">>" or ">" symbols, the function redirects it to the file at the path that
   * the user desires.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Error" if something bad happens, "Redirection Successful" followed
   *         by the specific task of redirection (append, overwrite or create
   *         new file) if redirection was successful after redirection was
   *         requested, or the documentations of the command specified by user
   *         that have been printed.
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
      Output.printInvalidArgumentSizeError(cmdAndParam, "at least 1");
      return "Error";
    }

    // make a new array for the same array as cmdAndParamWithoutArrow
    // but it doesn't include the command portion
    String[] paramWithoutArrow = new String[cmdAndParamWithoutArrow.length - 1];
    System.arraycopy(cmdAndParamWithoutArrow, 1, paramWithoutArrow, 0,
        cmdAndParamWithoutArrow.length - 1);

    if (paramWithoutArrow.length != 1) {
      Output.printManMultipleCommandsError();
      return "Error";
    }
    // run the command
    return printAllManual(cmdAndParam, paramWithoutArrow, root, currDir,
        redirected, indexOfArrow);
  }

  /**
   * This method obtains the documentations of the command specified by user.
   * 
   * @param cmdAndParamWithArrow - the user input including the arrow if
   *        redirection is requested
   * @param paramWithoutArrow - the user input without without the arrow
   * @param root - the root directory
   * @param currDir - current working directory
   * @param redirected - a boolean that indicates whether or not user has
   *        requested redirection.
   * @param indexOfArrow - the index of the arrow in cmdAndParamWithArrow if
   *        redirection is requested
   * @return none
   */
  private String printAllManual(String[] cmdAndParamWithArrow,
      String[] paramWithoutArrow, Directory root, CurrentDirectory currDir,
      boolean redirected, int indexOfArrow) {
    // a string for storing the output
    String result = "";
    int failed_index = -1;

    // the hash map that stores the documentations of all valid commands
    HashMap<String, String> manualHashMap = new HashMap<String, String>();
    initializeHashMapWithManual(manualHashMap);
    // traverse all arguments to get the documentations of each command
    // individually
    for (int i = 0; i < paramWithoutArrow.length; i++) {
      // stop if one fails
      String temp = printManual(manualHashMap, paramWithoutArrow[i]);
      if (temp == null) {
        failed_index = i;
        break;
      }
      // add the documentations to result
      if (i != 0) {
        result += "\n" + temp;
      } else
        result += temp;
      // if redirection is not requested, print the output
      if (!redirected)
        Output.println(temp);
    }
    // redirect the output to a file
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
   * This method obtains the documentations of a given command using hash map.
   * It prints an error message if the given command is invalid.
   * 
   * @param manualHashMap - the hash map that stores the documentations of all
   *        valid commands
   * @param cmd - the command which method will get the documentations of
   * @return result - the documentations of the command or null if the command
   *         is invalid
   */
  private String printManual(HashMap<String, String> manualHashMap,
      String cmd) {

    // a string for storing the documentations of a command
    String result = "";
    // if the command is invalid then it cannot be found on the hash map; print
    // an error message and return null
    if (manualHashMap.get(cmd) == null) {
      Output.printInvalidCommandError(cmd);
      return null;
    }
    result = manualHashMap.get(cmd);
    return result;
  }

  /**
   * This method uses hash map to assign and store the documentations to every
   * valid command.
   * 
   * @param manualHashMap - the hash map that will store the documentations of
   *        all valid commands
   * @return none
   */
  private void initializeHashMapWithManual(
      HashMap<String, String> manualHashMap) {

    manualHashMap.put("exit", "exit\n" + "Quit the program ");

    manualHashMap.put("mkdir",
        "mkdir DIR1 DIR2\n" + "This command takes in two arguments only. "
            + "Create directories, each of which may be relative to the "
            + "current directory or may be a full path.");

    manualHashMap.put("cd",
        "cd DIR\n" + "Change directory to DIR, which may be relative to the "
            + "current directory or may be a full path.");

    manualHashMap.put("ls",
        "ls [PATH ...]\n"
            + "If no paths are given, print the contents (file or directory) "
            + "of the current directory. Otherwise, for each path p, the "
            + "order listed: If p specifies a file, print p. If p specifies "
            + "a directory, print p, a colon, then the contents of that "
            + "directory, then an extra new line.");

    manualHashMap.put("pwd", "pwd\n" + "Print the current working directory "
        + "(including the whole path).");

    manualHashMap.put("pushd",
        "pushd DIR\n" + "Saves the current working directory by pushing onto "
            + "directory stack and then changes the new current working "
            + "directory to DIR.");

    manualHashMap.put("popd", "popd\n"
        + "Remove the top entry from the directory stack, and cd into it.");

    manualHashMap.put("history",
        "history [number]\n" + "This command will print out recent commands, "
            + "one command per line. We can truncate the output by "
            + "specifying a number (>=0) after the command.");

    manualHashMap.put("cat",
        "cat FILE1 [FILE2 ...]\n"
            + "Display the contents of FILE1 and other files "
            + "(i.e. File2 ....) concatenated in the shell.");

    manualHashMap.put("echo",
        "echo STRING [> OUTFILE]\n"
            + "If OUTFILE is not provided, print STRING on the shell. "
            + "Otherwise, put STRING into file OUTFILE.\n\n"
            + "echo STRING >> OUTFILE\n"
            + "Like the previous command, but appends instead of overwrites.");

    manualHashMap.put("man",
        "man CMD[CMD2 ...]\n" + "Print documentation for CMD (s)");

    manualHashMap.put("mv",
        "mv OLDPATH NEWPATH\n"
            + "Move item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH may be "
            + "relative to the current directory or may be full paths. If "
            + "NEWPATH is a diretory, move the item into the directory.");

    manualHashMap.put("cp",
        "cp OLDPATH NEWPATH\n"
            + "Similar to mv, but don't remove OLDPATH. if OLDPATH is a "
            + "directory, recursively copy the contents.");
  }
}
