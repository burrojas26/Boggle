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
    RoundRect timerBox;
    RoundRect scoreBox;
    RoundRect textBox;
    int wordsFound;
    int score;
    Rectangle welcomeBack;
    RoundRect playBtn;
    Text title;
    RoundRect rulesBtn;
    Image theRules;
    Text rulesText;
    // Play is used to determine when the playBtn is pressed
    boolean play = false;
    /*
     * Board constructor initializes all shape objects for the graphics
     */
    public Board() {
        Pad background = new Pad("BOGGLE", 750, 900);
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
        textBox = new RoundRect(0, 0, 420, 85, 20, 20);
        textBox.setCenter(375, 787.5);
        textBox.setFillColor(170, 270, 270);
        textBox.setFontSize(25);
        timerBox = new RoundRect(620, 850, 70, 40, 10, 10);
        timerBox.setFillColor(170, 270, 270);
        timerBox.setFontSize(20);
        scoreBox = new RoundRect(80, 850, 70, 40, 10, 10);
        scoreBox.setFillColor(170, 270, 270);
        scoreBox.setFontSize(20);
        wordsFound = 0;
        score = 0;
        scoreBox.setText(Integer.toString(score));
        // The objects below are for the welcome screen
        double welcomeX = background.getSize().getWidth();
        double welcomeY = background.getSize().getHeight();
        welcomeBack = new Rectangle(0, 0, welcomeX, welcomeY);
        welcomeBack.setFillColor(0, 0, 0, 200);
        playBtn = new RoundRect(0, 0, 250, 125, 20, 20);
        playBtn.setCenter(375, 500);
        playBtn.setFillColor(0, 95, 140);
        playBtn.setText("PLAY");
        playBtn.setFontSize(60);
        playBtn.setTextColor(0, 255, 0);
        playBtn.setMouseClickedHandler(this::onPlay);
        title = new Text("BOGGLE", 0, 0);
        title.setFontSize(150);
        title.setFillColor(0, 255, 0);
        title.setCenter(375, playBtn.getY()/2);
        rulesBtn = new RoundRect(0, 0, 120, 60, 20, 20);
        rulesBtn.setCenter(375, 500);
        rulesBtn.setY(playBtn.getY()+playBtn.getHeight()+50);
        rulesBtn.setFillColor(80, 80, 80);
        rulesText = new Text("RULES", 0, 0);
        rulesText.setFontSize(30);
        rulesText.setFillColor(240, 240, 240);
        rulesText.setCenter(rulesBtn.getCenter());
        rulesBtn.setMouseClickedHandler(this::onRules);
        rulesText.setMouseClickedHandler(this::onRules);
        theRules = new Image("theRules.png", 0, 0, 700, 406);
        theRules.setCenter(375, 450);
        theRules.setX(theRules.getX()+30);
        theRules.setVisible(false);

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
        if (button == 1 && shp.getText() != null) {
            Oval highlight;
            if (selected.size() > 0) {
                Point lastPt = selected.get(selected.size()-1).getCenter();
                Point currPt = shp.getCenter();
                if (distance(lastPt, currPt) <= tileSize*2-10) {
                    highlight = new Oval(0, 0, 60, 60);
                    highlight.setCenter(shp.getCenter());
                    highlight.setFillColor(0, 255, 0, 80);
                    highlight.setMouseClickedHandler(this::enterClick);
                    
                    Line connection = new Line(lastPt.getX(), lastPt.getY(), currPt.getX(), currPt.getY());
                    connection.setStrokeWidth(40);
                    connection.setStrokeColor(0, 255, 0, 80);
                    connection.setMouseClickedHandler(this::enterClick);
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
            textBox.setText(currStr);
            
        }
        else {
            enterWord();
        }
        
    }
    /*
     * the method enters the current word when a highlighted portion is clicked
     */
    public void enterClick(Shape shp, double x, double y, int button) {
        if (button != 1) {
            enterWord();
        }
    }
    /*
     * Starts the game when the play btn is clicked
     */
    public void onPlay(Shape shp, double x, double y, int button) {
        welcomeBack.setVisible(false);
        playBtn.setVisible(false);
        title.setVisible(false);
        rulesBtn.setVisible(false);
        rulesText.setVisible(false);
        play = true;
    }
    /*
     * returns play which represents when the player is playing
     */
    public boolean getPlay() {
        return play;
    }
    /*
     * goes to end screen
     */
    public void onEnd(Shape shp, double x, double y, int click) {
        System.out.println(enteredWords);
        EndScreen e = new EndScreen(enteredWords, score);
    }
    /*
     * Shows the rules page when the rules btn is clicked
     */
    public void onRules(Shape shp, double x, double y, int click) {
        theRules.setVisible(!theRules.getVisible());
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
        textBox.setText("");
        Checker check = new Checker();
        if (check.checkWord(currStr, enteredWords)) {
            enteredWords.add(currStr);
            wordsFound++;
            int wordLen = currStr.length();
            if (wordLen >= 8) {
                score+=11;
            }
            else if (wordLen >= 7) {
                score+=5;
            }
            else if (wordLen >= 6) {
                score+=3;
            }
            else if (wordLen >= 5) {
                score+=2;
            }
            else {
                score++;
            }
            scoreBox.setText(Integer.toString(score));
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
    }
    /*
     * Uses the passed in String to adjust the timer box
     */
    public void setTimerBox(String str) {
        timerBox.setText(str);
    }
    /*
     * returns the entered words
     */
    public ArrayList<String> getWords() {
        return enteredWords;
    }
    /*
     * returns the current score
     */
    public int getScore() {
        return score;
    }
    /*
     * Main method used for testing Board graphics
     */ 
    public static void main(String[] args) {
        Board b1 = new Board();
        b1.randomize();
    }
}