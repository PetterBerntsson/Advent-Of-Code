import java.io.IOException;
import java.util.Scanner;


public class AOC_05 {

    public static final String FilePath = "output-05_py";

    public AOC_05() {

    }


    //The solve is implemented by calling a script, for learning purposes, not for speed purposes
    public static void solve(Scanner elf) {

        try {
            //the python executable absolute path
            String myPythonExecutableAbsolutePath = "C:/Users/Petter/AppData/Local/Programs/Python/Python37/python.exe";
            //the script absolute path
            String myPythonScriptAbsolutePath = System.getProperty("user.dir") + "/py-scripts/aoc_05_script.py";
            Process script = Runtime.getRuntime().exec(myPythonExecutableAbsolutePath + " " + myPythonScriptAbsolutePath);
            try {
                script.waitFor();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }



    }
}
