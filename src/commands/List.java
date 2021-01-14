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
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.Redirection;
import system.UserInput;
import containers.File;

/**
 * The List class prints the contents of directories from given paths. Otherwise
 * it prints the contents of the current working directory.
 */
public class List extends Commands {

  /**
   * This method obtains user inputs before listing the contents of directories.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return none
   */
  public String executeCommand(JShell js, String[] cmdAndParam) {

    String[] cmdAndParamWithoutArrow = cmdAndParam;
    Directory root = js.getRootDirectory();
    CurrentDirectory currDir = js.getCurrDirObj();

    StringBuilder result = new StringBuilder();

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

    // if no paths are given, print the contents of the current directory
    if (cmdAndParamWithoutArrow.length == 1) {
      displayContents(currDir.getCurrDir(), result, redirected);
    }
    // traverse all arguments to list the contents of each path individually
    else {
      if (cmdAndParamWithoutArrow[1].equals("-R")
          && cmdAndParamWithoutArrow.length == 2) {
        printDir(currDir.getCurrDir(), result, redirected);
        listRecursively(root, currDir, currDir.getCurrDir(), result,
            redirected);
      } else if (cmdAndParamWithoutArrow[1].equals("-R")
          && cmdAndParamWithoutArrow.length >= 3) {
        for (int i = 2; i < cmdAndParamWithoutArrow.length; i++) {
          String listItem = listContent(root, currDir,
              cmdAndParamWithoutArrow[i], result, redirected);
          if (listItem == null) {
            if (i != 2) {
              return redirectList(result.toString(), root, currDir, cmdAndParam,
                  indexOfArrow, redirected);
            }
            if (redirected && i == 2) {
              return "Redirection Error";
            }

            return "Error";
          } else if (listItem.equals("Directory")) {
            Directory displayDirectory = PathHandler.getDirectoryFromPath(root,
                currDir, cmdAndParamWithoutArrow[i]);
            listRecursively(root, currDir, displayDirectory, result,
                redirected);
          }
        }
      } else {
        for (int i = 1; i < cmdAndParamWithoutArrow.length; i++) {
          // stop if one fails
          if (listContent(root, currDir, cmdAndParamWithoutArrow[i], result,
              redirected) == null) {
            if (i != 1) {
              return redirectList(result.toString(), root, currDir, cmdAndParam,
                  indexOfArrow, redirected);
            }
            if (redirected && i == 1) {
              return "Redirection Error";
            }

            return "Error";
          }
        }
      }
    }

    return redirectList(result.toString(), root, currDir, cmdAndParam,
        indexOfArrow, redirected);
  }

  private String redirectList(String res, Directory root,
      CurrentDirectory currDir, String[] cmdAndParam, int indexOfArrow,
      boolean redirected) {
    if (redirected) {
      String[] temp =
          {res, cmdAndParam[indexOfArrow], cmdAndParam[indexOfArrow + 1]};
      return Redirection.redirectToFile(root, temp, currDir);
    }
    return res;
  }

  private void listRecursively(Directory root, CurrentDirectory currDir,
      Directory displayDirectory, StringBuilder result, boolean redirected) {
    Directory currentDir;
    for (int i = 0; i < displayDirectory.getInnerDirectories().size(); i++) {
      currentDir = displayDirectory.getInnerDirectories().get(i);
      printDir(currentDir, result, redirected);
      listRecursively(root, currDir, currentDir, result, redirected);
    }
  }

  /**
   * This method checks and prints out the appropriate information corresponding
   * to the path.
   * 
   * @param root - the root directory of the path
   * @param path - the path to an item which method will display the contents of
   * @param currDir - current working directory
   * @return null if no such item to be listed exists, "Directory" if the item
   *         to be listed was a Directory object, and "File" if the item to be
   *         listed was a File object
   */
  private String listContent(Directory root, CurrentDirectory currDir,
      String path, StringBuilder result, boolean redirected) {

    String pathType = PathHandler.checkPath(root, currDir, path);
    // if path does not exist, print an error message and return false
    if (pathType == null) {
      Output.printPathDoesNotExistError(path);
      return null;
    }
    // print the path then the contents of the directory if the path
    // specifies a directory
    if (pathType.equals("Directory")) {
      Directory displayDirectory =
          PathHandler.getDirectoryFromPath(root, currDir, path);
      printDir(displayDirectory, result, redirected);
      return "Directory";
    }
    // print only the path if it specifies a file
    else {
      displayContents(path, result, redirected);
      return "File";
    }
    // return true if path exists and something is printed
  }

  private void printDir(Directory displayDirectory, StringBuilder result,
      boolean redirected) {
    result.append(PathHandler.getFullPath(displayDirectory) + ":\n");
    if (!redirected)
      Output.println(PathHandler.getFullPath(displayDirectory) + ":");
    displayContents(displayDirectory, result, redirected);
    result.append("\n");
    if (!redirected)
      Output.println("");
  }

  /**
   * s This method prints the contents inside a directory.
   * 
   * @param dir - the directory which method will display the contents of.
   * @return none
   */
  private void displayContents(Directory dir, StringBuilder result,
      boolean redirected) {
    ArrayList<Directory> allDirectories = dir.getInnerDirectories();
    ArrayList<File> allFiles = dir.getInnerFiles();
    // print the names of all child directories inside the directory
    for (int dirs = 0; dirs < allDirectories.size(); dirs++) {
      result.append(allDirectories.get(dirs).getDirectoryName() + "\n");
      if (!redirected)
        Output.println(allDirectories.get(dirs).getDirectoryName());
    }
    // print the names of all files inside the directory
    for (int files = 0; files < allFiles.size(); files++) {
      result.append(allFiles.get(files).getFileName() + "\n");
      if (!redirected)
        Output.println(allFiles.get(files).getFileName());
    }
  }

  /**
   * This method prints out only the given path.
   * 
   * @param path - the given path which method will print
   * @return none
   */
  private void displayContents(String path, StringBuilder result,
      boolean redirected) {
    result.append(path + "\n");
    if (!redirected)
      Output.println(path);
  }
}
