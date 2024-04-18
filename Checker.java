import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Checker {
    ArrayList<String> dictionary = new ArrayList<String>();
    /*
     * Constructor gets the dictionary from the file
     * File io from W3 schools
     */
    public Checker() {
        try {
            File myObj = new File("dictionary.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String rawData = myReader.nextLine();
              dictionary.add(rawData);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
    }
    /*
     * Checks the passed in word to make sure it is in the dictionary
     */
    public boolean checkWord(String str) {
        if (dictionary.contains(str) && str.length() >= 3) {
            return true;
        }
        return false;
    }
}
