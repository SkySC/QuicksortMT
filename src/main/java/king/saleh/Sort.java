package king.saleh;

import java.util.concurrent.atomic.AtomicInteger;

public interface Sort {

    AtomicInteger stepCounter = new AtomicInteger(0);

    static int partition(Folge aktuelleFolge, int linkerIndex, int rechterIndex) {
        /**
         * Ermittle das ganzzahlige Pivotelement
         */
        int pivot = aktuelleFolge.getElement(rechterIndex);
        /**
         * 1. Durchlaufe das Folgenobjekt
         * 2. Vergleiche das aufzeigende Element des ersten Zeigers mit dem Pivotelement
         * 3. [1] Fall: Element <= Pivot --> Vertausche beide aufzeigende Elemente
         *    [2] Fall: Element > Pivot --> Springe zum Schleifenkopf
         */
        int ersterZeiger = linkerIndex,
                zweiterZeiger = linkerIndex - 1;

        for (; ersterZeiger < rechterIndex; ersterZeiger++) {
            if (aktuelleFolge.getElement(ersterZeiger) <= pivot) {
                printStep(aktuelleFolge, zweiterZeiger + 1, ersterZeiger);
                aktuelleFolge.vertauscheElemente(++zweiterZeiger, ersterZeiger);
            }
        }
        /**
         * 1. Vertausche zum Schluss das Pivotelement mit dem Element des zweiten Zeigers
         * --> Grund: Neues Pivotlement als Vergleichselement für den nächsten Aufruf
         * 2. Gib das neue Pivotelement zurück
         */
        printStep(aktuelleFolge, zweiterZeiger + 1, rechterIndex);
        aktuelleFolge.vertauscheElemente(++zweiterZeiger, rechterIndex);
        return zweiterZeiger;
    }

    static void printStep(Folge folge, int linkerIndex, int zweiterIndex) {

        StringBuilder debug = new StringBuilder(("Step " + stepCounter.incrementAndGet() + "\t\t"
                + folge.getElement(linkerIndex) + " <=> " + folge.getElement(zweiterIndex) + "\t\t["));

        int laenge = folge.getFolge().size();
        for (int i = 0; i < laenge; i++) {
            if (i == linkerIndex || i == zweiterIndex)
                debug.append("\033[4;31m").append(folge.getElement(i)).append("\033[0m");
            else
                debug.append(folge.getElement(i));
            if(i < laenge - 1) debug.append(", ");
        }

        debug.append("]\t\t").append(Thread.currentThread());
        QuickSortMultiThreaded.addDebugMessage(debug.toString());
    }
}