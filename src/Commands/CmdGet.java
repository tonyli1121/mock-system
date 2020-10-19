package Commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import fileSystems.File;
import fileSystems.FileSystem;

/**
 * This method performs the command get
 * 
 * @author Tianxiao Li
 *
 */
public class CmdGet implements ExecutableCmd {

  /**
   * This method returns the valid form of the link
   * @param link The website link to be transformed
   * @return null if not a valid url, an url representaion otherwise
   */
  private URL validUrl(String link) {
    try {
      URL url = new URL(link);
      url.toURI();
      return url;
    } catch (Exception e) {
      System.out.println("Invalid url link: " + link);
      return null;
    }
  }

  /**
   * add file to the current directory of the filesystem
   * @param fileName Name of the file
   * @param strIn Content of the file
   * @param fs The given file system
   * @return true if success, false otherwise
   */
  private boolean addFileAtCurrentDirectory(String fileName, String strIn, FileSystem fs) {
    File fileIn = new File(fileName, strIn);
    if (fs.addFileAtCurrentDirectory(fileIn)) {
      return true;
    }
    System.out.println("File with name: " + fileName + " already exist in the current directory");
    return false;
  }

  /**
   * Retrieve the contents from the url and add to the current directory of the file system
   * 
   * @param url Given link (URL object)
   * @param fs file system
   * @return true if success, false otherwise
   */
  private boolean addContentsFromUrlToCurrentDirectory(URL url, FileSystem fs) {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;
      String contents = "";
      int lastIndex = url.toString().lastIndexOf("/") + 1;
      String fileName = url.toString().substring(lastIndex);
      while ((inputLine = in.readLine()) != null)
        contents = contents + inputLine + "\n";
      contents = contents.trim();
      in.close();
      return addFileAtCurrentDirectory(fileName, contents, fs);
    } catch (Exception e) {
      // IOException
      System.out.println("[" + url.toString() + "] " + "Error: Given url is not accessible.");
      return false;
    }
  }
  
  /**
   * execute the command get
   * 
   * @param param the input parameters
   * @param fs the input fileSystem
   * @return true if successful
   */
  @Override
  public boolean execute(String param, FileSystem fs) {
    if (param.isEmpty()) {
      errorLoggerSystem.MissingArgument.printErrorMessage();
      return false;
    }
    if (param.contains(" ")) {
      errorLoggerSystem.ExtraArgument.printErrorMessage();
      return false;
    }
    URL url = validUrl(param);
    if (url == null)
      return false;
    return addContentsFromUrlToCurrentDirectory(url, fs);
  }

  /**
   * display the execution info after success
   * 
   * @param param the input parameters
   */
  @Override
  public void displayDone(String param) {
    // TODO Auto-generated method stub
    System.out.println("get");
    System.out.println(param);
  }

}

