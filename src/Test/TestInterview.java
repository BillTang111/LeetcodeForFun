package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import interview.Softheon;

class TestInterview {
	
	Softheon sf = new Softheon();

	@Test
	void test() {
		
	}
	
	@Test
	void findFirstRepeat() {
		String s = " the the sky is blue ";
		String output = sf.findFirstRepeat(s);
		assertEquals("the", output);
	}
	
	@Test
	void findFirstRepeat2() {
		String s = " the the sky had had is blue ";
		String output = sf.findFirstRepeat(s);
		assertEquals("the", output);
	}
	
	@Test
	void findFirstRepeat3() {
		String s = " ";
		String output = sf.findFirstRepeat(s);
		assertEquals("", output);
	}
	
	@Test
	void maxDifference() {
		int[] from = new int[]{1,1,2,2,3,4};
		int[] to = new int[]{2,3,3,4,4,5};
		int output = sf.maxDifference(5, from, to);
		assertEquals(4, output);
	}

}
