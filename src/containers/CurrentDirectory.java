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
package containers;

/**
 * The CurrentDirectory class contains the current working directory as well as
 * its path.
 */
public class CurrentDirectory implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  // currDir: current working directory
  private Directory currDir;

  // currPath : the path to the current directory
  private String currPath;

  /**
   * This is the constructor which sets dir as the current directory and
   * currPath as the path to the current directory.
   * 
   * @param dir - the directory which method will set as the current directory
   * @param currPath - the path to the current directory
   * @return none
   */
  public CurrentDirectory(Directory dir, String currPath) {
    this.currDir = dir;
    this.currPath = currPath;
  }

  /**
   * This method sets a directory as the current directory.
   * 
   * @param currDir - the directory which method will set as the current working
   *        directory.
   * @return none
   */
  public void setCurrDir(Directory currDir) {
    this.currDir = currDir;
  }

  /**
   * This method returns the current directory.
   * 
   * @param none
   * @return the current directory
   */
  public Directory getCurrDir() {
    return this.currDir;
  }

  /**
   * This method sets a given path as the path to the current directory.
   * 
   * @param currDir - the path which method will set as the path to the current
   *        directory
   * @return none
   */
  public void setCurrPath(String currPath) {
    this.currPath = currPath;
  }

  /**
   * This method returns the path to the current directory.
   * 
   * @param none
   * @return the path to the current directory
   */
  public String getCurrPath() {
    return this.currPath;
  }

  /**
   * This method compares an object to another CurrentDirectory object outside
   * of the function and checks if they are equivalent.
   * 
   * @param o - the object
   * @return true if the two objects are equal, false if they are not equal or
   *         the object o does not exist or it is not an instance of
   *         CurrentDirectory
   */
  public boolean equals(Object o) {
    // return false if the object o does not exist or it is not the instance of
    // CurrentDirectory
    if (o == null || (!(o instanceof CurrentDirectory))) {
      return false;
    }
    // return false if the two CurrentDirectory objects are not the same
    CurrentDirectory currDir = (CurrentDirectory) o;
    if (!currDir.getCurrDir().equals(getCurrDir())) {
      return false;
    }
    return true;
  }
}
