import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AOC_01 {


    public static void solve(Scanner elf) {

        Integer[] input = parse(elf);
        printSolution(findFinalFrequency(input), findDuplicateFrequency(input));
    }




    public static void printSolution(int finalFreq, int duplicateFreq) {
        System.out.println("--------- Solution Found ---------");
        System.out.println("Final frequency: " + finalFreq);
        System.out.println("First duplicate frequency: " + duplicateFreq);
        System.out.println("----------------------------------");
    }





    public static Integer[] parse(Scanner elf) {
        ArrayList<Integer> input = new ArrayList<>();

        while(elf.hasNext()) {
            input.add(Integer.parseInt(elf.next()));
        }
        return input.toArray(new Integer[input.size()]);
    }

    public static int findFinalFrequency(Integer[] input) {
        int frequency = 0;
        for(Integer fc: input) {
            frequency += fc;
        }
        return frequency;
    }

    public static Integer findDuplicateFrequency(Integer[] freqs) {
        Integer frequency = 0;
        int firstDuplicate = 0;

        //Smallest primitive type boolean, 1 bit size
        HashMap<Integer, Boolean> visited = new HashMap<>();

        int index = 0;

        outer:
        while(true) {
            frequency += freqs[index];
            index = (index+1)%freqs.length;

            if(visited.get(frequency) != null && visited.containsKey(frequency)) {
                firstDuplicate = frequency;
                break outer;

            } else {
                visited.put(new Integer(frequency), true);
            }
        }
        return firstDuplicate;

    }

}
