package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import leetcode.SearchAndSort;

class TestSearchAndSort {

	SearchAndSort sas = new SearchAndSort();
	@Test
	void test() {

	}
	
	@Test
	void quickSort1() {
		int[] x = { 9, 2, 4, 7, 3, 7, 10 };
		int[] expect = {2, 3, 4, 7, 7, 9, 10};
		int low = 0;
		int high = x.length - 1;
 
		SearchAndSort.quickSort(x, low, high);
		
		assertArrayEquals(expect, x);
		
	}
	
	@Test
	void quickSort2() {
		int[] x = { 9, 2, 4, 7, 3, 7, 10 };
		int[] expect = {2, 3, 4, 7, 7, 9, 10};
		int low = 0;
		int high = x.length - 1;
 
		SearchAndSort.quickSort2(x, low, high);
		
		assertArrayEquals(expect, x);
		
	}

	
}
