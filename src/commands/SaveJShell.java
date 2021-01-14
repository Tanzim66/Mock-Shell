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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.UserInput;

/**
 * The SaveJShell class saves the current shell session in a file on user's
 * computer.
 */
public class SaveJShell extends Commands implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully saves the current shell session,
   *         "Error" if it fails
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
    if (cmdAndParam.length != 2) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "1");
      return "Error";
    }
    if (!PathHandler.checkValidItemName(cmdAndParam[1])) {
      return "Error";
    }
    // run the command
    return saveJShell(js, cmdAndParam);
  }


  /**
   * This method proceeds to save current shell session in a file on user's
   * computer. If the file already exists on user's computer, overwrite it with
   * the current shell session.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully saves the current shell session,
   *         "Error" if it fails
   */
  private String saveJShell(JShell js, String[] cmdAndParam) {
    try {
      // save current shell session in a file on user's computer
      FileOutputStream fileLocation = new FileOutputStream(cmdAndParam[1]);
      ObjectOutputStream newFile = new ObjectOutputStream(fileLocation);
      newFile.writeObject(js);
      newFile.close();
      fileLocation.close();
      Output.printSavedFile(cmdAndParam[1]);
      return "Success";
    } catch (FileNotFoundException f) {
      Output.printFileLocationNotFoundError();
      return "Error";
    } catch (IOException e) {
      Output.printExceptionCaught();
      return "Error";
    } catch (Exception e) {
      Output.printExceptionCaught();
      return "Error";
    }
  }

}
