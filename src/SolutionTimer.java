import java.sql.SQLOutput;

public class SolutionTimer {

    private long start;
    private long end;
    private boolean started;

    public SolutionTimer() {
        started = false;
    }

    public void start() {
        start = System.currentTimeMillis();
        started = true;
    }

    public void printSolutionTime() {
        if(started) {
            System.out.println("Solution time cost: " + (System.currentTimeMillis() - start) + " ms");
        } else {
            System.out.println("Solution timer was never started");
        }
    }
}
