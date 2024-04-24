import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Jasper Burroughs
 * @since 4/21/24
 * LocalTime code from W3 schools
 * This class creates a timer for the boggle game using the current time
 */
public class Timer {
    LocalTime time;
    DateTimeFormatter format;
    String formattedTime;
    int startTime;
    int endTime;
    String timeLeft;
    int timerLen = 180; //seconds
    
    /*
     * gets the current time and sets formattedTime to a string version
     */
    public void getCurrTime() {
        time = LocalTime.now();
        format = DateTimeFormatter.ofPattern("HH:mm:ss");

        formattedTime = time.format(format);
    }

    /*
     * converts the String time into an int as seconds
     */
    public int toSeconds(String theTime) {
        String[] currTime = theTime.split(":");
        int hours = Integer.parseInt(currTime[0]) * 60 * 60;
        int mins = Integer.parseInt(currTime[1]) * 60;
        return hours + mins + Integer.parseInt(currTime[2]);
    }

    /*
     * reformats the time in seconds as an int to a String
     */
    public String reformat(int seconds) {
        int hours = seconds/3600;
        String hrsStr = Integer.toString(hours);
        for (int i = 0; i < 2-Integer.toString(hours).length(); i++) {
            hrsStr = "0" + hrsStr;
        }
        
        int minutes = seconds%3600/60;
        String minStr = Integer.toString(minutes);
        for (int i = 0; i < 2-Integer.toString(minutes).length(); i++) {
            minStr = "0" + minStr;
        }
        int secs = seconds%3600%60;
        String secStr = Integer.toString(secs);
        for (int i = 0; i < 2-Integer.toString(secs).length(); i++) {
            secStr = "0" + secStr;
        }
        String theStr = hrsStr + ":" + minStr + ":" + secStr;
        return theStr;
    }

    /*
     * sets the endTime to the current time + the length of the timer (normally 3 minutes)
     */
    public void startTimer() {
        getCurrTime();
        startTime = toSeconds(formattedTime);
        endTime = startTime + timerLen;
    }
    /*
     * gets the end time - the current time to determine the time remaining on the timer
     */
    public String getTimeLeft() {
        getCurrTime();
        int currTime = toSeconds(formattedTime);
        int timeLeft = endTime - currTime;
        return reformat(timeLeft).substring(3);
    }

    /*
     * gets the seconds remaining on the timer
     */
    public int getSecondsLeft() {
        getCurrTime();
        int currTime = toSeconds(formattedTime);
        int timeLeft = endTime - currTime;
        return toSeconds(reformat(timeLeft));
    }

    /*
     * used for testing the timer
     */
    public static void main(String[] args) {
        Timer t = new Timer();
        t.startTimer();
        System.out.println(t.getTimeLeft());
    }
}
