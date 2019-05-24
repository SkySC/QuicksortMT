package another.implementation;

import king.saleh.QuickSortMultiThreaded;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    private static final int lengthOfList = 35;

    public static void main(String[] args) {
        /**
         * 1. Maximale Anzahl an Threads auslesen
         * 2. Mögliche Anzahl an Threads dem ThreadPool zuweisen
         */
        final int THREAD_CAPACITY = Runtime.getRuntime().availableProcessors();
    
        System.out.println("Moegliche Anzahl an Threads: " + THREAD_CAPACITY + "\n");
    
        System.out.println("Generierte Folge:\n");
        try {
            printAndSortListWithSingleThreadedAndMultithreaded(generateRandomListOfLength(lengthOfList), THREAD_CAPACITY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printAndSortListWithSingleThreadedAndMultithreaded(List<Integer> randomList, int numberOfThreads)
            throws InterruptedException {
    
        System.out.println("Multi Threaded 2: \n");
        
        int[] zuSortierendeFolge = new int[lengthOfList];
        for(int i = 0; i < zuSortierendeFolge.length; i++){
            zuSortierendeFolge[i] = (int)(Math.random() * 1000);
        }

        System.out.println("Initial\t\t" + Arrays.toString(zuSortierendeFolge) + "\n");
        
        Thread worker = new Thread(new QuickSortMultiThreaded2(zuSortierendeFolge, 0,
                zuSortierendeFolge.length - 1, true));
    
        QuickSortMultiThreaded.messages.clear();
        long startTimeMT = System.nanoTime();
        
        worker.start();
        worker.join();
        
        // DEBUG Ausgabe
        System.out.println(QuickSortMultiThreaded.messages.stream().collect(Collectors.joining("\n")));
        
        System.out.println("\nSortiert\t" + Arrays.toString(zuSortierendeFolge));
        printConsumedTime(startTimeMT, "MT");
    }

    // Gibt eine Liste mit ganzzahligen Zufallszahlen zurück
    private static List<Integer> generateRandomListOfLength(int lengthPerList) {
        return new Random()
                .ints(lengthPerList, 0, (int)Math.pow(2, 10)) // returns an IntStream
                .boxed() // returns a Stream containing the Integer (input) values
                .collect(Collectors.toList()); // accumulates the stream into a new List
    }

    private static long getConsumedTimeInNanoSeconds(long startTime){
        return System.nanoTime() - startTime;
    }

    private static void printConsumedTime(long startTime, String opt) {
        long consumedTime = getConsumedTimeInNanoSeconds(startTime);
        System.out.println("\n==> " + (opt.equals("MT") ? "Multi" : "Single")
                + " Thread time consumed: " + consumedTime + " ns ~ " + (consumedTime / 1000000) + " ms\n");
    }

    /*
    private boolean isSorted() {
        return IntStream.range(0, zahlenfolge.size() - 1)
                .allMatch( num -> zahlenfolge.get(num) <= zahlenfolge.get(num + 1));
    }
    */
}