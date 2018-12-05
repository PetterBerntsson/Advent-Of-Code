import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SantasLittleSolver {

    private static final int DAY_01 = 1;
    private static final int DAY_02 = 2;
    private static final int DAY_03 = 3;

    private static SolutionTimer timer;



    public static void main(String[] args0) throws FileNotFoundException {

        timer = new SolutionTimer();


        //------------//
        int day = 3;
        //------------//


        System.out.println("\n\t\t" + "Solving for day " + day + "\n");
        timer.start();
        solveForDay(day);
        timer.printSolutionTime();
    }


    public static void solveForDay(int day) throws FileNotFoundException {
        switch (day) {

            case DAY_01:
                AOC_01.solve(new Scanner(new File("input-01")));
                break;


            case DAY_02:
                AOC_02.solve(new Scanner(new File("input-02")));
                break;

            case DAY_03:
                AOC_03 aoc_03 = new AOC_03();
                aoc_03.solve(new Scanner(new File("input-03")));
                break;
        }


    }

}
