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
package driver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import commands.Commands;
import containers.CurrentDirectory;
import containers.Directory;
import containers.DirectoryStack;
import containers.UserInputList;
import system.FileSystem;
import system.Output;
import system.UserInput;

/**
 * The JShell class is responsible for running the entire JShell program. It
 * also stores informations such as the file system, current working directory,
 * directory stack, and a list of all recent user inputs.
 */
public class JShell implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  // fs: The file system
  private FileSystem fs;

  // currDir: Current working directory
  private CurrentDirectory currDir;

  // dirStack: The directory stack that saves all current directories
  private DirectoryStack dirStack;

  // userInputList: A string array list of all recent user inputs
  private UserInputList userInputList;

  /**
   * This is the constructor which initializes the file system, current
   * directory, directory stack, and list of all recent user inputs when the
   * JShell program starts.
   * 
   * @param none
   * @return none
   */
  public JShell() {
    this.fs = FileSystem.createFileSystemInstance();
    this.currDir = new CurrentDirectory(this.fs.getRootDirectory(),
        this.fs.getRootDirectory().getDirectoryName());
    this.dirStack = new DirectoryStack();
    this.userInputList = new UserInputList();
  }

  /**
   * This method sets fs as the current file system.
   * 
   * @param fs - the file system that will be set as the current file system
   * @return none
   */
  public void setFileSystem(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * This method sets currDir as the new current working directory.
   * 
   * @param currDir - the directory that will be set as the current directory
   * @return none
   */
  public void setCurrDir(CurrentDirectory currDir) {
    this.currDir = currDir;
  }

  /**
   * This method sets dirStack as the new directory stack.
   * 
   * @param dirStack - the new directory stack
   * @return none
   */
  public void setDirStack(DirectoryStack dirStack) {
    this.dirStack = dirStack;
  }

  /**
   * This method sets userInputList as the new user input list.
   * 
   * @param userInputList - the new user input list
   * @return none
   */
  public void setUserInputList(UserInputList userInputList) {
    this.userInputList = userInputList;
  }

  /**
   * This method returns the root directory stored in the file system.
   * 
   * @param none
   * @return the root directory
   */
  public Directory getRootDirectory() {
    return this.fs.getRootDirectory();
  }

  /**
   * This method returns the current working directory.
   * 
   * @param none
   * @return current working directory
   */
  public CurrentDirectory getCurrDirObj() {
    return this.currDir;
  }

  /**
   * This method returns the directory stack.
   * 
   * @param none
   * @return the directory stack
   */
  public DirectoryStack getDirStack() {
    return this.dirStack;
  }

  /**
   * This method returns the list of all recent user inputs.
   * 
   * @param none
   * @return the list of all recent user inputs
   */
  public UserInputList getUserInputList() {
    return this.userInputList;
  }

  /**
   * This method returns the file system.
   * 
   * @param none
   * @return the file system
   */
  public FileSystem getFileSystem() {
    return this.fs;
  }

  /**
   * This method keeps the JShell program running. It takes the processed input
   * and execute the corresponding commands using a hash map.
   */
  public static void main(String[] args) {

    JShell js = new JShell();
    // string array for storing the command and parameters
    String[] cmdAndParam;
    // the hash map that stores the name of class to run for all valid commands
    HashMap<String, String> commandHashMap = new HashMap<String, String>();
    initializeHashMapWithCommands(commandHashMap);

    while (true) {
      // print current working directory
      Output.displayCurrentDirectory(js.getCurrDirObj());
      // obtain the user input and have it processed and stored in
      // this.userInputList using the UserInput class
      cmdAndParam = UserInput.getCommandAndParam(js.getUserInputList());
      // obtain the name of command to execute
      if (cmdAndParam != null) {
        String userCommand = cmdAndParam[0];

        try {
          // obtain the name of class to run for userCommand using
          // commandHashMap
          String className = commandHashMap.get(userCommand);
          // print an error message if it is an invalid command
          if (className == null) {
            Output.printInvalidCommandError(userCommand);
            continue;
          }
          // assign the class with the same name as className to commandClass
          Class<?> commandClass = Class.forName(className);
          Object instanceClass =
              commandClass.getDeclaredConstructor().newInstance();
          Commands cmd = (Commands) instanceClass;
          // execute the command
          cmd.executeCommand(js, cmdAndParam);

        } catch (InstantiationException e) {
          Output.printExceptionCaught();
        } catch (IllegalAccessException e) {
          Output.printExceptionCaught();
        } catch (IllegalArgumentException e) {
          Output.printExceptionCaught();
        } catch (InvocationTargetException e) {
          Output.printExceptionCaught();
        } catch (NoSuchMethodException e) {
          Output.printExceptionCaught();
        } catch (SecurityException e) {
          Output.printExceptionCaught();
        } catch (ClassNotFoundException e) {
          Output.printExceptionCaught();
        } catch (NullPointerException e) {
          Output.printExceptionCaught();
        } catch (RuntimeException e) {
          Output.printExceptionCaught();
        } catch (Exception e) {
          Output.printExceptionCaught();
        }
      }
    }
  }

  /**
   * This method uses hash map to assign the name of class to run for every
   * valid command inputed by user.
   * 
   * @param commandHashMap - the hash map that will store the name of class to
   *        run for all valid commands
   * @return none
   */
  private static void initializeHashMapWithCommands(
      HashMap<String, String> commandHashMap) {
    commandHashMap.put("exit", "commands.Exit");
    commandHashMap.put("mkdir", "commands.MakeDirectory");
    commandHashMap.put("cd", "commands.ChangeDirectory");
    commandHashMap.put("ls", "commands.List");
    commandHashMap.put("pwd", "commands.PrintWorkingDirectory");
    commandHashMap.put("pushd", "commands.PushDirectory");
    commandHashMap.put("popd", "commands.PopDirectory");
    commandHashMap.put("history", "commands.History");
    commandHashMap.put("cat", "commands.Concatenate");
    commandHashMap.put("curl", "commands.CopyURL");
    commandHashMap.put("echo", "commands.Echo");
    commandHashMap.put("man", "commands.Manual");
    commandHashMap.put("rm", "commands.Remove");
    commandHashMap.put("mv", "commands.Move");
    commandHashMap.put("cp", "commands.Copy");
    commandHashMap.put("search", "commands.Search");
    commandHashMap.put("tree", "commands.Tree");
    commandHashMap.put("saveJShell", "commands.SaveJShell");
    commandHashMap.put("loadJShell", "commands.LoadJShell");
  }
}
