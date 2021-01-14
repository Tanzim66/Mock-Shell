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
import containers.File;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.Redirection;
import system.UserInput;

/**
 * The Search class searches for all directories or files that have the given
 * name in one or more given paths and displays the paths to all instances.
 */
public class Search extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid. If the user input is
   * valid, then the function will print the paths to all directories or files,
   * depending on which type user specifies, that have the given name in the
   * given paths. If the user requests to redirect the output with the ">>" or
   * ">" symbols, the function redirects it to the file at the path that the
   * user desires.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Error" if something bad happens, "Redirection Successful" followed
   *         by the specific task of redirection (append, overwrite or create
   *         new file) if redirection was successful after redirection was
   *         requested, or the paths to all the directories or files with the
   *         given name that have been printed.
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

    // Check if the arguments entered is of length less than 6 (including
    // command)
    // The program requires at least 5 arguments (6 with the command), so less
    // than 6 should not work
    if (cmdAndParamWithoutArrow.length < 6) {
      Output.printSearchParetersError();
      return "Error";
    }

    // Find the index where the "-type" argument appears
    int indexOfTypeParam = findIndexOfTypeParam(cmdAndParamWithoutArrow);

    // if the type argument is found
    if (indexOfTypeParam != -1) {
      // if the type argument is found, the test of the arguments that come
      // after the -type argument must be of length 3 which consists of
      // the type of the item, the -name argument and the expression
      if (cmdAndParamWithoutArrow.length - 1 - indexOfTypeParam != 3) {
        Output.printSearchParetersError();
        return "Error";
      }
      // if the length of the arguments are valid, then we go to check if the
      // arguments after and including the -type arguments are actually valid
      // eg: check if the user has inputed exactly -type [d/f] -name expression
      // where the expression is surrounded by quotes
      if (!checkValidParams(cmdAndParamWithoutArrow, indexOfTypeParam)) {
        return "Error";
      }
    } else {
      // if the type argument is not found,the input is not valid
      Output.printSearchTypeMissingError();
      return "Error";
    }

    // remove all quotes from the expression, the expression will always have
    // exactly 2 quotes
    String name =
        cmdAndParamWithoutArrow[indexOfTypeParam + 3].replaceAll("\"", "");
    // get full name of the type that the user has entered, eg d = Directory
    String type = getFullType(cmdAndParamWithoutArrow[indexOfTypeParam + 1]);

    // make a new array to store just the paths that the user entered
    String[] paths = new String[cmdAndParamWithoutArrow.length - 1 - 4];
    // copy the array that contains all the arguments from the index 1 (because)
    // the first index is the command "search" up to the index of the -type
    // argument, which would ensure that the new array contains just the paths
    // that the user has entered
    System.arraycopy(cmdAndParamWithoutArrow, 1, paths, 0,
        cmdAndParamWithoutArrow.length - 1 - 4);

    // we hand it over to the helper function that actually performs the search
    return performSearch(name, type, cmdAndParam, paths, root, currDir,
        redirected, indexOfArrow);

  }

  /**
   * This method performs the search for all directories or files, depending on
   * which type user specifies, that have the given name in the given paths.
   * 
   * @param name - the name of the items user wants to search for
   * @param type - the type of the items user wants to search for, it can be
   *        either directory or file
   * @param cmdAndParamWithArrow - the user input
   * @param paths - the paths which user wants to search the items in
   * @param root - the root directory
   * @param currDir - current working directory
   * @param redirected - a boolean that indicates whether or not user has
   *        requested redirection.
   * @param indexOfArrow - the index of the arrow in cmdAndParamWithArrow if
   *        redirection is requested
   * @return result - the string of all the paths to all instances user is
   *         searching for
   */
  private String performSearch(String name, String type,
      String[] cmdAndParamWithArrow, String[] paths, Directory root,
      CurrentDirectory currDir, boolean redirected, int indexOfArrow) {

    // result is the result string that can be redirected if the user wants
    String result = "";
    // failed_index is the index at which the loop fails, aka the index where
    // a path does not exist
    int failed_index = -1;

    // for all the path is the array with paths
    for (int i = 0; i < paths.length; i++) {
      // first get the directory that the path references
      Directory newDirectory =
          PathHandler.getDirectoryFromPath(root, currDir, paths[i]);
      // if the directory is null, it means it does not exist, so print error
      // and record it in the failed index and exit the loop
      if (newDirectory == null) {
        Output.printPathDoesNotExistError(paths[i]);
        failed_index = i;
        break;
      } else {
        // if the directory does exist, we get the desired item by performing
        // recursive search inside the directory
        String temp = recursivelyGetItemPath(newDirectory, name, type);
        // if the string that was return is the empty string, it means the
        // item was not found so there is no point of printing it.
        // but if the string is not empty and the user does not want to redirect
        // the output, then we print it
        if (!temp.equals("")) {
          if (!redirected)
            System.out.print(temp);
          // regardless if the user wants to redirect or not, we will record the
          // output into the result string
          result += temp;
        }
      }
    }
    // if the result string is empty and the failed index is not 0, it means
    // that
    // the program did perform search in at least on directory but the item the
    // user
    // is looking for was not found
    if (result.equals("") && failed_index != 0) {
      Output.println("The item " + name + " could not be found");
    }
    // if the result is not empty, the failed index is not the first index
    // and the user wants to redirect the output, we perform redirect
    else if (redirected && failed_index != 0) {
      String[] temp = {result, cmdAndParamWithArrow[indexOfArrow],
          cmdAndParamWithArrow[indexOfArrow + 1]};
      return Redirection.redirectToFile(root, temp, currDir);
    }
    return result;
  }

  /**
   * This method searches for the items with a given name in a directory. It
   * will be recursively called to search for the items in all the
   * sub-directories as well.
   * 
   * @param newDirectory - the directory user wants to search the items in
   * @param name - the name of the items user wants to search for
   * @param type - the type of the items user wants to search for
   * @return result - the string of all paths to all the instances user is
   *         searching for
   */
  private String recursivelyGetItemPath(Directory newDirectory, String name,
      String type) {
    // result is for when the item that we are looking for is found
    String result = "";

    // we first check if the item we are looking for is inside the current
    // directory

    // case the item in search for is a file inside the current directory
    if (type.equals("File")) {
      // try to get the file that has "name" as its name inside the current
      // directory
      File tempFile = newDirectory.getChildFile(name);
      if (tempFile != null) {
        // if it is found, then we update the result and add the full path to
        // this desired file
        result += PathHandler.getFullPath(tempFile) + "\n";
      }
    }
    // case the item in search for is a directory inside the current directory
    else if (type.equals("Directory")) {
      // try to get the directory that has "name" as its name inside the current
      // directory
      Directory tempFile = newDirectory.getChildDirectory(name);
      if (tempFile != null) {
        // if it is found, then we update the result and add the full path to
        // this
        // desired directory
        result += PathHandler.getFullPath(tempFile) + "\n";
      }
    }

    // get all the directories that are inside the current directory
    ArrayList<Directory> innerDirectories = newDirectory.getInnerDirectories();

    // for each of them, we perform search for the item and update result
    // respectively
    for (int i = 0; i < innerDirectories.size(); i++) {
      String temp = recursivelyGetItemPath(innerDirectories.get(i), name, type);
      if (!temp.equals(""))
        result += temp;
    }

    // return result when done
    return result;
  }

  /**
   * This method obtains a character from user and returns the full name of the
   * type as a string corresponding to the character.
   * 
   * @param str - the character, it is either "f" or "d"
   * @return "File" if str is "f", "Directory" if str is "d", or null if it's
   *         neither "f" or "d"
   */
  private String getFullType(String str) {
    // convert "f" to "File"
    if (str.equals("f"))
      return "File";
    // convert "d" to Directory
    else if (str.equals("d"))
      return "Directory";
    // if its neither, return null
    return null;
  }

  /**
   * This method checks if the parameters inputed by user are in the valid form.
   * 
   * @param cmdAndParam - the user input
   * @param indexOfTypeParam - the index of the -type parameter
   * @return true if all parameters are in the valid form, false otherwise
   */
  private boolean checkValidParams(String[] cmdAndParam, int indexOfTypeParam) {
    // the index where the -type argument is seen in the array
    int i = indexOfTypeParam;
    // the i+2 index should be the -name argument, if it is not print an error
    // statement and return false because it is not valid
    if (!cmdAndParam[i + 2].equals("-name")) {
      Output.printSearchNameMissingError();
      return false;
    }
    // the i+1 index should be the type of the item indicated by either "f" or
    // "d". If it is not either, print an error statement and return false
    // because it is not valid
    if (!cmdAndParam[i + 1].equals("f") && !cmdAndParam[i + 1].equals("d")) {
      Output.printSearchInvalidItemTypeError();
      return false;
    }
    // the i+3 index should be the expression that is surrounded by quotes,
    // containing exactly 2 quotes. If it does not, print an error statement
    // and return false because it is not valid
    if (!UserInput.surroundedByTwoQuotes(cmdAndParam[i + 3])
        || UserInput.numQuotes(cmdAndParam[i + 3]) != 2) {
      Output.printExpressionDoesNotContainTwoQuotesError();
      return false;
    }

    // if everything above is valid, return true
    return true;
  }

  /**
   * This method finds and returns the index of the -type parameter in the array
   * of user input.
   * 
   * @param cmdAndParam - the user input
   * @return index - the index of the -type parameter in cmdAndParam, or -1 if
   *         the -type parameter is not found
   */
  private int findIndexOfTypeParam(String[] cmdAndParam) {
    // if the -type parameter is not found, -1 will be returned
    int index = -1;
    // go through the array and try to find the -type parameter
    for (int i = 0; i < cmdAndParam.length; i++) {
      if (cmdAndParam[i].equals("-type")) {
        // if found, set index to i and exit loop
        index = i;
        break;
      }
    }
    return index;
  }

}
