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

import containers.Directory;

/**
 * The FileSystem class stores the root directory in the file system.
 */
public class FileSystem implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  // root: The root directory
  private Directory root;

  // fs: The file system
  private static FileSystem fs = null;

  /**
   * This is the constructor which initializes the root directory when a file
   * system is created.
   * 
   * @param none
   * @return none
   */
  private FileSystem() {
    this.root = new Directory("/");
  }

  /**
   * This method creates a file system instance if one does not already exist.
   * 
   * @param none
   * @return fs - the file system
   */
  public static FileSystem createFileSystemInstance() {
    if (fs == null) {
      fs = new FileSystem();
    }
    return fs;
  }

  /**
   * This method returns the root directory stored in the file system.
   * 
   * @param none
   * @return this.root - the root directory
   */
  public Directory getRootDirectory() {
    return this.root;
  }

  /**
   * This method compares an object to another FileSystem object outside of the
   * function and checks if they are equivalent.
   * 
   * @param o - the object
   * @return true if the two objects are equal, false if they are not equal or
   *         the object o does not exist or it is not an instance of FileSystem
   */
  public boolean equals(Object o) {
    // return false if the object o does not exist or it is not the instance of
    // FileSystem
    if (o == null || (!(o instanceof FileSystem))) {
      return false;
    }
    // check if the two FileSystem objects contain the same root directory
    FileSystem newFs = (FileSystem) o;
    if (!newFs.getRootDirectory().equals(getRootDirectory())) {
      return false;
    }
    return true;
  }
}
