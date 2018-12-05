import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AOC_03 {

    public AOC_03() {

    }

    public void solve(Scanner elf) {

        HashMap<String, Integer> fabric = new HashMap<>();
        ArrayList<Claim> claims = generateClaims(elf, fabric);

        //------------------- Part 1 ------------------------//

        int numConflicts = getConflictingAreas(fabric);

        //---------------------------------------------------//


        //------------------- Part 2 ------------------------//

        int ID = 0;
        for(Claim claim: claims) {
            if(!claim.isOverlapped(fabric)) {
                ID = claim.getID();
            }
        }
        //---------------------------------------------------//

        printSolution(numConflicts, ID);

    }


    //Parsing and generating claims at once is slightly faster than doing them sequentially, list by list
    private ArrayList<Claim> generateClaims(Scanner elf, HashMap<String, Integer> fabric) {

        ArrayList<Claim> claims = new ArrayList<>();
        Claim claim;

        while(elf.hasNext()) {
            claim = new Claim(elf.nextLine());
            claims.add(claim);
            claim.makeClaim(fabric);
        }
        return claims;
    }

    private int getConflictingAreas(HashMap<String, Integer> fabric) {
        int conflicts = 0;

        for(Map.Entry<String, Integer> area: fabric.entrySet()) {
            if(area.getValue() > 1) {
                conflicts++;
            }
        }
        return conflicts;
    }

    private void printSolution(int conflicts, int ID) {
        System.out.println("--------------- Solution Found ---------------");
        System.out.println("Conflicts: " + conflicts);
        System.out.println("ID of non-overlapping claim: " + ID);
        System.out.println("----------------------------------------------");
    }






    private class Claim {

        private int x_dist;
        private int y_dist;

        private int x_size;
        private int y_size;

        private int ID;



        private Claim(String input) {
            generate(input);
        }
        private void generate(String input) {
            String[] data = input.split("[# @,:x]", 0);
            ID = Integer.parseInt(data[1]);
            x_dist = Integer.parseInt(data[4]);
            y_dist = Integer.parseInt(data[5]);
            x_size = Integer.parseInt(data[7]);
            y_size = Integer.parseInt(data[8]);

        }


        private void print() {
            System.out.println("- Claim #" + ID + "-");
            System.out.println("Pos: " + x_dist + "x" + y_dist);
            System.out.println("Size: " + x_size + "x" + y_size);
            System.out.println("--------------");
        }

        private void makeClaim(HashMap<String, Integer> fabric) {
            for(int x = x_dist; x < x_size+x_dist; x++) {
                for(int y = y_dist; y < y_size+y_dist; y++) {
                    fabric.merge(x + "x" + y, 1, Integer::sum);
                }
            }
        }

        private boolean isEdgeOverlapped(HashMap<String, Integer> fabric) {
            //One could possibly check the biggest sides first, but this seems to give a bigger time cost
            for(int x = x_dist; x < x_size+x_dist; x++) {
                if(fabric.get(x + "x" + y_dist)> 1 || fabric.get(x + "x" + (y_dist + y_size-1))> 1) {
                    return true;
                }
            }
            for(int y = y_dist + 1; y < y_size+y_dist - 1; y++) {
                if(fabric.get(x_dist + "x" + y)> 1 || fabric.get((x_dist+x_size-1) + "x" + y)> 1) {
                    return true;
                }
            }

            return false;
        }

        private boolean isOverlapped(HashMap<String, Integer> fabric) {

            //Why check edges? Provides generally a 25-50% lower time cost
            if(isEdgeOverlapped(fabric)) {
               return true;
            }

            for(int x = x_dist; x < x_size+x_dist; x++) {
                for(int y = y_dist; y < y_size+y_dist; y++) {
                    if(fabric.containsKey(x + "x" + y) && fabric.get(x+"x"+y)>1) {
                        return true;
                    }

                }
            }

            return false;
        }

        private int getID() {
            return ID;
        }

        //method for checking proper drawing of squares in hashmap
        private void drawSquare() {
            String[][] square = new String[x_size+x_dist][y_size+y_dist];
            for(int i = 0; i < x_size+x_dist; i++) {
                for(int j = 0; j < y_size+y_dist; j++) {
                    square[i][j] = "-";
                }
            }

            for(int x = x_dist; x < x_size+x_dist; x++) {
                square[x][y_dist] = "O";
                square[x][y_dist+y_size-1] = "O";

            }
            for(int y = y_dist + 1; y < y_size+y_dist-1; y++) {
                square[x_dist][y] = "O";
                square[x_dist+x_size-1][y] = "O";
            }

            for(int i = 0; i < x_size+x_dist; i++) {
                String row = "";
                for(int j = 0; j < y_size+y_dist; j++) {
                    row += square[i][j];
                }
                System.out.println(row);
            }


            System.out.println("x_size: " + x_size);
            System.out.println("x_dist: " + x_dist);
            System.out.println("y_size: " + y_size);
            System.out.println("y_dist: " + y_dist);
        }


    }

}
