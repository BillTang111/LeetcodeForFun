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
	
	@Test
	void StringTest() {
		String a = "abcd";
		String c = "abcd";
		Assert.assertTrue(a==c);
		
	}
	
	@Test
	void try1() {
		int a = 14;
		String b = Integer.toString(a);
		Assert.assertEquals("14" , b);
		
	}
	
	@Test
	void calculate() {
		String a = "(1+(4+5+2)-3)+(6+8)";
		Assert.assertEquals(23,bt200.calculate(a));
		
	}
	
	@Test
	void wordPatternMatch() {
		String pattern = "abab";
		String str = "redblueredblue";
		assertEquals(true, bt200.wordPatternMatch( pattern,  str));
		
	}
	
	
}
