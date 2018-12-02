import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

    public class AOC_02 {

        public static void solve(Scanner elf) {


            ArrayList<String> input = parse(elf);
            printSolution(getCheckSum(input), findFabricBox(input));


        }

        public static void printSolution(int checkSum, String ID) {
            System.out.println("--------------- Solution Found ---------------");
            System.out.println("Checksum: " + checkSum);
            System.out.println("Common ID of boxes: " + ID);
            System.out.println("----------------------------------------------");
        }


        public static ArrayList<String> parse(Scanner elf) {

            ArrayList<String> input = new ArrayList<>();

            while(elf.hasNext()) {
                input.add(elf.next());
            }
            return input;
        }

        public static int getCheckSum(ArrayList<String> input) {

            int[] masterCount = new int[2];
            int[] subCount;

            for(String id: input) {
                subCount = getCounts(id);
                masterCount[0] += subCount[0];
                masterCount[1] += subCount[1];
            }

            return masterCount[0]*masterCount[1];

        }


        public static int[] getCounts(String id) {

            int[] counts = new int[2];
            char[] chars = id.toCharArray();

            Arrays.sort(chars);

            for(int i = 0; i < chars.length; i++) {
                int cntFC = 1;
                int j = i+1;

                while(j != chars.length && chars[i] == chars[j]) {
                    cntFC++;
                    j++;
                }

                i += cntFC-1;
                if(cntFC == 2) {
                    counts[0] = 1;
                } else if(cntFC == 3) {
                    counts[1] = 1;
                }
            }
            return counts;
        }

        public static String findFabricBox(ArrayList<String> input) {

            String[] ids = input.toArray(new String[input.size()]);
            String match = null;

            outer:
            for(int i = 0; i < ids.length; i++) {
                for(int j = i+1; j < ids.length; j++) {
                    if(matches(ids[i], ids[j])) {
                        match = trimMatch(ids[i], ids[j]);
                        break outer;
                    }
                }
            }
            return match;
        }

        public static boolean matches(String first, String second) {
            char[] word = first.toCharArray();
            char[] compare = second.toCharArray();
            boolean firstFail = false;
            boolean matches = true;

            for(int i = 0; i < word.length; i++) {
                if(word[i] != compare[i]) {
                    if(firstFail) {
                        matches = false;
                        break;
                    } else {
                        firstFail = true;
                    }
                }
            }
            return matches;
        }

        public static String trimMatch(String first, String second) {
            String match = "";
            char[] word = first.toCharArray();
            char[] compare = second.toCharArray();
            for(int i = 0; i < word.length; i++) {
                if(word[i] == compare[i]) {
                    match += word[i];
                }
            }
            return match;
        }
    }


