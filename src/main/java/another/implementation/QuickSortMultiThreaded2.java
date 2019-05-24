package another.implementation;

import king.saleh.QuickSortMultiThreaded;
import king.saleh.Sort;

public class QuickSortMultiThreaded2 implements Runnable {

    private int[] folge;
    private final int ersterIndex;
    private final int letzterIndex;
    private final boolean debug;

    QuickSortMultiThreaded2(int[] folge, int ersterIndex, int letzterIndex, boolean debug){
        this.folge = folge;
        this.ersterIndex = ersterIndex;
        this.letzterIndex = letzterIndex;
        this.debug = debug;
    }

    @Override
    public void run() {
        
        if (this.ersterIndex >= this.letzterIndex) return;
        int partition_index = partition(this.folge, this.ersterIndex, this.letzterIndex);
    
        Thread worker1 = new Thread(new QuickSortMultiThreaded2(this.folge, 0, partition_index - 1, debug));
        worker1.start();
        Thread worker2 = new Thread(new QuickSortMultiThreaded2(this.folge, partition_index + 1, this.letzterIndex, debug));
        worker2.start();
    
        try {
            worker1.join();
            worker2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int partition(int[] folgeay, int ersterIndex, int letzterIndex) {
        
        int pivot = folgeay[letzterIndex];
        int ersterZeiger = (ersterIndex - 1);

        for (int zweiterZeiger = ersterIndex; zweiterZeiger < letzterIndex; zweiterZeiger++) {
            if (folgeay[zweiterZeiger] <= pivot) {
                int swapTemp = folgeay[++ersterZeiger];
                folgeay[ersterZeiger] = folgeay[zweiterZeiger];
                folgeay[zweiterZeiger] = swapTemp;

                if(this.debug)
                    QuickSortMultiThreaded.addDebugMessage("Step " + Sort.stepCounter.incrementAndGet() + "\t\t"
                            + folgeay[ersterZeiger] + " <=> " + folgeay[zweiterZeiger]
                            + "\t\t" + Thread.currentThread());
            }
        }

        int swapTemp = folgeay[ersterZeiger + 1];
        folgeay[ersterZeiger + 1] = folgeay[letzterIndex];
        folgeay[letzterIndex] = swapTemp;

        return ersterZeiger + 1;
    }
}
