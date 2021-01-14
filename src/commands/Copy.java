package commands;

import java.util.ArrayList;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.UserInput;
import containers.CurrentDirectory;
import containers.Directory;
import containers.File;

/**
 * The Copy class copies a given item to a path specified by user.
 */
public class Copy extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully copies the item, "Error" if it fails
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
    // run the command
    return copy(root, currDir, cmdAndParam[1], cmdAndParam[2]);
  }


  /**
   * This method checks the path types then proceeds to copy the item.
   * 
   * @param root - the root directory
   * @param currDir - current working directory
   * @param oldPath - the path to the item that is being copied
   * @param newPath - the path which item will be copied to
   * @return "Success" if it successfully copies the item, "Error" if it fails
   */
  protected String copy(Directory root, CurrentDirectory currDir,
      String oldPath, String newPath) {

    // get the path types
    String oldPathType = PathHandler.checkPath(root, currDir, oldPath);
    String newPathType = PathHandler.checkPath(root, currDir, newPath);

    // print an error message and return "Error" if user is copying an item
    // that does not exist or user is copying an item to itself
    if (!checkInvalidCases(oldPath, oldPathType, newPath, "copy")) {
      return "Error";
    }

    // if the item is a directory
    if (oldPathType.equals("Directory")) {

      // print an error message and return "Error" if item that is being copied
      // to does not exist or if it is a file
      if (newPathType == null || newPathType.equals("File")) {
        Output.printInvalidDestinationForDirError();
        return "Error";
      }

      // can only copy directory to an existing directory
      Directory oldDir =
          PathHandler.getDirectoryFromPath(root, currDir, oldPath);
      Directory newDir =
          PathHandler.getDirectoryFromPath(root, currDir, newPath);

      // make sure user does not copy a parent directory into any of its
      // children directories
      if (containChildDirectory(oldDir, newDir)) {
        Output.printModifyChildDirError("copy");
        return "Error";
      }
      return copyDirectoryToDirectory(oldDir, newDir);
    }

    // if item is a file
    else {
      File file = PathHandler.getFileFromPath(root, currDir, oldPath);

      // if the item that is being copied to does not exist
      if (newPathType == null) {

        // make sure the item is a file and its parent directory exists
        Directory parentDir =
            PathHandler.getDirectoryBefore(root, currDir, newPath);
        if (parentDir == null) {
          Output.printPathDoesNotExistError(newPath);
          return "Error";
        }
        if (newPath.substring(newPath.length() - 1).equals("/")) {
          Output.printModifyFileToNewDirectoryError("copy");
          return "Error";
        }
        // copy to the new file by creating it
        return copyFileToDirectory(file, parentDir,
            PathHandler.getItemName(newPath));
      }
      // if user is trying to copy to a directory
      if (newPathType.equals("Directory")) {
        return copyFileToDirectory(file,
            PathHandler.getDirectoryFromPath(root, currDir, newPath));
      }
      // if user is trying to copy to a file, overwrite its content with the
      // content of file user is copying
      return overWriteFile(file,
          PathHandler.getFileFromPath(root, currDir, newPath));
    }
  }


  /**
   * This method copies a file to a directory.
   * 
   * @param file - the file that is being copied
   * @param dir - the directory which the file is being copied to
   * @return "Success" if it successfully copies the file to the directory,
   *         "Error" if it fails
   */
  protected String copyFileToDirectory(File file, Directory dir) {
    // if directory already contains a child with the file name, print an error
    // message and return "Error"
    if (containItemWithName(dir, file.getFileName())) {
      Output.printItemAlreadyExistsError(PathHandler.getFullPath(dir),
          file.getFileName());
      return "Error";
    }
    // copy the file to the directory then return "Success"
    dir.addFileInside(file.getFileName());
    File newFile = dir.getChildFile(file.getFileName());
    newFile.setFileContent(file.getFileContent());
    return "Success";
  }

  /**
   * This method copies a file to a directory. However the name of the file is
   * changed to fileName after it is copied to the directory.
   * 
   * @param file - the file that is being copied
   * @param dir - the directory which the file is being copied to
   * @param fileName - the new name of the file
   * @return "Success" if it successfully copies the file to the directory,
   *         "Error" if it fails
   */
  protected String copyFileToDirectory(File file, Directory dir,
      String fileName) {
    // if directory already contains a child with fileName, print an error
    // message and return "Error"
    if (containItemWithName(dir, fileName)) {
      Output.printItemAlreadyExistsError(PathHandler.getFullPath(dir),
          fileName);
      return "Error";
    }
    // copy the file to the directory then return "Success"
    dir.addFileInside(fileName);
    File newFile = dir.getChildFile(fileName);
    newFile.setFileContent(file.getFileContent());
    return "Success";
  }

  /**
   * This method copies a directory to another existing directory.
   * 
   * @param oldDir - the directory that is being copied
   * @param newDir - the directory which oldDir is being copied to
   * @return "Success" if it successfully copies the directory to another
   *         directory, "Error" if it fails
   */
  protected String copyDirectoryToDirectory(Directory oldDir,
      Directory newDir) {

    // if newDir already contains a child with the name of oldDir, print an
    // error message and return "Error"
    if (containItemWithName(newDir, oldDir.getDirectoryName())) {
      Output.printItemAlreadyExistsError(PathHandler.getFullPath(newDir),
          oldDir.getDirectoryName());
      return "Error";
    }
    // copy oldDir to newDir
    newDir.addDirectoryInside(oldDir.getDirectoryName());
    Directory dir = newDir.getChildDirectory(oldDir.getDirectoryName());

    // copy all inner files of oldDir to newDir
    ArrayList<File> innerFilesOfNewDir = oldDir.getInnerFiles();
    for (int i = 0; i < innerFilesOfNewDir.size(); i++) {
      copyFileToDirectory(innerFilesOfNewDir.get(i), dir);
    }

    // copy all child directories of oldDir to newDir
    ArrayList<Directory> innerDirectories = oldDir.getInnerDirectories();
    for (int i = 0; i < innerDirectories.size(); i++) {
      copyDirectoryToDirectory(innerDirectories.get(i), dir);
    }
    return "Success";
  }

  /**
   * This method overwrites the content of newFile with the content of oldFile.
   * 
   * @param oldFile - the file with the content that is going to overwrite the
   *        content of newFile
   * @param newFile - the file that is having its content overwritten
   * @return "Success" if it successfully overwrites content of newFile with
   *         content of oldFile, "Error" if it fails
   */
  protected String overWriteFile(File oldFile, File newFile) {
    newFile.setFileContent(oldFile.getFileContent());
    return "Success";
  }

  /**
   * This method checks if a directory contains any child with a given name.
   * 
   * @param parentDir - the directory which method will check if it contains any
   *        child with itemName
   * @param itemName - the name which method will check if directory contains
   *        any child with
   * @return true if directory contains a child with itemName, false otherwise
   */
  protected boolean containItemWithName(Directory parentDir, String itemName) {
    if (parentDir.getChildDirectory(itemName) != null
        || parentDir.getChildFile(itemName) != null) {
      return true;
    }
    return false;
  }

  /**
   * This method checks if childDir is a child directory of parentDir's.
   * 
   * @param parentDir - the directory which method will check if it is the
   *        parent directory of childDir
   * @param itemName - the directory which method will check if it is a child
   *        directory of parentDir's
   * @return true if childDir is a child directory of parentDir's, false
   *         otherwise
   */
  protected boolean containChildDirectory(Directory parentDir,
      Directory childDir) {
    // check the parent directories of childDir to see if any of them is
    // parentDir
    Directory tempDir = childDir;
    while (tempDir.getParentDirectory() != null) {
      tempDir = tempDir.getParentDirectory();
      if (tempDir.equals(parentDir)) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks if a oldPath exists and if oldPath and newPath are the
   * same path.
   * 
   * @param oldPath - the path which method will check if it exists
   * @param oldPathType - the path type of oldPath
   * @param newPath - the path which method will check if it is the same path as
   *        oldPath
   * @return true if oldPath exists and it is not the same path as newPath,
   *         false otherwise
   */
  protected boolean checkInvalidCases(String oldPath, String oldPathType,
      String newPath, String command) {
    // print an error message if oldPath does not exist
    if (oldPathType == null) {
      Output.printPathDoesNotExistError(oldPath);
      return false;
    }
    // print an error message if oldPath and newPath are the same path
    if (oldPath.equals(newPath)) {
      Output.printModifyItemToItselfError(command);
      return false;
    }
    return true;
  }

}
