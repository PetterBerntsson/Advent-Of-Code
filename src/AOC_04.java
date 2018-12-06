import java.util.*;

public class AOC_04 {

    public AOC_04() {

    }

    public void solve(Scanner elf) {

        HashMap<String, Shift> shifts = parse(elf);
        HashMap<Integer, ArrayList<Shift>> guardShifts = remake(shifts);

        HashMap<String, Integer> guardValues = getSleepiestGuard(guardShifts);

        printSolution(guardValues);
    }

    private void printSolution(HashMap<String, Integer> guardValues) {
        System.out.println("------------- Solution Found ----------");
        System.out.println("Sleepiest guard: " + guardValues.get("sleep-ID"));
        System.out.println("Sleepiest minute: " + guardValues.get("sleep-minute"));
        System.out.println("Multiplier: " + guardValues.get("sleep-ID")*guardValues.get("sleep-minute"));
        System.out.println("- - - - - - - - - - - - - - - - - - - - - ");
        System.out.println("Most Freq Minute Guard ID: " + guardValues.get("freq-ID"));
        System.out.println("Most Freq Minute: " + guardValues.get("freq-minute"));
        System.out.println("Multiplier: " + guardValues.get("freq-ID")*guardValues.get("freq-minute"));
        System.out.println("----------------------------------------");
    }

    private HashMap<String, Shift> parse(Scanner elf) {
        HashMap<String, Shift> shifts = new HashMap<>();
        Shift shift;
        String date = "nothing to show";
        while(elf.hasNext()) {
            String[] input = elf.nextLine().split("[-: #\\]]", 0);

            //------------------------- Adds Guard --------------------------------------//
            if(input[6].contains("G")) {

                //If the guard starts shift at 23:XX then its shift belongs to the next day
                if(input[3].equals("23")) {
                    if(Integer.parseInt(input[2]) < 9) {
                        date = input[1] + "-0" + (Integer.parseInt(input[2])+1);
                    } else {
                        date = input[1] + "-" + (Integer.parseInt(input[2])+1);
                    }
                } else { // If the shift starts at 00:XX then the shift belongs to this day
                    date = input[1] + "-" + input[2];
                }

                if(!shifts.containsKey(date)) {
                    shift = new Shift(date);
                    shift.addGuard(Integer.parseInt(input[8]));
                    shifts.put(date, shift);

                } else {
                    shifts.get(date).addGuard(Integer.parseInt(input[8]));

                }
            }
            //----------------------------------------------------------------------------//
            else {
                if(Integer.parseInt(input[2]) < 9) {
                    date = input[1] + "-0" + (Integer.parseInt(input[2]));
                } else {
                    date = input[1] + "-" + (Integer.parseInt(input[2]));
                }

                if(!shifts.containsKey(date)) {
                    shift = new Shift(date);
                    shift.addEvent(Integer.parseInt(input[4]));
                    shifts.put(date, shift);
                } else {
                    shifts.get(date).addEvent(Integer.parseInt(input[4]));
                }
            }
        }
        return shifts;
    }

    private HashMap<Integer, ArrayList<Shift>> remake(HashMap<String, Shift> shifts) {
        HashMap<Integer, ArrayList<Shift>> guards = new HashMap<>();

        Shift shift;
        int guard;
        ArrayList<Shift> guardShifts;

        for(Map.Entry<String, Shift> entry: shifts.entrySet()) {

            shift = entry.getValue();
            guard = shift.getGuard();

            if (!guards.containsKey(guard)) {
                guardShifts = new ArrayList<>();
                guardShifts.add(shift);
                guards.put(guard, guardShifts);

            } else {
                guards.get(guard).add(shift);
            }
        }
        return guards;
    }

    private HashMap<String, Integer> getSleepiestGuard(HashMap<Integer, ArrayList<Shift>> guards) {
        int longestSleep = 0;
        int sleepiestGuard = 0;
        int sleepiestMinute = 0;
        //the guard most frequently asleep on the same minute
        int mostFreqGuard = 0;
        int mostFreqMinute = 0;
        int mostFreqMinuteIndex = 0;

        int[] minuteValues;

        HashMap<String, Integer> guardValues = new HashMap<>();

        for(Map.Entry<Integer, ArrayList<Shift>> entry : guards.entrySet()) {
            int sleep = getTotalSleepTime(entry.getValue());
            int guard = entry.getKey();

            minuteValues = getSleepiestMinute(entry.getValue());

            int freqMinute = minuteValues[1];

            if(freqMinute > mostFreqMinute) {
                mostFreqMinute = freqMinute;
                mostFreqGuard = guard;
                mostFreqMinuteIndex = minuteValues[0];
            }

            if(sleep > longestSleep) {
                sleepiestGuard = guard;
                longestSleep = sleep;
                sleepiestMinute = minuteValues[0];
            }
        }

        guardValues.put("freq-ID", mostFreqGuard);
        guardValues.put("sleep-ID", guards.get(sleepiestGuard).get(0).getGuard());
        guardValues.put("sleep-minute", sleepiestMinute);
        guardValues.put("freq-minute", mostFreqMinuteIndex);

        //return guards.get(sleepiestGuard);
        return guardValues;
    }

    private int getTotalSleepTime(ArrayList<Shift> guard) {
        int total = 0;

        for(Shift shift: guard) {
            total += shift.getSleepTime();
        }
        return total;
    }

    private int[] getSleepiestMinute(ArrayList<Shift> guard) {
        //this is only done once
        int[] minutes = new int[60];
        int[] minuteValues = new int[2];

        for(Shift shift: guard) {
            if(shift.getQueueSize() > 0) {
                setSleepScheme(shift, minutes);
            }
        }

        int sleepiestMinute = 0;

        for(int i = 0; i < minutes.length; i++) {
            if(minutes[i] > minutes[sleepiestMinute]) {
                sleepiestMinute = i;
            }
        }

        minuteValues[0] = sleepiestMinute;
        minuteValues[1] = minutes[sleepiestMinute];

        return minuteValues;
    }

    private void setSleepScheme(Shift shift, int[] minutes) {
        PriorityQueue<Integer> events = shift.copyEventList();
        int event = events.poll();
        //set size after poll
        int size = events.size();
        int nextEvent = 0;
        boolean sleeping = true;

        for(int i = 0; i < size; i++) {
            nextEvent = events.poll();
            if(sleeping) {
                for(int j = event; j < nextEvent; j++) {
                    minutes[j]++;
                }
                sleeping = false;
            } else {
                sleeping = true;
            }
            event = nextEvent;
        }
    }



    private class Shift {

        //The guard-ID for the shift
        private int guard;
        private String date;
        private PriorityQueue<Integer> events;


        private Shift(String date) {
            this.date = date;
            events = new PriorityQueue<>();
        }

        private void addEvent(int time) {
            events.add(time);
        }

        private void addGuard(int guard) {
            this.guard = guard;
        }

        private void print() {
            //To keep events intact
            PriorityQueue<Integer> temp = new PriorityQueue<>();
            temp.addAll(events);

            System.out.println("Guard ID: " + guard);
            System.out.println("Shift date: " + date);

            int size = temp.size();

            for(int i = 0; i < size; i++) {
                System.out.println("Event: " + temp.poll());
            }
        }

        private int getSleepTime() {
            PriorityQueue<Integer> temp = new PriorityQueue<>();
            temp.addAll(events);

            int sleepTime = 0;
            int eventTime = 0;
            int prevEventTime = 0;

            int size = temp.size();

            boolean sleeping = false;

            for(int i = 0; i < size; i++) {
                eventTime = temp.poll();

                if(sleeping) {
                    sleepTime += eventTime-prevEventTime;
                    sleeping = false;
                } else {
                    sleeping = true;
                }

                prevEventTime = eventTime;
            }

            return sleepTime;
        }

        private int getQueueSize() {
            return events.size();
        }

        //if we want to leave the list intact
        private PriorityQueue<Integer> copyEventList() {
            PriorityQueue<Integer> temp = new PriorityQueue<>();
            temp.addAll(events);
            return temp;
        }

        private int getGuard() {
            return guard;
        }

    }
}
