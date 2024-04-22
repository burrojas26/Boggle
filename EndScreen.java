import java.util.ArrayList;
import doodlepad.*;

public class EndScreen {
    public EndScreen(ArrayList<String> words, int theScore) {
        Pad background = new Pad(750, 900);
        background.setBackground(0, 155, 200);
        RoundRect wordList = new RoundRect(0, 0, 500, 850, 30, 30);
        wordList.setCenter(375, 450);
        wordList.setFontSize(30);
        wordList.setFillColor(155, 255, 255);
        ArrayList<Text> wordsT = new ArrayList<Text>();
        for (String word : words) {
            if (wordsT.size() < 1) {
                Text currWord = new Text(word, 0, 0);
                currWord.setFontSize(20);
                currWord.setCenter(375, 0);
                currWord.setY(40);
                wordsT.add(currWord);
                
            }
            else {
                Text currWord = new Text(word, 0, 0);
                currWord.setFontSize(20);
                currWord.setCenter(375, 0);
                currWord.setY(wordsT.get(wordsT.size()-1).getY() + 30);
                wordsT.add(currWord);
            }  
        }
        RoundRect score = new RoundRect(0, 0, 70, 40, 10, 10);
        score.setFontSize(25);
        score.setFillColor(0, 255, 0, 70);
        score.setCenter(375, 845);
        score.setText(Integer.toString(theScore));
    }
}