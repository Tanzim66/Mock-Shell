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

/**
 * The Output class prints any text and error messages.
 */
public class Output {

  /**
   * This method prints an integer and stays on the same line.
   * 
   * @param num - the integer which the method is going to print
   * @return none
   */
  public static void print(int num) {
    System.out.print(num);
  }

  /**
   * This method prints any text and stays on the same line.
   * 
   * @param text - the text which the method is going to print
   * @return none
   */
  public static void print(String text) {
    System.out.print(text);
  }

  /**
   * This method prints any text then goes to a new line.
   * 
   * @param text - the text which the method is going to print
   * @return none
   */
  public static void println(String text) {
    System.out.println(text);
  }

  /**
   * This method displays the current working directory followed by a '>'.
   * 
   * @param currDir - current working directory.
   * @return none
   */
  public static void displayCurrentDirectory(CurrentDirectory currDir) {
    print(currDir.getCurrPath() + "> ");
  }

  /**
   * This method prints an error message for input with Invalid argument size.
   * This method takes in only one valid argument size.
   * 
   * @param input - the user input with invalid argument size
   * @param argument - the argument size the function must take in
   * @return none
   */
  public static void printInvalidArgumentSizeError(String[] input,
      String argument) {
    println("Error: User input has " + (input.length - 1)
        + " argument(s). This function must take in " + argument
        + " argument(s).");
  }

  /**
   * This method prints an error message if the name of a file/directory is
   * invalid
   * 
   * @param name - the name of the invalid item
   * @return none
   */
  public static void printInvalidNameError(String name) {
    println("Error: Invalid directory or file name \"" + name + "\".");
  }

  /**
   * This method prints an error message if the argument type is not an integer.
   * 
   * @param none
   * @return none
   */
  public static void printInvalidParamTypeError() {
    println("Error: Argument must be an integer.");
  }

  /**
   * This method prints an error message if user inputs a non-positive integer.
   * 
   * @param none
   * @return none
   */
  public static void printInvalidInteger() {
    println("Error: Argument must be a positive integer.");
  }

  /**
   * This method prints an error message for an invalid given directory or if
   * the directory already exists.
   * 
   * @param path - the path to the directory
   * @return none
   */
  public static void printCreateDirectoryError(String path) {
    println("Error: Could not create the directory " + path
        + " (invalid path or directory already exists).");
  }

  /**
   * This method prints a message if the directory was successfully created.
   * 
   * @param path - the path to the directory that has been created
   * @return none
   */
  public static void printDirectoryCreated(String path) {
    println("Successfully created the directory " + path + ".");
  }

  /**
   * This method prints an error message for a path that does not exist.
   * 
   * @param path - the path that does not exist
   * @return none
   */
  public static void printPathDoesNotExistError(String path) {
    println("Error: Path " + path + " does not exist.");
  }

  /**
   * This method prints an error message for a file that cannot be found.
   * 
   * @param fileName - the name of file that cannot be found
   * @return none
   */
  public static void printFileNotFoundError(String fileName) {
    println("Error: The file \"" + fileName + "\" could not be found.");
  }

  /**
   * This method prints an error message if an invalid command was given.
   * 
   * @param commandName - the given command
   * @return none
   */
  public static void printInvalidCommandError(String commandName) {
    println(commandName + ": command not found");
  }

  /**
   * This method prints an error message for an empty directory stack.
   * 
   * @param none
   * @return none
   */
  public static void printEmptyStackError() {
    println("popd: directory stack empty");
  }

  /**
   * This method prints an error message when it expected a directory argument
   * but got a file argument instead.
   * 
   * @param none
   * @return none
   */
  public static void printExpectedDirGotFileError() {
    println("Error: Expected a directory argument, instead got a file "
        + "argument.");
  }

  /**
   * This method prints an error message when an unexpected error is caught.
   * 
   * @param none
   * @return none
   */
  public static void printExceptionCaught() {
    println("Something bad happened.");
  }

  /**
   * This method prints an error message when user inputs anything other than
   * '>' or '>>' for the second argument of the echo command.
   * 
   * @param none
   * @return none
   */
  public static void printEchoArrowError() {
    println("Error: Second argument requires '>' or '>>' only");
  }

  /**
   * This method prints an error message for a string in the argument that is
   * not surrounded by two double quotes.
   * 
   * @param none
   * @return none
   */
  public static void printDoesNotContainTwoQuotesError() {
    println("Error: Input string must have exactly two quotes in the first "
        + "argument");
  }

  /**
   * This method prints an error message for a file with a name that already
   * exists.
   * 
   * @param fileName - the name of the file that already exists
   * @return none
   */
  public static void printFileAlreadyExistsError(String fileName) {
    println("Error: A file with name \"" + fileName + "\" already exists");
  }

  /**
   * This method prints an error message for a directory with a name that
   * already exists.
   * 
   * @param dirName - the name of the directory that already exists
   * @return none
   */
  public static void printDirectoryAlreadyExistsError(String dirName) {
    println("Error: A directory with name \"" + dirName + "\" already exists");
  }

  /**
   * This method prints an error message if user tries to remove the current
   * working directory.
   * 
   * @param dirName - the name of the directory that cannot be removed
   * @return none
   */
  public static void printRemoveCurrentDirectoryError(String dirName) {
    println("Error: Cannot remove the directory " + dirName
        + " containing the current directory.");
  }

  /**
   * This method prints a message indicating that a directory has been
   * successfully removed.
   * 
   * @param dirName - the name of the directory that is removed
   * @return none
   */
  public static void printDirectoryRemoved(String dirName) {
    println("The directory " + dirName + " has been successfully removed.");
  }

  /**
   * This method prints an error message if user is tries to move a directory to
   * a file.
   * 
   * @param none
   * @return none
   */
  public static void printMovingDirectoryToFileError() {
    println("Error: Cannot move a directory to a file.");

  }

  /**
   * This method prints an error message if user tries to move or copy a file to
   * a directory that does not exists.
   * 
   * @param command - the command user is trying to execute. It is either move
   *        or copy
   * @return none
   */
  public static void printModifyFileToNewDirectoryError(String command) {
    println(
        "Error: Cannot " + command + " a file to a non-existing directory.");

  }

  /**
   * This method prints an error message if the arguments for redirection is
   * invalid.
   * 
   * @param none
   * @return none
   */
  public static void printRedirectionParameterSizeError() {
    println("Error: Must have exactly one argument following redirection");
  }

  /**
   * This method prints an error message if user does not copy a directory to
   * another existing directory.
   * 
   * @param none
   * @return none
   */
  public static void printInvalidDestinationForDirError() {
    println("Error: Can only copy directory to an existing directory.");
  }

  /**
   * This method prints an error message if user tries to move or copy a parent
   * directory into one of its child directories.
   * 
   * @param command - the command user is trying to execute. It is either move
   *        or copy
   * @return none
   */
  public static void printModifyChildDirError(String command) {
    println("Error: Cannot " + command + " a parent directory into its children"
        + " directories.");
  }

  /**
   * This method prints an error message if user tries to move or copy an item
   * to itself.
   * 
   * @param command - the command user is trying to execute. It is either move
   *        or copy
   * @return none
   */
  public static void printModifyItemToItselfError(String command) {
    println("Error: Cannot " + command + " an item to itself.");
  }

  /**
   * This method prints an error message for an item that already exists; the
   * item can be a directory or a file.
   * 
   * @param dirPath - the path to the directory that contains the item with the
   *        name
   * @param name - the item name that already exists
   * @return none
   */
  public static void printItemAlreadyExistsError(String dirPath, String name) {
    println("Error: There already exists an item with name \"" + name + "\" in "
        + dirPath + " .");
  }

  /**
   * This method prints an error message if the input for search does not
   * include \"-type\" before type of item.
   * 
   * @param none
   * @return none
   */
  public static void printSearchTypeMissingError() {
    println("Error: The input must have \"-type\" before type of item");
  }

  /**
   * This method prints an error message if the input for search does not
   * include \"-name\" before name of item.
   * 
   * @param none
   * @return none
   */
  public static void printSearchNameMissingError() {
    println("Error: The input must have \"-name\" before name of item");
  }

  /**
   * This method prints an error message if the input for search is not in the
   * correct format.
   * 
   * @param none
   * @return none
   */
  public static void printSearchParetersError() {
    println("Error: The input must be of the form \"path ... "
        + "-type [f/d] -name expression\" with atleast 5 parameters");
  }

  /**
   * This method prints an error message if user is trying to search an invalid
   * item.
   * 
   * @param none
   * @return none
   */
  public static void printSearchInvalidItemTypeError() {
    println("Error: Item type must be a file (f) or directory (d)");
  }

  /**
   * This method prints an error message if user is trying to get the manual of
   * more than one command.
   * 
   * @param none
   * @return none
   */
  public static void printManMultipleCommandsError() {
    println("Error: Man can only print manual of one command at a time");
  }

  /**
   * This method prints an error message for an invalid URL.
   * 
   * @param none
   * @return none
   */
  public static void printInvalidURLError() {
    println("Error: Invalid URL");
  }

  /**
   * This method prints a message indicating that a file has been saved in a
   * given path.
   * 
   * @param path - the path which the file has been saved in
   * @return none
   */
  public static void printSavedFile(String path) {
    println("Saved file " + path);

  }

  /**
   * This method prints a message indicating that a file has been loaded from a
   * given path.
   * 
   * @param path - the path which the file has been loaded from
   * @return none
   */
  public static void printLoadFile(String path) {
    println("Loaded file " + path);
  }

  /**
   * This method prints an error message if a file cannot be found.
   * 
   * @param none
   * @return none
   */
  public static void printFileLocationNotFoundError() {
    println("Error: File location not found");
  }

  /**
   * This method prints an error message if user tries to load to another JShell
   * after inputting commands in the current shell.
   * 
   * @param none
   * @return none
   */
  public static void printNotFirstCommandError() {
    println(
        "Error: loadJShell was not first command, please restart the shell.");
  }

  /**
   * This method prints an error message for a new path that does not exist.
   * 
   * @param none
   * @return none
   */
  public static void printNewPathDoesNotExistError() {
    println("Error: New path does not exist.");
  }

  /**
   * This method prints an error message for an expression that is not closed
   * under exactly two quotes.
   * 
   * @param none
   * @return none
   */
  public static void printExpressionDoesNotContainTwoQuotesError() {
    println("Error: Name expression must be closed unders quotes with exactly 2"
        + " quotes");
  }
}
