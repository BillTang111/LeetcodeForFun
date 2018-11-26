package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import leetcode.BitTiger200;

class Test200 {

	BitTiger200 bt200 = new BitTiger200();
	
	@Test
	void test() {
	}
	
	@Test
	void findOrder() {
		int[][] prerequisites = new int[][] {{1,0}};
		int[] order = bt200.findOrder(2, prerequisites);
		
		int[] res = new int[] {0,1};
		Assert.assertArrayEquals(res,order);
	}
	
	@Test
	void findOrder2() {
		int[][] prerequisites = new int[][] {{1,0},{2,0},{3,1},{3,2}};
		int[] order = bt200.findOrder(4, prerequisites);
		
		int[] res = new int[] {0,1,2,3};
		Assert.assertArrayEquals(res,order);
	}
	

	@Test
	void moveZeroes() {
		int[] arr = new int[] {1,0,0};
		bt200.moveZeroes(arr);;
		int[] res = new int[] {1,0,0};
		Assert.assertArrayEquals(res,arr);
		
	}
	
	@Test
	void moveZeroes2() {
		int[] arr = new int[] {0,1,0};
		bt200.moveZeroes(arr);
		int[] res = new int[] {1,0,0};
		Assert.assertArrayEquals(res,arr);
		
	}
}
