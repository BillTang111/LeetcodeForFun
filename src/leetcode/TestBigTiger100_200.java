package leetcode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

class TestBigTiger100_200 {

	@Test
	void wordBreak() {
		BigTiger200 inst = new BigTiger200();
		String s = new String("Leetcode");
		List<String> wordDict = new ArrayList<>();
		wordDict.add("Leet"); wordDict.add("code");
		boolean res = inst.wordBreak( s,  wordDict);
		assertEquals(true, res);
	}
	
	@Test
	void LRUCache() {
		//["LRUCache","get","put","get","put","put","get","get"]
		//[[2],[2],[2,6],[1],[1,5],[1,2],[1],[2]]
		 LRUCache obj = new LRUCache(2);
		 obj.get(2);
		 obj.put(2,6);
		 obj.get(1);
		 obj.put(1,5);
		 obj.put(1,2);
		 obj.get(1);
		 int temp = obj.get(2);
		 assertEquals(6, temp);
	}
	
	@Test
	void test() {
	}

}
