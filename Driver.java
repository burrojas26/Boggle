
public class Driver {
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
