package leetcode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HeapsortTester {

    @Test
    public void testInsert() {
        int[] b= new int[10];
        int size= 0;
        size= Heapsort.add(2, b, size);
        assertEquals(2, b[0]);
        assertEquals(1, size);
        
        size= Heapsort.add(5, b, size);
        assertEquals(5, b[0]);
        assertEquals(2, b[1]);
        assertEquals(2, size);
        

        size= Heapsort.add(1, b, size);
        assertEquals(5, b[0]);
        assertEquals(2, b[1]);
        assertEquals(1, b[2]);
        assertEquals(3, size);
    }
    
    @Test
    public void testHeapsort() {
    int c[]= {5, 2, 4, 6, 1, -5, 8, 5, 4, 3, 9, 10, 11, 12};
    Heapsort.heapsort(c);
    assertEquals(-5, c[0]);
    assertEquals(1, c[1]);
    assertEquals(2, c[2]);
    assertEquals(3, c[3]);
    assertEquals(4, c[4]);
    assertEquals(4, c[5]);
    assertEquals(5, c[6]);
    assertEquals(5, c[7]);
    assertEquals(6, c[8]);
    assertEquals(8, c[9]);
    assertEquals(9, c[10]);
    assertEquals(10, c[11]);
    assertEquals(11, c[12]);
    assertEquals(12, c[13]);
}

}
