import java.util.ArrayList;
import doodlepad.*;

public class EndScreen {
    public EndScreen(ArrayList<String> words) {
        Pad background = new Pad(750, 900);
        background.setBackground(0, 155, 200);
        RoundRect wordList = new RoundRect(0, 0, 500, 850, 30, 30);
        wordList.setCenter(375, 450);
        wordList.setFontSize(30);
        wordList.setFillColor(155, 255, 255);
        String finalStr = "";
        for (String word : words) {
            finalStr+=word + "\n";
        }
        wordList.setText(finalStr);
    }
}