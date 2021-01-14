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

import java.util.ArrayList;

/**
 * The Directory class contains directory path and files in a directory.
 */
public class Directory implements java.io.Serializable {

  // 1L: represents the class version for serialization
  private static final long serialVersionUID = 1L;

  // name: The name of the directory
  private String name;

  // innerDirectories: An array list of all directories inside this directory
  private ArrayList<Directory> innerDirectories = new ArrayList<Directory>();

  // innerFiles: An array list of all files inside this directory
  private ArrayList<File> innerFiles = new ArrayList<File>();

  // parentDirectory: The parent directory which this directory is inside of
  private Directory parentDirectory;


  /**
   * This is the constructor which sets the name of the directory to dirName
   * when a directory is created.
   * 
   * @param dirName - the name of the directory
   * @return none
   */
  public Directory(String dirName) {
    this.name = dirName;
  }

  /**
   * This is the constructor which sets the name of the directory to dirName and
   * its parent directory to parentDir when a directory is created.
   * 
   * @param dirName - the name of the directory
   * @param parentDir - the parent directory
   * @return none
   */
  public Directory(String dirName, Directory parentDir) {
    this.name = dirName;
    this.parentDirectory = parentDir;
  }

  /**
   * This method checks if there are any directories or files inside the
   * directory.
   * 
   * @param none
   * @return true - if the directory is empty
   * @return false - if the directory is not empty
   */
  public boolean isEmpty() {
    return this.innerDirectories.size() == 0 && this.innerFiles.size() == 0;
  }

  /**
   * This method returns the name of the directory
   * 
   * @param none
   * @return the name of the directory
   */
  public String getDirectoryName() {
    return this.name;
  }

  /**
   * This method sets the name of the directory to dirName
   * 
   * @param dirName - the name which method will change the name of the
   *        directory to
   * @return none
   */
  public void setDirectoryName(String dirName) {
    this.name = dirName;
  }

  /**
   * This method returns the array list of all directories inside the directory
   * 
   * @param fileName - none
   * @return the array list of all directories inside the directory
   */
  public ArrayList<Directory> getInnerDirectories() {
    return this.innerDirectories;
  }

  /**
   * This method returns the array list of all files inside the directory
   * 
   * @param fileName - none
   * @return the array list of all files inside the directory
   */
  public ArrayList<File> getInnerFiles() {
    return this.innerFiles;
  }

  /**
   * This method creates a new directory and adds it inside the directory.
   * 
   * @param dirName - the name of the new directory
   * @return none
   */
  public void addDirectoryInside(String dirName) {
    this.innerDirectories.add(new Directory(dirName, this));
  }

  /**
   * This method creates a new file and adds it inside the directory.
   * 
   * @param fileName - the name of the new file
   * @return none
   */
  public void addFileInside(String fileName) {
    this.innerFiles.add(new File(fileName, this));
  }

  /**
   * This method returns a child directory specified by user inside the
   * directory. It returns null if the directory is empty or the child directory
   * does not exist inside the directory.
   * 
   * @param dirName - the name of the directory specified by user
   * @return the child directory specified or null if the directory is empty or
   *         the child directory does not exist inside the directory
   */
  public Directory getChildDirectory(String dirName) {
    if (this.innerDirectories == null) {
      return null;
    }
    // loop through the innerDirectories array list to find the directory
    // specified by user
    for (int i = 0; i < this.innerDirectories.size(); i++) {
      if (this.innerDirectories.get(i).getDirectoryName().equals(dirName)) {
        return this.innerDirectories.get(i);
      }
    }
    // return null if the directory specified by user is not found
    return null;
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
   * This method returns a file specified by user inside the directory. It
   * returns null if the directory is empty or the file does not exist inside
   * the directory.
   * 
   * @param fileName - the name of the file specified by user
   * @return the file specified by user or null if the directory is empty or the
   *         file does not exist inside the directory
   */
  public File getChildFile(String fileName) {
    if (this.innerFiles == null) {
      return null;
    }
    // loop through the innerFiles array list to find the file specified by user
    for (int i = 0; i < this.innerFiles.size(); i++) {
      if (this.innerFiles.get(i).getFileName().equals(fileName)) {
        return this.innerFiles.get(i);
      }
    }
    // return null if the file specified by user is not found
    return null;
  }

  /**
   * This method compares an object to another Directory object outside of the
   * function and checks if they are equivalent.
   * 
   * @param o - the object
   * @return true if the two objects are equal, false if they are not equal or
   *         the object o does not exist or it is not an instance of Directory
   */
  public boolean equals(Object o) {
    // return false if the object o does not exist or it is not the instance of
    // Directory
    if (o == null || (!(o instanceof Directory))) {
      return false;
    }
    Directory dir = (Directory) o;
    // return false if the two Directory objects do not have the same name
    if (!dir.getDirectoryName().equals(getDirectoryName())) {
      return false;
    }
    // check if the two Directory objects have the same child
    ArrayList<Directory> innerDirsOldDir = getInnerDirectories();
    ArrayList<Directory> innerDirsNewDir = dir.getInnerDirectories();
    ArrayList<File> innerFilesOldDir = getInnerFiles();
    ArrayList<File> innerFilesNewDir = dir.getInnerFiles();
    int maxDir = Math.max(innerDirsOldDir.size(), innerDirsNewDir.size());
    int maxFile = Math.max(innerFilesOldDir.size(), innerFilesNewDir.size());
    for (int dirIndex = 0; dirIndex < maxDir; dirIndex++) {
      if (!innerDirsOldDir.get(dirIndex)
          .equals(innerDirsNewDir.get(dirIndex))) {
        return false;
      }
    }
    for (int fileIndex = 0; fileIndex < maxFile; fileIndex++) {
      if (!innerFilesOldDir.get(fileIndex)
          .equals(innerFilesNewDir.get(fileIndex))) {
        return false;
      }
    }
    return true;

  }
}
