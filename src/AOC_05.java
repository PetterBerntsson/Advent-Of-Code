import java.io.IOException;



public class AOC_05 {

    public AOC_05() {

    }

    public static void solve() {
        System.out.println(System.getProperty("user.dir"));

        try {
            Runtime.getRuntime().exec("C:\\Users\\Petter\\AppData\\Local\\Programs\\Python\\Python37\\python.exe C:/Users/Petter/IdeaProjects/AOC/py-scripts/aoc_05_script.py");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
