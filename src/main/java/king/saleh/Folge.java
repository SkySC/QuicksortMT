package king.saleh;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Folge extends RecursiveTask<Folge> {

    private int ersterIndex = 0;
    private int letzterIndex;

    private List<Integer> zahlenfolge;

    /**
     * Beziehe die aktuell abzuhandelnde Folge
     * Starte den Sortieralgorithmus
     */
    @Override
    public Folge compute() {
        QuickSortMultiThreaded.quicksort(this);
        return this;
    }

    public Folge(List<Integer> zahlenfolge) {

        if(zahlenfolge.size() <= 1) return;

        this.zahlenfolge = zahlenfolge;
        this.letzterIndex = zahlenfolge.size() - 1;

        System.out.println("Initial\t\t" + toString() + "\n");
    }

    Folge(List<Integer> zahlenfolge, int linkerIndex, int rechterIndex) {

        if (zahlenfolge.size() <= 1) return;

        this.zahlenfolge = zahlenfolge;
        this.ersterIndex = linkerIndex;
        this.letzterIndex = rechterIndex;
    }

    void vertauscheElemente(int ersterIndex, int zweiterIndex) {
        int cachedElement = zahlenfolge.get(ersterIndex);
        zahlenfolge.set(ersterIndex, zahlenfolge.get(zweiterIndex));
        zahlenfolge.set(zweiterIndex, cachedElement);
    }

    @Override
    public String toString() {
        return zahlenfolge.toString();
    }
    
    /**
     * Getter Methods
     */

    int getElement(int index) {
        return zahlenfolge.get(index);
    }

    int getErsterIndex() {
        return ersterIndex;
    }

    int getLetzterIndex() {
        return letzterIndex;
    }

    List<Integer> getFolge() {
        return zahlenfolge;
    }
}