import king.saleh.Folge;
import king.saleh.Main;
import king.saleh.QuickSort;

import king.saleh.QuickSortMultiThreaded;
import org.junit.jupiter.api.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    
    private static Folge singleFolge, multiFolge;
    
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
    
    @Test
    void erstelleUndPruefeTestFolgen() {
    
        System.out.println("Single Threaded Test:\n");
        
        singleFolge = QuickSort.quicksort(new Folge(
                new ArrayList<Integer>(Main.generateRandomListOfLength(15))
        ));
        
        System.out.println("Sortierte Folge\t" + singleFolge.toString());
        if(singleFolge.isSorted()) System.out.println("Erfolgreich sortiert!\n");
    
        System.out.println("Multi Threaded Test:\n");
        
        multiFolge = Main.fjP.invoke(new Folge(
                Main.generateRandomListOfLength(15)
        ));
        
        System.out.println("Sortierte Folge\t" + multiFolge.toString());
        if(singleFolge.isSorted()) System.out.println("Erfolgreich sortiert!");
    }
    
    @AfterEach
    void clearFolge() {
        this.singleFolge = null;
        this.multiFolge = null;
    }
}