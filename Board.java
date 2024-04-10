import doodlepad.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Jasper Burroughs
 * @since 4/10/24
 * This class will create the boggle board using doodlepad graphics
 */
public class Board {
    ArrayList<Rectangle> letterBoxes;
    ArrayList<String> chosenDice = new ArrayList<String>();
    /*
     * Board constructor initializes all shape objects for the graphics
     */
    public Board() {
        Pad background = new Pad(750, 900);
        background.setBackground(0, 155, 155);
        letterBoxes = new ArrayList<Rectangle>();
        for (int row = 0; row < 4;  row++) {
            for (int col = 0; col < 4; col++) {
                Rectangle r = new Rectangle(col*175+25, row*175+25, 175, 175);
                r.setFilled(false);
                r.setFontSize(30);
                letterBoxes.add(r);
            }
            
        }
    }

    /*
     * Randomize method to populate the squares with randomized letters
     * File io from W3 schools
     * dice Combos list from wordsrated
     */
    public void randomize() {
        ArrayList<String[]> dice = new ArrayList<String[]>();
        try {
            File myObj = new File("diceCombos.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String rawData = myReader.nextLine();
              dice.add(rawData.split("-"));
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        
        // Randomizing and displaying on board
        System.out.println(dice.size());
        for (String[] die : dice) {
            int index = dice.indexOf(die);
            int num = (int)(Math.random()*6); // 6 possible letters
            chosenDice.add(die[num]);
            letterBoxes.get(index).setText(die[num]);
        }
    }

    /*
     * Main method used for testing Board graphics
     */ 
    public static void main(String[] args) {
        Board b1 = new Board();
        b1.randomize();
    }
}