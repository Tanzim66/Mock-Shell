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

import java.util.Arrays;
import containers.CurrentDirectory;
import containers.Directory;
import containers.File;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.UserInput;

/**
 * The Move class moves a given item to a path specified by user.
 */
public class Move extends Copy {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully moves the item, "Error" if it fails
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
    if (cmdAndParam.length != 3) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "2");
      return "Error";
    }
    Directory root = js.getRootDirectory();
    CurrentDirectory currDir = js.getCurrDirObj();
    String[] oldPathParam = Arrays.copyOfRange(cmdAndParam, 0, 2);
    // run the command
    return move(js, root, currDir, cmdAndParam[1], cmdAndParam[2],
        oldPathParam);
  }

  /**
   * This method checks the path types then proceeds to move the item.
   * 
   * @param js - JShell
   * @param root - the root directory
   * @param currDir - current working directory
   * @param oldPath - the path to the item that is being moved
   * @param newPath - the path which item will be moved to
   * @param oldPathParam - an array with 2 indexes that contain the command and
   *        oldPath
   * @return "Success" if it successfully moves the item, "Error" if it fails
   */
  private String move(JShell js, Directory root, CurrentDirectory currDir,
      String oldPath, String newPath, String[] oldPathParam) {
    Remove remove = new Remove();
    String oldPathType = PathHandler.checkPath(root, currDir, oldPath);
    String newPathType = PathHandler.checkPath(root, currDir, newPath);

    // print an error message and return "Error" if paths are invalid
    if (!checkInvalidCases(oldPath, oldPathType, newPath, "move")) {
      return "Error";
    }

    // New path can not exist
    if (newPathType == null) {
      // Check if parent directory exists, must exist
      Directory parentDir =
          PathHandler.getDirectoryBefore(root, currDir, newPath);
      // print an error message and return "Error" if parent directory does not
      // exist
      if (parentDir == null) {
        Output.printNewPathDoesNotExistError();
        return "Error";
      }

      // New path is a directory
      if (newPath.substring(newPath.length() - 1).equals("/")) {
        if (oldPathType.equals("Directory")) {
          // Directory -> New Directory: Change name
          Directory dir =
              PathHandler.getDirectoryFromPath(root, currDir, oldPath);
          dir.setDirectoryName(PathHandler
              .getItemName(newPath.substring(0, newPath.length() - 1)));
          return "Success";
        } else {
          // File -> New Directory: Not possible
          Output.printModifyFileToNewDirectoryError("move");
          return "Error";
        }
      }

      // New path is a file
      else {
        if (oldPathType.equals("Directory")) {
          // Directory -> File: Not possible
          Output.printMovingDirectoryToFileError();
          return "Error";
        } else {
          // File -> File: Change name
          File file = PathHandler.getFileFromPath(root, currDir, oldPath);
          file.setFileName(PathHandler.getItemName(newPath));
          return "Success";
        }
      }
    }

    // New path does exist
    else {
      Directory parentDir =
          PathHandler.getDirectoryBefore(root, currDir, oldPath);
      if (newPathType.equals("Directory")) {
        if (oldPathType.equals("Directory")) {
          // Directory -> Directory
          Directory oldDir =
              PathHandler.getDirectoryFromPath(root, currDir, oldPath);
          Directory newDir =
              PathHandler.getDirectoryFromPath(root, currDir, newPath);

          // make sure user does not copy a parent directory into any of its
          // children directories
          if (containChildDirectory(oldDir, newDir)) {
            Output.printModifyChildDirError("move");
            return "Error";
          }
          copyDirectoryToDirectory(oldDir, newDir);
          remove.executeCommand(js, oldPathParam);
          return "Success";
        } else {
          // File -> Directory
          File file = PathHandler.getFileFromPath(root, currDir, oldPath);
          copyFileToDirectory(file,
              PathHandler.getDirectoryFromPath(root, currDir, newPath));
          removeFile(file, parentDir);
          return "Success";

        }
      }

      // New path is a file
      if (oldPathType.equals("Directory")) {
        // Directory -> File: Not possible
        Output.printMovingDirectoryToFileError();
        return "Error";
      } else {
        // File -> File: Replace contents
        File file = PathHandler.getFileFromPath(root, currDir, oldPath);
        overWriteFile(file,
            PathHandler.getFileFromPath(root, currDir, newPath));
        removeFile(file, parentDir);
        return "Success";
      }
    }
  }

  /**
   * This method removes a file from a directory.
   * 
   * @param file - the file that will be removed
   * @param dir - the directory that contains the file
   * @return none
   */
  private void removeFile(File file, Directory dir) {
    dir.getInnerFiles().remove(file);
  }

}
