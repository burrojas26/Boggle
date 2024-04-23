/**
 * @author Jasper Burroughs
 * @since 4/10/24
 * The main driver class for Boggle
 */
public class Driver {
    /*
     * Main method - creates a board object, randomizes it, and starts the timer
     * when the timer ends the end screen is shown
     */
    public static void main(String[] args) {
        Board theBoard = new Board();
        theBoard.randomize();
        Timer timer = new Timer();
        timer.startTimer();
        while (!timer.getTimeLeft().equals("0:0")) {
            theBoard.setTimerBox(timer.getTimeLeft());
        }
        EndScreen e = new EndScreen(theBoard.getWords(), theBoard.getScore());
    }
    
}
