package LeecodeByTopics;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayTopicTester {

	@Test
	void testreverse() {
		int[] b= {3, 8, 5, -2};
		ArrayTopics.reverse(b);
		assertEquals("[-2,5,8,3]", ArrayTopics.printArr(b));
	}
	
	@Test
	void testtranspose() {
		int[][] b= new int[][] {{1,2,3},{4,5,6}};
		int[][] c= new int[][] {{1,4},{2,5},{3,6}};
		int[][] bb= ArrayTopics.transpose(b);
		assertEquals(ArrayTopics.print2D(c), ArrayTopics.print2D(bb));
	}

}
