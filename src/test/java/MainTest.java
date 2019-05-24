import king.saleh.Folge;
import king.saleh.Main;
import king.saleh.QuickSort;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    
    private Folge singleFolge, multiFolge;

    @Test
    void testMain()
    {
        assertTrue( true );
    }

    @Test
    void testrunMain() {
        
        king.saleh.Main.main(new String[] {});
    }
    
    @Test
    void testrunMain2() {
        
        another.implementation.Main.main(new String[] {});
    }
    
    @BeforeEach
    void erstelleTestFolgen() {
    
        System.out.println("Single Threaded Test:\n");
        
        this.singleFolge = QuickSort.quicksort(
                new Folge(
                        new ArrayList<Integer>(Main.generateRandomListOfLength(10))));
        
        System.out.println("Sortierte Folge\t" + singleFolge.toString() + "\n");
    
        System.out.println("Multi Threaded Test:\n");
        
        this.multiFolge = new ForkJoinPool(Runtime.getRuntime().availableProcessors())
                .invoke(new Folge(
                        Main.generateRandomListOfLength(10)));
        
        System.out.println("Sortierte Folge\t" + multiFolge.toString() + "\n");
    }
    
    @Test
    void singleFolgeIstSortiert() {
        Assertions.assertTrue(singleFolge.isSorted());
    }
    
    @Test
    void multiFolgeIstSortiert() {
        Assertions.assertTrue(multiFolge.isSorted());
    }
    
    @AfterEach
    void clearFolge() {
        this.singleFolge = null;
        this.multiFolge = null;
    }
}