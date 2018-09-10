package leetcode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SearchAndSortTest {

	@Test
	void testLinearSearch() {
        int[] b= {1, 3, 3, 3, 3, 4, 4, 6, 7, 8, 8, 10};
        assertEquals(12,SearchAndSort.linearSearch(b, -5));
        assertEquals(1,SearchAndSort.linearSearch(b, 3));
        assertEquals(0,SearchAndSort.linearSearch(b, 1));
        assertEquals(12, SearchAndSort.linearSearch(b, 2));
        assertEquals(9, SearchAndSort.linearSearch(b, 8));
        assertEquals(12, SearchAndSort.linearSearch(b, 12));
	}
	
	@Test
	void testBinarySearch() {
        int[] b= {1, 3, 3, 3, 3, 4, 4, 6, 7, 8, 8, 10};
        assertEquals(-1, SearchAndSort.binarySearch(b, -5));
        assertEquals(0, SearchAndSort.binarySearch(b, 1));
        assertEquals(0, SearchAndSort.binarySearch(b, 2));
        assertEquals(4, SearchAndSort.binarySearch(b, 3));
        assertEquals(6, SearchAndSort.binarySearch(b, 4));
        assertEquals(6, SearchAndSort.binarySearch(b, 5));
        assertEquals(10, SearchAndSort.binarySearch(b, 9));
        assertEquals(11, SearchAndSort.binarySearch(b, 10));
        assertEquals(11, SearchAndSort.binarySearch(b, 11));
	}
	
    @Test
    public void testMin() {
        int[] b= {3, 8, 5, -2};
        assertEquals(3, SearchAndSort.min(b, 0, 3));
        assertEquals(1, SearchAndSort.min(b, 1, 1));
        assertEquals(0, SearchAndSort.min(b, 0, 2));
    }
    
    @Test
    public void testSelectionSort() {
        int[] b= {};
        SearchAndSort.selectionSort(b);
        assertEquals("[]", SearchAndSort.toString(b));

        b= new int[] {6};
        SearchAndSort.selectionSort(b);
        assertEquals("[6]", SearchAndSort.toString(b));

        b= new int[] {6, 3, -2, 7, 5, 8, 7};
        SearchAndSort.selectionSort(b);
        assertEquals("[-2, 3, 5, 6, 7, 7, 8]", SearchAndSort.toString(b));
    }
    
    @Test
    public void testInsertValue() {
        int[] b= {2, 4, 6, 7, 8, 5, 1};
        SearchAndSort.insertValue(b, 1, 5);
        assertEquals("[2, 4, 5, 6, 7, 8, 1]", SearchAndSort.toString(b));
        
        SearchAndSort.insertValue(b, 1, 6);
        assertEquals("[2, 1, 4, 5, 6, 7, 8]", SearchAndSort.toString(b));
        
        SearchAndSort.insertValue(b, 0, 1);
        assertEquals("[1, 2, 4, 5, 6, 7, 8]", SearchAndSort.toString(b));
    }
    
    @Test
    public void testPartition() {
        int[] b= {4, 3, 8, 7, 8, 5, 1};
        int j= SearchAndSort.partition(b, 0, 6);
        for (int k= 0; k < j; k= k+1)
            assertTrue(b[k] < 4);
        assertTrue(b[j] == 4);
        for (int k= j+1; k < b.length; k= k+1)
            assertTrue(b[k] > 4);
        
        b= new int[]{0, 3, 8, 7, 8, 5, 1};
        j= SearchAndSort.partition(b, 0, 6);
        assertEquals(0, j);
        for (int k= 0; k < j; k= k+1)
            assertTrue(b[k] < 0);
        assertTrue(b[j] == 0);
        for (int k= j+1; k < b.length; k= k+1)
            assertTrue(b[k] > 0);
    }
    
    @Test
    public void testQuickSort() {
     // Test on an array {500, 499, 498, 497, ..., 1}
        int[] b= new int[500];
        for (int k= 0; k < b.length; k= k+1) {
            b[k]= b.length - k;
        }
        SearchAndSort.quickSort(b, 0, b.length-1);
        for (int k= 0; k < b.length; k= k+1) {
            assertEquals(k+1, b[k]);
        }
        
     // Test on an array {0, 1, 2, ..., 499}
        b= new int[500];
        for (int k= 0; k < b.length; k= k+1) {
            b[k]= k;
        }
        SearchAndSort.quickSort(b, 0, b.length-1);
        for (int k= 0; k < b.length; k= k+1) {
            assertEquals(k, b[k]);
        }
    }
    
    @Test
    public void testMerge() {
        int[] b= {2, 4, 5, 7, 9, 10, 0, 2, 3, 4, 8};
        SearchAndSort.merge(b, 0, 5, 10);
        assertEquals("[0, 2, 2, 3, 4, 4, 5, 7, 8, 9, 10]", SearchAndSort.toString(b));
        
        b= new int[]{2, 4, 5};
        SearchAndSort.merge(b, 0, 2, 2);
        assertEquals("[2, 4, 5]", SearchAndSort.toString(b));
    }
    
    @Test
    public void testMergeSort() {
        int[] b= new int[500];
        for (int k= 0; k < b.length; k= k+1) {
            b[k]= b.length - k;
        }
        SearchAndSort.mergeSort(b, 0, b.length-1);
        for (int k= 0; k < b.length; k= k+1) {
            assertEquals(k+1, b[k]);
        }
        
        b= new int[500];
        for(int k= 0; k < b.length; k= k+1) {
            b[k]= k;
        }
        SearchAndSort.mergeSort(b, 0, b.length-1);
        for (int k= 0; k < b.length; k= k+1) {
            assertEquals(k, b[k]);
        }
    }
}
