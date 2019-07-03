package king.saleh;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.concurrent.ForkJoinPool;

public class Main {
    
    private static final int lengthOfList = 20;
    private static final int THREAD_CAPACITY = Runtime.getRuntime().availableProcessors();
    
    public static ForkJoinPool fjP = new ForkJoinPool(THREAD_CAPACITY);

    public static void main(String[] args) {
        /**
         * 1. Maximale Anzahl an Threads auslesen
         * 2. Mögliche Anzahl an Threads dem ThreadPool zuweisen
         */

        System.out.println("Moegliche Anzahl an Threads: " + THREAD_CAPACITY + "\n");

        System.out.println("Generierte Folgen:\n");
        try {
            printAndSortListWithSingleThreadedAndMultithreaded(generateRandomListOfLength(lengthOfList), THREAD_CAPACITY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printAndSortListWithSingleThreadedAndMultithreaded(List<Integer> randomList, int numberOfThreads) {
    
        QuickSortMultiThreaded.messages.clear();
        System.out.println("Single Threaded: \n");
        
        long startTimeST = System.nanoTime();
        Folge folge1 = QuickSort.quicksort(new Folge(new ArrayList<Integer>(randomList)));

        System.out.println(QuickSortMultiThreaded.messages.stream().collect(Collectors.joining("\n")));
        QuickSortMultiThreaded.messages.clear();
        
        // DEBUG Ausgabe
        System.out.println("\n" + "Sortiert\t" + folge1.toString());
        printConsumedTime(startTimeST, "ST");
        
        System.out.println("Multi Threaded: \n");
        
        //Folge copy = new Folge(new ArrayList<Integer>(randomList));
        
        long startTimeMT = System.nanoTime();
        //Folge result = fjP.invoke(copy);
        Folge result = fjP.invoke(new Folge(new ArrayList<>(randomList)));

        // DEBUG Ausgabe
        System.out.println(QuickSortMultiThreaded.messages.stream().collect(Collectors.joining("\n")));

        System.out.println("\n" + "Sortiert\t" + result.toString());
        printConsumedTime(startTimeMT, "MT");
    }

    // Gibt eine Liste mit ganzzahligen Zufallszahlen zurück
    public static List<Integer> generateRandomListOfLength(int lengthPerList) {
        return new Random()
                .ints(lengthPerList, 0, (int)Math.pow(2, 10)) // returns an IntStream
                .boxed() // returns a Stream containing the Integer (input) values
                .collect(Collectors.toList()); // accumulates the stream into a new List
    }

    private static long getConsumedTimeNanoSeconds(long startTime){
        return System.nanoTime() - startTime;
    }

    private static void printConsumedTime(long startTime, String opt) {
        long consumedTime = getConsumedTimeNanoSeconds(startTime);
        System.out.println("\n==> " + (opt.equals("MT") ? "Multi" : "Single")
                + " Thread time consumed: " + consumedTime + " ns ~ " + (consumedTime / 1000000) + " ms\n");
    }
}