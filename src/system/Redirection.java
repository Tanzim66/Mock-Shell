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

/*
 * The Redirection class allows users to redirect the output produced by certain
 * commands to the content of files.
 */
public class Redirection {

  /**
   * This method locates the file specified by user and modifies its content by
   * either appending a string to it or overwriting it with the string. If no
   * file with the given name exists, this method creates a new file with the
   * given name containing the string as its contents. If no file name is given,
   * it prints the string.
   * 
   * Precondition: params must have three elements, the first one is the message
   * to be redirected, the second being the redirection symbols ">" or ">>" and
   * the third one is the path that message is being redirected to
   * 
   * @param root - the root directory
   * @param params - the user input without the command (must have 3 components)
   *        The first component is the text to be written in the file, then the
   *        redirection arrows and then the file path
   * @param currDir - current working directory
   * @return String - "Redirection Error" if something bad happens and
   *         "Redirection Successful" followed by the specific task of
   *         redirection (append, overwrite or create new file) if redirection
   *         was successful.
   */
  public static String redirectToFile(Directory root, String[] params,
      CurrentDirectory currDir) {

    String path = params[2];
    File file;

    String fileName = PathHandler.getItemName(path);

    if (!PathHandler.checkValidItemName(fileName)) {
      return "Redirection Error";
    }

    Directory dir = PathHandler.getDirectoryBefore(root, currDir, path);

    // if the parent directory does not exist, print an error message and
    // return "Redirection Error"
    if (dir == null) {
      Output.printPathDoesNotExistError(path);
      return "Redirection Error";
    }
    // if a child directory with the same name already exists in the parent
    // directory, print an error message and return "Redirection Error"
    if (dir.getChildDirectory(fileName) != null) {
      Output.printDirectoryAlreadyExistsError(fileName);
      return "Redirection Error";
    }
    // if no file with the given name exists, create a new file with that name
    // inside the current directory containing the first argument as its
    // content. In this case the method does not care whether user wants to
    // append or overwrite the file content
    file = dir.getChildFile(fileName);
    if (file == null) {
      dir.addFileInside(fileName);
      file = dir.getChildFile(fileName);
      overwrite(params[0], file);
      return "Redirection Successful: Created new file";
    }

    // check whether user wants to append or overwrite the file depending on
    // the second argument
    else {
      if (params[1].equals(">")) {
        overwrite(params[0], file);
        return "Redirection Successful: Existing file overwritten";
      } else {
        append(params[0], file);
        return "Redirection Successful: Existing file appended";
      }
    }

  }

  /**
   * This method appends a string to the content of a file.
   * 
   * @param text - the string which method will append to the content of file
   * @param file - the file which method will modify the content of
   * @return none
   */
  private static void append(String text, File file) {
    // insert the text in a new line to the content of file
    file.setFileContent(file.getFileContent() + "\n" + text);
  }

  /**
   * This method replaces the content of a file with a string.
   * 
   * @param text - the string which method will replace the file content with
   * @param file - the file which method will modify the content of
   * @return none
   */
  private static void overwrite(String text, File file) {
    file.setFileContent(text);
  }


}
