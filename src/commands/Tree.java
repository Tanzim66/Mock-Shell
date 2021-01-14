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

import driver.JShell;
import system.Output;
import system.UserInput;
import system.Redirection;
import containers.Directory;
import containers.File;
import containers.CurrentDirectory;
import java.util.ArrayList;

/*
 * The Tree class displays the entire file system as a tree diagram starting
 * from the root directory.
 */
public class Tree extends Commands {

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid. If the user input is
   * valid, then the function will print the tree diagram of the file system. If
   * the user requests to redirect the output with the ">>" or ">" symbols, the
   * function redirects it to the file at the path that the user desires.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Error" if something bad happens, "Redirection Successful" followed
   *         by the specific task of redirection (append, overwrite or create
   *         new file) if redirection was successful after redirection was
   *         requested, or the tree diagram of the file system that has been
   *         printed.
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

    // print an error message if user input is invalid
    if (cmdAndParamWithoutArrow.length != 1) {
      Output.printInvalidArgumentSizeError(cmdAndParamWithoutArrow, "0");
      return "Error";
    }
    // get the tree and store it in a string
    String tree = "";
    tree = getTree(root, 0, tree);

    // make the string the content of the file specified by user if redirection
    // is requested by user, print the string otherwise
    if (redirected) {
      String[] temp =
          {tree, cmdAndParam[indexOfArrow], cmdAndParam[indexOfArrow + 1]};
      return Redirection.redirectToFile(root, temp, currDir);
    } else {
      Output.println(tree);
      return tree;
    }
  }

  /**
   * This method obtains the tree diagram as a string starting from the root
   * directory. It will also be called recursively to get the sub-tree of all
   * the child directories.
   * 
   * @param dir - the root directory of the tree or any child directory that is
   *        the root of a sub-tree
   * @param treeLevel - the current level of the tree
   * @param tree - the string that will be used to stores the tree diagram
   * @return tree - the string of the tree diagram
   */
  private String getTree(Directory dir, int treeLevel, String tree) {
    // get the output of the directory
    tree = addItemToTree(dir.getDirectoryName(), treeLevel, tree);

    // get the output of all the child directories and their content
    ArrayList<Directory> innerDirectories = dir.getInnerDirectories();
    for (int i = 0; i < innerDirectories.size(); i++) {
      tree = getTree(innerDirectories.get(i), treeLevel + 1, tree);
    }
    // get the output of all the child files
    ArrayList<File> innerFiles = dir.getInnerFiles();
    for (int i = 0; i < innerFiles.size(); i++) {
      tree =
          addItemToTree(innerFiles.get(i).getFileName(), treeLevel + 1, tree);
    }
    return tree;
  }

  /**
   * This method adds an item to the string that contains the tree diagram. It
   * also indents the string for every level of tree.
   * 
   * @param itemName - the name of the item that is going to be added to the
   *        output string
   * @param numOfIndent - the number of times the string will be indented
   * @param tree - the string of the tree diagram
   * @return tree - the string after adding the new item to the tree diagram
   */
  private String addItemToTree(String itemName, int numOfIndent, String tree) {
    // indent for every level of tree
    for (int i = 0; i < numOfIndent; i++) {
      tree += "\t";
    }
    // add the name of the directory or file to the tree diagram
    tree += itemName + "\n";
    return tree;
  }

}


