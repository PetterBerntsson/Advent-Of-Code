import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

    public class AOC_02 {

        public static void solve(Scanner elf) {

            int[] masterCount = new int[2];
            int[] subCount;

            while(elf.hasNext()) {
                subCount = getCounts(elf.next());
                masterCount[0] += subCount[0];
                masterCount[1] += subCount[1];
            }


            printSolution(masterCount[0]*masterCount[1]);

        }

        public static void printSolution(int checkSum) {
            System.out.println("--- Solution Found ---");
            System.out.println("Checksum: " + checkSum);
            System.out.println("----------------------");
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
    }


