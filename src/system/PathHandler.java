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
package system;

import containers.CurrentDirectory;
import containers.Directory;
import containers.File;

/**
 * The PathHandler class handles all the paths and locate directories and files
 * from paths.
 */
public class PathHandler {

  // a string of characters which cannot be used in item names
  private static String invalidCharacters = "\"/. !@#$%^&*(){}~|<>?";

  /**
   * This method finds and returns the full path to a directory.
   * 
   * @param dir - the directory which method will find and return the path to
   * @param fullPath - the string to contain the full path
   * @return the full path to dir
   */
  private static String findFullPath(Directory dir, StringBuilder fullPath) {
    Directory current = dir;
    Directory parentDir;

    while (current != null) {
      fullPath.insert(0, current.getDirectoryName());
      parentDir = current.getParentDirectory();
      if (parentDir != null && !parentDir.getDirectoryName().equals("/")) {
        fullPath.insert(0, "/");
      }
      current = current.getParentDirectory();
    }

    return fullPath.toString();
  }

  /**
   * This method finds and returns the full path to a directory.
   * 
   * @param file - the file which method will find and return the path to
   * @return the full path to directory
   */
  public static String getFullPath(Directory dir) {
    StringBuilder fullPath = new StringBuilder();
    return findFullPath(dir, fullPath);
  }

  /**
   * This method finds and returns the full path to a file.
   * 
   * @param file - the file which method will find and return the path to
   * @return the full path to file
   */
  public static String getFullPath(File file) {
    StringBuilder fullPath = new StringBuilder();
    if (file.getParentDirectory().getDirectoryName().equals("/")) {
      fullPath.append(file.getFileName());
    } else {
      fullPath.append("/" + file.getFileName());
    }
    return findFullPath(file.getParentDirectory(), fullPath);
  }

  /**
   * This method checks if a path contains any consecutive slashes.
   * 
   * @param path - the path which method will check if it contains any
   *        consecutive slashes
   * @return true if the path contains consecutive slashes, false otherwise
   */
  private static boolean hasMultipleSlash(String path) {

    // traverse all characters in path to count the number of slash
    int count = 0;
    for (int i = 0; i < path.length(); i++) {
      if (path.charAt(i) == '/') {
        count++;
      } else {
        count = 0;
      }
      if (count > 1) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method locates and returns the name of the last item in a path.
   * 
   * @param path - the path which method will use to get the last item
   * @return the last item in the path
   */
  public static String getItemName(String path) {

    // if given path is the root directory
    if (path.equals("/")) {
      return path;
    }
    return path.substring(path.lastIndexOf("/") + 1);
  }

  /**
   * This method locates and returns a directory object from a given path.
   * 
   * @param root - the root directory
   * @param currDir - current working directory
   * @param path - the path which method will use to locate the directory
   * @param getPrevDir - true if we want to get the directory before
   * @return the directory or null if directory does not exist in the path
   */
  private static Directory getDirectory(Directory root,
      CurrentDirectory currDir, String path, boolean getPrevDir) {

    // return null if the path has invalid syntax
    if (hasMultipleSlash(path)) {
      return null;
    }

    // return root if given path is the root directory
    if (path.equals("/") || path.equals("")) {
      return root;
    }

    Directory current, parentDir, childDir;
    String[] dirNames;

    if (path.charAt(0) == '/') {
      path = path.substring(1);
      current = root;
    } else {
      current = currDir.getCurrDir();
    }

    dirNames = path.split("/");

    int checkLength = dirNames.length;

    // get the directory before if getPrevDir is true
    if (getPrevDir) {
      checkLength--;
    }

    // traverse the path to locate the directory
    for (int i = 0; i < checkLength; i++) {
      if (dirNames[i].equals("..")) {
        parentDir = current.getParentDirectory();
        if (parentDir == null) {
          return null;
        }
        current = current.getParentDirectory();
      } else if (!dirNames[i].equals(".")) {
        childDir = current.getChildDirectory(dirNames[i]);
        if (childDir == null) {
          return null;
        }
        current = current.getChildDirectory(dirNames[i]);
      }
    }

    return current;
  }

  /**
   * This method checks if the last item in a path is either directory or a
   * file. It returns null if no item exists in the end of the path.
   * 
   * @param root - the root directory
   * @param currDir - current working directory
   * @param path - the path which method will use to get the last item
   * @return a string that says what the last item is or null if last item does
   *         not exist
   */
  public static String checkPath(Directory root, CurrentDirectory currDir,
      String path) {
    if (getDirectoryFromPath(root, currDir, path) != null) {
      return "Directory";
    } else if (getFileFromPath(root, currDir, path) != null) {
      return "File";
    }
    return null;
  }

  /**
   * This method locates and returns the directory before the last item in a
   * path.
   * 
   * @param root - the root directory
   * @param currDir - current working directory
   * @param path - the path which method will use to locate the directory
   * @return the directory before the last item in a path
   */
  public static Directory getDirectoryBefore(Directory root,
      CurrentDirectory currDir, String path) {
    return getDirectory(root, currDir, path, true);
  }

  /**
   * This method locates and returns the directory object in the end of a path
   * that is concatenated to the root directory. It returns null if such
   * directory does not exist.
   * 
   * @param root - the root directory
   * @param currDir - current working directory
   * @param path - the path which method will use to get the directory
   * @return the directory or null if such directory does not exist
   */
  public static Directory getDirectoryFromPath(Directory root,
      CurrentDirectory currDir, String path) {
    return getDirectory(root, currDir, path, false);
  }

  /**
   * This method locates and returns the file object in the end of a path that
   * is concatenated to the root directory. It returns null if such file does
   * not exist.
   * 
   * @param root - the root directory
   * @param currDir - current working directory
   * @param path - the path which method will use to get the file
   * @return the file or null if such file does not exist
   */
  public static File getFileFromPath(Directory root, CurrentDirectory currDir,
      String path) {
    String last = getItemName(path);
    // get the parent directory of the file
    Directory dirBefore = getDirectory(root, currDir, path, true);
    File fileFound = null;
    // return null if the parent directory does not exist
    if (dirBefore == null) {
      return null;
    }
    if (dirBefore.getChildFile(last) != null) {
      fileFound = dirBefore.getChildFile(last);
    }

    return fileFound;
  }

  /**
   * This method checks if the name of an item is valid. The item name is
   * invalid if the item name is an empty string or contains any characters that
   * cannot be used in names.
   * 
   * @param name - the name of the item
   * @return true - if the item name is valid, false otherwise
   */
  public static boolean checkValidItemName(String itemName) {

    // if the last item is empty then it is an invalid name
    if (itemName.equals("")) {
      Output.printInvalidNameError(itemName);
      return false;
    }
    // if any invalid characters are found then it is an invalid name
    for (int i = 0; i < itemName.length(); i++) {
      if (invalidCharacters.indexOf(itemName.charAt(i)) != -1) {
        Output.printInvalidNameError(itemName);
        return false;
      }
    }
    // item name is valid
    return true;
  }

  /**
   * This method edits a given string into a valid item name.
   * 
   * @param itemName - the original item name
   * @return newItemName.toString() - the edited item name
   */
  public static String editItemName(String itemName) {
    StringBuilder newItemName = new StringBuilder();
    // make sure newItemName does not contain any invalid characters
    for (int i = 0; i < itemName.length(); i++) {
      if (invalidCharacters.indexOf(itemName.charAt(i)) == -1) {
        newItemName.append(itemName.charAt(i));
      }
    }
    return newItemName.toString();
  }
}
