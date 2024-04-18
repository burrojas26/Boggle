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
    int size = 5;
    ArrayList<RoundRect> letterBoxes;
    ArrayList<String> chosenDice = new ArrayList<String>();
    ArrayList<Oval> selected = new ArrayList<Oval>();
    ArrayList<Line> connections = new ArrayList<Line>();
    String currStr = "";
    double tileSize;
    ArrayList<String> enteredWords = new ArrayList<String>();
    /*
     * Board constructor initializes all shape objects for the graphics
     */
    public Board() {
        Pad background = new Pad(750, 900);
        background.setBackground(0, 155, 200);
        letterBoxes = new ArrayList<RoundRect>();
        tileSize = 700/size;
        for (int row = 0; row < size;  row++) {
            for (int col = 0; col < size; col++) {
                RoundRect r = new RoundRect(col*tileSize+25, row*tileSize+25, tileSize, tileSize, 30, 30);
                r.setFillColor(155, 255, 255);
                r.setFontSize(30);
                r.setMouseClickedHandler(this::onClicked);
                letterBoxes.add(r);
            }
            
        }
        RoundRect end = new RoundRect(340, 850, 70, 40, 15, 15);
        end.setFillColor(170, 0, 0);
        end.setText("END");
        end.setFontSize(20);
        end.setMouseClickedHandler(this::onEnd);
        RoundRect textBox = new RoundRect(0, 0, 420, 85, 20, 20);
        textBox.setCenter(375, 787.5);
        textBox.setFillColor(170, 270, 270);
    }

    /*
     * Randomize method to populate the squares with randomized letters
     * File io from W3 schools
     * dice Combos list from wordsrated
     */
    public void randomize() {
        ArrayList<String[]> dice = new ArrayList<String[]>();
        try {
            File myObj = new File("bigDiceCombos.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String rawData = myReader.nextLine();
              dice.add(rawData.split(""));
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        
        // Randomizing and displaying on board
        System.out.println(dice.size());
        String currLetter;
        for (String[] die : dice) {
            int num = (int)(Math.random()*6); // 6 possible letters
            if (die[num].equals("q")) {
                currLetter = "qu";
            } 
            else {
                currLetter = die[num];
            }
            boolean available = false;
            int index = 0;
            while (!available) {
                index = (int)(Math.random()*(size*size)); // Randomizes which square the die goes in
                if (letterBoxes.get(index).getText() == null) {
                    available = true;
                }
            }
            chosenDice.add(currLetter);
            letterBoxes.get(index).setText(currLetter);
        }
    }

    /*
     * onClickeed method used to highlight the square that is clicked to make a word
     * also makes sure the square is bordering the previous square
     */
    public void onClicked(Shape shp, double x, double y, int button) {
        if (button == 1) {
            Oval highlight;
            if (selected.size() > 0) {
                Point lastPt = selected.get(selected.size()-1).getCenter();
                Point currPt = shp.getCenter();
                if (distance(lastPt, currPt) <= tileSize*2-10) {
                    highlight = new Oval(0, 0, 60, 60);
                    highlight.setCenter(shp.getCenter());
                    highlight.setFillColor(0, 255, 0, 80);
                    highlight.setMouseClickedHandler(this::onClicked);
                    
                    Line connection = new Line(lastPt.getX(), lastPt.getY(), currPt.getX(), currPt.getY());
                    connection.setStrokeWidth(40);
                    connection.setStrokeColor(0, 255, 0, 80);
                    connection.setMouseClickedHandler(this::onClicked);
                    selected.add(highlight);
                    connections.add(connection);
                    currStr += shp.getText();
                } 
            }
            else {
                highlight = new Oval(0, 0, 60, 60);
                highlight.setCenter(shp.getCenter());
                highlight.setFillColor(0, 255, 0, 80);
                selected.add(highlight);
                currStr += shp.getText();
            }
            System.out.println(currStr);
            
        }
        else {
            enterWord();
        }
        
    }

    /*
     * goes to end screen
     */
    public void onEnd(Shape shp, double x, double y, int click) {
        System.out.println(enteredWords);
        EndScreen e = new EndScreen(enteredWords);
    }
    /*
     * distance finds the distance between two pts
     */
    public double distance(Point p1, Point p2) {
        double distance = Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2)+Math.pow(p2.getY()-p1.getY(), 2));
        return distance;
    }

    /*
     * Uses the checker class to check the entered word
     * clears the highlighting
     */
    public void enterWord() {
        Checker check = new Checker();
        if (check.checkWord(currStr)) {
            enteredWords.add(currStr);
        }
        currStr = "";
        for (Oval o : selected) {
            o.setVisible(false);
        }
        for (Line l : connections) {
            l.setVisible(false);
        }
        selected = new ArrayList<Oval>();
        connections = new ArrayList<Line>();
        for (String word : enteredWords) {
            System.out.println(word);
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