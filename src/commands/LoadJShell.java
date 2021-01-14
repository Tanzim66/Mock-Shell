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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.UserInput;

/**
 * The LoadJShell class loads a previous shell session that is saved in a file
 * on the user's computer.
 */
public class LoadJShell extends Commands implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  /**
   * This method checks if user input is valid before executing the command. It
   * prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully loads a previous shell session,
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

    // print an error message and do not load the shell session if user has
    // already run some commands before loading
    if (js.getUserInputList().getRecentCommandsSize() != 1) {
      Output.printNotFirstCommandError();
      return "Error";
    }
    // print and error message an return "Error" if user input is invalid
    if (cmdAndParam.length != 2) {
      Output.printInvalidArgumentSizeError(cmdAndParam, "1");
      return "Error";
    }
    if (!PathHandler.checkValidItemName(cmdAndParam[1])) {
      return "Error";
    }
    // run the command
    return loadJShell(js, cmdAndParam);
  }

  /**
   * This method loads a previous shell session specified by user that is saved
   * in a file on the user's computer.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully loads a previous shell session,
   *         "Error" if it fails
   */
  private String loadJShell(JShell js, String[] cmdAndParam) {
    JShell newJShell = null;
    try {
      // retrieve the shell session from a given file
      FileInputStream fileIn = new FileInputStream(cmdAndParam[1]);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      newJShell = (JShell) in.readObject();
      in.close();
      fileIn.close();
      js.setFileSystem(newJShell.getFileSystem());
      js.setCurrDir(newJShell.getCurrDirObj());
      js.setDirStack(newJShell.getDirStack());
      js.setUserInputList(newJShell.getUserInputList());
      // add the LoadJShell command to the user input list of the previous shell
      // session
      String temp = "";
      for (String str : cmdAndParam) {
        temp += " " + str;
      }
      temp = temp.trim();
      js.getUserInputList().getRecentCommands().add(temp);
      Output.printLoadFile(cmdAndParam[1]);
      return "Success";

    } catch (FileNotFoundException c) {
      Output.printFileLocationNotFoundError();
      return "Error";
    } catch (IOException i) {
      Output.printExceptionCaught();
      return "Error";
    } catch (ClassNotFoundException e) {
      Output.printExceptionCaught();
      return "Error";
    } catch (Exception e) {
      Output.printExceptionCaught();
      return "Error";
    }

  }



}
