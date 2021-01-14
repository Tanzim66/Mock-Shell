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
 * The File class stores the information of a file including the file name and
 * content.
 */
public class File implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  // name: The name of the file
  private String name;

  // content: The content of the file
  private String content;

  // parentDirectory: The parent directory which this directory is inside of
  private Directory parentDirectory;

  /**
   * This is the constructor which sets the name of the file to fileName and its
   * content as empty string.
   * 
   * @param fileName - the name of the file
   * @param parentDir - the parent directory
   * @return none
   */
  public File(String fileName, Directory parentDir) {
    this.name = fileName;;
    this.content = "";
    this.parentDirectory = parentDir;
  }

  /**
   * This method returns the parent directory.
   * 
   * @param none
   * @return the parent directory
   */
  public Directory getParentDirectory() {
    return this.parentDirectory;
  }

  /**
   * This method returns the name of a file.
   * 
   * @param none
   * @return the file name
   */
  public String getFileName() {
    return this.name;
  }

  /**
   * This method sets the name of a file to the string fileName
   * 
   * @param fileName - the name which method will set as the file name
   * @return none
   */
  public void setFileName(String fileName) {
    this.name = fileName;
  }

  /**
   * This method sets the content of a file to the string content
   * 
   * @param content - the content which method will set as the content of file
   * @return none
   */
  public void setFileContent(String content) {
    this.content = content;
  }

  /**
   * This method returns the content of a file
   * 
   * @param none
   * @return the content of the file
   */
  public String getFileContent() {
    return this.content;
  }

  /**
   * This method compares an object to another File object outside of the
   * function and checks if they are equivalent.
   * 
   * @param o - the object
   * @return true if the two objects are equal, false if they are not equal or
   *         the object o does not exist or it is not an instance of File
   */
  public boolean equals(Object o) {
    // return false if the object o does not exist or it is not the instance of
    // File
    if (o == null || (!(o instanceof File))) {
      return false;
    }
    // check if the two File objects have the same file name and file contents
    File file = (File) o;
    if (!file.getFileName().equals(getFileName())
        || !file.getFileContent().equals(getFileContent())) {
      return false;

    }
    return true;
  }
}
