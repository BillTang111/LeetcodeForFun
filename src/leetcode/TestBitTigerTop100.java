package leetcode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class TestBitTigerTop100 {
	
	
	@Test
	void inorderTraversal() {
		TreeNode root = new TreeNode(1);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(3);
		
		BitTigerTop100 instance = new BitTigerTop100();
		List<Integer> lst = instance.inorderTraversal_Rec(root);
		List<Integer> ans = Stream.of(1, 3, 2).collect(Collectors.toList());
		assertEquals(ans, lst);
	}
	
	@Test
	void test() {
		
	}

}
