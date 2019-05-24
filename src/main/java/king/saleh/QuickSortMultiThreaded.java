package king.saleh;

import java.util.ArrayList;
import java.util.List;

public final class QuickSortMultiThreaded implements Sort {

    public static List<String> messages = new ArrayList<>();

    public static synchronized void addDebugMessage(String s){
        QuickSortMultiThreaded.messages.add(s);
    }

    private QuickSortMultiThreaded() {

    }

    static void quicksort(Folge folge) {
        quicksort(folge, folge.getErsterIndex(), folge.getLetzterIndex());
    }

    private static void quicksort(Folge aktuelleFolge, int ersterIndex, int letzterIndex) {
        /**
         * Abbruchbedigungen für die Rekursion:
         * Abbruch, wenn der Zeiger bereits das letze Element angenommen hat
         */
        if (ersterIndex >= letzterIndex) return;
        /**
         * Wenn es nicht der Fall ist --> Sortiervorgang fortsetzen
         * 1. Ermittle das Pivot-Element als Trennelement
         */
        int partitionIndex = Sort.partition(aktuelleFolge, ersterIndex, letzterIndex);
        /**
         * 2. Rufe den Sortieralgorithmus jeweils für beide Teilfolgen rekurisv auf
         * Die Trennung der Folgen findet jeweils am Pivotelement statt
         */
        Folge linkeTeilfolge = new Folge(aktuelleFolge.getFolge(), ersterIndex, partitionIndex - 1);
        Folge rechteTeilfolge = new Folge(aktuelleFolge.getFolge(), partitionIndex + 1, letzterIndex);
        /**
         * 1. Breche die Tasks auf kleinster Ebene herunter
         * 2. Führe den Soriteralgorithmus beider Subsequenzen aus
         * 3. Führe die Ergebnisse der Subsequenzen zusammen
         */
        
        linkeTeilfolge.fork();
        rechteTeilfolge.compute();
        linkeTeilfolge.join();

        Sort.stepCounter.set(0);
    }
}
