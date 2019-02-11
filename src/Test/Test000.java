package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import leetcode.BitTiger000;
import leetcode.BitTiger200;

class Test000 {

	BitTiger000 bt000 = new BitTiger000();
	
	@Test
	void myAtoi() {
		String input = "   +0 123";
		int output = bt000.myAtoi(input);
		assertEquals(0, output);
	}
	
	@Test
	void test() {

	}

}
