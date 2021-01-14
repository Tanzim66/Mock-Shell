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

import containers.CurrentDirectory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import driver.JShell;
import system.Output;
import system.PathHandler;
import system.UserInput;

/**
 * The CopyURL retrieves file from an URL and add it to the current working
 * directory.
 */
public class CopyURL extends Commands {

  /**
   * This method checks if user input is valid before retrieving the file from
   * the URL. It prints an error message if user input is invalid.
   * 
   * @param js - JShell which stores the locations of root directory and current
   *        directory, the directory stack, a list of recent user inputs.
   * @param cmdAndParam - the user input
   * @return "Success" if it successfully retrieves the file from URL and adds
   *         it to the current directory, "Error" if it fails
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

    try {
      URL url = new URL(cmdAndParam[1]);
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(url.openStream()));
      StringBuilder contents = new StringBuilder();

      // get the content of the file
      String line = reader.readLine();
      while (line != null) {
        contents.append(line);
        line = reader.readLine();
        if (line != null) {
          contents.append('\n');
        }
      }

      CurrentDirectory currDir = js.getCurrDirObj();
      String fileName =
          PathHandler.editItemName(PathHandler.getItemName(cmdAndParam[1]));

      // print an error message and return "Error" if file is not found
      if (fileName.equals("")) {
        Output.printFileNotFoundError(fileName);
        return "Error";
      }
      // check if current directory already contains a file with fileName
      // before adding the file
      if (currDir.getCurrDir().getChildFile(fileName) == null
          && currDir.getCurrDir().getChildDirectory(fileName) == null) {

        currDir.getCurrDir().addFileInside(fileName);
        currDir.getCurrDir().getChildFile(fileName)
            .setFileContent(contents.toString());
      } else {
        Output.printItemAlreadyExistsError(
            PathHandler.getFullPath(currDir.getCurrDir()), fileName);
        return "Error";
      }

    } catch (MalformedURLException e) {
      Output.printInvalidURLError();
      return "Error";
    } catch (IOException e) {
      Output.printInvalidURLError();
      return "Error";
    }

    return "Success";
  }
}
