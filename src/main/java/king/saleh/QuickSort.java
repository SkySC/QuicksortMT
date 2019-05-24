package king.saleh;

public final class QuickSort implements Sort {

    private QuickSort() {

    }

    public static Folge quicksort(Folge folge) {
        quicksort(folge, folge.getErsterIndex(), folge.getLetzterIndex());
        return folge;
    }

    private static void quicksort(Folge aktuelleFolge, int ersterIndex, int letzterIndex) {
        if (ersterIndex >= letzterIndex) return;

        int partitionIndex = Sort.partition(aktuelleFolge, ersterIndex, letzterIndex);
        quicksort(aktuelleFolge, ersterIndex, partitionIndex - 1);
        quicksort(aktuelleFolge, partitionIndex + 1, letzterIndex);
        Sort.stepCounter.set(0);
    }
}