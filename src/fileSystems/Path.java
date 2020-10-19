package fileSystems;

import java.util.ArrayList;

/**
 * Path is the class that acts as the route of a file system.
 *
 * @author Xuezhi Yan
 */
public class Path {

  /**
   * Layers stores a path of directories.
   */
  private ArrayList<String> layers;

  /**
   * A path object is represented in layers of directories.
   */
  public Path() {
    this.layers = new ArrayList<String>();
  }
  
  /**
   * another constructor overload that accepts an initial path
   * 
   * @param path the initial path
   */
  public Path(String path) {
    this.layers = new ArrayList<String>();
    this.readPath(path);
  }


  /**
   * Return a copy of the path.
   */
  public Path clone() {
    Path temp = new Path();
    temp.readPath(this.toString());
    return temp;
  }

  /**
   * Return the string representation of the path.
   */
  @Override
  public String toString() {
    String result = String.join(Lib.seperator, this.layers);
    if(result.equals("")) {
      return result;
    }
    return result + Lib.seperator;
  }

  /**
   * Return the true if the string representation is the same.
   */
  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }

  /**
   * Check if the path is empty.
   * 
   * @return true is the layer contains no element, otherwise false
   */
  public boolean isEmpty() {
    return this.layers.isEmpty();
  }

  /**
   * Return the path represented as a array of directory names.
   * 
   * @return a list of directory names
   */
  public ArrayList<String> getPath() {
    return this.layers;
  }

  /**
   * Chop the first element from the layers.
   * 
   * @return null if the path is empty, otherwise the first element of the layers.
   */
  public String chop() {
    if (this.layers.isEmpty()) {
      return null;
    }
    return this.layers.remove(0);
  }

  /**
   * Read the string into the layers of the path.
   * 
   * @param strIn the string to read from
   */
  public void readPath(String strIn) {
    if (strIn == "") {
      this.layers.clear();
      return;
    }
    this.layers.clear();
    String[] result = strIn.split(Lib.seperator);
    for (String eachPath : result) {
      if (!eachPath.isEmpty()) {
        this.layers.add(eachPath);
      }
    }
  }

  /**
   * Go to the parent path by removing the last element of the layers.
   * 
   * @return null if the path is empty, otherwise the last element of the layers.
   */
  public String previousPath() {
    if (this.layers.isEmpty()) {
      return null;
    }
    return this.layers.remove(this.layers.size() - 1);
  }

  /**
   * Go to the next path by appending to the layers.
   * 
   * @param nextIn the directory name to append.
   */
  public void nextPath(String nextIn) {
    this.layers.add(nextIn);
  }
  
  /**
   * Combine the path name path into the original path.
   * 
   * @param path the path needed to be combined
   * @return the path after combining
   */
  public Path combine(Path path) {
    Path cont = this.clone();
    for(String entry:path.getPath()) {
      cont.nextPath(entry);
    }
    return cont;
  }
  
  /**
   * Distinguish whether the input path is a global path, and make return a new global path if not.
   * 
   * @param fs the fileSystem to get current path
   * @param path the input path
   * @return a global path
   */
  public static Path makeFullPath(FileSystem fs, String path) {
    Path temp = new Path(path);
    if(!path.startsWith(Lib.seperator)) {
      return new Path(fs.getCurrentPath()).combine(temp);
    }
    return temp;
  }
}
