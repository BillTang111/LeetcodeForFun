package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import leetcode.BitTiger000;
import leetcode.BitTiger100;

class Test100 {

	BitTiger100 bt100 = new BitTiger100();
	
	@Test
	void test() {
		String s = " the sky is blue ";
		String output = bt100.reverseWords2(s);
		assertEquals("blue is sky the", output);
	}
	
	@Test
	void test2() {
		String s = " the  sky  is blue ";
		String output = bt100.reverseWords2(s);
		assertEquals("blue is sky the", output);
	}
	
	@Test
	void test3() {
		String s = " the  ";
		String output = bt100.reverseWords(s);
		assertEquals("the", output);
	}

}
