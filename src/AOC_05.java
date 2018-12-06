import java.io.IOException;
import java.util.Scanner;


public class AOC_05 {

    public static final String FilePath = "output-05_py";

    public AOC_05() {

    }

    public static void solve(Scanner elf) {
        System.out.println(System.getProperty("user.dir"));

        try {
            String myPythonExecutableAbsolutePath = "C:/Users/Petter/AppData/Local/Programs/Python/Python37/python.exe";
            String myPythonScriptAbsolutePath = "C:/Users/Petter/IdeaProjects/AOC/py-scripts/aoc_05_script.py";
            Runtime.getRuntime().exec(myPythonExecutableAbsolutePath + " " + myPythonScriptAbsolutePath);
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println(elf.nextLine());

    }
}
