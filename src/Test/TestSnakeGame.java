package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import leetcode.SnakeGame;

class TestSnakeGame {

	
	@Test
	void test1() {
		int[][] food = new int[][] {{1,2},{0,1}};
		SnakeGame obj = new SnakeGame(3, 2, food);
		int param_1 = obj.move("R");
		assertEquals(0, param_1);
		int param_2 = obj.move("D");
		assertEquals(0, param_2);
		int param_3 = obj.move("R");
		assertEquals(1, param_3);
		int param_4 = obj.move("U");
		assertEquals(1, param_4);
		int param_5 = obj.move("L");
		assertEquals(2, param_5);
		int param_6 = obj.move("U");
		assertEquals(-1, param_6);
	}

	
}
