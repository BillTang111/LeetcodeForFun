package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import leetcode.BitTiger900;

class test900 {

	@Test
	void test() {
	}
	
	
//	@Test
//	void deckRevealedIncreasing() {
//		BitTiger900 obj = new BitTiger900();
//		int[] deck = new int[] {17,13,11,2,3,5,7};
//		int[] res = obj.deckRevealedIncreasing(deck);
//		int[] expect = new int[] {2,13,3,11,5,17,7};
//		assertEquals(expect, res);
//		
//	}

//	@Test
//	void isAlienSorted() {
//		BitTiger900 obj = new BitTiger900();
//		String[] words = new String[] {"kuvp","q"};
//		String order = "ngxlkthsjuoqcpavbfdermiywz";
//		boolean res = obj.isAlienSorted(words, order);
//		assertEquals(true, res);
//	}
	
	@Test
	void canReorderDoubled() {
		BitTiger900 obj = new BitTiger900();
		int[] A = new int[] {-8,-4,-2,-1,0,0,1,2,4,8};
//		String[] words = new String[] {"kuvp","q"};
//		String order = "ngxlkthsjuoqcpavbfdermiywz";
		boolean res = obj.canReorderDoubled(A);
		assertEquals(true, res);
		
	}
	

}
