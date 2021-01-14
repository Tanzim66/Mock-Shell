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

import java.util.ArrayList;
import java.util.Scanner;
import containers.UserInputList;

/**
 * The UserInput class obtains and processes all the user inputs.
 */
public class UserInput {

  // sc: An instance variable of Scanner class that is used to get user inputs.
  private static Scanner sc = new Scanner(System.in);

  /**
   * This method obtains an user input and if it is not empty, the method stores
   * it to the array list in History before splitting its command and parameters
   * by certain characters. The method then stores all the command and arrays
   * into a string array, where the command is stored at index 0 of the array
   * and parameters are stored at index 1 or above of the array.
   * 
   * @param userInputList - the list of all recent user inputs
   * @return splits - a string array of command and parameters
   */
  public static String[] getCommandAndParam(UserInputList userInputList) {
    String input = sc.nextLine().trim();

    // if the user input is not empty, add the full user input to the array
    // list in History before modifying it
    if (!input.trim().equals("")) {
      ArrayList<String> inputList = userInputList.getRecentCommands();
      inputList.add(input);
      userInputList.setRecentCommands(inputList);
    } else {
      // if the user input is empty, make sure the returned array is just the
      // empty string
      String[] temp = {""};
      return temp;
    }

    // Only time this function returns null is when the input has an odd
    // number of quotes, which means that at least one of the quotes must not
    // be closed
    if (numQuotes(input) % 2 != 0) {
      System.out.println("All quotes must be closed");
      return null;
    }

    // if the command the user desires is an STDOUT that can be redirected,
    // separate the first occurrence of the redirection arrow
    if (input.length() > 0 && input.indexOf(">") != -1) {
      input = separateByArrows(input).trim();
    }

    // return the string split by white space
    return splitStringBySpace(input);

  }

  /**
   * This method counts and returns the number of '"' symbols in a string.
   * 
   * @param str - the string which method will use count the number of quotes
   * @return count - the number of quotes in str
   */
  public static int numQuotes(String str) {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '"') {
        count++;
      }
    }
    return count;
  }


  /**
   * This method checks if a given string str is surrounded by two quotes
   * 
   * @param str - the string which method will check if it is surrounded by two
   *        quotes
   * @return true if str is surrounded by two quotes, false otherwise
   */
  public static boolean surroundedByTwoQuotes(String str) {
    // return true if str has at least two quotes and it is surrounded by two
    // quotes
    if (str.length() >= 2) {
      return str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"';
    }
    return false;
  }

  /**
   * This method replaces the redirection symbols that are not inside quotes
   * with the same redirection symbol but with site space around them (eg ">" =
   * " > ")
   * 
   * @param str - the initial string
   * @return a string with the redirection symbol surrounded by white space
   */
  public static String separateByArrows(String str) {
    int length = str.length();
    int indicator = 0;
    for (int i = 0; i < length; i++) {
      if (str.charAt(i) == '"')
        indicator++;
      if (str.charAt(i) == '>' && indicator % 2 != 1) {
        if (i + 1 < length && str.charAt(i + 1) == '>') {
          if (i + 2 < length) {
            return str.substring(0, i) + " >> " + str.substring(i + 2, length);
          } else
            return str.substring(0, i) + " >>";
        } else {
          if (i + 1 >= length)
            return str.substring(0, i) + " >";
          else
            return str.substring(0, i) + " > " + str.substring(i + 1, length);
        }
      }
    }
    return str;
  }

  /**
   * This method checks if a string contains only numeric characters.
   * 
   * @param str - a string which the method will check if it contains only
   *        numeric characters
   * @return true if string contains only numeric characters, false otherwise
   */
  public static boolean isNumeric(String str) {
    // return true if the string contains only numeric characters
    try {
      Integer.parseInt(str);
      return true;

      // return false otherwise
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * This method splits a string by space and stores them in a string array.
   * However it ignores any characters that are surrounded by double quotes,
   * which means it will not split any characters that are inside the double
   * quotes by space.
   * 
   * @param userInput - the string which method will split by space
   * @return strArray - a string array that contains all the strings that are
   *         split by the method
   */
  public static String[] splitStringBySpace(String userInput) {
    // split userInput by space but ignore characters inside the double quotes
    String[] strArray = userInput.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    return strArray;
  }

  /**
   * This method looks for the ">" or ">>" symbols in a string array and returns
   * its index in the array.
   * 
   * @param str - the string array which method will get the index of arrow
   * @return index - the index of arrow in str
   */
  public static int getFirstIndexOfArrow(String[] str) {
    int index = -1;
    // traverse the string array to find the ">" or ">>" symbols
    for (int i = 0; i < str.length; i++) {
      if (str[i].equals(">") || str[i].equals(">>")) {
        index = i;
        break;
      }
    }
    return index;
  }

  /**
   * This method removes the ">" or ">>" symbols from a string array and returns
   * a new string array without the arrows.
   * 
   * @param str - the string array which method will remove the arrows from
   * @return the new string array without the arrows
   */
  public static String[] getCmdAndParamWithoutArrow(String[] str, int index) {
    String[] temp = new String[index];
    System.arraycopy(str, 0, temp, 0, index);
    return temp;
  }

}
