package fileSystems;

/**
 * The File instance has the ability to store data, edited/overwrite/append to the data store in the
 * instance.
 * 
 * @author Xuezhi Yan
 */
public class File {

  /**
   * Content of the file.
   */
  private String data;
  /**
   * Name of the file.
   */
  private String name;

  /**
   * File class constructor, it initialize the data to an empty string.
   * 
   * @param nameIn the name of the file
   */
  public File(String nameIn) {
    this.data = "";
    this.name = nameIn;
  }

  /**
   * File class constructor, it initialize the data with an initial input data.
   * 
   * @param nameIn the name of the file
   * @param dataIn the initial data of file
   */
  public File(String nameIn, String dataIn) {
    this.data = dataIn;
    this.name = nameIn;
  }

  /**
   * The getter for data stored.
   * 
   * @return the data of the file
   */
  public String getData() {
    return this.data;
  }

  /**
   * The getter for the name of the file.
   * 
   * @return the name of the file.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Overwrites the data with the input data.
   * 
   * @param dataIn the data to replace with
   */
  public void overrideData(String dataIn) {
    this.data = dataIn;
  }

  /**
   * Appends to the data stored in the file.
   * 
   * @param dataIn the data to append
   */
  public void appendData(String dataIn) {
    this.data = this.data + "\n" + dataIn;
  }

  /**
   * Add a copy of this file to the target path.
   * 
   * @param path target path
   * @param fs the working fileSystem
   */
  public void copyTo(Path path, FileSystem fs) {
    fs.echoToFileWhole(path, this.name, this.data, false);
  }
}
