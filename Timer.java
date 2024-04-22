import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime code from W3 schools
 */
public class Timer {
    LocalTime time;
    DateTimeFormatter format;
    String formattedTime;
    int startTime;
    int endTime;
    String timeLeft;
    int timerLen = 180; //seconds
    
    public void getCurrTime() {
        time = LocalTime.now();
        format = DateTimeFormatter.ofPattern("HH:mm:ss");

        formattedTime = time.format(format);
    }

    public int toSeconds(String theTime) {
        String[] currTime = theTime.split(":");
        int hours = Integer.parseInt(currTime[0]) * 60 * 60;
        int mins = Integer.parseInt(currTime[1]) * 60;
        return hours + mins + Integer.parseInt(currTime[2]);
    }

    public String reformat(int seconds) {
        int hours = seconds/3600;
        int minutes = seconds%3600/60;
        int secs = seconds%3600%60;
        return hours + ":" + minutes + ":" + secs;
    }

    public void startTimer() {
        getCurrTime();
        startTime = toSeconds(formattedTime);
        endTime = startTime + timerLen;
    }
    
    public String getTimeLeft() {
        getCurrTime();
        int currTime = toSeconds(formattedTime);
        int timeLeft = endTime - currTime;
        return reformat(timeLeft).substring(2);
    }

    public static void main(String[] args) {
        Timer t = new Timer();
        t.startTimer();
        System.out.println(t.getTimeLeft());
    }
}
